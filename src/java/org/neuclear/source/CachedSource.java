/*
 *
 * $Id: CachedSource.java,v 1.7 2003/11/05 23:40:21 pelle Exp $
 * $Log: CachedSource.java,v $
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
 * PaymentReceiver should now be operational. Real testing needs to be done including in particular setting the
 * private key of the Receiver.
 * A new class TransferGlobals contains usefull settings for making life easier in the other contract based classes.
 * TransferContract the signed contract is functional and has a matching TransferRequestBuilder class for programmatically creating
 * TransferRequests for signing.
 * TransferReceiptBuilder has been created for use by Payment processors. It is used in the PaymentReceiver.
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
 * First import into the neuclear project. This was originally under the SF neudist
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

import java.io.*;

/**
 * This is a simple Cached version of the Source.
 * It can be used in front of any other Source, such as a HTTP based source to cache items locally.
 */
public final class CachedSource extends Source {
    public CachedSource(Source src) {
        this.src = src;
        cachedirpath = System.getProperty("user.home") + "/.neuclear/cache";
        File cachedir = new File(cachedirpath);
        if (!cachedir.exists())
            cachedir.mkdirs();
    }


    protected InputStream getStream(String endpoint, String name) throws NeuClearException {
        File object = new File(cachedirpath + NSTools.url2path(name) + "/root.id");
        try {
            if (!object.exists()) {   //TODO check for freshness
                object.getParentFile().mkdirs();
                InputStream in = src.getStream(endpoint, name);
                OutputStream out = new FileOutputStream(object);
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
            throw new NeuClearException(e);
        }

    }

    private final Source src;
    private final String cachedirpath;


}
