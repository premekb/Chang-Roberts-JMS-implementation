package ctu.fee.dsv.sem.communication.messages;

import ctu.fee.dsv.sem.communication.facade.MessageProcessor;

import java.io.Serializable;

public abstract class Message implements Serializable {
    public final String messageClass;

    public Message(String messageClass) {
        this.messageClass = messageClass;
    }

    public abstract void process(MessageProcessor messageProcessor);

    @Override
    public String toString() {
        return "Message{" +
                "messageClass='" + messageClass + '\'' +
                '}';
    }
}
