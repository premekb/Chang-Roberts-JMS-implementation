package ctu.fee.dsv.sem.communication.messages;

import ctu.fee.dsv.sem.clock.LogicalLocalClock;
import ctu.fee.dsv.sem.communication.facade.MessageProcessor;

import java.io.Serializable;

public abstract class Message implements Serializable {

    public final Integer logicalTimestamp;

    public Message(LogicalLocalClock logicalLocalClock) {
        this.logicalTimestamp = logicalLocalClock.getTimestampAndIncrement();
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
}
