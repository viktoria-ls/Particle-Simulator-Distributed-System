package Server;

import org.json.JSONObject;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;


public class ExplorerListener extends Thread {
    private DataInputStream inputStream;
    private DataOutputStream outputStream;
    private Particle particle;


    ExplorerListener(DataInputStream in, DataOutputStream out, Particle p) {
        inputStream = in;
        outputStream = out;
        particle = p;
    }

    public void run() {
        while(true) {
            send();
            sendFinishFlag();
            updateParticle();
        }
    }

    public void updateParticle() {

        try {
            byte[] buffer = new byte[1024];
            // Read data from client
            int bytesRead = 0;
            bytesRead = inputStream.read(buffer);
            String receivedData = new String(buffer, 0, bytesRead);

            JSONObject json = new JSONObject(receivedData);

            Double tempx = json.getDouble("x");
            Double tempy = json.getDouble("y");

            if(tempx >= 711) {
                tempx = 711.0;
            }
            if(tempy >= 1271) {
                tempy = 1271.0;
            }

            Main.commandQueue.add(new Command(tempx, tempy, particle));

        } catch (IOException e) {
            throw new RuntimeException(e);
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

        ParticleArea.explorerList.forEach(e -> {
            try {

                String eJson = e.getJSON();
                outputStream.writeInt(eJson.length());
                outputStream.writeUTF(eJson);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
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
