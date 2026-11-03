import javax.swing.JOptionPane;

public class GameController
{
    private GameBoard board;
    private GameView view;

    private int missStreak;
    private int strikes;
    private int totalMisses;
    private int totalHits;
    private boolean gameOver;

    public void startGame()
    {
        board = new GameBoard();
        board.placeShips();

        missStreak = 0;
        strikes = 0;
        totalMisses = 0;
        totalHits = 0;
        gameOver = false;

        if (view == null)
        {
            view = new GameView(this);
        }

        view.createBoard();
        view.updateCounters(missStreak, strikes, totalMisses, totalHits);
        view.setGameEnabled(true);
    }

    public void fireAt(int row, int col)
    {
        if (gameOver)
        {
            return;
        }

        Cell cell = board.getCell(row, col);

        if (cell.isFiredUpon())
        {
            return;
        }

        Ship ship = cell.getShip();
        boolean hit = board.fireAt(row, col);

        if (hit)
        {
            totalHits++;
            missStreak = 0;
            view.updateCell(row, col, true);
            view.updateCounters(missStreak, strikes, totalMisses, totalHits);

            if (ship.isSunk())
            {
                view.showShipSunkMessage(ship.getSize());
            }

            if (board.allShipsSunk())
            {
                gameOver = true;
                view.setGameEnabled(false);

                if (view.askReplay("You sank all the ships! You win! Play again?"))
                {
                    startGame();
                }
            }
        }
        else
        {
            missStreak++;
            totalMisses++;
            view.updateCell(row, col, false);

            if (missStreak == 5)
            {
                strikes++;
                missStreak = 0;
            }

            view.updateCounters(missStreak, strikes, totalMisses, totalHits);

            if (strikes == 3)
            {
                gameOver = true;
                view.setGameEnabled(false);

                if (view.askReplay("You reached 3 strikes. Game over! Play again?"))
                {
                    startGame();
                }
            }
        }
    }

    public void playAgain()
    {
        if (view.confirmRestart())
        {
            startGame();
        }
    }

    public void quitGame()
    {
        if (view.askReplay("Would you like to start a new game?"))
        {
            startGame();
        }
        else
        {
            System.exit(0);
        }
    }
}