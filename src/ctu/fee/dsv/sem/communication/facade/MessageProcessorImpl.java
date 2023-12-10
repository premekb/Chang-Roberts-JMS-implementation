package ctu.fee.dsv.sem.communication.facade;

import ctu.fee.dsv.sem.Neighbours;
import ctu.fee.dsv.sem.Node;
import ctu.fee.dsv.sem.NodeAddress;
import ctu.fee.dsv.sem.clock.LogicalLocalClock;
import ctu.fee.dsv.sem.communication.messages.*;
import ctu.fee.dsv.sem.communication.messages.election.ElectMessage;
import ctu.fee.dsv.sem.communication.messages.election.ElectedMessage;
import ctu.fee.dsv.sem.communication.messages.neighbourchange.*;
import ctu.fee.dsv.sem.sharedvariable.RemoteStringSharedVariable;
import ctu.fee.dsv.sem.sharedvariable.StringSharedVariable;
import ctu.fee.dsv.sem.util.LoggingUtil;
import ctu.fee.dsv.sem.util.RandomUtil;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.logging.Logger;

public class MessageProcessorImpl implements MessageProcessor {

    private final Node node;
    private final MessageSender messageSender;

    private final HeartbeatService heartbeatService;

    private final LogicalLocalClock logicalLocalClock;

    private static final Logger log = Logger.getLogger(MessageProcessorImpl.class.toString());

    public MessageProcessorImpl(Node node,
                                MessageSender messageSender,
                                HeartbeatService heartbeatService,
                                LogicalLocalClock logicalLocalClock) {
        this.node = node;
        this.messageSender = messageSender;
        this.heartbeatService = heartbeatService;
        this.logicalLocalClock = logicalLocalClock;
    }


    // TODO VYMEN TO MOZNA ZA IFY PRO KONKRETNI SCENARE
    // TODO TZN. KDYZ JSOU 2 NODES tak nastav adresy, kdyz 3 tak nejak, jestli 4 tak potom normal...
    @Override
    public void processLoginMessage(LoginMessage loginMessage) {
        LoggingUtil.logReceivingMessage(log, loginMessage, logicalLocalClock);
        Neighbours myNewNeighbours;
        NodeAddress currentInitialNext = node.getNeighbours().next;
        NodeAddress currentInitialPrev = node.getNeighbours().prev;

        Neighbours newNeighbours = new Neighbours( // neighbours toho joinujiciho nodu
                node.getNeighbours().leader,
                node.getNeighbours().next,
                node.getNeighbours().nnext,
                node.getNodeAddress()
        );
        LoginMessageResponse loginMessageResponse =  new LoginMessageResponse(logicalLocalClock, newNeighbours);
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
            NewPrevMessage newPrevMessage = new NewPrevMessage(logicalLocalClock, loginMessage.senderNodeAddress);
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
            NewNextNextMessage newNextNextMessage = new NewNextNextMessage(logicalLocalClock, loginMessage.senderNodeAddress);
            messageSender.sendMessageToPrev(newNextNextMessage);
        }
        messageSender.sendMessageToAddress(new NewNextNextMessage(logicalLocalClock, node.getNeighbours().nnext), loginMessage.senderNodeAddress);

        myNewNeighbours = new Neighbours(
                node.getNeighbours().leader,
                loginMessage.senderNodeAddress,
                currentInitialNext,
                node.getNeighbours().prev
                );
        node.setNeighbours(myNewNeighbours);

