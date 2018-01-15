package implementations;

import Exceptions.DbProblemException;
import interfaces.LocalStore;
import models.*;
import org.hibernate.ReplicationMode;
import org.hibernate.Session;

import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.logging.Logger;

public class DBLocalStore extends CollectionsLocalStore implements LocalStore {

    private final static Logger LOGGER = Logger.getLogger(CollectionsLocalStore.class.getName());
    private static DBLocalStore instance = new DBLocalStore();

    private DBLocalStore() {
        super();
    }

    public static DBLocalStore getInstance() {
        return instance;
    }

    @Override
    public void userRegistration(User user, ChatWithCommand chat) {
        super.userRegistration(user, chat);

/*        Session s = HibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();
        s.replicate(user, ReplicationMode.EXCEPTION);
        s.getTransaction().commit();
        s.close();*/
    }

    @Override
    public void addEvents(List<Event> eventsList) throws DbProblemException {
        Session s = HibernateUtil.getSessionFactory().openSession();
        try {

            s.beginTransaction();
            for (int i = 0; i < eventsList.size(); i++) {
                eventsList.set(i, (Event) s.merge(eventsList.get(i)));
            }
            s.getTransaction().commit();
            s.close();
            super.addEvents(eventsList);
        } catch (PersistenceException e) {
            s.getTransaction().rollback();
            e.printStackTrace();
            throw new DbProblemException(e.getMessage());
        }
    }

    @Override
    public void finishEvent(Event event) {
        super.finishEvent(event);
        Session s = HibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();
        s.merge(event);
        s.getTransaction().commit();
        s.close();


    }

    @Override
    public void startEvent(Event event) {
        super.startEvent(event);
        Session s = HibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();
        s.merge(event);
        s.getTransaction().commit();
        s.close();


    }

    @Override
    public ChatWithCommand addOrUpdateChat(Long id, ChatWithCommand chat) {

        Session s = HibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();
        s.replicate(chat, ReplicationMode.OVERWRITE);
        s.getTransaction().commit();
        s.close();
        return super.addOrUpdateChat(id, chat);
    }

    @Override
    public void setChatPreviousCommand(ChatWithCommand chat, Command command) {
        super.setChatPreviousCommand(chat, command);
        Session s = HibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();
        s.merge(chat);
        s.getTransaction().commit();
        s.close();
    }

    @Override
    public void initStore() {

        Session s = HibernateUtil.getSessionFactory().openSession();
        CriteriaBuilder criteriaBuilder = s.getCriteriaBuilder();

        CriteriaQuery<ChatWithCommand> chatCriteria = criteriaBuilder.createQuery(ChatWithCommand.class);
        Root<ChatWithCommand> chatRoot = chatCriteria.from(ChatWithCommand.class);
        chatCriteria.select(chatRoot);
        List<ChatWithCommand> chatsFromDB = s.createQuery(chatCriteria).getResultList();

        for (ChatWithCommand chat :
                chatsFromDB) {
            super.addOrUpdateChat(chat.getId(), chat);
            super.userRegistration(chat.getUser(), chat);
        }

        CriteriaQuery<Event> eventCriteria = criteriaBuilder.createQuery(Event.class);
        Root<Event> eventRoot = eventCriteria.from(Event.class);
        eventCriteria.where(criteriaBuilder.notEqual(eventRoot.get(Event_.status), EventStatus.FINISHED));
        List<Event> events = s.createQuery(eventCriteria).getResultList();
        for (int i = 0; i < events.size(); i++) {
            if (events.get(i).getStatus() != EventStatus.NEW) {
                events.get(i).setStatus(EventStatus.NEW);
                events.set(i, (Event) s.merge(events.get(i)));
            }
        }
        try {
            super.addEvents(s.createQuery(eventCriteria).getResultList());
        } catch (DbProblemException e) {
            e.printStackTrace();
        }

        s.close();
    }
}
