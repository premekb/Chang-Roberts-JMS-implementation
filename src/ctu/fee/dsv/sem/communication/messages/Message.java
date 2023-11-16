package ctu.fee.dsv.sem.communication.messages;

import java.io.Serializable;

public abstract class Message implements Serializable {
    public final String messageClass;

    public Message(String messageClass) {
        this.messageClass = messageClass;
    }

    @Override
    public String toString() {
        return "Message{" +
                "messageClass='" + messageClass + '\'' +
                '}';
    }
}
