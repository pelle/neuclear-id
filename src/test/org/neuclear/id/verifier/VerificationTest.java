package org.neuclear.id.verifier;

import junit.framework.TestCase;
import org.dom4j.DocumentException;
import org.neuclear.commons.NeuClearException;
import org.neuclear.id.InvalidIdentityException;
import org.neuclear.id.SignedNamedObject;
import org.neuclear.commons.crypto.CryptoTools;
import org.neuclear.xml.XMLException;

import java.io.*;

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

$Id: VerificationTest.java,v 1.6 2003/11/11 21:18:45 pelle Exp $
$Log: VerificationTest.java,v $
Revision 1.6  2003/11/11 21:18:45  pelle
Further vital reshuffling.
org.neudist.crypto.* and org.neudist.utils.* have been moved to respective areas under org.neuclear.commons
org.neuclear.signers.* as well as org.neuclear.passphraseagents have been moved under org.neuclear.commons.crypto as well.
Did a bit of work on the Canonicalizer and changed a few other minor bits.

Revision 1.5  2003/10/23 22:02:36  pelle
Moved some certificates to live status at http://repository.neuclear.org
Updated NSTools.url2path to support neuids with @ signs.

Revision 1.4  2003/10/21 22:31:14  pelle
Renamed NeudistException to NeuClearException and moved it to org.neuclear.commons where it makes more sense.
Unhooked the XMLException in the xmlsig library from NeuClearException to make all of its exceptions an independent hierarchy.
Obviously had to perform many changes throughout the code to support these changes.

Revision 1.3  2003/10/02 23:29:03  pelle
Updated Root Key. This will be the root key for the remainder of the beta period. With version 1.0 I will update it with a new key.
VerifyingTest works now and also does a pass for fake ones. Will have to think of better ways of making fake Identities to break it.
Cleaned up much of the tests and they all pass now.
The FileStoreTests need to be rethought out, by adding a test key.

Revision 1.2  2003/09/30 23:25:15  pelle
Added new JCE Provider and java Certificate implementation for NeuClear Identity.

Revision 1.1  2003/09/27 19:23:11  pelle
Added Builders to create named objects from scratch.

*/

/**
 * User: pelleb
 * Date: Sep 27, 2003
 * Time: 11:38:01 AM
 */
public class VerificationTest extends TestCase {
    public VerificationTest(String string) {

        super(string);
        CryptoTools.ensureProvider();
        reader = VerifyingReader.getInstance();
    }

    public void testSimple() throws IOException, DocumentException, NeuClearException, XMLException {
        runDirectoryTest("src/testdata/simple", true);
    }

    public void testSimpleSecond() throws IOException, DocumentException, NeuClearException, XMLException {
        runDirectoryTest("src/testdata/simple", true);
    }

    public void testFakes() throws IOException, DocumentException, NeuClearException, XMLException {
        runDirectoryTest("src/testdata/fakes", false);
    }

    public void runDirectoryTest(String path, boolean wantValid) throws DocumentException, IOException, FileNotFoundException, NeuClearException, XMLException {
        File dir = new File(path);
        if (!dir.exists()) {
            System.out.println("Doesnt exist");
            return;
        }
        FilenameFilter filter;
        filter = new FilenameFilter() {
            public boolean accept(File dirf, String name) {
                return name.endsWith(".id");
            }
        };

        File xmlfiles[] = dir.listFiles(filter);
        System.out.println("There are " + xmlfiles.length + " files in the directory");
        for (int i = 0; i < xmlfiles.length; i++) {

            File xmlfile = xmlfiles[i];
            System.out.print("Testing file: " + xmlfile.getName() + "... ");
            try {
                SignedNamedObject obj = reader.read(new FileInputStream(xmlfile));
                System.out.println("Name : " + obj.getName() + " VERIFIED");
                assertTrue(wantValid);

            } catch (InvalidIdentityException e) {
                System.out.println("INVALID  " + e.getMessage());
                assertTrue(!wantValid);
            }
        }


    }

    VerifyingReader reader;

}
