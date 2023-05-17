package tech.baconing.honeydew.entities;

import org.jetbrains.annotations.NotNull;

public class AccessibleEntity {
    private transient @NotNull Integer accessCount;
    private transient @NotNull Long noAccessEpoch;

    public AccessibleEntity() {
        this.accessCount = 0;
        this.noAccessEpoch = 0L;
    }

    public synchronized void request() {
        this.accessCount++;
    }

    public synchronized void release() {
        this.accessCount--;
        if (this.accessCount == 0) {
            this.noAccessEpoch = System.currentTimeMillis();
        }

        if (this.accessCount < 0) throw new RuntimeException("Access count is less than zero.");
    }

    @NotNull
    public synchronized Integer getAccessCount() {
        return this.accessCount;
    }

    @NotNull
    public synchronized Long getNoAccessEpoch() {
        return this.noAccessEpoch;
    }
}
