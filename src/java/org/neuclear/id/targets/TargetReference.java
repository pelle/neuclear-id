/*
 * $Id: TargetReference.java,v 1.4 2003/09/24 23:56:48 pelle Exp $
 * $Log: TargetReference.java,v $
 * Revision 1.4  2003/09/24 23:56:48  pelle
 * Refactoring nearly done. New model for creating signed objects.
 * With view for supporting the xmlpull api shortly for performance reasons.
 * Currently still uses dom4j but that has been refactored out that it
 * should now be very quick to implement a xmlpull implementation.
 *
 * A side benefit of this is that the API has been further simplified. I still have some work
 * todo with regards to cleaning up some of the outlying parts of the code.
 *
 * Revision 1.3  2003/09/23 19:16:27  pelle
 * Changed NameSpace to Identity.
 * To cause less confusion in the future.
 *
 * Revision 1.2  2003/09/22 19:24:01  pelle
 * More fixes throughout to problems caused by renaming.
 *
 * Revision 1.1.1.1  2003/09/19 14:41:10  pelle
 * First import into the neuclear project. This was originally under the SF neudist
 * project. This marks a general major refactoring and renaming ahead.
 *
 * The new name for this code is NeuClear Identity and has the general package header of
 * org.neuclear.id
 * There are other areas within the current code which will be split out into other subprojects later on.
 * In particularly the signers will be completely seperated out as well as the contract types.
 *
 *
 * Revision 1.3  2003/02/18 14:57:19  pelle
 * Finished Cleaning up Receivers and Stores.
 * Also updated nsdl.xsd xml schema with latest changes.
 * The whole API is now very simple.
 *
 * Revision 1.2  2003/02/14 21:10:33  pelle
 * The email sender works. The LogSender and the SoapSender should work but havent been tested yet.
 * The SignedNamedObject has a new log() method that logs it's contents at it's parent Identity's logger.
 * The Identity object also has a new method send() which allows one to send a named object to the Identity's
 * default receiver.
 *
 * Revision 1.1  2003/02/14 14:04:59  pelle
 * The New Source Classes work and NS resolution works as well.
 * I've renamed Target to TargetReference to prepare for the other main refactoring ahead. Implementation of
 * Senders.
 *
 * Revision 1.5  2003/02/10 22:30:12  pelle
 * Got rid of even further dependencies. In Particular OSCore
 *
 * Revision 1.4  2003/02/09 00:15:55  pelle
 * Fixed things so they now compile with r_0.7 of XMLSig
 *
 * Revision 1.3  2002/09/21 23:11:16  pelle
 * A bunch of clean ups. Got rid of as many hard coded URL's as I could.
 *
 * User: pelleb
 * Date: Sep 14, 2002
 * Time: 3:29:03 PM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package org.neuclear.id.targets;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.neuclear.id.SignedNamedObject;
import org.neuclear.senders.Sender;
import org.neudist.utils.NeudistException;
import org.neudist.utils.Utility;
import org.neudist.xml.AbstractElementProxy;

public class TargetReference extends AbstractElementProxy {
    public TargetReference(SignedNamedObject obj, Element elem) throws NeudistException {
        super(elem);
        if (!elem.getName().equals(TAG_NAME))
            throw new NeudistException("Element is not a <Target/> Element");
        owner = obj;
    }

    public TargetReference(SignedNamedObject obj, String href, String type) {
        super(DocumentHelper.createQName(TAG_NAME, SignedNamedObject.NS_NSDL));
        if (!Utility.isEmpty(href))
            getElement().addAttribute(DocumentHelper.createQName("href", SignedNamedObject.NS_NSDL), href);
        if (!Utility.isEmpty(type))
            getElement().addAttribute(DocumentHelper.createQName("type", SignedNamedObject.NS_NSDL), type);
        owner = obj;
    }

    public String getHref() {
        return getElement().attributeValue(DocumentHelper.createQName("href", SignedNamedObject.NS_NSDL));
    }

    public String getType() {
        return getElement().attributeValue(DocumentHelper.createQName("type", SignedNamedObject.NS_NSDL));
    }

    private static final String TAG_NAME = "Target";

    public String getTagName() {
        return TAG_NAME;
    }

    public Namespace getNS() {
        return SignedNamedObject.NS_NSDL;
    }

    public void send() throws NeudistException {
        Sender.quickSend(getHref(), owner);
    }

    private SignedNamedObject owner;
}
