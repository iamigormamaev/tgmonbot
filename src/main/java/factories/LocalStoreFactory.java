package factories;

import implementations.CollectionsLocalStore;
import implementations.DBLocalStore;
import interfaces.LocalStore;

public class LocalStoreFactory {
    public LocalStore getDefaultLocalStore() {
        return DBLocalStore.getInstance();
    }
}
