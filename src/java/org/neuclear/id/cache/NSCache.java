package org.neuclear.id.cache;

import org.neuclear.commons.Cache;
import org.neuclear.id.SignedNamedObject;

/**
 * The Idea of the NSCache is to have a quick cache of verified public NameSpaces. This is not stored, but is created from scratch
 * everytime the application starts.<p>
 * I have replaced the standard java HashMap implementation with Tyler Close's Cache class from his ADT collections library.
 * This should help with both security and memory usage.
 */
public final class NSCache extends Cache {


    /**
     * Attempts to get a verified PublicKey for the given name from the cache
     * 
     * @param name Fully Normalised name
     * @return a valid Identity object if found otherwise null
     */
    public SignedNamedObject fetchCached(final String name) {
//        System.out.println("Fetch:" + name);
        return (SignedNamedObject) lookup(name);
    }

    public SignedNamedObject cache(final SignedNamedObject obj) {
//        System.out.println("Caching: " + obj.getDigest());
        return (SignedNamedObject) cache(obj.getDigest(), obj);
    }

}
