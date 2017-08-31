package implementations;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

public class Main {
    private static MyMonitoringBot myMonitoringBot;

    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi botsApi = new TelegramBotsApi();
        myMonitoringBot = new MyMonitoringBot();
        try {
            botsApi.registerBot(myMonitoringBot);
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }

    public static MyMonitoringBot getMyMonitoringBot() {
        return myMonitoringBot;
    }
}
