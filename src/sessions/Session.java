package sessions;

import objects.Device;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class Session {
    public DataOutputStream dataOutputStream;
    public DataInputStream dataInputStream;
    public HashMap<String, HashMap<String, Device>> mapDevice;
    public String typeDevice;
    public String idDevice;
    public Device device;
}
