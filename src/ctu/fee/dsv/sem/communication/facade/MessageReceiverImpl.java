package ctu.fee.dsv.sem.communication.facade;

import ctu.fee.dsv.sem.Neighbours;
import ctu.fee.dsv.sem.Node;
import ctu.fee.dsv.sem.NodeAddress;
import ctu.fee.dsv.sem.communication.messages.Message;
import ctu.fee.dsv.sem.communication.wrapper.MessageConsumer;
import ctu.fee.dsv.sem.communication.wrapper.MessageConsumerImpl;
import ctu.fee.dsv.sem.communication.wrapper.MessageListenerImpl;
import ctu.fee.dsv.sem.communication.wrapper.MessageProducerImpl;

import javax.jms.MessageListener;
import javax.jms.Session;

public class MessageReceiverImpl implements MessageReceiver {

    private final Node node;

    private final Session session;

    private MessageConsumer consumer;


    public MessageReceiverImpl(Node node, Session session) {
        this.node = node;
        this.session = session;
    }

    // TODO consumer jenom jeden, protoze mam ten jenom receiver queue. Purge vsechny messages z queue pri inicializaci.
    @Override
    public void startListeningToMessages() {
        MessageListener messageListener = new MessageListenerImpl(node);
        this.consumer = new MessageConsumerImpl(session, node.getNodeAddress(), false);
        this.consumer.setMessageListener(messageListener);
    }
}
