package ctu.fee.dsv.sem.communication.messages.neighbourchange;

import ctu.fee.dsv.sem.NodeAddress;
import ctu.fee.dsv.sem.communication.facade.MessageProcessor;
import ctu.fee.dsv.sem.communication.messages.Message;

/**
 * Posila Node svojemu novemu nextovi kdyz zjisti, ze jeho puvodni next je missing.
 * Chce noveho nextnexta.
 */
public class RepairMyNextNextMessage extends Message {
    public final NodeAddress senderNodeAddress;

    public RepairMyNextNextMessage(NodeAddress senderNodeAddress) {
        this.senderNodeAddress = senderNodeAddress;
    }

    @Override
    public void process(MessageProcessor messageProcessor) {
        messageProcessor.processRepairMyNextNextMessage(this);
    }
}
