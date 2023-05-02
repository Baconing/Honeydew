package tech.baconing.honeydew.runnables;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tech.baconing.honeydew.Honeydew;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;

public class ThreadManager extends Thread {
    private static final Logger logger = LogManager.getLogger(Honeydew.class);

    private final LinkedBlockingQueue<EndableRunnable> queue = new LinkedBlockingQueue<>();
    private final HashMap<EndableRunnable, Thread> threads = new HashMap<>();

    private final HashMap<Thread, Integer> endAttempts = new HashMap<>();

    private final int maxThreads = 100;
    private final int maxEndAttempts = 5;

    private boolean running = true;
    private boolean ended = false;

    @Override
    public void run() {
        while (true) {
            if (!running) {
                for (EndableRunnable e : threads.keySet()) {
                    e.end();
                }

                while (threads.values().size() > 0) {
                    for (EndableRunnable e : threads.keySet()) {
                        if (threads.get(e).getState() != State.TERMINATED || e.isStopped()) {
                            if (endAttempts.containsKey(threads.get(e))) {
                                endAttempts.put(threads.get(e), endAttempts.getOrDefault(threads.get(e), 0) + 1);
                            }
                        } else if (endAttempts.getOrDefault(threads.get(e), 0) > maxEndAttempts && (threads.get(e).getState() == State.TERMINATED || e.isStopped())) {
                            threads.get(e).interrupt();
                            threads.remove(e);
                        } else {
                            threads.remove(e);
                            endAttempts.remove(threads.get(e));
                        }
                    }
                }
                break;
            }

            if (threads.size() <= maxThreads) {
                for (EndableRunnable runnable : queue) {
                    Thread thread = new Thread(runnable);
                    threads.put(runnable, thread);
                    thread.start();
                    queue.remove(runnable);
                }
            }

            for (EndableRunnable e : threads.keySet()) {
                if (threads.get(e).getState() == State.TERMINATED || e.isStopped()) {
                    threads.remove(e);
                    logger.trace("Runnable " + e.getName() + " ended.");
                }
            }
        }
        ended = true;
    }

    public synchronized void add(EndableRunnable runnable) {
        logger.trace("Adding runnable to queue: " + runnable.getName());
        queue.offer(runnable);
    }

    public synchronized Future<String> end() {
        CompletableFuture<String> future = new CompletableFuture<>();
        logger.info("Ending ThreadManager...");
        running = false;
        while (true) {
            if (ended) {
                break;
            }
        }
        future.complete("ThreadManager ended.");
        logger.info("ThreadManager ended.");
        return future;
    }
}
