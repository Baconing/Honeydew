package tech.baconing.honeydew.util;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.table.TableUtils;
import net.dv8tion.jda.api.entities.User;
import tech.baconing.honeydew.entities.UserEntity;

import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;

public class Database {
    private static JdbcPooledConnectionSource connectionSource;

    private static Dao<UserEntity, String> userDao;

    public static void init() throws SQLException {
        connectionSource = new JdbcPooledConnectionSource("jdbc:mariadb://" + Config.getConfig().getAddress() +
                ":" + Config.getConfig().getPort()
                + "/" + Config.getConfig().getDatabase() +
                "?user=" + Config.getConfig().getUsername() +
                "&password=" + Config.getConfig().getPassword());
        connectionSource.setMaxConnectionsFree(Config.getConfig().getMaxConnections());

        TableUtils.createTableIfNotExists(connectionSource, UserEntity.class);

        userDao = DaoManager.createDao(connectionSource, UserEntity.class);
    }

    public static UserEntity getUserEntity(String name) throws SQLException {
        return userDao.queryForId(name);
    }

    public static UserEntity getUserEntity(User user) throws SQLException {
        return getUserEntity(user.getId());
    }

    public static void saveUserEntity(UserEntity userEntity) throws SQLException {
        userDao.createOrUpdate(userEntity);
    }

    public static void deleteUserEntity(UserEntity userEntity) throws SQLException {
        userDao.delete(userEntity);
    }
}
