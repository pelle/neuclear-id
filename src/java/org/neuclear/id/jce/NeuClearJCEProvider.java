package org.neuclear.id.jce;

import java.security.Provider;

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

$Id: NeuClearJCEProvider.java,v 1.2 2003/10/01 17:05:37 pelle Exp $
$Log: NeuClearJCEProvider.java,v $
Revision 1.2  2003/10/01 17:05:37  pelle
Moved the NeuClearCertificate class to be an inner class of Identity.

Revision 1.1  2003/09/30 23:25:15  pelle
Added new JCE Provider and java Certificate implementation for NeuClear Identity.

*/

/**
 *   This is the beginnings of integrating NeuClear into the JCE architecture allowing
 * NeuClear to be plugged in relatively easily for other types of applications such as
 * Code signing.
 * <p>
 * Currently the provider provides a CertificateFactory with the name NeuClear. This
 * can be instantiated using:<br>
 * <tt> CertificateFactory certfact=CertificateFactory.getInstance("NeuClear");</tt><p>
 *
 * User: pelleb
 * Date: Sep 30, 2003
 * Time: 4:32:08 PM
 */
public final class NeuClearJCEProvider extends Provider {
    public NeuClearJCEProvider() {
        super("NeuClear", 0.7,"NeuClear Provider Implementing NeuClear Digital Certificates");
        put("CertificateFactory.NeuClear","org.neuclear.id.jce.NeuClearCertificateFactory");
    }

}
