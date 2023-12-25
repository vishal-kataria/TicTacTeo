import controllers.GameController;
import models.*;
import strategies.winningstrategies.*;

import java.util.List;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {

        GameController gameController = new GameController();
        Scanner scanner = new Scanner(System.in);
        Game game;
        List<Player> players = List.of(
                new Player(new Symbol('X'),"Vishal",PlayerType.HUMAN),
                new Bot(new Symbol('O'),"BOT",BotDifficultyLevel.EASY)
        );
        try{
            game = gameController.createGame(3,
                    players,
                    List.of(
                            new OrderOneDiagonalWinningStrategy(players),
                            new OrderOneColumnWinningStrategy(3,players),
                            new OrderOneRowWinningStrategy(3,players)
                            )
                    );

        }catch (Exception e){
            System.out.println("Something went wrong!!!");
            return;
        }

        System.out.println("------------------------- Game is starting -------------------------");

        while (game.getGameStatus().equals(GameStatus.IN_PROGRESS)){
            gameController.displayBoard(game);
            System.out.println("----------------------------------");
            System.out.println("Do you want to UNDO? (Y/N)");

            String input = scanner.next();
            if (input.equalsIgnoreCase("Y")){
                gameController.undo(game);
            }else{
                gameController.makeMove(game);
            }
        }

        gameController.printResult(game);


    }
}