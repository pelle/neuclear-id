package org.neuclear.id;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.neuclear.commons.NeuClearException;
import org.neuclear.id.builders.NamedObjectBuilder;
import org.neuclear.id.builders.Builder;
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

$Id: SignatureRequest.java,v 1.11 2004/01/12 22:39:26 pelle Exp $
$Log: SignatureRequest.java,v $
Revision 1.11  2004/01/12 22:39:26  pelle
Completed all the builders and contracts.
Added a new abstract Value class to contain either an amount or a list of serial numbers.
Now ready to finish off the AssetControllers.

Revision 1.10  2003/12/19 18:03:34  pelle
Revamped a lot of exception handling throughout the framework, it has been simplified in most places:
- For most cases the main exception to worry about now is InvalidNamedObjectException.
- Most lowerlevel exception that cant be handled meaningful are now wrapped in the LowLevelException, a
  runtime exception.
- Source and Store patterns each now have their own exceptions that generalizes the various physical
  exceptions that can happen in that area.

Revision 1.9  2003/12/10 23:58:51  pelle
Did some cleaning up in the builders
Fixed some stuff in IdentityCreator
New maven goal to create executable jarapp
We are close to 0.8 final of ID, 0.11 final of XMLSIG and 0.5 of commons.
Will release shortly.

Revision 1.8  2003/11/22 00:23:47  pelle
All unit tests in commons, id and xmlsec now work.
AssetController now successfully processes payments in the unit test.
Payment Web App has working form that creates a TransferRequest presents it to the signer
and forwards it to AssetControlServlet. (Which throws an XML Parser Exception) I think the XMLReaderServlet is bust.

Revision 1.7  2003/11/21 13:57:27  pelle
Changed some mutable fields in immutable classes, making them truely immutable. Thus safer.

Revision 1.6  2003/11/21 04:45:13  pelle
EncryptedFileStore now works. It uses the PBECipher with DES3 afair.
Otherwise You will Finaliate.
Anything that can be final has been made final throughout everyting. We've used IDEA's Inspector tool to find all instance of variables that could be final.
This should hopefully make everything more stable (and secure).

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

Revision 1.3  2003/11/18 00:01:55  pelle
The sample signing web application for logging in and out is now working.
There had been an issue in the canonicalizer when dealing with the embedded object of the SignatureRequest object.

Revision 1.2  2003/11/11 21:18:43  pelle
Further vital reshuffling.
org.neudist.crypto.* and org.neudist.utils.* have been moved to respective areas under org.neuclear.commons
org.neuclear.signers.* as well as org.neuclear.passphraseagents have been moved under org.neuclear.commons.crypto as well.
Did a bit of work on the Canonicalizer and changed a few other minor bits.

Revision 1.1  2003/11/06 20:01:54  pelle
Implemented AuthenticationTicket and friends to comply with the newer model.
Created SignatureRequest and friends to receive unsigned NamedObjectBuilders to interactive signing services.

*/

/**
 * The SignatureRequest presents an object for signing to a signing service.
 * The SignatureRequest would typically be created using a SignatureRequestBuilder buy
 * the Requesting site. The Users Signature service would present it to the user who signs it.
 * <p/>
 * <p/>
 * User: pelleb
 * Date: Nov 6, 2003
 * Time: 12:23:52 PM
 */
public final class SignatureRequest extends SignedNamedObject {
    private SignatureRequest(final SignedNamedCore core, final String userid, final Builder unsigned, final String description)  {
        super(core);
        this.userid = userid;
        this.unsigned = unsigned;
        this.description = description;
    }

    public final String getUserid() {
        return userid;
    }

    public final NamedObjectBuilder getUnsigned() {
        return (NamedObjectBuilder) unsigned.clone();
    }

    public final String getDescription() {
        return description;
    }

    public final static class Reader implements NamedObjectReader {
        /**
         * Read object from Element and fill in its details
         * 
         * @param elem 
         * @return 
         */
        public final SignedNamedObject read(final SignedNamedCore core, final Element elem) throws InvalidNamedObjectException {
            InvalidNamedObjectException.assertElementQName(core,elem,createNEUIDQName(SIGREQUEST_TAG));
            final Element request = InvalidNamedObjectException.assertContainsElementQName(core,elem,createNEUIDQName("Unsigned"));
            final String userid = InvalidNamedObjectException.assertAttributeQName(core,elem,createNEUIDQName("userid"));
            final Element uelem = ((Element) request.elements().get(0)).createCopy();
            final Document doc = DocumentHelper.createDocument(uelem);
            try {
                final Builder unsigned = new NamedObjectBuilder(uelem);
                String description = null;
                final Element descrelem = elem.element(DocumentHelper.createQName("Description", NSTools.NS_NEUID));
                if (descrelem != null)
                    description = descrelem.getText();

                return new SignatureRequest(core, userid, unsigned, description);
            } catch (XMLSecurityException e) {
                throw new InvalidNamedObjectException(core.getName(),e);
            }
        }


    }

    private final String userid;
    private final Builder unsigned;
    private final String description;
    public final static String SIGREQUEST_TAG = "SignatureRequest";

}
