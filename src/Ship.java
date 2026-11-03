public class Ship
{
    private final int size;
    private int hits;

    public Ship(int size)
    {
        this.size = size;
        hits = 0;
    }

    public void recordHit()
    {
        if (hits < size)
        {
            hits++;
        }
    }

    public boolean isSunk()
    {
        return hits == size;
    }

    public int getSize()
    {
        return size;
    }

    public int getHits()
    {
        return hits;
    }
}