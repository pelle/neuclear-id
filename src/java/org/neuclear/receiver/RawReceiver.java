package org.neuclear.receiver;

/**
 * Created by IntelliJ IDEA.
 * User: pelleb
 * Date: Oct 10, 2002
 * Time: 11:24:59 PM
 * To change this template use Options | File Templates.
 * $Id: RawReceiver.java,v 1.2 2003/11/06 23:48:59 pelle Exp $
 * $Log: RawReceiver.java,v $
 * Revision 1.2  2003/11/06 23:48:59  pelle
 * Major Refactoring of PaymentProcessor.
 * Factored out AssetController to be new abstract parent class together with most of its support classes.
 * Created (Half way) RemoteAssetController, which can perform transactions on external AssetControllers via NeuClear.
 * Created the first attempt at the ExchangeAgent. This will need use of the RemoteAssetController.
 * SOAPTools was changed to return a stream. This is required by the VerifyingReader in NeuClear.
 *
 * Revision 1.1  2003/10/03 23:48:51  pelle
 * Did various security related updates in the pay package with regards to immutability of fields etc.
 * PaymentReceiver should now be operational. Real testing needs to be done including in particular setting the
 * private key of the Receiver.
 * A new class TransferGlobals contains usefull settings for making life easier in the other contract based classes.
 * TransferContract the signed contract is functional and has a matching TransferRequestBuilder class for programmatically creating
 * TransferRequests for signing.
 * TransferReceiptBuilder has been created for use by Transfer processors. It is used in the PaymentReceiver.
 *
 * Revision 1.5  2003/09/26 23:53:10  pelle
 * Changes mainly in receiver and related fun.
 * First real neuclear stuff in the payment package. Added TransferContract and PaymentReceiver.
 *
 * Revision 1.4  2003/09/24 23:56:48  pelle
 * Refactoring nearly done. New model for creating signed objects.
 * With view for supporting the xmlpull api shortly for performance reasons.
 * Currently still uses dom4j but that has been refactored out that it
 * should now be very quick to implement a xmlpull implementation.
 *
 * A side benefit of this is that the API has been further simplified. I still have some work
 * todo with regards to cleaning up some of the outlying parts of the code.
 *
 * Revision 1.3  2003/09/23 19:16:28  pelle
 * Changed NameSpace to Identity.
 * To cause less confusion in the future.
 *
 * Revision 1.2  2003/09/22 19:24:02  pelle
 * More fixes throughout to problems caused by renaming.
 *
 * Revision 1.1.1.1  2003/09/19 14:41:49  pelle
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
 */

import org.neuclear.id.InvalidIdentityException;
import org.neuclear.id.builders.NamedObjectBuilder;

/**
 * <p>The RawReceiver interface is the base interface for almost all applications based on the NeuDist Framework.
 * You simply implement this to process the different types of NamedObjects.</p>
 * <p>For a fully fledged application SubClass ReceiverServlet to handle an instance of your Receiver.</p>
 */
public interface RawReceiver {
    /**
     * Add your main transaction processing logic within this method.
     * Remember you must check the validity of the SignedNamedObject here. Until you do so
     * you can not trust it.
     * 
     * @param obj 
     * @throws UnsupportedTransaction 
     */
    void receive(NamedObjectBuilder obj) throws UnsupportedTransaction, InvalidIdentityException;
}
