/*
 * $Id: Identity.java,v 1.24 2003/12/16 15:05:00 pelle Exp $
 * $Log: Identity.java,v $
 * Revision 1.24  2003/12/16 15:05:00  pelle
 * Added SignedMessage contract for signing simple textual contracts.
 * Added NeuSender, updated SmtpSender and Sender to take plain email addresses (without the mailto:)
 * Added AbstractObjectCreationTest to make it quicker to write unit tests to verify
 * NamedObjectBuilder/SignedNamedObject Pairs.
 * Sample application has been expanded with a basic email application.
 * Updated docs for sample web app.
 * Added missing LGPL LICENSE.txt files to signer and sample app
 *
 * Revision 1.23  2003/12/10 23:58:51  pelle
 * Did some cleaning up in the builders
 * Fixed some stuff in IdentityCreator
 * New maven goal to create executable jarapp
 * We are close to 0.8 final of ID, 0.11 final of XMLSIG and 0.5 of commons.
 * Will release shortly.
 *
 * Revision 1.22  2003/11/21 04:45:13  pelle
 * EncryptedFileStore now works. It uses the PBECipher with DES3 afair.
 * Otherwise You will Finaliate.
 * Anything that can be final has been made final throughout everyting. We've used IDEA's Inspector tool to find all instance of variables that could be final.
 * This should hopefully make everything more stable (and secure).
 *
 * Revision 1.21  2003/11/20 23:42:24  pelle
 * Getting all the tests to work in id
 * Removing usage of BC in CryptoTools as it was causing issues.
 * First version of EntityLedger that will use OFB's EntityEngine. This will allow us to support a vast amount databases without
 * writing SQL. (Yipee)
 *
 * Revision 1.20  2003/11/20 16:01:25  pelle
 * Did a security review of the basic Verification process and needed to make changes.
 * I've introduced the SignedNamedCore which all subclasses of SignedNamedObject need to include in their constructor.
 * What does this mean?
 * It means that all subclasses of SignedNamedObject have a guaranteed "signed final ticket" that can only be created in one place.
 * This also simplifies the constructors as well as the NamedObjectReaders.
 * I've gone through making everything in these contracts that is possible final. Thus further ensuring the security.
 *
 * Revision 1.19  2003/11/19 23:33:59  pelle
 * Signers now can generatekeys via the generateKey() method.
 * Refactored the relationship between SignedNamedObject and NamedObjectBuilder a bit.
 * SignedNamedObject now contains the full xml which is returned with getEncoded()
 * This means that it is now possible to further receive on or process a SignedNamedObject, leaving
 * NamedObjectBuilder for its original purposes of purely generating new Contracts.
 * NamedObjectBuilder.sign() now returns a SignedNamedObject which is the prefered way of processing it.
 * Updated all major interfaces that used the old model to use the new model.
 *
 * Revision 1.18  2003/11/18 15:07:36  pelle
 * Changes to JCE Implementation
 * Working on getting all tests working including store tests
 *
 * Revision 1.17  2003/11/15 01:58:16  pelle
 * More work all around on web applications.
 *
 * Revision 1.16  2003/11/11 21:18:43  pelle
 * Further vital reshuffling.
 * org.neudist.crypto.* and org.neudist.utils.* have been moved to respective areas under org.neuclear.commons
 * org.neuclear.signers.* as well as org.neuclear.passphraseagents have been moved under org.neuclear.commons.crypto as well.
 * Did a bit of work on the Canonicalizer and changed a few other minor bits.
 *
 * Revision 1.15  2003/11/10 21:08:49  pelle
 * More JavaDoc
 *
 * Revision 1.14  2003/11/10 19:28:01  pelle
 * Mainly documentation.
 *
 * Revision 1.13  2003/11/10 17:42:36  pelle
 * The AssetController interface has been more or less finalized.
 * CurrencyController fully implemented
 * AssetControlClient implementes a remote client for communicating with AssetControllers
 *
 * Revision 1.12  2003/11/08 01:40:52  pelle
 * WARNING this rev is majorly unstable and will almost certainly not compile.
 * More major refactoring in neuclear-pay.
 * Got rid of neuclear-ledger like features of pay such as Account and Issuer.
 * Accounts have been replaced by Identity from neuclear-id
 * Issuer is now Asset which is a subclass of Identity
 * AssetController supports more than one Asset. Which is important for most non ecurrency implementations.
 * TransferRequest/Receipt and its Held companions are now SignedNamedObjects. Thus to create them you must use
 * their matching TransferRequest/ReceiptBuilder classes.
 * PaymentProcessor has been renamed CurrencyController. I will extract a superclass later to be named AbstractLedgerController
 * which will handle all neuclear-ledger based AssetControllers.
 *
 * Revision 1.11  2003/11/06 23:48:59  pelle
 * Major Refactoring of CurrencyController.
 * Factored out AssetController to be new abstract parent class together with most of its support classes.
 * Created (Half way) AssetControlClient, which can perform transactions on external AssetControllers via NeuClear.
 * Created the first attempt at the ExchangeAgent. This will need use of the AssetControlClient.
 * SOAPTools was changed to return a stream. This is required by the VerifyingReader in NeuClear.
 *
 * Revision 1.10  2003/10/29 21:16:27  pelle
 * Refactored the whole signing process. Now we have an interface called Signer which is the old SignerStore.
 * To use it you pass a byte array and an alias. The sign method then returns the signature.
 * If a Signer needs a passphrase it uses a PassPhraseAgent to present a dialogue box, read it from a command line etc.
 * This new Signer pattern allows us to use secure signing hardware such as N-Cipher in the future for server applications as well
 * as SmartCards for end user applications.
 *
 * Revision 1.9  2003/10/25 00:39:54  pelle
 * Fixed SmtpSender it now sends the messages.
 * Refactored CommandLineSigner. Now it simply signs files read from command line. However new class IdentityCreator
 * is subclassed and creates new Identities. You can subclass CommandLineSigner to create your own variants.
 * Several problems with configuration. Trying to solve at the moment. Updated PicoContainer to beta-2
 *
 * Revision 1.8  2003/10/21 22:31:12  pelle
 * Renamed NeudistException to NeuClearException and moved it to org.neuclear.commons where it makes more sense.
 * Unhooked the XMLException in the xmlsig library from NeuClearException to make all of its exceptions an independent hierarchy.
 * Obviously had to perform many changes throughout the code to support these changes.
 *
 * Revision 1.7  2003/10/02 23:29:02  pelle
 * Updated Root Key. This will be the root key for the remainder of the beta period. With version 1.0 I will update it with a new key.
 * VerifyingTest works now and also does a pass for fake ones. Will have to think of better ways of making fake Identities to break it.
 * Cleaned up much of the tests and they all pass now.
 * The FileStoreTests need to be rethought out, by adding a test key.
 *
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
 * First import into the neuclear project. This was originally under the SF neuclear
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
 * Moved the Signer's into xml-sig
 *
 * Revision 1.9  2003/02/16 00:22:59  pelle
 * LogSender now works and there is a corresponding server side cgi script to do the logging in
 * http://neuclear.org/logger/ Site is not yet up but will be soon.
 *
 * Revision 1.8  2003/02/14 21:10:29  pelle
 * The email sender works. The LogSender and the SoapSender should work but havent been tested yet.
 * The SignedNamedObject has a new log() method that logs it's contents at it's parent Identity's logger.
 * The Identity object also has a new method receive() which allows one to receive a named object to the Identity's
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
 * added a new wrapper exception NeuClearException, to keep things clean in the ledger.
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
import org.neuclear.commons.NeuClearException;
import org.neuclear.commons.Utility;
import org.neuclear.commons.crypto.CryptoException;
import org.neuclear.commons.crypto.CryptoTools;
import org.neuclear.id.resolver.NSResolver;
import org.neuclear.senders.Sender;
import org.neuclear.xml.xmlsec.KeyInfo;
import org.neuclear.xml.xmlsec.XMLSecTools;
import org.neuclear.xml.xmlsec.XMLSecurityException;

import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * The Identity class is one of the most important concepts in <a href="http://neuclear.org">NeuClear</a>.
 * This is a representation of an online identity. An online identity is defined as a entity online which can
 * perform transactions with other identities. Thus an Identity doesnt have to be a real person or even a legal entitity.
 * <p/>
 * Each Identity is known by its unique name which follows a hierarchical model somewhat similar to DNS.
 * Examples of valid names are: <ul>
 * <li>neu://test/bux
 * <li>neu://bob
 * <li>neu://bob@test
 * </ul>
 * <p/>
 * Each identity has got a PublicKey which identifies contracts signed by it. <p>
 * New Identities are created using the IdentityBuilder class and signed by its parent Signatory.
 * 
 * @see org.neuclear.id.builders.IdentityBuilder
 */
