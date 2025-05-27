import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class GameWindow extends JFrame {
    private final List<String> players;
    private final int[] lives;
    private final int delayMs;
    private final List<String> sequence = new ArrayList<>();
    private int currentPlayer = 0;
    private int round = 1;
    private boolean showingSequence = true;
    private JLabel roundLabel, turnLabel;
    private JEditorPane playersInfo;
    private JTextField inputField;
    private JPanel colorPanel;
    private final String[] COLORS = {"Красный", "Зеленый", "Синий", "Желтый"};
    private final Color[] COLOR_VALUES = {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW};
    private final Font BIG_FONT = new Font("Comic Sans MS", Font.BOLD, 42);
    private final Font SMALL_FONT = new Font("Arial", Font.PLAIN, 16);

    public GameWindow(List<String> players, int initialLives, int delayMs) {
        this.players = players;
        this.delayMs = delayMs;
        this.lives = new int[players.size()];
        Arrays.fill(this.lives, initialLives);
        setupUI();
        showSequence();
        MemoryGame.setGameIcon(this);
    }

    private void setupUI() {
        setTitle("Запомни последовательность");
        setSize(1200, 650);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(240, 240, 250));

        JPanel topPanel = new JPanel(new GridLayout(1, 2));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        topPanel.setBackground(new Color(220, 220, 240));

        roundLabel = new JLabel("Раунд: " + round, SwingConstants.LEFT);
        roundLabel.setFont(new Font("Arial", Font.BOLD, 20));
        roundLabel.setForeground(new Color(80, 80, 160));

        turnLabel = new JLabel("Ход игрока: " + players.get(currentPlayer), SwingConstants.RIGHT);
        turnLabel.setFont(new Font("Arial", Font.BOLD, 20));
        turnLabel.setForeground(new Color(80, 80, 160));

        topPanel.add(roundLabel);
        topPanel.add(turnLabel);
        add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        centerPanel.setBackground(new Color(250, 250, 255));

        colorPanel = new JPanel();
        colorPanel.setPreferredSize(new Dimension(300, 300));
        colorPanel.setBackground(Color.WHITE);
        colorPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 220), 4));

        centerPanel.add(colorPanel, BorderLayout.CENTER);

        inputField = new JTextField(20);
        inputField.setFont(SMALL_FONT);
        inputField.setToolTipText("Введите цвета через пробел или запятую (например: Красный Зелёный)");
        inputField.addActionListener(e -> checkAnswer());

        JButton submitButton = createControlButton("Подтвердить", new Color(100, 180, 100));
        submitButton.addActionListener(e -> checkAnswer());

        JButton hintButton = createControlButton("Подсказка", new Color(100, 150, 200));
        hintButton.addActionListener(e -> showHint());

        JButton menuButton = createControlButton("Меню", new Color(220, 120, 100));
        menuButton.addActionListener(e -> returnToMenu());

        JPanel inputPanel = new JPanel();
        inputPanel.setBackground(new Color(250, 250, 255));
        inputPanel.add(inputField);
        inputPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        inputPanel.add(submitButton);
        inputPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        inputPanel.add(hintButton);
        inputPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        inputPanel.add(menuButton);

        centerPanel.add(inputPanel, BorderLayout.SOUTH);
        add(centerPanel, BorderLayout.CENTER);

        playersInfo = new JEditorPane("text/html", "");
        playersInfo.setEditable(false);
        playersInfo.setBackground(new Color(230, 240, 255));
        playersInfo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 210, 220), 2),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        updatePlayersInfo();

        JScrollPane scrollPane = new JScrollPane(playersInfo);
        add(scrollPane, BorderLayout.EAST);
    }

    private JButton createControlButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(SMALL_FONT.deriveFont(Font.BOLD, 14));
        button.setBackground(bgColor);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        return button;
    }

    private void updatePlayersInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append("<html><table style='width:100%; border-collapse: collapse;'>");
        sb.append("<tr style='background-color:#d0e0f0;'>");
        sb.append("<th style='text-align:left; padding:6px;'>Игрок</th>");
        sb.append("<th style='text-align:right; padding:6px;'>Жизни</th></tr>");

        for (int i = 0; i < players.size(); i++) {
            String player = players.get(i);
            int life = lives[i];
            sb.append("<tr>");
            sb.append("<td style='padding:4px;'>" + player + "</td>");
            sb.append("<td style='text-align:right; padding:4px;'>" + life + "</td>");
            sb.append("</tr>");
        }

        sb.append("</table></html>");
        playersInfo.setText(sb.toString());
    }

    private void showSequence() {
        showingSequence = true;
        inputField.setEnabled(false);
        String newColor = COLORS[new Random().nextInt(COLORS.length)];
        sequence.add(newColor);

        new Thread(() -> {
            try {
                for (String color : sequence) {
                    SwingUtilities.invokeLater(() -> showColor(color));
                    Thread.sleep(delayMs);
                    SwingUtilities.invokeLater(this::hideColor);
                    Thread.sleep(500);
                }
                SwingUtilities.invokeLater(() -> {
                    showingSequence = false;
                    currentPlayer = 0;
                    turnLabel.setText("Ход игрока: " + players.get(currentPlayer));
                    inputField.setEnabled(true);
                    inputField.requestFocus();
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void showColor(String color) {
        int index = Arrays.asList(COLORS).indexOf(color);
        colorPanel.setBackground(COLOR_VALUES[index]);
        JLabel label = new JLabel(color, SwingConstants.CENTER);
        label.setFont(BIG_FONT);
        label.setForeground(Color.WHITE);
        colorPanel.removeAll();
        colorPanel.add(label);
        colorPanel.revalidate();
        colorPanel.repaint();
    }

    private void hideColor() {
        colorPanel.setBackground(Color.WHITE);
        colorPanel.removeAll();
        colorPanel.revalidate();
        colorPanel.repaint();
    }

    private void checkAnswer() {
        if (showingSequence) return;
        String input = inputField.getText().trim();
        if (input.isEmpty()) {
            showMessage("Введите последовательность!", "Ошибка", true);
            return;
        }

        String[] userInput = input.split("[\\s,]+");
        boolean isCorrect = userInput.length == sequence.size();
        if (isCorrect) {
            for (int i = 0; i < sequence.size(); i++) {
                if (!userInput[i].equalsIgnoreCase(sequence.get(i))) {
                    isCorrect = false;
                    break;
                }
            }
        }

        inputField.setText("");
        if (isCorrect) {
            showMessage("Правильно!", "Успех", false);
            nextTurn();
        } else {
            lives[currentPlayer]--;
            showMessage("<html><div style='text-align:center;'>Ошибка! -1 жизнь<br>Ожидалось: " +
                            String.join(" ", sequence) + "</div></html>",
                    "Ошибка", true);
            updatePlayersInfo();
            if (lives[currentPlayer] <= 0) {
                showMessage("<html><div style='text-align:center;'>" + players.get(currentPlayer) + " выбыл!</div></html>",
                        "Игрок выбыл", true);
            }
            nextTurn();
        }
    }

    private void showMessage(String text, String title, boolean isError) {
        JOptionPane.showMessageDialog(this,
                text,
                title,
                isError ? JOptionPane.WARNING_MESSAGE : JOptionPane.INFORMATION_MESSAGE);
    }

    private void showHint() {
        if (!sequence.isEmpty()) {
            String lastColor = sequence.get(sequence.size() - 1);
            JOptionPane.showMessageDialog(this,
                    "<html><div style='text-align:center;'>Последний цвет: <b>" + lastColor + "</b></div></html>",
                    "Подсказка",
                    JOptionPane.PLAIN_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this,
                    "Нет последовательности для подсказки.",
                    "Ошибка",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    private void nextTurn() {
        currentPlayer++;
        if (currentPlayer >= players.size()) {
            round++;
            roundLabel.setText("Раунд: " + round);
            currentPlayer = 0;
            showSequence();
        } else {
            if (lives[currentPlayer] > 0) {
                turnLabel.setText("Ход игрока: " + players.get(currentPlayer));
                inputField.requestFocus();
            } else {
                nextTurn();
            }
        }
        if (hasGameEnded()) endGame();
    }

    private boolean hasGameEnded() {
        if (players.size() == 1) return lives[0] <= 0;
        int alive = 0;
        for (int life : lives) if (life > 0) alive++;
        return alive <= 1;
    }

    private void endGame() {
        String winner = "Никто";
        if (players.size() == 1) winner = players.get(0);
        else {
            int maxScore = -1;
            for (int i = 0; i < players.size(); i++) {
                if (lives[i] > 0 && sequence.size() > maxScore) {
                    maxScore = sequence.size();
                    winner = players.get(i);
                }
            }
        }
        dispose();
        new ResultsWindow(winner, players, lives);
    }

    private void returnToMenu() {
        dispose();
        new MainMenu().setVisible(true);
    }
}