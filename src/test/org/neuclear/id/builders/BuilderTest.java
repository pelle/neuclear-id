package org.neuclear.id.builders;

import junit.framework.TestCase;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.neuclear.commons.crypto.passphraseagents.UserCancellationException;
import org.neuclear.commons.crypto.signers.InvalidPassphraseException;
import org.neuclear.commons.crypto.signers.NonExistingSignerException;
import org.neuclear.commons.crypto.signers.Signer;
import org.neuclear.commons.crypto.signers.TestCaseSigner;
import org.neuclear.id.InvalidNamedObjectException;
import org.neuclear.id.NameResolutionException;
import org.neuclear.id.verifier.VerifyingReader;
import org.neuclear.xml.XMLException;
import org.neuclear.xml.XMLTools;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


/*
NeuClear Distributed Transaction Clearing Platform
(C) 2003 Pelle Braendgaard

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 2.1 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library; if not, write to the Free Software
Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

$Id: BuilderTest.java,v 1.1 2004/04/28 00:24:06 pelle Exp $
$Log: BuilderTest.java,v $
Revision 1.1  2004/04/28 00:24:06  pelle
Fixed the strange verification error
Added bunch of new unit tests to support this.
Updated Signer's dependencies and version number to be a 0.9 release.
Implemented ThreadLocalSession session management for Hibernate ledger.
Various other minor changes.

*/

/**
 * User: pelleb
 * Date: Apr 27, 2004
 * Time: 8:40:55 PM
 */
public class BuilderTest extends TestCase {
    public BuilderTest(String string) throws InvalidPassphraseException {
        super(string);
        this.signer = new TestCaseSigner();
        new File("target/testdata/builders/").mkdirs();
    }

    public void testSimpleBuilder() throws XMLException, NonExistingSignerException, UserCancellationException, FileNotFoundException, NameResolutionException {
        signSaveAndVerify(new Builder("test"), "simple.xml");
    }

    public void testSimpleBuilderFromElement() throws XMLException, NonExistingSignerException, UserCancellationException, FileNotFoundException, NameResolutionException {
        Element elem = DocumentHelper.createElement("test");
        signSaveAndVerify(new Builder(elem), "simplefromelem.xml");
    }

    public void signSaveAndVerify(Builder builder, String name) throws XMLException, NonExistingSignerException, UserCancellationException, FileNotFoundException, NameResolutionException {
        builder.sign("bob", signer);
        File output = new File("target/testdata/builders/" + name);
        XMLTools.writeFile(output, builder.getElement());
        try {
            VerifyingReader.getInstance().read(new BufferedInputStream(new FileInputStream(output)));
        } catch (InvalidNamedObjectException e) {
            e.printStackTrace();
            assertTrue(false);
        }
    }

    Signer signer;
}
