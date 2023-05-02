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

    public UserEntity(@NotNull String id) {
        super();
        this.id = id;
        this.balance = 0L;
        request();
    }

    @Deprecated
    public UserEntity() {}

    /**
     * @return User ID.
     */
    @NotNull
    public String getId() {
        return id;
    }

    /**
     * @return Balance.
     */
    @NotNull
    public Long getBalance() {
        return balance;
    }

    /**
     * @param balance
     */
    public synchronized void setBalance(@NotNull Long balance) {
        this.balance = balance;
    }
}
