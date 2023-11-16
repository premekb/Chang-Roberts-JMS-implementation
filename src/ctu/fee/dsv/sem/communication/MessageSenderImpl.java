package ctu.fee.dsv.sem.communication;

import ctu.fee.dsv.sem.communication.messages.Message;

import javax.jms.Connection;

public class MessageSenderImpl implements MessageSender {
    private final Connection connection;

    private MessageProducer producerForNext;

    private MessageProducer producerForNextNext;

    private MessageProducer producerForPrev;

    private MessageProducer producerForLeader;

    public MessageSenderImpl(Connection connection) {
        this.connection = connection;
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

    public MessageProducer getProducerForNext() {
        return producerForNext;
    }

    public MessageProducer getProducerForNextNext() {
        return producerForNextNext;
    }

    public MessageProducer getProducerForPrev() {
        return producerForPrev;
    }

    public MessageProducer getProducerForLeader() {
        return producerForLeader;
    }
}
