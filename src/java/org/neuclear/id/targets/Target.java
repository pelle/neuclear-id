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

$Id: Target.java,v 1.1 2004/02/18 00:14:31 pelle Exp $
$Log: Target.java,v $
Revision 1.1  2004/02/18 00:14:31  pelle
Many, many clean ups. I've readded Targets in a new method.
Gotten rid of NamedObjectBuilder and revamped Identity and Resolvers

*/

/**
 * Targets are services that receive SignedObjects
 */
final class Target {
    private Target( int type, String href) {
        this.href = href;
        this.type = type;
    }

    String getHref() {
        return href;
    }

    int getType() {
        return type;
    }

    final static Target make(String type,String href){
        int t = getType(type);
        return new Target(t,href);
    }

    final static int getType(String type) {
        int t=0;
        if (type!=null&&typemap.containsKey(type))
            t=((Byte)typemap.get(type)).byteValue();
        return t;
    }

    final static Target parseElement(Element elem){
        return make(elem.attributeValue("type"),elem.getTextTrim());
    }

    private final String href;
    private final int type;

    private final static HashMap typemap=new HashMap();

    // Add further standard types here
    private final static String[] types= new String[] {"misc","auditor","receiver","logger"};
    {
        for(byte i=0;i<types.length;i++)
            typemap.put(types[i],new Byte(i));
    }
}
