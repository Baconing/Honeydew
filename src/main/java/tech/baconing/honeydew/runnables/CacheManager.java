package tech.baconing.honeydew.runnables;

import tech.baconing.honeydew.entities.UserEntity;
import tech.baconing.honeydew.util.Database;

import java.sql.SQLException;
import java.util.HashMap;

public class CacheManager extends EndableRunnable {
    private static HashMap<String, UserEntity> userCache = new HashMap<>();

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
        }
    }

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

    @Override
    public String getName() {
        return "CacheManager";
    }
}