public class Identity extends SignedNamedObject implements Principal {
    private static final String NSROOTPKMOD = "pu/UOt9AKPt9txz/1TyYseL0vMUZXDxpz3PboE3X7J6r1UbfC9b45eO9SlD1wP52nCkVd5qL0fMOQV4lxP0KiL0DwCPid99YCXEn8BU4hEKLV3gD930ieFDtRhgDk7tcFzAqd6ilhVr3NENSjLRmlXK30qPmLRnDqhmx+UC7hLikhT6eJjDBatiYK4ESr2R5x/udIaJTSsenVA2zs1h2FcKXYExxJrPQBm0AXxguVlhqy+ImSjFQUEH9WOpr3zGLtXtcgawxtdapS8nwxbY38JR0HPloOWkFTC6XyurKr7TcDSrzgAUmJRy52pUvsqFRFcHxljL+Flv5iQS8TciKBQ==";
//    private static final String NSROOTPKMOD = "AKbv1DrfQCj7fbcc/9U8mLHi9LzFGVw8ac9z26BN1+yeq9VG3wvW+OXjvUpQ9cD+dpwpFXeai9Hz DkFeJcT9Coi9A8Aj4nffWAlxJ/AVOIRCi1d4A/d9InhQ7UYYA5O7XBcwKneopYVa9zRDUoy0ZpVy t9Kj5i0Zw6oZsflAu4S4pIU+niYwwWrYmCuBEq9kecf7nSGiU0rHp1QNs7NYdhXCl2BMcSaz0AZt AF8YLlZYasviJkoxUFBB/Vjqa98xi7V7XIGsMbXWqUvJ8MW2N/CUdBz5aDlpBUwul8rqyq+03A0q 84AFJiUcudqVL7KhURXB8ZYy/hZb+YkEvE3IigU=";
    private static final String NSROOTPKEXP = "AQAB";
    private static PublicKey nsrootpk;


