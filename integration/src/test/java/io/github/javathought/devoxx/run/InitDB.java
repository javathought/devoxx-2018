package io.github.javathought.devoxx.run;

import org.flywaydb.core.Flyway;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * ApiServer class.
 *
 */
public class InitDB {
    // Base URI the Grizzly HTTP server will listen on

    public static String jdbcUrl = "jdbc:tc:mysql:5.7://hostname/databasename?" +
            "TC_INITFUNCTION=io.github.javathought.devoxx.run.InitDB::initDB";
    public static String jdbcUrlInitiated = "jdbc:tc:mysql:5.7://hostname/databasename";


    public static void initDB(Connection connection) throws SQLException {
        // e.g. run schema setup or Flyway/liquibase/etc DB migrations here...

        // Create the Flyway instance
        Flyway flyway = new Flyway();

        // Point it to the database


        flyway.setDataSource(connection.getMetaData().getURL(), "root", "test");

        // Start the migration
        flyway.migrate();
    }


}

