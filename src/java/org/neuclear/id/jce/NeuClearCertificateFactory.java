package org.neuclear.id.jce;

import org.neuclear.id.Identity;
import org.neuclear.id.verifier.VerifyingReader;
import org.neudist.utils.NeudistException;

import java.security.cert.*;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;

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

$Id: NeuClearCertificateFactory.java,v 1.1 2003/09/30 23:25:15 pelle Exp $
$Log: NeuClearCertificateFactory.java,v $
Revision 1.1  2003/09/30 23:25:15  pelle
Added new JCE Provider and java Certificate implementation for NeuClear Identity.

*/

/**
 * 
 * User: pelleb
 * Date: Sep 30, 2003
 * Time: 4:39:08 PM
 */
public class NeuClearCertificateFactory extends CertificateFactorySpi {
    public Certificate engineGenerateCertificate(InputStream inputStream) throws CertificateException {
        try {
            Identity id=(Identity) VerifyingReader.getInstance().read(inputStream);
            return new NeuClearCertificate(id);
        } catch (NeudistException e) {
            throw new CertificateException("NeuClear: Problem reading Certificate:"+e.getMessage());
        }
    }

    public Collection engineGenerateCertificates(InputStream inputStream) throws CertificateException {
        List list=new ArrayList(1);
        list.add(engineGenerateCertificate(inputStream));
        return list;
    }

    public CRL engineGenerateCRL(InputStream inputStream) throws CRLException {
        return null;
    }

    public Collection engineGenerateCRLs(InputStream inputStream) throws CRLException {
        return new ArrayList(0);
    }
}
