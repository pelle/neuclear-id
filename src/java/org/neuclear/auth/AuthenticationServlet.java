package org.neuclear.auth;

import org.neuclear.commons.NeuClearException;
import org.neuclear.commons.Utility;
import org.neuclear.commons.crypto.signers.*;
import org.neuclear.commons.crypto.passphraseagents.*;
import org.neuclear.commons.crypto.CryptoTools;
import org.neuclear.commons.servlets.ServletTools;
import org.neuclear.id.builders.AuthenticationTicketBuilder;
import org.neuclear.id.builders.SignatureRequestBuilder;
import org.neuclear.id.builders.NamedObjectBuilder;
import org.neuclear.id.resolver.NSResolver;
import org.neuclear.id.Identity;
import org.neuclear.id.InvalidNamedObjectException;
import org.neuclear.xml.XMLException;
import org.neuclear.xml.xmlsec.XMLSecTools;
import org.neuclear.xml.xmlsec.XMLSecurityException;
import org.neuclear.signers.servlet.SignatureRequestServlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
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

$Id: AuthenticationServlet.java,v 1.12 2003/12/17 23:53:50 pelle Exp $
$Log: AuthenticationServlet.java,v $
Revision 1.12  2003/12/17 23:53:50  pelle
Added SignatureRequestServlet which is abstract and can be used for building SignatureRequests for various applications.

Revision 1.11  2003/12/16 15:04:59  pelle
Added SignedMessage contract for signing simple textual contracts.
Added NeuSender, updated SmtpSender and Sender to take plain email addresses (without the mailto:)
Added AbstractObjectCreationTest to make it quicker to write unit tests to verify
NamedObjectBuilder/SignedNamedObject Pairs.
Sample application has been expanded with a basic email application.
Updated docs for sample web app.
Added missing LGPL LICENSE.txt files to signer and sample app

Revision 1.10  2003/12/15 23:33:04  pelle
added ServletTools.getInitParam() which first tries the ServletConfig, then the context config.
All the web.xml's have been updated to support this. Also various further generalizations have been done throughout
for getServiceid(), getTitle(), getSigner()

Revision 1.9  2003/12/14 20:53:04  pelle
Added ServletPassPhraseAgent which uses ThreadLocal to transfer the passphrase to the signer.
Added ServletSignerFactory, which builds Signers for use within servlets based on parameters in the Servlets
Init parameters in web.xml
Updated SQLContext to use ThreadLocal
Added jakarta cactus unit tests to neuclear-commons to test the 2 new features above.
Added use of the new features in neuclear-commons to the servilets within neuclear-id and added
configuration parameters in web.xml

Revision 1.7  2003/12/10 23:58:51  pelle
Did some cleaning up in the builders
Fixed some stuff in IdentityCreator
New maven goal to create executable jarapp
We are close to 0.8 final of ID, 0.11 final of XMLSIG and 0.5 of commons.
Will release shortly.

Revision 1.6  2003/11/21 04:45:10  pelle
EncryptedFileStore now works. It uses the PBECipher with DES3 afair.
Otherwise You will Finaliate.
Anything that can be final has been made final throughout everyting. We've used IDEA's Inspector tool to find all instance of variables that could be final.
This should hopefully make everything more stable (and secure).

Revision 1.5  2003/11/18 23:35:45  pelle
Payment Web Application is getting there.

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
Created SignatureRequest and friends to receive unsigned NamedObjectBuilders to interactive signing services.

*/

/**
 * User: pelleb
 * Date: Nov 6, 2003
 * Time: 2:04:31 PM
 */
public class AuthenticationServlet extends SignatureRequestServlet {

    protected NamedObjectBuilder createBuilder(final HttpServletRequest request) throws NeuClearException {
        final String userns = request.getParameter("identity");
        request.getSession(true).setAttribute("auth", userns);
        return new AuthenticationTicketBuilder(userns, getServiceid(), request.getRequestURI());
    }

}
