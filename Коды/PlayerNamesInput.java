import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class PlayerNamesInput extends JFrame {
    public PlayerNamesInput(int playersCount, int lives, int delayMs) {
        setTitle("Ввод имён игроков");
        setSize(550, 450);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(250, 240, 230));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel title = new JLabel("Введите имена игроков:", SwingConstants.CENTER);
        title.setFont(new Font("Comic Sans MS", Font.BOLD, 26));
        title.setForeground(new Color(120, 80, 60));
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        mainPanel.add(title, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setBackground(new Color(250, 240, 230));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 50));

        List<JTextField> nameFields = new ArrayList<>();
        for (int i = 1; i <= playersCount; i++) {
            JPanel rowPanel = new JPanel(new BorderLayout(10, 0));
            rowPanel.setBackground(new Color(250, 240, 230));

            JLabel label = new JLabel("Игрок " + i + ":");
            label.setFont(new Font("Arial", Font.BOLD, 16));
            label.setPreferredSize(new Dimension(100, 30));
            rowPanel.add(label, BorderLayout.WEST);

            JTextField field = new PlaceholderTextField("Игрок " + i);
            field.setFont(new Font("Arial", Font.PLAIN, 16));
            field.setMaximumSize(new Dimension(300, 30));
            nameFields.add(field);
            rowPanel.add(field, BorderLayout.CENTER);

            inputPanel.add(rowPanel);
            inputPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        JScrollPane scrollPane = new JScrollPane(inputPanel);
        scrollPane.getViewport().setBackground(new Color(250, 240, 230));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setBackground(new Color(250, 240, 230));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        JButton backButton = createButton("Назад", new Color(220, 120, 100));
        backButton.addActionListener(e -> {
            dispose();
            new GameSettings().setVisible(true);
        });

        JButton startButton = createButton("Начать игру", new Color(100, 180, 100));
        startButton.addActionListener(e -> {
            List<String> names = new ArrayList<>();
            boolean emptyName = false;

            for (JTextField field : nameFields) {
                String name = field.getText().trim();
                if (name.isEmpty()) {
                    emptyName = true;
                    break;
                }
                names.add(name);
            }

            if (emptyName) {
                JOptionPane.showMessageDialog(this, "Пожалуйста, заполните все имена.", "Ошибка", JOptionPane.WARNING_MESSAGE);
                return;
            }

            dispose();
            new GameWindow(names, lives, delayMs).setVisible(true);
        });

        buttonPanel.add(backButton);
        buttonPanel.add(startButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
        MemoryGame.setGameIcon(this);
    }

    private JButton createButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(bgColor);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(150, 40));
        return button;
    }

    static class PlaceholderTextField extends JTextField {
        private String placeholder;
        private boolean showingPlaceholder = true;

        public PlaceholderTextField(String placeholder) {
            this.placeholder = placeholder;
            setText(placeholder);
            setForeground(Color.GRAY);

            addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                    if (showingPlaceholder) {
                        setText("");
                        setForeground(Color.BLACK);
                        showingPlaceholder = false;
                    }
                }

                @Override
                public void focusLost(FocusEvent e) {
                    if (getText().trim().isEmpty()) {
                        setText(placeholder);
                        setForeground(Color.GRAY);
                        showingPlaceholder = true;
                    }
                }
            });

            getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
                public void changedUpdate(javax.swing.event.DocumentEvent e) { update(); }
                public void removeUpdate(javax.swing.event.DocumentEvent e) { update(); }
                public void insertUpdate(javax.swing.event.DocumentEvent e) { update(); }

                public void update() {
                    showingPlaceholder = getText().trim().isEmpty();
                }
            });
        }
    }
}