package interfaces;

/**
 * Created by Игорь on 11.02.2017.
 */
public interface Strings {
    String SUCCESSFUL_REGISTRATION = "Вы успешно зарегистрированы! Чтобы добавить новое оповещение отправьте /add";
    String ALREADY_REGISTERED = "Вы были зарегестрированы ранее. Все хорошо.";
    String WRONG_EVENT = "Событие не добавлено. Невозможно распознать строку: ";
    String NOT_REGISTERED_USER = "Событие не добавлено. Пользователь не зарегистрирован: ";
    String CANT_PARSE_DATE = "Событие не добавлено. Невозможно распознать дату: ";
    String ADD_EVENT = "Для добавления событий отправьте строку вида \nИмя пользователя Дата Время текст напоминания\n (напр. paveldurov 10.09.2017 10:00 Вернуть стену)";

    String EVENTS_ADDED = "События добавлены";
}
