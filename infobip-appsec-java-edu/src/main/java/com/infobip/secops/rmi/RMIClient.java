package com.infobip.secops.rmi;

import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;
import java.util.List;

public class RMIClient {

    public static void main(String... args) throws Exception {
        RMI rmi = (RMI) LocateRegistry.getRegistry(1090).lookup("RMIServer");
        List<Integer> i = new ArrayList<>();
        i.add(1);
        i.add(2);
        System.out.println(rmi.sum(i));
    }
}
