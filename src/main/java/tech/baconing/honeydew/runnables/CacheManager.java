package tech.baconing.honeydew.runnables;

import org.jetbrains.annotations.Nullable;
import tech.baconing.honeydew.entities.GlobalConfigEntity;
import tech.baconing.honeydew.entities.UserEntity;
import tech.baconing.honeydew.util.Database;

import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;

public class CacheManager extends EndableRunnable {
    private static final ConcurrentHashMap<String, UserEntity> userCache = new ConcurrentHashMap<>();
    private static GlobalConfigEntity globalConfigCache;

    @Override
    public void run() {
        while (true) {
            if (!running) {
                for (UserEntity user : userCache.values()) {
                    try {
                        Database.saveUserEntity(user);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
                break;
            }

            for (UserEntity user : userCache.values()) {
                if (user.getNoAccessEpoch() != 0L && System.currentTimeMillis() - user.getNoAccessEpoch() >= 90000) {
                    try {
                        Database.saveUserEntity(user);
                        userCache.remove(user.getId());
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            if (globalConfigCache != null && globalConfigCache.getNoAccessEpoch() != 0L && System.currentTimeMillis() - globalConfigCache.getNoAccessEpoch() >= 90000) {
                try {
                    Database.saveGlobalConfigEntity(globalConfigCache);
                    globalConfigCache = null;
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @Nullable
    public static UserEntity getUserEntity(String id) {
        if (userCache.containsKey(id)) {
            return userCache.get(id);
        } else {
            try {
                UserEntity userEntity = Database.getUserEntity(id);
                userCache.put(id, userEntity);
                return userEntity;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void saveUserEntity(UserEntity userEntity) {
        userCache.put(userEntity.getId(), userEntity);
    }

    @Nullable
    public static GlobalConfigEntity getGlobalConfigEntity() {
        if (globalConfigCache == null) {
            try {
                globalConfigCache = Database.getGlobalConfigEntity();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return globalConfigCache;
    }

    public static void saveGlobalConfigEntity(GlobalConfigEntity globalConfigEntity) {
        globalConfigCache = globalConfigEntity;
    }

    @Override
    public String getName() {
        return "CacheManager";
    }
}
