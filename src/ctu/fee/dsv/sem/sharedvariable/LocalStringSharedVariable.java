package ctu.fee.dsv.sem.sharedvariable;

import java.io.Serializable;

public class LocalStringSharedVariable implements Serializable, SharedVariable<String> {
    private String data;

    public LocalStringSharedVariable() {
        this.data = "";
    }

    @Override
    public String getData() {
        return data;
    }

    @Override
    public void setData(String data) {
        this.data = data;
    }
}
