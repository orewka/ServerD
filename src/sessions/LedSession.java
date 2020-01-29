package sessions;

import objects.Device;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class LedSession extends Session implements Runnable {

    public LedSession(DataOutputStream dataOutputStream, DataInputStream dataInputStream, HashMap<String, HashMap<String, Device>> mapDevice, String typeDevice, String idDevice) {
        this.dataOutputStream = dataOutputStream;
        this.dataInputStream = dataInputStream;
        this.mapDevice = mapDevice;
        this.typeDevice = typeDevice;
        this.idDevice = idDevice;
    }

    @Override
    public void run() {
        System.out.println("LED session created");
        int input = 0;
        while (!Thread.currentThread().isInterrupted()) {
            try {
                input = dataInputStream.readInt();
                System.out.println(input);
            } catch (IOException e) {
                System.out.println("Client disconnected");
                mapDevice.get(typeDevice).get(idDevice).setBusy(false);
                Thread.currentThread().interrupt();
            }
            try {
                dataOutputStream.write(input);
            } catch (IOException e) {
                System.out.println("Device " + idDevice + " disconnected");
                mapDevice.get(typeDevice).remove(idDevice);
                Thread.currentThread().interrupt();
            }
        }
    }
}
