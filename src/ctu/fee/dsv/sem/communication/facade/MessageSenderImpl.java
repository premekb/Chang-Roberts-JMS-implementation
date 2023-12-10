package ctu.fee.dsv.sem.communication.facade;

import ctu.fee.dsv.sem.Neighbours;
import ctu.fee.dsv.sem.NodeAddress;
import ctu.fee.dsv.sem.communication.wrapper.MessageProducer;
import ctu.fee.dsv.sem.communication.wrapper.MessageProducerImpl;
import ctu.fee.dsv.sem.communication.messages.Message;

import javax.jms.Session;

public class MessageSenderImpl implements MessageSender {

    private final Session session;

    private final NodeAddress senderAddress;

    private MessageProducer producerForNext;

    private MessageProducer producerForNextNext;

    private MessageProducer producerForPrev;

    private MessageProducer producerForLeader;

    private final boolean logHeartbeat;

    public MessageSenderImpl(Session session, NodeAddress senderAddress, Neighbours neighbours, boolean logHeartbeat) {
        this.session = session;
        this.senderAddress = senderAddress;
        producerForNext = new MessageProducerImpl(session, senderAddress, neighbours.next, logHeartbeat);
        producerForNextNext = new MessageProducerImpl(session, senderAddress, neighbours.nnext, logHeartbeat);
        producerForPrev = new MessageProducerImpl(session, senderAddress, neighbours.prev, logHeartbeat);
        producerForLeader = new MessageProducerImpl(session, senderAddress, neighbours.leader, logHeartbeat);
        this.logHeartbeat = logHeartbeat;
    }

    @Override
    public void setNewReceivers(NodeAddress next, NodeAddress nnext, NodeAddress prev, NodeAddress leader) {
        producerForNext.close();
        producerForNextNext.close();
        producerForPrev.close();
        producerForLeader.close();

        producerForNext = new MessageProducerImpl(session, senderAddress, next, logHeartbeat);
        producerForNextNext = new MessageProducerImpl(session, senderAddress, nnext, logHeartbeat);
        producerForPrev = new MessageProducerImpl(session, senderAddress, prev, logHeartbeat);
        producerForLeader = new MessageProducerImpl(session, senderAddress, leader, logHeartbeat);
    }

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
        MessageProducerImpl producer = new MessageProducerImpl(session, senderAddress, destinationAddress, logHeartbeat);
        producer.sendMessage(message);
        producer.close();
    }

    @Override
    public NodeAddress getSenderAddress() {
        return senderAddress;
    }
}
