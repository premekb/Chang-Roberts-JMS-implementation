package ctu.fee.dsv.sem.communication.util;

import ctu.fee.dsv.sem.NodeAddress;

public class QueueNameUtil {

    @Deprecated
    public static String getQueueName(NodeAddress sender, NodeAddress receiver)
    {
        return "Sender." + sender.getNodeName() +
                ".Receiver." + receiver.getNodeName();
    }

    public static String getQueueName(NodeAddress receiver)
    {
        return "Receiver." + receiver.getNodeName();
    }
}
