package tech.baconing.honeydew.util;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.table.TableUtils;
import net.dv8tion.jda.api.entities.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tech.baconing.honeydew.entities.GlobalConfigEntity;
import tech.baconing.honeydew.entities.UserEntity;

import java.sql.SQLException;
import java.sql.SQLInvalidAuthorizationSpecException;

public class Database {
    private static final Logger logger = LogManager.getLogger(Database.class);

    private static JdbcPooledConnectionSource connectionSource;

    private static Dao<UserEntity, String> userDao;
    private static Dao<GlobalConfigEntity, Integer> globalConfigDao;

    public static void init() throws SQLException {
        try {
            connectionSource = new JdbcPooledConnectionSource("jdbc:mariadb://" + Config.getConfig().getAddress() +
                    ":" + Config.getConfig().getPort()
                    + "/" + Config.getConfig().getDatabase() +
                    "?user=" + Config.getConfig().getUsername() +
                    "&password=" + Config.getConfig().getPassword());
            connectionSource.setMaxConnectionsFree(Config.getConfig().getMaxConnections());

            TableUtils.createTableIfNotExists(connectionSource, UserEntity.class);
            TableUtils.createTableIfNotExists(connectionSource, GlobalConfigEntity.class);

            userDao = DaoManager.createDao(connectionSource, UserEntity.class);
            globalConfigDao = DaoManager.createDao(connectionSource, GlobalConfigEntity.class);
        } catch (SQLException e) {
            if (e.getClass().isAssignableFrom(SQLInvalidAuthorizationSpecException.class)) {
                logger.fatal("Invalid database credentials.");
                System.exit(1);
            } else {
                throw e;
            }
        }
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

    public static GlobalConfigEntity getGlobalConfigEntity() throws SQLException {
        return globalConfigDao.queryForId(1);
    }

    public static void saveGlobalConfigEntity(GlobalConfigEntity globalConfigEntity) throws SQLException {
        globalConfigDao.createOrUpdate(globalConfigEntity);
    }
}
