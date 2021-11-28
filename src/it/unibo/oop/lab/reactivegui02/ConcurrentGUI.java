package it.unibo.oop.lab.reactivegui02;

import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import it.unibo.oop.lab.reactivegui01.ConcurrentGUI.Agent;



public class ConcurrentGUI extends JFrame {

    private static final double WIDTH_PERC = 0.2;
    private static final double HEIGHT_PERC = 0.1;

    private final JLabel  display;
    private final JButton up;
    private final JButton down;
    private final JButton stop;

    public ConcurrentGUI() throws HeadlessException {
        super();
        final JPanel canvas = new JPanel();

        display = new JLabel();
        up = new JButton("up");
        down = new JButton("down"); 
        stop = new JButton("stop"); 

        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize((int) (screenSize.getWidth() * WIDTH_PERC), (int) (screenSize.getHeight() * HEIGHT_PERC));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        canvas.add(display);
        canvas.add(up);
        canvas.add(down);
        canvas.add(stop);

        final Agent agent = new Agent();
        new Thread(agent).start();

        stop.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                agent.stop();
            }
        });
        up.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e1) {
                agent.up();
            }
        });
        down.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e2) {
                agent.down();
            }
        });

        this.getContentPane().add(canvas);
        this.setVisible(true);
    }

    private class Agent implements Runnable {
        static final int INCREMENT = +1;
        static final int DECREMENT = -1;

        private volatile boolean stop;
        private volatile int counter;
        private int add;

        Agent() {
            this.add = INCREMENT;
            this.counter = 0;
            this.stop = false;
        }

        @Override
        public void run() {
            while (!this.stop) {
                try {
                    SwingUtilities.invokeAndWait(new Runnable() {
                        @Override
                        public void run() {
                            ConcurrentGUI.this.display.setText(Integer.toString(Agent.this.counter));
                        }
                    });
                    counter += this.add;
                } catch (InvocationTargetException | InterruptedException ex) {
                    ex.printStackTrace();
                }

            }
        }

        public void up() {
            this.add = INCREMENT;
        }

        public void down() {
            this.add = DECREMENT;
        }

        public void stop() {
            this.stop = true;
        }

    }


}
