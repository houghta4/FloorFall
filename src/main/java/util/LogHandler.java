package util;


import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LogHandler {
    private static final Logger logger = Logger.getLogger("errorLogger");

    /**
     * Sets up custom logger
     */
    public static void setup() {
        Handler fileHandler;
        try {
            fileHandler = new FileHandler("errorLog.log", true);
            logger.addHandler(fileHandler);
            fileHandler.setLevel(Level.ALL);
            fileHandler.setFormatter(new SimpleFormatter());
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Exception occurred during logging setup!", e);
        }
    }

}