package org.neuclear.id;

import junit.framework.TestCase;
import org.neuclear.commons.NeuClearException;
import org.neuclear.commons.crypto.passphraseagents.UserCancellationException;
import org.neuclear.commons.crypto.signers.NonExistingSignerException;
import org.neuclear.commons.crypto.signers.TestCaseSigner;
import org.neuclear.id.builders.AuthenticationTicketBuilder;
import org.neuclear.id.builders.Builder;
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

$Id: SignedNamedCoreTest.java,v 1.9 2004/04/01 23:19:51 pelle Exp $
$Log: SignedNamedCoreTest.java,v $
Revision 1.9  2004/04/01 23:19:51  pelle
Split Identity into Signatory and Identity class.
Identity remains a signed named object and will in the future just be used for self declared information.
Signatory now contains the PublicKey etc and is NOT a signed object.

Revision 1.8  2004/01/19 17:55:00  pelle
Updated the NeuClear ID naming scheme to support various levels of semantics

Revision 1.7  2004/01/14 06:42:15  pelle
Got rid of the verifyXXX() methods

Revision 1.6  2004/01/13 23:38:26  pelle
Refactoring parts of the core of XMLSignature. There shouldnt be any real API changes.

Revision 1.5  2004/01/12 22:39:26  pelle
Completed all the builders and contracts.
Added a new abstract Value class to contain either an amount or a list of serial numbers.
Now ready to finish off the AssetControllers.

Revision 1.4  2003/12/11 23:57:30  pelle
Trying to test the ReceiverServlet with cactus. Still no luck. Need to return a ElementProxy of some sort.
Cleaned up some missing fluff in the ElementProxy interface. getTagName(), getQName() and getNameSpace() have been killed.

Revision 1.3  2003/11/22 00:23:48  pelle
All unit tests in commons, id and xmlsec now work.
AssetController now successfully processes payments in the unit test.
Payment Web App has working form that creates a TransferRequest presents it to the signer
and forwards it to AssetControlServlet. (Which throws an XML Parser Exception) I think the XMLReaderServlet is bust.

Revision 1.2  2003/11/21 04:45:17  pelle
EncryptedFileStore now works. It uses the PBECipher with DES3 afair.
Otherwise You will Finaliate.
Anything that can be final has been made final throughout everyting. We've used IDEA's Inspector tool to find all instance of variables that could be final.
This should hopefully make everything more stable (and secure).

Revision 1.1  2003/11/20 23:42:24  pelle
Getting all the tests to work in id
Removing usage of BC in CryptoTools as it was causing issues.
First version of EntityLedger that will use OFB's EntityEngine. This will allow us to support a vast amount databases without
writing SQL. (Yipee)

*/

/**
 * User: pelleb
 * Date: Nov 20, 2003
 * Time: 5:26:22 PM
 */
public final class SignedNamedCoreTest extends TestCase {

    public SignedNamedCoreTest(String string) throws NeuClearException {
        super(string);
        signer = new TestCaseSigner();
    }

    public final void testCreateNymous() throws InvalidNamedObjectException, XMLException, NonExistingSignerException, UserCancellationException {
        final String name = "neu://bob@test";
        Signatory bobx = new Signatory(signer.getPublicKey(name));
        assertNotNull(bobx);
        assertNotNull(bobx.getName());
        System.out.println(bobx.getName());
        assertNotNull(bobx.getPublicKey());
    }

    public final void testRead() throws NeuClearException, GeneralSecurityException, XMLException, FileNotFoundException {
        final String name = "neu://bob@test";
        final Builder builder = new AuthenticationTicketBuilder(name, "neu://test", "http://slashdot.org");
        System.out.println("=====");
        System.out.println(builder.asXML());

        builder.sign(name, signer);
        System.out.println(builder.asXML());

        assertTrue(builder.verify());
        try {
            final SignedNamedCore core = SignedNamedCore.read(builder.getElement());
//            assertEquals(core.getSignatory().getName(), name);
        } catch (InvalidNamedObjectException e) {
            assertTrue(e.getLocalizedMessage(), false);
        }
    }

    private TestCaseSigner signer;
}
