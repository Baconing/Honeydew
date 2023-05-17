package tech.baconing.honeydew.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import org.jetbrains.annotations.NotNull;

@DatabaseTable(tableName = "honeydew_users")
public class UserEntity extends AccessibleEntity {
    @DatabaseField(id = true, canBeNull = false)
    private @NotNull String id;

    @DatabaseField(canBeNull = false)
    private @NotNull Long balance;

    @DatabaseField
    private @NotNull Long workCooldown;

    public UserEntity(@NotNull String id) {
        super();
        this.id = id;
        this.balance = 0L;
        this.workCooldown = 0L;
        request();
    }

    @Deprecated
    public UserEntity() {}

    @NotNull
    public String getId() {
        return id;
    }

    @NotNull
    public synchronized Long getBalance() {
        return balance;
    }

    public synchronized void setBalance(@NotNull Long balance) {
        this.balance = balance;
    }

    @NotNull
    public synchronized Long getWorkCooldown() {
        return workCooldown;
    }

    public synchronized void setWorkCooldown(@NotNull Long workCooldown) {
        this.workCooldown = workCooldown;
    }
}
