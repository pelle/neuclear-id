package org.neuclear.id;

import org.neuclear.tests.AbstractObjectCreationTest;
import org.neuclear.id.builders.SignedMessageBuilder;
import org.neuclear.id.builders.IdentityBuilder;
import org.neuclear.id.builders.NamedObjectBuilder;
import org.neuclear.commons.NeuClearException;

import java.security.GeneralSecurityException;

/**
 * Created by IntelliJ IDEA.
 * User: pelleb
 * Date: Dec 16, 2003
 * Time: 9:29:02 AM
 * To change this template use Options | File Templates.
 */
public class IdentityCreationTest extends AbstractObjectCreationTest {
    private static final String NAME = "neu://alice@test";
    private static final String REPOSITORY = "http://repository.neuclear.org";
    private static final String SIGNER = "http://users.neuclear.org:8080/DemoSigner";
    private static final String LOGGER = "http://logger.neuclear.org";
    private static final String RECEIVER = "mailto:pelle@neuclear.org";

    public IdentityCreationTest(String string) throws NeuClearException, GeneralSecurityException {
        super(string);
    }

    protected void verifyObject(SignedNamedObject obj) {
        assertTrue(obj instanceof Identity);
        Identity id=(Identity) obj;
        assertEquals(id.getLogger(),LOGGER);
        assertEquals(id.getName(),NAME);
        assertEquals(id.getRepository(),REPOSITORY);
        assertEquals(id.getSigner(),SIGNER);
        assertEquals(id.getReceiver(),RECEIVER);
        assertNotNull(id.getPublicKey());
    }

    protected NamedObjectBuilder createBuilder() throws NeuClearException {
        return new IdentityBuilder(NAME,getSigner().getPublicKey(NAME),REPOSITORY,SIGNER,LOGGER,RECEIVER);
    }
}
