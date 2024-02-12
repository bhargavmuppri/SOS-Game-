import java.io.Serializable;

public class Move implements Serializable {


    private int row;
    private int col;
    private String symbol;


    public Move(int row, int col, String symbol) {
        this.row = row;
        this.col = col;
        this.symbol = symbol;
    }


    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public String getSymbol() {
        return symbol;
    }


    @Override
    public String toString() {
        return "Move{" +
                "row=" + row +
                ", col=" + col +
                ", symbol='" + symbol + '}';
    }
}
