package controllers;

import models.*;
import strategies.winningstrategies.WinningStrategy;

import java.util.List;

public class GameController {

    public Game createGame(int dimensions, List<Player> player, List<WinningStrategy> winningStrategies){
        return Game.getBuilder()
                .setDimensions(dimensions)
                .setPlayers(player)
                .setWinningStrategies(winningStrategies)
                .build();
    }

    public void displayBoard(Game game){
        game.printBoard();
    }

    public void undo(Game game){
        game.undo();
    }

    public void makeMove(Game game){
        game.makeMove();
    }

    public GameStatus getGameStatus(Game game){
        return game.getGameStatus();
    }

    public void printWinner(Game game){
        game.printWinner();
    }

    public void printResult(Game game){
        game.printResult();
    }
}
