package ctu.fee.dsv.sem.communication.messages;

import ctu.fee.dsv.sem.NodeAddress;
import ctu.fee.dsv.sem.communication.facade.MessageProcessor;

public class HeartbeatMessage extends Message {
    public final NodeAddress SenderNodeAddress;

    public HeartbeatMessage(NodeAddress senderNodeAddress) {
        SenderNodeAddress = senderNodeAddress;
    }

    @Override
    public void process(MessageProcessor messageProcessor) {
        messageProcessor.processHeartbeatMessage(this);
    }
}