        log.info("Responded to login message from: " + loginMessage.senderNodeAddress +
                " With neighbours: " + newNeighbours);
    }

    @Override
    public void processLoginMessageResponse(LoginMessageResponse loginMessageResponse) {
        LoggingUtil.logReceivingMessage(log, loginMessageResponse, logicalLocalClock);

        node.setNeighbours(loginMessageResponse.neighbours);
    }

    @Override
    public void processGetSharedVariable(GetSharedVariableMessage getSharedVariableMessage) {
        LoggingUtil.logReceivingMessage(log, getSharedVariableMessage, logicalLocalClock);

        StringSharedVariable sharedVariable = node.getSharedVariable();
        GetSharedVariableMessageResponse message = new GetSharedVariableMessageResponse(logicalLocalClock, sharedVariable.getData());

        messageSender.sendMessageToAddress(message, getSharedVariableMessage.senderNodeAddress);
        log.info("Send shared variable data to node: " + getSharedVariableMessage.senderNodeAddress + "\n" +
                "Data: " + sharedVariable.getData());
    }

    @Override
    public void processGetSharedVariableResponse(GetSharedVariableMessageResponse getSharedVariableMessageResponse) {
        LoggingUtil.logReceivingMessage(log, getSharedVariableMessageResponse, logicalLocalClock);

        ((RemoteStringSharedVariable) node.getSharedVariable()).setCachedResultFromResponse(getSharedVariableMessageResponse.data);
    }

    @Override
    public void processSetSharedVariable(SetSharedVariableMessage setSharedVariableMessage) {
        LoggingUtil.logReceivingMessage(log, setSharedVariableMessage, logicalLocalClock);

        node.setSharedVariable(setSharedVariableMessage.getData());
    }

    @Override
    public void processNewPrev(NewPrevMessage newPrevMessage) {
        LoggingUtil.logReceivingMessage(log, newPrevMessage, logicalLocalClock);

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
        LoggingUtil.logReceivingMessage(log, newNextMessage, logicalLocalClock);

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
        LoggingUtil.logReceivingMessage(log, newNextNextMessage, logicalLocalClock);

        Neighbours currentNeighbours = node.getNeighbours();
        node.setNeighbours(
                new Neighbours(
                        currentNeighbours.leader,
                        currentNeighbours.next,
                        newNextNextMessage.newNextNext,
                        currentNeighbours.prev
                ));
    }

    @Override
    public void processLogoutInfoPrevMessage(LogoutInfoPrevMessage logoutInfoPrevMessage) {
        LoggingUtil.logReceivingMessage(log, logoutInfoPrevMessage, logicalLocalClock);

        throw new NotImplementedException();
        /*Neighbours newNeighbours;
        if (NeighboursEdgeCaseUtil.isTwoNodesConfig(node.getNeighbours()))
        {
            newNeighbours = new Neighbours(
                    node.getNodeAddress(),
                    node.getNodeAddress(),
                    node.getNodeAddress(),
                    node.getNodeAddress()
            );
            node.setNeighbours(newNeighbours);
        }

        if (NeighboursEdgeCaseUtil.isThreeNodesConfig(node.getNeighbours()))
        {

        }*/
    }

    @Override
    public void processElectMessage(ElectMessage electMessage) {
        boolean messageProcessed = false;
        LoggingUtil.logReceivingMessage(log, electMessage, logicalLocalClock);

        if (node.getNodeAddress().getNodeId() < electMessage.address.getNodeId() || node.isLoggingOut())
        {
            messageSender.sendMessageToNext(electMessage);
            messageProcessed = true;
        }

        if (!node.isVoting() && node.getNodeAddress().getNodeId() > electMessage.address.getNodeId())
        {
            messageSender.sendMessageToNext(new ElectMessage(logicalLocalClock, node.getNodeAddress(), electMessage.isDelayed()));
            messageProcessed = true;
        }

        if (node.getNodeAddress().getNodeId().equals(electMessage.address.getNodeId()))
        {
            ElectedMessage electedMessage = new ElectedMessage(logicalLocalClock, node.getNodeAddress(), electMessage.isDelayed());
            messageSender.sendMessageToNext(electedMessage);
            messageProcessed = true;
        }

        if (!messageProcessed)
        {
            log.severe("DISCARDING ELECT MESSAGE " + electMessage);
        }

        node.setVoting(true);
    }

    @Override
    public void processElectedMessage(ElectedMessage electedMessage) {
        LoggingUtil.logReceivingMessage(log, electedMessage, logicalLocalClock, "Setting new leader to: " + electedMessage.leaderAddress);

        // Ja jsem leader a nebyl jsem zvolen. Tak posli novemu leaderovi data.
        if (node.getNeighbours().leader.equals(node.getNodeAddress()) && !electedMessage.leaderAddress.equals(node.getNodeAddress()))
        {
            CacheVariableFromLeader message = new CacheVariableFromLeader(logicalLocalClock, node.getSharedVariable().getData());
            messageSender.sendMessageToAddress(message, electedMessage.leaderAddress);
        }

        node.setLeader(electedMessage.leaderAddress);

        if (!electedMessage.leaderAddress.equals(node.getNodeAddress()))
        {
            log.info("Forwarding elected message to the next node.");
            messageSender.sendMessageToNext(electedMessage);
        }

        node.setVoting(false);

        if (node.isLoggingOut())
        {
            node.finishLogout();
        }
    }

    @Override
    public void processHeartbeatMessage(HeartbeatMessage heartbeatMessage) {
        if (node.shouldLogHeartbeat())
        {
            LoggingUtil.logReceivingMessage(log, heartbeatMessage, logicalLocalClock, "Responding to heartbeat");
        }

        messageSender.sendMessageToAddress(new HeartbeatMessageResponse(logicalLocalClock, node.getNodeAddress(), node.shouldLogHeartbeat()), heartbeatMessage.senderNodeAddress);
    }

    @Override
    public void processHeartbeatMessageResponse(HeartbeatMessageResponse heartbeatMessageResponse) {
        if (node.shouldLogHeartbeat())
        {
            LoggingUtil.logReceivingMessage(log, heartbeatMessageResponse, logicalLocalClock);
        }

        heartbeatService.heartbeatReceived();
    }

    @Override
    public void processRepairMyNextNextMessage(RepairMyNextNextMessage repairMyNextNextMessage) {
        LoggingUtil.logReceivingMessage(log, repairMyNextNextMessage, logicalLocalClock, "REPAIR: Sending new next next to new prev.");

        messageSender.sendMessageToAddress(new NewNextNextMessage(logicalLocalClock, node.getNeighbours().next), repairMyNextNextMessage.senderNodeAddress);
    }

    @Override
    public void processExploreTopologyMessage(ExploreTopologyMessage exploreTopologyMessage)
    {
        if (exploreTopologyMessage.originalSenderNodeAddress.equals(node.getNodeAddress()))
        {
            LoggingUtil.logReceivingMessage(log, exploreTopologyMessage, logicalLocalClock, "System topology result.");
            node.getSystemTopology().setCachedResultFromResponse(exploreTopologyMessage.getTopology());
            return;
        }

        ExploreTopologyMessage updatedMessage = exploreTopologyMessage.createAppendedMessage(logicalLocalClock, node.getNodeAddress());
        LoggingUtil.logReceivingMessage(log, exploreTopologyMessage, logicalLocalClock);
        messageSender.sendMessageToNext(updatedMessage);
    }

    @Override
    public void processNewNeighboursMessage(NewNeighboursMessage newNeighboursMessage) {
        LoggingUtil.logReceivingMessage(log, newNeighboursMessage, logicalLocalClock);

        node.setNeighbours(newNeighboursMessage.neighbours);
    }

    /**
     * Used for logout.
     */
    @Override
    public void processSetNextNextOnYourPrev(SetNextNextOnYourPrev setYourNeighboursOnYourPrevMessage) {
        LoggingUtil.logReceivingMessage(log, setYourNeighboursOnYourPrevMessage, logicalLocalClock);

        messageSender.sendMessageToPrev(new NewNextNextMessage(logicalLocalClock, node.getNeighbours().nnext));
    }

    @Override
    public void processCacheVariableFromLeader(CacheVariableFromLeader cacheVariableFromLeader) {
        node.setCacheVariable(cacheVariableFromLeader.data);
    }
}
