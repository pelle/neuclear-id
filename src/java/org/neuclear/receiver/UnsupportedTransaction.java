package org.neuclear.receiver;

import org.neuclear.commons.NeuClearException;
import org.neuclear.id.SignedNamedCore;
import org.neuclear.id.SignedNamedObject;

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

$Id: UnsupportedTransaction.java,v 1.5 2003/11/22 00:23:47 pelle Exp $
$Log: UnsupportedTransaction.java,v $
Revision 1.5  2003/11/22 00:23:47  pelle
All unit tests in commons, id and xmlsec now work.
AssetController now successfully processes payments in the unit test.
Payment Web App has working form that creates a TransferRequest presents it to the signer
and forwards it to AssetControlServlet. (Which throws an XML Parser Exception) I think the XMLReaderServlet is bust.

Revision 1.4  2003/11/21 04:45:13  pelle
EncryptedFileStore now works. It uses the PBECipher with DES3 afair.
Otherwise You will Finaliate.
Anything that can be final has been made final throughout everyting. We've used IDEA's Inspector tool to find all instance of variables that could be final.
This should hopefully make everything more stable (and secure).

Revision 1.3  2003/11/09 03:27:19  pelle
More house keeping and shuffling about mainly pay

Revision 1.2  2003/10/21 22:31:13  pelle
Renamed NeudistException to NeuClearException and moved it to org.neuclear.commons where it makes more sense.
Unhooked the XMLException in the xmlsig library from NeuClearException to make all of its exceptions an independent hierarchy.
Obviously had to perform many changes throughout the code to support these changes.

Revision 1.1  2003/09/26 23:53:10  pelle
Changes mainly in receiver and related fun.
First real neuclear stuff in the payment package. Added TransferContract and AssetControllerReceiver.

*/

/**
 * User: pelleb
 * Date: Sep 26, 2003
 * Time: 1:48:58 PM
 */
public final class UnsupportedTransaction extends NeuClearException {
    /**
     * @param obj 
     */
    public UnsupportedTransaction(final SignedNamedCore obj) {
        super("NeuClear Transaction not Supported by this Reader: " + obj.getClass().getName());
    }

    public UnsupportedTransaction(final SignedNamedObject obj) {
        super("NeuClear Transaction not Supported by this Receiver: " + obj.getClass().getName());
    }
}
