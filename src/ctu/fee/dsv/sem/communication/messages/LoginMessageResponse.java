package ctu.fee.dsv.sem.communication.messages;

import ctu.fee.dsv.sem.Neighbours;

public class LoginMessageResponse extends Message {
    public final Neighbours neighbours;
    public LoginMessageResponse(Neighbours neighbours) {
        super(LoginMessageResponse.class.getName());
        this.neighbours = neighbours;
    }
}
