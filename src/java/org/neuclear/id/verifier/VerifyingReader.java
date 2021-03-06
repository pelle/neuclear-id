package org.neuclear.id.verifier;

import org.dom4j.Attribute;
import org.dom4j.Element;
import org.neuclear.id.*;
import org.neuclear.id.auth.AuthenticationTicket;
import org.neuclear.id.resolver.Resolver;
import org.neuclear.xml.XMLException;
import org.neuclear.xml.XMLTools;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

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

$Id: VerifyingReader.java,v 1.26 2004/05/21 19:24:28 pelle Exp $
$Log: VerifyingReader.java,v $
Revision 1.26  2004/05/21 19:24:28  pelle
Changed name of Neuclear Personal Signer to NeuClear Personal Trader
More changes from Personality to Account
Moved hibernates.properties out from the jar file and to the test directory and where ever it gets used, to avoid conflicts between multiple files.

Revision 1.25  2004/04/19 18:44:17  pelle
Stores a cache on disk

Revision 1.24  2004/04/17 19:28:22  pelle
Identity is now fully html based as is the ServiceBuilder.
VerifyingReader correctly identifies html files and parses them as such.
Targets and Target now parse html link tags
AssetBuilder and ExchangeAgentBuilder have been updated to support it and provide html formatted contracts.
The Asset.Reader and ExchangeAgent.Reader still need to be updated.

Revision 1.23  2004/04/02 17:33:31  pelle
Added automatic caching of SignedNamedObject

Revision 1.22  2004/03/22 20:09:49  pelle
Added simple ledger for unit testing and in memory use

Revision 1.21  2004/03/03 23:26:43  pelle
Updated various tests to use the AbstractObjectCreationTest

Revision 1.20  2004/03/02 18:59:11  pelle
Further cleanups in neuclear-id. Moved everything under id.

Revision 1.19  2004/02/19 15:30:21  pelle
Various cleanups and corrections

Revision 1.18  2004/01/10 00:03:20  pelle
Implemented new Schema for Transfer*
Working on it for Exchange*, so far all Receipts are implemented.
Added SignedNamedDocument which is a generic SignedNamedObject that works with all Signed XML.
Changed SignedNamedObject.getDigest() from byte array to String.
The whole malarchy in neuclear-pay does not build yet. The refactoring is a big job, but getting there.

Revision 1.17  2003/12/19 18:03:34  pelle
Revamped a lot of exception handling throughout the framework, it has been simplified in most places:
- For most cases the main exception to worry about now is InvalidNamedObjectException.
- Most lowerlevel exception that cant be handled meaningful are now wrapped in the LowLevelException, a
  runtime exception.
- Source and Store patterns each now have their own exceptions that generalizes the various physical
  exceptions that can happen in that area.

Revision 1.16  2003/12/16 15:05:00  pelle
Added SignedMessage contract for signing simple textual contracts.
Added NeuSender, updated SmtpSender and Sender to take plain email addresses (without the mailto:)
Added AbstractObjectCreationTest to make it quicker to write unit tests to verify
NamedObjectBuilder/SignedNamedObject Pairs.
Sample application has been expanded with a basic email application.
Updated docs for simple web app.
Added missing LGPL LICENSE.txt files to signer and simple app

Revision 1.15  2003/12/10 23:58:51  pelle
Did some cleaning up in the builders
Fixed some stuff in IdentityCreator
New maven goal to create executable jarapp
We are close to 0.8 final of ID, 0.11 final of XMLSIG and 0.5 of commons.
Will release shortly.

Revision 1.14  2003/11/22 00:23:46  pelle
All unit tests in commons, id and xmlsec now work.
AssetController now successfully processes payments in the unit test.
Payment Web App has working form that creates a TransferRequest presents it to the signer
and forwards it to AssetControlServlet. (Which throws an XML Parser Exception) I think the XMLReaderServlet is bust.

Revision 1.13  2003/11/21 04:45:12  pelle
EncryptedFileStore now works. It uses the PBECipher with DES3 afair.
Otherwise You will Finaliate.
Anything that can be final has been made final throughout everyting. We've used IDEA's Inspector tool to find all instance of variables that could be final.
This should hopefully make everything more stable (and secure).

Revision 1.12  2003/11/20 23:42:24  pelle
Getting all the tests to work in id
Removing usage of BC in CryptoTools as it was causing issues.
First version of EntityLedger that will use OFB's EntityEngine. This will allow us to support a vast amount databases without
writing SQL. (Yipee)

Revision 1.11  2003/11/20 16:01:25  pelle
Did a security review of the basic Verification process and needed to make changes.
I've introduced the SignedNamedCore which all subclasses of SignedNamedObject need to include in their constructor.
What does this mean?
It means that all subclasses of SignedNamedObject have a guaranteed "signed final ticket" that can only be created in one place.
This also simplifies the constructors as well as the NamedObjectReaders.
I've gone through making everything in these contracts that is possible final. Thus further ensuring the security.

