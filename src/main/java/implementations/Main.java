package implementations;

import interfaces.UpdateHandler;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

public class Main {
    private static TcsMonitoringBot tcsMonitoringBot;

    public static void main(String[] args)  {
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
}
