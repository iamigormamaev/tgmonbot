package implementations;

import interfaces.UpdateHandler;
import models.ChatWithCommand;
import models.Command;
import models.Event;
import models.User;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.LogManager;

public class Main {
    private static TcsMonitoringBot tcsMonitoringBot;

    public static void main(String[] args) {
        loggerInit();
        ApiContextInitializer.init();
        TelegramBotsApi botsApi = new TelegramBotsApi();
        tcsMonitoringBot = new TcsMonitoringBot();
        try {
            botsApi.registerBot(tcsMonitoringBot);
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
        UpdateHandler updateHandler = new UpdateHandlerImpl();
        updateHandler.pollingUpdates();
    }

    public static TcsMonitoringBot getTcsMonitoringBot() {
        return tcsMonitoringBot;
    }

    private static void loggerInit() {
        try {
            LogManager.getLogManager().readConfiguration(Main.class.getResourceAsStream("/logging.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
