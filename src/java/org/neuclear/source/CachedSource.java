/*
 *
 * $Id: CachedSource.java,v 1.17 2003/12/19 18:03:35 pelle Exp $
 * $Log: CachedSource.java,v $
 * Revision 1.17  2003/12/19 18:03:35  pelle
 * Revamped a lot of exception handling throughout the framework, it has been simplified in most places:
 * - For most cases the main exception to worry about now is InvalidNamedObjectException.
 * - Most lowerlevel exception that cant be handled meaningful are now wrapped in the LowLevelException, a
 *   runtime exception.
 * - Source and Store patterns each now have their own exceptions that generalizes the various physical
 *   exceptions that can happen in that area.
 *
 * Revision 1.16  2003/12/09 23:41:44  pelle
 * IdentityCreator is now the default class of the uber jar.
 * It has many new features such as:
 * - Self signed certificates
 * - Unsigned Certificates (for external signing)
 * - Signing of Externally generated Certificates
 * - Command Line verification of an Identity name
 *
 * CachedSource now supports freshness. It needs to be tested a bit more thoroughly
 * though.
 *
 * Documentation including the bdg has been updated to reflect these changes.
 *
 * Revision 1.15  2003/12/06 00:17:03  pelle
 * Updated various areas in NSTools.
 * Updated URI Validation in particular to support new expanded format
 * Updated createUniqueID and friends to be a lot more unique and more efficient.
 * In CryptoTools updated getRandom() to finally use a SecureRandom.
 * Changed CryptoTools.getFormatURLSafe to getBase36 because that is what it really is.
 *
 * Revision 1.14  2003/12/04 21:50:36  pelle
 * Mainly documentation changes.
 *
 * Revision 1.13  2003/11/21 04:45:14  pelle
 * EncryptedFileStore now works. It uses the PBECipher with DES3 afair.
 * Otherwise You will Finaliate.
 * Anything that can be final has been made final throughout everyting. We've used IDEA's Inspector tool to find all instance of variables that could be final.
 * This should hopefully make everything more stable (and secure).
 *
 * Revision 1.12  2003/11/11 21:18:44  pelle
 * Further vital reshuffling.
 * org.neudist.crypto.* and org.neudist.utils.* have been moved to respective areas under org.neuclear.commons
 * org.neuclear.signers.* as well as org.neuclear.passphraseagents have been moved under org.neuclear.commons.crypto as well.
 * Did a bit of work on the Canonicalizer and changed a few other minor bits.
 *
 * Revision 1.11  2003/11/10 17:42:36  pelle
 * The AssetController interface has been more or less finalized.
 * CurrencyController fully implemented
 * AssetControlClient implementes a remote client for communicating with AssetControllers
 *
 * Revision 1.10  2003/11/09 03:27:19  pelle
 * More house keeping and shuffling about mainly pay
 *
 * Revision 1.9  2003/11/08 01:40:53  pelle
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
 * Revision 1.8  2003/11/06 23:49:00  pelle
 * Major Refactoring of CurrencyController.
 * Factored out AssetController to be new abstract parent class together with most of its support classes.
 * Created (Half way) AssetControlClient, which can perform transactions on external AssetControllers via NeuClear.
 * Created the first attempt at the ExchangeAgent. This will need use of the AssetControlClient.
 * SOAPTools was changed to return a stream. This is required by the VerifyingReader in NeuClear.
 *
 * Revision 1.7  2003/11/05 23:40:21  pelle
 * A few minor fixes to make all the unit tests work
 * Also the start of getting SigningServlet and friends back working.
 *
 * Revision 1.6  2003/11/05 18:50:34  pelle
 * Refactored org.neuclear.signers.source.Source and implementing classes to provide support for a local filesystem cache.
 * Also added Unit tests to make sure it actually works and modified IdentityCreator to write directly to the cache if no output filename is given.
 *
 * Revision 1.5  2003/10/21 22:31:13  pelle
 * Renamed NeudistException to NeuClearException and moved it to org.neuclear.commons where it makes more sense.
 * Unhooked the XMLException in the xmlsig library from NeuClearException to make all of its exceptions an independent hierarchy.
 * Obviously had to perform many changes throughout the code to support these changes.
 *
 * Revision 1.4  2003/10/03 23:48:51  pelle
 * Did various security related updates in the pay package with regards to immutability of fields etc.
 * AssetControllerReceiver should now be operational. Real testing needs to be done including in particular setting the
 * private key of the Receiver.
 * A new class TransferGlobals contains usefull settings for making life easier in the other contract based classes.
 * TransferContract the signed contract is functional and has a matching TransferRequestBuilder class for programmatically creating
 * TransferRequests for signing.
 * TransferReceiptBuilder has been created for use by Transfer processors. It is used in the AssetControllerReceiver.
 *
 * Revision 1.3  2003/09/24 23:56:49  pelle
 * Refactoring nearly done. New model for creating signed objects.
 * With view for supporting the xmlpull api shortly for performance reasons.
 * Currently still uses dom4j but that has been refactored out that it
 * should now be very quick to implement a xmlpull implementation.
 *
 * A side benefit of this is that the API has been further simplified. I still have some work
 * todo with regards to cleaning up some of the outlying parts of the code.
 *
 * Revision 1.2  2003/09/22 19:24:02  pelle
 * More fixes throughout to problems caused by renaming.
 *
 * Revision 1.1.1.1  2003/09/19 14:41:44  pelle
 * First import into the neuclear project. This was originally under the SF neuclear
 * project. This marks a general major refactoring and renaming ahead.
 *
 * The new name for this code is NeuClear Identity and has the general package header of
 * org.neuclear.id
 * There are other areas within the current code which will be split out into other subprojects later on.
 * In particularly the signers will be completely seperated out as well as the contract types.
 *
 *
 * Revision 1.2  2003/02/18 14:57:30  pelle
 * Finished Cleaning up Receivers and Stores.
 * Also updated nsdl.xsd xml schema with latest changes.
 * The whole API is now very simple.
 *
 * Revision 1.1  2003/02/14 14:05:01  pelle
 * The New Source Classes work and NS resolution works as well.
 * I've renamed Target to TargetReference to prepare for the other main refactoring ahead. Implementation of
 * Senders.
 *
 */
