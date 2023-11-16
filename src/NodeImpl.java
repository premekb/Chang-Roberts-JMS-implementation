public class NodeImpl implements Node {
    private SharedVariable sharedVariable;


    @Override
    public SharedVariable getSharedVariable() {
        return sharedVariable;
    }

    @Override
    public void setSharedVariable(SharedVariable sharedVariable) {
        this.sharedVariable = sharedVariable;
    }
}
