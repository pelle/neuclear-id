package org.neuclear.receiver;

import org.neuclear.id.NSTools;
import org.neuclear.id.builders.Builder;

/**
 * A receipt for returning simple status from a Receiver.
 */
public class SimpleReceipt extends Builder{
    public SimpleReceipt(String message) {
        super("Receipt", NSTools.NS_NEUID);
        getElement().setText(message);
    }
}
