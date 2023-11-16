import java.io.Serializable;

public class SharedVariableData<T extends Serializable> implements Serializable {

    private final T data;
    public SharedVariableData(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }
}
