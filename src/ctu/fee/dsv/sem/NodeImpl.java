package ctu.fee.dsv.sem;

import ctu.fee.dsv.sem.cmdline.NodeConfiguration;
import ctu.fee.dsv.sem.communication.facade.*;
import ctu.fee.dsv.sem.communication.messages.GetSharedVariableMessage;
import ctu.fee.dsv.sem.communication.wrapper.MessageConsumer;
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

    private final Session session;

    private boolean voting = false;

    public NodeImpl(NodeConfiguration cfg, Session session) {
        this.session = session;
        address = new NodeAddress(cfg.getNodeName(), cfg.getId());
        initialNodeAddress = new NodeAddress(cfg.getLoginNodeName(), cfg.getLoginNodeId());
        neighbours = new Neighbours(address);
        this.messageSender = new MessageSenderImpl(session, address, neighbours);
        this.messageReceiver = new MessageReceiverImpl(this, session);
        this.messageProcessor = new MessageProcessorImpl(this, messageSender);
        this.sharedVariable = new LocalStringSharedVariable();
    }


    @Override
    public void run() {
        // tady jsou neighbours sami na sebe
        login();
        messageReceiver.startListeningToMessages();
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
        messageSender.sendMessageToAddress(new LoginMessage(address), initialNodeAddress);

        Message response = consumer.tryGetMessage(2000);

        if (response == null)
        {
            log.severe("Login failed. Continuing as a single node");
        }

        else
        {
            sharedVariable = new RemoteStringSharedVariable(messageSender);
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
}
