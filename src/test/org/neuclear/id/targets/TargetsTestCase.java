package org.neuclear.id.targets;

import junit.framework.TestCase;
import org.dom4j.Document;
import org.neuclear.xml.XMLException;
import org.neuclear.xml.XMLTools;

import java.io.File;

/*
 *  The NeuClear Project and it's libraries are
 *  (c) 2002-2004 Antilles Software Ventures SA
 *  For more information see: http://neuclear.org
 *
 *  This library is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public
 *  License as published by the Free Software Foundation; either
 *  version 2.1 of the License, or (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *  Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public
 *  License along with this library; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

/**
 * User: pelleb
 * Date: Apr 17, 2004
 * Time: 11:14:41 AM
 */
public class TargetsTestCase extends TestCase {
    public TargetsTestCase(String name) {
        super(name);
    }

    public void testReadTargetList() throws XMLException {
        Targets targets = parseTargets("src/testdata/targettests/targetlist.html", 6);
        assertEquals(2, targets.getTargets()[0].getType());
        assertEquals("http://portfolio.neuclear.org", targets.getTargets()[0].getHref());
        assertEquals(2, targets.getTargets()[1].getType());
        assertEquals("mailto:pelle@neuclear.org", targets.getTargets()[1].getHref());
        assertEquals(3, targets.getTargets()[2].getType());
        assertEquals("http://logger.neuclear.org", targets.getTargets()[2].getHref());
        assertEquals(1, targets.getTargets()[3].getType());
        assertEquals("http://auditor.neuclear.org", targets.getTargets()[3].getHref());
        assertEquals(0, targets.getTargets()[4].getType());
        assertEquals("http://misc.neuclear.org", targets.getTargets()[4].getHref());
        assertEquals(4, targets.getTargets()[5].getType());
        assertEquals("http://controller.neuclear.org", targets.getTargets()[5].getHref());
    }

    public void testReadEmptyTargetList() throws XMLException {
        Targets targets = parseTargets("src/testdata/targettests/notargets.html", 0);
    }

    public void testEmptyTargetListWithUnknownTargetTypes() throws XMLException {
        Targets targets = parseTargets("src/testdata/targettests/unknowntargets.html", 8);
        Target tg[] = targets.getTargets();
        for (int i = 0; i < tg.length; i++) {
            Target target = tg[i];
            assertEquals(0, target.getType());
            assertEquals("http://portfolio.neuclear.org", target.getHref());
        }
    }

    private static Targets parseTargets(final String file, final int count) throws XMLException {
        Document doc = XMLTools.loadDocument(new File(file));
        assertNotNull(doc);
        Targets targets = Targets.parseList(doc.getRootElement());
        assertNotNull(targets);
        assertEquals(count, targets.getSize());
        return targets;
    }

}
