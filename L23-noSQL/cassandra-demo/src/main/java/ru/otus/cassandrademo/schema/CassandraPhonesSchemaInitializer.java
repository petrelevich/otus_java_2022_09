package ru.otus.cassandrademo.schema;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import lombok.RequiredArgsConstructor;
import ru.otus.cassandrademo.db.CassandraConnection;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

@RequiredArgsConstructor
public class CassandraPhonesSchemaInitializer implements CassandraSchemaInitializer {
    private final CassandraConnection cassandraConnection;

    @Override
    public void initSchema() {
        CqlSession session = cassandraConnection.getSession();
        createKeySpace(session);
        createTable(session);
    }

    @Override
    public void dropSchemaIfExists() {
        cassandraConnection.getSession()
                .execute(SimpleStatement.builder("DROP KEYSPACE IF EXISTS Products")
                        .setTimeout(Duration.of(10, ChronoUnit.SECONDS))
                        .build()
                );
    }

    private void createKeySpace(CqlSession session) {
        String query = "CREATE KEYSPACE IF NOT EXISTS Products" +
                " WITH replication = {" +
                "'class':'SimpleStrategy','replication_factor':1};";
        session.execute(query);
    }

    private void createTable(CqlSession session) {
        String query = "CREATE TABLE IF NOT EXISTS Products.Phones (" +
                "id uuid PRIMARY KEY, " +
                "model text," +
                "color text," +
                "serialNumber text," +
                "operatingSystem text);";
        session.execute(query);
    }
}
