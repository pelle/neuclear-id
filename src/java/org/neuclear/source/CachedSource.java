/*
 *
 * $Id: CachedSource.java,v 1.3 2003/09/24 23:56:49 pelle Exp $
 * $Log: CachedSource.java,v $
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

import org.apache.commons.collections.LRUMap;
import org.neuclear.id.NSTools;
import org.neuclear.id.SignedNamedObject;
import org.neudist.utils.NeudistException;

import java.util.Map;

/**
 * This is a simple Cached version of the Source.
 * It can be used in front of any other Source, such as a HTTP based source to cache items locally.
 */
public final class CachedSource extends Source {
    public CachedSource(Source src) {
        this.src = src;
        cache = new LRUMap(CACHE_SIZE);
    }


    public SignedNamedObject fetch(String endpoint, String name) throws NeudistException {
        name = NSTools.normalizeNameURI(name);
        SignedNamedObject candidate = (SignedNamedObject) cache.get(name);
        if (candidate != null)
            return candidate;

        candidate = src.fetch(endpoint, name);
        if (candidate != null)
            storeInCache(candidate);
        return candidate;

    }

    private void storeInCache(SignedNamedObject obj) throws NeudistException {
        cache.put(obj.getName(), obj);
    }

    private final Map cache;
    private final Source src;

    public static final int CACHE_SIZE = 10000;


}
