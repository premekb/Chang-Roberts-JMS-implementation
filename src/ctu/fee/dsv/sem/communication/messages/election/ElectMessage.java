package ctu.fee.dsv.sem.communication.messages.election;

import ctu.fee.dsv.sem.NodeAddress;
import ctu.fee.dsv.sem.clock.LogicalLocalClock;
import ctu.fee.dsv.sem.communication.facade.MessageProcessor;
import ctu.fee.dsv.sem.communication.messages.Message;

public class ElectMessage extends Message {
    public final NodeAddress address;

    public ElectMessage(LogicalLocalClock logicalLocalClock, NodeAddress address) {
        super(logicalLocalClock);
        this.address = address;
    }

    @Override
    public void process(MessageProcessor messageProcessor) {
        messageProcessor.processElectMessage(this);
    }
}
