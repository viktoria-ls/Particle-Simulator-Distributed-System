package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;


public class ExplorerListener extends Thread {
    private DataInputStream inputStream;
    private DataOutputStream outputStream;


    ExplorerListener(DataInputStream in, DataOutputStream out) {
        inputStream = in;
        outputStream = out;
    }

    public void run() {
        this.send();
    }

    public void send() {
        ParticleArea.particleList.forEach(p -> {
            try {
                outputStream.writeUTF(p.getJSON());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void sendFinishFlag() {
        try {
            outputStream.writeInt(-1);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
}
