package org.neuclear.id;

import org.dom4j.Element;
import org.neudist.utils.NeudistException;

import java.sql.Timestamp;

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

$Id: NamedObjectReader.java,v 1.1 2003/09/24 23:56:48 pelle Exp $
$Log: NamedObjectReader.java,v $
Revision 1.1  2003/09/24 23:56:48  pelle
Refactoring nearly done. New model for creating signed objects.
With view for supporting the xmlpull api shortly for performance reasons.
Currently still uses dom4j but that has been refactored out that it
should now be very quick to implement a xmlpull implementation.

A side benefit of this is that the API has been further simplified. I still have some work
todo with regards to cleaning up some of the outlying parts of the code.

*/

/**
 * 
 * User: pelleb
 * Date: Sep 23, 2003
 * Time: 5:09:57 PM
 */
public interface NamedObjectReader {

    /**
     * Read object from Element and fill in its details
     * @param elem
     * @return
     */
    public SignedNamedObject read(Element elem,String name,Identity signatory,String digest,Timestamp timestamp) throws NeudistException;
}
