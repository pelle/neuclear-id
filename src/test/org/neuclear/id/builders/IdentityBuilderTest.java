package org.neuclear.id.builders;

import org.neuclear.commons.NeuClearException;
import org.neuclear.commons.crypto.passphraseagents.GuiDialogAgent;
import org.neuclear.commons.crypto.signers.DefaultSigner;
import org.neuclear.commons.crypto.signers.PublicKeySource;
import org.neuclear.commons.crypto.signers.Signer;
import org.neuclear.id.NSTools;
import org.neuclear.id.SignedNamedObject;
import org.neuclear.tests.AbstractSigningTest;
import org.neuclear.xml.XMLException;
import org.neuclear.xml.XMLTools;

import java.io.File;
import java.io.FileNotFoundException;
import java.security.GeneralSecurityException;

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

$Id: IdentityBuilderTest.java,v 1.4 2003/11/18 00:01:56 pelle Exp $
$Log: IdentityBuilderTest.java,v $
Revision 1.4  2003/11/18 00:01:56  pelle
The sample signing web application for logging in and out is now working.
There had been an issue in the canonicalizer when dealing with the embedded object of the SignatureRequest object.

Revision 1.3  2003/11/15 01:58:18  pelle
More work all around on web applications.

Revision 1.2  2003/11/13 23:26:42  pelle
The signing service and web authentication application is now almost working.

Revision 1.1  2003/11/12 23:48:14  pelle
Much work done in creating good test environment.
PaymentReceiverTest works, but needs a abit more work in its environment to succeed testing.

*/

/**
 * User: pelleb
 * Date: Nov 12, 2003
 * Time: 5:33:30 PM
 */
public class IdentityBuilderTest extends AbstractSigningTest {
    public IdentityBuilderTest(String string) throws GeneralSecurityException, NeuClearException, FileNotFoundException {
        super(string);
        rootsigner = new DefaultSigner(new GuiDialogAgent());
    }

    public void createIdentities(String name) throws NeuClearException, XMLException {
        if (getSigner().canSignFor(name)) {
            IdentityBuilder id = new IdentityBuilder(
                    name,
                    ((PublicKeySource) getSigner()).getPublicKey(name),
                    "http://repository.neuclear.org",
                    "http://users.neuclear.org:8080/Signer",
                    "http://logger.neuclear.org",
                    "mailto:pelle@neuclear.org");

            final String parent = NSTools.getParentNSURI(id.getName());
            if (getSigner().canSignFor(parent)) {
                id.sign(getSigner());

            } else if (parent.equals("neu://")) {
                id.sign(rootsigner);
            }
            File file = new File(PATH + NSTools.url2path(id.getName()) + "/root.id");
            file.getParentFile().mkdirs();
            XMLTools.writeFile(file, id.getElement());
            System.out.println("Wrote: " + file.getAbsolutePath());
            SignedNamedObject sec = id.verify();
            assertEquals(id.getName(), sec.getName());
            assertTrue(true);
        } else {
//            assertTrue(false);
            System.out.println("Couldnt sign for: " + name);

        }
    }

    public void testBuild() throws NeuClearException, XMLException {
        createIdentities("neu://test");
        createIdentities("neu://test/bux");
        createIdentities("neu://bob@test");
        createIdentities("neu://alice@test");

    }

    private static final String PATH = "target/testdata/unsigned";
    private final Signer rootsigner;
}
