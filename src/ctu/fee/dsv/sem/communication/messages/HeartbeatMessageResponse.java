package ctu.fee.dsv.sem.communication.messages;

import ctu.fee.dsv.sem.NodeAddress;
import ctu.fee.dsv.sem.clock.LogicalLocalClock;
import ctu.fee.dsv.sem.communication.facade.MessageProcessor;

public class HeartbeatMessageResponse extends Message {
    public final NodeAddress senderNodeAddress;

    public HeartbeatMessageResponse(LogicalLocalClock logicalLocalClock, NodeAddress senderNodeAddress) {
        super(logicalLocalClock);
        this.senderNodeAddress = senderNodeAddress;
    }

    @Override
    public void process(MessageProcessor messageProcessor) {
        messageProcessor.processHeartbeatMessageResponse(this);
    }
}
