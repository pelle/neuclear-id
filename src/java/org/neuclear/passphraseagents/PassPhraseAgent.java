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

$Id: PassPhraseAgent.java,v 1.1 2003/10/29 21:16:27 pelle Exp $
$Log: PassPhraseAgent.java,v $
Revision 1.1  2003/10/29 21:16:27  pelle
Refactored the whole signing process. Now we have an interface called Signer which is the old SignerStore.
To use it you pass a byte array and an alias. The sign method then returns the signature.
If a Signer needs a passphrase it uses a PassPhraseAgent to present a dialogue box, read it from a command line etc.
This new Signer pattern allows us to use secure signing hardware such as N-Cipher in the future for server applications as well
as SmartCards for end user applications.

*/

/**
 * This interface is used to retrieve passphrases either interactively
 * or from a configuration file.
 * User: pelleb
 * Date: Oct 29, 2003
 * Time: 11:47:39 AM
 */
public interface PassPhraseAgent {
    char[] getPassPhrase(String name);
}
