/*
 * $Id: FileStore.java,v 1.3 2003/09/23 19:16:29 pelle Exp $
 * $Log: FileStore.java,v $
 * Revision 1.3  2003/09/23 19:16:29  pelle
 * Changed NameSpace to Identity.
 * To cause less confusion in the future.
 *
 * Revision 1.2  2003/09/22 19:24:02  pelle
 * More fixes throughout to problems caused by renaming.
 *
 * Revision 1.1.1.1  2003/09/19 14:41:19  pelle
 * First import into the neuclear project. This was originally under the SF neudist
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

//import org.neuclear.id.NSDLObject;

import org.dom4j.Document;
import org.neuclear.id.NSTools;
import org.neuclear.id.NamedObject;
import org.neuclear.id.NamedObjectFactory;
import org.neudist.utils.NeudistException;
import org.neudist.xml.XMLTools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * We need both a simple FileStore and an encrypted one. The encrypted one stores each object using a filename generated through
 * a Hashing system of some sort. The files themselves are encrypted using perhaps their name and a store specific code. The filetimes would also be set to a
 * uniform time, so if the operator was sopeanad(Spelling) i
 *
 */
public class FileStore extends Store {
    public FileStore(String base) {
        this.base = base;
    }

    protected void rawStore(NamedObject obj) throws IOException, NeudistException {
        String outputFilename = base + getFileName(obj);
        System.out.println("Outputting to: " + outputFilename);
        File outputFile = new File(outputFilename);
        outputFile.getParentFile().mkdirs();
        XMLTools.writeFile(outputFile, obj.getElement());
    }

//    public void store(Document doc) throws InvalidIdentityException,IOException {
//        store(new NSDLObject(doc));
//    }

    protected NamedObject fetch(String name) throws NeudistException {
        String inputFilename = base + getFileName(NSTools.normalizeNameURI(name));
        System.out.println("Loading from: " + inputFilename);
        File fin = new File(inputFilename);
        if (!fin.exists())
            return null;

        NamedObject ns = null;
        try {
            Document doc = XMLTools.loadDocument(new FileInputStream(fin));
            ns = NamedObjectFactory.createNamedObject(doc);
//           System.out.println("NEUDIST: Fetched NamedObject tag:"+rootName.getName()+" URI:"+rootName.getNamespaceURI());
//        } catch (ParserConfigurationException e) {
//            Utility.rethrowException(e);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            //           Utility.rethrowException(e);
        }

        return ns;
    }


    protected static String getFileName(String name) throws NeudistException {
        if (name.startsWith("neu://"))
            name = name.substring(5);
        if (name.equals("/") || name.equals(""))
            return "/root.id";
        else
            return name + ".id";
    }

    protected static String getFileName(NamedObject obj) throws NeudistException {
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
