package org.neuclear.id.auth;

import org.neuclear.commons.Utility;
import org.neuclear.commons.crypto.Base64;
import org.neuclear.id.Identity;
import org.neuclear.id.SignedNamedObject;
import org.neuclear.id.verifier.VerifyingReader;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.Principal;

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

$Id: AuthenticationFilter.java,v 1.1 2004/03/02 18:59:10 pelle Exp $
$Log: AuthenticationFilter.java,v $
Revision 1.1  2004/03/02 18:59:10  pelle
Further cleanups in neuclear-id. Moved everything under id.

Revision 1.2  2003/11/21 04:45:10  pelle
EncryptedFileStore now works. It uses the PBECipher with DES3 afair.
Otherwise You will Finaliate.
Anything that can be final has been made final throughout everyting. We've used IDEA's Inspector tool to find all instance of variables that could be final.
This should hopefully make everything more stable (and secure).

Revision 1.1  2003/11/15 01:58:16  pelle
More work all around on web applications.

*/

/**
 * User: pelleb
 * Date: Nov 14, 2003
 * Time: 3:56:48 PM
 */
public final class AuthenticationFilter implements Filter {
    public final void init(final FilterConfig filterConfig) throws ServletException {
        serviceid = filterConfig.getInitParameter("serviceid");
        ctx = filterConfig.getServletContext();
        ctx.log("AUTH: Starting AuthenticationFilter");

    }

    public final void doFilter(ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
        final HttpSession sess = ((HttpServletRequest) request).getSession(true);
        ctx.log("AUTH: Filtering request: " + ((HttpServletRequest) request).getServletPath());

        if (!Utility.isEmpty(request.getParameter("logout"))) {
            ctx.log("AUTH: Logging out");
            sess.removeAttribute("NeuClearAuthTicket");
        }
        try {
            AuthenticationTicket ticket = null;
            final String reqstring = request.getParameter("neuclear-request");
            if (!Utility.isEmpty(reqstring)) {
                ctx.log("AUTH: Got neuclear-request");

                final SignedNamedObject obj = VerifyingReader.getInstance().read(new ByteArrayInputStream(Base64.decode(reqstring)));
                if (obj instanceof AuthenticationTicket) {
                    ticket = (AuthenticationTicket) obj;
                    sess.setAttribute("NeuClearAuthTicket", ticket);
                }
            } else {
                ticket = (AuthenticationTicket) sess.getAttribute("NeuClearAuthTicket");
            }
            if (ticket != null) {
                final Identity user = ticket.getSignatory();
                request = new HttpServletRequestWrapper((HttpServletRequest) request) {
                    public String getRemoteUser() {
                        return user.getName();    //To change body of overriden methods use Options | File Templates.
                    }

                    public Principal getUserPrincipal() {
                        return user;    //To change body of overriden methods use Options | File Templates.
                    }
                };
                ctx.log("AUTH: logged in:" + user.getName());
            }
        } catch (Exception e) {
            ctx.log("AUTH: " + e.getLocalizedMessage());// The errors arent important we ignore them
        }
        chain.doFilter(request, response);

    }

    public final void destroy() {

    }

    private String serviceid;
    private ServletContext ctx;
}
