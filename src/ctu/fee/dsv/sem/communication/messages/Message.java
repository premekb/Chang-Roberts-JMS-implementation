package ctu.fee.dsv.sem.communication.messages;

import ctu.fee.dsv.sem.clock.LogicalLocalClock;
import ctu.fee.dsv.sem.communication.facade.MessageProcessor;

import java.io.Serializable;

public abstract class Message implements Serializable {

    protected Integer logicalTimestamp;

    public Message(LogicalLocalClock logicalLocalClock) {
        this.logicalTimestamp = logicalLocalClock.getTimestampAndIncrement();
    }

    protected Message(LogicalLocalClock logicalLocalClock, boolean logHeartbeat) {
        if (logHeartbeat)
        {
            this.logicalTimestamp = logicalLocalClock.getTimestampAndIncrement();
        }
    }

    public abstract void process(MessageProcessor messageProcessor);

    @Override
    public String toString() {
        return "[LT-" + logicalTimestamp + "] " + this.getClass().getSimpleName();
    }

    public boolean isDelayed()
    {
        return false;
    }

    public Integer getLogicalTimestamp() {
        return logicalTimestamp;
    }
}
