package org.neuclear.id.jce;

import org.neuclear.id.Identity;
import org.neuclear.id.NSTools;
import org.neuclear.id.resolver.NSResolver;
import org.neuclear.id.verifier.VerifyingReader;
import org.neuclear.commons.NeuClearException;
import org.neuclear.xml.XMLException;

import java.security.cert.*;
import java.io.*;
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

$Id: NeuClearCertificateFactory.java,v 1.6 2003/11/18 15:45:09 pelle Exp $
$Log: NeuClearCertificateFactory.java,v $
Revision 1.6  2003/11/18 15:45:09  pelle
FileStoreTest now passes. FileStore works again.

Revision 1.5  2003/11/18 15:07:36  pelle
Changes to JCE Implementation
Working on getting all tests working including store tests

Revision 1.4  2003/11/11 21:18:42  pelle
Further vital reshuffling.
org.neudist.crypto.* and org.neudist.utils.* have been moved to respective areas under org.neuclear.commons
org.neuclear.signers.* as well as org.neuclear.passphraseagents have been moved under org.neuclear.commons.crypto as well.
Did a bit of work on the Canonicalizer and changed a few other minor bits.

Revision 1.3  2003/10/21 22:31:12  pelle
Renamed NeudistException to NeuClearException and moved it to org.neuclear.commons where it makes more sense.
Unhooked the XMLException in the xmlsig library from NeuClearException to make all of its exceptions an independent hierarchy.
Obviously had to perform many changes throughout the code to support these changes.

Revision 1.2  2003/10/01 17:05:37  pelle
Moved the NeuClearCertificate class to be an inner class of Identity.

Revision 1.1  2003/09/30 23:25:15  pelle
Added new JCE Provider and java Certificate implementation for NeuClear Identity.

*/

/**
 * This is the beginnings of integrating NeuClear into the JCE architecture allowing
 * NeuClear to be plugged in relatively easily for other types of applications such as
 * Code signing.
 * <p>
 * Currently the provider provides a CertificateFactory with the name NeuClear. This
 * can be instantiated using:<br>
 * <tt> CertificateFactory certfact=CertificateFactory.getInstance("NeuClear");</tt><p>
 *
 * User: pelleb
 * Date: Sep 30, 2003
 * Time: 4:39:08 PM
 */
public class NeuClearCertificateFactory extends CertificateFactorySpi {
    final public Certificate engineGenerateCertificate(InputStream inputStream) throws CertificateException {
        try {
            //Identity id=(Identity) VerifyingReader.getInstance().read(inputStream);
            BufferedReader d = new BufferedReader(new InputStreamReader(inputStream));
            if (d.ready()) {
                String name = d.readLine();
                if (name==null)
                    throw new CertificateEncodingException("Certificate is empty");
                if (!NSTools.isValidName(name))
                    throw new CertificateParsingException("Invalid format");
                Identity identity = null;
                identity = NSResolver.resolveIdentity(name);
                if (identity==null)
                    throw new CertificateException("Invalid Certificate");
                return identity.getCertificate();
            }
            return null;
        } catch (NeuClearException e) {
            throw new CertificateException("NeuClear: Problem reading Certificate:"+e.getMessage());
        } catch (IOException e) {
            throw new CertificateException("NeuClear: Problem reading Certificate:"+e.getMessage());
        }
    }

    final public Collection engineGenerateCertificates(InputStream inputStream) throws CertificateException {
        List list=new LinkedList();
        try {
            while(inputStream.available()>0) {
                list.add(engineGenerateCertificate(inputStream));
            }
        } catch (IOException e) {
            throw new CertificateException(e.getLocalizedMessage());
        }
        return list;
    }

    final public CRL engineGenerateCRL(InputStream inputStream) throws CRLException {
        return null;
    }

    final public Collection engineGenerateCRLs(InputStream inputStream) throws CRLException {
        return new ArrayList(0);
    }
}
