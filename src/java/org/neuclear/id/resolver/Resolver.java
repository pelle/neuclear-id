package org.neuclear.id.resolver;

import org.neuclear.id.Identity;
import org.neuclear.id.InvalidNamedObjectException;
import org.neuclear.id.NameResolutionException;
import org.neuclear.id.SignedNamedObject;
import org.neuclear.id.cache.NSCache;
import org.neuclear.id.verifier.VerifyingReader;

import java.io.IOException;
import java.net.URL;

/**
 * Secure Identity resolver. To get an Identity object simply do:
 * <code>
 * Identity bob=NSResolver.resolveIdentity("neu://bob@test");
 * </code>
 * To resolve an arbitrary SignedNamedObject use:
 * <code>
 * SignedNamedObject obj=NSResolver.resolve("neu://bob@test/wicked");
 * </code>
 */
public final class Resolver {
    private Resolver() { //Dont Instantiate
    }

    private static final NSCache NSCACHE = new NSCache();

    public static final String PKYP = "http://pkyp.org";

    /**
     * Given a given URI this resolves the Identity object
     * 
     * @param name 
     * @return 
     */
    public final static Identity resolveIdentity(final String name) throws NameResolutionException, InvalidNamedObjectException {
        final SignedNamedObject id = resolve(name);
        if (id instanceof Identity)
            return (Identity) id;
        throw new InvalidNamedObjectException(name);
    }

    /**
     * Retrieves the Identity object of the given name
     * defaultstore for the given namespace.
     * This is guaranteed to be valid as it checks the signatures on each level.
     * 
     * @param name 
     * @return 
     */
    public final static SignedNamedObject resolve(final String name) throws NameResolutionException, InvalidNamedObjectException {

        // If name is a hash check the cache
        if (name.length() == 32 || name.length() == 37 || name.length() == 38 || name.length() == 69) {
            System.out.println("Fetching Cached");
            SignedNamedObject obj = NSCACHE.fetchCached(cleanName(name));
            if (obj != null)
                return obj;
        }

        try {

            return VerifyingReader.getInstance().read(new URL(getURLString(name)).openStream());
        } catch (IOException e) {
            throw new InvalidNamedObjectException(name, e);
        }
    }

    public static SignedNamedObject cache(SignedNamedObject obj) {
        return NSCACHE.cache(obj);
    }

    private static String getURLString(final String name) {
        if ((name.startsWith("http://") || name.startsWith("https://")))
            return name;
        return "http://pkyp.org/id/" + cleanName(name);

    }

    private static String cleanName(String name) {
        if (name.charAt(36) == '!')
            return name.substring(37);
        if (name.startsWith("sha1:"))
            return name.substring(5);
        if (name.startsWith("neu:"))
            return name.substring(4);

        return name;
    }


}
