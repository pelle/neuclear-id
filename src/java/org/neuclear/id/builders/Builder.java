package org.neuclear.id.builders;

import org.neuclear.xml.xmlsec.SignedElement;
import org.neuclear.xml.xmlsec.XMLSecurityException;
import org.neuclear.id.SignedNamedObject;
import org.neuclear.id.InvalidNamedObjectException;
import org.neuclear.id.NameResolutionException;
import org.neuclear.id.NSTools;
import org.neuclear.id.verifier.VerifyingReader;
import org.neuclear.commons.time.TimeTools;
import org.dom4j.QName;
import org.dom4j.Namespace;
import org.dom4j.Element;
import org.dom4j.DocumentHelper;

/**
 * Created by IntelliJ IDEA.
 * User: pelleb
 * Date: Jan 10, 2004
 * Time: 10:21:48 PM
 * To change this template use Options | File Templates.
 */
public class Builder extends SignedElement {
    public Builder(final QName qname) {
        super(qname);
    }

    public Builder(final String name, final Namespace ns) {
        super(name, ns);
    }

    public Builder(final Element elem) throws XMLSecurityException {
        super(elem);
    }

    public Builder(final String name, final String prefix, final String nsURI) {
        super(name, prefix, nsURI);
    }

    final public SignedNamedObject convert() throws InvalidNamedObjectException, NameResolutionException{

        return VerifyingReader.getInstance().read(getElement());
    }

    /**
     * Sign NamedObject using given PrivateKey. This also adds a timestamp to the root element prior to signing
     */
    protected final void preSign() throws XMLSecurityException {
        // We need to timestamp it before we sign it
        getElement().addAttribute(DocumentHelper.createQName("timestamp", NSTools.NS_NEUID), TimeTools.createTimeStamp());
    }
}
