/*
 * $Id: Identity.java,v 1.6 2003/10/01 19:08:31 pelle Exp $
 * $Log: Identity.java,v $
 * Revision 1.6  2003/10/01 19:08:31  pelle
 * Changed XML Format. Now NameSpace has been modified to Identity also the
 * xml namespace prefix nsdl has been changed to neuid.
 * The standard constants for using these have been moved into NSTools.
 * The NamedObjectBuilder can also now take an Element, such as an unsigned template.
 *
 * Revision 1.5  2003/10/01 17:05:37  pelle
 * Moved the NeuClearCertificate class to be an inner class of Identity.
 *
 * Revision 1.4  2003/09/29 23:17:31  pelle
 * Changes to the senders. Now the senders only work with NamedObjectBuilders
 * which are the only NamedObject representations that contain full XML.
 *
 * Revision 1.3  2003/09/26 00:22:06  pelle
 * Cleanups and final changes to code for refactoring of the Verifier and Reader part.
 *
 * Revision 1.2  2003/09/24 23:56:48  pelle
 * Refactoring nearly done. New model for creating signed objects.
 * With view for supporting the xmlpull api shortly for performance reasons.
 * Currently still uses dom4j but that has been refactored out that it
 * should now be very quick to implement a xmlpull implementation.
 *
 * A side benefit of this is that the API has been further simplified. I still have some work
 * todo with regards to cleaning up some of the outlying parts of the code.
 *
 * Revision 1.1  2003/09/23 19:16:26  pelle
 * Changed NameSpace to Identity.
 * To cause less confusion in the future.
 *
 * Revision 1.2  2003/09/22 19:24:01  pelle
 * More fixes throughout to problems caused by renaming.
 *
 * Revision 1.1.1.1  2003/09/19 14:41:08  pelle
 * First import into the neuclear project. This was originally under the SF neudist
 * project. This marks a general major refactoring and renaming ahead.
 *
 * The new name for this code is NeuClear Identity and has the general package header of
 * org.neuclear.id
 * There are other areas within the current code which will be split out into other subprojects later on.
 * In particularly the signers will be completely seperated out as well as the contract types.
 *
 *
 * Revision 1.11  2003/02/18 14:57:18  pelle
 * Finished Cleaning up Receivers and Stores.
 * Also updated nsdl.xsd xml schema with latest changes.
 * The whole API is now very simple.
 *
 * Revision 1.10  2003/02/18 00:06:15  pelle
 * Moved the SignerStore's into xml-sig
 *
 * Revision 1.9  2003/02/16 00:22:59  pelle
 * LogSender now works and there is a corresponding server side cgi script to do the logging in
 * http://neuclear.org/logger/ Site is not yet up but will be soon.
 *
 * Revision 1.8  2003/02/14 21:10:29  pelle
 * The email sender works. The LogSender and the SoapSender should work but havent been tested yet.
 * The SignedNamedObject has a new log() method that logs it's contents at it's parent Identity's logger.
 * The Identity object also has a new method send() which allows one to send a named object to the Identity's
 * default receiver.
 *
 * Revision 1.7  2003/02/10 22:30:05  pelle
 * Got rid of even further dependencies. In Particular OSCore
 *
 * Revision 1.6  2003/02/09 00:15:52  pelle
 * Fixed things so they now compile with r_0.7 of XMLSig
 *
 * Revision 1.5  2003/01/16 22:20:02  pelle
 * First Draft of new generalised Ledger Interface.
 * Currently we have a Book and Transaction class.
 * We also need a Ledger class and a Ledger Factory.
 *
 * Revision 1.4  2002/12/17 21:40:54  pelle
 * First part of refactoring of SignedNamedObject and SignedObject Interface/Class parings.
 *
 * Revision 1.3  2002/12/17 20:34:39  pelle
 * Lots of changes to core functionality.
 * First of all I've refactored most of the Resolving and verification code. I have a few more things to do
 * on it before I'm happy.
 * There is now a NSResolver class, which handles all the namespace resolution. I took most of the functionality
 * for this out of SignedNamedObject.
 * Then there is the veriifer, which verifies a given SignedNamedObject using the NSResolver.
 * This has simplified the SignedNamedObject classes drastically, leaving them as mainly data objects, which is what they
 * should be.
 * I have also gone around and tightened up security on many different classes, making clases and/or methods final where appropriate.
 * NSCache now operates using http://www.waterken.com's fantastic ADT collections library.
 * Something important has been added, which is a SignRequest named object. This signed object, embeds an unsigned
 * named object for signing by an end users' signing service.
 * Now were almost ready to start seriously implementing AssetIssuers and Transfers, which will be the most important
 * part of the framework.
 *
 * Revision 1.2  2002/10/02 21:03:44  pelle
 * Major Commit
 * I completely redid the namespace resolving code.
 * It now works correctly with the new store attribute of the namespace
 * And can correctly work out the location of a namespace file
 * by hierarchically signing it.
 * I have also included several top level namespaces and finalised
 * the root namespace.
 * In short all of the above means that we can theoretically call
 * Neubia live now. (Well on my first deployment anyway).
 * There is a new CommandLineSigner utility class which creates and signs
 * namespaces using standard java keystores.
 * I'm now working on updating the documentation, so other people
 * than me might have a chance at using it.
 *
 * Revision 1.1.1.1  2002/09/18 10:55:42  pelle
 * First release in new CVS structure.
 * Also first public release.
 * This implemnts simple named objects.
 * - Identity Objects
 * - NSAuth Objects
 *
 * Storage systems
 * - In Memory Storage
 * - Clear text file based storage
 * - Encrypted File Storage (with SHA256 digested filenames)
 * - CachedStorage
 * - SoapStorage
 *
 * Simple SOAP client/server
 * - Simple Single method call SOAP client, for arbitrary dom4j based requests
 * - Simple Abstract SOAP Servlet for implementing http based SOAP Servers
 *
 * Simple XML-Signature Implementation
 * - Based on dom4j
 * - SHA-RSA only
 * - Very simple (likely imperfect) highspeed canonicalizer
 * - Zero support for X509 (We dont like that anyway)
 * - Super Simple
 *
 *
 * Revision 1.5  2002/06/18 03:04:11  pelle
 * Just added all the necessary jars.
 * Fixed a few things in the framework and
 * started a GUI Application to manage Neu's.
 *
 * Revision 1.4  2002/06/17 20:48:33  pelle
 * The NS functionality should now work. FileStore is working properly.
 * The example .id objects in the neuspace folder have been updated with the
 * latest version of the format.
 * "neuspace/root.id" should now be considered the universal parent of the
 * neuclear system.
 * Still more to go, but we're getting there. I will now focus on a quick
 * Web interface. After which Contracts will be added.
 *
 * Revision 1.3  2002/06/13 19:04:07  pelle
 * A start to a web interface into the architecture.
 * We're getting a bit further now with functionality.
 *
 * Revision 1.2  2002/06/05 23:42:04  pelle
 * The Throw clauses of several method definitions were getting out of hand, so I have
 * added a new wrapper exception NeudistException, to keep things clean in the ledger.
 * This is used as a catchall wrapper for all Exceptions in the underlying API's such as IOExceptions,
 * XML Exceptions etc.
 * You can catch any Exception and rethrow it using Utility.rethrowException(e) as a quick way of handling
 * exceptions.
 * Otherwise the Store framework and the NameSpaces are really comming along quite well. I added a CachedStore
 * which wraps around any other Store and caches the access to the store.
 *
 * Revision 1.1.1.1  2002/05/29 10:02:22  pelle
 * Lets try one more time. This is the first rev of the next gen of Neudist
 *
 *
 */
