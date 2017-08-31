package interfaces;

/**
 * Created by Игорь on 11.02.2017.
 */
public interface Strings {
    String SUCCESSFUL_REGISTRATION = "Вы успешно зарегестрированы! Чтобы добавить новое оповещение отправьте /add";
    String ALREADY_REGISTERED = "Вы были зарегестрированы ранее. Все хорошо.";
    String WRONG_EVENT = "Событие не добавлено. Невозможно распознать строку: ";
    String NOT_REGISTERED_USER = "Событие не добавлено. Пользователь не зарегестрирован: ";
    String CANT_PARSE_DATE = "Событие не добавлено. Невозможно распознать дату: ";
    String ADD_EVENT = "Для добавления событий отправьте строку вида \nИмя пользователя Дата\n где Имя пользователя - ник в Telegram, а дата в формате день.месяц.год (ДД.ММ.ГГГГ)";

    String EVENTS_ADDED = "События добавлены";
}