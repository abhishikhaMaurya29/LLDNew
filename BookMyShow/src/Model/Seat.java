package Model;

public class Seat {
    private final long id;
    private final int row;
    private final int col;

    public Seat(long id, int row, int col) {
        this.id = id;
        this.row = row;
        this.col = col;
    }

    public long getId() {
        return id;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
