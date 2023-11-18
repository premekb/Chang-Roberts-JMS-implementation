package ctu.fee.dsv.sem.communication.messages;

public class SetSharedVariableMessage<T> extends Message {
    private final T data;

    public SetSharedVariableMessage(T data) {
        super(SetSharedVariableMessage.class.getName());
        this.data = data;
    }

    public T getData() {
        return data;
    }
}
