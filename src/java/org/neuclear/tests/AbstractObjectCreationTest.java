package org.neuclear.tests;

import org.neuclear.commons.NeuClearException;
import org.neuclear.commons.crypto.signers.NonExistingSignerException;
import org.neuclear.id.SignedNamedObject;
import org.neuclear.id.builders.Builder;
import org.neuclear.xml.XMLException;

import java.security.GeneralSecurityException;

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
        Builder builder=createBuilder();
        assertNotNull(builder);
//        assertTrue(NSTools.isNamedObject(builder.getElement()));
        SignedNamedObject obj=builder.convert(NAME,getSigner());
        assertNotNull(obj);
        assertEquals(getRequiredClass(),obj.getClass());
        verifyObject(obj);
    }

    protected abstract void verifyObject(SignedNamedObject obj) throws Exception;
    protected abstract Class getRequiredClass();
    protected abstract Builder createBuilder() throws Exception;

    public static final String NAME="test";
}
