package org.neuclear.id.builders;

import org.neuclear.auth.AuthenticationTicket;
import org.neuclear.commons.NeuClearException;
import org.neuclear.id.InvalidNamedObject;
import org.neuclear.id.SignatureRequest;
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

$Id: SigningRequestBuilderTest.java,v 1.1 2003/11/18 00:01:56 pelle Exp $
$Log: SigningRequestBuilderTest.java,v $
Revision 1.1  2003/11/18 00:01:56  pelle
The sample signing web application for logging in and out is now working.
There had been an issue in the canonicalizer when dealing with the embedded object of the SignatureRequest object.

*/

/**
 * User: pelleb
 * Date: Nov 17, 2003
 * Time: 3:28:05 PM
 */
public class SigningRequestBuilderTest extends AbstractSigningTest {
    public SigningRequestBuilderTest(String string) throws NeuClearException, GeneralSecurityException {
        super(string);
    }

    public void testSignatureRequest() throws NeuClearException, XMLException {
        AuthenticationTicketBuilder authreq = new AuthenticationTicketBuilder("neu://bob@test", "neu://test", "http://users.neuclear.org:8080");
        SignatureRequestBuilder sigreq = new SignatureRequestBuilder("neu://test", "neu://bob@test", authreq, "For testing purposes");
        assertEquals(sigreq.getParent().getName(), "neu://test");
        sigreq.sign(signer);
        assertTrue(sigreq.isSigned());
        try {
            SignatureRequest tosign = (SignatureRequest) sigreq.verify();
            assertEquals(tosign.getName(), sigreq.getName());
            NamedObjectBuilder auth2 = tosign.getUnsigned();
            assertEquals(auth2.getParent().getName(), "neu://bob@test");
            auth2.sign(signer);
            assertTrue(auth2.isSigned());

            AuthenticationTicket auth = (AuthenticationTicket) auth2.verify();
            assertEquals(auth.getName(), authreq.getName());
            assertEquals(auth.getSiteHref(), "http://users.neuclear.org:8080");
        } catch (InvalidNamedObject e) {
            assertTrue(false);
        }

    }
}
