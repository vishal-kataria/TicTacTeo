package strategies.botgameplaying;

import models.BotDifficultyLevel;

public class BotPlayingStrategyFactory {
    public static BotPlayingStrategies getBotplayingStrategiesForDifficult(BotDifficultyLevel botDifficultyLevel){
        return switch (botDifficultyLevel){
            case EASY -> new EasyBotPlayingStrategy();
            case MEDIUM -> new MediumBotPlayingStrategy();
            case HARD -> new HardBotPlayingStrategy();
        };
    }
}
