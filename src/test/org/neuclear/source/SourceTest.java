package org.neuclear.source;

import junit.framework.TestCase;
import org.neuclear.commons.NeuClearException;
import org.neuclear.id.SignedNamedObject;

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

$Id: SourceTest.java,v 1.2 2003/11/21 04:45:17 pelle Exp $
$Log: SourceTest.java,v $
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
 * Time: 1:21:24 PM
 */
public class SourceTest extends TestCase {
    public SourceTest(final String name) {
        super(name);
        source = createSource();
    }

    protected Source createSource() {
        return Source.getInstance();
    }

    public final void testFetch() throws NeuClearException {
        final SignedNamedObject test = source.fetch("http://repository.neuclear.org", "neu://test");
        assertNotNull(test);
        assertEquals("neu://test", test.getName());
        final SignedNamedObject root = source.fetch("http://repository.neuclear.org", "neu://");
        assertNotNull(root);
        assertEquals("neu://", root.getName());
    }

    private final Source source;
}
