package org.neuclear.id;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.neuclear.commons.NeuClearException;
import org.neuclear.id.builders.NamedObjectBuilder;
import org.neudist.xml.xmlsec.XMLSecurityException;

import java.sql.Timestamp;

/*
NeuClear Distributed Transaction Clearing Platform
(C) 2003 Pelle Braendgaard

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 2.1 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library; if not, write to the Free Software
Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

$Id: SignatureRequest.java,v 1.1 2003/11/06 20:01:54 pelle Exp $
$Log: SignatureRequest.java,v $
Revision 1.1  2003/11/06 20:01:54  pelle
Implemented AuthenticationTicket and friends to comply with the newer model.
Created SignatureRequest and friends to send unsigned NamedObjectBuilders to interactive signing services.

*/

/**
 * The SignatureRequest presents an object for signing to a signing service.
 * The SignatureRequest would typically be created using a SignatureRequestBuilder buy
 * the Requesting site. The Users Signature service would present it to the user who signs it.
 * User: pelleb
 * Date: Nov 6, 2003
 * Time: 12:23:52 PM
 */
public class SignatureRequest extends SignedNamedObject {
    private SignatureRequest(String name, Identity signer, Timestamp timestamp, String digest, String userid, NamedObjectBuilder unsigned, String description) throws NeuClearException {
        super(name, signer, timestamp, digest);
        this.userid = userid;
        this.unsigned = unsigned;
        this.description = description;
    }

    public String getUserid() {
        return userid;
    }

    public NamedObjectBuilder getUnsigned() {
        return unsigned;
    }

    public String getDescription() {
        return description;
    }

    public final static class Reader implements NamedObjectReader {
        /**
         * Read object from Element and fill in its details
         * 
         * @param elem 
         * @return 
         */
        public SignedNamedObject read(Element elem, String name, Identity signatory, String digest, Timestamp timestamp) throws XMLSecurityException, NeuClearException {
            Element request = elem.element(DocumentHelper.createQName("Unsigned", NSTools.NS_NEUID));
            String userid = request.attributeValue(DocumentHelper.createQName("userid", NSTools.NS_NEUID));
            NamedObjectBuilder unsigned = new NamedObjectBuilder((Element) request.elements().get(0));
            String description = null;
            Element descrelem = elem.element(DocumentHelper.createQName("Description", NSTools.NS_NEUID));
            if (descrelem != null)
                description = descrelem.getText();

            return new SignatureRequest(name, signatory, timestamp, digest, userid, unsigned, description);
        }

    }

    private final String userid;
    private final NamedObjectBuilder unsigned;
    private final String description;
    public final static String SIGREQUEST_TAG = "SignatureRequest";

}
