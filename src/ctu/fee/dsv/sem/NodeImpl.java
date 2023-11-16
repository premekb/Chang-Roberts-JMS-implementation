package ctu.fee.dsv.sem;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class NodeImpl implements Node {
    private SharedVariable sharedVariable;

    private Neighbours neighbours;



    @Override
    public SharedVariable getSharedVariable() {
        return sharedVariable;
    }

    @Override
    public void setSharedVariable(SharedVariable sharedVariable) {
        this.sharedVariable = sharedVariable;
    }

    @Override
    public void terminateWithLogout() {
        throw new NotImplementedException();
    }

    @Override
    public void terminateWithoutLogout() {
        throw new NotImplementedException();
    }

    @Override
    public void login() {
        throw new NotImplementedException();
    }
}
