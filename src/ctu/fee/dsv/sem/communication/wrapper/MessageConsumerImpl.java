package ctu.fee.dsv.sem.communication.wrapper;

import ctu.fee.dsv.sem.NodeAddress;
import ctu.fee.dsv.sem.communication.messages.Message;
import ctu.fee.dsv.sem.communication.util.QueueNameUtil;

import javax.jms.*;
import java.util.logging.Logger;

public class MessageConsumerImpl implements MessageConsumer {
    private final javax.jms.MessageConsumer jmsConsumer;

    private final String queueName;

    private static final Logger log = Logger.getLogger(MessageConsumerImpl.class.toString());
    public MessageConsumerImpl(Session session, NodeAddress receiverAddress, boolean purgeQueue) {
        try {
            queueName = QueueNameUtil.getQueueName(receiverAddress);
            Queue queue = new com.sun.messaging.Queue(queueName);
            jmsConsumer = session.createConsumer(queue);
            if (purgeQueue)
            {
                purgeQueue();
            }
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
            return (Message) object;
        } catch (JMSException e) {
            log.severe("Failed to process message. " + e.getMessage());
            return null;
        }
    }

    @Override
    public void setMessageListener(MessageListener messageListener) {
        try {
            jmsConsumer.setMessageListener(messageListener);
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }

    private void purgeQueue() throws JMSException {
        log.info("PURGING QUEUE: " + queueName);
        while (jmsConsumer.receive(100) != null)
        {

        }
    }

    @Override
    public void close() {
        try {
            jmsConsumer.close();
        } catch (JMSException e) {
            log.severe("Failed to close consumer.");
            throw new RuntimeException(e);
        }
    }
}
