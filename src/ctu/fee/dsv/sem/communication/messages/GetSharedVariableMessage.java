package ctu.fee.dsv.sem.communication.messages;

import ctu.fee.dsv.sem.communication.facade.MessageProcessor;

public class GetSharedVariableMessage extends Message {
    public GetSharedVariableMessage() {
        super(GetSharedVariableMessage.class.getName());
    }

    @Override
    public void process(MessageProcessor messageProcessor) {
        messageProcessor.processGetSharedVariable(this);
    }
}
