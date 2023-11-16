package ctu.fee.dsv.sem;

import java.io.Serializable;

public class StringSharedVariable implements Serializable, SharedVariable {
    private final SharedVariableData<String> data;

    public StringSharedVariable(SharedVariableData<String> data) {
        this.data = data;
    }

    @Override
    public SharedVariableData<String> getData() {
        return null;
    }
}
