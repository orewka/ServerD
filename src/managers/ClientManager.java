package managers;

import objects.Device;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class ClientManager extends Manager implements Runnable {

    public ClientManager(HashMap<String, HashMap<String, Device>> mapDevices) {
        this.mapDevices = mapDevices;
    }

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(9595)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                dataInputStream = new DataInputStream(clientSocket.getInputStream());
                dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
                Thread createManager = new Thread(new CreateManager(mapDevices, dataInputStream,dataOutputStream, typeDevice, idDevice));
                createManager.start();
                System.out.println("Client connected");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
