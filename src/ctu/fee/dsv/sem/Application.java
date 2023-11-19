package ctu.fee.dsv.sem;

import com.sun.messaging.ConnectionFactory;
import ctu.fee.dsv.sem.cmdline.ConsoleHandler;
import ctu.fee.dsv.sem.cmdline.NodeConfiguration;

import javax.jms.*;
import javax.naming.NamingException;

public class Application {

    private static final String IMQ_BROKER_HOST_NAME_KEY = "imqBrokerHostName";

    private static final String IMQ_CONNECTION_URL_KEY = "imqConnectionURL";
    public static void main(String[] args) throws JMSException, NamingException, InterruptedException {
        NodeConfiguration nodeCfg = initNodeCfg(args);
        Connection connection = initJmsConnection();
        Session session = connection.createSession();

        Node node = new NodeImpl(nodeCfg, session);
        ConsoleHandler consoleHandler = new ConsoleHandler(node);
        new Thread(consoleHandler).start();
        node.run();

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
