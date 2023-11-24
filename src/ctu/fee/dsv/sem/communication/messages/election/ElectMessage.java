package ctu.fee.dsv.sem.communication.messages.election;

import ctu.fee.dsv.sem.NodeAddress;
import ctu.fee.dsv.sem.communication.facade.MessageProcessor;
import ctu.fee.dsv.sem.communication.messages.Message;

public class ElectMessage extends Message {
    public final NodeAddress address;

    public ElectMessage(NodeAddress address) {
        this.address = address;
    }

    @Override
    public void process(MessageProcessor messageProcessor) {
        messageProcessor.processElectMessage(this);
    }
}
