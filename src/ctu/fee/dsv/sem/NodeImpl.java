package ctu.fee.dsv.sem;

import ctu.fee.dsv.sem.cmdline.NodeConfiguration;
import ctu.fee.dsv.sem.communication.facade.*;
import ctu.fee.dsv.sem.communication.wrapper.MessageConsumerImpl;
import ctu.fee.dsv.sem.communication.messages.LoginMessage;
import ctu.fee.dsv.sem.communication.messages.Message;
import ctu.fee.dsv.sem.sharedvariable.SharedVariable;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.jms.Session;
import java.util.logging.Logger;

public class NodeImpl implements Node, Runnable {

    private static final Logger log = Logger.getLogger(NodeImpl.class.toString());
    private SharedVariable<String> sharedVariable; // Tohle by mohla byt proxy classa a jestli bude node leader tak bude jina implementace nez kdyz neni leader

    private Neighbours neighbours;

    private final NodeAddress address;

    private final NodeAddress initialNodeAddress;

    private final MessageSender messageSender;

    private final MessageReceiver messageReceiver;

    private final MessageProcessor messageProcessor;

    private final Session session;

    public NodeImpl(NodeConfiguration cfg, Session session) {
        this.session = session;
        address = new NodeAddress(cfg.getNodeName(), cfg.getId());
        initialNodeAddress = new NodeAddress(cfg.getLoginNodeName(), cfg.getLoginNodeId());
        neighbours = new Neighbours(address);
        this.messageSender = new MessageSenderImpl(session, address, neighbours);
        this.messageReceiver = new MessageReceiverImpl(this, session);
        this.messageProcessor = new MessageProcessorImpl(this, messageSender);
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

    @Override
    public SharedVariable getSharedVariable() {
        return sharedVariable;
    }

    @Override
    public void setSharedVariable(SharedVariable sharedVariable) {
        this.sharedVariable = sharedVariable;
    }

    @Override
    public void terminateWithLogout() {
        throw new NotImplementedException();
    }

    @Override
    public void terminateWithoutLogout() {
        throw new NotImplementedException();
    }

    /**
     * Send a login request to the initial node from config. Wait synchronously for 5 seconds.
     * If not reply is received, then this is the first node.
     */
    private void login() {
        log.info("Trying to login to node: " + initialNodeAddress.toString());
        messageSender.sendMessageToAddress(new LoginMessage(address), initialNodeAddress);

        MessageConsumerImpl consumer = new MessageConsumerImpl(session, initialNodeAddress, address);
        Message response = consumer.tryGetMessage(2000);

        if (response == null)
        {
            log.severe("Login failed. Continuing as a single node");
        }

        else
        {
            processMessage(response);
            log.info("Login successful.");
        }
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
    }
}
