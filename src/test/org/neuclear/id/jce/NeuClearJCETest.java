package org.neuclear.id.jce;

import junit.framework.TestCase;

import java.security.Security;
import java.security.cert.CertificateFactory;
import java.security.cert.CertificateException;

/*
NeuClear Distributed Transaction Clearing Platform
(C) 2003 Pelle Braendgaard

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 2.1 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library; if not, write to the Free Software
Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

$Id: NeuClearJCETest.java,v 1.1 2003/10/01 17:05:38 pelle Exp $
$Log: NeuClearJCETest.java,v $
Revision 1.1  2003/10/01 17:05:38  pelle
Moved the NeuClearCertificate class to be an inner class of Identity.

*/

/**
 * 
 * User: pelleb
 * Date: Oct 1, 2003
 * Time: 11:50:58 AM
 */
public class NeuClearJCETest extends TestCase {
    public NeuClearJCETest(String string) {
        super(string);
        if (Security.getProvider("NeuClear") == null) {
            Security.addProvider(new NeuClearJCEProvider());
            System.err.println("Added NeuClearJCEProvider");
        }
    }

    public void testProvider() {
        assertNotNull(Security.getProvider("NeuClear"));
    }
    public void testCertificateFactory() throws CertificateException {
        assertNotNull(CertificateFactory.getInstance("NeuClear"));

    }
}
