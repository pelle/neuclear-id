package org.neuclear.id.jce;

import org.neuclear.commons.NeuClearException;
import org.neuclear.commons.crypto.passphraseagents.AlwaysTheSamePassphraseAgent;
import org.neuclear.commons.crypto.signers.JCESigner;
import org.neuclear.id.Identity;
import org.neuclear.id.builders.AuthenticationTicketBuilder;
import org.neuclear.id.builders.IdentityBuilder;
import org.neuclear.tests.AbstractSigningTest;
import org.neuclear.xml.XMLException;

import java.io.IOException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

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

$Id: NeuClearJCETest.java,v 1.7 2003/12/17 12:45:57 pelle Exp $
$Log: NeuClearJCETest.java,v $
Revision 1.7  2003/12/17 12:45:57  pelle
NeuClear JCE Certificates now work with KeyStore.
We can now create JCE certificates based on NeuClear Identity's and store them in a keystore.

Revision 1.6  2003/12/10 23:58:52  pelle
Did some cleaning up in the builders
Fixed some stuff in IdentityCreator
New maven goal to create executable jarapp
We are close to 0.8 final of ID, 0.11 final of XMLSIG and 0.5 of commons.
Will release shortly.

Revision 1.5  2003/11/21 04:45:17  pelle
EncryptedFileStore now works. It uses the PBECipher with DES3 afair.
Otherwise You will Finaliate.
Anything that can be final has been made final throughout everyting. We've used IDEA's Inspector tool to find all instance of variables that could be final.
This should hopefully make everything more stable (and secure).

Revision 1.4  2003/11/20 23:42:24  pelle
Getting all the tests to work in id
Removing usage of BC in CryptoTools as it was causing issues.
First version of EntityLedger that will use OFB's EntityEngine. This will allow us to support a vast amount databases without
writing SQL. (Yipee)

Revision 1.3  2003/11/19 23:34:00  pelle
Signers now can generatekeys via the generateKey() method.
Refactored the relationship between SignedNamedObject and NamedObjectBuilder a bit.
SignedNamedObject now contains the full xml which is returned with getEncoded()
This means that it is now possible to further receive on or process a SignedNamedObject, leaving
NamedObjectBuilder for its original purposes of purely generating new Contracts.
NamedObjectBuilder.sign() now returns a SignedNamedObject which is the prefered way of processing it.
Updated all major interfaces that used the old model to use the new model.

Revision 1.2  2003/11/18 15:07:37  pelle
Changes to JCE Implementation
Working on getting all tests working including store tests

Revision 1.1  2003/10/01 17:05:38  pelle
Moved the NeuClearCertificate class to be an inner class of Identity.

*/

/**
 * User: pelleb
 * Date: Oct 1, 2003
 * Time: 11:50:58 AM
 */
public final class NeuClearJCETest extends AbstractSigningTest {
    public NeuClearJCETest(final String string) throws NeuClearException, GeneralSecurityException {
        super(string);
        if (Security.getProvider("NeuClear") == null) {
            Security.addProvider(new NeuClearJCEProvider());
            System.err.println("Added NeuClearJCEProvider");
        }
    }

    public final void testProvider() {
        assertNotNull(Security.getProvider("NeuClear"));
    }

    public final void testCertificateFactory() throws CertificateException {
        assertNotNull(CertificateFactory.getInstance("NeuClear"));

    }

    public final void testGetCertificate() throws NeuClearException, XMLException {
        final IdentityBuilder id = new IdentityBuilder("neu://bob@test", signer.getPublicKey("neu://bob@test"));
        final Identity bob = (Identity) id.sign(signer);
        final Certificate cert = bob.getCertificate();
        assertNotNull(cert);
        assertEquals(cert.getPublicKey(), bob.getPublicKey());
    }

    public final void testStoreKey() throws NeuClearException, XMLException, NoSuchProviderException, NoSuchAlgorithmException, KeyStoreException, IOException, CertificateException, UnrecoverableKeyException {
        final KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        final KeyStore ks = KeyStore.getInstance("jks", "SUN");
        ks.load(null, null);

        kpg.initialize(512);
        final KeyPair kp = kpg.generateKeyPair();
        final JCESigner sig2 = new JCESigner(ks, new AlwaysTheSamePassphraseAgent("neuclear"));
        final IdentityBuilder id = new IdentityBuilder("neu://eve@test", kp.getPublic());
        final Identity eve = (Identity) id.sign(signer);

        ks.setKeyEntry("neu://eve@test", kp.getPrivate(), "neuclear".toCharArray(), eve.getCertificateChain());
        assertTrue(ks.containsAlias("neu://eve@test"));
//        assertTrue(ks.isCertificateEntry("neu://eve@test"));
        assertTrue(ks.isKeyEntry("neu://eve@test"));
        assertNotNull(ks.getCertificate("neu://eve@test"));
        assertNotNull(ks.getCertificate("neu://eve@test").getPublicKey());
        assertEquals(eve.getCertificate(),ks.getCertificate("neu://eve@test"));
        assertEquals(eve.getPublicKey(),ks.getCertificate("neu://eve@test").getPublicKey());
        assertEquals(kp.getPrivate(),ks.getKey("neu://eve@test","neuclear".toCharArray()));

        //Lets write it
        File ksfile=new File("target/testdata/keystores/testneuclearcert.jks");
        ksfile.getParentFile().mkdirs();
        try {
            ks.store(new FileOutputStream(ksfile),"neuclear".toCharArray());
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue("Couldnt write file",false);
        }
        final KeyStore ks2 = KeyStore.getInstance("jks", "SUN");
        try {
            ks2.load(new FileInputStream(ksfile), "neuclear".toCharArray());
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue("Couldnt Read File",false);
        }

        assertTrue(ks2.containsAlias("neu://eve@test"));
//        assertTrue(ks2.isCertificateEntry("neu://eve@test"));
        assertTrue(ks2.isKeyEntry("neu://eve@test"));
        assertNotNull(ks2.getCertificate("neu://eve@test"));
        assertNotNull(ks2.getCertificate("neu://eve@test").getPublicKey());
        assertEquals(eve.getCertificate(),ks2.getCertificate("neu://eve@test"));
        assertEquals(eve.getPublicKey(),ks2.getCertificate("neu://eve@test").getPublicKey());
        assertEquals(kp.getPrivate(),ks2.getKey("neu://eve@test","neuclear".toCharArray()));

        //final AuthenticationTicketBuilder authb = new AuthenticationTicketBuilder("neu://eve@test", "neu://test", "http://users.neuclear.org:8080");
        //authb.sign(sig2);

    }
}
