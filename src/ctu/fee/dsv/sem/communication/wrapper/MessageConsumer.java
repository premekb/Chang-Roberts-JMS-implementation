package ctu.fee.dsv.sem.communication.wrapper;

import ctu.fee.dsv.sem.communication.messages.Message;

import javax.jms.MessageListener;

public interface MessageConsumer {

    /**
     * Retrieves message synchronously. Timeout = 100 ms.
     */
    Message tryGetMessage();

    /**
     * Retrieves message synchronously.
     */
    Message tryGetMessage(long millisecondsToWait);

    void setMessageListener(MessageListener messageListener);

    void close();
}
