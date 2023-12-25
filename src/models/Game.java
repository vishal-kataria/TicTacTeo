package models;

import exceptions.InvalidGameMoveException;
import exceptions.InvalidGameParamException;
import exceptions.InvalidUndoMoveException;
import strategies.winningstrategies.WinningStrategy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Game {
    private List<Move> moves;
    private Board board;

    private List<Player> players;

    private int currentMovePlayer;

    private List <WinningStrategy> winningStrategies;

    private GameStatus gameStatus;

    private Player winner;


    private Game(int dimension,List<Player> playersList ,List<WinningStrategy> winningStrategies){
        this.moves=new ArrayList<>();
        this.board = new Board(dimension);
        this.players = playersList;
        this.currentMovePlayer = 0;
        this.winningStrategies = winningStrategies;
        this.gameStatus = GameStatus.IN_PROGRESS;
    }

    public boolean validateMove(Cell cell){

        int row = cell.getRow();
        int col = cell.getColumn();
        if(row <0 || col < 0 || row >= board.getSize() || col >= board.getSize()){
            return false;
        }
        if (board.getBoard().get(row).get(col).getCellState().equals(CellState.FILLED))
            return false;
        return true;
    }
    public void makeMove(){

        Player playerCurrent  = players.get(currentMovePlayer);
        System.out.println("----------------------------------");
        System.out.println("It is "+playerCurrent.getName()+"'s turn. ");
        Cell proposedCell = playerCurrent.makeMove(board);

        System.out.println("Move made at row: "+proposedCell.getRow()+" col:"+proposedCell.getColumn());
        try {
            if(!validateMove(proposedCell)){
                throw new InvalidGameMoveException("Invalid Game move");
            }
        } catch (InvalidGameMoveException e) {
            throw new RuntimeException(e);
        }
        Cell cell = board.getBoard().get(proposedCell.getRow()).get(proposedCell.getColumn());
        cell.setCellState(CellState.FILLED);
        cell.setPlayer(playerCurrent);
        Move move = new Move(playerCurrent,cell);
        moves.add(move);
        System.out.println("----------------------------------");
        if (checkGameWon(move, playerCurrent)) return;

        if (checkDraw()) return;
        currentMovePlayer += 1 ;
        currentMovePlayer %= players.size();
    }

    public void undo(){
        try {
            if (moves.size() == 0) {
                throw new InvalidUndoMoveException("No moves can't UNDO");
            }
        }catch (InvalidUndoMoveException e){
            throw new RuntimeException(e);
        }
        Move last = moves.get(moves.size()-1);

        for(WinningStrategy winningStrategy: winningStrategies){
            winningStrategy.handleUndo(board,last);
        }

        Cell cell = last.getCell();
        cell.setCellState(CellState.EMPTY);
        cell.setPlayer(null);

        moves.remove(last);

        currentMovePlayer -=1;
        currentMovePlayer +=players.size();
        currentMovePlayer %=players.size();
    }

    private boolean checkDraw() {
        if (moves.size() == board.getSize() * board.getSize()){
            gameStatus = GameStatus.DRAW;
            return true;
        }
        return false;
    }

    private boolean checkGameWon(Move move, Player playerCurrent) {
        for(WinningStrategy winningStrategy:winningStrategies ){
            if(winningStrategy.checkWinner(board, move)){
                gameStatus = GameStatus.ENDED;
                winner = playerCurrent;
                return true;
            }
        }
        return false;
    }

    public void printWinner(){
        System.out.println(winner);
    }

    public void printResult(){
        if(gameStatus.equals(GameStatus.ENDED)){
            System.out.println("Winner is "+winner.getName());
        } else{
            System.out.println("DRAW");
        }
    }




    public List<Move> getMoves() {
        return moves;
    }

    public void setMoves(List<Move> moves) {
        this.moves = moves;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public int getCurrentMovePlayer() {
        return currentMovePlayer;
    }

    public void setCurrentMovePlayer(int currentMovePlayer) {
        this.currentMovePlayer = currentMovePlayer;
    }

    public List<WinningStrategy> getWinningStrategies() {
        return winningStrategies;
    }

    public void setWinningStrategies(List<WinningStrategy> winningStrategies) {
        this.winningStrategies = winningStrategies;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public void printBoard(){
       this.board.print();
    }

    public static Builder getBuilder(){
        return new Builder();
    }

    public static class Builder{
        private List<Player> players;
        private int dimensions;


        public Builder setPlayers(List<Player> players) {
            this.players = players;
            return this;
        }

        public Builder setDimensions(int dimensions) {
            this.dimensions = dimensions;
            return this;
        }

        public Builder setWinningStrategies(List<WinningStrategy> winningStrategies) {
            this.winningStrategies = winningStrategies;
            return this;
        }

        private List <WinningStrategy> winningStrategies;

        private boolean validate(){
            if(this.players.size() < 2){
                return false;
            }
            if(this.players.size() != this.dimensions-1){
                return false;
            }
            int botCount =0;

            for(Player player :this.players){
                if (player.getPlayerType().equals(PlayerType.BOT)){
                    botCount +=1;
                }
            }
            if (botCount >=2){
                return false;
            }
            Set<Character> existingSymbols = new HashSet<>();
            for(Player player : players){
                if(existingSymbols.contains(player.getSymbol())){
                    return false;
                }else{
                    existingSymbols.add(player.getSymbol().getaChar());
                }

            }
            return true;
        }
        public Game build(){
            if(!validate()){
                try {
                    throw new InvalidGameParamException("INVALID PARAMS");
                } catch (InvalidGameParamException e) {
                    throw new RuntimeException(e);
                }
            }
            return new Game(dimensions,players,winningStrategies);
        }
        private Builder(){}

    }


}
