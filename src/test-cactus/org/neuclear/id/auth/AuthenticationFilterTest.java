package org.neuclear.id.auth;

import org.apache.cactus.FilterTestCase;
import org.apache.cactus.WebRequest;
import org.neuclear.commons.NeuClearException;
import org.neuclear.commons.crypto.Base32;
import org.neuclear.commons.crypto.Base64;
import org.neuclear.commons.crypto.CryptoTools;
import org.neuclear.commons.crypto.signers.NonExistingSignerException;
import org.neuclear.commons.crypto.signers.TestCaseSigner;
import org.neuclear.id.SignedNamedObject;
import org.neuclear.id.builders.AuthenticationTicketBuilder;
import org.neuclear.xml.XMLException;
import org.neuclear.xml.xmlsec.XMLSecTools;

import javax.servlet.*;
import java.io.IOException;
import java.security.GeneralSecurityException;

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

$Id: AuthenticationFilterTest.java,v 1.2 2004/04/14 23:44:46 pelle Exp $
$Log: AuthenticationFilterTest.java,v $
Revision 1.2  2004/04/14 23:44:46  pelle
Got the cactus tests working and the sample web app

Revision 1.1  2004/03/02 18:59:13  pelle
Further cleanups in neuclear-id. Moved everything under id.

Revision 1.2  2004/01/13 23:38:26  pelle
Refactoring parts of the core of XMLSignature. There shouldnt be any real API changes.

Revision 1.1  2003/12/12 19:28:03  pelle
All the Cactus tests now for signing signers.
Added working AuthenticationFilterTest
Returned original functionality to DemoSigningServlet.
This is set up to use the test keys stored in neuclear-commons.
SigningServlet should now work for general use. It uses the default
keystore. Will add configurability later. It also uses the GUIDialogAgent.

*/

/**
 * User: pelleb
 * Date: Dec 12, 2003
 * Time: 1:25:21 PM
 */
public class AuthenticationFilterTest extends FilterTestCase {
    public AuthenticationFilterTest() throws GeneralSecurityException, NeuClearException {
        signer = new TestCaseSigner();
    }

    public void beginValid(WebRequest theRequest) throws GeneralSecurityException, NeuClearException, XMLException {

        AuthenticationTicketBuilder authreq = new AuthenticationTicketBuilder("http://localhost");
        SignedNamedObject signed = authreq.convert("neu://bob@test", signer);
        theRequest.setContentType("application/x-www-form-urlencoded");
        String b64 = Base64.encode(signed.getEncoded().getBytes());
        theRequest.addParameter("neuclear-request", b64, "POST");
        theRequest.setURL("http://users.neuclear.org", "/test", "/Receiver",
                null, null);
    }

    public void testValid() throws ServletException, IOException {
        AuthenticationFilter filter = new AuthenticationFilter();
        config.setInitParameter("service", "neu://test");
        filter.init(config);

        FilterChain mockFilterChain = new FilterChain() {
            public void doFilter(ServletRequest theRequest,
                                 ServletResponse theResponse) throws IOException, ServletException {
                assertNotNull(request.getUserPrincipal());
                try {
                    assertEquals(Base32.encode(CryptoTools.digest(signer.getPublicKey("neu://bob@test").getEncoded())), request.getUserPrincipal().getName());
                } catch (NonExistingSignerException e) {
                    e.printStackTrace();
                    assertTrue(false);
                }
            }

            public void init(FilterConfig theConfig) {
            }

            public void destroy() {
            }
        };

        filter.doFilter(request, response, mockFilterChain);
    }

    public void beginUnsigned(WebRequest theRequest) throws GeneralSecurityException, NeuClearException, XMLException {

        AuthenticationTicketBuilder authreq = new AuthenticationTicketBuilder("http://localhost");
        theRequest.setContentType("application/x-www-form-urlencoded");
        String b64 = XMLSecTools.encodeElementBase64(authreq);
        theRequest.addParameter("neuclear-request", b64, "POST");
        theRequest.setURL("http://users.neuclear.org", "/test", "/Receiver",
                null, null);
    }

    public void testUnsigned() throws ServletException, IOException {
        AuthenticationFilter filter = new AuthenticationFilter();
        config.setInitParameter("service", "neu://test");
        filter.init(config);

        FilterChain mockFilterChain = new FilterChain() {
            public void doFilter(ServletRequest theRequest,
                                 ServletResponse theResponse) throws IOException, ServletException {
                assertNull(request.getUserPrincipal());
            }

            public void init(FilterConfig theConfig) {
            }

            public void destroy() {
            }
        };

        filter.doFilter(request, response, mockFilterChain);
    }

    private TestCaseSigner signer;
}
