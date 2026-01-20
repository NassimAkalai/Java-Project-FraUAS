package manager;

import java.util.logging.Logger;
import utils.LoggerFactory;

/**
 * Option to exit the application.
 */
public class ExitOption implements SelectionOptions {
    private static final Logger logger = LoggerFactory.getLogger();

    /**
     * Executes the exit logic, terminating the application.
     *
     * @param args Arguments passed to the execution logic (not used).
     */
    @Override
    public void execute(String[] args) {
        logger.info("Exiting program...");
        System.exit(0);
    }
}