package ctu.fee.dsv.sem.communication.facade;

import ctu.fee.dsv.sem.Node;
import ctu.fee.dsv.sem.NodeAddress;
import ctu.fee.dsv.sem.communication.messages.Message;

public interface MessageReceiver {
    void startListeningToMessages();
}
