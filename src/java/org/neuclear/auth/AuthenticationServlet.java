package org.neuclear.auth;

import org.neuclear.commons.NeuClearException;
import org.neuclear.commons.Utility;
import org.neuclear.commons.crypto.signers.Signer;
import org.neuclear.commons.crypto.signers.TestCaseSigner;
import org.neuclear.commons.servlets.ServletTools;
import org.neuclear.id.builders.AuthenticationTicketBuilder;
import org.neuclear.id.builders.SignatureRequestBuilder;
import org.neuclear.id.resolver.NSResolver;
import org.neuclear.xml.XMLException;
import org.neuclear.xml.xmlsec.XMLSecTools;
import org.neuclear.xml.xmlsec.XMLSecurityException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
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

$Id: AuthenticationServlet.java,v 1.4 2003/11/15 01:58:16 pelle Exp $
$Log: AuthenticationServlet.java,v $
Revision 1.4  2003/11/15 01:58:16  pelle
More work all around on web applications.

Revision 1.3  2003/11/12 23:48:14  pelle
Much work done in creating good test environment.
PaymentReceiverTest works, but needs a abit more work in its environment to succeed testing.

Revision 1.2  2003/11/11 21:18:42  pelle
Further vital reshuffling.
org.neudist.crypto.* and org.neudist.utils.* have been moved to respective areas under org.neuclear.commons
org.neuclear.signers.* as well as org.neuclear.passphraseagents have been moved under org.neuclear.commons.crypto as well.
Did a bit of work on the Canonicalizer and changed a few other minor bits.

Revision 1.1  2003/11/06 20:01:52  pelle
Implemented AuthenticationTicket and friends to comply with the newer model.
Created SignatureRequest and friends to send unsigned NamedObjectBuilders to interactive signing services.

*/

/**
 * User: pelleb
 * Date: Nov 6, 2003
 * Time: 2:04:31 PM
 */
public class AuthenticationServlet extends HttpServlet {
    public void init(ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);
        serviceid = servletConfig.getInitParameter("serviceid");
        title = servletConfig.getInitParameter("title");

        try {
            signer = new TestCaseSigner();
        } catch (NeuClearException e) {
            throw new ServletException(e);
        } catch (GeneralSecurityException e) {
            throw new ServletException(e);
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("text/html");

        String siteurl = ServletTools.getAbsoluteURL(request, "/");
        String userns = request.getParameter("identity");
        if (Utility.isEmpty(userns)) {
            response.sendError(500, "No Identity");
            response.flushBuffer();
            return;
        }
        Cookie usercookie = new Cookie("identity", userns);
        //usercookie.setSecure(true);
        usercookie.setMaxAge(2592000);
        response.addCookie(usercookie);
        PrintWriter out = response.getWriter();
        out.write("\n ");
        out.write("<html>\n");
        out.write("<head>");
        out.write("<title>\n");
        out.write(title);
        out.write("</title>");
        out.write("</head>\n");
        out.write("<body>\n");
        out.write("<h3>contacting signing service...");
        out.write("</h3>\n");
        out.flush();

        try {
            AuthenticationTicketBuilder authreq = new AuthenticationTicketBuilder(userns, serviceid, request.getRequestURI());
            SignatureRequestBuilder sigreq = new SignatureRequestBuilder("neu://test", userns, authreq, "Login to Site");
            sigreq.sign(serviceid, signer);
            request.getSession(true).setAttribute("auth", userns);
            out.write("<form action=\"");
            out.print(NSResolver.resolveIdentity(userns).getSigner());
            out.write("\" method=\"POST\">\n    ");
            out.write("<input name=\"base64xml\" value=\"");
            out.print(XMLSecTools.encodeElementBase64(sigreq));
            out.write("\" type=\"hidden\">\n    ");
            out.write("<input name=\"endpoint\" value=\"");
            out.print(siteurl);
            out.write("\" type=\"hidden\"/>\n");
//            out.write("<input type=\"submit\">");
            out.write("</form>\n");
            out.write("<script language=\"javascript\">\n");
            out.write("<!--\n   document.forms[0].submit();\n-->\n");
            out.write("</script>\n");

        } catch (NeuClearException e) {
            e.printStackTrace(out);
        } catch (XMLSecurityException e) {
            e.printStackTrace(out);
        } catch (XMLException e) {
            e.printStackTrace(out);
        }
    }

    private Signer signer;
    private String serviceid;
    private String title;
}
