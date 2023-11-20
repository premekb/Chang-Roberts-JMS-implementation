package ctu.fee.dsv.sem;

import java.io.Serializable;
import java.util.Objects;

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
        return nodeName + " " + nodeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NodeAddress that = (NodeAddress) o;

        if (!Objects.equals(nodeId, that.nodeId)) return false;
        return Objects.equals(nodeName, that.nodeName);
    }
}
