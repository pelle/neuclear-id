package org.neuclear.senders;
  /**
 * (C) 2003 Antilles Software Ventures SA
 * User: pelleb
 * Date: Feb 14, 2003
 * Time: 9:50:30 AM
 * $Id: SoapSender.java,v 1.1 2003/09/19 14:41:29 pelle Exp $
 * $Log: SoapSender.java,v $
 * Revision 1.1  2003/09/19 14:41:29  pelle
 * Initial revision
 *
 * Revision 1.1  2003/02/14 21:10:35  pelle
 * The email sender works. The LogSender and the SoapSender should work but havent been tested yet.
 * The NamedObject has a new log() method that logs it's contents at it's parent NameSpace's logger.
 * The NameSpace object also has a new method send() which allows one to send a named object to the NameSpace's
 * default receiver.
 *
 */

import org.neuclear.id.NamedObject;
import org.neuclear.utils.NeudistException;
import org.neuclear.xml.soap.SOAPTools;


public class SoapSender extends Sender {
    public void send(String endpoint, NamedObject obj) throws NeudistException {
        SOAPTools.soapRequest(endpoint,obj.getElement(),"/receive");
    }
}
