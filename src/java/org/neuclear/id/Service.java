package org.neuclear.id;

import org.dom4j.Element;
import org.neuclear.id.targets.Targets;
import org.neuclear.xml.xmlsec.KeyInfo;
import org.neuclear.xml.xmlsec.XMLSecurityException;

import java.security.PublicKey;

/*
$Id: Service.java,v 1.1 2004/04/05 16:32:52 pelle Exp $
$Log: Service.java,v $
Revision 1.1  2004/04/05 16:32:52  pelle
Created new ServiceBuilder class for creating services. A service is an identity that has a seperate service URL and Service Public Key.

*/

/**
 * User: pelleb
 * Date: Apr 5, 2004
 * Time: 9:24:06 AM
 */
public class Service extends Identity {
    public Service(final SignedNamedCore core, String serviceUrl, PublicKey serviceKey, Targets targets) {
        super(core, null, targets);
        this.serviceKey = serviceKey;
        this.serviceUrl = serviceUrl;
    }

    public final PublicKey getServiceKey() {
        return serviceKey;
    }

    public final String getServiceUrl() {
        return serviceUrl;
    }

    protected static PublicKey extractPublicKey(Element kiElem) throws XMLSecurityException {
        final KeyInfo sKi = new KeyInfo(kiElem);
        return sKi.getPublicKey();
    }

    protected final String serviceUrl;
    protected final PublicKey serviceKey;
}
