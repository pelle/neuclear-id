package org.neuclear.id.builders;

import org.neuclear.id.auth.AuthenticationTicket;
import org.neuclear.commons.NeuClearException;
import org.neuclear.commons.crypto.signers.NonExistingSignerException;
import org.neuclear.id.InvalidNamedObjectException;
import org.neuclear.id.SignatureRequest;
import org.neuclear.id.SignedNamedObject;
import org.neuclear.id.NameResolutionException;
import org.neuclear.tests.AbstractSigningTest;
import org.neuclear.tests.AbstractObjectCreationTest;
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

$Id: SigningRequestBuilderTest.java,v 1.12 2004/03/03 23:26:45 pelle Exp $
$Log: SigningRequestBuilderTest.java,v $
Revision 1.12  2004/03/03 23:26:45  pelle
Updated various tests to use the AbstractObjectCreationTest

Revision 1.11  2004/03/02 18:59:12  pelle
Further cleanups in neuclear-id. Moved everything under id.

Revision 1.10  2004/01/20 17:39:13  pelle
Further updates to unit tests

Revision 1.9  2004/01/19 23:49:45  pelle
Unit testing uncovered further issues with Base32
NSTools is now uptodate as are many other classes. All transactional builders habe been updated.
Well on the way towards full "green" on Junit.

Revision 1.8  2004/01/13 23:38:26  pelle
Refactoring parts of the core of XMLSignature. There shouldnt be any real API changes.

Revision 1.7  2004/01/13 15:11:35  pelle
Now builds.
Now need to do unit tests

Revision 1.6  2003/12/11 23:57:29  pelle
Trying to test the ReceiverServlet with cactus. Still no luck. Need to return a ElementProxy of some sort.
Cleaned up some missing fluff in the ElementProxy interface. getTagName(), getQName() and getNameSpace() have been killed.

Revision 1.5  2003/12/10 23:58:52  pelle
Did some cleaning up in the builders
Fixed some stuff in IdentityCreator
New maven goal to create executable jarapp
We are close to 0.8 final of ID, 0.11 final of XMLSIG and 0.5 of commons.
Will release shortly.

Revision 1.4  2003/11/21 17:55:16  pelle
misc fixes

Revision 1.3  2003/11/21 04:45:17  pelle
EncryptedFileStore now works. It uses the PBECipher with DES3 afair.
Otherwise You will Finaliate.
Anything that can be final has been made final throughout everyting. We've used IDEA's Inspector tool to find all instance of variables that could be final.
This should hopefully make everything more stable (and secure).

Revision 1.2  2003/11/19 23:34:00  pelle
Signers now can generatekeys via the generateKey() method.
Refactored the relationship between SignedNamedObject and NamedObjectBuilder a bit.
SignedNamedObject now contains the full xml which is returned with getEncoded()
This means that it is now possible to further receive on or process a SignedNamedObject, leaving
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
public final class SigningRequestBuilderTest extends AbstractObjectCreationTest {
    public SigningRequestBuilderTest(final String string) throws NeuClearException, GeneralSecurityException {
        super(string);
    }

    protected void verifyObject(SignedNamedObject obj) throws Exception  {
        SignatureRequest tosign=(SignatureRequest) obj;
        assertNotNull(tosign.getUnsigned());
        final Builder auth2 = tosign.getUnsigned();
//            assertEquals(auth2.getSignatory().getName(), "neu://bob@test");
        assertNotNull(auth2);
        assertNotNull(auth2.getElement());
        final AuthenticationTicket auth = (AuthenticationTicket) auth2.convert("neu://bob@test",signer);
        assertTrue(auth2.isSigned());
//            assertEquals(auth.getName(), authreq.getName());
        assertEquals(auth.getSiteHref(), "http://users.neuclear.org:8080");

    }

    protected Class getRequiredClass() {
        return SignatureRequest.class;
    }

    protected Builder createBuilder() throws Exception {
        final AuthenticationTicketBuilder authreq = new AuthenticationTicketBuilder("neu://bob@test", "neu://test", "http://users.neuclear.org:8080");
        return new SignatureRequestBuilder( "neu://bob@test", authreq, "For testing purposes");
    }

}
