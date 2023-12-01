package ctu.fee.dsv.sem.communication.messages;

import ctu.fee.dsv.sem.NodeAddress;
import ctu.fee.dsv.sem.clock.LogicalLocalClock;
import ctu.fee.dsv.sem.communication.facade.MessageProcessor;

public class HeartbeatMessage extends Message {
    public final NodeAddress senderNodeAddress;

    public HeartbeatMessage(LogicalLocalClock logicalLocalClock, NodeAddress senderNodeAddress) {
        super(logicalLocalClock);
        this.senderNodeAddress = senderNodeAddress;
    }

    @Override
    public void process(MessageProcessor messageProcessor) {
        messageProcessor.processHeartbeatMessage(this);
    }

    @Override
    public String toString() {
        return super.toString() + " from " + senderNodeAddress + " ";
    }
}
