package ctu.fee.dsv.sem.communication.messages;

import ctu.fee.dsv.sem.Neighbours;
import ctu.fee.dsv.sem.clock.LogicalLocalClock;
import ctu.fee.dsv.sem.communication.facade.MessageProcessor;

public class LoginMessageResponse extends Message {
    public final Neighbours neighbours;
    public LoginMessageResponse(LogicalLocalClock logicalLocalClock, Neighbours neighbours) {
        super(logicalLocalClock);
        this.neighbours = neighbours;
    }

    @Override
    public void process(MessageProcessor messageProcessor) {
        messageProcessor.processLoginMessageResponse(this);
    }

    @Override
    public String toString() {
        return super.toString() +  "Neighbours: " + neighbours;
    }
}
