package org.neuclear.id;

import org.neuclear.commons.NeuClearException;
import org.neuclear.id.builders.NamedObjectBuilder;
import org.neuclear.id.builders.SignedMessageBuilder;
import org.neuclear.tests.AbstractObjectCreationTest;

import java.security.GeneralSecurityException;

/**
 * Created by IntelliJ IDEA.
 * User: pelleb
 * Date: Dec 16, 2003
 * Time: 9:19:51 AM
 * To change this template use Options | File Templates.
 */
public class SignedMessageTest extends AbstractObjectCreationTest {
    public SignedMessageTest(String string) throws NeuClearException, GeneralSecurityException {
        super(string);
    }

    protected void verifyObject(SignedNamedObject obj) {
        assertTrue(obj instanceof SignedMessage);
        SignedMessage msg=(SignedMessage)obj;
        assertNotNull(msg.getRecipient());
        assertNotNull(msg.getSubject());
        assertNotNull(msg.getMessage());
    }

    protected NamedObjectBuilder createBuilder() throws NeuClearException {
        return new SignedMessageBuilder(getBob(),"neu://alice@test","Test","Hello there");
    }
}
