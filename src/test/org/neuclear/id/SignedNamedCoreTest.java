package org.neuclear.id;

import junit.framework.TestCase;
import org.neuclear.commons.NeuClearException;
import org.neuclear.commons.crypto.passphraseagents.GuiDialogAgent;
import org.neuclear.commons.crypto.signers.DefaultSigner;
import org.neuclear.commons.crypto.signers.JCESigner;
import org.neuclear.commons.crypto.signers.TestCaseSigner;
import org.neuclear.id.builders.AuthenticationTicketBuilder;
import org.neuclear.id.builders.NamedObjectBuilder;
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

$Id: SignedNamedCoreTest.java,v 1.2 2003/11/21 04:45:17 pelle Exp $
$Log: SignedNamedCoreTest.java,v $
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

    public final void testCreateRoot() {
        final SignedNamedCore core = SignedNamedCore.createRootCore();
        assertNotNull(core);
        assertEquals(core.getName(), "neu://");
        assertNull(core.getSignatory());
    }

    public final void testRead() throws NeuClearException, GeneralSecurityException, XMLException, FileNotFoundException {
        final String name = "neu://bob@test";
        final NamedObjectBuilder builder = new AuthenticationTicketBuilder(name, "neu://test", "http://slashdot.org");
        final JCESigner signer = new TestCaseSigner();
        builder.sign(name, signer);
        assertTrue(builder.verifySignature(signer.getPublicKey(name)));
        assertTrue(builder.verifySignature(Identity.getRootPK()));
        assertTrue(builder.verifySignature(Identity.NEUROOT.getPublicKey()));
        try {
            final SignedNamedCore core = SignedNamedCore.read(builder.getElement());
            assertEquals(core.getSignatory().getName(), name);
        } catch (InvalidNamedObject e) {
            assertTrue(e.getLocalizedMessage(), false);
        }


    }
}
