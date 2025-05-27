import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ResultsWindow extends JFrame {
    public ResultsWindow(String winner, List<String> players, int[] lives) {
        setTitle("Результаты");
        setSize(500, 700);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        MemoryGame.setGameIcon(this);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(230, 240, 255));
        mainPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 255), 3),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        JLabel titleLabel = new JLabel("Игра окончена", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
        titleLabel.setForeground(new Color(80, 80, 160));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        StringBuilder sb = new StringBuilder();
        sb.append("<html><div style='background:#f0f8ff; padding:15px; border-radius:10px;'>");

        if (players.size() == 1) {
            sb.append("<h2 style='color:red; text-align:center;'>❌ Вы проиграли</h2>");
        } else {
            sb.append("<h2 style='color:#551A8B; text-align:center;'>🏆 Победитель:</h2>");
            sb.append("<div style='margin: 20px 0; padding: 20px; background-color: #fff3cd; border: 3px solid #ffeeba; border-radius: 15px; box-shadow: 0 0 15px rgba(0,0,0,0.1);'>");
            sb.append("<h1 style='text-align:center; color:#b30000; font-weight:bold; font-size:42px; text-shadow: 1px 1px 2px #ffdb4d;'>")
                    .append(winner)
                    .append("</h1>");
            sb.append("</div>");
        }

        sb.append("<hr>");

        for (int i = 0; i < players.size(); i++) {
            sb.append("<div style='margin:6px 0; font-size:16px;'>")
                    .append(players.get(i))
                    .append(": <b>").append(lives[i] > 0 ? lives[i] : "0").append("</b> жизней</div>");
        }

        sb.append("</div></html>");

        JEditorPane resultsText = new JEditorPane("text/html", sb.toString());
        resultsText.setEditable(false);
        resultsText.setBackground(new Color(230, 240, 255));
        resultsText.setOpaque(false);

        JScrollPane scrollPane = new JScrollPane(resultsText);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(new Color(230, 240, 255));

        JButton menuButton = createStyledButton("Главное меню", new Color(100, 150, 200));
        menuButton.addActionListener(e -> {
            dispose();
            new MainMenu().setVisible(true);
        });

        JButton exitButton = createStyledButton("Выход", new Color(220, 100, 100));
        exitButton.addActionListener(e -> System.exit(0));

        buttonPanel.add(menuButton);
        buttonPanel.add(exitButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(bgColor);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        return button;
    }
}