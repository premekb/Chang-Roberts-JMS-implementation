package ctu.fee.dsv.sem;

import ctu.fee.dsv.sem.clock.LogicalLocalClock;
import ctu.fee.dsv.sem.clock.LogicalLocalClockImpl;
import ctu.fee.dsv.sem.cmdline.NodeConfiguration;
import ctu.fee.dsv.sem.communication.facade.*;
import ctu.fee.dsv.sem.communication.messages.election.ElectMessage;
import ctu.fee.dsv.sem.communication.messages.neighbourchange.NewNextNextMessage;
import ctu.fee.dsv.sem.communication.messages.neighbourchange.NewPrevMessage;
import ctu.fee.dsv.sem.communication.messages.neighbourchange.RepairMyNextNextMessage;
import ctu.fee.dsv.sem.communication.util.NeighboursEdgeCaseUtil;
import ctu.fee.dsv.sem.communication.wrapper.MessageConsumerImpl;
import ctu.fee.dsv.sem.communication.messages.LoginMessage;
import ctu.fee.dsv.sem.communication.messages.Message;
import ctu.fee.dsv.sem.sharedvariable.LocalStringSharedVariable;
import ctu.fee.dsv.sem.sharedvariable.RemoteStringSharedVariable;
import ctu.fee.dsv.sem.sharedvariable.StringSharedVariable;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.jms.Session;
import java.util.logging.Logger;

public class NodeImpl implements Node, Runnable {

    private static final Logger log = Logger.getLogger(NodeImpl.class.toString());
    private StringSharedVariable sharedVariable; // Tohle by mohla byt proxy classa a jestli bude node leader tak bude jina implementace nez kdyz neni leader

    private Neighbours neighbours;

    private final NodeAddress address;

    private final NodeAddress initialNodeAddress;

    private final MessageSender messageSender;

    private final MessageReceiver messageReceiver;

    private final MessageProcessor messageProcessor;

    private final HeartbeatService heartbeatService;

    private final LogicalLocalClock logicalLocalClock;

    private final Session session;

    private boolean voting = false;

    public NodeImpl(NodeConfiguration cfg, Session session) {
        this.logicalLocalClock = new LogicalLocalClockImpl();
        this.session = session;
        address = new NodeAddress(cfg.getNodeName(), cfg.getId());
        initialNodeAddress = new NodeAddress(cfg.getLoginNodeName(), cfg.getLoginNodeId());
        neighbours = new Neighbours(address);
        this.messageSender = new MessageSenderImpl(session, address, neighbours);
        this.messageReceiver = new MessageReceiverImpl(this, session);
        this.heartbeatService = new HeartbeatServiceImpl(messageSender, this, logicalLocalClock);
        this.messageProcessor = new MessageProcessorImpl(this, messageSender, heartbeatService, logicalLocalClock);
        this.sharedVariable = new LocalStringSharedVariable();
    }


    @Override
    public void run() {
        // tady jsou neighbours sami na sebe
        login();
        messageReceiver.startListeningToMessages();
        new Thread(heartbeatService).start();
        while (true)
        {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    // TODO checki asi jestli jsem leader nebo ne, kdyztak zkontrolovat, ze tam je remote
    @Override
    public StringSharedVariable getSharedVariable() {
        return sharedVariable;
    }

    @Override
    public void setSharedVariable(String data) {
        this.sharedVariable.setData(data);
    }

    @Override
    public void logout() {
        log.info("Logging out.");
        // Nexte, zmen si prev na meho prev
        // Preve, prevezmi moje neighbours.
        // Preve, predej svoje neighbours svojemu prevovi.

        throw new NotImplementedException();
    }

    @Override
    public void terminateWithoutLogout() {
        log.warning("TERMINATING WITHOUT LETTING OTHER NODES KNOW.");
        System.exit(1);
    }

    /**
     * Send a login request to the initial node from config. Wait synchronously for 5 seconds.
     * If not reply is received, then this is the first node.
     */
    private void login() {
        log.info("Trying to login to node: " + initialNodeAddress.toString());
        MessageConsumerImpl consumer = new MessageConsumerImpl(session, address, true);
        messageSender.sendMessageToAddress(new LoginMessage(logicalLocalClock, address), initialNodeAddress);

        Message response = consumer.tryGetMessage(2000);

        if (response == null)
        {
            log.severe("Login failed. Continuing as a single node");
        }

        else
        {
            sharedVariable = new RemoteStringSharedVariable(messageSender, logicalLocalClock);
            processMessage(response);
            log.info("Login successful.");
        }
        consumer.close();
    }

    @Override
    public NodeAddress getNodeAddress() {
        return address;
    }

    @Override
    public Neighbours getNeighbours() {
        return neighbours;
    }

    @Override
    public void processMessage(Message message) {
        message.process(messageProcessor);
    }

    @Override
    public void setNeighbours(Neighbours neighbours) {
        this.neighbours = neighbours;
        this.messageReceiver.startListeningToMessages();
        this.messageSender.setNewReceivers(neighbours.next, neighbours.nnext, neighbours.prev, neighbours.leader);
    }

    @Override
    public String toString() {
        return "NODE STATUS\n"
                + address + "\n"
                + neighbours;
    }

    @Override
    public boolean isVoting() {
        return voting;
    }

    @Override
    public void setVoting(boolean voting) {
        this.voting = voting;
    }

    @Override
    public void setLeader(NodeAddress address) {
        setNeighbours(new Neighbours(
                address,
                neighbours.next,
                neighbours.nnext,
                neighbours.prev
        ));
    }

    /**
     * TODO tady mozna potrebujes synchronni odpoved na to kdo je tvuj novy nnext
     * TODO ted se ptas asynchronne, mozna ale budes muset prepsat system prijimani a posilani zprav do nejakeho synchronniho while cyklu
     */
    @Override
    public void repairNextNodeMissing() {
        log.severe("HEARBEAT NOT RECEIVED. REPAIRING TOPOLOGY");
        boolean leaderMissing = neighbours.next.equals(neighbours.leader);
        boolean isThreeNodesConfig = NeighboursEdgeCaseUtil.isThreeNodesConfig(neighbours);

        // SET NEW NEXT
        setNeighbours(new Neighbours(
                neighbours.leader,
                neighbours.nnext,
                neighbours.nnext,
                neighbours.prev
        ));

        // OD NOVEHO NEXTA DOSTAN SVUJ NOVY NNEXT
        if (isThreeNodesConfig)
        {
            setNeighbours(new Neighbours(
                    neighbours.leader,
                    neighbours.next,
                    address,
                    neighbours.prev
            ));
        }
        else
        {
            messageSender.sendMessageToNext(new RepairMyNextNextMessage(logicalLocalClock, address));
        }

        // NNEXTOVI POSLI ZE JSI JEHO PREV
        messageSender.sendMessageToNext(new NewPrevMessage(logicalLocalClock, this.address)); // Tohle mozna nemusi probehnout, on to pozna z te zpravy?
        // PREVOVI POSLI NOVY NNEXT
        messageSender.sendMessageToPrev(new NewNextNextMessage(logicalLocalClock, neighbours.next));
        // LEADER ELECTION JESTLI UMREL LEADER
        if (leaderMissing)
        {
            startElection();
        }
    }

    @Override
    public void startElection() {
        processMessage(new ElectMessage(logicalLocalClock, getNeighbours().prev));
    }
}
