package org.neuclear.id;

import org.dom4j.Element;

/**
 * Created by IntelliJ IDEA.
 * User: pelleb
 * Date: Dec 15, 2003
 * Time: 11:48:34 PM
 * To change this template use Options | File Templates.
 */
public class SignedMessage extends SignedNamedObject implements HTMLBased {
    private SignedMessage(SignedNamedCore core, String subject) {
        super(core);
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }

    protected static String extractSubject(final Element elem, final SignedNamedCore core) {
        final Element namelement = ((Element) elem.selectSingleNode("//html/head/title"));
        final String name = (namelement != null) ? namelement.getTextTrim() : core.getName();
        return name;
    }

    public String toString() {
        return getSubject();
    }

    public final static class Reader implements NamedObjectReader {
        /**
         * Read object from Element and fill in its details
         *
         * @param elem
         * @return
         */
        public final SignedNamedObject read(final SignedNamedCore core, final Element elem) {
            final String subject = extractSubject(elem, core);
            return new SignedMessage(core, subject);
        }

    }

    private final String subject;

}
