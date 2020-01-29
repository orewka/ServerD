package managers;

import com.google.gson.Gson;
import objects.Device;
import readers.StringReader;
import sessions.LedSession;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CreateManager extends Manager implements Runnable {

    public CreateManager(HashMap<String, HashMap<String, Device>> mapDevices, DataInputStream dataInputStream,DataOutputStream dataOutputStream, String typeDevice, String idDevice) {
        this.mapDevices = mapDevices;
        this.dataInputStream = dataInputStream;
        this.dataOutputStream = dataOutputStream;
        this.typeDevice = typeDevice;
        this.idDevice = idDevice;
    }

    private void sendDevices(DataOutputStream dataOutputStream, String typeDevice) {
        ArrayList<String> listIdDevices = new ArrayList<>();
        if (mapDevices.containsKey(typeDevice)) {
            for (Map.Entry<String, HashMap<String, Device>> entry : mapDevices.entrySet()) {
                for (Map.Entry<String, Device> entry1: entry.getValue().entrySet()) {
                    if (!entry1.getValue().isBusy()) {
                            listIdDevices.add(entry1.getKey());
                    }
                }
            }
        } else {
            System.out.println("No type device connected");
        }
        try {
            String jsonStr = new Gson().toJson(listIdDevices);
            System.out.println(jsonStr);
            dataOutputStream.write(jsonStr.getBytes());
            dataOutputStream.flush();
        } catch (IOException e) {
            System.out.println("Error send devices, client disconnected");
        }

    }

    public void createSession(String typeDevice, String idDevice) {
        Socket DeviceSocket = mapDevices.get(typeDevice).get(idDevice).getSocket();
        try  {
            DataOutputStream dataOutputStream = new DataOutputStream(DeviceSocket.getOutputStream());
            mapDevices.get(typeDevice).get(idDevice).setBusy(true);
            switch (typeDevice) {
                case "LED" :
                    Thread ledSession = new Thread(new LedSession(dataOutputStream, dataInputStream, mapDevices, typeDevice, idDevice));
                    ledSession.start();
            }
        } catch (IOException e) {
            System.out.println("Error create session, device " + idDevice + "disconnected");
            mapDevices.get(typeDevice).remove(idDevice);
        }

    }

    @Override
    public void run() {
        StringReader stringReader = new StringReader();
        typeDevice = stringReader.readCommand(dataInputStream);
        sendDevices(dataOutputStream, typeDevice);
        idDevice = stringReader.readCommand(dataInputStream);
        createSession(typeDevice, idDevice);
    }
}
