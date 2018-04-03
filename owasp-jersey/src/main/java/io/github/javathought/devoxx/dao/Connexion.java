package io.github.javathought.devoxx.dao;

import io.github.javathought.devoxx.filters.CrossDomainFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.UUID;

/**
 *
 */
public class Connexion {
    private static Connexion ourInstance = new Connexion();

    private static final Logger LOG = LoggerFactory.getLogger(CrossDomainFilter.class);


    private Connection dbConnection;

    public static Connexion getInstance() {
        return ourInstance;
    }

    private Connexion() {
/*
        try {
            dbConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/devoxx_tia",
                    "devoxx", "owasp-2017;");
        } catch (SQLException e) {
            LOG.error("Unable to create connection", e);
        }
*/
    }

    public Connexion setConnection(String dbUrl, String user, String pwd) {
        try {
//            dbConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/devoxx_tia",
            ourInstance.dbConnection = DriverManager.getConnection(
                    dbUrl, user, pwd);
        } catch (SQLException e) {
            LOG.error("Unable to create connection", e);
        }
        return this;
    }

    public Connection getDbConnection() {
        return dbConnection;
    }

/*    public static UUID toUUID(byte[] bytes) {
        if (bytes.length != 16) {
            throw new IllegalArgumentException();
        }
        int i = 0;
        long msl = 0;
        for (; i < 8; i++) {
            msl = (msl << 8) | (bytes[i] & 0xFF);
        }
        long lsl = 0;
        for (; i < 16; i++) {
            lsl = (lsl << 8) | (bytes[i] & 0xFF);
        }
        return new UUID(msl, lsl);
    }*/

    public static byte[] UUIDToBytes(UUID uuid) {
        return ByteBuffer.wrap(new byte[16])
                .order(ByteOrder.BIG_ENDIAN)
                .putLong(uuid.getMostSignificantBits())
                .putLong(uuid.getLeastSignificantBits()).array();
    }

    public static UUID asUUID(byte[] bytes){
        ByteBuffer bb = ByteBuffer.wrap(bytes);
        long firstLong = bb.getLong();
        long secondLong = bb.getLong();
        return new UUID(firstLong, secondLong);
    }


}
