package org.neuclear.id;

import org.neuclear.commons.NeuClearException;
import org.neuclear.commons.time.TimeTools;
import org.neuclear.xml.xmlsec.XMLSecurityException;
import org.neuclear.auth.AuthenticationTicket;
import org.dom4j.Element;
import org.dom4j.DocumentHelper;

import java.sql.Timestamp;

/**
 * Created by IntelliJ IDEA.
 * User: pelleb
 * Date: Dec 15, 2003
 * Time: 11:48:34 PM
 * To change this template use Options | File Templates.
 */
public class SignedMessage extends SignedNamedObject{
    private SignedMessage(SignedNamedCore core, String recipient,String subject, String message) throws NeuClearException {
        super(core);
        this.recipient=recipient;
        this.subject=subject;
        this.message=message;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getSubject() {
        return subject;
    }

    public String getMessage() {
        return message;
    }

    public final static class Reader implements NamedObjectReader {
        /**
         * Read object from Element and fill in its details
         *
         * @param elem
         * @return
         */
        public final SignedNamedObject read(final SignedNamedCore core, final Element elem) throws NeuClearException, XMLSecurityException {
            final String to=elem.attributeValue("recipient");
            final String subject=elem.element("subject").getText();
            final String message=elem.element("message").getText();
            return new SignedMessage(core, to,subject,message);
        }

    }
    private final String recipient;
    private final String subject;
    private final String message;

    public static final String TAG_NAME="SignedMessage";
}
