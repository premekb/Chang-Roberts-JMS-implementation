package ctu.fee.dsv.sem.sharedvariable;

import ctu.fee.dsv.sem.NoResponseException;
import ctu.fee.dsv.sem.NodeImpl;
import ctu.fee.dsv.sem.communication.facade.MessageSender;
import ctu.fee.dsv.sem.communication.messages.GetSharedVariableMessage;
import ctu.fee.dsv.sem.communication.messages.SetSharedVariableMessage;

import java.io.Serializable;
import java.util.logging.Logger;

public class RemoteStringSharedVariable implements Serializable, StringSharedVariable {
    private final MessageSender messageSender;

    private String cachedResponse;

    private static final Logger log = Logger.getLogger(RemoteStringSharedVariable.class.toString());
    public RemoteStringSharedVariable(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    @Override
    public synchronized String getData() {
        cachedResponse = null;
        try {
            messageSender.sendMessageToLeader(new GetSharedVariableMessage(messageSender.getSenderAddress()));
            for (int i = 0; i < 10; i++)
            {
                Thread.sleep(i * 200);
                if (cachedResponse != null)
                {
                    return cachedResponse;
                }
            }
            throw new NoResponseException("No response received");
        } catch (InterruptedException e) {
            return cachedResponse;
        }
    }

    @Override
    public void setData(String data) {
        messageSender.sendMessageToLeader(new SetSharedVariableMessage(data));
    }

    public void setCachedResultFromResponse(String data)
    {
        cachedResponse = data;
    }
}
