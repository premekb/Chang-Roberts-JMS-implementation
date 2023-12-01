package ctu.fee.dsv.sem.communication.messages;

import ctu.fee.dsv.sem.NodeAddress;
import ctu.fee.dsv.sem.clock.LogicalLocalClock;
import ctu.fee.dsv.sem.communication.facade.MessageProcessor;

public class LoginMessage extends Message {
    public final NodeAddress senderNodeAddress;

    public LoginMessage(LogicalLocalClock logicalLocalClock, NodeAddress senderNodeAddress) {
        super(logicalLocalClock);
        this.senderNodeAddress = senderNodeAddress;
    }

    @Override
    public void process(MessageProcessor messageProcessor) {
        messageProcessor.processLoginMessage(this);
    }
}
