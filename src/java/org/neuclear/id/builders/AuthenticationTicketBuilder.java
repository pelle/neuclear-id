package org.neuclear.id.builders;

import org.dom4j.DocumentHelper;
import org.dom4j.QName;
import org.neuclear.auth.AuthenticationTicket;
import org.neuclear.commons.NeuClearException;
import org.neuclear.id.NSTools;
import org.neuclear.commons.time.TimeTools;

import java.sql.Timestamp;
import java.util.Date;

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

$Id: AuthenticationTicketBuilder.java,v 1.2 2003/11/11 21:18:42 pelle Exp $
$Log: AuthenticationTicketBuilder.java,v $
Revision 1.2  2003/11/11 21:18:42  pelle
Further vital reshuffling.
org.neudist.crypto.* and org.neudist.utils.* have been moved to respective areas under org.neuclear.commons
org.neuclear.signers.* as well as org.neuclear.passphraseagents have been moved under org.neuclear.commons.crypto as well.
Did a bit of work on the Canonicalizer and changed a few other minor bits.

Revision 1.1  2003/11/06 20:01:53  pelle
Implemented AuthenticationTicket and friends to comply with the newer model.
Created SignatureRequest and friends to send unsigned NamedObjectBuilders to interactive signing services.

*/

/**
 * User: pelleb
 * Date: Nov 6, 2003
 * Time: 11:59:58 AM
 */
public class AuthenticationTicketBuilder extends NamedObjectBuilder {
    public AuthenticationTicketBuilder(String user, String requester, String site) throws NeuClearException {
        this(user, requester, new Timestamp(new Date().getTime() + 1800000), site);
    }

    public AuthenticationTicketBuilder(String user, String requester, Date validto, String site) throws NeuClearException {
        super(NSTools.createUniqueNamedID(user, requester), AuthenticationTicket.TAG_NAME, AuthenticationTicket.NS_NSAUTH);
        getElement().addAttribute(createQName("requester"), NSTools.normalizeNameURI(requester));
        getElement().addAttribute(createQName("validto"), TimeTools.formatTimeStamp(validto));
        getElement().addAttribute(createQName("sitehref"), site);
    }

    private static QName createQName(String name) {
        return DocumentHelper.createQName(name, AuthenticationTicket.NS_NSAUTH);
    }

    public String getTagName() {
        return AuthenticationTicket.TAG_NAME;
    }

}
