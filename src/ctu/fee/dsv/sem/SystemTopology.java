package ctu.fee.dsv.sem;

import ctu.fee.dsv.sem.clock.LogicalLocalClock;
import ctu.fee.dsv.sem.communication.facade.HeartbeatServiceImpl;
import ctu.fee.dsv.sem.communication.facade.MessageSender;
import ctu.fee.dsv.sem.communication.messages.ExploreTopologyMessage;

import java.util.logging.Logger;

public class SystemTopology {

    private static final Logger log = Logger.getLogger(HeartbeatServiceImpl.class.toString());

    private String cachedResponse;

    private final MessageSender messageSender;

    private final LogicalLocalClock logicalLocalClock;

    public SystemTopology(MessageSender messageSender, LogicalLocalClock logicalLocalClock) {
        this.messageSender = messageSender;
        this.logicalLocalClock = logicalLocalClock;
    }

    public String getData()
    {
        cachedResponse = null;
        try {
            messageSender.sendMessageToNext(new ExploreTopologyMessage(logicalLocalClock, messageSender.getSenderAddress()));
            for (int i = 0; i < 10; i++)
            {
                Thread.sleep(i * 200);
                if (cachedResponse != null)
                {
                    return cachedResponse;
                }
            }
            log.severe("!!!!!!!-----FAILED TO RETRIEVE SYSTEM TOPOLOGY-----!!!!!!!");
            return null;
        } catch (InterruptedException e) {
            return cachedResponse;
        }
    }

    public void setCachedResultFromResponse(String data)
    {
        cachedResponse = data;
    }
}
