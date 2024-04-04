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
        while(true) {
            send();
            sendFinishFlag();
        }
    }

    public void send() {
        ParticleArea.particleList.forEach(p -> {
            try {
                String test = p.getJSON();
                outputStream.writeInt(test.length());
                outputStream.writeUTF(test);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void sendFinishFlag() {
        try {
            outputStream.writeInt("finish".length());
            outputStream.writeUTF("finish");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
}
