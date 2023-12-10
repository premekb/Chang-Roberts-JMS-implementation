package ctu.fee.dsv.sem;

import ctu.fee.dsv.sem.communication.messages.Message;
import ctu.fee.dsv.sem.sharedvariable.StringSharedVariable;

public interface Node extends Runnable {
    StringSharedVariable getSharedVariable();

    void setSharedVariable(String data);

    void startLogout();

    void finishLogout();

    void terminateWithoutLogout();

    NodeAddress getNodeAddress();

    Neighbours getNeighbours();

    void processMessage(Message message);

    void setNeighbours(Neighbours neighbours);

    void setVoting(boolean isVoting);

    boolean isVoting();

    void setLeader(NodeAddress address);

    void repairNextNodeMissing();

    void startElection();

    void startDelayedElection();

    SystemTopology getSystemTopology();

    void setCacheVariable(String variable);

    boolean isLoggingOut();

    boolean shouldLogHeartbeat();
}
