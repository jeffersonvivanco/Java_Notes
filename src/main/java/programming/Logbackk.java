package programming;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Logbackk {

    private static Logger logger = LoggerFactory.getLogger(Logbackk.class);

    public static void main(String[] args){
        logger.warn("Testing warning");
        logger.info("Testing info");
        logger.error("Testing Error");
        logger.debug("Testing debug");
    }
}
