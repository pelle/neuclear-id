package org.neuclear.id.senders;

/**
 * (C) 2003 Antilles Software Ventures SA
 * User: pelleb
 * Date: Feb 14, 2003
 * Time: 9:29:29 AM
 * $Id: Sender.java,v 1.2 2004/03/22 20:09:43 pelle Exp $
 * $Log: Sender.java,v $
 * Revision 1.2  2004/03/22 20:09:43  pelle
 * Added simple ledger for unit testing and in memory use
 *
 * Revision 1.1  2004/03/02 18:59:10  pelle
 * Further cleanups in neuclear-id. Moved everything under id.
 *
 * Revision 1.15  2004/02/19 15:30:21  pelle
 * Various cleanups and corrections
 *
 * Revision 1.14  2003/12/16 15:05:00  pelle
 * Added SignedMessage contract for signing simple textual contracts.
 * Added NeuSender, updated SmtpSender and Sender to take plain email addresses (without the mailto:)
 * Added AbstractObjectCreationTest to make it quicker to write unit tests to verify
 * NamedObjectBuilder/SignedNamedObject Pairs.
 * Sample application has been expanded with a basic email application.
 * Updated docs for simple web app.
 * Added missing LGPL LICENSE.txt files to signer and simple app
 *
 * Revision 1.13  2003/12/10 23:58:52  pelle
 * Did some cleaning up in the builders
 * Fixed some stuff in IdentityCreator
 * New maven goal to create executable jarapp
 * We are close to 0.8 final of ID, 0.11 final of XMLSIG and 0.5 of commons.
 * Will release shortly.
 *
 * Revision 1.12  2003/11/21 04:45:13  pelle
 * EncryptedFileStore now works. It uses the PBECipher with DES3 afair.
 * Otherwise You will Finaliate.
 * Anything that can be final has been made final throughout everyting. We've used IDEA's Inspector tool to find all instance of variables that could be final.
 * This should hopefully make everything more stable (and secure).
 *
 * Revision 1.11  2003/11/19 23:33:59  pelle
 * Signers now can generatekeys via the generateKey() method.
 * Refactored the relationship between SignedNamedObject and NamedObjectBuilder a bit.
 * SignedNamedObject now contains the full xml which is returned with getEncoded()
 * This means that it is now possible to further receive on or process a SignedNamedObject, leaving
 * NamedObjectBuilder for its original purposes of purely generating new Contracts.
 * NamedObjectBuilder.sign() now returns a SignedNamedObject which is the prefered way of processing it.
 * Updated all major interfaces that used the old model to use the new model.
 *
 * Revision 1.10  2003/11/11 21:18:43  pelle
 * Further vital reshuffling.
 * org.neudist.crypto.* and org.neudist.utils.* have been moved to respective areas under org.neuclear.commons
 * org.neuclear.signers.* as well as org.neuclear.passphraseagents have been moved under org.neuclear.commons.crypto as well.
 * Did a bit of work on the Canonicalizer and changed a few other minor bits.
 *
 * Revision 1.9  2003/11/10 17:42:36  pelle
 * The AssetController interface has been more or less finalized.
 * CurrencyController fully implemented
 * AssetControlClient implementes a remote client for communicating with AssetControllers
 *
 * Revision 1.8  2003/11/09 03:27:19  pelle
 * More house keeping and shuffling about mainly pay
 *
 * Revision 1.7  2003/11/08 01:40:52  pelle
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
 * Revision 1.6  2003/11/06 23:48:59  pelle
 * Major Refactoring of CurrencyController.
 * Factored out AssetController to be new abstract parent class together with most of its support classes.
 * Created (Half way) AssetControlClient, which can perform transactions on external AssetControllers via NeuClear.
 * Created the first attempt at the ExchangeAgent. This will need use of the AssetControlClient.
 * SOAPTools was changed to return a stream. This is required by the VerifyingReader in NeuClear.
 *
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
import org.neuclear.id.SignedNamedObject;
import org.neuclear.xml.XMLException;

import java.util.HashMap;
import java.util.Map;

public abstract class Sender {

    public abstract SignedNamedObject send(String endpoint, SignedNamedObject obj) throws NeuClearException, XMLException, UnsupportedEndpointException;

    public static SignedNamedObject quickSend(final String endpoint, final SignedNamedObject obj) throws NeuClearException {
        final String protocol = getProtocol(endpoint);
        final Sender sender = getSender(protocol);
        if (sender == null)
            throw new NeuClearException("Unsupported Send Protocol:" + endpoint.toString());
        try {
            return sender.send(endpoint, obj);
        } catch (XMLException e) {
            throw new NeuClearException(e);
        }
    }

    private static String getProtocol(final String endpoint) throws NeuClearException {
        final int protloc = endpoint.indexOf(":");
        final int atloc = endpoint.indexOf("@");
        if (protloc < 0 && atloc < 0)
            throw new NeuClearException(endpoint + "Is not in URL format");
        if (protloc >= 0)
            return endpoint.substring(0, protloc);
        return "mailto";
    }

    public static Sender getSender(final String protocol) {
        makeSenders();

        return (Sender) SENDERS.get(protocol);
    }

    private static HashMap makeSenders() {
        HashMap map = new HashMap();
        map.put("soap", new SoapSender());
        map.put("http", new SoapSender());
        map.put("mailto", new SmtpSender());
        map.put("neu", new NeuSender());
        return map;
    }

    private static final Map SENDERS = makeSenders();

}

