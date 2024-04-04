package Server;

import javax.swing.*;
import java.awt.*;

public class MainLayout {
    static JFrame MainFrame = new JFrame();

    static JPanel MainPanel = new JPanel();

    static JLabel fpsLabel = new JLabel("FPS: ");
    static JLabel fpsValue = new JLabel("0");

    //Panels
    static ParticleArea particlePanel = new ParticleArea();

    //This is where the JPanel containing the UI for adding particles is
    static Dimension particlePanelDimension = new Dimension(250, 200);

    static JPanel particleGenerationContainer = new JPanel();
    static JLabel particleGenerationLabel = new JLabel("Server.Particle Generation");

    //Adding from point
    static JPanel pointPanel = new JPanel();
    static JLabel pointLabel = new JLabel("Batch between two points");
    static JLabel pointNumText = new JLabel("Number of Particles");
    static JTextField pointNumField = new JTextField();
    static JLabel pointStartXText = new JLabel("Start X: ");
    static JTextField pointStartXField = new JTextField();
    static JLabel pointStartYText = new JLabel("Start Y: ");
    static JTextField pointStartYField = new JTextField();
    static JLabel pointEndXText = new JLabel("End X: ");
    static JTextField pointEndXField = new JTextField();
    static JLabel pointEndYText = new JLabel("End Y: ");
    static JTextField pointEndYField = new JTextField();

    static JLabel pointVelocityText = new JLabel("Enter Velocity: ");
    static JTextField pointVelocityField = new JTextField();

    static JLabel pointAngleText = new JLabel("Enter Angle: ");
    static JTextField pointAngleField = new JTextField();
    static JButton pointGenerateParticleButton = new JButton("Generate Server.Particle(s)");


    //Adding from angle
    static JPanel anglePanel = new JPanel();
    static JLabel angleLabel = new JLabel("Batch from two angles");
    static JLabel angleNumText = new JLabel("Number of Particles");
    static JTextField angleNumField = new JTextField();
    static JLabel angleStartAngleText = new JLabel("Start Angle: ");
    static JTextField angleStartAngleField = new JTextField();
    static JLabel angleEndAngleText = new JLabel("End Angle: ");
    static JTextField angleEndAngleField = new JTextField();

    static JLabel angleVelocityText = new JLabel("Enter Velocity: ");
    static JTextField angleVelocityField = new JTextField();

    static JLabel angleStartXText = new JLabel("Start X: ");
    static JTextField angleStartXField = new JTextField();
    static JLabel angleStartYText = new JLabel("Start Y: ");
    static JTextField angleStartYField = new JTextField();
    static JButton angleGenerateParticleButton = new JButton("Generate Server.Particle(s)");


    //Adding from Velocities
    static JPanel velocityPanel = new JPanel();
    static JLabel velocityLabel = new JLabel("Batch within range of Velocities");
    static JLabel velocityNumText = new JLabel("Number of Particles");
    static JTextField velocityNumField = new JTextField();
    static JLabel velocityStartVelocityText = new JLabel("Start Velocity: ");
    static JTextField velocityStartVelocityField = new JTextField();
    static JLabel velocityEndVelocityText = new JLabel("End Velocity: ");
    static JTextField velocityEndVelocityField = new JTextField();

    static JLabel velocityAngleText = new JLabel("Enter Angle: ");
    static JTextField velocityAngleField = new JTextField();

    static JLabel velocityStartXText = new JLabel("Start X: ");
    static JTextField velocityStartXField = new JTextField();
    static JLabel velocityStartYText = new JLabel("Start Y: ");
    static JTextField velocityStartYField = new JTextField();
    static JButton velocityGenerateParticleButton = new JButton("Generate Server.Particle(s)");

    // Simulation Mode
    static JLabel modeText = new JLabel("DEVELOPER MODE");
    static JButton modeButton = new JButton("Go to Explorer Mode");

    //For the grid layouts. Since the Panels for adding particles are of dimension (7,2) there are times when you only have one component on one row, so this is to add a filler label for those cases.
    public static void addFiller(JPanel panel) {
        JLabel filler = new JLabel();
        panel.add(filler);
    }