    /**
     * @param repository URL of Default Store for Identity. (Note. A Identity object is stored in the default repository of it's parent namespace)
     * @param signer     URL of default interactive signing service for namespace. If null it doesnt allow interactive signing
     * @param receiver   URL of default receiver for namespace
     * @throws NeuClearException 
     */

    protected Identity(final SignedNamedCore core, final String repository, final String signer, final String logger, final String receiver, final PublicKey pub) throws NeuClearException {
        super(core);
        this.repository = repository;
        this.logger = logger;
        this.signer = signer;
        this.receiver = receiver;
        this.pub = pub;
    }


    public final String getRepository() {
        return repository;
    }

    public final String getSigner() {
        return signer;
    }

    public final String getLogger() {
        return logger;
    }

    public final String getReceiver() {
        return receiver;
    }

    public final SignedNamedObject receive(final SignedNamedObject obj) throws NeuClearException {
        if (!Utility.isEmpty(receiver))
            return Sender.quickSend(receiver, obj);
        else
            throw new NeuClearException("Cant receive object, " + getName() + " doesnt have a registered Receiver");
    }

    final void log(final SignedNamedObject obj) throws NeuClearException {
        if (!Utility.isEmpty(logger))
            Sender.quickSend(logger, obj);
    }

    public final PublicKey getPublicKey() {
        return pub;
    }

