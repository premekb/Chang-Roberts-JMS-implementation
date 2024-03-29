package ctu.fee.dsv.sem.communication.wrapper;

import ctu.fee.dsv.sem.NodeAddress;
import ctu.fee.dsv.sem.ProducerClosingException;
import ctu.fee.dsv.sem.communication.messages.HeartbeatMessage;
import ctu.fee.dsv.sem.communication.messages.HeartbeatMessageResponse;
import ctu.fee.dsv.sem.communication.messages.Message;
import ctu.fee.dsv.sem.communication.util.QueueNameUtil;

import javax.jms.*;
import javax.jms.IllegalStateException;
import java.util.logging.Logger;

public class MessageProducerImpl implements MessageProducer{
    private final javax.jms.MessageProducer jmsProducer;

    private static final Logger log = Logger.getLogger(MessageProducerImpl.class.toString());

    private final String queueName;

    private final Session jmsSession;

    private final boolean logHeartbeat;
    public MessageProducerImpl(Session session, NodeAddress senderAddress, NodeAddress receiverAddress, boolean logHeartbeat) {
        try {
            jmsSession = session;
            queueName = QueueNameUtil.getQueueName(receiverAddress);
            Queue queue = new com.sun.messaging.Queue(queueName);
            jmsProducer = session.createProducer(queue);
            this.logHeartbeat = logHeartbeat;
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

            if ((message instanceof HeartbeatMessage || message instanceof HeartbeatMessageResponse) && !logHeartbeat)
            {
                return;
            }

            log.info("SENT     " + message.toString() +  " To queue: " + queueName);
        } catch (JMSException e) {
            log.severe("FAILED   " + e.getMessage());
            if (e instanceof IllegalStateException)
            {
                throw new ProducerClosingException(e.getMessage());
            }

            else
            {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void close() {
        try {
            jmsProducer.close();
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }
}
