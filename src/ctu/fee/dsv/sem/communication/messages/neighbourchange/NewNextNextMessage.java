package ctu.fee.dsv.sem.communication.messages.neighbourchange;

import ctu.fee.dsv.sem.NodeAddress;
import ctu.fee.dsv.sem.clock.LogicalLocalClock;
import ctu.fee.dsv.sem.communication.facade.MessageProcessor;
import ctu.fee.dsv.sem.communication.messages.Message;

public class NewNextNextMessage extends Message {
    public final NodeAddress newNextNext;

    public NewNextNextMessage(LogicalLocalClock logicalLocalClock, NodeAddress newNextNext) {
        super(logicalLocalClock);
        this.newNextNext = newNextNext;
    }

    @Override
    public void process(MessageProcessor messageProcessor) {
        messageProcessor.processNewNextNext(this);
    }
}
