package org.neuclear.id.builders;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.QName;
import org.neuclear.id.InvalidNamedObjectException;
import org.neuclear.xml.xmlsec.KeyInfo;

import java.security.PublicKey;

/*
$Id: ServiceBuilder.java,v 1.1 2004/04/05 16:32:52 pelle Exp $
$Log: ServiceBuilder.java,v $
Revision 1.1  2004/04/05 16:32:52  pelle
Created new ServiceBuilder class for creating services. A service is an identity that has a seperate service URL and Service Public Key.

*/

/**
 * User: pelleb
 * Date: Apr 5, 2004
 * Time: 9:28:55 AM
 */
public class ServiceBuilder extends IdentityBuilder {
    public ServiceBuilder(final QName tag, final String serviceUrl, PublicKey serviceKey) throws InvalidNamedObjectException {
        super(tag, null);
        final Element serviceElem = DocumentHelper.createElement(createNEUIDQName("Service"));
        serviceElem.add(new KeyInfo(serviceKey).getElement());
        final Element urlElem = DocumentHelper.createElement(createNEUIDQName("Url"));
        urlElem.setText(serviceUrl);
        serviceElem.add(urlElem);
        getElement().add(serviceElem);

    }
}
