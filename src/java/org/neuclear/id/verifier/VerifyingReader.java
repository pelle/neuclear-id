package org.neuclear.id.verifier;

import org.neudist.xml.xmlsec.SignedElement;
import org.neudist.xml.xmlsec.XMLSecurityException;
import org.neudist.xml.xmlsec.XMLSecTools;
import org.neudist.xml.XMLTools;
import org.neudist.xml.XMLException;
import org.neudist.utils.NeudistException;
import org.neudist.crypto.CryptoTools;
import org.dom4j.Namespace;
import org.dom4j.Element;
import org.dom4j.QName;
import org.dom4j.DocumentHelper;
import org.neuclear.id.*;
import org.neuclear.id.resolver.NSResolver;
import org.neuclear.id.cache.NSCache;
import org.neuclear.time.TimeTools;

import java.security.PublicKey;
import java.io.InputStream;
import java.util.Map;
import java.util.HashMap;
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

$Id: VerifyingReader.java,v 1.2 2003/10/01 17:05:38 pelle Exp $
$Log: VerifyingReader.java,v $
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
 * 
 * User: pelleb
 * Date: Sep 23, 2003
 * Time: 4:47:15 PM
 */
public class VerifyingReader {
    private VerifyingReader(){
        readers=new HashMap();
        readers.put("Identity",new Identity.Reader());
        defaultReader=new SignedNamedObject.Reader();
    }

    public static VerifyingReader getInstance() {
        return new VerifyingReader();
    }
    /**
     * Read Object from input stream.
     * Verify signature with parent Identity
     * @param is
     * @return
     * @throws NeudistException
     */
    public SignedNamedObject read(InputStream is) throws NeudistException {
        Element elem=XMLTools.loadDocument(is).getRootElement();
        String name=NSTools.normalizeNameURI(elem.attributeValue(getNameAttrQName()));
        String signatoryName=NSTools.getParentNSURI(name);
        PublicKey pubs[]=null;
        Identity signatory=NSResolver.resolveIdentity(signatoryName);
        if (XMLSecTools.verifySignature(elem,signatory.getPublicKey())) {
            //I should be able to get this from within. This is just a quick hack.
            String digest=new String(CryptoTools.digest(XMLSecTools.canonicalize(elem)));
            Timestamp timestamp=TimeTools.parseTimeStamp(elem.attributeValue("timestamp"));
            return resolveReader(elem).read(elem,name,signatory, digest,timestamp);
        } else
                throw new InvalidIdentityException(name+" isnt valid");
    }

    private NamedObjectReader resolveReader(Element elem){
        NamedObjectReader reader=(NamedObjectReader) readers.get(elem.getName());
        if (reader==null)
            reader=defaultReader;
        return reader;
    }

     private static QName getNameAttrQName() {
        return DocumentHelper.createQName("name",SignedNamedObject.NS_NSDL);

    }
    private Map readers;
    private NamedObjectReader defaultReader;
}
