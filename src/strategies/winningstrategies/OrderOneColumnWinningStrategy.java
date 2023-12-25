package strategies.winningstrategies;

import models.Board;
import models.Move;
import models.Player;
import models.Symbol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderOneColumnWinningStrategy implements  WinningStrategy{

    private List<Map<Symbol,Integer>> colMaps;

    public OrderOneColumnWinningStrategy(int dimensions, List<Player> players){
        colMaps = new ArrayList<>();

        for(int i=0; i<dimensions; i++){
            colMaps.add(new HashMap<>());
            for(Player player:players){
                colMaps.get(i).put(player.getSymbol(),0);
            }
        }
    }
    @Override
    public boolean checkWinner(Board board, Move lastMove) {

        int col = lastMove.getCell().getColumn();
        Symbol symbol = lastMove.getPlayer().getSymbol();
        colMaps.get(col).put(
                symbol,1+colMaps.get(col).get(symbol)
        );
        return colMaps.get(col).get(symbol) == board.getSize();
    }

    @Override
    public void handleUndo(Board board, Move move) {
        int col = move.getCell().getColumn();
        Symbol symbol = move.getPlayer().getSymbol();

        colMaps.get(col).put(
                symbol,
                colMaps.get(col).get(symbol)-1
        );
    }
}
