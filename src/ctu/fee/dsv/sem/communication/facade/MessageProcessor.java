package ctu.fee.dsv.sem.communication.facade;

import ctu.fee.dsv.sem.communication.messages.*;

public interface MessageProcessor {
    void processLoginMessage(LoginMessage loginMessage);

    void processLoginMessageResponse(LoginMessageResponse loginMessageResponse);

    void processGetSharedVariable(GetSharedVariableMessage getSharedVariableMessage);

    void processGetSharedVariableResponse(GetSharedVariableMessageResponse getSharedVariableMessageResponse);

    void processSetSharedVariable(SetSharedVariableMessage setSharedVariableMessage);
}
