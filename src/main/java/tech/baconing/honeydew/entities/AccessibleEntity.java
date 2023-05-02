package tech.baconing.honeydew.entities;

public class AccessibleEntity {
    private transient Integer accessCount;
    private transient Long noAccessEpoch;
    private transient Long accessEpoch;

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

        if (this.accessCount < 0) throw new RuntimeException("Access count is less than zero. " + this);
    }

    public synchronized Integer getAccessCount() {
        return this.accessCount;
    }

    public synchronized Long getNoAccessEpoch() {
        return this.noAccessEpoch;
    }
}
