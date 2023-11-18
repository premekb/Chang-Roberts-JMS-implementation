package ctu.fee.dsv.sem;

import java.io.Serializable;

public class NodeAddress implements Serializable {

    private final Integer nodeId;

    private final String nodeName;


    public NodeAddress(String nodeName, Integer nodeId) {
        this.nodeId = nodeId;
        this.nodeName = nodeName;
    }

    public Integer getNodeId() {
        return nodeId;
    }

    public String getNodeName() {
        return nodeName;
    }

    @Override
    public String toString() {
        return "Node: " + nodeName + " " + nodeId;
    }
}
