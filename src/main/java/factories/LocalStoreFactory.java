package factories;

import implementations.InMemoryLocalStore;
import interfaces.LocalStore;

public class LocalStoreFactory {
    public LocalStore getDefaultLocalStore() {
        return InMemoryLocalStore.getInstance();
    }
}
