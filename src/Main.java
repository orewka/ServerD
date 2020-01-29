import managers.ClientManager;
import managers.DeviceManager;
import objects.Device;

import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) {
        HashMap<String, HashMap<String, Device>> mapDevices = new HashMap<>();
        Thread deviceManager = new Thread(new DeviceManager(mapDevices));
        deviceManager.start();
        Thread clientManager = new Thread(new ClientManager(mapDevices));
        clientManager.start();
    }
}
