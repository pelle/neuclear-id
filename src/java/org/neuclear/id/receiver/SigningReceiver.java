package org.neuclear.id.receiver;

import org.neuclear.commons.crypto.signers.Signer;

/*
$Id: SigningReceiver.java,v 1.1 2004/07/21 23:04:05 pelle Exp $
$Log: SigningReceiver.java,v $
Revision 1.1  2004/07/21 23:04:05  pelle
Added DelegatingReceiver and friends. This was created to make it easier to create complex services in NeuClear.

*/

/**
 * User: pelleb
 * Date: Jul 21, 2004
 * Time: 11:04:22 AM
 */
public abstract class SigningReceiver implements Receiver {
    protected SigningReceiver(Signer signer) {
        this.signer = signer;
    }

    final protected Signer signer;
}
