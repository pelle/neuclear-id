/*
 * $Id: Asset.java,v 1.2 2003/09/22 19:24:01 pelle Exp $
 * $Log: Asset.java,v $
 * Revision 1.2  2003/09/22 19:24:01  pelle
 * More fixes throughout to problems caused by renaming.
 *
 * Revision 1.1.1.1  2003/09/19 14:41:40  pelle
 * First import into the neuclear project. This was originally under the SF neudist
 * project. This marks a general major refactoring and renaming ahead.
 *
 * The new name for this code is NeuClear Identity and has the general package header of
 * org.neuclear.id
 * There are other areas within the current code which will be split out into other subprojects later on.
 * In particularly the signers will be completely seperated out as well as the contract types.
 *
 *
 * Revision 1.6  2003/02/14 21:10:32  pelle
 * The email sender works. The LogSender and the SoapSender should work but havent been tested yet.
 * The NamedObject has a new log() method that logs it's contents at it's parent NameSpace's logger.
 * The NameSpace object also has a new method send() which allows one to send a named object to the NameSpace's
 * default receiver.
 *
 * Revision 1.5  2003/02/10 22:30:10  pelle
 * Got rid of even further dependencies. In Particular OSCore
 *
 * Revision 1.4  2003/02/09 00:15:55  pelle
 * Fixed things so they now compile with r_0.7 of XMLSig
 *
 * Revision 1.3  2002/12/17 21:40:56  pelle
 * First part of refactoring of NamedObject and SignedObject Interface/Class parings.
 *
 * Revision 1.2  2002/12/17 20:34:40  pelle
 * Lots of changes to core functionality.
 * First of all I've refactored most of the Resolving and verification code. I have a few more things to do
 * on it before I'm happy.
 * There is now a NSResolver class, which handles all the namespace resolution. I took most of the functionality
 * for this out of NamedObject.
 * Then there is the veriifer, which verifies a given NamedObject using the NSResolver.
 * This has simplified the NamedObject classes drastically, leaving them as mainly data objects, which is what they
 * should be.
 * I have also gone around and tightened up security on many different classes, making clases and/or methods final where appropriate.
 * NSCache now operates using http://www.waterken.com's fantastic ADT collections library.
 * Something important has been added, which is a SignRequest named object. This signed object, embeds an unsigned
 * named object for signing by an end users' signing service.
 * Now were almost ready to start seriously implementing AssetIssuers and Transfers, which will be the most important
 * part of the framework.
 *
 * Revision 1.1  2002/12/04 13:52:47  pelle
 * Biggest change is a fix to the Canonicalization Writer It should be a lot more compliant with C14N now.
 * I think the only thing left to do is to sort the element attributes.
 *
 */
package org.neuclear.contracts.transfer;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.neuclear.id.NamedObject;
import org.neuclear.receiver.Receiver;
import org.neudist.utils.NeudistException;

public class Asset extends NamedObject implements Receiver {

    public Asset(String name, String service, String interactive, String description) {//TODO Add validity fields
        super(name, TAG_NAME, NS_NSASSET);
        Element root = getElement();
        root.addElement(DocumentHelper.createQName("Description", NS_NSASSET)).addText(description);
        root.addAttribute(DocumentHelper.createQName("service", NS_NSASSET), service);
        root.addAttribute(DocumentHelper.createQName("interactive", NS_NSASSET), interactive);


    }

    public Asset(Element elem) throws NeudistException {
        super(elem);
    }

    public String getTagName() {
        return TAG_NAME;
    }

    /**
     * @return the XML NameSpace object
     */
    public Namespace getNS() {
        return NS_NSASSET;
    }

    public String getInteractiveURL() {
        return getElement().attributeValue(DocumentHelper.createQName("interactive", NS_NSASSET));
    }

    public String getServiceURL() {
        return getElement().attributeValue(DocumentHelper.createQName("service", NS_NSASSET));
    }

    public void receive(NamedObject obj) throws NeudistException {
//        SOAPStore.storeDirect(getServiceURL(),obj);
    }

    private static final String TAG_NAME = "Asset";
    public static final String URI_NSASSET = "http://neuclear.org/neu/nsasset";
    public static final Namespace NS_NSASSET = DocumentHelper.createNamespace("nsasset", URI_NSASSET);

}
