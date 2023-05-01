package tech.baconing.honeydew.runnables;

public abstract class EndableRunnable implements NamedRunnable {
    boolean running = true;
    boolean stopped = false;

    public boolean isRunning() {
        return running;
    }

    public boolean isStopped() {
        return stopped;
    }

    public void end() {
        running = false;
    }
}
