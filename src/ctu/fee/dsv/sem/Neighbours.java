package ctu.fee.dsv.sem;

public class Neighbours {
    public NodeAddress leader;

    public NodeAddress next;

    public NodeAddress nnext;

    public NodeAddress prev;

    public Neighbours(NodeAddress leader, NodeAddress next, NodeAddress nnext, NodeAddress prev) {
        this.leader = leader;
        this.next = next;
        this.nnext = nnext;
        this.prev = prev;
    }
}
