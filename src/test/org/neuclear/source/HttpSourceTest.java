package org.neuclear.source;

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

$Id: HttpSourceTest.java,v 1.3 2003/11/22 00:23:48 pelle Exp $
$Log: HttpSourceTest.java,v $
Revision 1.3  2003/11/22 00:23:48  pelle
All unit tests in commons, id and xmlsec now work.
AssetController now successfully processes payments in the unit test.
Payment Web App has working form that creates a TransferRequest presents it to the signer
and forwards it to AssetControlServlet. (Which throws an XML Parser Exception) I think the XMLReaderServlet is bust.

Revision 1.2  2003/11/21 04:45:17  pelle
EncryptedFileStore now works. It uses the PBECipher with DES3 afair.
Otherwise You will Finaliate.
Anything that can be final has been made final throughout everyting. We've used IDEA's Inspector tool to find all instance of variables that could be final.
This should hopefully make everything more stable (and secure).

Revision 1.1  2003/11/05 18:50:34  pelle
Refactored org.neuclear.signers.source.Source and implementing classes to provide support for a local filesystem cache.
Also added Unit tests to make sure it actually works and modified IdentityCreator to write directly to the cache if no output filename is given.

*/

/**
 * User: pelleb
 * Date: Nov 5, 2003
 * Time: 1:27:17 PM
 */
public final class HttpSourceTest extends AbstractSourceTest {
    public HttpSourceTest(final String name) {
        super(name);
    }

    public final Source createSource() {
        return new HttpSource();
    }
}
