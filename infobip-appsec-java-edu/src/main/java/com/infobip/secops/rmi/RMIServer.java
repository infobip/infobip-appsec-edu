package com.infobip.secops.rmi;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RMIServer extends UnicastRemoteObject implements RMI {

    public RMIServer() throws RemoteException {
        super();
    }

    @Override
    public Integer sum(List<Integer> ints) {
        Integer s = 0;
        for (Integer i : ints) {
            s += i;
        }
        return s;
    }

    public static void main(String... args) throws Exception {
        LocateRegistry.createRegistry(1090).rebind("RMIServer", new RMIServer());
        System.out.println("RMI started");
    }
}

class P1 extends ArrayList<Integer> {
    @Override
    public Iterator<Integer> iterator() {
        return null;
    }

    public static void main(String... args) throws Exception {
        RMI rmi = (RMI) LocateRegistry.getRegistry(1090).lookup("RMIServer");
        //List<Integer> i = new ArrayList<>();
        //i.add(1);
        //i.add(2);
        System.out.println(new P1());
    }
}
