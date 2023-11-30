package ctu.fee.dsv.sem.communication.messages;

import ctu.fee.dsv.sem.NodeAddress;
import ctu.fee.dsv.sem.communication.facade.MessageProcessor;

public class HeartbeatMessageResponse extends Message {
    public final NodeAddress senderNodeAddress;

    public HeartbeatMessageResponse(NodeAddress senderNodeAddress) {
        this.senderNodeAddress = senderNodeAddress;
    }

    @Override
    public void process(MessageProcessor messageProcessor) {
        messageProcessor.processHeartbeatMessageResponse(this);
    }
}
