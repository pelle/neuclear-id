package org.neuclear.id;

import org.dom4j.Element;
import org.neuclear.commons.NeuClearException;
import org.neuclear.xml.xmlsec.XMLSecurityException;

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

$Id: NamedObjectReader.java,v 1.7 2003/12/19 18:03:34 pelle Exp $
$Log: NamedObjectReader.java,v $
Revision 1.7  2003/12/19 18:03:34  pelle
Revamped a lot of exception handling throughout the framework, it has been simplified in most places:
- For most cases the main exception to worry about now is InvalidNamedObjectException.
- Most lowerlevel exception that cant be handled meaningful are now wrapped in the LowLevelException, a
  runtime exception.
- Source and Store patterns each now have their own exceptions that generalizes the various physical
  exceptions that can happen in that area.

Revision 1.6  2003/12/10 23:58:51  pelle
Did some cleaning up in the builders
Fixed some stuff in IdentityCreator
New maven goal to create executable jarapp
We are close to 0.8 final of ID, 0.11 final of XMLSIG and 0.5 of commons.
Will release shortly.

Revision 1.5  2003/11/20 16:01:25  pelle
Did a security review of the basic Verification process and needed to make changes.
I've introduced the SignedNamedCore which all subclasses of SignedNamedObject need to include in their constructor.
What does this mean?
It means that all subclasses of SignedNamedObject have a guaranteed "signed final ticket" that can only be created in one place.
This also simplifies the constructors as well as the NamedObjectReaders.
I've gone through making everything in these contracts that is possible final. Thus further ensuring the security.

Revision 1.4  2003/11/19 23:33:59  pelle
Signers now can generatekeys via the generateKey() method.
Refactored the relationship between SignedNamedObject and NamedObjectBuilder a bit.
SignedNamedObject now contains the full xml which is returned with getEncoded()
This means that it is now possible to further receive on or process a SignedNamedObject, leaving
NamedObjectBuilder for its original purposes of purely generating new Contracts.
NamedObjectBuilder.sign() now returns a SignedNamedObject which is the prefered way of processing it.
Updated all major interfaces that used the old model to use the new model.

Revision 1.3  2003/11/11 21:18:43  pelle
Further vital reshuffling.
org.neudist.crypto.* and org.neudist.utils.* have been moved to respective areas under org.neuclear.commons
org.neuclear.signers.* as well as org.neuclear.passphraseagents have been moved under org.neuclear.commons.crypto as well.
Did a bit of work on the Canonicalizer and changed a few other minor bits.

Revision 1.2  2003/10/21 22:31:13  pelle
Renamed NeudistException to NeuClearException and moved it to org.neuclear.commons where it makes more sense.
Unhooked the XMLException in the xmlsig library from NeuClearException to make all of its exceptions an independent hierarchy.
Obviously had to perform many changes throughout the code to support these changes.

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
 * Time: 5:09:57 PM
 */
public interface NamedObjectReader {

    /**
     * Read object from Element and fill in its details
     * 
     * @param elem 
     * @return 
     */
    public SignedNamedObject read(SignedNamedCore core, Element elem) throws InvalidNamedObjectException;
}
