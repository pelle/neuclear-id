package org.neuclear.receiver;

import org.neuclear.commons.NeuClearException;
import org.neuclear.id.SignedNamedObject;
import org.neuclear.xml.ElementProxy;

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

$Id: MockReceiver.java,v 1.1 2003/12/11 23:57:30 pelle Exp $
$Log: MockReceiver.java,v $
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
    /**
     * Add your main transaction processing logic within this method.
     * Remember you must check the validity of the SignedNamedObject here. Until you do so
     * you can not trust it.
     * 
     * @param obj 
     * @throws UnsupportedTransaction 
     */
    public ElementProxy receive(SignedNamedObject obj) throws UnsupportedTransaction, NeuClearException {
        received = obj;
        return;
    }

    public SignedNamedObject getLastReceived() {
        return received;
    }

    private SignedNamedObject received = null;
}
