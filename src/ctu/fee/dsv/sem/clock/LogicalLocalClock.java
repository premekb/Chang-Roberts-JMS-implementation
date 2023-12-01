package ctu.fee.dsv.sem.clock;

import ctu.fee.dsv.sem.communication.messages.Message;

public interface LogicalLocalClock {
    Integer getTimestampAndIncrement();

    Integer getTimestampForReceivingMessage(Message message);
}
