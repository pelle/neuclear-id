package org.neuclear.id.targets;

import org.dom4j.Element;

import java.util.HashMap;

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

$Id: Target.java,v 1.3 2004/04/17 19:28:21 pelle Exp $
$Log: Target.java,v $
Revision 1.3  2004/04/17 19:28:21  pelle
Identity is now fully html based as is the ServiceBuilder.
VerifyingReader correctly identifies html files and parses them as such.
Targets and Target now parse html link tags
AssetBuilder and ExchangeAgentBuilder have been updated to support it and provide html formatted contracts.
The Asset.Reader and ExchangeAgent.Reader still need to be updated.

Revision 1.2  2004/02/19 15:30:20  pelle
Various cleanups and corrections

Revision 1.1  2004/02/18 00:14:31  pelle
Many, many clean ups. I've readded Targets in a new method.
Gotten rid of NamedObjectBuilder and revamped Identity and Resolvers

*/

/**
 * Targets are services that receive SignedObjects
 */
final class Target {
    private Target(int type, String href) {
        this.href = href;
        this.type = type;
    }

    String getHref() {
        return href;
    }

    int getType() {
        return type;
    }

    final static Target make(String type, String href) {
        int t = getType(type);
        return new Target(t, href);
    }

    final static int getType(String type) {
        int t = 0;
        if (type != null && TYPEMAP.containsKey(type))
            t = ((Byte) TYPEMAP.get(type)).byteValue();
        return t;
    }

    final static Target parseElement(Element elem) {

        return make(extractType(elem), elem.attributeValue("href"));
    }

    private static String extractType(Element elem) {
        return elem.attributeValue("rel").substring(4);
    }

    private final String href;
    private final int type;

    private final static String[] TYPES = new String[]{"misc", "auditor", "receiver", "logger", "controller"};

    private final static HashMap TYPEMAP = makeTypeMap();

    // Add further standard TYPES here

    private static HashMap makeTypeMap() {
        HashMap map = new HashMap();
        for (byte i = 0; i < TYPES.length; i++)
            map.put(TYPES[i], new Byte(i));
        return map;
    }
}
