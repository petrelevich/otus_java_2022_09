package ru.otus.rmi;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class RmiServer extends UnicastRemoteObject implements EchoInterface {
    private static final long serialVersionUID = 1L;

    private static final int SERVER_PORT = 8090;
    //public static final int REGISTRY_PORT = 1099;
    public static final int REGISTRY_PORT = 1099;


    private RmiServer(int port) throws RemoteException {
        super(port);
    }

    @Override
    public String echo(String data) {
        System.out.printf("data from client: %s%n", data);
        return "echo:" + data;

    }

    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(REGISTRY_PORT);
            Naming.rebind(String.format("//localhost:%d/EchoServer", REGISTRY_PORT), new RmiServer(SERVER_PORT));
            System.out.println("waiting for client connection...");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
