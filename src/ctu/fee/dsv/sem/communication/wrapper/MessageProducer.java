package ctu.fee.dsv.sem.communication.wrapper;

import ctu.fee.dsv.sem.NodeAddress;
import ctu.fee.dsv.sem.communication.messages.Message;

public interface MessageProducer {
    void sendMessage(Message message);
}
