package org.neuclear.id;

import org.dom4j.Element;
import org.neuclear.commons.NeuClearException;
import org.neuclear.id.senders.Sender;
import org.neuclear.id.targets.Targets;
import org.neuclear.xml.xmlsec.KeyInfo;
import org.neuclear.xml.xmlsec.XMLSecurityException;

import java.security.PublicKey;

/*
$Id: Service.java,v 1.3 2004/04/23 23:34:11 pelle Exp $
$Log: Service.java,v $
Revision 1.3  2004/04/23 23:34:11  pelle
Major update. Added an original url and nickname to Identity and friends.

Revision 1.2  2004/04/17 19:28:22  pelle
Identity is now fully html based as is the ServiceBuilder.
VerifyingReader correctly identifies html files and parses them as such.
Targets and Target now parse html link tags
AssetBuilder and ExchangeAgentBuilder have been updated to support it and provide html formatted contracts.
The Asset.Reader and ExchangeAgent.Reader still need to be updated.

Revision 1.1  2004/04/05 16:32:52  pelle
Created new ServiceBuilder class for creating services. A service is an identity that has a seperate service URL and Service Public Key.

*/

/**
 * User: pelleb
 * Date: Apr 5, 2004
 * Time: 9:24:06 AM
 */
public class Service extends Identity {
    public Service(final SignedNamedCore core, String nickname, String original, String serviceUrl, PublicKey serviceKey, Targets targets) {
        super(core, nickname, original, targets);
        this.serviceKey = serviceKey;
        this.serviceUrl = serviceUrl;
    }

    public final PublicKey getServiceKey() {
        return serviceKey;
    }

    public final String getServiceUrl() {
        return serviceUrl;
    }

    public final SignedNamedObject service(SignedNamedObject obj) throws NeuClearException {
        return Sender.quickSend(serviceUrl, obj);
    }

    protected static PublicKey extractPublicKey(Element kiElem) throws XMLSecurityException {
        final KeyInfo sKi = new KeyInfo(kiElem);
        return sKi.getPublicKey();
    }

    protected final String serviceUrl;
    protected final PublicKey serviceKey;
}
