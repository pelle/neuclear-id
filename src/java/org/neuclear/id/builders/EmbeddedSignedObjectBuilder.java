package org.neuclear.id.builders;

import org.neuclear.xml.xmlsec.SignedElement;
import org.neuclear.xml.xmlsec.XMLSecurityException;
import org.neuclear.id.SignedNamedObject;
import org.neuclear.id.NameResolutionException;
import org.neuclear.id.InvalidNamedObjectException;
import org.neuclear.commons.crypto.signers.Signer;
import org.neuclear.commons.crypto.signers.NonExistingSignerException;
import org.neuclear.commons.crypto.passphraseagents.UserCancellationException;
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
public class EmbeddedSignedObjectBuilder extends Builder {
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
}
