package models;

import strategies.botgameplaying.BotPlayingStrategies;
import strategies.botgameplaying.BotPlayingStrategyFactory;

public class Bot extends Player{
    private BotDifficultyLevel botDifficultyLevel;

    private BotPlayingStrategies botPlayingStrategies;
    public Bot(Symbol symbol, String name, BotDifficultyLevel botDifficultyLevel) {
        super(symbol, name, PlayerType.BOT);
        this.botDifficultyLevel = botDifficultyLevel;
        this.botPlayingStrategies = BotPlayingStrategyFactory.getBotplayingStrategiesForDifficult(botDifficultyLevel);
    }

    public Cell makeMove(Board board){
        return botPlayingStrategies.makeMove(board);
    }
}