package org.neuclear.source;

import org.neuclear.commons.NeuClearException;
import org.neuclear.id.NSTools;
import org.neuclear.id.InvalidNamedObjectException;

import java.io.*;

/**
 * This is a simple Cached version of the Source.
 * It can be used in front of any other Source, such as a HTTP based source to cache items locally.
 * <p/>
 * The items are stored in a cache folder in the users home directory called:
 * <p/>
 * <code>~/.neuclear/cache</code>
 * <p>This can safely be removed at all times.
 */
public final class CachedSource extends Source {
    public CachedSource(final Source src) {
        this.src = src;
        cachedirpath = System.getProperty("user.home") + "/.neuclear/cache";
        final File cachedir = new File(cachedirpath);
        if (!cachedir.exists())
            cachedir.mkdirs();
    }


    protected InputStream getStream(final String endpoint, final String name) throws SourceException, InvalidNamedObjectException {
        final File object = new File(cachedirpath + NSTools.name2path(name) + "/root.id");
        try {
            if (!object.exists()||((object.lastModified()+MS_STALE)<System.currentTimeMillis())) {
                object.getParentFile().mkdirs();
                if (object.exists())
                    object.delete();
                final InputStream in = src.getStream(endpoint, name);
                final OutputStream out = new FileOutputStream(object);
                int character;
                //TODO Explore more efficient ways of copying streams
                while ((character = in.read()) != -1)
                    out.write(character);
                in.close();
                out.close();
            }
            return new FileInputStream(object);
        } catch (IOException e) {
            // I have already checked for this but will wrap it anyhow.
            throw new SourceException(this,e);
        }

    }

    private final Source src;
    private final String cachedirpath;
    private final static long MS_STALE=8640000; // Milliseconds until a cache entry is stale

}
