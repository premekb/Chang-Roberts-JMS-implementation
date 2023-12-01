package ctu.fee.dsv.sem.communication.messages;

import ctu.fee.dsv.sem.clock.LogicalLocalClock;
import ctu.fee.dsv.sem.communication.facade.MessageProcessor;

public class GetSharedVariableMessageResponse extends Message {
    public final String data;

    public GetSharedVariableMessageResponse(LogicalLocalClock logicalLocalClock, String data) {
        super(logicalLocalClock);
        this.data = data;
    }

    @Override
    public void process(MessageProcessor messageProcessor) {
        messageProcessor.processGetSharedVariableResponse(this);
    }

    @Override
    public String toString() {
        return super.toString() + '"' + data + '"';
    }
}
