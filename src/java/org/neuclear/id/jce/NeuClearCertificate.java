package org.neuclear.id.jce;

import org.neuclear.id.Identity;

import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.*;


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

$Id: NeuClearCertificate.java,v 1.1 2003/09/30 23:25:15 pelle Exp $
$Log: NeuClearCertificate.java,v $
Revision 1.1  2003/09/30 23:25:15  pelle
Added new JCE Provider and java Certificate implementation for NeuClear Identity.

*/

/**
 * 
 * User: pelleb
 * Date: Sep 30, 2003
 * Time: 4:19:29 PM
 */
public class NeuClearCertificate extends Certificate {
    public NeuClearCertificate(Identity id) {
        super(NEUCLEAR);
        this.id=id;
    }

    public byte[] getEncoded() throws CertificateEncodingException {
        return new byte[0];
    }

    public void verify(PublicKey publicKey) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException {

    }

    public void verify(PublicKey publicKey, String string) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException {

    }

    /**
     * Always returns the first of the authorized keys
     * @return
     */
    public PublicKey getPublicKey() {
        return id.getPublicKeys()[0];
    }
    public String toString() {
        return "";
    }
    private final Identity id;

    private final static String NEUCLEAR="NeuClear";

}
