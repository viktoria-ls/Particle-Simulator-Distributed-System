package Server;

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
    
                Main.explorerListeners.add(new ExplorerListener(tempInStream, tempOutStream));

                Main.explorerListeners.get(Main.explorerListeners.size() - 1).start();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
