package org.neuclear.auth;

import org.neuclear.commons.crypto.signers.Signer;
import org.neuclear.commons.crypto.signers.TestCaseSigner;
import org.neuclear.commons.NeuClearException;

import javax.servlet.ServletConfig;
import java.io.FileNotFoundException;
import java.security.GeneralSecurityException;

/**
 * Created by IntelliJ IDEA.
 * User: pelleb
 * Date: Dec 12, 2003
 * Time: 9:17:38 PM
 * To change this template use Options | File Templates.
 */
public class DemoAuthenticationServlet extends AuthenticationServlet{
    protected Signer createSigner(ServletConfig config) throws FileNotFoundException, GeneralSecurityException, NeuClearException {
        return new TestCaseSigner();
    }
}
