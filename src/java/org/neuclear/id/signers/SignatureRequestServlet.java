package org.neuclear.id.signers;

import org.neuclear.commons.NeuClearException;
import org.neuclear.commons.Utility;
import org.neuclear.commons.crypto.signers.ServletSignerFactory;
import org.neuclear.commons.crypto.signers.Signer;
import org.neuclear.commons.servlets.ServletTools;
import org.neuclear.id.Identity;
import org.neuclear.id.builders.Builder;
import org.neuclear.id.builders.SignatureRequestBuilder;
import org.neuclear.id.resolver.Resolver;
import org.neuclear.xml.XMLException;
import org.neuclear.xml.xmlsec.XMLSecTools;
import org.neuclear.xml.xmlsec.XMLSecurityException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
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

$Id: SignatureRequestServlet.java,v 1.2 2004/04/01 23:19:48 pelle Exp $
$Log: SignatureRequestServlet.java,v $
Revision 1.2  2004/04/01 23:19:48  pelle
Split Identity into Signatory and Identity class.
Identity remains a signed named object and will in the future just be used for self declared information.
Signatory now contains the PublicKey etc and is NOT a signed object.

Revision 1.1  2004/03/02 18:59:10  pelle
Further cleanups in neuclear-id. Moved everything under id.

Revision 1.5  2004/01/19 23:49:45  pelle
Unit testing uncovered further issues with Base32
NSTools is now uptodate as are many other classes. All transactional builders habe been updated.
Well on the way towards full "green" on Junit.

Revision 1.4  2004/01/12 22:39:26  pelle
Completed all the builders and contracts.
Added a new abstract Value class to contain either an amount or a list of serial numbers.
Now ready to finish off the AssetControllers.

Revision 1.3  2003/12/22 22:15:27  pelle
Last minute cleanups and documentation prior to release 0.8.1

Revision 1.2  2003/12/20 00:21:19  pelle
overwrote the standard Object.toString(), hashCode() and equals() methods for SignedNamedObject/Core
fixed cactus tests
Added TransferRequestServlet
Added cactus tests to pay

Revision 1.1  2003/12/17 23:53:50  pelle
Added SignatureRequestServlet which is abstract and can be used for building SignatureRequests for various applications.

*/

/**
 * User: pelleb
 * Date: Dec 17, 2003
 * Time: 5:54:15 PM
 */
public abstract class SignatureRequestServlet extends HttpServlet {
    public final void init(final ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);
        serviceid = ServletTools.getInitParam("serviceid", servletConfig);
        title = ServletTools.getInitParam("title", servletConfig);

        try {
            signer = createSigner(servletConfig);
        } catch (NeuClearException e) {
            throw new ServletException(e);
        } catch (GeneralSecurityException e) {
            throw new ServletException(e);
        } catch (FileNotFoundException e) {
            throw new ServletException(e);
        }

    }

    protected final String getServiceid() {
        return serviceid;
    }

    protected final String getTitle() {
        return title;
    }

    protected Signer createSigner(ServletConfig config) throws FileNotFoundException, GeneralSecurityException, NeuClearException {
        return ServletSignerFactory.getInstance().createSigner(config);
    }

    protected final void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("text/html");

        final String siteurl = ServletTools.getAbsoluteURL(request, "/");
/*
        final Cookie usercookie = new Cookie("identity", userns);
        //usercookie.setSecure(true);
        usercookie.setMaxAge(2592000);
        response.addCookie(usercookie);
*/
        final PrintWriter out = response.getWriter();
        out.write("\n ");
        out.write("<html>\n");
        out.write("<head>");
        out.write("<title>");
        out.write(title);
        out.write("</title>");
        out.write("</head>\n");
        out.write("<body>\n");
        out.write("<h3>contacting signing service...");
        out.write("</h3>\n");
        out.flush();

        try {
            final Identity user = getUserNS(request);
            final Builder namedreq = createBuilder(request);
            final SignatureRequestBuilder sigreq = new SignatureRequestBuilder(user.getName(), namedreq, "Login to Site");
            sigreq.sign(serviceid, signer);
            out.write("<form action=\"");
            out.print(user.getSigner());
            out.write("\" method=\"POST\" id=\"sigrequest\">\n    ");
            out.write("<input name=\"neuclear-request\" value=\"");
            out.print(XMLSecTools.encodeElementBase64(sigreq));
            out.write("\" type=\"hidden\">\n    ");
            out.write("<input name=\"endpoint\" value=\"");
            out.print(siteurl);
            out.write("\" type=\"hidden\"/>\n");
//            out.write("<input type=\"submit\">");
            out.write("</form>\n");
            out.write("<script language=\"javascript\">\n");
            out.write("<!--\n   sigrequest.submit();\n-->\n");
            out.write("</script>\n");

        } catch (NeuClearException e) {
            e.printStackTrace(out);
        } catch (XMLSecurityException e) {
            e.printStackTrace(out);
        } catch (XMLException e) {
            e.printStackTrace(out);
        }
    }

    protected Identity getUserNS(final HttpServletRequest request) throws NeuClearException {
        if (request.getUserPrincipal() != null)
            return (Identity) request.getUserPrincipal();
        final String username = request.getParameter("identity");
        if (Utility.isEmpty(username))
            throw new NeuClearException("No Identity Provided");
        return Resolver.resolveIdentity(username);
    }

    protected abstract Builder createBuilder(HttpServletRequest request) throws NeuClearException;

    private Signer signer;
    private String serviceid;
    private String title;
}
