package ctu.fee.dsv.sem.sharedvariable;

import java.io.Serializable;

public class LocalStringSharedVariable implements Serializable, StringSharedVariable {
    private String data;

    public LocalStringSharedVariable() {
        this.data = "";
    }

    @Override
    public synchronized String getData() {
        return data;
    }

    @Override
    public synchronized void setData(String data) {
        this.data = data;
    }
}