    public static void initializeAnglePanel() {
        anglePanel.setLayout(new GridLayout(9, 2));
        anglePanel.setPreferredSize(particlePanelDimension);

        anglePanel.add(angleLabel);
        addFiller(anglePanel);

        anglePanel.add(angleNumText);
        anglePanel.add(angleNumField);

        anglePanel.add(angleStartAngleText);
        anglePanel.add(angleStartAngleField);

        anglePanel.add(angleEndAngleText);
        anglePanel.add(angleEndAngleField);

        anglePanel.add(angleStartXText);
        anglePanel.add(angleStartXField);

        anglePanel.add(angleStartYText);
        anglePanel.add(angleStartYField);

        anglePanel.add(angleVelocityText);
        anglePanel.add(angleVelocityField);

        addFiller(anglePanel);
        anglePanel.add(angleGenerateParticleButton);
    }

    public static void initializePointPanel() {
        pointPanel.setLayout(new GridLayout(9, 2));
        pointPanel.setPreferredSize(new Dimension(particlePanelDimension));

        pointPanel.add(pointLabel);
        addFiller(pointPanel);

        pointPanel.add(pointNumText);
        pointPanel.add(pointNumField);

        pointPanel.add(pointStartXText);
        pointPanel.add(pointStartXField);

        pointPanel.add(pointStartYText);
        pointPanel.add(pointStartYField);

        pointPanel.add(pointEndXText);
        pointPanel.add(pointEndXField);

        pointPanel.add(pointEndYText);
        pointPanel.add(pointEndYField);

        pointPanel.add(pointVelocityText);
        pointPanel.add(pointVelocityField);

        pointPanel.add(pointAngleText);
        pointPanel.add(pointAngleField);

        addFiller(pointPanel);
        pointPanel.add(pointGenerateParticleButton);
    }

    public static void initializeVelocityPanel() {
        velocityPanel.setLayout(new GridLayout(9, 2));
        velocityPanel.setPreferredSize(new Dimension(particlePanelDimension));

        velocityPanel.add(velocityLabel);
        addFiller(velocityPanel);

        velocityPanel.add(velocityNumText);
        velocityPanel.add(velocityNumField);

        velocityPanel.add(velocityStartVelocityText);
        velocityPanel.add(velocityStartVelocityField);

        velocityPanel.add(velocityEndVelocityText);
        velocityPanel.add(velocityEndVelocityField);

        velocityPanel.add(velocityStartXText);
        velocityPanel.add(velocityStartXField);

        velocityPanel.add(velocityStartYText);
        velocityPanel.add(velocityStartYField);

        velocityPanel.add(velocityAngleText);
        velocityPanel.add(velocityAngleField);

        addFiller(velocityPanel);
        velocityPanel.add(velocityGenerateParticleButton);
    }

    public static void initializeGUI() {
        MainFrame.setPreferredSize(new Dimension(1920,1080));
        MainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        MainPanel.setPreferredSize(new Dimension(1920,1080));
        MainPanel.setLayout(new FlowLayout());

        MainPanel.add(modeText);
        newRow();

        MainPanel.add(fpsLabel);
        MainPanel.add(fpsValue);
        newRow();

        MainPanel.add(particlePanel);
        particlePanel.setPreferredSize(new Dimension(1280,720));

        newRow();

        //Generating as a grid of 1 row and 3 columns. There are 3 ways to add particles, so that's why there are 3 columns
        particleGenerationContainer.setLayout(new GridLayout(1, 3));
        particleGenerationContainer.setPreferredSize(new Dimension(1300, 115));

        initializePointPanel();
        initializeAnglePanel();
        initializeVelocityPanel();

        particleGenerationContainer.add(pointPanel);
        particleGenerationContainer.add(anglePanel);
        particleGenerationContainer.add(velocityPanel);


        MainPanel.add(particleGenerationLabel);
        newRow();

        MainPanel.add(particleGenerationContainer);
        newRow();

        MainPanel.add(modeButton);

        JScrollPane scroll = new JScrollPane(MainPanel,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

        MainFrame.getContentPane().add(scroll);

        MainFrame.pack();
    }

    public static void newRow() {
        JLabel spacerLabel = new JLabel();
        spacerLabel.setPreferredSize(new Dimension(9999, 0));
        MainPanel.add(spacerLabel);
    }

}
