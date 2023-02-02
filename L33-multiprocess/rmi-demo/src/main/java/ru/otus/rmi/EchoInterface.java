package ru.otus.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface EchoInterface extends Remote {

  String echo(String data) throws RemoteException;
}
