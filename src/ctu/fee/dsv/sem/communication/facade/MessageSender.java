package ctu.fee.dsv.sem.communication.facade;

import ctu.fee.dsv.sem.NodeAddress;
import ctu.fee.dsv.sem.communication.messages.Message;

public interface MessageSender {
    void sendMessageToNext(Message message);

    void sendMessageToNextNext(Message message);

    void sendMessageToPrev(Message message);

    void sendMessageToLeader(Message message);

    void sendMessageToAddress(Message message, NodeAddress destinationAddress);

    void setNewReceivers(NodeAddress next, NodeAddress nnext, NodeAddress prev, NodeAddress leader);

    NodeAddress getSenderAddress();
}
