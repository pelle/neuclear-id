package org.neuclear.id.resolver;

import junit.framework.TestCase;
import org.neuclear.commons.NeuClearException;

/**
 * Created by IntelliJ IDEA.
 * User: pelleb
 * Date: Dec 8, 2003
 * Time: 11:56:22 AM
 * To change this template use Options | File Templates.
 */
public class ResolverTests extends TestCase {

    public void testResolve() throws NeuClearException {
        assertNotNull(NSResolver.resolveIdentity("neu://test"));
        assertNotNull(NSResolver.resolveIdentity("neu://bob@test"));
        assertNotNull(NSResolver.resolveIdentity("neu://alice@test"));
//        assertNotNull(NSResolver.resolveIdentity("neu://neuclear.org"));
//        assertNotNull(NSResolver.resolveIdentity("neu://pelle@neuclear.org"));
//        assertNotNull(NSResolver.resolveIdentity("neu://veraxpay.com"));
//        assertNotNull(NSResolver.resolveIdentity("neu://pelle@talk.org"));
        //assertNotNull(NSResolver.resolveIdentity("neu://will-k.com"));

    }
}
