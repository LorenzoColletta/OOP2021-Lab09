package it.unibo.oop.lab.reactivegui03;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 */
public class AnotherConcurrentGUI extends JFrame {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private static final double WIDTH_PERC = 0.2;
    private static final double HEIGHT_PERC = 0.1;
    private static final int TIME_TO_SHUTDOWN = 10_000;

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

        final Agent agent = new Agent();
        new Thread(agent).start();

        stop.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                agent.stop();
                up.setEnabled(false);
                down.setEnabled(false);
                stop.setEnabled(false);
            }
        });

        up.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e1) {
                agent.up();
            }
        });

        down.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e1) {
                agent.down();
            }
        });

        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(TIME_TO_SHUTDOWN);
                    SwingUtilities.invokeAndWait(new Runnable() {
                        @Override
                        public void run() {
                            AnotherConcurrentGUI.this.stop.doClick();
                        }
                    });
                } catch (InterruptedException | InvocationTargetException e) {
                    e.printStackTrace();
                }

            }

        }).start();

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.getContentPane().add(canvas);
        this.setVisible(true);
    }

    private class Agent implements Runnable {

        private volatile int counter;
        private volatile boolean stopCounter;
        private boolean up;

        Agent() {
            up = true;
            this.counter = 0;
            this.stopCounter = false;
        }

        @Override
        public void run() {
            while (!stopCounter) {
                try {
                    SwingUtilities.invokeAndWait(new Runnable() {
                        @Override
                        public void run() {
                            AnotherConcurrentGUI.this.display.setText("" + counter);
                        }
                    });
                    this.counter += up ? +1 : -1;
                    Thread.sleep(100);
                } catch (InvocationTargetException | InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }

        public void up() {
            this.up = true;
        }

        public void down() {
            this.up = false;
        }

        public void stop() {
            this.stopCounter = true;
        }

    }

}