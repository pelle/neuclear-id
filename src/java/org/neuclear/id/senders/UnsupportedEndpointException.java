package org.neuclear.id.senders;

import org.neuclear.commons.NeuClearException;

/**
 * Created by IntelliJ IDEA.
 * User: pelleb
 * Date: Dec 16, 2003
 * Time: 8:45:31 AM
 * To change this template use Options | File Templates.
 */
public class UnsupportedEndpointException extends NeuClearException {
    public UnsupportedEndpointException(Sender sender,String endpoint) {
        super("endpoint: "+endpoint+" not supported by: "+sender.getClass().getName());
        this.endpoint=endpoint;
        this.sender=sender;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public Sender getSender() {
        return sender;
    }

    private final String endpoint;
    private final Sender sender;
}
