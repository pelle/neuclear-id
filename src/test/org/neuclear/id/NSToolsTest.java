/*
  $Id: NSToolsTest.java,v 1.8 2003/10/23 22:02:36 pelle Exp $
  $Log: NSToolsTest.java,v $
  Revision 1.8  2003/10/23 22:02:36  pelle
  Moved some certificates to live status at http://repository.neuclear.org
  Updated NSTools.url2path to support neuids with @ signs.

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
  First import into the neuclear project. This was originally under the SF neudist
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


/**
 * @author Pelle Braendgaard
 */
public class NSToolsTest extends TestCase {

    public NSToolsTest(String name) {
        super(name);
    }

    private static void assertValidName(String name) throws NeuClearException {
        assertTrue("Should be valid='" + name + "'", NSTools.isValidName(name));
    }

    private static void assertInvalidName(String name) throws NeuClearException {
        assertTrue("Should be invalid='" + name + "'", !NSTools.isValidName(name));
    }

    public void testValidName() throws NeuClearException {
        assertValidName("/");
        assertValidName("neu://");
        assertValidName("/help");
        assertValidName("neu://help");
        assertValidName("neu://help/abcdefg232Avc");
        assertValidName("/help/abcdefg232Avc");

        assertValidName("neu://pelle@help");
        assertValidName("neu://pelle@help/abcdefg232Avc");

        assertValidName("neu://pelle@neuclear.org");
        assertValidName("neu://pelle@neuclear.org/abcdefg232Avc");

        assertInvalidName("neu:/");
        assertInvalidName("neu://pelle@");
        assertInvalidName("neu://test/pelle@help");
        assertInvalidName("neu://test/pelle@help/abcdefg232Avc");

        assertInvalidName("neu");
        assertInvalidName("");
        assertInvalidName("neu://abcde%01&^");
        assertInvalidName("/help/abcd_efg.-232Avc/");
    }

    public static void testNormalize() throws NeuClearException {
        assertEquals("neu://hello", NSTools.normalizeNameURI("/hello"));
        assertEquals("neu://hello", NSTools.normalizeNameURI("neu://hello"));
    }

    public static void testFindParent() throws NeuClearException {
        assertEquals("neu://hello", NSTools.getParentNSURI("neu://hello/one"));
        assertEquals("neu://hello", NSTools.getParentNSURI("neu://one@hello"));
        assertEquals("neu://one@hello", NSTools.getParentNSURI("neu://one@hello/test"));
        assertEquals("neu://hello", NSTools.getParentNSURI("/hello/one"));
        assertEquals("neu://", NSTools.getParentNSURI("neu://hello"));
        assertEquals("neu://", NSTools.getParentNSURI("/hello"));
        assertEquals("neu://", NSTools.getParentNSURI("neu://"));
        assertEquals("neu://", NSTools.getParentNSURI("/"));

    }

    public static void testURL2Path() {
        assertEquals("/", NSTools.url2path("neu://"));
        assertEquals("/", NSTools.url2path("/"));
        assertEquals("/test", NSTools.url2path("neu://test"));
        assertEquals("/test", NSTools.url2path("/test"));
        assertEquals("/test/@pelle", NSTools.url2path("neu://pelle@test"));
        assertEquals("/test/@pelle", NSTools.url2path("/pelle@test"));
        assertEquals("/test/@pelle/one", NSTools.url2path("neu://pelle@test/one"));
        assertEquals("/test/@pelle/one", NSTools.url2path("/pelle@test/one"));
        assertEquals("/test/@pelle/one/two", NSTools.url2path("neu://pelle@test/one/two"));
        assertEquals("/test/@pelle/one/two", NSTools.url2path("/pelle@test/one/two"));

    }

}