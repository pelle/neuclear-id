/*
 * $Id: EncryptedFileStoreTest.java,v 1.1 2003/09/19 14:41:54 pelle Exp $
 * $Log: EncryptedFileStoreTest.java,v $
 * Revision 1.1  2003/09/19 14:41:54  pelle
 * Initial revision
 *
 * Revision 1.2  2003/02/10 22:30:26  pelle
 * Got rid of even further dependencies. In Particular OSCore
 *
 * Revision 1.1  2003/01/16 19:16:09  pelle
 * Major Structural Changes.
 * We've split the test classes out of the normal source tree, to enable Maven's test support to work.
 * WARNING
 * for Development purposes the code does not as of this commit until otherwise notified actually verifysigs.
 * We are reworking the XMLSig library and need to continue work elsewhere for the time being.
 * DO NOT USE THIS FOR REAL APPS
 *
 * Revision 1.2  2002/09/21 23:11:16  pelle
 * A bunch of clean ups. Got rid of as many hard coded URL's as I could.
 *
 * User: pelleb
 * Date: Sep 10, 2002
 * Time: 5:58:25 PM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package org.neuclear.store;

import junit.framework.Test;
import junit.framework.TestSuite;

import java.security.GeneralSecurityException;

public class EncryptedFileStoreTest extends AbstractStoreTest{
    public EncryptedFileStoreTest(String name) throws GeneralSecurityException {
        super(name);
    }
   public static void main (String[] args) {
        try {
            AbstractStoreTest test=new EncryptedFileStoreTest("Encrypted Test");
            test.setUp();
            test.testStore();
        } catch (Exception e) {
//            if (e instanceof NeudistException) {
//                ((NeudistException)e).getParcel().printStackTrace();
//            } else
                e.printStackTrace();

        }

//		junit.textui.TestRunner.run (suite());
	}
    /**
     */
    public Store getStoreInstance() {
        return new EncryptedFileStore("/home/java/projects/neuclear/build/tests/efs");
    }
    public static Test suite() {
		return new TestSuite(EncryptedFileStoreTest.class);
	}

}
