package org.neuclear.id.cache;

import org.neuclear.commons.Cache;
import org.neuclear.id.SignedNamedObject;
import org.neuclear.id.verifier.VerifyingReader;

import java.io.*;

/**
 * The Idea of the NSCache is to have a quick cache of verified public NameSpaces. This is not stored, but is created from scratch
 * everytime the application starts.<p>
 * I have replaced the standard java HashMap implementation with Tyler Close's Cache class from his ADT collections library.
 * This should help with both security and memory usage.
 */
public final class NSCache extends Cache {

    public NSCache() {
        base = BASE_LOG_DIR;
    }

    /**
     * Attempts to get a verified PublicKey for the given name from the cache
     * 
     * @param name Fully Normalised name
     * @return a valid Identity object if found otherwise null
     */
    public SignedNamedObject fetchCached(final String name) {
//        System.out.println("Fetch:" + name);
        final SignedNamedObject object = (SignedNamedObject) lookup(name);
        if (object != null)
            return object;
        File file = getFile(name);
        if (file.exists()) {
            try {
                InputStream is = new BufferedInputStream(new FileInputStream(file));
                return VerifyingReader.getInstance().read(is);
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    public SignedNamedObject cache(final SignedNamedObject obj) {
        File file = getFile(obj.getDigest());
        if (!file.exists()) {
            try {
                OutputStream os = new BufferedOutputStream(new FileOutputStream(file));
                os.write(obj.getEncoded().getBytes());
                os.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return (SignedNamedObject) cache(obj.getDigest(), obj);
    }

    private File getFile(String name) {
        // I do this to not end up with 50 million files in one directory. It creates a series of directories in the base directory
        File file = new File(base + name.substring(0, 1) + File.separatorChar + name.substring(1) + ".xml");
        file.getParentFile().mkdirs();
        return file;
    }

    private final String base;
    private static final String BASE_LOG_DIR = System.getProperty("user.home") + File.separatorChar + ".neuclear" + File.separatorChar + "messagelog" + File.separatorChar;
}
