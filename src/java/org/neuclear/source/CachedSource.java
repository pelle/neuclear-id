/*
 *
 * $Id: CachedSource.java,v 1.1 2003/09/19 14:41:44 pelle Exp $
 * $Log: CachedSource.java,v $
 * Revision 1.1  2003/09/19 14:41:44  pelle
 * Initial revision
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
import org.neuclear.id.NamedObject;
import org.neuclear.utils.NeudistException;

import java.util.Map;

/**
 * This is a simple Cached version of the Source.
 * It can be used in front of any other Source, such as a HTTP based source to cache items locally.
 */
public final class CachedSource extends Source {
    public CachedSource(Source src) {
        this.src=src;
        cache=new LRUMap(CACHE_SIZE);
    }


    public NamedObject fetch(String endpoint, String name) throws NeudistException {
        name=NSTools.normalizeNameURI(name);
        NamedObject candidate=(NamedObject)cache.get(name);
        if (candidate!=null)
            return candidate;

        candidate=src.fetch(endpoint, name);
        if (candidate!=null)
            storeInCache(candidate);
        return candidate;

    }

    private void storeInCache(NamedObject obj)  throws NeudistException{
        cache.put(obj.getName(),obj);
    }

    private final Map cache;
    private final Source src;

    public static final int CACHE_SIZE=10000;


}
