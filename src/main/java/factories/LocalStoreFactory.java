package factories;

import implementations.CollectionsLocalStore;
import interfaces.LocalStore;

public class LocalStoreFactory {
    public LocalStore getDefaultLocalStore() {
        return CollectionsLocalStore.getInstance();
    }
}
