/*
  $Id: NameSpaceTest.java,v 1.8 2003/11/18 15:07:37 pelle Exp $
  $Log: NameSpaceTest.java,v $
  Revision 1.8  2003/11/18 15:07:37  pelle
  Changes to JCE Implementation
  Working on getting all tests working including store tests

  Revision 1.7  2003/11/11 21:18:46  pelle
  Further vital reshuffling.
  org.neudist.crypto.* and org.neudist.utils.* have been moved to respective areas under org.neuclear.commons
  org.neuclear.signers.* as well as org.neuclear.passphraseagents have been moved under org.neuclear.commons.crypto as well.
  Did a bit of work on the Canonicalizer and changed a few other minor bits.

  Revision 1.6  2003/10/21 22:31:14  pelle
  Renamed NeudistException to NeuClearException and moved it to org.neuclear.commons where it makes more sense.
  Unhooked the XMLException in the xmlsig library from NeuClearException to make all of its exceptions an independent hierarchy.
  Obviously had to perform many changes throughout the code to support these changes.

  Revision 1.5  2003/10/02 23:29:03  pelle
  Updated Root Key. This will be the root key for the remainder of the beta period. With version 1.0 I will update it with a new key.
  VerifyingTest works now and also does a pass for fake ones. Will have to think of better ways of making fake Identities to break it.
  Cleaned up much of the tests and they all pass now.
  The FileStoreTests need to be rethought out, by adding a test key.

  Revision 1.4  2003/09/29 23:17:32  pelle
  Changes to the senders. Now the senders only work with NamedObjectBuilders
  which are the only NamedObject representations that contain full XML.

  Revision 1.3  2003/09/23 19:16:29  pelle
  Changed NameSpace to Identity.
  To cause less confusion in the future.

  Revision 1.2  2003/09/19 14:54:43  pelle
  Bumped version to 0.7
  Changed the logo to the neuclear logo
  And commented out various tests that no longer make sense.

  Revision 1.1.1.1  2003/09/19 14:41:53  pelle
  First import into the neuclear project. This was originally under the SF neuclear
  project. This marks a general major refactoring and renaming ahead.

  The new name for this code is NeuClear Identity and has the general package header of
  org.neuclear.id
  There are other areas within the current code which will be split out into other subprojects later on.
  In particularly the signers will be completely seperated out as well as the contract types.


  Revision 1.5  2003/02/14 05:10:14  pelle
  New Source model is implemented.
  It doesnt quite verify things correctly yet. I'm not yet sure why.
  CommandLineSigner is simplified to make it easier to use.

  Revision 1.4  2003/02/10 22:30:23  pelle
  Got rid of even further dependencies. In Particular OSCore

  Revision 1.3  2003/02/09 00:15:56  pelle
  Fixed things so they now compile with r_0.7 of XMLSig

  Revision 1.2  2003/01/16 22:20:03  pelle
  First Draft of new generalised Ledger Interface.
  Currently we have a Book and Transaction class.
  We also need a Ledger class and a Ledger Factory.

  Revision 1.1  2003/01/16 19:16:09  pelle
  Major Structural Changes.
  We've split the test classes out of the normal source tree, to enable Maven's test support to work.
  WARNING
  for Development purposes the code does not as of this commit until otherwise notified actually verifysigs.
  We are reworking the XMLSig library and need to continue work elsewhere for the time being.
  DO NOT USE THIS FOR REAL APPS

  Revision 1.2  2002/10/02 21:03:44  pelle
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

  Revision 1.1.1.1  2002/09/18 10:55:42  pelle
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


  Revision 1.4  2002/06/18 03:04:11  pelle
  Just added all the necessary jars.
  Fixed a few things in the framework and
  started a GUI Application to manage Neu's.

  Revision 1.3  2002/06/13 19:04:07  pelle
  A start to a web interface into the architecture.
  We're getting a bit further now with functionality.

  Revision 1.2  2002/06/05 23:42:04  pelle
  The Throw clauses of several method definitions were getting out of hand, so I have
  added a new wrapper exception NeuClearException, to keep things clean in the ledger.
  This is used as a catchall wrapper for all Exceptions in the underlying API's such as IOExceptions,
  XML Exceptions etc.
  You can catch any Exception and rethrow it using Utility.rethrowException(e) as a quick way of handling
  exceptions.
  Otherwise the Store framework and the NameSpaces are really comming along quite well. I added a CachedStore
  which wraps around any other Store and caches the access to the store.

  Revision 1.1.1.1  2002/05/29 10:02:22  pelle
  Lets try one more time. This is the first rev of the next gen of Neudist


  Revision 1.1.1.1  2002/03/20 00:46:52  pelleb
  no message


*/

package org.neuclear.id;

import junit.framework.TestCase;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.security.PrivateKey;

import org.neuclear.commons.crypto.CryptoTools;


/**
 * @author Pelle Braendgaard
 */
public class NameSpaceTest extends TestCase {

    public NameSpaceTest(String string) {
        super(string);
        CryptoTools.ensureProvider();
    }

    /**
     * The general test setup consists of a minimal neuspace with 4 keys:
     *<ul>
     * <li>root
     * <li>bob
     * <li>alice
     * <li>eve
     * </ul>
     * We set up a parent neu owned by root. Bob and Alice each own a sub space. Alice also has a subspace of Bob:
     * <ul>
     * <li>neu://
     * <li>neu://bob
     * <li>neu://bob/alice
     * <li>neu://alice
     * </ul>
     * We attempt to create the following NeuSpaces, which shouldn't be allowed:
     * <ul>
     * <li>neu://eve (owned by eve)
     * <li>neu://bob/eve (owned by eve)
     * </ul>
     */
    protected void setUp() throws FileNotFoundException, GeneralSecurityException, IOException {

/*
        try {
            //SignatureAlgorithm.providerInit();
            //SignatureAlgorithm.register(XMLSignature.ALGO_ID_SIGNATURE_RSA,);
            //KeyFactory fact=KeyFactory.getInstance("RSA");
            KeyPairGenerator kgen=KeyPairGenerator.getInstance("RSA");
            kgen.initialize(1024);
            bob=kgen.generateKeyPair();
            alice=kgen.generateKeyPair();
            eve=kgen.generateKeyPair();

            KeyStore ks=KeyStore.getInstance("Uber");
            FileInputStream in=new FileInputStream("src/webapp/WEB-INF/testkeys.ks");
            ks.load(in,"neuclear".toCharArray());
            test= (RSAPrivateKey)ks.getKey("neu://test","neuclear".toCharArray());
//
//            Identity neuBob=createNeuBob();
//            Identity neuAlice=new Identity("neu://alice",root,alice.getPublic());
//            Identity neuBobAlice=new Identity("neu://bob/alice",bob,alice.getPublic());


//            neuspace=new MemoryStore();
//            neuspace.store(neuRoot);
//            neuspace.store(neuBob);
//            neuspace.store(neuAlice);
//            neuspace.store(neuBobAlice);
        } catch (NoSuchAlgorithmException e) {
            Utility.handleException(e);

//        } catch (NeuClearException e) {
//            Utility.handleException(e);
        }
*/

    }


    public void testGetRoot() {
        assertNotNull(Identity.NEUROOT);
    }

    KeyPair root;
    PrivateKey aliceSigner;
    PrivateKey bobSigner;
    PrivateKey test;
    KeyPair bob;
    KeyPair alice;
    KeyPair eve;
//    Store neuspace;


}