package org.neuclear.id.verifier;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.QName;
import org.neuclear.auth.AuthenticationTicket;
import org.neuclear.commons.NeuClearException;
import org.neuclear.id.*;
import org.neuclear.id.resolver.NSResolver;
import org.neuclear.time.TimeTools;
import org.neudist.crypto.CryptoTools;
import org.neudist.xml.XMLException;
import org.neudist.xml.XMLTools;
import org.neudist.xml.xmlsec.XMLSecTools;

import java.io.InputStream;
import java.security.PublicKey;
import java.sql.Timestamp;
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

$Id: VerifyingReader.java,v 1.6 2003/11/06 20:01:54 pelle Exp $
$Log: VerifyingReader.java,v $
Revision 1.6  2003/11/06 20:01:54  pelle
Implemented AuthenticationTicket and friends to comply with the newer model.
Created SignatureRequest and friends to send unsigned NamedObjectBuilders to interactive signing services.

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
public class VerifyingReader {
    private VerifyingReader() {
        readers = new HashMap();
        readers.put("Identity", new Identity.Reader());
        readers.put(AuthenticationTicket.TAG_NAME, new AuthenticationTicket.Reader());
        readers.put(SignatureRequest.SIGREQUEST_TAG, new SignatureRequest.Reader());
        defaultReader = new SignedNamedObject.Reader();
    }

    public static VerifyingReader getInstance() {
        return instance;
    }

    /**
     * Read Object from input stream.
     * Verify signature with parent Identity
     * 
     * @param is 
     * @return 
     * @throws NeuClearException 
     */
    public SignedNamedObject read(InputStream is) throws XMLException, NeuClearException {
        Element elem = XMLTools.loadDocument(is).getRootElement();
        String name = NSTools.normalizeNameURI(elem.attributeValue(getNameAttrQName()));
        String signatoryName = NSTools.getParentNSURI(name);
        PublicKey pubs[] = null;
        Identity signatory = NSResolver.resolveIdentity(signatoryName);
        if (XMLSecTools.verifySignature(elem, signatory.getPublicKey())) {
            //I should be able to get this from within. This is just a quick hack.
            String digest = new String(CryptoTools.digest(XMLSecTools.canonicalize(elem)));
            Timestamp timestamp = TimeTools.parseTimeStamp(elem.attributeValue("timestamp"));
            return resolveReader(elem).read(elem, name, signatory, digest, timestamp);
        } else
            throw new InvalidIdentityException(name + " isnt valid");
    }

    private NamedObjectReader resolveReader(Element elem) {
        NamedObjectReader reader = (NamedObjectReader) readers.get(elem.getName());
        if (reader == null)
            reader = defaultReader;
        return reader;
    }

    private static QName getNameAttrQName() {
        return DocumentHelper.createQName("name", NSTools.NS_NEUID);

    }

    public void registerReader(String name, NamedObjectReader reader) {
        readers.put(name, reader);
    }

    private Map readers;
    private NamedObjectReader defaultReader;
    private static VerifyingReader instance = new VerifyingReader();
}
