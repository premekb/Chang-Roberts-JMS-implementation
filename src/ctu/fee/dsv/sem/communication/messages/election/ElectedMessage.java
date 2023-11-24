package ctu.fee.dsv.sem.communication.messages.election;

import ctu.fee.dsv.sem.NodeAddress;
import ctu.fee.dsv.sem.communication.facade.MessageProcessor;
import ctu.fee.dsv.sem.communication.messages.Message;

public class ElectedMessage extends Message {
    public final NodeAddress leaderAddress;

    public ElectedMessage(NodeAddress address) {
        this.leaderAddress = address;
    }

    @Override
    public void process(MessageProcessor messageProcessor) {
        messageProcessor.processElectedMessage(this);
    }
}
