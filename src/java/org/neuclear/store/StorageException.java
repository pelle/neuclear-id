package org.neuclear.store;

import org.neuclear.commons.NeuClearException;

/**
 * Created by IntelliJ IDEA.
 * User: pelleb
 * Date: Dec 19, 2003
 * Time: 11:11:50 AM
 * To change this template use Options | File Templates.
 */
public class StorageException extends NeuClearException {
    public StorageException(Store store,final Throwable cause) {
        super("NeuClear Storage Exception in:\n"+store.getClass().getName()+"\n"+cause.getLocalizedMessage(),cause);
        this.store=store;
    }

    public StorageException(Store store,final String message) {
        super("NeuClear Storage Exception in:\n"+store.getClass().getName()+"\n"+message);
        this.store=store;
    }

    public StorageException(Store store,final String message, final Throwable cause) {
        super("NeuClear Storage Exception in:\n"+store.getClass().getName()+"\n"+message,cause);
        this.store=store;
    }

    public Store getStore() {
        return store;
    }

    private final Store store;
}
