package org.neuclear.tests;

import org.neuclear.commons.NeuClearException;
import org.neuclear.id.SignedNamedObject;
import org.neuclear.id.builders.Builder;
import org.neuclear.xml.XMLException;

import java.security.GeneralSecurityException;
import java.security.PublicKey;

/**
 * Created by IntelliJ IDEA.
 * User: pelleb
 * Date: Dec 16, 2003
 * Time: 9:28:16 AM
 * To change this template use Options | File Templates.
 */
public abstract class AbstractObjectCreationTest extends AbstractSigningTest {
    public AbstractObjectCreationTest(final String string) throws NeuClearException, GeneralSecurityException {
        super(string);
    }

    public void testCreateObject() throws Exception, XMLException {
        Builder builder = createBuilder();
//        System.out.println(builder.asXML());
        assertNotNull(builder);
        SignedNamedObject obj = builder.convert(NAME, getSigner());
        assertNotNull(obj);
        assertEquals(getRequiredClass(), obj.getClass());
        verifyObject(obj);
    }

    protected void assertEquals(byte a[], byte b[]) {
        if (a == null && b == null)
            return;
        assertFalse("neither is null", a == null || b == null);
        assertEquals("same length", a.length, b.length);
        for (int i = 0; i < a.length; i++)
            assertEquals("content same", a[i], b[i]);
    }

    protected void assertEqualPublicKeys(PublicKey a, PublicKey b) {
        assertEquals(a.getClass(), b.getClass());
        assertEquals(a.getEncoded(), b.getEncoded());
    }

    protected abstract void verifyObject(SignedNamedObject obj) throws Exception;

    protected abstract Class getRequiredClass();

    protected abstract Builder createBuilder() throws Exception;

    public static final String NAME = "neu://test";
}
