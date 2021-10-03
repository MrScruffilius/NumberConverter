import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.*;


public class ConvFrame extends JFrame {

    public final static int WIDTH = 290;
    public final static int HEIGHT = 220;

    public JTextPane[] expl = new JTextPane[5];

    public JTextPane[] field = new JTextPane[4];
    public JButton errButton = new JButton();

    private int buttonCounter = 0;

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


        for (int i = 0; i < 4; i++) {

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


        errButton.setSize(200, 20);
        errButton.setLocation(70, (9 * 20));
        this.add(errButton);

        errButton.setBackground(Color.green);


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


        for (int i = 0; i < 4; i++) {
            field[i].addKeyListener(new KeyUpdater());
        }


        errButton.setText("uns. int");
        errButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (buttonCounter) {

                    case 0 -> {
                        buttonCounter = 1;
                        errButton.setText("int");
                    }
                    case 1 -> {
                        buttonCounter = 2;
                        errButton.setText("uns. long");
                    }
                    case 2 -> {
                        buttonCounter = 3;
                        errButton.setText("long");
                    }
                    case 3 -> {
                        buttonCounter = 4;
                        errButton.setText("double");
                        field[1].setEditable(false);
                        field[2].setEditable(false);
                        field[3].setEditable(false);
                    }
                    case 4 -> {
                        buttonCounter = 5;
                        errButton.setText("float");
                    }
                    case 5 -> {
                        buttonCounter = 0;
                        errButton.setText("uns. int");
                        field[1].setEditable(true);
                        field[2].setEditable(true);
                        field[3].setEditable(true);
                    }
                }
                update();
            }
        });

        this.addComponentListener(new ResizeList(this));


        this.setVisible(true);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    public void update() {
        switch (buttonCounter) {

            case 0 -> {
                switch (edit) {
                    case 0 -> {
                        String inp = field[0].getText();
                        int out;
                        try {
                            out = Integer.parseUnsignedInt(inp, 10);
                            field[1].setText(Integer.toHexString(out));
                            field[2].setText(Integer.toBinaryString(out));
                            field[3].setText(Integer.toOctalString(out));
                            errButton.setBackground(Color.green);
                        } catch (NumberFormatException e) {
                            errButton.setBackground(Color.red);
                        }
                    }
                    case 1 -> {
                        String inp = field[1].getText();
                        int out;
                        try {
                            out = Integer.parseUnsignedInt(inp, 16);
                            field[0].setText(Integer.toUnsignedString(out));
                            field[2].setText(Integer.toBinaryString(out));
                            field[3].setText(Integer.toOctalString(out));
                            errButton.setBackground(Color.green);
                        } catch (NumberFormatException e) {
                            errButton.setBackground(Color.red);
                        }

                    }
                    case 2 -> {
                        String inp = field[2].getText();
                        int out;
                        try {
                            out = Integer.parseUnsignedInt(inp, 2);
                            field[0].setText(Integer.toUnsignedString(out));
                            field[1].setText(Integer.toHexString(out));
                            field[3].setText(Integer.toOctalString(out));
                            errButton.setBackground(Color.green);
                        } catch (NumberFormatException e) {
                            errButton.setBackground(Color.red);
                        }

                    }
                    case 3 -> {
                        String inp = field[3].getText();
                        int out;
                        try {
                            out = Integer.parseUnsignedInt(inp, 8);
                            field[0].setText(Integer.toUnsignedString(out));
                            field[2].setText(Integer.toBinaryString(out));
                            field[1].setText(Integer.toHexString(out));
                            errButton.setBackground(Color.green);
                        } catch (NumberFormatException e) {
                            errButton.setBackground(Color.red);
                        }

                    }

                }
            }
            case 1 -> {
                switch (edit) {
                    case 0 -> {
                        String inp = field[0].getText();
                        int out;
                        try {
                            out = Integer.parseInt(inp, 10);
                            field[1].setText(Integer.toHexString(out));
                            field[2].setText(Integer.toBinaryString(out));
                            field[3].setText(Integer.toOctalString(out));
                            errButton.setBackground(Color.green);
                        } catch (NumberFormatException e) {
                            errButton.setBackground(Color.red);
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
                            errButton.setBackground(Color.green);
                        } catch (NumberFormatException e) {
                            errButton.setBackground(Color.red);
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
                            errButton.setBackground(Color.green);
                        } catch (NumberFormatException e) {
                            errButton.setBackground(Color.red);
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
                            errButton.setBackground(Color.green);
                        } catch (NumberFormatException e) {
                            errButton.setBackground(Color.red);
                        }

                    }

                }
            }
            case 2 -> {
                switch (edit) {
                    case 0 -> {
                        String inp = field[0].getText();
                        long out;
                        try {
                            out = Long.parseUnsignedLong(inp, 10);
                            field[1].setText(Long.toHexString(out));
                            field[2].setText(Long.toBinaryString(out));
                            field[3].setText(Long.toOctalString(out));
                            errButton.setBackground(Color.green);
                        } catch (NumberFormatException e) {
                            errButton.setBackground(Color.red);
                        }
                    }
                    case 1 -> {
                        String inp = field[1].getText();
                        long out;
                        try {
                            out = Long.parseUnsignedLong(inp, 16);
                            field[0].setText(Long.toUnsignedString(out));
                            field[2].setText(Long.toBinaryString(out));
                            field[3].setText(Long.toOctalString(out));
                            errButton.setBackground(Color.green);
                        } catch (NumberFormatException e) {
                            errButton.setBackground(Color.red);
                        }

                    }
                    case 2 -> {
                        String inp = field[2].getText();
                        long out;
                        try {
                            out = Long.parseUnsignedLong(inp, 2);
                            field[0].setText(Long.toUnsignedString(out));
                            field[1].setText(Long.toHexString(out));
                            field[3].setText(Long.toOctalString(out));
                            errButton.setBackground(Color.green);
                        } catch (NumberFormatException e) {
                            errButton.setBackground(Color.red);
                        }

                    }
                    case 3 -> {
                        String inp = field[3].getText();
                        long out;
                        try {
                            out = Long.parseUnsignedLong(inp, 8);
                            field[0].setText(Long.toUnsignedString(out));
                            field[2].setText(Long.toBinaryString(out));
                            field[1].setText(Long.toHexString(out));
                            errButton.setBackground(Color.green);
                        } catch (NumberFormatException e) {
                            errButton.setBackground(Color.red);
                        }

                    }

                }
            }
            case 3 -> {
                switch (edit) {
                    case 0 -> {
                        String inp = field[0].getText();
                        long out;
                        try {
                            out = Long.parseLong(inp, 10);
                            field[1].setText(Long.toHexString(out));
                            field[2].setText(Long.toBinaryString(out));
                            field[3].setText(Long.toOctalString(out));
                            errButton.setBackground(Color.green);
                        } catch (NumberFormatException e) {
                            errButton.setBackground(Color.red);
                        }
                    }
                    case 1 -> {
                        String inp = field[1].getText();
                        long out;
                        try {
                            out = Long.parseLong(inp, 16);
                            field[0].setText(Long.toString(out));
                            field[2].setText(Long.toBinaryString(out));
                            field[3].setText(Long.toOctalString(out));
                            errButton.setBackground(Color.green);
                        } catch (NumberFormatException e) {
                            errButton.setBackground(Color.red);
                        }

                    }
                    case 2 -> {
                        String inp = field[2].getText();
                        long out;
                        try {
                            out = Long.parseLong(inp, 2);
                            field[0].setText(Long.toString(out));
                            field[1].setText(Long.toHexString(out));
                            field[3].setText(Long.toOctalString(out));
                            errButton.setBackground(Color.green);
                        } catch (NumberFormatException e) {
                            errButton.setBackground(Color.red);
                        }

                    }
                    case 3 -> {
                        String inp = field[3].getText();
                        long out;
                        try {
                            out = Long.parseLong(inp, 8);
                            field[0].setText(Long.toString(out));
                            field[2].setText(Long.toBinaryString(out));
                            field[1].setText(Long.toHexString(out));
                            errButton.setBackground(Color.green);
                        } catch (NumberFormatException e) {
                            errButton.setBackground(Color.red);
                        }

                    }

                }
            }
            case 4 -> {

                String inp = field[0].getText();
                double out;
                try {
                    out = Double.parseDouble(inp);
                    field[1].setText(Double.toHexString(out));
                    field[2].setText(Long.toBinaryString(Double.doubleToLongBits(out)));
                    field[3].setText(Long.toOctalString(Double.doubleToLongBits(out)));
                    errButton.setBackground(Color.green);
                } catch (NumberFormatException e) {
                    errButton.setBackground(Color.red);
                }
            }
            case 5 -> {
                String inp = field[0].getText();
                float out;
                try {
                    out = Float.parseFloat(inp);
                    field[1].setText(Float.toHexString(out));
                    field[2].setText(Integer.toBinaryString(Float.floatToIntBits(out)));
                    field[3].setText(Integer.toOctalString(Float.floatToIntBits(out)));
                    errButton.setBackground(Color.green);
                } catch (NumberFormatException e) {
                    errButton.setBackground(Color.red);
                }
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
            if (newWidth < WIDTH) {
                newWidth = WIDTH;
            }
            frame.getContentPane().setPreferredSize(new Dimension(newWidth, HEIGHT));
            frame.pack();

            for (int i = 0; i < 5; i++) {
                frame.expl[i].setSize(50, 20);
                frame.expl[i].setLocation(20, ((((i + 1) * 2) - 1) * 20));
            }

            for (int i = 0; i < 4; i++) {

                field[i].setSize(newWidth - 90, 20);
                field[i].setLocation(70, ((((i + 1) * 2) - 1) * 20));

            }

            errButton.setSize(newWidth - 90, 20);
            errButton.setLocation(70, (9 * 20));


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


    class KeyUpdater implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {

        }

        @Override
        public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_MINUS || e.getKeyCode() == KeyEvent.VK_PLUS || e.getKeyCode() == KeyEvent.VK_0 || e.getKeyCode() == KeyEvent.VK_1 || e.getKeyCode() == KeyEvent.VK_2 || e.getKeyCode() == KeyEvent.VK_3 || e.getKeyCode() == KeyEvent.VK_4 || e.getKeyCode() == KeyEvent.VK_5 || e.getKeyCode() == KeyEvent.VK_6 || e.getKeyCode() == KeyEvent.VK_7 || e.getKeyCode() == KeyEvent.VK_8 || e.getKeyCode() == KeyEvent.VK_9 || e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_B || e.getKeyCode() == KeyEvent.VK_C || e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_E || e.getKeyCode() == KeyEvent.VK_F || e.getKeyCode() == KeyEvent.VK_V) {
                update();
            }
        }
    }


    public static void main(String[] args) {
        new ConvFrame();
    }
}

