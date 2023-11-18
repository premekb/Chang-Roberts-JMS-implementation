package ctu.fee.dsv.sem.communication;

import ctu.fee.dsv.sem.Neighbours;
import ctu.fee.dsv.sem.NodeAddress;
import ctu.fee.dsv.sem.communication.messages.Message;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Session;

public class MessageSenderImpl implements MessageSender {

    private final Session session;

    private final NodeAddress senderAddress;

    private MessageProducer producerForNext;

    private MessageProducer producerForNextNext;

    private MessageProducer producerForPrev;

    private MessageProducer producerForLeader;

    public MessageSenderImpl(Session session, NodeAddress senderAddress, Neighbours neighbours) {
        this.session = session;
        this.senderAddress = senderAddress;
        producerForNext = new MessageProducerImpl(session, senderAddress, neighbours.next);
        producerForNextNext = new MessageProducerImpl(session, senderAddress, neighbours.nnext);
        producerForPrev = new MessageProducerImpl(session, senderAddress, neighbours.prev);
        producerForLeader = new MessageProducerImpl(session, senderAddress, neighbours.leader);}

    @Override
    public void sendMessageToNext(Message message) {
        producerForNext.sendMessage(message);
    }

    @Override
    public void sendMessageToNextNext(Message message) {
        producerForNextNext.sendMessage(message);
    }

    @Override
    public void sendMessageToPrev(Message message) {
        producerForPrev.sendMessage(message);
    }

    @Override
    public void sendMessageToLeader(Message message) {
        producerForLeader.sendMessage(message);
    }

    @Override
    public void sendMessageToAddress(Message message, NodeAddress destinationAddress) {
        new MessageProducerImpl(session, senderAddress, destinationAddress).sendMessage(message);
    }
}
