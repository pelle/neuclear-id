package org.neuclear.id.builders;

import org.neuclear.commons.NeuClearException;
import org.neuclear.id.SignedMessage;

/**
 * Created by IntelliJ IDEA.
 * User: pelleb
 * Date: Dec 15, 2003
 * Time: 11:54:36 PM
 * To change this template use Options | File Templates.
 */
public class SignedMessageBuilder extends Builder{
    public SignedMessageBuilder(String recipient, String subject, String message) throws NeuClearException {
        super(createNEUIDQName(SignedMessage.TAG_NAME));
        addElement("Recipient").setText(recipient);
        addElement("Subject").setText(subject);
        addElement("Message").setText(message);
    }
}
