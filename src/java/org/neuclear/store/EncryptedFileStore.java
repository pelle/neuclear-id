/*
 * $Id: EncryptedFileStore.java,v 1.4 2003/09/24 23:56:49 pelle Exp $
 * $Log: EncryptedFileStore.java,v $
 * Revision 1.4  2003/09/24 23:56:49  pelle
 * Refactoring nearly done. New model for creating signed objects.
 * With view for supporting the xmlpull api shortly for performance reasons.
 * Currently still uses dom4j but that has been refactored out that it
 * should now be very quick to implement a xmlpull implementation.
 *
 * A side benefit of this is that the API has been further simplified. I still have some work
 * todo with regards to cleaning up some of the outlying parts of the code.
 *
 * Revision 1.3  2003/09/23 19:16:29  pelle
 * Changed NameSpace to Identity.
 * To cause less confusion in the future.
 *
 * Revision 1.2  2003/09/22 19:24:02  pelle
 * More fixes throughout to problems caused by renaming.
 *
 * Revision 1.1.1.1  2003/09/19 14:41:22  pelle
 * First import into the neuclear project. This was originally under the SF neudist
 * project. This marks a general major refactoring and renaming ahead.
 *
 * The new name for this code is NeuClear Identity and has the general package header of
 * org.neuclear.id
 * There are other areas within the current code which will be split out into other subprojects later on.
 * In particularly the signers will be completely seperated out as well as the contract types.
 *
 *
 * Revision 1.9  2003/02/18 14:57:33  pelle
 * Finished Cleaning up Receivers and Stores.
 * Also updated nsdl.xsd xml schema with latest changes.
 * The whole API is now very simple.
 *
 * Revision 1.8  2003/02/10 22:30:17  pelle
 * Got rid of even further dependencies. In Particular OSCore
 *
 * Revision 1.7  2003/02/09 00:15:56  pelle
 * Fixed things so they now compile with r_0.7 of XMLSig
 *
 * Revision 1.6  2003/01/16 22:20:03  pelle
 * First Draft of new generalised Ledger Interface.
 * Currently we have a Book and Transaction class.
 * We also need a Ledger class and a Ledger Factory.
 *
 * Revision 1.5  2002/10/02 21:03:45  pelle
 * Major Commit
 * I completely redid the namespace resolving code.
 * It now works correctly with the new store attribute of the namespace
 * And can correctly work out the location of a namespace file
 * by hierarchically signing it.
 * I have also included several top level namespaces and finalised
 * the root namespace.
 * In short all of the above means that we can theoretically call
 * Neubia live now. (Well on my first deployment anyway).
 * There is a new CommandLineSigner utility class which creates and signs
 * namespaces using standard java keystores.
 * I'm now working on updating the documentation, so other people
 * than me might have a chance at using it.
 *
 * Revision 1.4  2002/09/23 15:09:18  pelle
 * Got the SimpleSignerStore working properly.
 * I couldn't get SealedObjects working with BouncyCastle's Symmetric keys.
 * Don't know what I was doing, so I reimplemented it. Encrypting
 * and decrypting it my self.
 *
 * Revision 1.3  2002/09/21 23:11:16  pelle
 * A bunch of clean ups. Got rid of as many hard coded URL's as I could.
 *
 * Revision 1.2  2002/09/21 16:45:53  pelle
 * Got the simple web based signing service working.
 * There is also a simple User Authentication Web app to demonstrate.
 * Docs to follow.
 *
 * Revision 1.1.1.1  2002/09/18 10:55:55  pelle
 * First release in new CVS structure.
 * Also first public release.
 * This implemnts simple named objects.
 * - Identity Objects
 * - NSAuth Objects
 *
 * Storage systems
 * - In Memory Storage
 * - Clear text file based storage
 * - Encrypted File Storage (with SHA256 digested filenames)
 * - CachedStorage
 * - SoapStorage
 *
 * Simple SOAP client/server
 * - Simple Single method call SOAP client, for arbitrary dom4j based requests
 * - Simple Abstract SOAP Servlet for implementing http based SOAP Servers
 *
 * Simple XML-Signature Implementation
 * - Based on dom4j
 * - SHA-RSA only
 * - Very simple (likely imperfect) highspeed canonicalizer
 * - Zero support for X509 (We dont like that anyway)
 * - Super Simple
 *
 *
 * Revision 1.4  2002/06/18 03:04:11  pelle
 * Just added all the necessary jars.
 * Fixed a few things in the framework and
 * started a GUI Application to manage Neu's.
 *
 * Revision 1.3  2002/06/17 20:48:33  pelle
 * The NS functionality should now work. FileStore is working properly.
 * The example .id objects in the neuspace folder have been updated with the
 * latest version of the format.
 * "neuspace/root.id" should now be considered the universal parent of the
 * neuclear system.
 * Still more to go, but we're getting there. I will now focus on a quick
 * Web interface. After which Contracts will be added.
 *
 * Revision 1.2  2002/06/05 23:42:05  pelle
 * The Throw clauses of several method definitions were getting out of hand, so I have
 * added a new wrapper exception NeudistException, to keep things clean in the ledger.
 * This is used as a catchall wrapper for all Exceptions in the underlying API's such as IOExceptions,
 * XML Exceptions etc.
 * You can catch any Exception and rethrow it using Utility.rethrowException(e) as a quick way of handling
 * exceptions.
 * Otherwise the Store framework and the NameSpaces are really comming along quite well. I added a CachedStore
 * which wraps around any other Store and caches the access to the store.
 *
 * Revision 1.1.1.1  2002/05/29 10:02:23  pelle
 * Lets try one more time. This is the first rev of the next gen of Neudist
 *
 *
 */
