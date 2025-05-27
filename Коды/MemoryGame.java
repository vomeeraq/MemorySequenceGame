import javax.swing.*;
import java.awt.*;

public class MemoryGame {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                MainMenu menu = new MainMenu();
                setGameIcon(menu);
                menu.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public static void setGameIcon(JFrame frame) {
        try {
            frame.setIconImage(Toolkit.getDefaultToolkit().getImage(MemoryGame.class.getResource("/resources/icon.png")));
        } catch (Exception e) {
            System.err.println("❌ Не удалось загрузить иконку");
        }
    }
}