package org.neuclear.receiver;

import org.neuclear.xml.AbstractElementProxy;
import org.neuclear.id.builders.NamedObjectBuilder;
import org.neuclear.id.SignedNamedObject;
import org.neuclear.id.NSTools;
import org.dom4j.Namespace;

/**
 * A receipt for returning simple status from a Receiver.
 */
public class SimpleReceipt extends AbstractElementProxy{
    public SimpleReceipt(String message) {
        super("Receipt", NSTools.NS_NEUID);
        getElement().setText(message);
    }
}
