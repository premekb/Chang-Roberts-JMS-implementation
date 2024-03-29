package ctu.fee.dsv.sem.communication.messages;

import ctu.fee.dsv.sem.NodeAddress;
import ctu.fee.dsv.sem.clock.LogicalLocalClock;
import ctu.fee.dsv.sem.communication.facade.MessageProcessor;

public class ExploreTopologyMessage extends Message {
    public final NodeAddress originalSenderNodeAddress;

    private String topology;
    public ExploreTopologyMessage(LogicalLocalClock logicalLocalClock, NodeAddress originalSenderNodeAddress) {
        super(logicalLocalClock);
        this.originalSenderNodeAddress = originalSenderNodeAddress;
        this.topology = originalSenderNodeAddress.toString();
    }

    public ExploreTopologyMessage createAppendedMessage(LogicalLocalClock logicalLocalClock, NodeAddress currentSender)
    {
        ExploreTopologyMessage message = new ExploreTopologyMessage(logicalLocalClock, originalSenderNodeAddress);
        message.setTopology(topology + " - " + currentSender.toString());
        return message;
    }

    public String getTopology() {
        return topology;
    }

    private void setTopology(String topology) {
        this.topology = topology;
    }

    @Override
    public void process(MessageProcessor messageProcessor) {
        messageProcessor.processExploreTopologyMessage(this);
    }
}
