package ctu.fee.dsv.sem.clock;

import ctu.fee.dsv.sem.communication.messages.Message;

public class LogicalLocalClockImpl implements LogicalLocalClock {

    private Integer counter = 1;

    @Override
    public Integer getTimestampAndIncrement() {
        return counter++;
    }

    @Override
    public Integer getTimestampForReceivingMessage(Message message) {
        if (message.logicalTimestamp > counter)
        {
            counter = message.logicalTimestamp;
        }

        return getTimestampAndIncrement();
    }
}
