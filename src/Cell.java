public class Cell
{
    private Ship ship;
    private boolean firedUpon;

    public Cell()
    {
        ship = null;
        firedUpon = false;
    }

    public boolean hasShip()
    {
        return ship != null;
    }

    public Ship getShip()
    {
        return ship;
    }

    public void setShip(Ship ship)
    {
        this.ship = ship;
    }

    public boolean isFiredUpon()
    {
        return firedUpon;
    }

    public void setFiredUpon(boolean firedUpon)
    {
        this.firedUpon = firedUpon;
    }
}