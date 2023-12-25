package strategies.winningstrategies;

import models.Board;
import models.Move;
import models.Player;
import models.Symbol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderOneRowWinningStrategy implements  WinningStrategy{

    private List<Map<Symbol,Integer>> rowMaps;

    public OrderOneRowWinningStrategy(int dimensions, List<Player> players){
        rowMaps = new ArrayList<>();

        for(int i=0; i<dimensions; i++){
            rowMaps.add(new HashMap<>());
            for(Player player:players){
                rowMaps.get(i).put(player.getSymbol(),0);
            }
        }
    }

    @Override
    public boolean checkWinner(Board board, Move lastMove) {

        int row = lastMove.getCell().getRow();
        Symbol symbol = lastMove.getPlayer().getSymbol();
        rowMaps.get(row).put(
                symbol,1+rowMaps.get(row).get(symbol)
        );
        return rowMaps.get(row).get(symbol) == board.getSize();
    }

    @Override
    public void handleUndo(Board board, Move move) {
        int row = move.getCell().getRow();
        Symbol symbol = move.getPlayer().getSymbol();

        rowMaps.get(row).put(
                symbol,
                rowMaps.get(row).get(symbol)-1
        );
    }
}