    public final java.security.cert.Certificate getCertificate() {
        return new NeuClearCertificate();
    }

    private final String repository;
    private final String signer;
    private final String logger;
    private final String receiver;

    private final PublicKey pub;

    private final static Identity createRootIdentity() {

        try {
            final PublicKey rootpk = CryptoTools.createPK(NSROOTPKMOD, NSROOTPKEXP);
            return new Identity(SignedNamedCore.createRootCore(), NSResolver.NSROOTSTORE,
                    null, null, null, rootpk);
        } catch (NeuClearException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static final Identity NEUROOT = createRootIdentity();

    public final java.security.cert.Certificate[] getCertificateChain() {
        final ArrayList certs = new ArrayList(3);
        Identity id = this;
        while (id != null) {
            certs.add(id.getCertificate());
            id = id.getSignatory();
        }
        certs.add(NEUROOT.getCertificate());
        certs.trimToSize();
        final Certificate[] cert = new Certificate[certs.size()];
        final Iterator iter = certs.iterator();
        int i = 0;
        while (iter.hasNext()) {
            final Certificate certificate = (java.security.cert.Certificate) iter.next();
            cert[i++] = certificate;
        }
        return cert;
    }

    /**
     * Returns the fixed Root PublicKey
     */
    final static PublicKey getRootPK() throws CryptoException {
        if (nsrootpk == null)
            nsrootpk = CryptoTools.createPK(NSROOTPKMOD, NSROOTPKEXP);
        return nsrootpk;
    }

    private final class NeuClearCertificate extends Certificate {
        public NeuClearCertificate() {
            super("NeuClear");

        }

        /**
         * For efficiency purposes we do not store the source material here but instead
         * return the URI of the certificate which allows us to regenerate it from source.
         * 
         * @return 
         * @throws CertificateEncodingException 
         */
        public final byte[] getEncoded() throws CertificateEncodingException {
            return getEncoded();
        }

        /**
         * Since the Instance of Identity implies that it has already been verified in the
         * creation process. I just check if the signers key is the same as the given.
         * TODO: This almost certainly has bad security implications and needs to be though through
         * 
         * @param publicKey 
         * @throws CertificateException 
         * @throws java.security.NoSuchAlgorithmException
         *                              
         * @throws java.security.InvalidKeyException
         *                              
         * @throws java.security.NoSuchProviderException
         *                              
         * @throws java.security.SignatureException
         *                              
         */
        public final void verify(final PublicKey publicKey) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException {
            if (!getSignatory().getPublicKey().equals(publicKey))
                throw new SignatureException("Key didnt match Signature");
        }

        public final void verify(final PublicKey publicKey, final String string) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException {
            verify(publicKey);
        }

        public final PublicKey getPublicKey() {
            return pub;
        }

        public final String toString() {
            return getName();
        }

    }

    //TODO I dont like this being public
    public static final class Reader implements NamedObjectReader {
        /**
         * Read object from Element and fill in its details
         * 
         * @param elem 
         * @return 
         */
        public final SignedNamedObject read(final SignedNamedCore core, final Element elem) throws NeuClearException, XMLSecurityException {
            final String repository = elem.attributeValue(DocumentHelper.createQName("repository", NSTools.NS_NEUID));
            final String signer = elem.attributeValue(DocumentHelper.createQName("signer", NSTools.NS_NEUID));
            final String logger = elem.attributeValue(DocumentHelper.createQName("logger", NSTools.NS_NEUID));
            final String receiver = elem.attributeValue(DocumentHelper.createQName("receiver", NSTools.NS_NEUID));

            final Element allowElement = elem.element(DocumentHelper.createQName("allow", NSTools.NS_NEUID));
            final KeyInfo ki = new KeyInfo(allowElement.element(XMLSecTools.createQName("KeyInfo")));
            final PublicKey pub = ki.getPublicKey();
            return new Identity(core, repository, signer, logger, receiver, pub);
        }

    }


}
