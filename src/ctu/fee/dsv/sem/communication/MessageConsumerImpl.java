package ctu.fee.dsv.sem.communication;

import ctu.fee.dsv.sem.NodeAddress;
import ctu.fee.dsv.sem.communication.messages.Message;
import ctu.fee.dsv.sem.communication.util.QueueNameUtil;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import java.util.logging.Logger;

public class MessageConsumerImpl implements MessageConsumer {
    private final javax.jms.MessageConsumer jmsConsumer;

    private static final Logger log = Logger.getLogger(MessageConsumerImpl.class.toString());
    public MessageConsumerImpl(Session session, NodeAddress senderAddress, NodeAddress receiverAddress) {
        try {
            String queueName = QueueNameUtil.getQueueName(senderAddress, receiverAddress);
            Queue queue = new com.sun.messaging.Queue(queueName);
            jmsConsumer = session.createConsumer(queue);
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Message tryGetMessage() {
        return tryGetMessage(100);
    }

    @Override
    public Message tryGetMessage(long millisecondsToWait) {
        try {
            javax.jms.Message jmsMessage = jmsConsumer.receive(millisecondsToWait);
            if (jmsMessage == null) return null;

            Object object = ((ObjectMessage) jmsMessage).getObject();
            Message message = (Message) object;
            log.info("Received message. " + message.toString());
            return (Message) object;
        } catch (JMSException e) {
            log.severe("Failed to process message. " + e.getMessage());
            return null;
        }
    }
}
