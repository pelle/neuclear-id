package org.neuclear.signers;

import org.neuclear.commons.NeuClearException;
import org.neuclear.passphraseagents.PassPhraseAgent;
import org.neudist.crypto.CryptoTools;

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

$Id: DefaultSigner.java,v 1.2 2003/10/31 23:58:53 pelle Exp $
$Log: DefaultSigner.java,v $
Revision 1.2  2003/10/31 23:58:53  pelle
The IdentityCreator now fully works with the new Signer architecture.

Revision 1.1  2003/10/29 21:16:28  pelle
Refactored the whole signing process. Now we have an interface called Signer which is the old SignerStore.
To use it you pass a byte array and an alias. The sign method then returns the signature.
If a Signer needs a passphrase it uses a PassPhraseAgent to present a dialogue box, read it from a command line etc.
This new Signer pattern allows us to use secure signing hardware such as N-Cipher in the future for server applications as well
as SmartCards for end user applications.

*/

/**
 * Easy to use preconfigured Signer that uses the standard default JCE KeyStore
 * User: pelleb
 * Date: Oct 29, 2003
 * Time: 3:22:17 PM
 */
public class DefaultSigner extends JCESigner {
    public DefaultSigner(PassPhraseAgent agent) throws NeuClearException, GeneralSecurityException {
        super(CryptoTools.DEFAULT_KEYSTORE, "jks", "SUN", agent);

    }

}
