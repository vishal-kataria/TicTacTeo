package strategies.winningstrategies;

import models.*;

public interface WinningStrategy {

    public boolean checkWinner(Board board, Move lastMove);

    void handleUndo(Board board, Move move);

}
