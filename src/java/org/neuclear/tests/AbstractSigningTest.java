package org.neuclear.tests;

import junit.framework.TestCase;
import org.neuclear.commons.NeuClearException;
import org.neuclear.commons.crypto.CryptoTools;
import org.neuclear.commons.crypto.signers.JCESigner;
import org.neuclear.commons.crypto.signers.TestCaseSigner;
import org.neuclear.id.Identity;
import org.neuclear.id.resolver.NSResolver;

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

$Id: AbstractSigningTest.java,v 1.4 2003/11/22 00:23:47 pelle Exp $
$Log: AbstractSigningTest.java,v $
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
        alice = NSResolver.resolveIdentity("neu://alice@test");
        bob = NSResolver.resolveIdentity("neu://bob@test");
    }

    /**
     * Handy Test User always available
     * 
     * @return 
     */

    protected final Identity getBob() {
        return bob;
    }

    /**
     * Handy Test User always available
     * 
     * @return 
     */

    protected final Identity getAlice() {
        return alice;
    }

    protected final JCESigner getSigner() {
        return signer;
    }

    private Identity bob;
    private Identity alice;
    protected final JCESigner signer;
}
