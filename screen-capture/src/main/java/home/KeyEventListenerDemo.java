package home;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyEventListenerDemo {
    private static class KeyEventDemo extends JFrame implements KeyListener, ActionListener {
        private final JTextArea displayArea;
        private final JTextField typingArea;

        KeyEventDemo(String title) throws HeadlessException {
            super(title);
            displayArea = new JTextArea();
            displayArea.setEditable(false);
            typingArea = new JTextField(20);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            displayArea.setText("");
            typingArea.setText("");

            //Return the focus to the typing area.
            typingArea.requestFocusInWindow();
        }

        @Override
        public void keyTyped(KeyEvent e) {
            System.out.printf("keyTyped: keyCode: %d, extendedKeyCode: %d, actionKey: %s, %s, %s, %s\n",
                    e.getKeyCode(), e.getExtendedKeyCode(), e.isActionKey(),
                    e.isAltDown() ? "ALT" : "",
                    e.isControlDown() ? "CTRL" : "",
                    e.isShiftDown() ? "SHFT" : "");
        }

        @Override
        public void keyPressed(KeyEvent e) {
            System.out.printf("keyPressed: keyCode: %d, extendedKeyCode: %d, actionKey: %s, %s, %s, %s\n",
                    e.getKeyCode(), e.getExtendedKeyCode(), e.isActionKey(),
                    e.isAltDown() ? "ALT" : "",
                    e.isControlDown() ? "CTRL" : "",
                    e.isShiftDown() ? "SHFT" : "");
        }

        @Override
        public void keyReleased(KeyEvent e) {
            System.out.printf("keyReleased: keyCode: %d, extendedKeyCode: %d, actionKey: %s, %s, %s, %s\n",
                    e.getKeyCode(), e.getExtendedKeyCode(), e.isActionKey(),
                    e.isAltDown() ? "ALT" : "",
                    e.isControlDown() ? "CTRL" : "",
                    e.isShiftDown() ? "SHFT" : "");
        }

        void addComponentsToPane() {
            JButton button = new JButton("Clear");
            button.addActionListener(this);

            typingArea.addKeyListener(this);

            // Uncomment this if you wish to turn off focus traversal.  The focus subsystem consumes focus traversal
            // keys, such as Tab and Shift Tab.
            // If you uncomment the following line of code, this disables focus traversal and the Tab events will
            // become available to the key event listener.
            //typingArea.setFocusTraversalKeysEnabled(false);

            JScrollPane scrollPane = new JScrollPane(displayArea);
            scrollPane.setPreferredSize(new Dimension(375, 125));

            getContentPane().add(typingArea, BorderLayout.PAGE_START);
            getContentPane().add(scrollPane, BorderLayout.CENTER);
            getContentPane().add(button, BorderLayout.PAGE_END);
        }
    }

    public static void main(String[] args) throws Exception {
        UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        UIManager.put("swing.boldMetal", Boolean.FALSE);
        SwingUtilities.invokeLater(() -> {
            KeyEventDemo frame = new KeyEventDemo("KeyEventDemo");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.addComponentsToPane();
            frame.pack();
            frame.setVisible(true);
        });
    }
}
