package org.neuclear.id.builders;

import org.dom4j.*;
import org.neuclear.commons.crypto.passphraseagents.UserCancellationException;
import org.neuclear.commons.crypto.signers.NonExistingSignerException;
import org.neuclear.commons.crypto.signers.Signer;
import org.neuclear.commons.time.TimeTools;
import org.neuclear.id.InvalidNamedObjectException;
import org.neuclear.id.NSTools;
import org.neuclear.id.NameResolutionException;
import org.neuclear.id.SignedNamedObject;
import org.neuclear.id.verifier.VerifyingReader;
import org.neuclear.xml.xmlsec.SignedElement;
import org.neuclear.xml.xmlsec.XMLSecurityException;

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
        createDocument();
    }

    public Builder(final String name, final Namespace ns) {
        super(name, ns);
        createDocument();
    }

    public Builder(final Element elem) throws XMLSecurityException {
        super(elem);
        createDocument();    }

    final public SignedNamedObject convert() throws InvalidNamedObjectException, NameResolutionException{

        return VerifyingReader.getInstance().read(getElement());
    }
    private void createDocument() {
        final Element elem = getElement();
        if (elem.getDocument() == null) {
            final Document doc = DocumentHelper.createDocument(elem);
        }
    }
    /**
     * Sign NamedObject using given PrivateKey. This also adds a timestamp to the root element prior to signing
     */
    protected final void preSign() throws XMLSecurityException {
        // We need to timestamp it before we sign it
        getElement().addAttribute(DocumentHelper.createQName("timestamp", NSTools.NS_NEUID), TimeTools.createTimeStamp());
    }
     public Object clone() {
        try {
            final Element elem = (Element) getElement().clone();
            DocumentHelper.createDocument(elem);
            return new NamedObjectBuilder(elem);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public final SignedNamedObject convert(String name,Signer signer) throws NameResolutionException, InvalidNamedObjectException {
        try {
            sign(name,signer);
        } catch (XMLSecurityException e) {
            throw new InvalidNamedObjectException("Problem in XML Sig",e);
        } catch (NonExistingSignerException e) {
            throw new InvalidNamedObjectException("Can not Sign",e);
        } catch (UserCancellationException e) {
            throw new InvalidNamedObjectException("User Cancelled Signing",e);
        }
        return convert();
    }

    /**
     * Helper method to create and add an attribute to this element within the NEUID namespace
     *
     * @param name
     */
    protected final void createNEUIDAttribute(String name, String value) {
        getElement().addAttribute(createNEUIDQName(name), value);
    }

    /**
     * Helper method to create a QName within the NEUID namespace
     *
     * @param name
     * @return
     */
    protected static QName createNEUIDQName(String name) {
        return DocumentHelper.createQName(name, NSTools.NS_NEUID);
    }
}
