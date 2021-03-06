package org.neuclear.tests;

import junit.framework.TestCase;
import org.neuclear.commons.NeuClearException;
import org.neuclear.commons.crypto.Base32;
import org.neuclear.commons.crypto.CryptoTools;
import org.neuclear.commons.crypto.signers.JCESigner;
import org.neuclear.commons.crypto.signers.NonExistingSignerException;
import org.neuclear.commons.crypto.signers.TestCaseSigner;
import org.neuclear.id.Signatory;

import java.security.GeneralSecurityException;
import java.security.KeyPair;

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

$Id: AbstractSigningTest.java,v 1.9 2004/04/14 23:44:45 pelle Exp $
$Log: AbstractSigningTest.java,v $
Revision 1.9  2004/04/14 23:44:45  pelle
Got the cactus tests working and the sample web app

Revision 1.8  2004/04/12 19:27:27  pelle
Hibernate and Pervayler implementations of the Ledger all pass now for both currency and ledger tests.

Revision 1.7  2004/04/01 23:19:50  pelle
Split Identity into Signatory and Identity class.
Identity remains a signed named object and will in the future just be used for self declared information.
Signatory now contains the PublicKey etc and is NOT a signed object.

Revision 1.6  2004/02/18 00:14:34  pelle
Many, many clean ups. I've readded Targets in a new method.
Gotten rid of NamedObjectBuilder and revamped Identity and Resolvers

Revision 1.5  2003/12/17 12:45:57  pelle
NeuClear JCE Certificates now work with KeyStore.
We can now create JCE certificates based on NeuClear Identity's and store them in a keystore.

Revision 1.4  2003/11/22 00:23:47  pelle
All unit tests in commons, id and xmlsec now work.
AssetController now successfully processes payments in the unit test.
Payment Web App has working form that creates a TransferRequest presents it to the signer
and forwards it to AssetControlServlet. (Which throws an XML Parser Exception) I think the XMLReaderServlet is bust.

Revision 1.3  2003/11/21 04:45:16  pelle
EncryptedFileStore now works. It uses the PBECipher with DES3 afair.
Otherwise You will Finaliate.
Anything that can be final has been made final throughout everyting. We've used IDEA's Inspector tool to find all instance of variables that could be final.
This should hopefully make everything more stable (and secure).

Revision 1.2  2003/11/18 15:07:36  pelle
Changes to JCE Implementation
Working on getting all tests working including store tests

Revision 1.1  2003/11/12 23:48:14  pelle
Much work done in creating good test environment.
PaymentReceiverTest works, but needs a abit more work in its environment to succeed testing.

*/

/**
 * User: pelleb
 * Date: Nov 12, 2003
 * Time: 5:36:25 PM
 */
public class AbstractSigningTest extends TestCase {
    public AbstractSigningTest(final String string) throws NeuClearException, GeneralSecurityException {
        super(string);
        CryptoTools.ensureProvider();
        signer = new TestCaseSigner();
        assertNotNull(signer);
        assertNotNull(signer.getPublicKey("neu://bob@test"));

        alice = new Signatory(signer.getPublicKey("neu://alice@test"));
        assertNotNull(alice);
        bob = new Signatory(signer.getPublicKey("neu://bob@test"));
        assertNotNull(bob);
        issuerkp = CryptoTools.createTinyRSAKeyPair();
        issuer = new Signatory(issuerkp.getPublic());
    }

    protected String getPublicKeyName(String alias) throws NonExistingSignerException {
        return Base32.encode(CryptoTools.digest(signer.getPublicKey(alias).getEncoded()));
    }

    /**
     * Handy Test User always available
     * 
     * @return 
     */

    protected final Signatory getBob() {
        return bob;
    }

    /**
     * Handy Test User always available
     * 
     * @return 
     */

    protected final Signatory getAlice() {
        return alice;
    }

    protected final Signatory getIssuer() {
        return issuer;
    }

    protected final JCESigner getSigner() {
        return signer;
    }

    private Signatory bob;
    private Signatory alice;
    private Signatory issuer;
    private KeyPair issuerkp;

    protected final JCESigner signer;
}
