package org.neuclear.id.builders;

import org.neuclear.auth.AuthenticationTicket;
import org.neuclear.commons.NeuClearException;
import org.neuclear.id.InvalidNamedObject;
import org.neuclear.tests.AbstractSigningTest;
import org.neuclear.xml.XMLException;

import java.security.GeneralSecurityException;

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

$Id: AuthenticationBuilderTest.java,v 1.2 2003/11/19 23:34:00 pelle Exp $
$Log: AuthenticationBuilderTest.java,v $
Revision 1.2  2003/11/19 23:34:00  pelle
Signers now can generatekeys via the generateKey() method.
Refactored the relationship between SignedNamedObject and NamedObjectBuilder a bit.
SignedNamedObject now contains the full xml which is returned with getEncoded()
This means that it is now possible to further send on or process a SignedNamedObject, leaving
NamedObjectBuilder for its original purposes of purely generating new Contracts.
NamedObjectBuilder.sign() now returns a SignedNamedObject which is the prefered way of processing it.
Updated all major interfaces that used the old model to use the new model.

Revision 1.1  2003/11/18 00:01:56  pelle
The sample signing web application for logging in and out is now working.
There had been an issue in the canonicalizer when dealing with the embedded object of the SignatureRequest object.

*/

/**
 * User: pelleb
 * Date: Nov 17, 2003
 * Time: 3:28:05 PM
 */
public class AuthenticationBuilderTest extends AbstractSigningTest {
    public AuthenticationBuilderTest(String string) throws NeuClearException, GeneralSecurityException {
        super(string);
    }

    public void testAuthenticate() throws NeuClearException, XMLException {
        AuthenticationTicketBuilder authreq = new AuthenticationTicketBuilder("neu://bob@test", "neu://test", "http://users.neuclear.org:8080");
        assertEquals(authreq.getParent().getName(), "neu://bob@test");
        try {
            AuthenticationTicket auth = (AuthenticationTicket) authreq.sign(signer);
            assertTrue(authreq.isSigned());
            assertEquals(auth.getName(), authreq.getName());
            assertEquals(auth.getSiteHref(), "http://users.neuclear.org:8080");
        } catch (InvalidNamedObject e) {
            assertTrue(false);
        }

    }
}
