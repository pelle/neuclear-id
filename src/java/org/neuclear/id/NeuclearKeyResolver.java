package org.neuclear.id;

import org.neuclear.commons.crypto.keyresolvers.KeyResolver;
import org.neuclear.id.resolver.Resolver;

import java.security.PublicKey;

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

$Id: NeuclearKeyResolver.java,v 1.2 2004/04/01 23:19:49 pelle Exp $
$Log: NeuclearKeyResolver.java,v $
Revision 1.2  2004/04/01 23:19:49  pelle
Split Identity into Signatory and Identity class.
Identity remains a signed named object and will in the future just be used for self declared information.
Signatory now contains the PublicKey etc and is NOT a signed object.

Revision 1.1  2004/01/08 23:39:06  pelle
XMLSignature can now give you the Signing key and the id of the signer.
SignedElement can now self verify using embedded public keys as well as KeyName's
Added NeuclearKeyResolver for resolving public key's from Identity certificates.
SignedNamedObjects can now generate their own name using the following format:
neu:sha1://[sha1 of PublicKey]![sha1 of full signed object]
The resulting object has a special internally generted Identity containing the PublicKey
Identity can now contain nothing but a public key

*/

/**
 * User: pelleb
 * Date: Jan 8, 2004
 * Time: 9:34:56 PM
 */
public class NeuclearKeyResolver implements KeyResolver {
    public PublicKey resolve(String name) {
        try {
            return Resolver.resolveIdentity(name).getSignatory().getPublicKey();
        } catch (NameResolutionException e) {
            return null;
        } catch (InvalidNamedObjectException e) {
            return null;
        }
    }
}
