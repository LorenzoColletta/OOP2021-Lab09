package it.unibo.oop.lab.reactivegui03;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AnotherConcurrentGUI extends JFrame {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private static final double WIDTH_PERC = 0.2;
    private static final double HEIGHT_PERC = 0.1;

    private final JLabel  display;
    private final JButton up;
    private final JButton down;
    private final JButton stop;

    /**
     * 
     */
    public AnotherConcurrentGUI() {
        super();
        final JPanel canvas = new JPanel();
        display = new JLabel();
        up = new JButton("Up");
        down = new JButton("Down");
        stop = new JButton("Stop");

        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize((int) (screenSize.getWidth() * WIDTH_PERC), (int) (screenSize.getHeight() * HEIGHT_PERC));


        canvas.setLayout(new FlowLayout());
        canvas.add(display);
        canvas.add(up);
        canvas.add(down);
        canvas.add(stop);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.getContentPane().add(canvas);
        this.setVisible(true);


    }


}