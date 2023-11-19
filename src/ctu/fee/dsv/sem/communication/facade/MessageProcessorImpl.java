package ctu.fee.dsv.sem.communication.facade;

import ctu.fee.dsv.sem.Neighbours;
import ctu.fee.dsv.sem.Node;
import ctu.fee.dsv.sem.NodeImpl;
import ctu.fee.dsv.sem.communication.messages.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.logging.Logger;

public class MessageProcessorImpl implements MessageProcessor {

    private final Node node;
    private final MessageSender messageSender;

    private static final Logger log = Logger.getLogger(MessageProcessorImpl.class.toString());

    public MessageProcessorImpl(Node node, MessageSender messageSender) {
        this.node = node;
        this.messageSender = messageSender;
    }

    @Override
    public void processLoginMessage(LoginMessage loginMessage) {
        // TODO Change neighbours of other nodes
        Neighbours currentNodeNeighbours = node.getNeighbours();

        Neighbours newNeighbours = new Neighbours(
                currentNodeNeighbours.leader,
                currentNodeNeighbours.next,
                currentNodeNeighbours.nnext,
                currentNodeNeighbours.prev
        ); // TODO jinak
        LoginMessageResponse loginMessageResponse =  new LoginMessageResponse(newNeighbours);

        messageSender.sendMessageToAddress(loginMessageResponse, loginMessage.senderNodeAddress);
        log.info("Responded to login message from: " + loginMessage.senderNodeAddress + "\n" +
                "With neighbours: " + newNeighbours);
    }

    @Override
    public void processLoginMessageResponse(LoginMessageResponse loginMessageResponse) {
        node.setNeighbours(loginMessageResponse.neighbours);
        log.info("New neighbours set from a login response: " + loginMessageResponse);
    }

    @Override
    public void processGetSharedVariable(GetSharedVariableMessage getSharedVariableMessage) {
        throw new NotImplementedException();
    }

    @Override
    public void processGetSharedVariableResponse(GetSharedVariableMessageResponse getSharedVariableMessageResponse) {
        throw new NotImplementedException();
    }

    @Override
    public void processSetSharedVariable(SetSharedVariableMessage setSharedVariableMessage) {
        throw new NotImplementedException();
    }
}
