package org.neuclear.id.resolver;

import org.neuclear.commons.NeuClearException;
import org.neuclear.id.Identity;
import org.neuclear.id.InvalidNamedObject;
import org.neuclear.id.NSTools;
import org.neuclear.id.SignedNamedObject;
import org.neuclear.id.cache.NSCache;
import org.neuclear.source.Source;

/**
 * Secure Identity resolver
 */
public final class NSResolver {
    private static final NSCache NSCACHE = NSCache.make();

    public static final String NSROOTSTORE = "http://repository.neuclear.org";

    /**
     * Retrieves the Identity object of the given name
     * defaultstore for the given namespace.
     * This is guaranteed to be valid as it checks the signatures on each level.
     * 
     * @param name 
     * @return 
     */
    public final static Identity resolveIdentity(String name) throws NeuClearException, InvalidNamedObject {
        SignedNamedObject id = resolve(name);
        if (id instanceof Identity)
            return (Identity) id;
        throw new InvalidNamedObject(name + " is not a valid Identity");
    }

    /**
     * Retrieves the Identity object of the given name
     * defaultstore for the given namespace.
     * This is guaranteed to be valid as it checks the signatures on each level.
     * 
     * @param name 
     * @return 
     */
    public final static SignedNamedObject resolve(String name) throws NeuClearException, InvalidNamedObject {
        SignedNamedObject obj = NSCACHE.fetchCached(name);
        if (obj != null)
            return obj;

        String parentname = NSTools.getParentNSURI(name);
        String store = NSROOTSTORE;

        if (parentname == null || name.equals("neu://"))
            return Identity.NEUROOT;

        Identity parent = resolveIdentity(parentname);
        store = parent.getRepository();
        // fetches Identity from parent Identity's Default Store
        obj = (Identity) Source.getInstance().fetch(store, name);
        if (obj == null)
            throw new NeuClearException("Identity: " + name + " was not resolved");
        NSCACHE.cache(obj);
        return obj; //This may not be null
    }

}
