package org.neuclear.id.resolver;

import org.neuclear.id.InvalidNameSpaceException;
import org.neuclear.id.NSTools;
import org.neuclear.id.NameSpace;
import org.neuclear.id.cache.NSCache;
import org.neuclear.id.verifier.NSVerifier;
import org.neuclear.source.Source;
import org.neudist.utils.NeudistException;

import java.security.PublicKey;

/**
 * Secure NameSpace resolver
 */
public final class NSResolver {
    private static final NSCache NSCACHE = NSCache.make();

    public static final String NSROOTSTORE = "http://repository.neuclear.org";


    /**
     * Retrieves the NameSpace object of the given name
     * defaultstore for the given namespace.
     *  This is guaranteed to be valid as it checks the signatures on each level.
     * @param name
     * @return
     */
    public final static NameSpace resolveNameSpace(String name) throws NeudistException, InvalidNameSpaceException {
        NameSpace ns = NSCACHE.fetchCached(name);
        if (ns != null)
            return ns;

        String parentname = NSTools.getParentNSURI(name);
        String store = NSROOTSTORE;
        boolean isRootlevel = parentname == null || parentname.equals("neu://");
        NameSpace parent = null;
        if (!isRootlevel) {
            parent = resolveNameSpace(parentname);
            store = parent.getRepository();
        }
        // fetches NameSpace from parent NameSpace's Default Store
        ns = (NameSpace) Source.getInstance().fetch(store, name);
        if (ns == null)
            throw new NeudistException("NameSpace: " + name + " was not resolved");
        PublicKey parentkey = (isRootlevel) ? NSVerifier.getRootPK() : parent.getAllowed();
        if (!ns.verifySignature(parentkey))
            throw new InvalidNameSpaceException("NameSpace: " + name + " not allowed in " + parentname);
        NSCACHE.cache(ns);
        return ns; //This may not be null
    }

}
