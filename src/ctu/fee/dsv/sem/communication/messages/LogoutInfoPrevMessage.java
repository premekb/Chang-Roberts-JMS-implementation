package ctu.fee.dsv.sem.communication.messages;

import ctu.fee.dsv.sem.Neighbours;
import ctu.fee.dsv.sem.NodeAddress;
import ctu.fee.dsv.sem.communication.facade.MessageProcessor;

public class LogoutInfoPrevMessage extends Message {

    public final NodeAddress sender;

    public final Neighbours sendersNeighbours;

    public LogoutInfoPrevMessage(NodeAddress sender, Neighbours sendersNeighbours) {
        this.sender = sender;
        this.sendersNeighbours = sendersNeighbours;
    }

    @Override
    public void process(MessageProcessor messageProcessor) {
        messageProcessor.processLogoutInfoPrevMessage(this);
    }

    @Override
    public String toString() {
        return "Informing prev node about logout.";
    }
}
