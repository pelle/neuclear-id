package org.neuclear.source;

import org.neuclear.commons.NeuClearException;
import org.neuclear.id.NSTools;
import org.neuclear.id.SignedNamedObject;
import org.neuclear.id.verifier.VerifyingReader;
import org.neuclear.commons.Utility;
import org.neuclear.xml.XMLException;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * (C) 2003 Antilles Software Ventures SA
 * User: pelleb
 * Date: Feb 10, 2003
 * Time: 8:26:04 PM
 * $Id: Source.java,v 1.7 2003/11/21 04:45:14 pelle Exp $
 * $Log: Source.java,v $
 * Revision 1.7  2003/11/21 04:45:14  pelle
 * EncryptedFileStore now works. It uses the PBECipher with DES3 afair.
 * Otherwise You will Finaliate.
 * Anything that can be final has been made final throughout everyting. We've used IDEA's Inspector tool to find all instance of variables that could be final.
 * This should hopefully make everything more stable (and secure).
 *
 * Revision 1.6  2003/11/11 21:18:44  pelle
 * Further vital reshuffling.
 * org.neudist.crypto.* and org.neudist.utils.* have been moved to respective areas under org.neuclear.commons
 * org.neuclear.signers.* as well as org.neuclear.passphraseagents have been moved under org.neuclear.commons.crypto as well.
 * Did a bit of work on the Canonicalizer and changed a few other minor bits.
 *
 * Revision 1.5  2003/11/05 18:50:34  pelle
 * Refactored org.neuclear.signers.source.Source and implementing classes to provide support for a local filesystem cache.
 * Also added Unit tests to make sure it actually works and modified IdentityCreator to write directly to the cache if no output filename is given.
 *
 * Revision 1.4  2003/10/21 22:31:13  pelle
 * Renamed NeudistException to NeuClearException and moved it to org.neuclear.commons where it makes more sense.
 * Unhooked the XMLException in the xmlsig library from NeuClearException to make all of its exceptions an independent hierarchy.
 * Obviously had to perform many changes throughout the code to support these changes.
 * <p/>
 * Revision 1.3  2003/09/24 23:56:49  pelle
 * Refactoring nearly done. New model for creating signed objects.
 * With view for supporting the xmlpull api shortly for performance reasons.
 * Currently still uses dom4j but that has been refactored out that it
 * should now be very quick to implement a xmlpull implementation.
 * <p/>
 * A side benefit of this is that the API has been further simplified. I still have some work
 * todo with regards to cleaning up some of the outlying parts of the code.
 * <p/>
 * Revision 1.2  2003/09/22 19:24:02  pelle
 * More fixes throughout to problems caused by renaming.
 * <p/>
 * Revision 1.1.1.1  2003/09/19 14:41:49  pelle
 * First import into the neuclear project. This was originally under the SF neuclear
 * project. This marks a general major refactoring and renaming ahead.
 * <p/>
 * The new name for this code is NeuClear Identity and has the general package header of
 * org.neuclear.id
 * There are other areas within the current code which will be split out into other subprojects later on.
 * In particularly the signers will be completely seperated out as well as the contract types.
 * <p/>
 * <p/>
 * Revision 1.3  2003/02/18 14:57:31  pelle
 * Finished Cleaning up Receivers and Stores.
 * Also updated nsdl.xsd xml schema with latest changes.
 * The whole API is now very simple.
 * <p/>
 * Revision 1.2  2003/02/14 14:05:01  pelle
 * The New Source Classes work and NS resolution works as well.
 * I've renamed Target to TargetReference to prepare for the other main refactoring ahead. Implementation of
 * Senders.
 * <p/>
 * Revision 1.1  2003/02/14 05:10:13  pelle
 * New Source model is implemented.
 * It doesnt quite verify things correctly yet. I'm not yet sure why.
 * CommandLineSigner is simplified to make it easier to use.
 */
public abstract class Source {
    protected Source() {
        cache = new HashMap(CACHE_SIZE);
    }

    public final SignedNamedObject fetch(final String endpoint, String name) throws NeuClearException {
        try {
            name = NSTools.normalizeNameURI(name);
            SignedNamedObject candidate = (SignedNamedObject) cache.get(name);
            if (candidate != null)
                return candidate;

            candidate = VerifyingReader.getInstance().read(getStream(endpoint, name));
            if (candidate != null)
                storeInCache(candidate);
            return candidate;
        } catch (XMLException e) {
            throw new NeuClearException(e);
        }
    }

    /**
     * This gets the stream of the content from the source
     * 
     * @param endpoint 
     * @param name     
     * @return 
     */
    protected InputStream getStream(final String endpoint, final String name) throws NeuClearException {
        return null;
    }

    private void storeInCache(final SignedNamedObject obj) throws NeuClearException {
        cache.put(obj.getName(), obj);
    }

    private final Map cache;
    public static final int CACHE_SIZE = 10000;

    public static synchronized Source getInstance() {
        if (instance == null) {
            Source mainSource = null;
            String name = System.getProperty("org.neuclear.source");
            if (Utility.isEmpty(name)) {
                name = DEFAULT;
            }
            try {
                mainSource = (Source) Class.forName(name).newInstance();
            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use Options | File Templates.
                try {
                    mainSource = (Source) Class.forName(DEFAULT).newInstance();
                } catch (Exception e1) {
                    e1.printStackTrace();  //To change body of catch statement use Options | File Templates.
                    System.err.println("Real big problems");
                }
            }
            instance = new CachedSource(mainSource);
        }

        return instance;
    }

    private static Source instance;

    private final static String DEFAULT = "org.neuclear.source.HttpSource";
}
