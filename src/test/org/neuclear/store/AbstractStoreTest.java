/*
  $Id: AbstractStoreTest.java,v 1.8 2003/11/11 21:18:46 pelle Exp $
  $Log: AbstractStoreTest.java,v $
  Revision 1.8  2003/11/11 21:18:46  pelle
  Further vital reshuffling.
  org.neudist.crypto.* and org.neudist.utils.* have been moved to respective areas under org.neuclear.commons
  org.neuclear.signers.* as well as org.neuclear.passphraseagents have been moved under org.neuclear.commons.crypto as well.
  Did a bit of work on the Canonicalizer and changed a few other minor bits.

  Revision 1.7  2003/10/29 21:16:28  pelle
  Refactored the whole signing process. Now we have an interface called Signer which is the old SignerStore.
  To use it you pass a byte array and an alias. The sign method then returns the signature.
  If a Signer needs a passphrase it uses a PassPhraseAgent to present a dialogue box, read it from a command line etc.
  This new Signer pattern allows us to use secure signing hardware such as N-Cipher in the future for server applications as well
  as SmartCards for end user applications.

  Revision 1.6  2003/10/21 22:31:15  pelle
  Renamed NeudistException to NeuClearException and moved it to org.neuclear.commons where it makes more sense.
  Unhooked the XMLException in the xmlsig library from NeuClearException to make all of its exceptions an independent hierarchy.
  Obviously had to perform many changes throughout the code to support these changes.

  Revision 1.5  2003/10/02 23:29:03  pelle
  Updated Root Key. This will be the root key for the remainder of the beta period. With version 1.0 I will update it with a new key.
  VerifyingTest works now and also does a pass for fake ones. Will have to think of better ways of making fake Identities to break it.
  Cleaned up much of the tests and they all pass now.
  The FileStoreTests need to be rethought out, by adding a test key.

  Revision 1.4  2003/09/24 23:56:49  pelle
  Refactoring nearly done. New model for creating signed objects.
  With view for supporting the xmlpull api shortly for performance reasons.
  Currently still uses dom4j but that has been refactored out that it
  should now be very quick to implement a xmlpull implementation.

  A side benefit of this is that the API has been further simplified. I still have some work
  todo with regards to cleaning up some of the outlying parts of the code.

  Revision 1.3  2003/09/23 19:16:29  pelle
  Changed NameSpace to Identity.
  To cause less confusion in the future.

  Revision 1.2  2003/09/22 19:24:03  pelle
  More fixes throughout to problems caused by renaming.

  Revision 1.1.1.1  2003/09/19 14:42:03  pelle
  First import into the neuclear project. This was originally under the SF neuclear
  project. This marks a general major refactoring and renaming ahead.

  The new name for this code is NeuClear Identity and has the general package header of
  org.neuclear.id
  There are other areas within the current code which will be split out into other subprojects later on.
  In particularly the signers will be completely seperated out as well as the contract types.


  Revision 1.1  2003/02/10 22:30:24  pelle
  Got rid of even further dependencies. In Particular OSCore

  Revision 1.2  2003/02/09 00:15:56  pelle
  Fixed things so they now compile with r_0.7 of XMLSig

  Revision 1.1  2003/01/16 19:16:09  pelle
  Major Structural Changes.
  We've split the test classes out of the normal source tree, to enable Maven's test support to work.
  WARNING
  for Development purposes the code does not as of this commit until otherwise notified actually verifysigs.
  We are reworking the XMLSig library and need to continue work elsewhere for the time being.
  DO NOT USE THIS FOR REAL APPS

  Revision 1.3  2002/10/02 21:03:45  pelle
  Major Commit
  I completely redid the namespace resolving code.
  It now works correctly with the new store attribute of the namespace
  And can correctly work out the location of a namespace file
  by hierarchically signing it.
  I have also included several top level namespaces and finalised
  the root namespace.
  In short all of the above means that we can theoretically call
  Neubia live now. (Well on my first deployment anyway).
  There is a new CommandLineSigner utility class which creates and signs
  namespaces using standard java keystores.
  I'm now working on updating the documentation, so other people
  than me might have a chance at using it.

  Revision 1.2  2002/09/21 23:11:16  pelle
  A bunch of clean ups. Got rid of as many hard coded URL's as I could.

  Revision 1.1.1.1  2002/09/18 10:55:55  pelle
  First release in new CVS structure.
  Also first public release.
  This implemnts simple named objects.
  - Identity Objects
  - NSAuth Objects

  Storage systems
  - In Memory Storage
  - Clear text file based storage
  - Encrypted File Storage (with SHA256 digested filenames)
  - CachedStorage
  - SoapStorage

  Simple SOAP client/server
  - Simple Single method call SOAP client, for arbitrary dom4j based requests
  - Simple Abstract SOAP Servlet for implementing http based SOAP Servers

  Simple XML-Signature Implementation
  - Based on dom4j
  - SHA-RSA only
  - Very simple (likely imperfect) highspeed canonicalizer
  - Zero support for X509 (We dont like that anyway)
  - Super Simple


  Revision 1.1.1.1  2002/05/29 10:02:22  pelle
  Lets try one more time. This is the first rev of the next gen of Neudist


  Revision 1.1.1.1  2002/03/20 00:46:52  pelleb
  no message


*/

