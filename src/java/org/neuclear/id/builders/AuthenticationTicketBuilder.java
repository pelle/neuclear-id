package org.neuclear.id.builders;

import org.dom4j.DocumentHelper;
import org.neuclear.id.auth.AuthenticationTicket;
import org.neuclear.commons.time.TimeTools;
import org.neuclear.id.InvalidNamedObjectException;
import org.neuclear.id.NSTools;

import java.sql.Timestamp;
import java.util.Date;

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

$Id: AuthenticationTicketBuilder.java,v 1.11 2004/03/02 18:59:10 pelle Exp $
$Log: AuthenticationTicketBuilder.java,v $
Revision 1.11  2004/03/02 18:59:10  pelle
Further cleanups in neuclear-id. Moved everything under id.

Revision 1.10  2004/01/13 23:38:26  pelle
Refactoring parts of the core of XMLSignature. There shouldnt be any real API changes.

Revision 1.9  2004/01/13 15:11:35  pelle
Now builds.
Now need to do unit tests

Revision 1.8  2003/12/22 13:45:31  pelle
Added a naive benchmarking tool.
Fixed a bug in AskAtStartupAgent

Revision 1.7  2003/12/11 16:29:26  pelle
Updated various builders to use the new helper methods in AbstractElementProxy hopefully making them more readable.

Revision 1.6  2003/12/11 16:16:14  pelle
Some changes to make the xml a bit more readable.
Also added some helper methods in AbstractElementProxy to make it easier to build objects.

Revision 1.5  2003/12/10 23:58:51  pelle
Did some cleaning up in the builders
Fixed some stuff in IdentityCreator
New maven goal to create executable jarapp
We are close to 0.8 final of ID, 0.11 final of XMLSIG and 0.5 of commons.
Will release shortly.

Revision 1.4  2003/12/06 00:17:03  pelle
Updated various areas in NSTools.
Updated URI Validation in particular to support new expanded format
Updated createUniqueID and friends to be a lot more unique and more efficient.
In CryptoTools updated getRandom() to finally use a SecureRandom.
Changed CryptoTools.getFormatURLSafe to getBase36 because that is what it really is.

Revision 1.3  2003/11/21 04:45:10  pelle
EncryptedFileStore now works. It uses the PBECipher with DES3 afair.
Otherwise You will Finaliate.
Anything that can be final has been made final throughout everyting. We've used IDEA's Inspector tool to find all instance of variables that could be final.
This should hopefully make everything more stable (and secure).

Revision 1.2  2003/11/11 21:18:42  pelle
Further vital reshuffling.
org.neudist.crypto.* and org.neudist.utils.* have been moved to respective areas under org.neuclear.commons
org.neuclear.signers.* as well as org.neuclear.passphraseagents have been moved under org.neuclear.commons.crypto as well.
Did a bit of work on the Canonicalizer and changed a few other minor bits.

Revision 1.1  2003/11/06 20:01:53  pelle
Implemented AuthenticationTicket and friends to comply with the newer model.
Created SignatureRequest and friends to receive unsigned NamedObjectBuilders to interactive signing services.

*/

/**
 * User: pelleb
 * Date: Nov 6, 2003
 * Time: 11:59:58 AM
 */
public final class AuthenticationTicketBuilder extends Builder {
    public AuthenticationTicketBuilder(final String user, final String requester, final String site) throws InvalidNamedObjectException {
        this(user, requester, new Timestamp(new Date().getTime() + 1800000), site);
    }

    public AuthenticationTicketBuilder(final String user, final String requester, final Date validto, final String site) throws InvalidNamedObjectException {
        super(DocumentHelper.createQName(AuthenticationTicket.TAG_NAME, AuthenticationTicket.NS_NSAUTH));
        createAttribute("userid",user);
        createAttribute("requester", NSTools.normalizeNameURI(requester));
        createAttribute("validto", TimeTools.formatTimeStamp(validto));
        createAttribute("sitehref", site);
    }



}
