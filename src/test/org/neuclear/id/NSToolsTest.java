/*
  $Id: NSToolsTest.java,v 1.14 2003/12/10 23:58:52 pelle Exp $
  $Log: NSToolsTest.java,v $
  Revision 1.14  2003/12/10 23:58:52  pelle
  Did some cleaning up in the builders
  Fixed some stuff in IdentityCreator
  New maven goal to create executable jarapp
  We are close to 0.8 final of ID, 0.11 final of XMLSIG and 0.5 of commons.
  Will release shortly.

  Revision 1.13  2003/12/08 19:32:33  pelle
  Added support for the http scheme into ID. See http://neuclear.org/archives/000195.html

  Revision 1.12  2003/12/06 00:17:04  pelle
  Updated various areas in NSTools.
  Updated URI Validation in particular to support new expanded format
  Updated createUniqueID and friends to be a lot more unique and more efficient.
  In CryptoTools updated getRandom() to finally use a SecureRandom.
  Changed CryptoTools.getFormatURLSafe to getBase36 because that is what it really is.

  Revision 1.11  2003/11/21 04:45:17  pelle
  EncryptedFileStore now works. It uses the PBECipher with DES3 afair.
  Otherwise You will Finaliate.
  Anything that can be final has been made final throughout everyting. We've used IDEA's Inspector tool to find all instance of variables that could be final.
  This should hopefully make everything more stable (and secure).

  Revision 1.10  2003/11/11 21:18:46  pelle
  Further vital reshuffling.
  org.neudist.crypto.* and org.neudist.utils.* have been moved to respective areas under org.neuclear.commons
  org.neuclear.signers.* as well as org.neuclear.passphraseagents have been moved under org.neuclear.commons.crypto as well.
  Did a bit of work on the Canonicalizer and changed a few other minor bits.

  Revision 1.9  2003/11/05 23:40:22  pelle
  A few minor fixes to make all the unit tests work
  Also the start of getting SigningServlet and friends back working.

  Revision 1.8  2003/10/23 22:02:36  pelle
  Moved some certificates to live status at http://repository.neuclear.org
  Updated NSTools.name2path to support neuids with @ signs.

  Revision 1.7  2003/10/22 23:11:43  pelle
  Updated the getParentURI method to support the new neu://test@home format.

  Revision 1.6  2003/10/22 22:12:33  pelle
  Replaced the dependency for the Apache Regex library with JDK1.4's Regex implementation.
  Changed the valid format of NeuClear ID's to include neu://bob@hello/ formatted ids.
  These ids are not identical to neu://hello/bob however in both cases neu://hello has to sign the Identity document.

  Revision 1.5  2003/10/21 22:31:14  pelle
  Renamed NeudistException to NeuClearException and moved it to org.neuclear.commons where it makes more sense.
  Unhooked the XMLException in the xmlsig library from NeuClearException to make all of its exceptions an independent hierarchy.
  Obviously had to perform many changes throughout the code to support these changes.

  Revision 1.4  2003/09/29 23:17:32  pelle
  Changes to the senders. Now the senders only work with NamedObjectBuilders
  which are the only NamedObject representations that contain full XML.

  Revision 1.3  2003/09/23 19:16:29  pelle
  Changed NameSpace to Identity.
  To cause less confusion in the future.

  Revision 1.2  2003/09/22 19:24:03  pelle
  More fixes throughout to problems caused by renaming.

  Revision 1.1.1.1  2003/09/19 14:41:54  pelle
  First import into the neuclear project. This was originally under the SF neuclear
  project. This marks a general major refactoring and renaming ahead.

  The new name for this code is NeuClear Identity and has the general package header of
  org.neuclear.id
  There are other areas within the current code which will be split out into other subprojects later on.
  In particularly the signers will be completely seperated out as well as the contract types.


  Revision 1.3  2003/02/10 22:30:22  pelle
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

  Revision 1.2  2002/09/21 23:11:13  pelle
  A bunch of clean ups. Got rid of as many hard coded URL's as I could.

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




*/

package org.neuclear.id;

import junit.framework.TestCase;
import org.neuclear.commons.NeuClearException;
import org.neuclear.commons.crypto.CryptoTools;


/**
 * @author Pelle Braendgaard
 */
public final class NSToolsTest extends TestCase {

    public NSToolsTest(final String name) {
        super(name);
        CryptoTools.createRandomID();//Initialises the Random number generator to not scew times in the generateID test


    }

    private static void assertValidName(final String name) throws NeuClearException {
        assertTrue("Should be valid='" + name + "'", NSTools.isValidName(name));
    }

    private static void assertInvalidName(final String name) throws NeuClearException {
        assertTrue("Should be invalid='" + name + "'", !NSTools.isValidName(name));
    }

