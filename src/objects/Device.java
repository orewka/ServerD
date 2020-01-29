package objects;

import java.net.Socket;

public class Device {
    private Socket socket;
    private boolean busy;

    public Device(Socket socket, boolean busy) {
        this.socket = socket;
        this.busy = busy;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public boolean isBusy() {
        return busy;
    }

    public void setBusy(boolean busy) {
        this.busy = busy;
    }

    @Override
    public String toString() {
        return "Device{" +
                "socket=" + socket +
                ", busy=" + busy +
                '}';
    }
}
