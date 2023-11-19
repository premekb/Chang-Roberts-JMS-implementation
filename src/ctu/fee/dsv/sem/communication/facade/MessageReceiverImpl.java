package ctu.fee.dsv.sem.communication.facade;

import ctu.fee.dsv.sem.Neighbours;
import ctu.fee.dsv.sem.Node;
import ctu.fee.dsv.sem.communication.wrapper.MessageConsumer;
import ctu.fee.dsv.sem.communication.wrapper.MessageConsumerImpl;
import ctu.fee.dsv.sem.communication.wrapper.MessageListenerImpl;

import javax.jms.MessageListener;
import javax.jms.Session;

public class MessageReceiverImpl implements MessageReceiver {

    private final Node node;

    private final Session session;

    private MessageConsumer nextConsumer;

    private MessageConsumer nnextConsumer;

    private MessageConsumer prevConsumer;

    private MessageConsumer leaderconsumer;


    public MessageReceiverImpl(Node node, Session session) {
        this.node = node;
        this.session = session;
    }

    @Override
    public void startListeningToMessages() {
        Neighbours neighbours = node.getNeighbours();
        MessageListener messageListener = new MessageListenerImpl(node);
        this.nextConsumer = new MessageConsumerImpl(session, node.getNodeAddress(), neighbours.next);
        this.nextConsumer.setMessageListener(messageListener);

        this.nnextConsumer = new MessageConsumerImpl(session, node.getNodeAddress(), neighbours.nnext);
        this.nnextConsumer.setMessageListener(messageListener);

        this.prevConsumer = new MessageConsumerImpl(session, node.getNodeAddress(), neighbours.prev);
        this.prevConsumer.setMessageListener(messageListener);

        this.leaderconsumer = new MessageConsumerImpl(session, node.getNodeAddress(), neighbours.leader);
        this.leaderconsumer.setMessageListener(messageListener);
    }
}
