package ctu.fee.dsv.sem.communication.facade;

import ctu.fee.dsv.sem.Neighbours;
import ctu.fee.dsv.sem.Node;
import ctu.fee.dsv.sem.NodeAddress;
import ctu.fee.dsv.sem.communication.messages.*;
import ctu.fee.dsv.sem.communication.messages.neighbourchange.NewNextMessage;
import ctu.fee.dsv.sem.communication.messages.neighbourchange.NewNextNextMessage;
import ctu.fee.dsv.sem.communication.messages.neighbourchange.NewPrevMessage;
import ctu.fee.dsv.sem.sharedvariable.RemoteStringSharedVariable;
import ctu.fee.dsv.sem.sharedvariable.StringSharedVariable;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.xml.ws.soap.Addressing;
import java.util.logging.Logger;

public class MessageProcessorImpl implements MessageProcessor {

    private final Node node;
    private final MessageSender messageSender;

    private static final Logger log = Logger.getLogger(MessageProcessorImpl.class.toString());

    public MessageProcessorImpl(Node node, MessageSender messageSender) {
        this.node = node;
        this.messageSender = messageSender;
    }


    // TODO VYMEN TO MOZNA ZA IFY PRO KONKRETNI SCENARE
    // TODO TZN. KDYZ JSOU 2 NODES tak nastav adresy, kdyz 3 tak nejak, jestli 4 tak potom normal...
    @Override
    public void processLoginMessage(LoginMessage loginMessage) {
        Neighbours myNewNeighbours;
        NodeAddress currentInitialNext = node.getNeighbours().next;
        NodeAddress currentInitialPrev = node.getNeighbours().prev;

        Neighbours newNeighbours = new Neighbours( // neighbours toho joinujiciho nodu
                node.getNeighbours().leader,
                node.getNeighbours().next,
                node.getNeighbours().nnext,
                node.getNodeAddress()
        );
        LoginMessageResponse loginMessageResponse =  new LoginMessageResponse(newNeighbours);
        messageSender.sendMessageToAddress(loginMessageResponse, loginMessage.senderNodeAddress);


        if (node.getNeighbours().next.equals(node.getNodeAddress()))
        {
            myNewNeighbours = new Neighbours(
                    node.getNeighbours().leader,
                    node.getNeighbours().next,
                    node.getNeighbours().nnext,
                    loginMessage.senderNodeAddress
            );
            node.setNeighbours(myNewNeighbours);
        }

        // nextovi poslu jako prev toho kdo se joinul
        else
        {
            NewPrevMessage newPrevMessage = new NewPrevMessage(loginMessage.senderNodeAddress);
            messageSender.sendMessageToNext(newPrevMessage);
        }


        // prevovi poslu jako nnext toho kde se joinul
        if (currentInitialPrev.equals(node.getNodeAddress()))
        {
            myNewNeighbours = new Neighbours(
                    node.getNeighbours().leader,
                    node.getNeighbours().next,
                    loginMessage.senderNodeAddress,
                    node.getNeighbours().prev
            );
            node.setNeighbours(myNewNeighbours);
        }

        else
        {
            NewNextNextMessage newNextNextMessage = new NewNextNextMessage(loginMessage.senderNodeAddress);
            messageSender.sendMessageToPrev(newNextNextMessage);
        }
        messageSender.sendMessageToAddress(new NewNextNextMessage(node.getNeighbours().nnext), loginMessage.senderNodeAddress);

        myNewNeighbours = new Neighbours(
                node.getNeighbours().leader,
                loginMessage.senderNodeAddress,
                currentInitialNext,
                node.getNeighbours().prev
                );
        node.setNeighbours(myNewNeighbours);

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
        StringSharedVariable sharedVariable = node.getSharedVariable();
        GetSharedVariableMessageResponse message = new GetSharedVariableMessageResponse(sharedVariable.getData());

        messageSender.sendMessageToAddress(message, getSharedVariableMessage.senderNodeAddress);
        log.info("Send shared variable data to node: " + getSharedVariableMessage.senderNodeAddress + "\n" +
                "Data: " + sharedVariable.getData());
    }

    @Override
    public void processGetSharedVariableResponse(GetSharedVariableMessageResponse getSharedVariableMessageResponse) {
        log.info("RECEIVED SHARED VARIABLE FROM LEADER: " + getSharedVariableMessageResponse.data);
        ((RemoteStringSharedVariable) node.getSharedVariable()).setCachedResultFromResponse(getSharedVariableMessageResponse.data);
    }

    @Override
    public void processSetSharedVariable(SetSharedVariableMessage setSharedVariableMessage) {
        throw new NotImplementedException();
    }

    @Override
    public void processNewPrev(NewPrevMessage newPrevMessage) {
        Neighbours currentNeighbours = node.getNeighbours();
        node.setNeighbours(
                new Neighbours(
                        currentNeighbours.leader,
                        currentNeighbours.next,
                        currentNeighbours.nnext,
                        newPrevMessage.newPrev
                ));
    }

    @Override
    public void processNewNext(NewNextMessage newNextMessage) {
        Neighbours currentNeighbours = node.getNeighbours();
        node.setNeighbours(
                new Neighbours(
                        currentNeighbours.leader,
                        newNextMessage.newNext,
                        currentNeighbours.nnext,
                        currentNeighbours.prev
                ));
    }

    @Override
    public void processNewNextNext(NewNextNextMessage newNextNextMessage) {
        Neighbours currentNeighbours = node.getNeighbours();
        node.setNeighbours(
                new Neighbours(
                        currentNeighbours.leader,
                        currentNeighbours.next,
                        newNextNextMessage.newNextNext,
                        currentNeighbours.prev
                ));
    }
}
