package org.neuclear.id.cache;

import org.neuclear.commons.NeuClearException;
import org.neuclear.id.Identity;
import org.neuclear.id.NSTools;
import org.neuclear.id.SignedNamedObject;
import org.neuclear.id.InvalidNamedObjectException;

import java.util.Map;
import java.util.HashMap;
import java.lang.ref.WeakReference;

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
        if (ref==null)
            return null;
        return (SignedNamedObject) ref.get();
    }

    public void cache(final SignedNamedObject ns)  {
        // Only store if it's parent is already here
        try {
            final String parentName = NSTools.getSignatoryURI(ns.getName());
            if ((fetchCached(parentName) != null) || (parentName.equals("neu://")||NSTools.isHttpScheme(ns.getName())!=null)) {
                spaces.put(ns.getName(),new WeakReference(ns));
            }
        } catch (InvalidNamedObjectException e) {
            ;// If we have an issue here we will silently ignore it.
        }
    }

    private final Map spaces;
}
