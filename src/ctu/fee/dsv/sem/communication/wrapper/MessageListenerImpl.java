package ctu.fee.dsv.sem.communication.wrapper;

import ctu.fee.dsv.sem.Node;
import ctu.fee.dsv.sem.NodeImpl;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import java.util.logging.Logger;

public class MessageListenerImpl implements MessageListener {
    private final Node node;

    private static final Logger log = Logger.getLogger(NodeImpl.class.toString());
    public MessageListenerImpl(Node node) {
        this.node = node;
    }

    @Override
    public void onMessage(Message message) {
        try {
            Object object = ((ObjectMessage) message).getObject();
            ctu.fee.dsv.sem.communication.messages.Message retrievedMessage = (ctu.fee.dsv.sem.communication.messages.Message) object;
            node.processMessage(retrievedMessage);
        } catch (JMSException e) {
            log.severe("Failed to retrieve message: " + message);
        }
    }
}
