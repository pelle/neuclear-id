package org.neuclear.id.builders;

import org.neuclear.xml.xmlsec.SignedElement;
import org.neuclear.xml.xmlsec.XMLSecurityException;
import org.neuclear.id.SignedNamedObject;
import org.dom4j.QName;
import org.dom4j.DocumentHelper;
import org.dom4j.DocumentException;

/**
 * Created by IntelliJ IDEA.
 * User: pelleb
 * Date: Jan 10, 2004
 * Time: 6:20:26 PM
 * To change this template use Options | File Templates.
 */
public class EmbeddedSignedObjectBuilder extends SignedElement {
    public EmbeddedSignedObjectBuilder(final QName qname, final SignedNamedObject embedded)  {
        super(qname);
        try {
            getElement().add(DocumentHelper.parseText(embedded.getEncoded()));
        } catch (DocumentException e) {
            throw new RuntimeException((e));
        }
    }

    public String getURI() throws XMLSecurityException {
        return "#";  //To change body of implemented methods use Options | File Templates.
    }
}
