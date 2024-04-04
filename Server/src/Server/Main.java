package Server;

import org.json.JSONObject;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    static final int THREAD_COUNT = 8;
    public static DelayQueue<Command> commandQueue = new DelayQueue<>();
    public static ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
    public static ServerSocket server;
    public static CopyOnWriteArrayList<ExplorerListener> explorerListeners = new CopyOnWriteArrayList<ExplorerListener>();

    public static void main(String[] args) throws InterruptedException {
        try {
            server = new ServerSocket(1337);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        ExplorerAcceptThread explorerAcceptThread = new ExplorerAcceptThread();
        explorerAcceptThread.start();
        System.out.println("Explorer Accept Thread Started.");

        RenderRunnable renderRunnable = new RenderRunnable();
        Thread renderThread = new Thread(renderRunnable);

        Controller.initializeActionListeners();
        MainLayout.initializeGUI();
        MainLayout.MainFrame.setVisible(true);

        // Start rendering thread when the first point
        executorService.execute(new WorkerRunnable(commandQueue.take()));
        renderThread.start();
        while(true) {
            executorService.execute(new WorkerRunnable(commandQueue.take()));
        }
    }
}

class ExplorerAcceptThread extends Thread {
    public void run() {
        while(true) {
            try {
                System.out.println("Accepting Sockets.");
                Socket temp = Main.server.accept();
                System.out.println("Sockets Accepted.");
    
                DataInputStream tempInStream = new DataInputStream(temp.getInputStream());
                DataOutputStream tempOutStream = new DataOutputStream(temp.getOutputStream());

                byte[] buffer = new byte[1024];
                // Read data from client
                int bytesRead = tempInStream.read(buffer);
                String receivedData = new String(buffer, 0, bytesRead);


                JSONObject json = new JSONObject(receivedData);

                Double tempx = json.getDouble("x");
                Double tempy =  json.getDouble("y");

                if(tempy >= 711) {
                    tempx = 711.0;
                }
                if(tempx >= 1271) {
                    tempy = 1271.0;
                }

                System.out.println("inital");
                System.out.println(tempx);
                System.out.println(tempy);

                Particle tempParticle = new Particle(tempx, tempy, "explorer");

                ParticleArea.explorerList.add(tempParticle);
                Main.explorerListeners.add(new ExplorerListener(tempInStream, tempOutStream, tempParticle));

                Main.explorerListeners.get(Main.explorerListeners.size() - 1).start();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
