package ctu.fee.dsv.sem.communication.facade;

import ctu.fee.dsv.sem.communication.messages.*;
import ctu.fee.dsv.sem.communication.messages.election.ElectMessage;
import ctu.fee.dsv.sem.communication.messages.election.ElectedMessage;
import ctu.fee.dsv.sem.communication.messages.neighbourchange.NewNextMessage;
import ctu.fee.dsv.sem.communication.messages.neighbourchange.NewNextNextMessage;
import ctu.fee.dsv.sem.communication.messages.neighbourchange.NewPrevMessage;
import ctu.fee.dsv.sem.communication.messages.neighbourchange.RepairMyNextNextMessage;

public interface MessageProcessor {
    void processLoginMessage(LoginMessage loginMessage);

    void processLoginMessageResponse(LoginMessageResponse loginMessageResponse);

    void processGetSharedVariable(GetSharedVariableMessage getSharedVariableMessage);

    void processGetSharedVariableResponse(GetSharedVariableMessageResponse getSharedVariableMessageResponse);

    void processSetSharedVariable(SetSharedVariableMessage setSharedVariableMessage);

    void processNewPrev(NewPrevMessage newPrevMessage);

    void processNewNext(NewNextMessage newNextMessage);

    void processNewNextNext(NewNextNextMessage newNextNextMessage);

    void processLogoutInfoPrevMessage(LogoutInfoPrevMessage logoutInfoPrevMessage);

    void processElectMessage(ElectMessage electMessage);

    void processElectedMessage(ElectedMessage electedMessage);

    void processHeartbeatMessage(HeartbeatMessage heartbeatMessage);

    void processHeartbeatMessageResponse(HeartbeatMessageResponse heartbeatMessageResponse);

    void processRepairMyNextNextMessage(RepairMyNextNextMessage repairMyNextNextMessage);

    void processExploreTopologyMessage(ExploreTopologyMessage exploreTopologyMessage);
}