    public final void testValidName() throws NeuClearException {
        assertValidName("neu://");
        assertValidName("neu://hel-_.p");
        assertValidName("neu://help/ab-c_defg232Avc");

        assertValidName("neu://pelle.user-guy_type@help");
        assertValidName("neu://pelle@help/abcdefg232Avc");

        assertValidName("neu://pelle@neuclear.org");
        assertValidName("neu://pelle@neuclear.org/abcdefg232Avc");

        assertValidName("neu://help!aasfdasdf3_.-243");
        assertValidName("neu://help/abcdefg232Avc!aasfdasdf3_.-243");

        assertValidName("neu://pelle@help!aasfdasdf3_.-243");
        assertValidName("neu://pelle@help/abcdefg232Avc!aasfdasdf3_.-243");

        assertValidName("neu://pelle@neuclear.org!aasfdasdf3_.-243");
        assertValidName("neu://pelle@neuclear.org/abcdefg232Avc!aasfdasdf3_.-243");

        assertInvalidName("neu:/");
        assertInvalidName("neu://pelle@");
        assertInvalidName("neu://pelle@/test");
        assertInvalidName("neu://@test");
        assertInvalidName("neu://test/pelle@help");
        assertInvalidName("neu://test/pelle@help/abcdefg232Avc");

        assertInvalidName("neu://hel-_.p*34)");
        assertInvalidName("neu://help/ab-c_d.efg232Avc");

        assertInvalidName("neu://pelle.user-g!uy_type@help");
        assertInvalidName("neu://pelle@help/ab.cdefg232Avc");

        assertInvalidName("/");
        assertInvalidName("/help");
        assertInvalidName("/help/abcdefg232Avc");
        assertInvalidName("/help/abcd_efg.-232Avc/");

        assertInvalidName("neu");
        assertInvalidName("");
        assertInvalidName("neu://abcde%01&^");
        assertInvalidName("/help/test@test/");
    }

    public static void testNormalize() throws NeuClearException {
        assertEquals("neu://hello", NSTools.normalizeNameURI("/hello"));
        assertEquals("neu://hello", NSTools.normalizeNameURI("neu://hello"));
    }

    public static void testFindParent() throws NeuClearException {
        assertEquals("neu://hello", NSTools.getSignatoryURI("neu://hello/one"));
        assertEquals("neu://hello", NSTools.getSignatoryURI("neu://one@hello"));
        assertEquals("neu://one@hello", NSTools.getSignatoryURI("neu://one@hello/test"));
        assertEquals("neu://hello", NSTools.getSignatoryURI("neu://hello/one"));
        assertEquals("neu://", NSTools.getSignatoryURI("neu://hello"));
        assertEquals("neu://one@hello", NSTools.getSignatoryURI("neu://one@hello!test"));
        assertEquals("neu://hello", NSTools.getSignatoryURI("neu://hello!one"));
        assertEquals("neu://", NSTools.getSignatoryURI("neu://"));

    }

    public static void testURL2Path() throws InvalidNamedObject {
        assertEquals("/", NSTools.name2path("neu://"));
        assertEquals("/test", NSTools.name2path("neu://test"));
        assertEquals("/test/@pelle", NSTools.name2path("neu://pelle@test"));
        assertEquals("/test/@pelle/one", NSTools.name2path("neu://pelle@test/one"));
        assertEquals("/test/@pelle/one/two", NSTools.name2path("neu://pelle@test/one/two"));
    }

    public static void testGenerateIDs() {
        assertTrue(NSTools.isValidName(NSTools.createUniqueTransactionID("neu://bob@test", "neu://neuclear.org")));
        assertTrue(NSTools.isValidName(NSTools.createUniqueTransactionID("neu://bob@test/one", "neu://neuclear.org")));
        assertTrue(NSTools.isValidName(NSTools.createUniqueTransactionID("neu://neuclear.org", "neu://bob@neuclear.org")));
        assertTrue(NSTools.isValidName(NSTools.createUniqueTransactionID("neu://bob@test.org", "neu://neuclear.org/test")));
    }

    public static void testIsHttpScheme() {
        assertNotNull(NSTools.isHttpScheme("neu://neuclear.org"));
        assertNotNull(NSTools.isHttpScheme("neu://repository.neuclear.org"));
        assertNull(NSTools.isHttpScheme("neu://neuclear.org/test"));
        assertNull(NSTools.isHttpScheme("neu://test@neuclear.org/test"));
        assertNull(NSTools.isHttpScheme("neu://test@neuclear.org"));
        assertNull(NSTools.isHttpScheme("neu://neuclear.org!sdfsdfdsf"));

    }

    public static void testGetLocal() throws NeuClearException {
        assertEquals("test", NSTools.getLocalName("neu://test"));
        assertEquals("test", NSTools.getLocalName("neu://test@no"));
        assertEquals("test", NSTools.getLocalName("neu://no/test"));
        assertEquals("test", NSTools.getLocalName("neu://no!test"));
        assertEquals("test", NSTools.getLocalName("neu://no@no!test"));
    }
}