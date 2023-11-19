package ctu.fee.dsv.sem.sharedvariable;

import ctu.fee.dsv.sem.communication.facade.MessageSender;
import ctu.fee.dsv.sem.communication.messages.SetSharedVariableMessage;

import java.io.Serializable;

public class RemoteStringSharedVariable implements Serializable, SharedVariable<String> {
    private final MessageSender messageSender;

    public RemoteStringSharedVariable(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    @Override
    public String getData() {
        return null;
    }

    @Override
    public void setData(String data) {
        messageSender.sendMessageToLeader(new SetSharedVariableMessage<>(data));
    }
}
