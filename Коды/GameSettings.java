import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameSettings extends JFrame {
    private JSpinner playersSpinner, livesSpinner, delaySpinner;

    public GameSettings() {
        setTitle("Настройки игры");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(240, 245, 250));

        JLabel title = new JLabel("Настройки игры", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setForeground(new Color(70, 70, 150));
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        mainPanel.add(title, BorderLayout.NORTH);

        JPanel settingsPanel = new JPanel(new GridLayout(3, 2, 15, 15));
        settingsPanel.setBackground(new Color(240, 245, 250));
        settingsPanel.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));

        playersSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 5, 1));
        livesSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
        delaySpinner = new JSpinner(new SpinnerNumberModel(0.5, 0.5, 3.0, 0.1));

        ((JSpinner.DefaultEditor) playersSpinner.getEditor()).getTextField().setText("");
        ((JSpinner.DefaultEditor) livesSpinner.getEditor()).getTextField().setText("");
        ((JSpinner.DefaultEditor) delaySpinner.getEditor()).getTextField().setText("");

        addSetting(settingsPanel, "Количество игроков:", playersSpinner);
        addSetting(settingsPanel, "Жизни у каждого:", livesSpinner);
        addSetting(settingsPanel, "Задержка (сек):", delaySpinner);

        mainPanel.add(settingsPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setBackground(new Color(240, 245, 250));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        JButton backButton = createButton("Назад", new Color(220, 120, 100), e -> returnToMenu());
        JButton startButton = createButton("Играть", new Color(100, 180, 100), e -> startGame());

        buttonPanel.add(backButton);
        buttonPanel.add(startButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
        MemoryGame.setGameIcon(this);
    }

    private void addSetting(JPanel panel, String text, JSpinner spinner) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(label);

        JPanel spinnerWrapper = new JPanel(new BorderLayout());
        spinnerWrapper.setBackground(new Color(240, 245, 250, 180));
        spinnerWrapper.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        spinner.setFont(new Font("Arial", Font.BOLD, 14));
        spinner.setEditor(new JSpinner.DefaultEditor(spinner));
        spinner.setOpaque(false);
        spinner.setBackground(new Color(255, 255, 255, 0));

        spinnerWrapper.add(spinner, BorderLayout.CENTER);
        panel.add(spinnerWrapper);
    }

    private JButton createButton(String text, Color bgColor, ActionListener action) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setBackground(bgColor);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(150, 40));
        button.addActionListener(action);
        return button;
    }

    private void startGame() {
        int playersCount = (Integer) playersSpinner.getValue();
        int livesCount = (Integer) livesSpinner.getValue();
        int delayMs = (int) ((Double) delaySpinner.getValue() * 1000);

        if (playersCount < 1 || livesCount < 1) {
            JOptionPane.showMessageDialog(this, "Введите корректные настройки!", "Ошибка", JOptionPane.WARNING_MESSAGE);
            return;
        }

        dispose();
        new PlayerNamesInput(playersCount, livesCount, delayMs).setVisible(true);
    }

    private void returnToMenu() {
        dispose();
        new MainMenu().setVisible(true);
    }
}