package ctu.fee.dsv.sem.communication.messages;

import ctu.fee.dsv.sem.NodeAddress;

public class LoginMessage extends Message {
    public final NodeAddress senderNodeAddress;

    public LoginMessage(NodeAddress senderNodeAddress) {
        super(LoginMessage.class.getName());
        this.senderNodeAddress = senderNodeAddress;
    }
}
