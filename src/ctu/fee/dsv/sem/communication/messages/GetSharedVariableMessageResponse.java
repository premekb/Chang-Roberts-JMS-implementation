package ctu.fee.dsv.sem.communication.messages;

import ctu.fee.dsv.sem.communication.facade.MessageProcessor;

public class GetSharedVariableMessageResponse extends Message {
    public final String data;

    public GetSharedVariableMessageResponse(String data) {
        this.data = data;
    }

    @Override
    public void process(MessageProcessor messageProcessor) {
        messageProcessor.processGetSharedVariableResponse(this);
    }
}
