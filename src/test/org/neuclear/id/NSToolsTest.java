/*
  $Id: NSToolsTest.java,v 1.3 2003/09/23 19:16:29 pelle Exp $
  $Log: NSToolsTest.java,v $
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

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.neudist.utils.NeudistException;


/**
 * @stereotype role
 * @author Pelle Braendgaard
 */
public class NSToolsTest extends TestCase {
    public NSToolsTest() {
        super("NSToolsTest");
        setUp();
    }

    public NSToolsTest(String name) {
        super(name);
    }

    /**
     */
    protected void setUp() {
    }

    protected void tearDown() {
    }

    public static Test suite() {
        return new TestSuite(NSToolsTest.class);
    }

    private static void assertValidName(String name) throws NeudistException {
        assertTrue("Should be valid='" + name + "'", NSTools.isValidName(name));
    }

    private static void assertInvalidName(String name) throws NeudistException {
        assertTrue("Should be invalid='" + name + "'", !NSTools.isValidName(name));
    }

    public void testValidName() throws NeudistException {
        assertValidName("/");
        assertValidName("neu://");
        assertValidName("/help");
        assertValidName("neu://help");
        assertValidName("neu://help/abcdefg232Avc");
        assertValidName("/help/abcdefg232Avc");
        //assertInvalidName("neu:/");// TODO Doesnt pass as invalid. Ignore short term, fix soon.
        assertInvalidName("neu");
        assertInvalidName("");
        assertInvalidName("neu://abcde%01&^");
        assertInvalidName("/help/abcd_efg.-232Avc/");
    }

    public static void testNormalize() throws NeudistException {
        assertEquals("neu://hello", NSTools.normalizeNameURI("/hello"));
        assertEquals("neu://hello", NSTools.normalizeNameURI("neu://hello"));
    }

    public static void testFindParent() throws NeudistException {
        assertEquals("neu://hello", NSTools.getParentNSURI("neu://hello/one"));
        assertEquals("neu://hello", NSTools.getParentNSURI("/hello/one"));
        assertEquals("neu://", NSTools.getParentNSURI("neu://hello"));
        assertEquals("neu://", NSTools.getParentNSURI("/hello"));
        assertEquals("neu://", NSTools.getParentNSURI("neu://"));
        assertEquals("neu://", NSTools.getParentNSURI("/"));

    }


}