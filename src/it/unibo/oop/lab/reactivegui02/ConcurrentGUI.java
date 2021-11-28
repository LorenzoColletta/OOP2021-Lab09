package it.unibo.oop.lab.reactivegui02;

import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ConcurrentGUI extends JFrame {

    private static final long SERIAL_VERSION_UID = 1L;
    private static final double WIDTH_PERC = 0.2;
    private static final double HEIGHT_PERC = 0.1;

    public ConcurrentGUI() throws HeadlessException {
        super();
        final JPanel canvas = new JPanel();
        private final JLabel display = new JLabel();
        private final JButton up = new JButton("up");
        private final JButton down = new JButton("down");
        private final JButton stop = new JButton("stop");

        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize((int) (screenSize.getWidth() * WIDTH_PERC), (int) (screenSize.getHeight() * HEIGHT_PERC));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        canvas.add(display);
        canvas.add(up);
        canvas.add(down);
        canvas.add(stop);

        this.getContentPane().add(panel);
        this.setVisible(true);
    }


}
