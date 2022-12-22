package ru.otus.cassandrademo.db;


import com.datastax.oss.driver.api.core.CqlSession;

import java.net.InetSocketAddress;

public class CassandraConnection implements AutoCloseable {

    private final CqlSession session;

    public CassandraConnection(String nodeAddress, int port) {
        session = CqlSession.builder()
                .addContactPoint(new InetSocketAddress(nodeAddress, port))
                .withLocalDatacenter("datacenter1")
                .build();
    }

    public CqlSession getSession() {
        return this.session;
    }

    @Override
    public void close() {
        session.close();
    }
}
