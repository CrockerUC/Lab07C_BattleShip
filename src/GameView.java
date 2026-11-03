import javax.swing.*;
import java.awt.*;

public class GameView extends JFrame
{
    private static final int BOARD_SIZE = 10;

    private final GameController controller;
    private final JButton[][] buttons;

    private final JLabel missLabel;
    private final JLabel strikeLabel;
    private final JLabel totalMissLabel;
    private final JLabel totalHitLabel;

    public GameView(GameController controller)
    {
        this.controller = controller;
        buttons = new JButton[BOARD_SIZE][BOARD_SIZE];

        setTitle("Battleship");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel counterPanel = new JPanel(new GridLayout(2, 2, 10, 5));

        missLabel = new JLabel("MISS: 0 / 5");
        strikeLabel = new JLabel("STRIKE: 0 / 3");
        totalMissLabel = new JLabel("TOTAL MISS: 0 / 83");
        totalHitLabel = new JLabel("TOTAL HIT: 0 / 17");

        counterPanel.add(missLabel);
        counterPanel.add(strikeLabel);
        counterPanel.add(totalMissLabel);
        counterPanel.add(totalHitLabel);

        add(counterPanel, BorderLayout.NORTH);

        JPanel boardPanel = new JPanel(
                new GridLayout(BOARD_SIZE, BOARD_SIZE, 2, 2));

        boardPanel.setBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10));

        for (int row = 0; row < BOARD_SIZE; row++)
        {
            for (int col = 0; col < BOARD_SIZE; col++)
            {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(45, 45));
                button.setBackground(Color.CYAN);
                button.setOpaque(true);

                final int r = row;
                final int c = col;

                button.addActionListener(e -> controller.fireAt(r, c));

                buttons[row][col] = button;
                boardPanel.add(button);
            }
        }

        add(boardPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout());

        JButton playAgainButton = new JButton("Play Again");
        playAgainButton.addActionListener(e -> controller.playAgain());

        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(e -> controller.quitGame());

        bottomPanel.add(playAgainButton);
        bottomPanel.add(quitButton);

        add(bottomPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void createBoard()
    {
        for (int row = 0; row < BOARD_SIZE; row++)
        {
            for (int col = 0; col < BOARD_SIZE; col++)
            {
                buttons[row][col].setText("");
                buttons[row][col].setBackground(Color.CYAN);
                buttons[row][col].setForeground(Color.BLACK);
                buttons[row][col].setEnabled(true);
            }
        }
    }

    public void updateCell(int row, int col, boolean hit)
    {
        JButton button = buttons[row][col];

        if (hit)
        {
            button.setText("H");
            button.setForeground(Color.BLACK);
            button.setBackground(Color.RED);
        }
        else
        {
            button.setText("M");
            button.setForeground(Color.BLACK);
            button.setBackground(Color.YELLOW);
        }

        button.setEnabled(false);
    }

    public void updateCounters(
            int missStreak, int strikes, int totalMisses, int totalHits)
    {
        missLabel.setText("MISS: " + missStreak + " / 5");
        strikeLabel.setText("STRIKE: " + strikes + " / 3");
        totalMissLabel.setText("TOTAL MISS: " + totalMisses + " / 83");
        totalHitLabel.setText("TOTAL HIT: " + totalHits + " / 17");
    }

    public void setGameEnabled(boolean enabled)
    {
        for (int row = 0; row < BOARD_SIZE; row++)
        {
            for (int col = 0; col < BOARD_SIZE; col++)
            {
                if (buttons[row][col].getText().isEmpty())
                {
                    buttons[row][col].setEnabled(enabled);
                }
            }
        }
    }

    public void showShipSunkMessage(int shipSize)
    {
        JOptionPane.showMessageDialog(
                this,
                "You sunk a ship of size " + shipSize + "!",
                "Ship Sunk",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    public boolean confirmRestart()
    {
        int choice = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to start a new game?",
                "Play Again",
                JOptionPane.YES_NO_OPTION
        );

        return choice == JOptionPane.YES_OPTION;
    }

    public boolean askReplay(String message)
    {
        int choice = JOptionPane.showConfirmDialog(
                this,
                message,
                "Battleship",
                JOptionPane.YES_NO_OPTION
        );

        return choice == JOptionPane.YES_OPTION;
    }
}