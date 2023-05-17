package tech.baconing.honeydew.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import org.jetbrains.annotations.NotNull;

@DatabaseTable(tableName = "honeydew_global_config")
public class GlobalConfigEntity extends AccessibleEntity {
    @DatabaseField(id = true)
    public @NotNull Integer id = 1;

    @DatabaseField(canBeNull = false)
    public @NotNull Long workCooldown;

    @DatabaseField(canBeNull = false)
    public @NotNull Integer workRewardMax;

    @DatabaseField(canBeNull = false)
    public @NotNull Integer workRewardMin;

    public GlobalConfigEntity() {
        super();
        workCooldown = 60000L;
        workRewardMax = 100;
        workRewardMin = 10;
    }

    @NotNull
    public synchronized Long getWorkCooldown() {
        return workCooldown;
    }

    public synchronized void setWorkCooldown(@NotNull Long workCooldown) {
        this.workCooldown = workCooldown;
    }

    @NotNull
    public synchronized Integer getWorkRewardMax() {
        return workRewardMax;
    }

    public synchronized void setWorkRewardMax(@NotNull Integer workRewardMax) {
        this.workRewardMax = workRewardMax;
    }

    @NotNull
    public synchronized Integer getWorkRewardMin() {
        return workRewardMin;
    }

    public synchronized void setWorkRewardMin(@NotNull Integer workRewardMin) {
        this.workRewardMin = workRewardMin;
    }
}
