package org.neuclear.id;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

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

$Id: SignedNamedDocument.java,v 1.1 2004/01/10 00:03:21 pelle Exp $
$Log: SignedNamedDocument.java,v $
Revision 1.1  2004/01/10 00:03:21  pelle
Implemented new Schema for Transfer*
Working on it for Exchange*, so far all Receipts are implemented.
Added SignedNamedDocument which is a generic SignedNamedObject that works with all Signed XML.
Changed SignedNamedObject.getDigest() from byte array to String.
The whole malarchy in neuclear-pay does not build yet. The refactoring is a big job, but getting there.

*/

/**
 * A general purpose object XML information from an arbitrary signed Document.
 */
public class SignedNamedDocument extends SignedNamedObject{

    private SignedNamedDocument(SignedNamedCore core) {
        super(core);
    }

    /**
     * This parses the document from the encoded version and returns it to you.
     * Note it parses it on every call, so you might want to
     * @return
     * @throws DocumentException
     */
    public Document getDocument() throws DocumentException {
        return DocumentHelper.parseText(getEncoded());
    }

    final public static class Reader implements NamedObjectReader {
        /**
         * Read object from Element and fill in its details
         *
         * @param elem
         * @return
         */
        public SignedNamedObject read(final SignedNamedCore core, final Element elem)  {
            return new SignedNamedDocument(core);
        }
    }

}
