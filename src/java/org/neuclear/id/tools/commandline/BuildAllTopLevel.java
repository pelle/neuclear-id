package org.neuclear.id.tools.commandline;

import org.neuclear.commons.NeuClearException;
import org.neuclear.commons.crypto.signers.JCESigner;
import org.neuclear.commons.crypto.signers.PublicKeySource;
import org.neuclear.commons.crypto.signers.Signer;
import org.neuclear.commons.crypto.signers.TestCaseSigner;
import org.neuclear.id.SignedNamedObject;
import org.neuclear.id.builders.IdentityBuilder;
import org.neuclear.store.FileStore;
import org.neuclear.store.Store;
import org.neuclear.xml.XMLException;

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

$Id: BuildAllTopLevel.java,v 1.5 2004/01/16 23:42:09 pelle Exp $
$Log: BuildAllTopLevel.java,v $
Revision 1.5  2004/01/16 23:42:09  pelle
Added Base32 class. The Base32 encoding used wasnt following the standards.
Added user creatable Identity for Public Keys

Revision 1.4  2004/01/15 00:02:08  pelle
Problem fixed with Enveloping signatures.

Revision 1.3  2003/12/19 00:31:30  pelle
Lots of usability changes through out all the passphrase agents and end user tools.

Revision 1.2  2003/12/12 21:13:16  pelle
I have now done manual testing of the SigningServlet et al and am happy releasing it to 0.8

Revision 1.1  2003/12/09 23:41:44  pelle
IdentityCreator is now the default class of the uber jar.
It has many new features such as:
- Self signed certificates
- Unsigned Certificates (for external signing)
- Signing of Externally generated Certificates
- Command Line verification of an Identity name

CachedSource now supports freshness. It needs to be tested a bit more thoroughly
though.

Documentation including the bdg has been updated to reflect these changes.

Revision 1.3  2003/12/08 19:32:32  pelle
Added support for the http scheme into ID. See http://neuclear.org/archives/000195.html

Revision 1.2  2003/11/21 04:45:13  pelle
EncryptedFileStore now works. It uses the PBECipher with DES3 afair.
Otherwise You will Finaliate.
Anything that can be final has been made final throughout everyting. We've used IDEA's Inspector tool to find all instance of variables that could be final.
This should hopefully make everything more stable (and secure).

Revision 1.1  2003/11/20 23:42:24  pelle
Getting all the tests to work in id
Removing usage of BC in CryptoTools as it was causing issues.
First version of EntityLedger that will use OFB's EntityEngine. This will allow us to support a vast amount databases without
writing SQL. (Yipee)

*/

/**
 * User: pelleb
 * Date: Nov 20, 2003
 * Time: 3:26:45 PM
 */
public final class BuildAllTopLevel {
    public static SignedNamedObject createIdentities(final String name, final Signer signer, final PublicKeySource pubsource) throws NeuClearException, XMLException {
        final IdentityBuilder id = new IdentityBuilder(
                name,
                pubsource.getPublicKey(name),
                "http://repository.neuclear.org",
                "http://users.neuclear.org:8080/DemoSigner",
                "http://logger.neuclear.org",
                "mailto:pelle@neuclear.org");
        System.out.println("Signing: " + name);
        return id.sign(signer);
    }

    public static void main(final String[] args) {
        try {
//            final JCESigner rootsig = new DefaultSigner(new GuiDialogAgent());
            final JCESigner testsig = new TestCaseSigner();
            final Store store = new FileStore("target/testdata/repository");
//            store.receive(createIdentities("neu://", rootsig, rootsig));
//            store.receive(createIdentities("neu://test", rootsig, testsig));
            store.receive(createIdentities("neu://bob@test", testsig, testsig));
            store.receive(createIdentities("neu://alice@test", testsig, testsig));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

