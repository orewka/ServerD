package managers;

import objects.Device;
import readers.StringReader;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class DeviceManager extends Manager implements Runnable {

    public DeviceManager(HashMap<String, HashMap<String, Device>> mapDevices) {
        this.mapDevices = mapDevices;
    }

    private void updateDevices(HashMap<String, HashMap<String, Device>> mapDevices, String[] strings, Socket deviceSocket) {
        Device device = new Device(deviceSocket, false);
        if (mapDevices.containsKey(strings[0])) {
            mapDevices.get(strings[0]).put(strings[1], device);
        } else {
            HashMap<String, Device> deviceHashMap = new HashMap<>();
            deviceHashMap.put(strings[1], device);
            mapDevices.put(strings[0], deviceHashMap);
        }
        System.out.println("Device " + strings[1] + " connected");
    }

    @Override
    public void run() {
        StringReader stringReader = new StringReader();
        try (ServerSocket serverSocket = new ServerSocket(9090)) {
            while (true) {
                Socket deviceSocket = serverSocket.accept();
                dataInputStream = new DataInputStream(deviceSocket.getInputStream());
                typeDevice = stringReader.readCommand(dataInputStream);
                strings = typeDevice.split(":");
                updateDevices(mapDevices, strings, deviceSocket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
