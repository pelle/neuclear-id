/*
 * $Id: MemoryStoreTest.java,v 1.1 2003/09/19 14:41:56 pelle Exp $
 * $Log: MemoryStoreTest.java,v $
 * Revision 1.1  2003/09/19 14:41:56  pelle
 * Initial revision
 *
 * Revision 1.2  2003/02/10 22:30:29  pelle
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

public class MemoryStoreTest extends AbstractStoreTest{
    public MemoryStoreTest(String name) throws GeneralSecurityException {
        super(name);
    }
    public MemoryStoreTest() throws GeneralSecurityException {
        super("MemoryStoreTest");
    }

    /**
     */
    public Store getStoreInstance() {
        return new MemoryStore();
    }
    public static Test suite() {
		return new TestSuite(MemoryStoreTest.class);
	}

}
