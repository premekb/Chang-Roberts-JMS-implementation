package ctu.fee.dsv.sem.cmdline;

import java.util.InputMismatchException;

public enum HeartbeatLogsConfigEnum {
    WITH_HEARTBEAT_LOGS,
    NO_HEARTBEAT_LOGS;

    public static HeartbeatLogsConfigEnum fromString(String string)
    {
        if (string.toLowerCase().equals("with_heartbeat_logs"))
        {
            return WITH_HEARTBEAT_LOGS;
        }

        else if (string.toLowerCase().equals("no_heartbeat_logs"))
        {
            return NO_HEARTBEAT_LOGS;
        }

        throw new InputMismatchException("Only allowed inputs for hearbeat options are:" +
                " WITH_HEART_LOGS or NO_HEARTBEAT_LOGS");
    }

    public boolean shouldLog()
    {
        return this.equals(WITH_HEARTBEAT_LOGS);
    }
}
