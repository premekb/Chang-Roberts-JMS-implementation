package ctu.fee.dsv.sem.sharedvariable;

import java.io.Serializable;

public interface SharedVariable<T extends Serializable> {
    T getData();

    void setData(T data);
}
