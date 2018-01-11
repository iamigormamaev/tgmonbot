package implementations;

import interfaces.UpdateHandler;
import models.ChatWithCommand;
import models.Command;
import models.Event;
import models.User;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Main {
    private static TcsMonitoringBot tcsMonitoringBot;

    public static void main(String[] args) {
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

/*        User u = new User();
        u.setFirstName("lol");
        u.setId(1);

        ChatWithCommand chat = new ChatWithCommand();
        chat.setFirstName("lol");
        chat.setId(1L);
        chat.setPreviousCommand(Command.NOTHING);

        new DBLocalStore().userRegistration(u, chat);

        List<Event> eventList = new ArrayList<>();
        Event e = new Event();
        e.setAuthor(u);
        e.setUser(u);
        e.setDate(new Date(System.currentTimeMillis()));
        eventList.add(e);


        new DBLocalStore().addEvents(eventList);*/


    }

    public static TcsMonitoringBot getTcsMonitoringBot() {
        return tcsMonitoringBot;
    }
}
