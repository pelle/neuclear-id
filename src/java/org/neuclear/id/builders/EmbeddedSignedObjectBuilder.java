package org.neuclear.id.builders;

import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.QName;
import org.neuclear.id.SignedNamedObject;

/**
 * Created by IntelliJ IDEA.
 * User: pelleb
 * Date: Jan 10, 2004
 * Time: 6:20:26 PM
 * To change this template use Options | File Templates.
 */
public class EmbeddedSignedObjectBuilder extends Builder {
    public EmbeddedSignedObjectBuilder(final QName qname, final SignedNamedObject embedded) {
        super(qname);
        try {
            getElement().add(DocumentHelper.parseText(embedded.getEncoded()).getRootElement());
        } catch (DocumentException e) {
            throw new RuntimeException((e));
        }
    }

}
