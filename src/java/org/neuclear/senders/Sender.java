package org.neuclear.senders;

/**
 * (C) 2003 Antilles Software Ventures SA
 * User: pelleb
 * Date: Feb 14, 2003
 * Time: 9:29:29 AM
 * $Id: Sender.java,v 1.5 2003/10/21 22:31:13 pelle Exp $
 * $Log: Sender.java,v $
 * Revision 1.5  2003/10/21 22:31:13  pelle
 * Renamed NeudistException to NeuClearException and moved it to org.neuclear.commons where it makes more sense.
 * Unhooked the XMLException in the xmlsig library from NeuClearException to make all of its exceptions an independent hierarchy.
 * Obviously had to perform many changes throughout the code to support these changes.
 *
 * Revision 1.4  2003/09/29 23:17:32  pelle
 * Changes to the senders. Now the senders only work with NamedObjectBuilders
 * which are the only NamedObject representations that contain full XML.
 *
 */
import org.neuclear.commons.NeuClearException;
import org.neuclear.id.builders.NamedObjectBuilder;

import java.util.HashMap;
import java.util.Map;

public abstract class Sender {

    public abstract void send(String endpoint,NamedObjectBuilder obj) throws NeuClearException;

    public static void quickSend(String endpoint,NamedObjectBuilder obj) throws NeuClearException {
        int protloc=endpoint.indexOf(":");
        if (protloc<0)
            throw new NeuClearException(endpoint+"Is not in URL format");
        String protocol=endpoint.substring(0,protloc);
        Sender sender=getSender(protocol);
        if (sender==null)
            throw new NeuClearException("Unsupported Send Protocol:" + endpoint.toString());
        sender.send(endpoint,obj);
    }

    public static Sender getSender(String protocol) {
        if (SENDERS==null){
            SENDERS=new HashMap();
            SENDERS.put("soap",new SoapSender());
            SENDERS.put("mailto",new SmtpSender());
        }

        return (Sender) SENDERS.get(protocol);
    }
    private static Map SENDERS;
    public static void main(String args[]){
/*
        try {
            NameSpace pelle=(NameSpace)NamedObjectFactory.fetchNamedObject("neu://free/pelle");
            NamedObject free=NamedObjectFactory.fetchNamedObject("neu://free");
            pelle.send(free);
        } catch (NeuClearException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        }
*/

    }
}

