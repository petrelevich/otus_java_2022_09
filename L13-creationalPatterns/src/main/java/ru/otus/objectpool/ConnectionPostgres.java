package ru.otus.objectpool;

public class ConnectionPostgres implements Connection {
    @Override
    public void connect() {
        System.out.print("start connect...");
        sleep(2000);
        System.out.println("connected");
    }

    @Override
    public void execSelect() {
        System.out.println("run SELECT");
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            new RuntimeException(e);
        }
    }

}
