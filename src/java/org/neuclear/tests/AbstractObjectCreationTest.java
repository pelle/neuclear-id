package org.neuclear.tests;

import org.neuclear.commons.NeuClearException;
import org.neuclear.xml.XMLException;
import org.neuclear.id.builders.NamedObjectBuilder;
import org.neuclear.id.builders.SignedMessageBuilder;
import org.neuclear.id.NSTools;
import org.neuclear.id.SignedNamedObject;

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

    public void testCreateObject() throws NeuClearException, XMLException {
        NamedObjectBuilder builder=createBuilder();
        assertNotNull(builder);
        assertTrue(NSTools.isNamedObject(builder.getElement()));
        SignedNamedObject obj=builder.sign(getSigner());
        assertNotNull(obj);
        verifyObject(obj);
    }

    protected abstract void verifyObject(SignedNamedObject obj);

    protected abstract NamedObjectBuilder createBuilder() throws NeuClearException;
}