package org.neuclear.id;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.neuclear.id.builders.NamedObjectBuilder;
import org.neuclear.id.resolver.NSResolver;
import org.neuclear.senders.Sender;
import org.neudist.crypto.CryptoTools;
import org.neudist.utils.NeudistException;
import org.neudist.utils.Utility;
import org.neudist.xml.xmlsec.KeyInfo;
import org.neudist.xml.xmlsec.XMLSecTools;
import org.neudist.xml.xmlsec.XMLSecurityException;

import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.sql.Timestamp;

public final class Identity extends SignedNamedObject {
    private static final String NSROOTPKMOD = "AKbv1DrfQCj7fbcc/9U8mLHi9LzFGVw8ac9z26BN1+yeq9VG3wvW+OXjvUpQ9cD+dpwpFXeai9Hz DkFeJcT9Coi9A8Aj4nffWAlxJ/AVOIRCi1d4A/d9InhQ7UYYA5O7XBcwKneopYVa9zRDUoy0ZpVy t9Kj5i0Zw6oZsflAu4S4pIU+niYwwWrYmCuBEq9kecf7nSGiU0rHp1QNs7NYdhXCl2BMcSaz0AZt AF8YLlZYasviJkoxUFBB/Vjqa98xi7V7XIGsMbXWqUvJ8MW2N/CUdBz5aDlpBUwul8rqyq+03A0q 84AFJiUcudqVL7KhURXB8ZYy/hZb+YkEvE3IigU=";
    private static final String NSROOTPKEXP = "AQAB";
    private static PublicKey nsrootpk;


