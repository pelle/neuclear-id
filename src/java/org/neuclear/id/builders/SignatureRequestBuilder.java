package org.neuclear.id.builders;

import org.dom4j.Element;
import org.neuclear.commons.NeuClearException;
import org.neuclear.commons.Utility;
import org.neuclear.id.SignatureRequest;

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

$Id: SignatureRequestBuilder.java,v 1.11 2004/04/14 23:44:28 pelle Exp $
$Log: SignatureRequestBuilder.java,v $
Revision 1.11  2004/04/14 23:44:28  pelle
Got the cactus tests working and the sample web app

Revision 1.10  2004/01/19 23:49:44  pelle
Unit testing uncovered further issues with Base32
NSTools is now uptodate as are many other classes. All transactional builders habe been updated.
Well on the way towards full "green" on Junit.

Revision 1.9  2004/01/13 15:11:35  pelle
Now builds.
Now need to do unit tests

Revision 1.8  2004/01/12 22:39:26  pelle
Completed all the builders and contracts.
Added a new abstract Value class to contain either an amount or a list of serial numbers.
Now ready to finish off the AssetControllers.

Revision 1.7  2003/12/11 23:57:29  pelle
Trying to test the ReceiverServlet with cactus. Still no luck. Need to return a ElementProxy of some sort.
Cleaned up some missing fluff in the ElementProxy interface. getTagName(), getQName() and getNameSpace() have been killed.

Revision 1.6  2003/12/11 16:29:26  pelle
Updated various builders to use the new helper methods in AbstractElementProxy hopefully making them more readable.

Revision 1.5  2003/12/10 23:58:51  pelle
Did some cleaning up in the builders
Fixed some stuff in IdentityCreator
New maven goal to create executable jarapp
We are close to 0.8 final of ID, 0.11 final of XMLSIG and 0.5 of commons.
Will release shortly.

Revision 1.4  2003/12/06 00:17:03  pelle
Updated various areas in NSTools.
Updated URI Validation in particular to support new expanded format
Updated createUniqueID and friends to be a lot more unique and more efficient.
In CryptoTools updated getRandom() to finally use a SecureRandom.
Changed CryptoTools.getFormatURLSafe to getBase36 because that is what it really is.

Revision 1.3  2003/11/21 04:45:10  pelle
EncryptedFileStore now works. It uses the PBECipher with DES3 afair.
Otherwise You will Finaliate.
Anything that can be final has been made final throughout everyting. We've used IDEA's Inspector tool to find all instance of variables that could be final.
This should hopefully make everything more stable (and secure).

Revision 1.2  2003/11/11 21:18:42  pelle
Further vital reshuffling.
org.neudist.crypto.* and org.neudist.utils.* have been moved to respective areas under org.neuclear.commons
org.neuclear.signers.* as well as org.neuclear.passphraseagents have been moved under org.neuclear.commons.crypto as well.
Did a bit of work on the Canonicalizer and changed a few other minor bits.

Revision 1.1  2003/11/06 20:01:54  pelle
Implemented AuthenticationTicket and friends to comply with the newer model.
Created SignatureRequest and friends to receive unsigned NamedObjectBuilders to interactive signing services.

*/

/**
 * User: pelleb
 * Date: Nov 6, 2003
 * Time: 12:45:14 PM
 */
public final class SignatureRequestBuilder extends Builder {
    public SignatureRequestBuilder(final Builder unsigned, final String description) throws NeuClearException {
        super(createNEUIDQName(SignatureRequest.SIGREQUEST_TAG));
        final Element unsignedElem = addElement("Unsigned");
        unsignedElem.add(unsigned.getElement());
        if (!Utility.isEmpty(description))
            addElement("Description").setText(description);

    }

}
