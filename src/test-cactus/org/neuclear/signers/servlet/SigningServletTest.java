package org.neuclear.signers.servlet;

import com.meterware.httpunit.WebForm;
import org.apache.cactus.ServletTestCase;
import org.apache.cactus.WebRequest;
import org.neuclear.auth.AuthenticationTicket;
import org.neuclear.commons.NeuClearException;
import org.neuclear.commons.Utility;
import org.neuclear.commons.crypto.Base64;
import org.neuclear.commons.crypto.signers.JCESigner;
import org.neuclear.commons.crypto.signers.TestCaseSigner;
import org.neuclear.id.SignatureRequest;
import org.neuclear.id.SignedNamedObject;
import org.neuclear.id.builders.AuthenticationTicketBuilder;
import org.neuclear.id.builders.SignatureRequestBuilder;
import org.neuclear.id.verifier.VerifyingReader;
import org.neuclear.xml.XMLException;
import org.xml.sax.SAXException;

import javax.servlet.ServletException;
import java.io.ByteArrayInputStream;
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

$Id: SigningServletTest.java,v 1.4 2004/01/19 23:49:45 pelle Exp $
$Log: SigningServletTest.java,v $
Revision 1.4  2004/01/19 23:49:45  pelle
Unit testing uncovered further issues with Base32
NSTools is now uptodate as are many other classes. All transactional builders habe been updated.
Well on the way towards full "green" on Junit.

Revision 1.3  2003/12/20 00:21:19  pelle
overwrote the standard Object.toString(), hashCode() and equals() methods for SignedNamedObject/Core
fixed cactus tests
Added TransferRequestServlet
Added cactus tests to pay

Revision 1.2  2003/12/12 19:28:03  pelle
All the Cactus tests now for signing servlet.
Added working AuthenticationFilterTest
Returned original functionality to DemoSigningServlet.
This is set up to use the test keys stored in neuclear-commons.
SigningServlet should now work for general use. It uses the default
keystore. Will add configurability later. It also uses the GUIDialogAgent.

Revision 1.1  2003/12/12 15:12:50  pelle
The ReceiverServletTest now passes.
Add first stab at a SigningServletTest which currently doesnt pass.

Revision 1.4  2003/12/12 12:32:54  pelle
Working on getting the SOAPServletTest working under cactus

Revision 1.3  2003/12/11 23:57:30  pelle
Trying to test the ReceiverServlet with cactus. Still no luck. Need to return a ElementProxy of some sort.
Cleaned up some missing fluff in the ElementProxy interface. getTagName(), getQName() and getNameSpace() have been killed.

Revision 1.2  2003/11/28 00:12:59  pelle
Getting the NeuClear web transactions working.

Revision 1.1  2003/11/24 23:33:38  pelle
More Cactus unit testing going on.

*/

/**
 * User: pelleb
 * Date: Nov 24, 2003
 * Time: 5:08:05 PM
 */
public class SigningServletTest extends ServletTestCase {
    public SigningServletTest(String string) throws GeneralSecurityException, NeuClearException {
        super(string);
        signer = new TestCaseSigner();
    }

    public void beginSign(WebRequest theRequest) throws GeneralSecurityException, NeuClearException, XMLException {

        AuthenticationTicketBuilder authreq = new AuthenticationTicketBuilder("neu://bob@test", "neu://test", "http://localhost");
        SignatureRequestBuilder sigreq = new SignatureRequestBuilder("neu://bob@test", authreq, "test");
        SignedNamedObject signed = sigreq.convert("neu://test",signer);
        theRequest.setContentType("application/x-www-form-urlencoded");
        String b64 = Base64.encode(signed.getEncoded().getBytes());
        theRequest.addParameter("neuclear-request", b64, "POST");
        theRequest.addParameter("endpoint", "http://localhost", "POST");
        theRequest.addParameter("passphrase", "neuclear", "POST");
        theRequest.addParameter("sign", "Sign", "POST");
        theRequest.setURL("http://users.neuclear.org", "/test", "/Receiver",
                null, null);
    }

    public void testSign() throws ServletException, IOException {
        assertEquals(request.getContentType(), "application/x-www-form-urlencoded");
        assertEquals(request.getMethod(), "POST");
        SigningServlet servlet = new DemoSigningServlet();
        servlet.init(config);
        servlet.service(request, response);

    }

    public void endSign(com.meterware.httpunit.WebResponse theResponse) throws SAXException, NeuClearException, XMLException {
//        assertEquals("NeuClear Signing Service", theResponse.getTitle());
        WebForm forms[] = theResponse.getForms();
        assertNotNull(forms);
        assertEquals(1, forms.length);
        assertTrue(forms[0].hasParameterNamed("neuclear-request"));
        String encoded = forms[0].getParameterValue("neuclear-request");
        assertTrue(!Utility.isEmpty(encoded));
        final SignedNamedObject obj = VerifyingReader.getInstance().read(new ByteArrayInputStream(Base64.decode(encoded)));
        assertNotNull(obj);
        assertTrue(obj instanceof AuthenticationTicket);
        AuthenticationTicket ticket = (AuthenticationTicket) obj;
        assertEquals(ticket.getSignatory().getName(), "neu://bob@test");
        assertEquals("http://localhost", forms[0].getAction());
    }

    public void beginSignatureRequest(WebRequest theRequest) throws GeneralSecurityException, NeuClearException, XMLException {

        AuthenticationTicketBuilder authreq = new AuthenticationTicketBuilder("neu://bob@test", "neu://test", "http://localhost");
        SignatureRequestBuilder sigreq = new SignatureRequestBuilder( "neu://bob@test", authreq, "test");
        SignedNamedObject signed = sigreq.convert("neu://test",signer);
        theRequest.setContentType("application/x-www-form-urlencoded");
        String b64 = Base64.encode(signed.getEncoded().getBytes());
        theRequest.addParameter("neuclear-request", b64, "POST");
        theRequest.addParameter("endpoint", "http://localhost", "POST");
        theRequest.setURL("http://users.neuclear.org", "/test", "/Receiver",
                null, null);
    }

    public void testSignatureRequest() throws ServletException, IOException {
        assertEquals(request.getContentType(), "application/x-www-form-urlencoded");
        assertEquals(request.getMethod(), "POST");
        SigningServlet servlet = new DemoSigningServlet();
        servlet.init(config);
        servlet.service(request, response);

    }

    public void endSignatureRequest(com.meterware.httpunit.WebResponse theResponse) throws SAXException, NeuClearException, XMLException {
//        assertEquals("NeuClear Signing Service", theResponse.getTitle());
        WebForm forms[] = theResponse.getForms();
        assertNotNull(forms);
        assertEquals(1, forms.length);
        assertTrue(forms[0].hasParameterNamed("neuclear-request"));
        String encoded = forms[0].getParameterValue("neuclear-request");
        assertTrue(!Utility.isEmpty(encoded));
        final SignedNamedObject obj = VerifyingReader.getInstance().read(new ByteArrayInputStream(Base64.decode(encoded)));
        assertNotNull(obj);
        assertTrue(obj instanceof SignatureRequest);
        SignatureRequest sigreq = (SignatureRequest) obj;
        assertEquals(sigreq.getSignatory().getName(), "neu://test");
        assertTrue(forms[0].hasParameterNamed("endpoint"));
        assertEquals("http://localhost", forms[0].getParameterValue("endpoint"));

    }

    JCESigner signer;
}
