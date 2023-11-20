package ctu.fee.dsv.sem.communication.messages;

import ctu.fee.dsv.sem.communication.facade.MessageProcessor;

public class SetSharedVariableMessage extends Message {
    private final String data;

    public SetSharedVariableMessage(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    @Override
    public void process(MessageProcessor messageProcessor) {
        messageProcessor.processSetSharedVariable(this);
    }
}
