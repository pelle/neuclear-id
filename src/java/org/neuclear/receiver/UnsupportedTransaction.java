package org.neuclear.receiver;

import org.neuclear.id.SignedNamedObject;
import org.neudist.utils.NeudistException;

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

$Id: UnsupportedTransaction.java,v 1.1 2003/09/26 23:53:10 pelle Exp $
$Log: UnsupportedTransaction.java,v $
Revision 1.1  2003/09/26 23:53:10  pelle
Changes mainly in receiver and related fun.
First real neuclear stuff in the payment package. Added TransferContract and PaymentReceiver.

*/

/**
 * 
 * User: pelleb
 * Date: Sep 26, 2003
 * Time: 1:48:58 PM
 */
public class UnsupportedTransaction extends NeudistException {
    /**
     *
     * @param obj
     */
    public UnsupportedTransaction(SignedNamedObject obj) {
        super("NeuClear Transaction not Supported by this Receiver: " + obj.getClass().getName());
    }
}
