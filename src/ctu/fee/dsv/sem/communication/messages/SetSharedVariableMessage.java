package ctu.fee.dsv.sem.communication.messages;

import ctu.fee.dsv.sem.communication.facade.MessageProcessor;

public class SetSharedVariableMessage<T> extends Message {
    private final T data;

    public SetSharedVariableMessage(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    @Override
    public void process(MessageProcessor messageProcessor) {
        messageProcessor.processSetSharedVariable(this);
    }
}
