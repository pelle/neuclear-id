package org.neuclear.signers.commandline;

import org.neuclear.commons.NeuClearException;
import org.neuclear.commons.crypto.passphraseagents.GuiDialogAgent;
import org.neuclear.commons.crypto.signers.*;
import org.neuclear.id.SignedNamedObject;
import org.neuclear.id.builders.IdentityBuilder;
import org.neuclear.store.FileStore;
import org.neuclear.store.Store;
import org.neuclear.xml.XMLException;

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

$Id: BuildAllTopLevel.java,v 1.2 2003/11/21 04:45:13 pelle Exp $
$Log: BuildAllTopLevel.java,v $
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
                "http://users.neuclear.org:8080/Signer",
                "http://logger.neuclear.org",
                "mailto:pelle@neuclear.org");
        System.out.println("Signing: " + name);
        return id.sign(signer);
    }

    public static void main(final String[] args) {
        try {
            final JCESigner rootsig = new DefaultSigner(new GuiDialogAgent());
            final JCESigner testsig = new TestCaseSigner();
            final Store store = new FileStore("target/testdata/repository");
            store.receive(createIdentities("neu://", rootsig, rootsig));
            store.receive(createIdentities("neu://test", rootsig, testsig));
            store.receive(createIdentities("neu://pelle", rootsig, rootsig));
            store.receive(createIdentities("neu://verax", rootsig, rootsig));
            store.receive(createIdentities("neu://bob@test", testsig, testsig));
            store.receive(createIdentities("neu://alice@test", testsig, testsig));

        } catch (NeuClearException e) {
            e.printStackTrace();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (XMLException e) {
            e.printStackTrace();
        }
    }
}

