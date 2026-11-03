import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GameBoard
{
    public static final int SIZE = 10;

    private final Cell[][] cells;
    private final List<Ship> ships;
    private final Random random;

    public GameBoard()
    {
        cells = new Cell[SIZE][SIZE];
        ships = new ArrayList<>();
        random = new Random();

        for (int row = 0; row < SIZE; row++)
        {
            for (int col = 0; col < SIZE; col++)
            {
                cells[row][col] = new Cell();
            }
        }
    }

    public Cell getCell(int row, int col)
    {
        return cells[row][col];
    }

    public void placeShips()
    {
        int[] shipSizes = {5, 4, 3, 3, 2};

        for (int size : shipSizes)
        {
            Ship ship = new Ship(size);
            placeShip(ship);
            ships.add(ship);
        }
    }

    private void placeShip(Ship ship)
    {
        boolean placed = false;

        while (!placed)
        {
            boolean horizontal = random.nextBoolean();

            if (horizontal)
            {
                int row = random.nextInt(SIZE);
                int startCol = random.nextInt(SIZE - ship.getSize() + 1);

                List<Integer> positions = new ArrayList<>();

                for (int col = startCol; col <= SIZE - ship.getSize(); col++)
                {
                    positions.add(col);
                }

                Collections.shuffle(positions, random);

                for (int col : positions)
                {
                    if (canPlaceShip(row, col, ship.getSize(), true))
                    {
                        for (int i = 0; i < ship.getSize(); i++)
                        {
                            cells[row][col + i].setShip(ship);
                        }

                        placed = true;
                        break;
                    }
                }
            }
            else
            {
                int col = random.nextInt(SIZE);
                int startRow = random.nextInt(SIZE - ship.getSize() + 1);

                List<Integer> positions = new ArrayList<>();

                for (int row = startRow; row <= SIZE - ship.getSize(); row++)
                {
                    positions.add(row);
                }

                Collections.shuffle(positions, random);

                for (int row : positions)
                {
                    if (canPlaceShip(row, col, ship.getSize(), false))
                    {
                        for (int i = 0; i < ship.getSize(); i++)
                        {
                            cells[row + i][col].setShip(ship);
                        }

                        placed = true;
                        break;
                    }
                }
            }
        }
    }

    private boolean canPlaceShip(int row, int col, int size, boolean horizontal)
    {
        if (horizontal && col + size > SIZE)
        {
            return false;
        }

        if (!horizontal && row + size > SIZE)
        {
            return false;
        }

        for (int i = 0; i < size; i++)
        {
            int shipRow = horizontal ? row : row + i;
            int shipCol = horizontal ? col + i : col;

            if (cells[shipRow][shipCol].hasShip())
            {
                return false;
            }
        }

        return true;
    }

    public boolean fireAt(int row, int col)
    {
        Cell cell = cells[row][col];

        if (cell.isFiredUpon())
        {
            return false;
        }

        cell.setFiredUpon(true);

        if (cell.hasShip())
        {
            cell.getShip().recordHit();
            return true;
        }

        return false;
    }

    public boolean allShipsSunk()
    {
        for (Ship ship : ships)
        {
            if (!ship.isSunk())
            {
                return false;
            }
        }

        return true;
    }
}