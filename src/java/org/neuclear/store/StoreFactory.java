/*
 * $Id: StoreFactory.java,v 1.2 2003/09/23 19:16:29 pelle Exp $
 * $Log: StoreFactory.java,v $
 * Revision 1.2  2003/09/23 19:16:29  pelle
 * Changed NameSpace to Identity.
 * To cause less confusion in the future.
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
 * Revision 1.3  2003/02/18 14:57:35  pelle
 * Finished Cleaning up Receivers and Stores.
 * Also updated nsdl.xsd xml schema with latest changes.
 * The whole API is now very simple.
 *
 * Revision 1.2  2003/02/09 00:15:56  pelle
 * Fixed things so they now compile with r_0.7 of XMLSig
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
 * Revision 1.2  2002/06/13 19:04:07  pelle
 * A start to a web interface into the architecture.
 * We're getting a bit further now with functionality.
 *
 * Revision 1.1.1.1  2002/05/29 10:02:23  pelle
 * Lets try one more time. This is the first rev of the next gen of Neudist
 *
 *
 */
package org.neuclear.store;



public class StoreFactory {

    private StoreFactory() {
    }

    public synchronized Store getStoreInstance(String path) {
//        synchronized (store) {
            if (store==null)
                store=new FileStore(path);//new EncryptedFileStore("/home/java/projects/neuclear/cryptstore");
//        }
        return store;

    }
    public static synchronized StoreFactory getInstance() {
//        synchronized (fact) {
            if (fact==null)
                fact=new StoreFactory();
 //       }
        return fact;
    }

    private Store store;
    private static StoreFactory fact;
}