    /**
     * @param name The Name of Identity
     * @param signatory The Signatory that signed this object
     * @param timestamp The TimeStamp of the SignedNamedObject
     * @param repository URL of Default Store for Identity. (Note. A Identity object is stored in the default repository of it's parent namespace)
     * @param signer URL of default interactive signing service for namespace. If null it doesnt allow interactive signing
     * @param receiver URL of default receiver for namespace
     * @throws NeudistException
     */

    Identity(String name, Identity signatory, Timestamp timestamp, String digest, String repository, String signer, String logger, String receiver, PublicKey pub) throws NeudistException {
        super(name, signatory, timestamp, digest);
        this.repository = repository;
        this.logger = logger;
        this.signer = signer;
        this.receiver = receiver;
        this.pub = pub;
    }


    public String getRepository() {
        return repository;
    }

    public String getSigner() {
        return signer;
    }

    public String getLogger() {
        return logger;
    }

    public final void send(NamedObjectBuilder obj) throws NeudistException {
        if (!Utility.isEmpty(receiver))
            Sender.quickSend(receiver, obj);
        else
            throw new NeudistException("Cant send object, " + getName() + " doesnt have a registered Receiver");
    }

    void log(NamedObjectBuilder obj) throws NeudistException {
        if (!Utility.isEmpty(logger))
            Sender.quickSend(logger, obj);
    }

    public String getTagName() {
        return "Identity";
    }

    public PublicKey getPublicKey() {
        return pub;
    }

    public Certificate getCertificate() {
        return new NeuClearCertificate();
    }

    private final String repository;
    private final String signer;
    private final String logger;
    private final String receiver;

    private final PublicKey pub;

    private final static Identity createRootIdentity() {

        try {
            PublicKey rootpk = CryptoTools.createPK(NSROOTPKMOD, NSROOTPKEXP);
            return new Identity("neu://", null, new Timestamp(0), null, NSResolver.NSROOTSTORE,
                    null, null, null, rootpk);
        } catch (NeudistException e) {
            e.printStackTrace();

            return null;
        }

    }

    public static final Identity NEUROOT = createRootIdentity();


    /**
     *  Returns the fixed Root PublicKey
     */
    private final static PublicKey getRootPK() throws XMLSecurityException {
        if (nsrootpk == null)
            nsrootpk = CryptoTools.createPK(NSROOTPKMOD, NSROOTPKEXP);
        return nsrootpk;
    }

    private class NeuClearCertificate extends Certificate {
        public NeuClearCertificate() {
            super("NeuClear");

        }

        /**
         * For efficiency purposes we do not store the source material here but instead
         * return the URI of the certificate which allows us to regenerate it from source.
         * @return
         * @throws CertificateEncodingException
         */
        public byte[] getEncoded() throws CertificateEncodingException {
            return getName().getBytes();
        }

        /**
         * Since the Instance of Identity implies that it has already been verified in the
         * creation process. I just check if the signers key is the same as the given.
         * TODO: This almost certainly has bad security implications and needs to be though through
         * @param publicKey
         * @throws CertificateException
         * @throws NoSuchAlgorithmException
         * @throws InvalidKeyException
         * @throws NoSuchProviderException
         * @throws SignatureException
         */
        public void verify(PublicKey publicKey) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException {
            if (!getSignatory().getPublicKey().equals(publicKey))
                throw new SignatureException("Key didnt match Signature");
        }

        public void verify(PublicKey publicKey, String string) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException {
            verify(publicKey);
        }

        public PublicKey getPublicKey() {
            return pub;
        }

        public String toString() {
            return getName();
        }

    }

    //TODO I dont like this being public
    public final static class Reader implements NamedObjectReader {
        /**
         * Read object from Element and fill in its details
         * @param elem
         * @return
         */
        public SignedNamedObject read(Element elem, String name, Identity signatory, String digest, Timestamp timestamp) throws NeudistException {
            String repository = elem.attributeValue(DocumentHelper.createQName("store", NSTools.NS_NEUID));
            String signer = elem.attributeValue(DocumentHelper.createQName("signer", NSTools.NS_NEUID));
            String logger = elem.attributeValue(DocumentHelper.createQName("logger", NSTools.NS_NEUID));
            String receiver = elem.attributeValue(DocumentHelper.createQName("receiver", NSTools.NS_NEUID));

            Element allowElement = elem.element(DocumentHelper.createQName("allow", NSTools.NS_NEUID));
            KeyInfo ki = new KeyInfo(allowElement.element(XMLSecTools.createQName("KeyInfo")));
            PublicKey pub = ki.getPublicKey();
            return new Identity(name, signatory, timestamp, digest, repository, signer, logger, receiver, pub);
        }

    }


}