Revision 1.10  2003/11/19 23:33:59  pelle
Signers now can generatekeys via the generateKey() method.
Refactored the relationship between SignedNamedObject and NamedObjectBuilder a bit.
SignedNamedObject now contains the full xml which is returned with getEncoded()
This means that it is now possible to further receive on or process a SignedNamedObject, leaving
NamedObjectBuilder for its original purposes of purely generating new Contracts.
NamedObjectBuilder.sign() now returns a SignedNamedObject which is the prefered way of processing it.
Updated all major interfaces that used the old model to use the new model.

Revision 1.9  2003/11/18 00:01:55  pelle
The simple signing web application for logging in and out is now working.
There had been an issue in the canonicalizer when dealing with the embedded object of the SignatureRequest object.

Revision 1.8  2003/11/15 01:58:16  pelle
More work all around on web applications.

Revision 1.7  2003/11/11 21:18:43  pelle
Further vital reshuffling.
org.neudist.crypto.* and org.neudist.utils.* have been moved to respective areas under org.neuclear.commons
org.neuclear.signers.* as well as org.neuclear.passphraseagents have been moved under org.neuclear.commons.crypto as well.
Did a bit of work on the Canonicalizer and changed a few other minor bits.

Revision 1.6  2003/11/06 20:01:54  pelle
Implemented AuthenticationTicket and friends to comply with the newer model.
Created SignatureRequest and friends to receive unsigned NamedObjectBuilders to interactive signing services.

Revision 1.5  2003/10/25 00:39:54  pelle
Fixed SmtpSender it now sends the messages.
Refactored CommandLineSigner. Now it simply signs files read from command line. However new class IdentityCreator
is subclassed and creates new Identities. You can subclass CommandLineSigner to create your own variants.
Several problems with configuration. Trying to solve at the moment. Updated PicoContainer to beta-2

Revision 1.4  2003/10/21 22:31:12  pelle
Renamed NeudistException to NeuClearException and moved it to org.neuclear.commons where it makes more sense.
Unhooked the XMLException in the xmlsig library from NeuClearException to make all of its exceptions an independent hierarchy.
Obviously had to perform many changes throughout the code to support these changes.

Revision 1.3  2003/10/01 19:08:31  pelle
Changed XML Format. Now NameSpace has been modified to Identity also the
xml namespace prefix nsdl has been changed to neuid.
The standard constants for using these have been moved into NSTools.
The NamedObjectBuilder can also now take an Element, such as an unsigned template.

Revision 1.2  2003/10/01 17:05:38  pelle
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
 * Time: 4:47:15 PM
 */
public final class VerifyingReader {
    private VerifyingReader() {
        readers = new HashMap();
        readers.put("identity", new Identity.Reader());
        readers.put("asset", new Identity.Reader());
        readers.put(AuthenticationTicket.TAG_NAME, new AuthenticationTicket.Reader());
        readers.put(SignatureRequest.SIGREQUEST_TAG, new SignatureRequest.Reader());
        readers.put("message", new SignedMessage.Reader());
        defaultReader = new SignedNamedDocument.Reader();
    }

    public static VerifyingReader getInstance() {
        return INSTANCE;
    }

    /**
     * Read Object from input stream.
     * Verify signature with parent Identity
     *
     * @param is
     * @return
     * @throws InvalidNamedObjectException
     */
    public final SignedNamedObject read(final InputStream is) throws InvalidNamedObjectException, NameResolutionException {
        try {
            final Element elem = XMLTools.loadDocument(is).getRootElement();
            return read(elem);
        } catch (XMLException e) {
            throw new InvalidNamedObjectException("unknown", e.getLocalizedMessage());
        }
    }

    public final SignedNamedObject read(final Element elem) throws InvalidNamedObjectException {
        return Resolver.cache(resolveReader(elem).read(SignedNamedCore.read(elem), elem));
    }


    private NamedObjectReader resolveReader(final Element elem) {
        NamedObjectReader reader = (NamedObjectReader) readers.get(extractName(elem));
        if (reader == null)
            reader = defaultReader;
        return reader;
    }

    private String extractName(final Element elem) {
        if (elem.getName().equals("html")) {
            Attribute type = (Attribute) elem.selectSingleNode("//html/head/meta[@name='neu:type']/@content");
            if (type != null && type.getValue() != null)
                return type.getValue().toLowerCase();
            return "identity"; //default to identity
        }
        return elem.getName();
    }


    public final void registerReader(final String name, final NamedObjectReader reader) {
        System.out.println("Registering: " + name);
        readers.put(name, reader);
    }

    private final Map readers;
    private final NamedObjectReader defaultReader;
    private static final VerifyingReader INSTANCE = new VerifyingReader();
}
