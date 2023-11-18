package ctu.fee.dsv.sem;

import ctu.fee.dsv.sem.sharedvariable.SharedVariable;

public interface Node {
    SharedVariable getSharedVariable();

    void setSharedVariable(SharedVariable data);

    void terminateWithLogout();

    void terminateWithoutLogout();
}
