/*
 * $Id: TargetReference.java,v 1.4 2003/11/11 21:18:42 pelle Exp $
 * $Log: TargetReference.java,v $
 * Revision 1.4  2003/11/11 21:18:42  pelle
 * Further vital reshuffling.
 * org.neudist.crypto.* and org.neudist.utils.* have been moved to respective areas under org.neuclear.commons
 * org.neuclear.signers.* as well as org.neuclear.passphraseagents have been moved under org.neuclear.commons.crypto as well.
 * Did a bit of work on the Canonicalizer and changed a few other minor bits.
 *
 * Revision 1.3  2003/10/21 22:31:12  pelle
 * Renamed NeudistException to NeuClearException and moved it to org.neuclear.commons where it makes more sense.
 * Unhooked the XMLException in the xmlsig library from NeuClearException to make all of its exceptions an independent hierarchy.
 * Obviously had to perform many changes throughout the code to support these changes.
 *
 * Revision 1.2  2003/10/01 19:08:30  pelle
 * Changed XML Format. Now NameSpace has been modified to Identity also the
 * xml namespace prefix nsdl has been changed to neuid.
 * The standard constants for using these have been moved into NSTools.
 * The NamedObjectBuilder can also now take an Element, such as an unsigned template.
 *
 * Revision 1.1  2003/09/27 19:23:11  pelle
 * Added Builders to create named objects from scratch.
 *
 * Revision 1.3  2003/02/18 14:57:19  pelle
 * Finished Cleaning up Receivers and Stores.
 * Also updated nsdl.xsd xml schema with latest changes.
 * The whole API is now very simple.
 *
 * Revision 1.2  2003/02/14 21:10:33  pelle
 * The email sender works. The LogSender and the SoapSender should work but havent been tested yet.
 * The NamedObject has a new log() method that logs it's contents at it's parent NameSpace's logger.
 * The NameSpace object also has a new method send() which allows one to send a named object to the NameSpace's
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
package org.neuclear.id.builders;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.neuclear.id.NSTools;
import org.neuclear.senders.Sender;
import org.neuclear.commons.NeuClearException;
import org.neuclear.commons.Utility;
import org.neuclear.xml.AbstractElementProxy;

public class TargetReference extends AbstractElementProxy {
    public TargetReference(NamedObjectBuilder obj, Element elem) throws NeuClearException {
        super(elem);
        if (!elem.getName().equals(TAG_NAME))
            throw new NeuClearException("Element is not a <Target/> Element");
        owner = obj;
    }

    public TargetReference(NamedObjectBuilder obj, String href, String type) {
        super(DocumentHelper.createQName(TAG_NAME, NSTools.NS_NEUID));
        if (!Utility.isEmpty(href))
            getElement().addAttribute(DocumentHelper.createQName("href", NSTools.NS_NEUID), href);
        if (!Utility.isEmpty(type))
            getElement().addAttribute(DocumentHelper.createQName("type", NSTools.NS_NEUID), type);
        owner = obj;
    }

    public String getHref() {
        return getElement().attributeValue(DocumentHelper.createQName("href", NSTools.NS_NEUID));
    }

    public String getType() {
        return getElement().attributeValue(DocumentHelper.createQName("type", NSTools.NS_NEUID));
    }

    private static final String TAG_NAME = "Target";

    public String getTagName() {
        return TAG_NAME;
    }

    public Namespace getNS() {
        return NSTools.NS_NEUID;
    }

    public void send() throws NeuClearException {
        Sender.quickSend(getHref(), owner);
    }

    private NamedObjectBuilder owner;
}
