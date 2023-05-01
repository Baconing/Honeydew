package tech.baconing.honeydew.runnables;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tech.baconing.honeydew.Honeydew;
import tech.baconing.honeydew.listeners.CommandListener;
import tech.baconing.honeydew.listeners.ReadyListener;

public class RegisterListeners extends EndableRunnable {
    private final static Logger logger = LogManager.getLogger(RegisterListeners.class);
    @Override
    public void run() {
        if (isRunning()) {
            logger.info("Registering listeners...");
            Honeydew.getJda().addEventListener(new ReadyListener());
            Honeydew.getJda().addEventListener(new CommandListener());
            logger.info("Registered listeners.");
        }
        stopped = true;
    }

    @Override
    public String getName() {
        return "RegisterListeners";
    }
}
