package ctu.fee.dsv.sem;

import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;

public class Neighbours implements Serializable {
    public final NodeAddress leader;

    public final NodeAddress next;

    public final NodeAddress nnext;

    public final NodeAddress prev;

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

    @Override
    public String toString() {
        return  "leader=" + leader +
                ", next=" + next +
                ", nnext=" + nnext +
                ", prev=" + prev;
    }
}
