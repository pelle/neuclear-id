/*
 * $Id: FileStore.java,v 1.19 2003/12/19 18:03:35 pelle Exp $
 * $Log: FileStore.java,v $
 * Revision 1.19  2003/12/19 18:03:35  pelle
 * Revamped a lot of exception handling throughout the framework, it has been simplified in most places:
 * - For most cases the main exception to worry about now is InvalidNamedObjectException.
 * - Most lowerlevel exception that cant be handled meaningful are now wrapped in the LowLevelException, a
 *   runtime exception.
 * - Source and Store patterns each now have their own exceptions that generalizes the various physical
 *   exceptions that can happen in that area.
 *
 * Revision 1.18  2003/12/11 23:57:29  pelle
 * Trying to test the ReceiverServlet with cactus. Still no luck. Need to return a ElementProxy of some sort.
 * Cleaned up some missing fluff in the ElementProxy interface. getTagName(), getQName() and getNameSpace() have been killed.
 *
 * Revision 1.17  2003/12/10 23:58:52  pelle
 * Did some cleaning up in the builders
 * Fixed some stuff in IdentityCreator
 * New maven goal to create executable jarapp
 * We are close to 0.8 final of ID, 0.11 final of XMLSIG and 0.5 of commons.
 * Will release shortly.
 *
 * Revision 1.16  2003/12/06 00:17:04  pelle
 * Updated various areas in NSTools.
 * Updated URI Validation in particular to support new expanded format
 * Updated createUniqueID and friends to be a lot more unique and more efficient.
 * In CryptoTools updated getRandom() to finally use a SecureRandom.
 * Changed CryptoTools.getFormatURLSafe to getBase36 because that is what it really is.
 *
 * Revision 1.15  2003/11/21 04:45:16  pelle
 * EncryptedFileStore now works. It uses the PBECipher with DES3 afair.
 * Otherwise You will Finaliate.
 * Anything that can be final has been made final throughout everyting. We've used IDEA's Inspector tool to find all instance of variables that could be final.
 * This should hopefully make everything more stable (and secure).
 *
 * Revision 1.14  2003/11/19 23:34:00  pelle
 * Signers now can generatekeys via the generateKey() method.
 * Refactored the relationship between SignedNamedObject and NamedObjectBuilder a bit.
 * SignedNamedObject now contains the full xml which is returned with getEncoded()
 * This means that it is now possible to further receive on or process a SignedNamedObject, leaving
 * NamedObjectBuilder for its original purposes of purely generating new Contracts.
 * NamedObjectBuilder.sign() now returns a SignedNamedObject which is the prefered way of processing it.
 * Updated all major interfaces that used the old model to use the new model.
 *
 * Revision 1.13  2003/11/18 23:35:46  pelle
 * Payment Web Application is getting there.
 *
 * Revision 1.12  2003/11/18 19:23:58  pelle
 * Missed this in latest checkin
 *
 * Revision 1.11  2003/11/18 15:45:09  pelle
 * FileStoreTest now passes. FileStore works again.
 *
 * Revision 1.10  2003/11/15 01:58:16  pelle
 * More work all around on web applications.
 *
 * Revision 1.9  2003/11/11 21:18:44  pelle
 * Further vital reshuffling.
 * org.neudist.crypto.* and org.neudist.utils.* have been moved to respective areas under org.neuclear.commons
 * org.neuclear.signers.* as well as org.neuclear.passphraseagents have been moved under org.neuclear.commons.crypto as well.
 * Did a bit of work on the Canonicalizer and changed a few other minor bits.
 *
 * Revision 1.8  2003/10/29 21:16:28  pelle
 * Refactored the whole signing process. Now we have an interface called Signer which is the old SignerStore.
 * To use it you pass a byte array and an alias. The sign method then returns the signature.
 * If a Signer needs a passphrase it uses a PassPhraseAgent to present a dialogue box, read it from a command line etc.
 * This new Signer pattern allows us to use secure signing hardware such as N-Cipher in the future for server applications as well
 * as SmartCards for end user applications.
 *
 * Revision 1.7  2003/10/21 22:31:14  pelle
 * Renamed NeudistException to NeuClearException and moved it to org.neuclear.commons where it makes more sense.
 * Unhooked the XMLException in the xmlsig library from NeuClearException to make all of its exceptions an independent hierarchy.
 * Obviously had to perform many changes throughout the code to support these changes.
 *
 * Revision 1.6  2003/10/02 23:29:03  pelle
 * Updated Root Key. This will be the root key for the remainder of the beta period. With version 1.0 I will update it with a new key.
 * VerifyingTest works now and also does a pass for fake ones. Will have to think of better ways of making fake Identities to break it.
 * Cleaned up much of the tests and they all pass now.
 * The FileStoreTests need to be rethought out, by adding a test key.
 *
 * Revision 1.5  2003/09/26 00:22:07  pelle
 * Cleanups and final changes to code for refactoring of the Verifier and Reader part.
 *
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
 * Revision 1.1.1.1  2003/09/19 14:41:19  pelle
 * First import into the neuclear project. This was originally under the SF neuclear
 * project. This marks a general major refactoring and renaming ahead.
 *
 * The new name for this code is NeuClear Identity and has the general package header of
 * org.neuclear.id
 * There are other areas within the current code which will be split out into other subprojects later on.
 * In particularly the signers will be completely seperated out as well as the contract types.
 *
 *
 * Revision 1.8  2003/02/18 14:57:34  pelle
 * Finished Cleaning up Receivers and Stores.
 * Also updated nsdl.xsd xml schema with latest changes.
 * The whole API is now very simple.
 *
 * Revision 1.7  2003/02/10 22:30:18  pelle
 * Got rid of even further dependencies. In Particular OSCore
 *
 * Revision 1.6  2003/02/09 00:15:56  pelle
 * Fixed things so they now compile with r_0.7 of XMLSig
 *
 * Revision 1.5  2003/01/16 22:20:03  pelle
 * First Draft of new generalised Ledger Interface.
 * Currently we have a Book and Transaction class.
 * We also need a Ledger class and a Ledger Factory.
 *
 * Revision 1.4  2002/09/23 15:09:18  pelle
 * Got the SimpleSigner working properly.
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
 * added a new wrapper exception NeuClearException, to keep things clean in the ledger.
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

//import org.neuclear.id.NSDLObject;

import org.neuclear.commons.NeuClearException;
import org.neuclear.id.NSTools;
import org.neuclear.id.SignedNamedObject;
import org.neuclear.id.InvalidNamedObjectException;
import org.neuclear.id.NameResolutionException;
import org.neuclear.id.verifier.VerifyingReader;
import org.neuclear.xml.XMLException;

import java.io.*;

/**
 * We need both a simple FileStore and an encrypted one. The encrypted one stores each object using a filename generated through
 * a Hashing system of some sort. The files themselves are encrypted using perhaps their name and a store specific code. The filetimes would also be set to a
 * uniform time, so if the operator was sopeanad(Spelling) i
 */
