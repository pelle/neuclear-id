/*
 * $Id: EncryptedFileStoreTest.java,v 1.4 2003/11/18 15:07:37 pelle Exp $
 * $Log: EncryptedFileStoreTest.java,v $
 * Revision 1.4  2003/11/18 15:07:37  pelle
 * Changes to JCE Implementation
 * Working on getting all tests working including store tests
 *
 * Revision 1.3  2003/11/11 21:18:46  pelle
 * Further vital reshuffling.
 * org.neudist.crypto.* and org.neudist.utils.* have been moved to respective areas under org.neuclear.commons
 * org.neuclear.signers.* as well as org.neuclear.passphraseagents have been moved under org.neuclear.commons.crypto as well.
 * Did a bit of work on the Canonicalizer and changed a few other minor bits.
 *
 * Revision 1.2  2003/10/02 23:29:03  pelle
 * Updated Root Key. This will be the root key for the remainder of the beta period. With version 1.0 I will update it with a new key.
 * VerifyingTest works now and also does a pass for fake ones. Will have to think of better ways of making fake Identities to break it.
 * Cleaned up much of the tests and they all pass now.
 * The FileStoreTests need to be rethought out, by adding a test key.
 *
 * Revision 1.1.1.1  2003/09/19 14:41:54  pelle
 * First import into the neuclear project. This was originally under the SF neuclear
 * project. This marks a general major refactoring and renaming ahead.
 *
 * The new name for this code is NeuClear Identity and has the general package header of
 * org.neuclear.id
 * There are other areas within the current code which will be split out into other subprojects later on.
 * In particularly the signers will be completely seperated out as well as the contract types.
 *
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

import org.neuclear.commons.NeuClearException;

import java.security.GeneralSecurityException;

public class EncryptedFileStoreTest extends AbstractStoreTest {
    public EncryptedFileStoreTest(String name) throws GeneralSecurityException, NeuClearException {
        super(name);
    }

    /**
     */
    public Store getStoreInstance() {
        return new EncryptedFileStore("target/testdata/efs");
    }

}
