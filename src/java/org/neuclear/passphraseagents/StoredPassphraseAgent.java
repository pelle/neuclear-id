package org.neuclear.passphraseagents;

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

$Id: StoredPassphraseAgent.java,v 1.1 2003/10/31 23:58:53 pelle Exp $
$Log: StoredPassphraseAgent.java,v $
Revision 1.1  2003/10/31 23:58:53  pelle
The IdentityCreator now fully works with the new Signer architecture.

*/

/**
 * This agent contains one passphrase which is read at startup
 * from the configuration files.
 * This should never be used for any kind of production server usage.
 * User: pelleb
 * Date: Oct 30, 2003
 * Time: 5:01:14 PM
 */
public class StoredPassphraseAgent implements PassPhraseAgent {

    public StoredPassphraseAgent(String name, String passphrase) {
        this.name = name;
        this.passphrase = passphrase.toCharArray();
        System.out.println("StoredPassphraseAgent started.\nDO NOT USE FOR PRODUCTION SERVERS");
    }

    public char[] getPassPhrase(String name) {
        if (name.equals(this.name))
            return passphrase;
        else
            return new char[0];
    }

    private final String name;
    private final char[] passphrase;
}
