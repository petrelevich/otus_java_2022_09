package ru.otus.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JdbcDemo {

    private final String url;
    private final String user;
    private final String pwd;

    private static final Logger logger = LoggerFactory.getLogger(JdbcDemo.class);


    public JdbcDemo(String url, String user, String pwd) {
        this.url = url;
        this.user = user;
        this.pwd = pwd;
        flywayMigrations();
    }

    public long exec() throws SQLException {
        try (var connection = DriverManager.getConnection(url, user, pwd)) {
            connection.setAutoCommit(false);
            var id = 1;
            insertRecord(connection, id);
            selectRecord(connection, id);
            return id;
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

    private void flywayMigrations() {
        logger.info("db migration started...");
        var flyway = Flyway.configure()
                .dataSource(url, user, pwd)
                .locations("classpath:/db/migration")
                .load();
        flyway.migrate();
        logger.info("db migration finished.");
        logger.info("***");
    }
}
