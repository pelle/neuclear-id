package org.neuclear.id;

import org.neuclear.commons.NeuClearException;

/**
 * Created by IntelliJ IDEA.
 * User: pelleb
 * Date: Dec 18, 2003
 * Time: 2:03:37 PM
 * To change this template use Options | File Templates.
 */
public class NameResolutionException extends NeuClearException{
    private final String name;

    public NameResolutionException(final String name,final String cause) {
        super(name+" couldnt be resolved\nCause:"+cause);
        this.name=name;
    }
    public NameResolutionException(final String name) {
        super(name+" couldnt be resolved");
        this.name=name;
    }

    public String getName() {
        return name;
    }
}
