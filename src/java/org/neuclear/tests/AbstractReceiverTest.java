package org.neuclear.tests;

import org.neuclear.commons.NeuClearException;
import org.neuclear.commons.crypto.CryptoTools;
import org.neuclear.id.InvalidNamedObjectException;
import org.neuclear.id.SignedNamedObject;
import org.neuclear.id.verifier.VerifyingReader;
import org.neuclear.receiver.Receiver;
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

$Id: AbstractReceiverTest.java,v 1.6 2003/12/11 23:57:29 pelle Exp $
$Log: AbstractReceiverTest.java,v $
Revision 1.6  2003/12/11 23:57:29  pelle
Trying to test the ReceiverServlet with cactus. Still no luck. Need to return a ElementProxy of some sort.
Cleaned up some missing fluff in the ElementProxy interface. getTagName(), getQName() and getNameSpace() have been killed.

Revision 1.5  2003/11/21 04:45:16  pelle
EncryptedFileStore now works. It uses the PBECipher with DES3 afair.
Otherwise You will Finaliate.
Anything that can be final has been made final throughout everyting. We've used IDEA's Inspector tool to find all instance of variables that could be final.
This should hopefully make everything more stable (and secure).

Revision 1.4  2003/11/15 01:58:18  pelle
More work all around on web applications.

Revision 1.3  2003/11/12 23:48:14  pelle
Much work done in creating good test environment.
PaymentReceiverTest works, but needs a abit more work in its environment to succeed testing.

Revision 1.2  2003/11/11 21:18:44  pelle
Further vital reshuffling.
org.neudist.crypto.* and org.neudist.utils.* have been moved to respective areas under org.neuclear.commons
org.neuclear.signers.* as well as org.neuclear.passphraseagents have been moved under org.neuclear.commons.crypto as well.
Did a bit of work on the Canonicalizer and changed a few other minor bits.

Revision 1.1  2003/11/10 17:42:37  pelle
The AssetController interface has been more or less finalized.
CurrencyController fully implemented
AssetControlClient implementes a remote client for communicating with AssetControllers

Revision 1.1  2003/10/25 00:39:54  pelle
Fixed SmtpSender it now sends the messages.
Refactored CommandLineSigner. Now it simply signs files read from command line. However new class IdentityCreator
is subclassed and creates new Identities. You can subclass CommandLineSigner to create your own variants.
Several problems with configuration. Trying to solve at the moment. Updated PicoContainer to beta-2

*/

/**
 * User: pelleb
 * Date: Oct 24, 2003
 * Time: 11:09:16 AM
 */
public abstract class AbstractReceiverTest extends AbstractSigningTest {

    protected AbstractReceiverTest(final String string) throws GeneralSecurityException, NeuClearException {
        super(string);
        CryptoTools.ensureProvider();
        reader = VerifyingReader.getInstance();

    }

    /**
     * The receiver to test. This would probably be initialized by the constructor
     * of the implementing class.
     * 
     * @return 
     */
    public abstract Receiver getReceiver();

    /**
     * The extension of files to verify, eg. ".xml"
     * 
     * @return 
     */
    public abstract String getExtension();

    /**
     * Verify the effect of the transaction based on the given state.
     * 
     * @param obj   The Transaction Object to test
     * @param state An object created first from the matching getPreTransactionState() method.
     * @return 
     */
    public abstract boolean verifyTransaction(SignedNamedObject obj, Object state) throws Exception;

    /**
     * Should return an object identifying the state of the system prior to the transaction.
     * The object should be meaningful to the matching verifyTransaction method.
     * 
     * @param obj 
     * @return 
     * @throws Exception 
     */
    public abstract Object getPreTransactionState(SignedNamedObject obj) throws Exception;


    public final void runDirectoryTest(final String path) throws Exception, IOException, FileNotFoundException, NeuClearException, XMLException {
        final File dir = new File(path);
        if (!dir.exists()) {
            System.out.println("Doesnt exist");
            return;
        }
        final FilenameFilter filter;
        filter = new FilenameFilter() {
            public boolean accept(final File dirf, final String name) {
                return name.endsWith(getExtension());
            }
        };

        final File[] xmlfiles = dir.listFiles(filter);
        System.out.println("There are " + xmlfiles.length + " files in the directory");
        for (int i = 0; i < xmlfiles.length; i++) {

            final File xmlfile = xmlfiles[i];
            System.out.print("Testing file: " + xmlfile.getName() + "... ");
            try {
                final SignedNamedObject obj = reader.read(new FileInputStream(xmlfile));
                System.out.println("Receiving name : " + obj.getName());
                final Object prestate = getPreTransactionState(obj);
                getReceiver().receive(obj);
                assertTrue(verifyTransaction(obj, prestate));

            } catch (InvalidNamedObjectException e) {
                System.out.println("INVALID  " + e.getMessage());
                assertTrue(false);
            }
        }

    }

    private final VerifyingReader reader;

}
