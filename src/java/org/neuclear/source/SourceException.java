package org.neuclear.source;

import org.neuclear.commons.NeuClearException;

/**
 * Created by IntelliJ IDEA.
 * User: pelleb
 * Date: Dec 19, 2003
 * Time: 9:59:44 AM
 * To change this template use Options | File Templates.
 */
public class SourceException extends NeuClearException{
    public SourceException(final Source source,final Throwable cause) {
        super(cause);
        this.source=source;
    }

    public SourceException(final Source source,final String message) {
        super(message);
        this.source=source;
    }

    public SourceException(final Source source,final String message, final Throwable cause) {
        super(message, cause);
        this.source=source;
    }
    private final Source source;
}
