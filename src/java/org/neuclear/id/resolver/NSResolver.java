package org.neuclear.id.resolver;

import org.neuclear.id.InvalidIdentityException;
import org.neuclear.id.NSTools;
import org.neuclear.id.Identity;
import org.neuclear.id.cache.NSCache;
import org.neuclear.id.verifier.NSVerifier;
import org.neuclear.source.Source;
import org.neudist.utils.NeudistException;

import java.security.PublicKey;

/**
 * Secure Identity resolver
 */
public final class NSResolver {
    private static final NSCache NSCACHE = NSCache.make();

    public static final String NSROOTSTORE = "http://repository.neuclear.org";


    /**
     * Retrieves the Identity object of the given name
     * defaultstore for the given namespace.
     *  This is guaranteed to be valid as it checks the signatures on each level.
     * @param name
     * @return
     */
    public final static Identity resolveIdentity(String name) throws NeudistException, InvalidIdentityException {
        Identity ns = NSCACHE.fetchCached(name);
        if (ns != null)
            return ns;

        String parentname = NSTools.getParentNSURI(name);
        String store = NSROOTSTORE;
        if ( parentname == null || parentname.equals("neu://"))
            return Identity.getRootIdentity();
        Identity parent = resolveIdentity(parentname);
        store = parent.getRepository();
        // fetches Identity from parent Identity's Default Store
        ns = (Identity) Source.getInstance().fetch(store, name);
        if (ns == null)
            throw new NeudistException("Identity: " + name + " was not resolved");
        NSCACHE.cache(ns);
        return ns; //This may not be null
    }

}
