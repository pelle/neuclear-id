package org.neuclear.id;

import org.neuclear.commons.NeuClearException;
import org.neuclear.id.builders.Builder;
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
        SignedMessage msg = (SignedMessage) obj;
        assertNotNull(msg.getSubject());
        assertNotNull(msg.getMessage());
    }

    protected Class getRequiredClass() {
        return SignedMessage.class;
    }

    protected Builder createBuilder() throws NeuClearException {
        return new SignedMessageBuilder("Test", "Hello there");
    }
}
