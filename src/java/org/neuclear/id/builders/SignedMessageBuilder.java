package org.neuclear.id.builders;

import org.dom4j.Namespace;
import org.neuclear.commons.NeuClearException;
import org.neuclear.id.NSTools;
import org.neuclear.id.SignedMessage;
import org.neuclear.id.Identity;

/**
 * Created by IntelliJ IDEA.
 * User: pelleb
 * Date: Dec 15, 2003
 * Time: 11:54:36 PM
 * To change this template use Options | File Templates.
 */
public class SignedMessageBuilder extends NamedObjectBuilder{
    public SignedMessageBuilder(Identity signer, String recipient, String subject, String message) throws NeuClearException {
        super(NSTools.createUniqueTransactionID(signer.getName(), recipient), SignedMessage.TAG_NAME);
        createAttribute("recipient",recipient);
        addElement("subject").setText(subject);
        addElement("message").setText(message);
    }
}
