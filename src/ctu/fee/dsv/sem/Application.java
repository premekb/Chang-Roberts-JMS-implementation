package ctu.fee.dsv.sem;

import com.sun.messaging.ConnectionConfiguration;
import com.sun.messaging.ConnectionFactory;
import ctu.fee.dsv.sem.cmdline.ConsoleHandler;
import ctu.fee.dsv.sem.cmdline.NodeConfiguration;

import javax.jms.*;
import java.io.IOException;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Application {

    private static final String IMQ_BROKER_HOST_NAME_KEY = "imqBrokerHostName";

    private static final String IMQ_CONNECTION_URL_KEY = "imqConnectionURL";

    private static final String IMQ_ACK_TIMEOUT_KEY = "imqAckTimeout";

    private static final String IMQ_ACK_TIMEOUT_VALUE = "1000";

    private static final Logger ROOT_LOGGER = Logger.getLogger("");
    public static void main(String[] args) throws JMSException, IOException {
        NodeConfiguration nodeCfg = initNodeCfg(args);
        configureLogger(nodeCfg);
        Connection connection = initJmsConnection(nodeCfg);

        Node node = new NodeImpl(nodeCfg, connection);
        ConsoleHandler consoleHandler = new ConsoleHandler(node);
        new Thread(consoleHandler).start();
        node.run();
    }

    private static Connection initJmsConnection(NodeConfiguration nodeCfg) throws JMSException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setProperty(IMQ_BROKER_HOST_NAME_KEY, nodeCfg.getBrokerHostname());
        connectionFactory.setProperty(ConnectionConfiguration.imqAddressList, nodeCfg.getAddressList());
        connectionFactory.setProperty(IMQ_CONNECTION_URL_KEY, nodeCfg.getBrokerUrl());
        connectionFactory.setProperty(IMQ_ACK_TIMEOUT_KEY, IMQ_ACK_TIMEOUT_VALUE);
        connectionFactory.setProperty(ConnectionConfiguration.imqReconnectEnabled, "true");
        Connection connection = connectionFactory.createConnection();
        connection.start();
        return connection;
    }

    private static NodeConfiguration initNodeCfg(String[] args)
    {
        NodeConfiguration nodeCfg;
        if (args.length == 5)
        {
            nodeCfg = NodeConfiguration.createWithLocalhostIp(args);
        }

        else if (args.length == 6)
        {
            nodeCfg = NodeConfiguration.create(args);
        }

        else if (args.length == 7)
        {
            nodeCfg = NodeConfiguration.createClusterConfiguration(args);
        }

        else {
            throw new IllegalArgumentException("Invalid cmdline arguments");
        }

        return nodeCfg;
    }

    private static void configureLogger(NodeConfiguration nodeConfiguration) throws IOException {
        FileHandler fileHandler = new FileHandler(nodeConfiguration.getId() + " " + nodeConfiguration.getNodeName() + ".txt");
        fileHandler.setFormatter(new LogFormatter());

        // Add file handler as
        // handler of logs
        ROOT_LOGGER.addHandler(fileHandler);

        ROOT_LOGGER.removeHandler(ROOT_LOGGER.getHandlers()[0]);
        java.util.logging.ConsoleHandler consoleHandler = new java.util.logging.ConsoleHandler();
        consoleHandler.setFormatter(new LogFormatter());
        ROOT_LOGGER.addHandler(consoleHandler);
    }

    private static class LogFormatter extends SimpleFormatter
    {
        private static final String format = "[%2$-7s] %3$s %n";

        @Override
        public synchronized String format(LogRecord lr) {
            return String.format(format,
                    new Date(lr.getMillis()),
                    lr.getLevel().getLocalizedName(),
                    lr.getMessage()
            );
        }
    }
}
