package org.neuclear.id;

import org.dom4j.Element;

/**
 * Created by IntelliJ IDEA.
 * User: pelleb
 * Date: Dec 15, 2003
 * Time: 11:48:34 PM
 * To change this template use Options | File Templates.
 */
public class SignedMessage extends SignedNamedObject {
    private SignedMessage(SignedNamedCore core, String subject, String message) {
        super(core);
        this.subject = subject;
        this.message = message;
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
        public final SignedNamedObject read(final SignedNamedCore core, final Element elem) {
            final String subject = elem.element("Subject").getText();
            final String message = elem.element("Message").getText();
            return new SignedMessage(core, subject, message);
        }

    }

    private final String subject;
    private final String message;

    public static final String TAG_NAME = "SignedMessage";
}
