/* $Id: SimpleSignerStoreTest.java,v 1.1 2003/09/19 14:42:03 pelle Exp $
 * $Log: SimpleSignerStoreTest.java,v $
 * Revision 1.1  2003/09/19 14:42:03  pelle
 * Initial revision
 *
 * Revision 1.4  2003/02/18 00:06:15  pelle
 * Moved the SignerStore's into xml-sig
 *
 * Revision 1.3  2003/02/10 22:30:24  pelle
 * Got rid of even further dependencies. In Particular OSCore
 *
 * Revision 1.2  2003/02/09 00:15:56  pelle
 * Fixed things so they now compile with r_0.7 of XMLSig
 *
 * Revision 1.1  2003/01/16 19:16:09  pelle
 * Major Structural Changes.
 * We've split the test classes out of the normal source tree, to enable Maven's test support to work.
 * WARNING
 * for Development purposes the code does not as of this commit until otherwise notified actually verifysigs.
 * We are reworking the XMLSig library and need to continue work elsewhere for the time being.
 * DO NOT USE THIS FOR REAL APPS
 *
 * Revision 1.2  2002/10/06 00:39:26  pelle
 * I have now expanded support for different types of Signers.
 * There is now a JCESignerStore which uses a JCE KeyStore for signing.
 * I have refactored the SigningServlet a bit, eliminating most of the demo code.
 * This has been moved into DemoSigningServlet.
 * I have expanded the CommandLineSigner, so it now also has an option for specifying a default signing service.
 * The default web application now contains two signers.
 * - The Demo one is still at /Signer
 * - There is a new one at /personal/Signer this uses the testkeys.ks for
 * signing anything under neu://test
 * Note neu://test now has a default interactive signer running on localhost.
 * So to play with this you must install the webapp on your own local machine.
 *
 * Revision 1.1  2002/09/23 15:09:11  pelle
 * Got the SimpleSignerStore working properly.
 * I couldn't get SealedObjects working with BouncyCastle's Symmetric keys.
 * Don't know what I was doing, so I reimplemented it. Encrypting
 * and decrypting it my self.
 *
 */
package org.neuclear.signers;

import junit.framework.TestCase;
import org.neuclear.utils.NeudistException;
import org.neuclear.crypto.signerstores.SimpleSignerStore;

import java.io.File;
import java.io.IOException;
import java.security.*;


/**
 * @author pelleb
 * @version $Revision: 1.1 $
 **/
public class SimpleSignerStoreTest extends TestCase  {
    public SimpleSignerStoreTest(String name) throws GeneralSecurityException,NeudistException {
		super(name);
        setUp();
    }
    /**
     */
    public static  SimpleSignerStore getSignerStoreInstance() throws NeudistException, GeneralSecurityException{

        return new SimpleSignerStore(new File("target/tests/keystores"));
    }

    protected void setUp() throws NeudistException,GeneralSecurityException{
        store=getSignerStoreInstance();
        generateKeys();
    }
    protected static synchronized void generateKeys() throws GeneralSecurityException {
        if (kg==null) {
            System.out.println("Generating Test Keys");
            kg=KeyPairGenerator.getInstance("RSA");

            kg.initialize(1048,new SecureRandom("Bear it all with NeuDist".getBytes()));
     //       kp=kg.generateKeyPair();
            root=kg.generateKeyPair();
            bob=kg.generateKeyPair();
        }
    }

    protected void tearDown() {
        store=null;
        }

     public void testAddKey() throws NeudistException,GeneralSecurityException,IOException {
         boolean success=false;
         try {
             store.addKey("root","root".toCharArray(),root.getPrivate());
             success=true;
         } catch (GeneralSecurityException e) {
            e.printStackTrace();
         } catch (IOException e) {
             e.printStackTrace();
         }
         assertTrue("Managed to add a key",success);
     }
    public void testGetKey() throws NeudistException,GeneralSecurityException,IOException {
         boolean success=false;
         PrivateKey key=null;
         try {
             store.addKey("bob","bob".toCharArray(),bob.getPrivate());
             key=store.getKey("bob","bob".toCharArray());
             success=true;
         } catch (GeneralSecurityException e) {
            e.printStackTrace();
         } catch (IOException e) {
             e.printStackTrace();
         }
         assertTrue("Managed to add and Fetch a  key",success);
         assertNotNull("Key wasn't null",key);
         assertEquals("Gotten Key was the same as Stored Key",bob.getPrivate(),key);
     }
    public static void main (String[] args) {
        try {
            SimpleSignerStoreTest test=new SimpleSignerStoreTest("SimpleSignerStoreTest");
            test.setUp();
            test.testGetKey();
        } catch (Exception e) {
//            if (e instanceof NeudistException) {
//                ((NeudistException)e).getParcel().printStackTrace();
//            } else
                e.printStackTrace();

        }

//		junit.textui.TestRunner.run (suite());
	}

    private SimpleSignerStore store;
    private static KeyPairGenerator kg;
    protected static KeyPair root;
    protected static KeyPair bob;

}
