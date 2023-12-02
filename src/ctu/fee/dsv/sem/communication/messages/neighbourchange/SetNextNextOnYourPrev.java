package ctu.fee.dsv.sem.communication.messages.neighbourchange;

import ctu.fee.dsv.sem.clock.LogicalLocalClock;
import ctu.fee.dsv.sem.communication.facade.MessageProcessor;
import ctu.fee.dsv.sem.communication.messages.Message;

// Nastav jako nextnexta na svojem prevovi
public class SetNextNextOnYourPrev extends Message {

    public SetNextNextOnYourPrev(LogicalLocalClock logicalLocalClock) {
        super(logicalLocalClock);
    }

    @Override
    public void process(MessageProcessor messageProcessor) {
        messageProcessor.processSetNextNextOnYourPrev(this);
    }
}
