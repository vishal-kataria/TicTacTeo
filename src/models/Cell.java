package models;

public class Cell {

    private int row, column;
    private CellState cellState;

    private Player player;


    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public CellState getCellState() {
        return cellState;
    }

    public void setCellState(CellState cellState) {
        this.cellState = cellState;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Cell(int r, int c){
        this.row = r;
        this.column = c;
        this.cellState = CellState.EMPTY;
    }
    public void display(){
        if(getCellState().equals(CellState.EMPTY)){
            System.out.printf(" - |");
        }else{
            System.out.printf(" "+getPlayer().getSymbol().getaChar()+" |");
        }
    }
}
