package ctu.fee.dsv.sem.communication;

import ctu.fee.dsv.sem.communication.messages.Message;

public interface MessageConsumer {
    public Message tryGetMessage();

    public Message tryGetMessage(long millisecondsToWait);
}
