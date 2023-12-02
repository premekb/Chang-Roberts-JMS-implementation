package ctu.fee.dsv.sem.communication.messages.neighbourchange;

import ctu.fee.dsv.sem.Neighbours;
import ctu.fee.dsv.sem.clock.LogicalLocalClock;
import ctu.fee.dsv.sem.communication.facade.MessageProcessor;
import ctu.fee.dsv.sem.communication.messages.Message;

public class NewNeighboursMessage extends Message {
    public final Neighbours neighbours;

    public NewNeighboursMessage(LogicalLocalClock logicalLocalClock, Neighbours neighbours) {
        super(logicalLocalClock);
        this.neighbours = neighbours;
    }

    @Override
    public void process(MessageProcessor messageProcessor) {
        messageProcessor.processNewNeighboursMessage(this);
    }
}
