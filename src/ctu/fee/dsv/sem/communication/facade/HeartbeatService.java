package ctu.fee.dsv.sem.communication.facade;

public interface HeartbeatService extends Runnable{
    // Periodically sends heartbeats to neighburs.
    // Listen to heartbeats. If no heartbeat is received for some time, then repair topology.
    void run();

    void heartbeatReceived();
}
