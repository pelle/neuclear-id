package org.neuclear.id.examples;

import org.neuclear.commons.NeuClearException;
import org.neuclear.commons.crypto.passphraseagents.swing.SwingAgent;
import org.neuclear.commons.crypto.signers.BrowsableSigner;
import org.neuclear.commons.crypto.signers.DefaultSigner;
import org.neuclear.id.Identity;
import org.neuclear.id.InvalidNamedObjectException;
import org.neuclear.id.NameResolutionException;
import org.neuclear.id.SignedMessage;
import org.neuclear.id.builders.SignedMessageBuilder;
import org.neuclear.id.resolver.Resolver;

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

$Id: FetchIdentity.java,v 1.2 2004/04/29 13:16:41 pelle Exp $
$Log: FetchIdentity.java,v $
Revision 1.2  2004/04/29 13:16:41  pelle
Changed Identity.receive(obj) to Identity.send(obj) it makes more sense.
Updated documentation.

Revision 1.1  2004/04/28 23:51:08  pelle
Created various sample programs in src/examplecode/
Preparing documentation for release 0.9
Updating BDG

*/

/**
 * User: pelleb
 * Date: Apr 28, 2004
 * Time: 8:58:48 PM
 */
public class FetchIdentity {
    public static void main(String args[]) {
        try {
            final String name = (args.length > 0) ? args[0] : "http://talk.org/pelletest.html";
            // First lets create a Signer
            BrowsableSigner signer = new DefaultSigner(new SwingAgent());

            // Now lets fetch the identity
            Identity id = Resolver.resolveIdentity(name);

            // Build the unsigned message
            SignedMessageBuilder builder = new SignedMessageBuilder("Hello World", "This was a test!.\n How do you do?");

            // Sign and convert message
            SignedMessage message = (SignedMessage) builder.convert(signer);

            // Send it to Id
            id.send(message);

            System.out.println("Finished");
            System.exit(0);

        } catch (NameResolutionException e) {
            e.printStackTrace();
        } catch (InvalidNamedObjectException e) {
            e.printStackTrace();
        } catch (NeuClearException e) {
            e.printStackTrace();
        }
        System.exit(1);
    }
}
