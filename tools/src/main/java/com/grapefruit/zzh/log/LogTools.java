package com.grapefruit.zzh.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Grapefruit
 * @version 1.0
 * @date 2021/4/24
 */
public class LogTools {

    public static final String SEND_LOGGER = "sendLogger";
    public static final String RECEIVE_LOGGER = "receiveLogger";
    public static final String DEBUG_LOGGER = "debugLogger";


    public static Logger getSendLogger(){
        return LoggerFactory.getLogger(SEND_LOGGER);
    }

    public static Logger getReceiveLogger(){
        return LoggerFactory.getLogger(RECEIVE_LOGGER);
    }

    public static Logger getDebugLogger(){
        return LoggerFactory.getLogger(DEBUG_LOGGER);
    }
}
