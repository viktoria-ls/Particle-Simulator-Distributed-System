package Server;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class ParticleArea extends JPanel {
    static CopyOnWriteArrayList<Particle> particleList = new CopyOnWriteArrayList<>();
    static CopyOnWriteArrayList<Particle> explorerList = new CopyOnWriteArrayList<>();
    int THREAD_COUNT = 8;
    ArrayList<Thread> drawThreadList = new ArrayList<>();

    static User user;
    static Graphics2D g2d = null;
    double scaleX = 1.0;
    double scaleY = 1.0;
    static int ovalSize = 9;

    ParticleArea() {
        super();
        this.setOpaque(true);
        this.setBackground(Color.WHITE);
    }

    public void createUser() {
        if(user == null) {
            user = new User();
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setBackground(Color.WHITE);

        g2d = (Graphics2D) g.create();

//        System.out.println(user.x.intValue() + " " + user.y.intValue());

        if (Controller.SIM_MODE == ModeType.EXPLORER) {
            createUser();
            g2d.translate(user.cameraX.intValue(), user.cameraY.intValue());
            scaleX = (double) getWidth() / (33.0 * ovalSize);
            scaleY = (double) getHeight() / (19.0 * ovalSize);

            g2d.setTransform(new AffineTransform());
            g2d.scale(scaleX, scaleY);

            g2d.translate(-user.cameraX.intValue(), -user.cameraY.intValue());

        } else {
            scaleX = 1.0;
            scaleY = 1.0;
        }

        g2d.setColor(Color.BLACK);
        g2d.fillRect(-144, -81, 144, 850);
        g2d.fillRect(1280, -81, 144, 850);
        g2d.fillRect(-144, -81, 1280 + 144, 81);
        g2d.fillRect(-144, 720, 1280 + 144, 81);

        if(user != null) {
            g2d.setColor(Color.green);
            g2d.fillOval(user.x.intValue(), user.y.intValue(), ovalSize, ovalSize);
        }

        for(Particle p : explorerList) {
            g2d.setColor(Color.red);
            g2d.fillOval(p.x.intValue(), p.y.intValue(), ovalSize, ovalSize);
        }

        int size = particleList.size();
        if(size > 0) {
            int numPerThread = size / THREAD_COUNT;
            int tempStart = 0;
            int tempEnd = tempStart + numPerThread - 1;

            if(numPerThread < 1) {
                numPerThread = 1;
            }

            THREAD_COUNT = Math.min(THREAD_COUNT, particleList.size());
            for (int i = 0; i < THREAD_COUNT; i++) {
                Thread t = new Thread(new DrawRunnable(user, tempStart, tempEnd, g2d));
                drawThreadList.add(t);
                drawThreadList.get(drawThreadList.size() - 1).start();

                tempStart = tempEnd + 1;
                //Any excess goes to the final thread
                if (i == THREAD_COUNT - 2) {
                    tempEnd = size - 1;
                }
                else
                    tempEnd += numPerThread;
            }

            for(Thread t : drawThreadList) {
                try {
                    t.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            RenderRunnable.frames++;

            drawThreadList.clear();
        }

    }
}

class DrawRunnable implements Runnable {
    User user;
    int start;
    int end;
    Graphics g;

    public DrawRunnable(User user, int start, int end, Graphics g) {
        this.user = user;
        this.start = start;
        this.end = end;
        this.g = g;
    }

    @Override
    public void run() {
        for(int i = start; i <= end; i++) {
            Particle p = ParticleArea.particleList.get(i);
            if(Controller.SIM_MODE == ModeType.EXPLORER && p.x.intValue() >= user.cameraX - 9 && p.x.intValue() <= (user.cameraX + 288) && p.y.intValue() >= user.cameraY - 9 && p.y.intValue() <= (user.cameraY + 162)){
                g.setColor(Color.black);
                g.fillOval(p.x.intValue(), p.y.intValue(), ParticleArea.ovalSize, ParticleArea.ovalSize);
            } else {
                g.setColor(Color.black);
                g.fillOval(p.x.intValue(), p.y.intValue(), ParticleArea.ovalSize, ParticleArea.ovalSize);
            }
        }
    }
}

class RenderRunnable implements Runnable {
    static int frames = 0;
    double fps = 0;
    @Override
    public void run() {
        runTimers();
        Timer timer = new Timer((0), e -> {
            SwingUtilities.invokeLater(() -> {
                MainLayout.particlePanel.repaint();
            });
        });

        timer.start();
    }

    private void runTimers() {
        Timer timer = new Timer(500, e ->{
            fps = frames/0.5;
            MainLayout.fpsValue.setText(String.valueOf(fps));
            frames = 0;
        });
        timer.start();
    }
}

