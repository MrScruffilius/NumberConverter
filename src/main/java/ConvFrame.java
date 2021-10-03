import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.TimeUnit;

public class ConvFrame extends JFrame {

    public final static int WIDTH = 290;
    public final static int HEIGHT = 220;

    public JTextPane[] expl = new JTextPane[5];

    public JTextPane[] field = new JTextPane[5];

    private int edit = 0;

    private int offset;

    public ConvFrame() throws HeadlessException {
        this.getContentPane().setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.pack();
        offset = this.getWidth() - WIDTH;
        this.setResizable(true);
        this.setLayout(null);


        for (int i = 0; i < 5; i++) {

            expl[i] = new JTextPane();
            expl[i].setSize(50, 20);
            expl[i].setLocation(20, ((((i + 1) * 2) - 1) * 20));
            expl[i].setBackground(Color.white);
            expl[i].setFocusable(false);


            StyledDocument doc = expl[i].getStyledDocument();
            SimpleAttributeSet center = new SimpleAttributeSet();
            StyleConstants.setAlignment(center, StyleConstants.ALIGN_RIGHT);
            doc.setParagraphAttributes(0, doc.getLength(), center, false);

            this.add(expl[i]);

        }


        expl[0].setText("dec ");
        expl[1].setText("hex ");
        expl[2].setText("bin ");
        expl[3].setText("oct ");
        expl[4].setText("err:");


        for (int i = 0; i < 5; i++) {

            field[i] = new JTextPane();
            field[i].setSize(200, 20);
            field[i].setLocation(70, ((((i + 1) * 2) - 1) * 20));
            field[i].setBackground(Color.white);


            StyledDocument doc = field[i].getStyledDocument();
            SimpleAttributeSet center = new SimpleAttributeSet();
            StyleConstants.setAlignment(center, StyleConstants.ALIGN_RIGHT);
            doc.setParagraphAttributes(0, doc.getLength(), center, false);

            this.add(field[i]);
        }

        field[4].setBackground(Color.green);
        field[4].setEditable(false);

        Thread th = new Thread(new UpdateRunner());
        th.start();


        field[0].addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                edit = 0;
            }
        });
        field[1].addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                edit = 1;
            }
        });
        field[2].addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                edit = 2;
            }
        });
        field[3].addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                edit = 3;
            }
        });

        this.addComponentListener(new ResizeList(this));


        this.setVisible(true);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    public void update() {
        switch (edit) {
            case 0 -> {
                String inp = field[0].getText();
                int out;
                try {
                    out = Integer.parseInt(inp, 10);
                    field[1].setText(Integer.toHexString(out));
                    field[2].setText(Integer.toBinaryString(out));
                    field[3].setText(Integer.toOctalString(out));
                    field[4].setBackground(Color.green);
                } catch (NumberFormatException e) {
                    field[4].setBackground(Color.red);
                }
            }
            case 1 -> {
                String inp = field[1].getText();
                int out;
                try {
                    out = Integer.parseInt(inp, 16);
                    field[0].setText(Integer.toString(out));
                    field[2].setText(Integer.toBinaryString(out));
                    field[3].setText(Integer.toOctalString(out));
                    field[4].setBackground(Color.green);
                } catch (NumberFormatException e) {
                    field[4].setBackground(Color.red);
                }

            }
            case 2 -> {
                String inp = field[2].getText();
                int out;
                try {
                    out = Integer.parseInt(inp, 2);
                    field[0].setText(Integer.toString(out));
                    field[1].setText(Integer.toHexString(out));
                    field[3].setText(Integer.toOctalString(out));
                    field[4].setBackground(Color.green);
                } catch (NumberFormatException e) {
                    field[4].setBackground(Color.red);
                }

            }
            case 3 -> {
                String inp = field[3].getText();
                int out;
                try {
                    out = Integer.parseInt(inp, 8);
                    field[0].setText(Integer.toString(out));
                    field[2].setText(Integer.toBinaryString(out));
                    field[1].setText(Integer.toHexString(out));
                    field[4].setBackground(Color.green);
                } catch (NumberFormatException e) {
                    field[4].setBackground(Color.red);
                }

            }

        }
    }


    class UpdateRunner implements Runnable {

        private final static int REFRESH_RATE = 60;

        @Override
        public void run() {
            while (true) {
                try {
                    TimeUnit.MILLISECONDS.sleep(1000 / REFRESH_RATE);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                update();
            }
        }
    }

    class ResizeList implements ComponentListener {

        public ConvFrame frame;

        public ResizeList(ConvFrame frame) {
            this.frame = frame;
        }

        @Override
        public void componentResized(ComponentEvent e) {
            int newWidth = frame.getWidth() - offset;
            frame.getContentPane().setPreferredSize(new Dimension(newWidth, HEIGHT));
            frame.pack();

            for (int i = 0; i < 5; i++) {
                frame.expl[i].setSize(50, 20);
                frame.expl[i].setLocation(20, ((((i + 1) * 2) - 1) * 20));
            }

            for (int i = 0; i < 5; i++) {

                field[i].setSize(newWidth - 90, 20);
                field[i].setLocation(70, ((((i + 1) * 2) - 1) * 20));

            }


        }

        @Override
        public void componentMoved(ComponentEvent e) {

        }

        @Override
        public void componentShown(ComponentEvent e) {

        }

        @Override
        public void componentHidden(ComponentEvent e) {

        }
    }


    public static void main(String[] args) {
        new ConvFrame();
    }
}
