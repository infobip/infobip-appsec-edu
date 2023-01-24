package com.infobip.secops.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface RMI extends Remote {

    Integer sum(List<Integer> ints) throws RemoteException;
}
