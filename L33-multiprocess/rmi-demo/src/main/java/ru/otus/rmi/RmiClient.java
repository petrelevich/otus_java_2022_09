package ru.otus.rmi;

import java.rmi.Naming;

public class RmiClient {
    public static void main(String[] args) throws Exception {

        EchoInterface echoInterface = (EchoInterface) Naming.lookup(String.format("//localhost:%d/EchoServer",
                RmiServer.REGISTRY_PORT));
        var dataFromServer = echoInterface.echo("hello");
        System.out.printf("response from the server: %s%n", dataFromServer);

    }
}
