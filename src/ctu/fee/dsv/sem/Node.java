package ctu.fee.dsv.sem;

public interface Node {
    SharedVariable getSharedVariable();

    void setSharedVariable(SharedVariable data);

    void terminateWithLogout();

    void terminateWithoutLogout();

    void login();
}
