package ctu.fee.dsv.sem.communication.facade;

import ctu.fee.dsv.sem.Node;
import ctu.fee.dsv.sem.NodeImpl;
import ctu.fee.dsv.sem.communication.messages.HeartbeatMessage;

import java.util.logging.Logger;

public class HeartbeatServiceImpl implements HeartbeatService {

    private static final Logger log = Logger.getLogger(HeartbeatServiceImpl.class.toString());

    private static Integer  CHECKING_PERIOD = 5_000;
    private final MessageSender messageSender;

    private final Node node;

    private boolean heartbeatResponseReceived;

    public HeartbeatServiceImpl(MessageSender messageSender, Node node) {
        this.messageSender = messageSender;
        this.node = node;
    }

    /**
     *
     */
    @Override
    public void run() {
        try {
            log.info("Sending heratbeat request.");
            messageSender.sendMessageToNext(new HeartbeatMessage(node.getNodeAddress()));
            Thread.sleep(CHECKING_PERIOD);

            while (true) {
                if (!heartbeatResponseReceived)
                {
                    node.repairNextNodeMissing();
                    Thread.sleep(1000);
                }
                heartbeatResponseReceived = false;
                log.info("Sending heratbeat request.");
                messageSender.sendMessageToNext(new HeartbeatMessage(node.getNodeAddress()));
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
