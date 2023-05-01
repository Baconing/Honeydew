package tech.baconing.honeydew.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tech.baconing.honeydew.Honeydew;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class Config {
    private static Config config;
    private static final Logger logger = LogManager.getLogger(Config.class);
    protected static GsonBuilder jsonBuilder = new GsonBuilder();
    protected static Gson json = jsonBuilder.create();

    private String token  = "";

    //DATABASE CONFIGURATION
    private String address = "";
    private int port = 0;
    private String username = "";
    private String password = "";
    private String database = "";
    private int maxConnections = 10;



    public static synchronized Config getConfig() {
        String sPath = System.getProperty("user.dir") + "/config.json";
        Path path = Path.of(sPath);
        boolean fileExists = Files.exists(path);
        try {
            if (!fileExists) {
                new FileOutputStream(sPath, true).close();
            }
            byte[] dataByte = Files.newInputStream(path).readAllBytes();
            StringBuilder dataBuilder = new StringBuilder();
            for (byte b : dataByte) {
                dataBuilder.append((char) b);
            }
            String data = dataBuilder.toString();

            if (data.length() == 0 || data.equals("null")) {
                 config = new Config();
                 saveConfig();
                 logger.fatal("Missing config, please fill out config.json and restart the bot.");
                 System.exit(1);
                 return null;
            }

            config = json.fromJson(data, Config.class);

            if (!checkConfiguration()) {
                System.exit(1);
                return null;
            }

            return config;
        } catch (IOException e) {
            logger.fatal("Failed to load config.json.");
            e.printStackTrace();
            System.exit(1);
            return null;
        }
    }

    public static synchronized void saveConfig() {
        try {
            OutputStream out = Files.newOutputStream(Path.of(System.getProperty("user.dir") + "/config.json"));
            out.write(json.toJson(config).getBytes());
            out.flush();
            out.close();
        } catch (IOException e) {
            logger.fatal("Failed to save config.json.");
            e.printStackTrace();
        }
    }

    public static synchronized boolean checkConfiguration() {
        if (config.getToken().length() == 0) {
            logger.fatal("Missing token, please fill out config.json and restart the bot.");
            return false;
        }
        if (config.getAddress().length() == 0) {
            logger.fatal("Missing database address, please fill out config.json and restart the bot.");
            return false;
        }
        if (config.getPort() < 1 || config.getPort() > 65535) {
            logger.fatal("Database port out of range (1-65535), please fill out config.json and restart the bot.");
            return false;
        }
        if (config.getUsername().length() == 0) {
            logger.fatal("Missing database username, please fill out config.json and restart the bot.");
            return false;
        }
        if (config.getPassword().length() == 0) {
            logger.fatal("Missing database password, please fill out config.json and restart the bot.");
            return false;
        }
        if (config.getDatabase().length() == 0) {
            logger.fatal("Missing database name, please fill out config.json and restart the bot.");
            return false;
        }
        if (config.getMaxConnections() < 1) {
            logger.fatal("Max connections must be greater than 0, please fill out config.json and restart the bot.");
            return false;
        }
        return true;
    }

    public synchronized String getToken() {
        return token;
    }

    public synchronized String getAddress() {
        return address;
    }

    public synchronized int getPort() {
        return port;
    }

    public synchronized String getUsername() {
        return username;
    }

    public synchronized String getPassword() {
        return password;
    }

    public synchronized String getDatabase() {
        return database;
    }

    public synchronized int getMaxConnections() {
        return maxConnections;
    }
}
