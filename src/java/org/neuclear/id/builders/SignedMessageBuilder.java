package org.neuclear.id.builders;

import org.dom4j.Element;

/**
 * Created by IntelliJ IDEA.
 * User: pelleb
 * Date: Dec 15, 2003
 * Time: 11:54:36 PM
 * To change this template use Options | File Templates.
 */
public class SignedMessageBuilder extends Builder {
    public SignedMessageBuilder(String subject) {
        super("html");
        head = getElement().addElement("head");
        body = getElement().addElement("body");
        Element meta = head.addElement("meta");
        meta.addAttribute("name", "neu:type");
        meta.addAttribute("content", "message");
        head.addElement("title").setText(subject);
        body.addElement("h1").setText(subject);
    }

    public SignedMessageBuilder(String subject, String message) {
        this(subject);
        body.addElement("p").setText(message);
    }

    public Element getBody() {
        return body;
    }

    private Element head;
    private Element body;
}
