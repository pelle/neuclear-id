package org.neuclear.senders;

import org.neuclear.id.SignedNamedObject;
import org.neuclear.id.builders.NamedObjectBuilder;
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

*/

/**
 * 
 * User: pelleb
 * Date: Sep 22, 2003
 * Time: 1:28:39 PM
 */
public abstract class Sender {
    public abstract void send(String endpoint, NamedObjectBuilder obj) throws NeudistException;

    public static void quickSend(String receiver, NamedObjectBuilder obj) {
    }
}
