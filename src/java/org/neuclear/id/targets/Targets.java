package org.neuclear.id.targets;

import org.dom4j.Element;
import org.neuclear.commons.NeuClearException;
import org.neuclear.id.SignedNamedObject;
import org.neuclear.id.senders.Sender;

import java.util.List;

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

$Id: Targets.java,v 1.3 2004/04/17 19:28:22 pelle Exp $
$Log: Targets.java,v $
Revision 1.3  2004/04/17 19:28:22  pelle
Identity is now fully html based as is the ServiceBuilder.
VerifyingReader correctly identifies html files and parses them as such.
Targets and Target now parse html link tags
AssetBuilder and ExchangeAgentBuilder have been updated to support it and provide html formatted contracts.
The Asset.Reader and ExchangeAgent.Reader still need to be updated.

Revision 1.2  2004/03/02 18:59:10  pelle
Further cleanups in neuclear-id. Moved everything under id.

Revision 1.1  2004/02/18 00:14:31  pelle
Many, many clean ups. I've readded Targets in a new method.
Gotten rid of NamedObjectBuilder and revamped Identity and Resolvers

*/

/**
 * User: pelleb
 * Date: Feb 17, 2004
 * Time: 10:39:07 PM
 */
public final class Targets {
    private Targets(Target list[]) {
        targetlist = list;
    }

    public final void send(SignedNamedObject obj) throws NeuClearException {
        for (int i = 0; i < targetlist.length; i++) {
            Sender.quickSend(targetlist[i].getHref(), obj);
        }
    }

    public final void send(String type, SignedNamedObject obj) throws NeuClearException {
        final int ti = Target.getType(type);
        for (int i = 0; i < targetlist.length; i++) {
            if (targetlist[i].getType() == ti)
                Sender.quickSend(targetlist[i].getHref(), obj);
        }
    }

    public final void log(SignedNamedObject obj) throws NeuClearException {
        send("logger", obj);
    }

    public final int getSize() {
        return targetlist.length;
    }

    Target[] getTargets() {
        return targetlist;
    }

    public final static Targets parseList(Element elem) {
        List list = elem.selectNodes("//html/head/link[starts-with(@rel,'neu:')]");
        Target targets[] = new Target[list.size()];
        Target signer = null;
        for (int i = 0; i < list.size(); i++) {
            targets[i] = Target.parseElement((Element) list.get(i));
        }
        return new Targets(targets);
    }

    private final Target[] targetlist;

    public static final Targets EMPTY = new Targets(new Target[0]);

}

