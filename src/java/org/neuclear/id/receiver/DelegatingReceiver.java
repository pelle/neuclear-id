package org.neuclear.id.receiver;

import org.neuclear.commons.NeuClearException;
import org.neuclear.id.SignedNamedObject;

import java.util.HashMap;
import java.util.Map;

/*
$Id: DelegatingReceiver.java,v 1.1 2004/07/21 23:04:05 pelle Exp $
$Log: DelegatingReceiver.java,v $
Revision 1.1  2004/07/21 23:04:05  pelle
Added DelegatingReceiver and friends. This was created to make it easier to create complex services in NeuClear.

*/

/**
 * The DelegatingReceiver allows you to build up complex receivers for various object types. Simply subclass it and register individual Receivers within it.
 */
public class DelegatingReceiver implements Receiver {
    protected DelegatingReceiver() {
        receivers = new HashMap();
    }

    public final SignedNamedObject receive(SignedNamedObject obj) throws UnsupportedTransaction, NeuClearException {
        Receiver receiver = (Receiver) receivers.get(obj.getTagName());
        if (receiver == null)
            return null;

        return receiver.receive(obj);
    }

    protected DelegatingReceiver register(String tagname, Receiver receiver) {
        receivers.put(tagname, receiver);
        return this;
    }

    protected DelegatingReceiver register(HandlingReceiver receiver) {
        register(receiver.handlesTagName(), receiver);
        return this;
    }

    private final Map receivers;
}
