package org.neuclear.senders;

/**
 * (C) 2003 Antilles Software Ventures SA
 * User: pelleb
 * Date: Feb 14, 2003
 * Time: 9:50:30 AM
 * $Id: SoapSender.java,v 1.3 2003/09/23 19:16:28 pelle Exp $
 * $Log: SoapSender.java,v $
 * Revision 1.3  2003/09/23 19:16:28  pelle
 * Changed NameSpace to Identity.
 * To cause less confusion in the future.
 *
 * Revision 1.2  2003/09/22 19:24:02  pelle
 * More fixes throughout to problems caused by renaming.
 *
 * Revision 1.1.1.1  2003/09/19 14:41:29  pelle
 * First import into the neuclear project. This was originally under the SF neudist
 * project. This marks a general major refactoring and renaming ahead.
 *
 * The new name for this code is NeuClear Identity and has the general package header of
 * org.neuclear.id
 * There are other areas within the current code which will be split out into other subprojects later on.
 * In particularly the signers will be completely seperated out as well as the contract types.
 *
 *
 * Revision 1.1  2003/02/14 21:10:35  pelle
 * The email sender works. The LogSender and the SoapSender should work but havent been tested yet.
 * The NamedObject has a new log() method that logs it's contents at it's parent Identity's logger.
 * The Identity object also has a new method send() which allows one to send a named object to the Identity's
 * default receiver.
 *
 */

import org.neuclear.id.NamedObject;
import org.neudist.utils.NeudistException;
import org.neudist.xml.soap.SOAPTools;


public class SoapSender extends Sender {
    public void send(String endpoint, NamedObject obj) throws NeudistException {
        SOAPTools.soapRequest(endpoint, obj.getElement(), "/receive");
    }
}
