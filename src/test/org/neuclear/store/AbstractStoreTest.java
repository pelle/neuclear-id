/*
  $Id: AbstractStoreTest.java,v 1.17 2004/02/18 00:14:36 pelle Exp $
  $Log: AbstractStoreTest.java,v $
  Revision 1.17  2004/02/18 00:14:36  pelle
  Many, many clean ups. I've readded Targets in a new method.
  Gotten rid of NamedObjectBuilder and revamped Identity and Resolvers

  Revision 1.16  2003/12/11 23:57:30  pelle
  Trying to test the ReceiverServlet with cactus. Still no luck. Need to return a ElementProxy of some sort.
  Cleaned up some missing fluff in the ElementProxy interface. getTagName(), getQName() and getNameSpace() have been killed.

  Revision 1.15  2003/12/10 23:58:52  pelle
  Did some cleaning up in the builders
  Fixed some stuff in IdentityCreator
  New maven goal to create executable jarapp
  We are close to 0.8 final of ID, 0.11 final of XMLSIG and 0.5 of commons.
  Will release shortly.

  Revision 1.14  2003/11/21 04:45:17  pelle
  EncryptedFileStore now works. It uses the PBECipher with DES3 afair.
  Otherwise You will Finaliate.
  Anything that can be final has been made final throughout everyting. We've used IDEA's Inspector tool to find all instance of variables that could be final.
  This should hopefully make everything more stable (and secure).

  Revision 1.13  2003/11/19 23:34:00  pelle
  Signers now can generatekeys via the generateKey() method.
  Refactored the relationship between SignedNamedObject and NamedObjectBuilder a bit.
  SignedNamedObject now contains the full xml which is returned with getEncoded()
  This means that it is now possible to further receive on or process a SignedNamedObject, leaving
  NamedObjectBuilder for its original purposes of purely generating new Contracts.
  NamedObjectBuilder.sign() now returns a SignedNamedObject which is the prefered way of processing it.
  Updated all major interfaces that used the old model to use the new model.

  Revision 1.12  2003/11/18 15:45:09  pelle
  FileStoreTest now passes. FileStore works again.

  Revision 1.11  2003/11/18 15:07:37  pelle
  Changes to JCE Implementation
  Working on getting all tests working including store tests

  Revision 1.10  2003/11/15 01:58:19  pelle
  More work all around on web applications.

  Revision 1.9  2003/11/12 23:48:14  pelle
  Much work done in creating good test environment.
  PaymentReceiverTest works, but needs a abit more work in its environment to succeed testing.

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

import org.neuclear.commons.NeuClearException;
import org.neuclear.id.InvalidNamedObjectException;
import org.neuclear.id.SignedNamedObject;
import org.neuclear.id.builders.IdentityBuilder;
import org.neuclear.tests.AbstractSigningTest;
import org.neuclear.xml.XMLException;

import java.security.GeneralSecurityException;


/**
 * @author Pelle Braendgaard
 */
public abstract class AbstractStoreTest extends AbstractSigningTest {
    public AbstractStoreTest(final String name) throws GeneralSecurityException, NeuClearException {
        super(name);
        store = getStoreInstance();
        //generateKeys();
    }

    /**
     */
    public abstract Store getStoreInstance();


    protected final void tearDown() {
        store = null;
    }


    public final void testStore() throws NeuClearException, InvalidNamedObjectException, XMLException {
        System.out.println("\nTesting " + this.getClass().getName());
        System.out.println("Storing " + bobName);
        final IdentityBuilder bob = new IdentityBuilder(signer.getPublicKey(bobName));
        store.receive(bob.convert(bobName, signer));
        System.out.println("Storing " + aliceName);
        final IdentityBuilder alice = new IdentityBuilder(signer.getPublicKey(aliceName));
        store.receive(alice.convert(aliceName, signer));

        System.out.println("Fetching " + bobName);
        final SignedNamedObject nobj2 = store.fetch(bobName);
        assertNotNull(nobj2);
        assertEquals(bobName, nobj2.getName());

        System.out.println("Fetching " + aliceName);
        final SignedNamedObject nobj4 = store.fetch(aliceName);
        assertEquals(aliceName, nobj4.getName());
    }

    private Store store;
    protected static final String bobName = "neu://bob@test";
    protected static final String aliceName = "neu://alice@test";

}