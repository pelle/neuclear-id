package org.neuclear.id.receiver;

/**
 * Created by IntelliJ IDEA.
 * User: pelleb
 * Date: Oct 10, 2002
 * Time: 11:24:59 PM
 * To change this template use Options | File Templates.
 * $Id: Receiver.java,v 1.1 2004/03/03 23:26:43 pelle Exp $
 * $Log: Receiver.java,v $
 * Revision 1.1  2004/03/03 23:26:43  pelle
 * Updated various tests to use the AbstractObjectCreationTest
 *
 * Revision 1.14  2004/01/13 15:11:35  pelle
 * Now builds.
 * Now need to do unit tests
 *
 * Revision 1.13  2003/12/10 23:58:51  pelle
 * Did some cleaning up in the builders
 * Fixed some stuff in IdentityCreator
 * New maven goal to create executable jarapp
 * We are close to 0.8 final of ID, 0.11 final of XMLSIG and 0.5 of commons.
 * Will release shortly.
 *
 * Revision 1.12  2003/11/19 23:33:59  pelle
 * Signers now can generatekeys via the generateKey() method.
 * Refactored the relationship between SignedNamedObject and NamedObjectBuilder a bit.
 * SignedNamedObject now contains the full xml which is returned with getEncoded()
 * This means that it is now possible to further receive on or process a SignedNamedObject, leaving
 * NamedObjectBuilder for its original purposes of purely generating new Contracts.
 * NamedObjectBuilder.sign() now returns a SignedNamedObject which is the prefered way of processing it.
 * Updated all major interfaces that used the old model to use the new model.
 *
 * Revision 1.11  2003/11/11 21:18:43  pelle
 * Further vital reshuffling.
 * org.neudist.crypto.* and org.neudist.utils.* have been moved to respective areas under org.neuclear.commons
 * org.neuclear.signers.* as well as org.neuclear.passphraseagents have been moved under org.neuclear.commons.crypto as well.
 * Did a bit of work on the Canonicalizer and changed a few other minor bits.
 *
 * Revision 1.10  2003/11/10 17:42:36  pelle
 * The AssetController interface has been more or less finalized.
 * CurrencyController fully implemented
 * AssetControlClient implementes a remote client for communicating with AssetControllers
 *
 * Revision 1.9  2003/11/09 03:27:19  pelle
 * More house keeping and shuffling about mainly pay
 *
 * Revision 1.8  2003/11/08 01:40:52  pelle
 * WARNING this rev is majorly unstable and will almost certainly not compile.
 * More major refactoring in neuclear-pay.
 * Got rid of neuclear-ledger like features of pay such as Account and Issuer.
 * Accounts have been replaced by Identity from neuclear-id
 * Issuer is now Asset which is a subclass of Identity
 * AssetController supports more than one Asset. Which is important for most non ecurrency implementations.
 * TransferRequest/Receipt and its Held companions are now SignedNamedObjects. Thus to create them you must use
 * their matching TransferRequest/ReceiptBuilder classes.
 * PaymentProcessor has been renamed CurrencyController. I will extract a superclass later to be named AbstractLedgerController
 * which will handle all neuclear-ledger based AssetControllers.
 *
 * Revision 1.7  2003/11/06 23:48:59  pelle
 * Major Refactoring of CurrencyController.
 * Factored out AssetController to be new abstract parent class together with most of its support classes.
 * Created (Half way) AssetControlClient, which can perform transactions on external AssetControllers via NeuClear.
 * Created the first attempt at the ExchangeAgent. This will need use of the AssetControlClient.
 * SOAPTools was changed to return a stream. This is required by the VerifyingReader in NeuClear.
 *
 * Revision 1.6  2003/10/03 23:48:51  pelle
 * Did various security related updates in the pay package with regards to immutability of fields etc.
 * AssetControllerReceiver should now be operational. Real testing needs to be done including in particular setting the
 * private key of the Receiver.
 * A new class TransferGlobals contains usefull settings for making life easier in the other contract based classes.
 * TransferContract the signed contract is functional and has a matching TransferRequestBuilder class for programmatically creating
 * TransferRequests for signing.
 * TransferReceiptBuilder has been created for use by Transfer processors. It is used in the AssetControllerReceiver.
 *
 * Revision 1.5  2003/09/26 23:53:10  pelle
 * Changes mainly in receiver and related fun.
 * First real neuclear stuff in the payment package. Added TransferContract and AssetControllerReceiver.
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
 * First import into the neuclear project. This was originally under the SF neuclear
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

import org.neuclear.commons.NeuClearException;
import org.neuclear.id.SignedNamedObject;

/**
 * <p>The Receiver interface is the base interface for almost all applications based on the NeuDist Framework.
 * You simply implement this to process the different types of NamedObjects.</p>
 * <p>For a fully fledged application SubClass ReceiverServlet to handle an instance of your Receiver.</p>
 */
public interface Receiver {
    /**
     * Add your main transaction processing logic within this method.
     * Remember you must check the validity of the SignedNamedObject here. Until you do so
     * you can not trust it.
     *
     * @param obj
     * @throws UnsupportedTransaction
     */
    SignedNamedObject receive(SignedNamedObject obj) throws UnsupportedTransaction, NeuClearException;
}
