package org.selenium.java.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerManager {

    public static Logger getLogger(Class <?> clazz)
    {
        return LogManager.getLogger();
    }

}
