package org.neuclear.id.senders;

import org.neuclear.commons.NeuClearException;
import org.neuclear.id.Identity;
import org.neuclear.id.NSTools;
import org.neuclear.id.SignedNamedObject;
import org.neuclear.id.resolver.Resolver;
import org.neuclear.xml.XMLException;

/**
 * Created by IntelliJ IDEA.
 * User: pelleb
 * Date: Dec 16, 2003
 * Time: 8:42:56 AM
 * To change this template use Options | File Templates.
 */
public class NeuSender extends Sender {
    public SignedNamedObject send(String endpoint, SignedNamedObject obj) throws NeuClearException, XMLException, UnsupportedEndpointException {
        if (NSTools.isValidName(endpoint)) {
            Identity id = Resolver.resolveIdentity(endpoint);
            return id.send(obj);
        }
        throw new UnsupportedEndpointException(this, endpoint);
    }
}
