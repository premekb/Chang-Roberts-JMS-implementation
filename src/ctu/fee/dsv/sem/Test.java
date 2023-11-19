package ctu.fee.dsv.sem;

import com.sun.messaging.ConnectionFactory;
import ctu.fee.dsv.sem.cmdline.NodeConfiguration;
import ctu.fee.dsv.sem.communication.wrapper.MessageConsumer;
import ctu.fee.dsv.sem.communication.wrapper.MessageConsumerImpl;
import ctu.fee.dsv.sem.communication.wrapper.MessageProducerImpl;
import ctu.fee.dsv.sem.communication.messages.GetSharedVariableMessage;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.naming.NamingException;

public class Test {

    private static final String IMQ_BROKER_HOST_NAME_KEY = "imqBrokerHostName";

    private static final String IMQ_CONNECTION_URL_KEY = "imqConnectionURL";
    public static void main(String[] args) throws JMSException, NamingException, InterruptedException {
        NodeConfiguration nodeCfg = initNodeCfg(args);
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

    private static NodeConfiguration initNodeCfg(String[] args)
    {
        NodeConfiguration nodeCfg;
        if (args.length == 4)
        {
            nodeCfg = NodeConfiguration.createWithLocalhostIp(args);
        }

        else if (args.length == 5)
        {
            nodeCfg = NodeConfiguration.create(args);
        }

        else {
            throw new IllegalArgumentException("Invalid cmdline arguments");
        }

        return nodeCfg;
    }
}
