package org.neuclear.id.builders;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.neuclear.id.NSTools;
import org.neuclear.id.SignatureRequest;
import org.neuclear.commons.Utility;

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

$Id: SignatureRequestBuilder.java,v 1.2 2003/11/11 21:18:42 pelle Exp $
$Log: SignatureRequestBuilder.java,v $
Revision 1.2  2003/11/11 21:18:42  pelle
Further vital reshuffling.
org.neudist.crypto.* and org.neudist.utils.* have been moved to respective areas under org.neuclear.commons
org.neuclear.signers.* as well as org.neuclear.passphraseagents have been moved under org.neuclear.commons.crypto as well.
Did a bit of work on the Canonicalizer and changed a few other minor bits.

Revision 1.1  2003/11/06 20:01:54  pelle
Implemented AuthenticationTicket and friends to comply with the newer model.
Created SignatureRequest and friends to send unsigned NamedObjectBuilders to interactive signing services.

*/

/**
 * User: pelleb
 * Date: Nov 6, 2003
 * Time: 12:45:14 PM
 */
public class SignatureRequestBuilder extends NamedObjectBuilder {
    public SignatureRequestBuilder(String requestor, String userid, NamedObjectBuilder unsigned, String description) {
        super(NSTools.createUniqueNamedID(requestor, userid), SignatureRequest.SIGREQUEST_TAG);
        Element unsignedElem = getElement().addElement(DocumentHelper.createQName("Unsigned", NSTools.NS_NEUID));
        unsignedElem.add(unsigned.getElement());
        getElement().addAttribute(DocumentHelper.createQName("userid", NSTools.NS_NEUID), userid);
        if (!Utility.isEmpty(description))
            getElement().addElement(DocumentHelper.createQName("Description")).setText(description);

    }

    public String getTagName() {
        return SignatureRequest.SIGREQUEST_TAG;
    }

}
