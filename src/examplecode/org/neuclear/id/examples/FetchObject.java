package org.neuclear.id.examples;

import org.neuclear.commons.NeuClearException;
import org.neuclear.id.InvalidNamedObjectException;
import org.neuclear.id.NameResolutionException;
import org.neuclear.id.SignedNamedObject;
import org.neuclear.id.resolver.Resolver;

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

$Id: FetchObject.java,v 1.1 2004/04/28 23:51:08 pelle Exp $
$Log: FetchObject.java,v $
Revision 1.1  2004/04/28 23:51:08  pelle
Created various sample programs in src/examplecode/
Preparing documentation for release 0.9
Updating BDG

*/

/**
 * User: pelleb
 * Date: Apr 28, 2004
 * Time: 8:58:48 PM
 */
public class FetchObject {
    public static void main(String args[]) {
        try {
            final String name = (args.length > 0) ? args[0] : "http://talk.org/pelletest.html";

            System.out.print("Fetching: " + name + "...");
            SignedNamedObject obj = Resolver.resolve(name);
            System.out.println(" Success");

            System.out.println("Digest: " + obj.getDigest());
            System.out.println("Signatory: " + obj.getSignatory().getName());

        } catch (NameResolutionException e) {
            e.printStackTrace();
        } catch (InvalidNamedObjectException e) {
            e.printStackTrace();
        } catch (NeuClearException e) {
            e.printStackTrace();
        }
    }
}
