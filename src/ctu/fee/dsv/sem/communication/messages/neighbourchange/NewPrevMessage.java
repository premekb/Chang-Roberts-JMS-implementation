package ctu.fee.dsv.sem.communication.messages.neighbourchange;

import ctu.fee.dsv.sem.NodeAddress;
import ctu.fee.dsv.sem.communication.facade.MessageProcessor;
import ctu.fee.dsv.sem.communication.messages.Message;

public class NewPrevMessage extends Message {
    public final NodeAddress newPrev;

    public NewPrevMessage(NodeAddress newPrev) {
        this.newPrev = newPrev;
    }

    @Override
    public void process(MessageProcessor messageProcessor) {
        messageProcessor.processNewPrev(this);
    }
}
