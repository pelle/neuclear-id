package org.neuclear.tests;

import junit.framework.TestCase;
import org.neudist.crypto.CryptoTools;
import org.neudist.xml.XMLException;
import org.neuclear.id.verifier.VerifyingReader;
import org.neuclear.id.SignedNamedObject;
import org.neuclear.id.InvalidIdentityException;
import org.neuclear.receiver.Receiver;
import org.neuclear.commons.NeuClearException;

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

$Id: AbstractReceiverTest.java,v 1.1 2003/11/10 17:42:37 pelle Exp $
$Log: AbstractReceiverTest.java,v $
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
public abstract class AbstractReceiverTest extends TestCase {

    protected AbstractReceiverTest(String string) {
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


    public void runDirectoryTest(String path) throws Exception, IOException, FileNotFoundException, NeuClearException, XMLException {
        File dir = new File(path);
        if (!dir.exists()) {
            System.out.println("Doesnt exist");
            return;
        }
        FilenameFilter filter;
        filter = new FilenameFilter() {
            public boolean accept(File dirf, String name) {
                return name.endsWith(getExtension());
            }
        };

        File xmlfiles[] = dir.listFiles(filter);
        System.out.println("There are " + xmlfiles.length + " files in the directory");
        for (int i = 0; i < xmlfiles.length; i++) {

            File xmlfile = xmlfiles[i];
            System.out.print("Testing file: " + xmlfile.getName() + "... ");
            try {
                SignedNamedObject obj = reader.read(new FileInputStream(xmlfile));
                System.out.println("Receiving name : " + obj.getName());
                Object prestate = getPreTransactionState(obj);
                getReceiver().receive(obj);
                assertTrue(verifyTransaction(obj, prestate));

            } catch (InvalidIdentityException e) {
                System.out.println("INVALID  " + e.getMessage());
                assertTrue(false);
            }
        }

    }

    private VerifyingReader reader;

}
