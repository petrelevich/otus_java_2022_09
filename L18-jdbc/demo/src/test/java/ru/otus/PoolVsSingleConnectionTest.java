package ru.otus;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.BindMode;
import org.testcontainers.containers.PostgreSQLContainer;

import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

@DisplayName("Сравниваем скорость работы с connectionPool и без него ")
@Testcontainers
class PoolVsSingleConnectionTest {
    private static final Logger logger = LoggerFactory.getLogger(PoolVsSingleConnectionTest.class);

    private HikariDataSource dataSourcePool;

    // will be started before and stopped after each test method
    @Container
    private final PostgreSQLContainer<?> postgresqlContainer = new PostgreSQLContainer<>("postgres:12-alpine")
            .withDatabaseName("testDataBase")
            .withUsername("owner")
            .withPassword("secret")
            .withClasspathResourceMapping("00_createTables.sql", "/docker-entrypoint-initdb.d/00_createTables.sql", BindMode.READ_ONLY)
            .withClasspathResourceMapping("01_insertData.sql", "/docker-entrypoint-initdb.d/01_insertData.sql", BindMode.READ_ONLY);

    @DisplayName(" выполняем sql-запрос")
    @ParameterizedTest(name = " с использованием connection Pool: {0}")
    @ValueSource(booleans = {false, true})
    void doSelect(boolean usePool) throws SQLException {
        if (usePool) {
            makeConnectionPool();
        }

        logger.info("before getting connection");
        long before = System.currentTimeMillis();

        try (Connection connection = getConnection(usePool)) {
        }

        logger.info("usePool: {}, after getting connection, time:{}", usePool, (System.currentTimeMillis() - before));

        if (usePool) {
            dataSourcePool.close();
        }
    }

    private Properties getConnectionProperties() {
        Properties props = new Properties();
        props.setProperty("user", postgresqlContainer.getUsername());
        props.setProperty("password", postgresqlContainer.getPassword());
        props.setProperty("ssl", "false");
        return props;
    }

    private Connection makeSingleConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(postgresqlContainer.getJdbcUrl(), getConnectionProperties());
        connection.setAutoCommit(false);
        return connection;
    }

    void makeConnectionPool() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(postgresqlContainer.getJdbcUrl());
        config.setConnectionTimeout(3000); //ms
        config.setIdleTimeout(60000); //ms
        config.setMaxLifetime(600000);//ms
        config.setAutoCommit(false);
        config.setMinimumIdle(5);
        config.setMaximumPoolSize(10);
        config.setPoolName("DemoHiPool");
        config.setRegisterMbeans(true);

        config.setDataSourceProperties(getConnectionProperties());

        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        dataSourcePool = new HikariDataSource(config);
    }

    private Connection getConnection(boolean usePool) throws SQLException {
        return usePool ? dataSourcePool.getConnection() : makeSingleConnection();
    }
}
