import javax.swing.*;
import java.awt.*;

public class MainMenu extends JFrame {
    public MainMenu() {
        setTitle("Запомни последовательность");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 450);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new GradientPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 20, 40, 20));

        JLabel title = new JLabel("Главное меню", SwingConstants.CENTER);
        title.setFont(new Font("Comic Sans MS", Font.BOLD, 36));
        title.setForeground(new Color(50, 50, 120));
        title.setAlignmentX(CENTER_ALIGNMENT);
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        mainPanel.add(title);

        String[] buttons = {"Начать игру", "Правила игры", "Выход"};
        Color[] colors = {new Color(100, 200, 100), new Color(100, 150, 200), new Color(200, 100, 100)};

        for (int i = 0; i < buttons.length; i++) {
            JButton button = createMenuButton(buttons[i], colors[i]);
            button.setAlignmentX(CENTER_ALIGNMENT);

            switch (i) {
                case 0:
                    button.addActionListener(e -> openGameSettings());
                    break;
                case 1:
                    button.addActionListener(e -> showRules());
                    break;
                case 2:
                    button.addActionListener(e -> System.exit(0));
                    break;
            }

            mainPanel.add(button);
            if (i < buttons.length - 1) {
                mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
            }
        }

        add(mainPanel);
        MemoryGame.setGameIcon(this);
    }

    private JButton createMenuButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 20));
        button.setBackground(bgColor);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(250, 50));
        button.setMaximumSize(new Dimension(250, 50));
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE, 2),
                BorderFactory.createEmptyBorder(5, 25, 5, 25)
        ));
        return button;
    }

    private void openGameSettings() {
        dispose();
        new GameSettings().setVisible(true);
    }

    private void showRules() {
        String rules = "<html><div style='width: 350px; font-size: 14pt;'>"
                + "<h1 style='color: #336699; text-align: center;'>Правила игры</h1>"
                + "<p style='text-align: left; margin-left: 20px;'>1. Запоминайте последовательность цветов.</p>"
                + "<p style='text-align: left; margin-left: 20px;'>2. Вводите цвета в том же порядке.</p>"
                + "<p style='text-align: left; margin-left: 20px;'>3. За правильный ответ продолжаете дальше.</p>"
                + "<p style='text-align: left; margin-left: 20px;'>4. За ошибку теряете одну жизнь.</p>"
                + "<p style='text-align: left; margin-left: 20px;'>5. Игра продолжается, пока в живых остаётся только один игрок или вы не потеряете все жизни.</p>"
                + "<p style='color: #993366; text-align: center; margin-top: 20px;'><b>Удачи!</b></p>"
                + "</div></html>";

        JOptionPane.showMessageDialog(this, rules, "Правила игры", JOptionPane.PLAIN_MESSAGE);
    }

    static class GradientPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            GradientPaint gp = new GradientPaint(0, 0, new Color(230, 240, 255),
                    getWidth(), getHeight(), new Color(255, 240, 230));
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }
    }
}