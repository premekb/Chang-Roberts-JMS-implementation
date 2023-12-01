package ctu.fee.dsv.sem.communication.wrapper;

import ctu.fee.dsv.sem.NodeAddress;
import ctu.fee.dsv.sem.communication.messages.Message;
import ctu.fee.dsv.sem.communication.util.QueueNameUtil;

import javax.jms.*;
import java.util.logging.Logger;

public class MessageProducerImpl implements MessageProducer{
    private final javax.jms.MessageProducer jmsProducer;

    private static final Logger log = Logger.getLogger(MessageProducerImpl.class.toString());

    private final String queueName;

    private final Session jmsSession;
    public MessageProducerImpl(Session session, NodeAddress senderAddress, NodeAddress receiverAddress) {
        try {
            jmsSession = session;
            queueName = QueueNameUtil.getQueueName(receiverAddress);
            Queue queue = new com.sun.messaging.Queue(queueName);
            jmsProducer = session.createProducer(queue);
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendMessage(Message message) {
        try {
            ObjectMessage objectMessage = jmsSession.createObjectMessage();
            objectMessage.setObject(message);

            jmsProducer.send(objectMessage);
            log.info("SENT     " + message.toString() +  " To queue: " + queueName);
        } catch (JMSException e) {
            log.severe("FAILED   " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
