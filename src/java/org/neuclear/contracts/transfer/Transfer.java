/*
 * $Id: Transfer.java,v 1.3 2003/09/23 19:16:26 pelle Exp $
 * $Log: Transfer.java,v $
 * Revision 1.3  2003/09/23 19:16:26  pelle
 * Changed NameSpace to Identity.
 * To cause less confusion in the future.
 *
 * Revision 1.2  2003/09/22 19:24:01  pelle
 * More fixes throughout to problems caused by renaming.
 *
 * Revision 1.1.1.1  2003/09/19 14:41:42  pelle
 * First import into the neuclear project. This was originally under the SF neudist
 * project. This marks a general major refactoring and renaming ahead.
 *
 * The new name for this code is NeuClear Identity and has the general package header of
 * org.neuclear.id
 * There are other areas within the current code which will be split out into other subprojects later on.
 * In particularly the signers will be completely seperated out as well as the contract types.
 *
 *
 * Revision 1.7  2003/02/14 21:10:32  pelle
 * The email sender works. The LogSender and the SoapSender should work but havent been tested yet.
 * The NamedObject has a new log() method that logs it's contents at it's parent Identity's logger.
 * The Identity object also has a new method send() which allows one to send a named object to the Identity's
 * default receiver.
 *
 * Revision 1.6  2003/02/10 22:30:10  pelle
 * Got rid of even further dependencies. In Particular OSCore
 *
 * Revision 1.5  2003/02/09 00:15:55  pelle
 * Fixed things so they now compile with r_0.7 of XMLSig
 *
 * Revision 1.4  2002/12/17 21:40:56  pelle
 * First part of refactoring of NamedObject and SignedObject Interface/Class parings.
 *
 * Revision 1.3  2002/12/04 13:52:47  pelle
 * Biggest change is a fix to the Canonicalization Writer It should be a lot more compliant with C14N now.
 * I think the only thing left to do is to sort the element attributes.
 *
 */
package org.neuclear.contracts.transfer;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.neuclear.id.NamedObject;
import org.neuclear.id.NamedObjectFactory;
import org.neudist.crypto.CryptoTools;
import org.neudist.utils.NeudistException;

import java.util.Date;

public class Transfer extends NamedObject {

    public Transfer(String userNameSpace, String recipient, String assetType, double amount) {//TODO Add validity fields
        super(createUniqueTransferName(userNameSpace, recipient), DocumentHelper.createQName(TAG_NAME, NS_NSASSET));
        Element root = getElement();
        root.addElement(DocumentHelper.createQName("Recipient", NS_NSASSET)).addText(recipient);
        root.addElement(DocumentHelper.createQName("AssetType", NS_NSASSET)).addText(assetType);
        root.addElement(DocumentHelper.createQName("Amount", NS_NSASSET)).addText(Double.toString(amount));


    }

    public Transfer(Element elem) throws NeudistException {
        super(elem);
    }

    private static String createUniqueTransferName(String userNameSpace, String reqNameSpace) {
        // Yeah, yeah there are better ways to do this
        String ms = new Long(new Date().getTime()).toString();
        byte ticketsrc[] = new byte[ms.length() + reqNameSpace.length()];
        System.arraycopy(ms.getBytes(), 0, ticketsrc, 0, ms.length());
        System.arraycopy(reqNameSpace.getBytes(), 0, ticketsrc, ms.length(), reqNameSpace.length());
        String ticket = CryptoTools.formatAsURLSafe(CryptoTools.digest256(ticketsrc));
        //Lets reuse ticketsrc for memory reasons
        int offset = ms.length() + 1;
        if (reqNameSpace.startsWith("neu://"))
            offset += 5;


        for (int i = offset; i < ticketsrc.length; i++) {
            if (ticketsrc[i] == (byte) '/')
                ticketsrc[i] = (byte) '.';
        }
/*
        byte ticketName[]=new byte[userNameSpace.length()+33]; // Create new Name byte array to hold userNameSpace a '/' and the generated ticket (size 32)
        System.arraycopy(userNameSpace.getBytes(),0,ticketName,0,userNameSpace.length());
        ticketName[userNameSpace.length()]=(byte)'/';
        System.arraycopy(ticket,0,ticketName,userNameSpace.length()+1,ticket.length);
*/
        return userNameSpace + '/' + new String(ticketsrc, offset, ticketsrc.length - offset) + '.' + ticket;
    }

    public String getTagName() {
        return TAG_NAME;
    }

    /**
     * @return the XML Identity object
     */
    public Namespace getNS() {
        return NS_NSASSET;
    }

    public String getAssetType() {
        return getElement().attributeValue(DocumentHelper.createQName("AssetType", NS_NSASSET));
    }

    /**
     * Perform this Transfer
     * @throws NeudistException
     */
    public void performTransfer() throws NeudistException {
        Asset asset = (Asset) NamedObjectFactory.fetchNamedObject(getAssetType());
        asset.receive(this);
    }

    private static final String TAG_NAME = "TransferRequest";
    public static final String URI_NSASSET = "http://neuclear.org/neu/nsasset";
    public static final Namespace NS_NSASSET = DocumentHelper.createNamespace("nsasset", URI_NSASSET);

}
