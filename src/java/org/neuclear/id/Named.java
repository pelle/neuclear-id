package org.neuclear.id;

import org.neuclear.commons.NeuClearException;

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

$Id: Named.java,v 1.4 2003/12/10 23:58:51 pelle Exp $
$Log: Named.java,v $
Revision 1.4  2003/12/10 23:58:51  pelle
Did some cleaning up in the builders
Fixed some stuff in IdentityCreator
New maven goal to create executable jarapp
We are close to 0.8 final of ID, 0.11 final of XMLSIG and 0.5 of commons.
Will release shortly.

Revision 1.3  2003/10/21 22:31:13  pelle
Renamed NeudistException to NeuClearException and moved it to org.neuclear.commons where it makes more sense.
Unhooked the XMLException in the xmlsig library from NeuClearException to make all of its exceptions an independent hierarchy.
Obviously had to perform many changes throughout the code to support these changes.

Revision 1.2  2003/10/01 17:05:37  pelle
Moved the NeuClearCertificate class to be an inner class of Identity.

Revision 1.1  2003/09/24 23:56:48  pelle
Refactoring nearly done. New model for creating signed objects.
With view for supporting the xmlpull api shortly for performance reasons.
Currently still uses dom4j but that has been refactored out that it
should now be very quick to implement a xmlpull implementation.

A side benefit of this is that the API has been further simplified. I still have some work
todo with regards to cleaning up some of the outlying parts of the code.

*/

/**
 * User: pelleb
 * Date: Sep 23, 2003
 * Time: 4:06:57 PM
 */
public interface Named {
    String getName();

    String getLocalName() throws NeuClearException;
}
