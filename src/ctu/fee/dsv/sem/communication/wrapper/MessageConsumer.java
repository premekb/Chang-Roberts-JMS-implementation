package ctu.fee.dsv.sem.communication.wrapper;

import ctu.fee.dsv.sem.communication.messages.Message;

import javax.jms.MessageListener;

public interface MessageConsumer {
    Message tryGetMessage();

    Message tryGetMessage(long millisecondsToWait);

    void setMessageListener(MessageListener messageListener);
}
