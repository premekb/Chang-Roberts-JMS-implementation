package ctu.fee.dsv.sem;

import com.sun.messaging.ConnectionConfiguration;
import com.sun.messaging.ConnectionFactory;
import ctu.fee.dsv.sem.communication.MessageConsumer;
import ctu.fee.dsv.sem.communication.MessageConsumerImpl;
import ctu.fee.dsv.sem.communication.MessageProducerImpl;
import ctu.fee.dsv.sem.communication.messages.GetSharedVariableMessage;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.naming.spi.InitialContextFactoryBuilder;
import java.util.Properties;

public class Application {

    private static final String IMQ_BROKER_HOST_NAME_KEY = "imqBrokerHostName";

    private static final String IMQ_CONNECTION_URL_KEY = "imqConnectionURL";
    public static void main(String[] args) throws JMSException, NamingException, InterruptedException {
        Connection connection = initJmsConnection();
        Session session = connection.createSession();

        NodeAddress thisNodeAddress = new NodeAddress("okok", 100);
        NodeAddress receiverNodeAddress = new NodeAddress("cau", 200);
        MessageProducerImpl producer = new MessageProducerImpl(session, thisNodeAddress, thisNodeAddress);
        producer.sendMessage(new GetSharedVariableMessage());

        Thread.sleep(1000);

        MessageConsumer consumer = new MessageConsumerImpl(session, thisNodeAddress, thisNodeAddress);
        System.out.println(consumer.tryGetMessage());

        connection.close();
        session.close();
    }

    private static Connection initJmsConnection() throws JMSException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setProperty(IMQ_BROKER_HOST_NAME_KEY, "localhost");
        connectionFactory.setProperty(IMQ_CONNECTION_URL_KEY, "http://localhost/imq/tunnel");
        Connection connection = connectionFactory.createConnection();
        connection.start();
        return connection;
    }
}
