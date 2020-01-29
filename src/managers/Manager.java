package managers;

import objects.Device;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

public class Manager {
    public HashMap<String, HashMap<String, Device>> mapDevices;
    public DataInputStream dataInputStream;
    public DataOutputStream dataOutputStream;
    public String typeDevice = "";
    public String idDevice;
    public String typeIdDevice;
    String[] strings;

}