package org.neuclear.store;

import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.neuclear.id.NSTools;
import org.neuclear.id.SignedNamedObject;
import org.neuclear.id.NamedObjectFactory;
import org.neudist.crypto.CryptoTools;
import org.neudist.utils.NeudistException;
import org.neudist.utils.Utility;
import org.neudist.xml.xmlsec.XMLSecTools;

import java.io.*;

/**
 * We need both a simple FileStore and an encrypted one. The encrypted one stores each object using a filename generated through
 * a Hashing system of some sort. The files themselves are encrypted using perhaps their name and a store specific code. The filetimes would also be set to a
 * uniform time, so if the operator was sopeanad(Spelling) i
 *
 */
public class EncryptedFileStore extends FileStore {
    public EncryptedFileStore(String base) {
        super(base);
    }

    protected void rawStore(SignedNamedObject obj) throws IOException, NeudistException {
        String outputFilename = base + getFileName(obj);
        System.out.println("Outputting to: " + outputFilename);
        File outputFile = new File(outputFilename);
        outputFile.getParentFile().mkdirs();

        // Quick and dirty encryption for now.
//        String xmlData=obj.getElement().asXML();
        byte encrypted[] = CryptoTools.encrypt(obj.getName(), XMLSecTools.getElementBytes(obj.getElement()));

        BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(outputFile));
        os.write(encrypted);
        os.close();
    }

    protected SignedNamedObject fetch(String name) throws NeudistException {
        String deURLizedName = NSTools.normalizeNameURI(name);
        String inputFilename = base + getFileName(deURLizedName);
        System.out.println("Loading from: " + inputFilename);
        File fin = new File(inputFilename);
        if (!fin.exists())
            return null;

        SignedNamedObject ns = null;
        try {
            byte input[] = new byte[(int) fin.length()];
            FileInputStream fis = new FileInputStream(fin);
            fis.read(input, 0, input.length);
            byte clear[] = CryptoTools.decrypt(deURLizedName.getBytes(), input);
            int last = clear.length;
            for (last = clear.length; clear[last - 1] != (byte) '>'; last--) ;
            String clearString = new String(clear, 0, last);
//            System.out.print("Read: ");
//            System.out.println(clearString);
            org.dom4j.Document doc = DocumentHelper.parseText(clearString);
            ns = NamedObjectFactory.createNamedObject(doc);
//            Utility.rethrowException(e);
        } catch (IOException e) {
            Utility.rethrowException(e);
        } catch (DocumentException e) {
            Utility.rethrowException(e);
//        } catch (FileNotFoundException e) {
//            Utility.rethrowException(e);
        }

        return ns;
    }

    protected static String getFileName(String name) throws NeudistException {
        String deURLizedName = NSTools.normalizeNameURI(name);
        byte hash[] = CryptoTools.formatAsURLSafe(CryptoTools.digest512(deURLizedName.getBytes())).getBytes();
        //if (true) return new String(hash);
        int partlength = hash.length / 8;
        byte newName[] = new byte[hash.length + 8];
        for (int i = 0; i < 8; i++) {
            newName[i * (partlength + 1)] = (byte) '/';
            System.arraycopy(hash, (i * partlength), newName, (i * (partlength + 1)) + 1, partlength);
        }
        try {
            if (hash.length % 8 != 0)
                System.arraycopy(hash, 8 * partlength, newName, (8 * (partlength + 1)), hash.length % 8);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("hash.length=" + hash.length + ", newName.length=" + newName.length + ", partlength=" + partlength);
            System.err.println("System.arraycopy(hash," + 9 * partlength + ",newName," + (9 * (partlength + 1)) + "," + hash.length % 8 + ");");
        }
        return new String(newName);
    }

    protected static String getFileName(SignedNamedObject obj) throws NeudistException {
        return getFileName(obj.getName());
    }


}
