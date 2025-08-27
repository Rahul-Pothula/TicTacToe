package org.example.factory;

import org.example.models.BotDifficultyLevel;
import org.example.strategies.botplayingstrategy.BotPlayingStrategy;
import org.example.strategies.botplayingstrategy.EasyBotPlayingStrategy;
import org.example.strategies.botplayingstrategy.HardBotPlayingStrategy;
import org.example.strategies.botplayingstrategy.MediumBotPlayingStrategy;

public class BotPlayingStrategyFactory {
    public static BotPlayingStrategy getBotPlayingStrategy(BotDifficultyLevel botDifficultyLevel) {
        if (botDifficultyLevel == BotDifficultyLevel.EASY) {
            return new EasyBotPlayingStrategy();
        } else if (botDifficultyLevel == BotDifficultyLevel.MEDIUM) {
            return new MediumBotPlayingStrategy();
        } else if (botDifficultyLevel == BotDifficultyLevel.HARD) {
            return new HardBotPlayingStrategy();
        }

        return null;
    }
}