package org.neuclear.store;

import junit.framework.TestCase;
import org.neuclear.commons.NeuClearException;
import org.neuclear.id.InvalidIdentityException;
import org.neuclear.xml.xmlsec.XMLSecurityException;

import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;


/**
 * @author Pelle Braendgaard
 */
public abstract class AbstractStoreTest extends TestCase {
    public AbstractStoreTest(String name) throws GeneralSecurityException {
        super(name);
        store = getStoreInstance();
        generateKeys();
    }

    /**
     */
    public abstract Store getStoreInstance();

    protected static synchronized void generateKeys() throws GeneralSecurityException {
        if (kg == null) {
            System.out.println("Generating Test Keys");
            kg = KeyPairGenerator.getInstance("RSA");

            kg.initialize(2048, new SecureRandom("Bear it all with NeuDist".getBytes()));
            //       kp=kg.generateKeyPair();
            root = kg.generateKeyPair();
            bob = kg.generateKeyPair();
            alice = kg.generateKeyPair();
            eve = kg.generateKeyPair();
        }
    }

    protected void tearDown() {
        store = null;
    }


    public void testStore() throws NeuClearException, InvalidIdentityException, XMLSecurityException {
//        System.out.println("\nTesting " + this.getClass().getName());
//        System.out.println("Storing " + rootName);
//        store.receive(new IdentityBuilder(rootName, root.getPrivate(), root.getPublic()));
//        System.out.println("Storing " + bobName);
//        store.receive(new IdentityBuilder(bobName, root.getPrivate(), bob.getPublic()));
//        System.out.println("Storing " + bobAliceName);
//        store.receive(new IdentityBuilder(bobAliceName, bob.getPrivate(), alice.getPublic()));
//        System.out.println("Storing " + eveName);
//        store.receive(new IdentityBuilder(eveName, root.getPrivate(), eve.getPublic()));
/* TODO: To complete this part I need to have a parent testkey in a keystore that is signed by root.
                System.out.println("Fetching "+rootName);
                SignedNamedObject nobj1=store.fetch(rootName);
                assertEquals(NSTools.normalizeNameURI(rootName),nobj1.getName());
                System.out.println("Fetching "+bobName);
                SignedNamedObject nobj2=store.fetch(bobName);
                assertEquals(NSTools.normalizeNameURI(bobName),nobj2.getName());

                System.out.println("Fetching "+bobAliceName);
                SignedNamedObject nobj3=store.fetch(bobAliceName);
                assertEquals(NSTools.normalizeNameURI(bobAliceName),nobj3.getName());

                System.out.println("Fetching "+eveName);
                SignedNamedObject nobj4=store.fetch(eveName);
                assertEquals(NSTools.normalizeNameURI(eveName),nobj4.getName());
*/
    }

//    KeyPair root;
//    PrivateKey aliceSigner;
//    PrivateKey bobSigner;
    Store store;
    protected static final String rootName = "/";
    protected static final String bobName = "/bob";
    protected static final String bobAliceName = "/bob/alice";
    protected static final String eveName = "/eve";

    protected static KeyPairGenerator kg;
    protected static KeyPair root;
    protected static KeyPair alice;
    protected static KeyPair bob;
    protected static KeyPair eve;

}