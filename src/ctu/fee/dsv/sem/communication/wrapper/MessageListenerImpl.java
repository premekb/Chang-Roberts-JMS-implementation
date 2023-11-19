package ctu.fee.dsv.sem.communication.wrapper;

import ctu.fee.dsv.sem.Node;

import javax.jms.Message;
import javax.jms.MessageListener;

public class MessageListenerImpl implements MessageListener {
    private final Node node;
    public MessageListenerImpl(Node node) {
        this.node = node;
    }

    @Override
    public void onMessage(Message message) {
        System.out.println("Cus picus message received.");
    }
}
