package org.neuclear.id;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.neuclear.commons.NeuClearException;
import org.neuclear.commons.crypto.Base32;
import org.neuclear.commons.crypto.CryptoException;
import org.neuclear.commons.crypto.CryptoTools;
import org.neuclear.id.builders.Builder;
import org.neuclear.id.builders.IdentityBuilder;
import org.neuclear.id.verifier.VerifyingReader;
import org.neuclear.tests.AbstractObjectCreationTest;
import org.neuclear.xml.xmlsec.EnvelopedSignature;
import org.neuclear.xml.xmlsec.XMLSecurityException;
import org.neuclear.xml.xmlsec.XMLSignature;

import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;

/**
 * Created by IntelliJ IDEA.
 * User: pelleb
 * Date: Dec 16, 2003
 * Time: 9:29:02 AM
 * To change this template use Options | File Templates.
 */
public class IdentityTests extends AbstractObjectCreationTest {
    private static final String SIGNER = "http://localhost:11870/Signer";

    public IdentityTests(String string) throws NeuClearException, GeneralSecurityException {
        super(string);
    }

    protected void verifyObject(SignedNamedObject obj) throws CryptoException {
        assertTrue(obj instanceof Identity);
        Identity id = (Identity) obj;
        assertEquals(CryptoTools.encodeBase32(CryptoTools.digest(signer.getPublicKey(NAME).getEncoded())),
                obj.getName().substring(5, 37));
        assertEquals(new String(CryptoTools.digest(signer.getPublicKey(NAME).getEncoded())),
                new String(Base32.decode(obj.getName().substring(5, 37))));
        assertEquals(id.getSigner(), SIGNER);
        assertNotNull(id.getPublicKey());
    }

    protected Class getRequiredClass() {
        return Identity.class;
    }

    protected Builder createBuilder() throws NeuClearException {
        return new IdentityBuilder(getSigner().getPublicKey(NAME), SIGNER, null, null);
    }

    public void testAnonymous() throws NoSuchAlgorithmException {
        KeyPair kp = CryptoTools.createTinyDSAKeyPair();
        Identity id = new Identity(kp.getPublic());
        assertNotNull(id);
        assertNotNull(id.getName());
        assertEquals("sha1:", id.getName().substring(0, 5));
        assertEquals(CryptoTools.encodeBase32(CryptoTools.digest(kp.getPublic().getEncoded())), id.getName().substring(5));
        assertEquals(kp.getPublic(), id.getPublicKey());

    }

    public void testEmbedded() throws NoSuchAlgorithmException, XMLSecurityException, CryptoException, NameResolutionException, InvalidNamedObjectException {
        KeyPair kp = CryptoTools.createTinyDSAKeyPair();
        Document doc = DocumentHelper.createDocument();
        Element elem = doc.addElement("TestElement");
        XMLSignature sig = new EnvelopedSignature(kp, elem);
        assertEquals(kp.getPublic(), sig.getSignersKey());
        SignedNamedObject obj = VerifyingReader.getInstance().read(elem);
        System.out.println("Name: " + obj.getName());
        assertEquals("sha1:", obj.getName().substring(0, 5));
        assertEquals(CryptoTools.encodeBase32(CryptoTools.digest(kp.getPublic().getEncoded())), obj.getName().substring(5, 37));
        assertEquals(CryptoTools.encodeBase32(CryptoTools.digest(obj.getEncoded().getBytes())), obj.getName().substring(obj.getName().length() - 32));


        Identity id = obj.getSignatory();
        assertNotNull(id);
        assertNotNull(id.getName());
        assertEquals("sha1:", id.getName().substring(0, 5));
        assertEquals(CryptoTools.encodeBase32(CryptoTools.digest(kp.getPublic().getEncoded())), id.getName().substring(5));
        assertEquals(kp.getPublic(), id.getPublicKey());


    }
}
