package org.neuclear.receiver;

import org.apache.cactus.ServletTestCase;
import org.apache.cactus.WebRequest;
import org.neuclear.auth.AuthenticationTicket;
import org.neuclear.commons.NeuClearException;
import org.neuclear.commons.crypto.signers.JCESigner;
import org.neuclear.commons.crypto.signers.TestCaseSigner;
import org.neuclear.id.builders.AuthenticationTicketBuilder;
import org.neuclear.xml.XMLException;
import org.neuclear.xml.soap.SOAPTools;
import org.neuclear.xml.xmlsec.XMLSecTools;

import javax.servlet.ServletException;
import java.io.IOException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.security.GeneralSecurityException;
import java.net.HttpURLConnection;

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

$Id: ReceiverServletTest.java,v 1.4 2003/12/12 12:32:54 pelle Exp $
$Log: ReceiverServletTest.java,v $
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
public class ReceiverServletTest extends ServletTestCase {
    public ReceiverServletTest(String string) throws GeneralSecurityException, NeuClearException {
        super(string);
        signer = new TestCaseSigner();
    }

    public void beginReceiveBase64(WebRequest theRequest) throws GeneralSecurityException, NeuClearException, XMLException {

        AuthenticationTicketBuilder builder = new AuthenticationTicketBuilder("neu://bob@test", "neu://test", "http://localhost");
        AuthenticationTicket ticket = (AuthenticationTicket) builder.sign(signer);
        theRequest.setContentType("application/x-www-form-urlencoded");
        String b64 = XMLSecTools.encodeElementBase64(builder.getElement());
        theRequest.addParameter("neuclear-request", b64, WebRequest.POST_METHOD);
        theRequest.setURL("http://users.neuclear.org", "/test", "/Receiver",
                null, null);
    }

    public void testReceiveBase64() throws ServletException, IOException {
        ReceiverServlet servlet = new ReceiverServlet();
        MockReceiver receiver = new MockReceiver();
        servlet.setReceiver(receiver);
        servlet.init(config);
        servlet.service(request, response);
        assertNotNull(receiver.getLastReceived());
        assertEquals(receiver.getLastReceived().getSignatory().getName(), "neu://bob@test");

    }
   public void beginReceiveSOAP(WebRequest theRequest) throws GeneralSecurityException, NeuClearException, XMLException, IOException {

        AuthenticationTicketBuilder builder = new AuthenticationTicketBuilder("neu://alice@test", "neu://test", "http://localhost");
        AuthenticationTicket ticket = (AuthenticationTicket) builder.sign(signer);
        theRequest.setContentType("text/xml");
        theRequest.addHeader("SOAPAction:","/Receive");
        theRequest.setURL("http://users.neuclear.org", "/test", "/Service",
                null, null);
       ByteArrayOutputStream bos=new ByteArrayOutputStream();
       SOAPTools.createSoapRequestString(bos,ticket.getEncoded());
       theRequest.setUserData(new ByteArrayInputStream(bos.toByteArray()));
     }

    public void testReceiveSOAP() throws ServletException, IOException {
        ReceiverServlet servlet = new ReceiverServlet();
        MockReceiver receiver = new MockReceiver();
        servlet.setReceiver(receiver);
        servlet.init(config);
        servlet.service(request, response);
        assertNotNull(receiver.getLastReceived());
        assertEquals(receiver.getLastReceived().getSignatory().getName(), "neu://alice@test");

    }
    JCESigner signer;
}
