package org.neuclear.id.cache;

import org.neuclear.id.SignedNamedObject;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

/**
 * The Idea of the NSCache is to have a quick cache of verified public NameSpaces. This is not stored, but is created from scratch
 * everytime the application starts.<p>
 * I have replaced the standard java HashMap implementation with Tyler Close's Cache class from his ADT collections library.
 * This should help with both security and memory usage.
 */
public final class NSCache {
    public NSCache() {
        spaces = new HashMap();
    }


    /**
     * Attempts to get a verified PublicKey for the given name from the cache
     * 
     * @param name Fully Normalised name
     * @return a valid Identity object if found otherwise null
     */
    public SignedNamedObject fetchCached(final String name) {
        final WeakReference ref = (WeakReference) spaces.get(name);
        if (ref == null)
            return null;
        return (SignedNamedObject) ref.get();
    }

    public SignedNamedObject cache(final SignedNamedObject obj) {
        if (!spaces.containsKey(obj.getDigest()))
            spaces.put(obj.getDigest(), new WeakReference(obj));
        return obj;
    }

    private final Map spaces;
}
