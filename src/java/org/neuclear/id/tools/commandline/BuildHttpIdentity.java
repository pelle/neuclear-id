package org.neuclear.id.tools.commandline;

import org.neuclear.commons.NeuClearException;
import org.neuclear.commons.Utility;
import org.neuclear.commons.crypto.passphraseagents.GuiDialogAgent;
import org.neuclear.commons.crypto.signers.DefaultSigner;
import org.neuclear.commons.crypto.signers.JCESigner;
import org.neuclear.id.NSTools;
import org.neuclear.id.builders.IdentityBuilder;
import org.neuclear.xml.XMLException;

import java.io.*;
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

$Id: BuildHttpIdentity.java,v 1.2 2003/12/10 23:58:51 pelle Exp $
$Log: BuildHttpIdentity.java,v $
Revision 1.2  2003/12/10 23:58:51  pelle
Did some cleaning up in the builders
Fixed some stuff in IdentityCreator
New maven goal to create executable jarapp
We are close to 0.8 final of ID, 0.11 final of XMLSIG and 0.5 of commons.
Will release shortly.

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

Revision 1.2  2003/12/08 22:05:08  pelle
Some further documentation. Added the start of a busy developers guide form neuclear-id

Revision 1.1  2003/12/08 19:32:32  pelle
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
public final class BuildHttpIdentity {

    public static void main(final String[] args) {
        try {
            if (args.length < 2) {
                System.err.println("Usage: java org.neuclear.id.tools.commandline.BuildHttpIdentity name receiver");
                System.err.println("eg. java org.neuclear.id.tools.commandline.BuildHttpIdentity neu://neuclear.org mailto:bob@neuclear.org");
                System.exit(1);
            }
            final JCESigner rootsig = new DefaultSigner(new GuiDialogAgent());

            createIdentity(args[0], rootsig, args[1]);
        } catch (NeuClearException e) {
            e.printStackTrace();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (XMLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        }
        System.exit(0);
    }

    private static void createIdentity(final String name, final JCESigner rootsig, final String receiver) throws NeuClearException, XMLException, IOException {
        System.out.println("Creating " + name);
        String store = NSTools.isHttpScheme(name);
        boolean isTopLevel = !Utility.isEmpty(store);
        if (!isTopLevel) {
            // If this isn't a top level we will derive the repository from its parent.
            store = NSTools.isHttpScheme(NSTools.getSignatoryURI(name));
        }
        final IdentityBuilder id = new IdentityBuilder(
                name,
                rootsig.getPublicKey(name),
                store,
                "http://localhost:11870/Signer",
                "http://logger.neuclear.org",
                receiver);

        System.out.println("Signing: " + name);
        id.sign((isTopLevel) ? name : NSTools.getSignatoryURI(name), rootsig);
        String filename = "_NEUID" + NSTools.name2path(name) + "/root.id";
        System.out.println("Saving to: " + filename);
        File fout = new File(filename);
        fout.getParentFile().mkdirs();
        OutputStream os = new FileOutputStream(fout);
        os.write(id.canonicalize());
        os.close();
    }
}

