/*
 * $Id: NameSpace.java,v 1.2 2003/09/22 19:24:01 pelle Exp $
 * $Log: NameSpace.java,v $
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
 * The NamedObject has a new log() method that logs it's contents at it's parent NameSpace's logger.
 * The NameSpace object also has a new method send() which allows one to send a named object to the NameSpace's
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
 * First part of refactoring of NamedObject and SignedObject Interface/Class parings.
 *
 * Revision 1.3  2002/12/17 20:34:39  pelle
 * Lots of changes to core functionality.
 * First of all I've refactored most of the Resolving and verification code. I have a few more things to do
 * on it before I'm happy.
 * There is now a NSResolver class, which handles all the namespace resolution. I took most of the functionality
 * for this out of NamedObject.
 * Then there is the veriifer, which verifies a given NamedObject using the NSResolver.
 * This has simplified the NamedObject classes drastically, leaving them as mainly data objects, which is what they
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
 * - NameSpace Objects
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
import org.dom4j.QName;
import org.neuclear.id.resolver.NSResolver;
import org.neuclear.senders.LogSender;
import org.neuclear.senders.Sender;
import org.neudist.utils.NeudistException;
import org.neudist.utils.Utility;
import org.neudist.xml.xmlsec.KeyInfo;
import org.neudist.xml.xmlsec.XMLSecTools;

import java.security.PublicKey;
import java.util.Iterator;
import java.util.List;

public final class NameSpace extends NamedObject {


    /**
     * This constructor should be used by subclasses of NameSpace. It creates a Standard NameSpace document, but doesn't sign it.
     * The signing should be done as the last step of the constructor of the subclass.
     * @param name The Name of NameSpace
     * @param allow PublicKey allowed to sign in here
     * @param repository URL of Default Store for NameSpace. (Note. A NameSpace object is stored in the default repository of it's parent namespace)
     * @param signer URL of default interactive signing service for namespace. If null it doesnt allow interactive signing
     * @param receiver URL of default receiver for namespace
     * @throws NeudistException
     */
    public NameSpace(String name, PublicKey allow, String repository, String signer, String logger, String receiver) throws NeudistException {
        super(name, "NameSpace");

        Element root = getElement();
        // We have meaningful defaults for the following two
        this.repository = Utility.denullString(repository, NSResolver.NSROOTSTORE);
        this.logger = Utility.denullString(repository, LogSender.LOGGER);

        this.signer = signer;
        this.receiver = receiver;
        root.addAttribute(DocumentHelper.createQName("repository", NamedObject.NS_NSDL), this.repository);
        root.addAttribute(DocumentHelper.createQName("logger", NamedObject.NS_NSDL), receiver);
        if (!Utility.isEmpty(signer))
            root.addAttribute(DocumentHelper.createQName("signer", NamedObject.NS_NSDL), signer);

        if (!Utility.isEmpty(receiver))
            root.addAttribute(DocumentHelper.createQName("receiver", NamedObject.NS_NSDL), receiver);

        if (allow != null) {
            QName allowName = DocumentHelper.createQName("allow", NamedObject.NS_NSDL);
            Element pub = root.addElement(allowName);
            pubs = new PublicKey[1];
            pubs[0] = allow;
            pub.add(XMLSecTools.createKeyInfo(allow));
        }
    }

    public NameSpace(String name, PublicKey allow, String repository) throws NeudistException {
        this(name, allow, repository, null, null, null);
    }

    public NameSpace(String name, PublicKey allow) throws NeudistException {
        this(name, allow, null);
    }

    /**
     * This constructor should be used by subclasses of NameSpace. It creates a Standard NameSpace document, but doesn't sign it.
     * The signing should be done as the last step of the constructor of the subclass.
     */
/*
   protected NameSpace(String name) throws NeudistException {
        super(name,"NameSpace");
   }
*/

    /**
     * Builds a NameSpace from an XML Document.
     */
    public NameSpace(Element nsElem) throws NeudistException/*,KeyResolverException*/ {
        super(nsElem);
        try {
            Element ns = getElement();
            repository = ns.attributeValue(DocumentHelper.createQName("store", NamedObject.NS_NSDL));
            signer = ns.attributeValue(DocumentHelper.createQName("signer", NamedObject.NS_NSDL));
            logger = ns.attributeValue(DocumentHelper.createQName("logger", NamedObject.NS_NSDL));
            receiver = ns.attributeValue(DocumentHelper.createQName("receiver", NamedObject.NS_NSDL));

            Element allowElement = ns.element(DocumentHelper.createQName("allow", NamedObject.NS_NSDL));
            List keys = allowElement.elements(XMLSecTools.createQName("KeyInfo"));
            pubs = new PublicKey[keys.size()];
            int i = 0;
            for (Iterator iter = keys.iterator(); iter.hasNext(); i++) {
                KeyInfo ki = new KeyInfo((Element) iter.next());
                pubs[i] = ki.getPublicKey();
            }
        } catch (Exception e) {
            Utility.rethrowException(e);
        }
    }

    /**
     * Returns the first allowed public key
     * @return the first allowed public key
     */
    public PublicKey getAllowed() {
        if (pubs != null && pubs.length > 0)
            return pubs[0];
        else
            return null;
    }

    public boolean postAllowed(NamedObject obj) throws NeudistException {
        try {
            for (int i = 0; i < pubs.length; i++) {
                if (obj.verifySignature(pubs[i]))
                    return true;
            }
        } catch (Exception e) {
            Utility.rethrowException(e);
        }
        return false;
    };

    public String getRepository() {
        return repository;
    }

    public String getSigner() {
        return signer;
    }

    public String getLogger() {
        return logger;
    }

    public final void send(NamedObject obj) throws NeudistException {
        if (!Utility.isEmpty(receiver))
            Sender.quickSend(receiver, obj);
        else
            throw new NeudistException("Cant send object, " + getName() + " doesnt have a registered Receiver");
    }

    void log(NamedObject obj) throws NeudistException {
        if (!Utility.isEmpty(logger))
            Sender.quickSend(logger, obj);
    }

    public String getTagName() {
        return "NameSpace";
    }

    private String repository;
    private String signer;
    private String logger;
    private String receiver;

    private PublicKey pubs[];
}
