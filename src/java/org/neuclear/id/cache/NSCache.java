package org.neuclear.id.cache;

import com.waterken.adt.NoSuchElement;
import com.waterken.adt.cache.Cache;
import org.neuclear.id.NSTools;
import org.neuclear.id.Identity;
import org.neudist.utils.NeudistException;

/**
 * The Idea of the NSCache is to have a quick cache of verified public NameSpaces. This is not stored, but is created from scratch
 * everytime the application starts.<p>
 * I have replaced the standard java HashMap implementation with Tyler Close's Cache class from his ADT collections library.
 * This should help with both security and memory usage.
 */
public final class NSCache {
    private NSCache() {
        spaces = new Cache();
    }

    public static final NSCache make() {
        return new NSCache();
    }

    /**
     * Attempts to get a verified PublicKey for the given name from the cache
     * @param name Fully Normalised name
     * @return a valid Identity object if found otherwise null
     */
    public Identity fetchCached(String name) {
        try { // I dont like the way it forces me to catch this. I need to rewrite it.
            return (Identity) spaces.fetch(name);
        } catch (NoSuchElement noSuchElement) {
            return null;
        }
    }

    public void cache(Identity ns) throws NeudistException {
        // Only store if it's parent is already here
        String parentName = NSTools.getParentNSURI(ns.getName());
        if ((fetchCached(parentName) != null) || (parentName.equals("neu://"))) {
            spaces.put(ns.getName(), ns);
        }
    }

    private final Cache spaces;
}
