package ctu.fee.dsv.sem.communication.messages.election;

import ctu.fee.dsv.sem.NodeAddress;
import ctu.fee.dsv.sem.clock.LogicalLocalClock;
import ctu.fee.dsv.sem.communication.facade.MessageProcessor;
import ctu.fee.dsv.sem.communication.messages.Message;

public class ElectedMessage extends Message {
    public final NodeAddress leaderAddress;

    private final boolean delayed;

    public ElectedMessage(LogicalLocalClock logicalLocalClock, NodeAddress address, boolean delayed) {
        super(logicalLocalClock);
        this.leaderAddress = address;
        this.delayed = delayed;
    }

    @Override
    public void process(MessageProcessor messageProcessor) {
        messageProcessor.processElectedMessage(this);
    }

    @Override
    public boolean isDelayed() {
        return delayed;
    }
}
