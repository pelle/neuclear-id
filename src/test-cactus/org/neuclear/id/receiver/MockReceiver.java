package org.neuclear.id.receiver;

import org.neuclear.commons.NeuClearException;
import org.neuclear.commons.crypto.signers.InvalidPassphraseException;
import org.neuclear.commons.crypto.signers.Signer;
import org.neuclear.commons.crypto.signers.TestCaseSigner;
import org.neuclear.id.SignedNamedObject;
import org.neuclear.id.builders.AuthenticationTicketBuilder;

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

$Id: MockReceiver.java,v 1.2 2004/04/14 23:44:46 pelle Exp $
$Log: MockReceiver.java,v $
Revision 1.2  2004/04/14 23:44:46  pelle
Got the cactus tests working and the sample web app

Revision 1.1  2004/03/02 18:59:13  pelle
Further cleanups in neuclear-id. Moved everything under id.

Revision 1.3  2004/01/13 15:11:36  pelle
Now builds.
Now need to do unit tests

Revision 1.2  2003/12/12 00:13:11  pelle
This may actually work now. Need to put a few more test cases in to make sure.

Revision 1.1  2003/12/11 23:57:30  pelle
Trying to test the ReceiverServlet with cactus. Still no luck. Need to return a ElementProxy of some sort.
Cleaned up some missing fluff in the ElementProxy interface. getTagName(), getQName() and getNameSpace() have been killed.

*/

/**
 * User: pelleb
 * Date: Dec 11, 2003
 * Time: 6:03:55 PM
 */
public class MockReceiver implements Receiver {
    public MockReceiver() {
        try {
            signer = new TestCaseSigner();
        } catch (InvalidPassphraseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Add your main transaction processing logic within this method.
     * Remember you must check the validity of the SignedNamedObject here. Until you do so
     * you can not trust it.
     *
     * @param obj
     * @throws UnsupportedTransaction
     */
    public SignedNamedObject receive(SignedNamedObject obj) throws UnsupportedTransaction, NeuClearException {
        received = obj;
        return new AuthenticationTicketBuilder("http://localhost").convert("neu://bob@test", signer);//Just some dummy
    }

    public SignedNamedObject getLastReceived() {
        return received;
    }

    private Signer signer;
    private SignedNamedObject received = null;
}
