package org.neuclear.id.builders;

import org.neuclear.commons.NeuClearException;
import org.neuclear.commons.crypto.signers.PublicKeySource;
import org.neuclear.id.NSTools;
import org.neuclear.id.SignedNamedObject;
import org.neuclear.tests.AbstractSigningTest;
import org.neuclear.xml.XMLException;

import java.io.FileNotFoundException;
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

$Id: IdentityBuilderTest.java,v 1.11 2004/01/20 20:28:24 pelle Exp $
$Log: IdentityBuilderTest.java,v $
Revision 1.11  2004/01/20 20:28:24  pelle
Fixed final issues highlighted by unit tests. Really just a bunch of smaller stuff.

Revision 1.10  2004/01/20 17:39:13  pelle
Further updates to unit tests

Revision 1.9  2003/12/10 23:58:52  pelle
Did some cleaning up in the builders
Fixed some stuff in IdentityCreator
New maven goal to create executable jarapp
We are close to 0.8 final of ID, 0.11 final of XMLSIG and 0.5 of commons.
Will release shortly.

Revision 1.8  2003/11/22 00:23:47  pelle
All unit tests in commons, id and xmlsec now work.
AssetController now successfully processes payments in the unit test.
Payment Web App has working form that creates a TransferRequest presents it to the signer
and forwards it to AssetControlServlet. (Which throws an XML Parser Exception) I think the XMLReaderServlet is bust.

Revision 1.7  2003/11/21 04:45:17  pelle
EncryptedFileStore now works. It uses the PBECipher with DES3 afair.
Otherwise You will Finaliate.
Anything that can be final has been made final throughout everyting. We've used IDEA's Inspector tool to find all instance of variables that could be final.
This should hopefully make everything more stable (and secure).

Revision 1.6  2003/11/19 23:34:00  pelle
Signers now can generatekeys via the generateKey() method.
Refactored the relationship between SignedNamedObject and NamedObjectBuilder a bit.
SignedNamedObject now contains the full xml which is returned with getEncoded()
This means that it is now possible to further receive on or process a SignedNamedObject, leaving
NamedObjectBuilder for its original purposes of purely generating new Contracts.
NamedObjectBuilder.sign() now returns a SignedNamedObject which is the prefered way of processing it.
Updated all major interfaces that used the old model to use the new model.

Revision 1.5  2003/11/18 15:07:37  pelle
Changes to JCE Implementation
Working on getting all tests working including store tests

Revision 1.4  2003/11/18 00:01:56  pelle
The sample signing web application for logging in and out is now working.
There had been an issue in the canonicalizer when dealing with the embedded object of the SignatureRequest object.

Revision 1.3  2003/11/15 01:58:18  pelle
More work all around on web applications.

Revision 1.2  2003/11/13 23:26:42  pelle
The signing service and web authentication application is now almost working.

Revision 1.1  2003/11/12 23:48:14  pelle
Much work done in creating good test environment.
PaymentReceiverTest works, but needs a abit more work in its environment to succeed testing.

*/

/**
 * User: pelleb
 * Date: Nov 12, 2003
 * Time: 5:33:30 PM
 */
public final class IdentityBuilderTest extends AbstractSigningTest {
    public IdentityBuilderTest(final String string) throws GeneralSecurityException, NeuClearException, FileNotFoundException {
        super(string);
    }

    public final void createIdentities(final String name) throws NeuClearException, XMLException {
        if (getSigner().canSignFor(name)) {
            final IdentityBuilder id = new IdentityBuilder(
                    name,
                    ((PublicKeySource) getSigner()).getPublicKey(name),
                    "http://repository.neuclear.org",
                    "http://users.neuclear.org:8080/Signer",
                    "http://logger.neuclear.org",
                    "mailto:pelle@neuclear.org");

            assertEquals("neu://test", NSTools.getSignatoryURI(id.getName()));
            final SignedNamedObject sec = id.convert(name,getSigner());
            assertNotNull(sec);
//            assertEquals(id.getName(), sec.getName());

        } else {
//            assertTrue(false);
            System.out.println("Couldnt sign for: " + name);

        }
    }

    public final void testBuild() throws NeuClearException, XMLException {
//        createIdentities("neu://test");
        createIdentities("neu://bob@test");
        createIdentities("neu://alice@test");

    }

}
