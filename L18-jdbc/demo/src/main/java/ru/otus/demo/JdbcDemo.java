package ru.otus.demo;

import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcDemo {
    private static final String URL = "jdbc:postgresql://localhost:5430/demoDB";
    private static final String USER = "usr";
    private static final String PASSWORD = "pwd";

    private static final Logger logger = LoggerFactory.getLogger(JdbcDemo.class);

    public static void main(String[] args) throws SQLException {
        flywayMigrations();
        var demo = new JdbcDemo();
        try (var connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            connection.setAutoCommit(false);
            var id = 1;
            demo.insertRecord(connection, id);
            demo.selectRecord(connection, id);
        }
    }

    private void insertRecord(Connection connection, int id) throws SQLException {
        try (var pst = connection.prepareStatement("insert into test(id, name) values (?, ?)")) {
            var savePoint = connection.setSavepoint("savePointName");
            pst.setInt(1, id);
            pst.setString(2, "NameValue");
            try {
                var rowCount = pst.executeUpdate(); //Блокирующий вызов
                connection.commit();
                logger.info("inserted rowCount: {}", rowCount);
            } catch (SQLException ex) {
                connection.rollback(savePoint);
                logger.error(ex.getMessage(), ex);
            }
        }
    }

    private void selectRecord(Connection connection, int id) throws SQLException {
        try (var pst = connection.prepareStatement("select name from test where id  = ?")) {
            pst.setInt(1, id);
            try (var rs = pst.executeQuery()) {
                if (rs.next()) {
                    var name = rs.getString("name");
                    logger.info("name:{}", name);
                }
            }
        }
    }

    private static void flywayMigrations() {
        logger.info("db migration started...");
        var flyway = Flyway.configure()
                .dataSource(URL, USER, PASSWORD)
                .locations("classpath:/db/migration")
                .load();
        flyway.migrate();
        logger.info("db migration finished.");
        logger.info("***");
    }
}
