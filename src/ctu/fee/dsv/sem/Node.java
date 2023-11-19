package ctu.fee.dsv.sem;

import ctu.fee.dsv.sem.communication.messages.Message;
import ctu.fee.dsv.sem.sharedvariable.SharedVariable;

public interface Node extends Runnable {
    SharedVariable getSharedVariable();

    void setSharedVariable(SharedVariable data);

    void terminateWithLogout();

    void terminateWithoutLogout();

    NodeAddress getNodeAddress();

    Neighbours getNeighbours();

    void processMessage(Message message);

    void setNeighbours(Neighbours neighbours);
}
