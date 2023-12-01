package ctu.fee.dsv.sem.util;

import ctu.fee.dsv.sem.clock.LogicalLocalClock;
import ctu.fee.dsv.sem.communication.messages.Message;

import java.util.logging.Logger;

public class LoggingUtil {
    public static void logReceivingMessage(Logger logger, Message message, LogicalLocalClock clock)
    {
        logReceivingMessage(logger, message, clock, "");
    }

    public static void logReceivingMessage(Logger logger, Message message, LogicalLocalClock clock, String additionaltext)
    {
        logger.info("RECEIVED " + message.toString() + "[LT-" + clock.getTimestampForReceivingMessage(message) + "] "
                + additionaltext);
    }
}
