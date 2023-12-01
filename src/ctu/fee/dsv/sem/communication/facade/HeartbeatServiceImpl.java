package ctu.fee.dsv.sem.communication.facade;

import ctu.fee.dsv.sem.Node;
import ctu.fee.dsv.sem.NodeImpl;
import ctu.fee.dsv.sem.clock.LogicalLocalClock;
import ctu.fee.dsv.sem.communication.messages.HeartbeatMessage;

import java.util.logging.Logger;

public class HeartbeatServiceImpl implements HeartbeatService {

    private static final Logger log = Logger.getLogger(HeartbeatServiceImpl.class.toString());

    private static Integer  CHECKING_PERIOD = 15_000;
    private final MessageSender messageSender;

    private final Node node;

    private final LogicalLocalClock logicalLocalClock;

    private boolean heartbeatResponseReceived;

    public HeartbeatServiceImpl(MessageSender messageSender, Node node, LogicalLocalClock logicalLocalClock) {
        this.messageSender = messageSender;
        this.node = node;
        this.logicalLocalClock = logicalLocalClock;
    }

    /**
     *
     */
    @Override
    public void run() {
        try {
            messageSender.sendMessageToNext(new HeartbeatMessage(logicalLocalClock, node.getNodeAddress()));
            Thread.sleep(CHECKING_PERIOD);

            while (true) {
                if (!heartbeatResponseReceived)
                {
                    node.repairNextNodeMissing();
                    Thread.sleep(1000);
                }
                heartbeatResponseReceived = false;
                messageSender.sendMessageToNext(new HeartbeatMessage(logicalLocalClock, node.getNodeAddress()));
                Thread.sleep(CHECKING_PERIOD);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void heartbeatReceived() {
        heartbeatResponseReceived = true;
    }
}