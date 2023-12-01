package ctu.fee.dsv.sem.communication.messages.neighbourchange;

import ctu.fee.dsv.sem.NodeAddress;
import ctu.fee.dsv.sem.clock.LogicalLocalClock;
import ctu.fee.dsv.sem.communication.facade.MessageProcessor;
import ctu.fee.dsv.sem.communication.messages.Message;

public class NewNextMessage extends Message {
    public final NodeAddress newNext;

    public NewNextMessage(LogicalLocalClock logicalLocalClock, NodeAddress newNext) {
        super(logicalLocalClock);
        this.newNext = newNext;
    }

    @Override
    public void process(MessageProcessor messageProcessor) {
        messageProcessor.processNewNext(this);
    }
}