public class FileStore extends Store {
    public FileStore(final String base) {
        this.base = base;
    }

    protected final void rawStore(final SignedNamedObject obj) throws StorageException, InvalidNamedObjectException {
        final OutputStream out = getOutputStream(obj);
        try {
            out.write(obj.getEncoded().getBytes("UTF-8"));
            out.close();
        } catch (IOException e) {
            throw new StorageException(this,e);
        }
    }

    protected OutputStream getOutputStream(final SignedNamedObject obj) throws  StorageException, InvalidNamedObjectException {
        final String outputFilename = base + getFileName(obj);
        System.out.println("Outputting to: " + outputFilename);
        final File outputFile = new File(outputFilename);
        outputFile.getParentFile().mkdirs();
        try {
            final OutputStream out = new FileOutputStream(outputFile);
            return out;
        } catch (FileNotFoundException e) {
            throw new StorageException(this,e);
        }
    }

//    public void store(Document doc) throws InvalidNamedObjectException,IOException {
//        store(new NSDLObject(doc));
//    }

    final SignedNamedObject fetch(final String name) throws StorageException, InvalidNamedObjectException, NameResolutionException {
        return VerifyingReader.getInstance().read(getInputStream(name));
    }

    protected InputStream getInputStream(final String name) throws InvalidNamedObjectException, StorageException {
        final String inputFilename = base + getFileName(name);
        System.out.println("Loading from: " + inputFilename);
        final File fin = new File(inputFilename);
        if (!fin.exists())
            throw new InvalidNamedObjectException(name);

        try {
            return new FileInputStream(fin);
        } catch (FileNotFoundException e) {
            throw new StorageException(this,e);
        }
    }


    protected String getFileName(final String name) throws InvalidNamedObjectException {
        return NSTools.name2path(name) + "/root.id";
    }

    protected final String getFileName(final SignedNamedObject obj) throws InvalidNamedObjectException {
        return getFileName(obj.getName());
//        if (! (obj instanceof Identity))
//            return obj.getName();
//        else if (obj.getName().equals("/")||obj.getName().equals(""))
//            return "/root.id";
//        else
//            return obj.getName()+".id";
    }

    protected final String base;


}
