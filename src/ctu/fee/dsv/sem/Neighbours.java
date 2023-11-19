package ctu.fee.dsv.sem;

import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;

public class Neighbours implements Serializable {
    public NodeAddress leader;

    public NodeAddress next;

    public NodeAddress nnext;

    public NodeAddress prev;

    public Neighbours(NodeAddress me) {
        this.leader = me;
        this.next = me;
        this.nnext = me;
        this.prev = me;
    }

    public Neighbours(NodeAddress leader, NodeAddress next, NodeAddress nnext, NodeAddress prev) {
        this.leader = leader;
        this.next = next;
        this.nnext = nnext;
        this.prev = prev;
    }
}
