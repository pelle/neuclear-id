/*
 * $Id: NamedObjectBuilder.java,v 1.7 2003/11/11 21:18:42 pelle Exp $
 * $Log: NamedObjectBuilder.java,v $
 * Revision 1.7  2003/11/11 21:18:42  pelle
 * Further vital reshuffling.
 * org.neudist.crypto.* and org.neudist.utils.* have been moved to respective areas under org.neuclear.commons
 * org.neuclear.signers.* as well as org.neuclear.passphraseagents have been moved under org.neuclear.commons.crypto as well.
 * Did a bit of work on the Canonicalizer and changed a few other minor bits.
 *
 * Revision 1.6  2003/11/10 17:42:36  pelle
 * The AssetController interface has been more or less finalized.
 * CurrencyController fully implemented
 * AssetControlClient implementes a remote client for communicating with AssetControllers
 *
 * Revision 1.5  2003/10/21 22:31:12  pelle
 * Renamed NeudistException to NeuClearException and moved it to org.neuclear.commons where it makes more sense.
 * Unhooked the XMLException in the xmlsig library from NeuClearException to make all of its exceptions an independent hierarchy.
 * Obviously had to perform many changes throughout the code to support these changes.
 *
 * Revision 1.4  2003/10/02 23:29:03  pelle
 * Updated Root Key. This will be the root key for the remainder of the beta period. With version 1.0 I will update it with a new key.
 * VerifyingTest works now and also does a pass for fake ones. Will have to think of better ways of making fake Identities to break it.
 * Cleaned up much of the tests and they all pass now.
 * The FileStoreTests need to be rethought out, by adding a test key.
 *
 * Revision 1.3  2003/10/01 19:08:30  pelle
 * Changed XML Format. Now NameSpace has been modified to Identity also the
 * xml namespace prefix nsdl has been changed to neuid.
 * The standard constants for using these have been moved into NSTools.
 * The NamedObjectBuilder can also now take an Element, such as an unsigned template.
 *
 * Revision 1.2  2003/10/01 17:05:37  pelle
 * Moved the NeuClearCertificate class to be an inner class of Identity.
 *
 * Revision 1.1  2003/09/27 19:23:11  pelle
 * Added Builders to create named objects from scratch.
 *
 * Revision 1.13  2003/02/18 14:57:19  pelle
 * Finished Cleaning up Receivers and Stores.
 * Also updated nsdl.xsd xml schema with latest changes.
 * The whole API is now very simple.
 *
 * Revision 1.12  2003/02/14 21:10:30  pelle
 * The email sender works. The LogSender and the SoapSender should work but havent been tested yet.
 * The NamedObject has a new log() method that logs it's contents at it's parent NameSpace's logger.
 * The NameSpace object also has a new method send() which allows one to send a named object to the NameSpace's
 * default receiver.
 *
 * Revision 1.11  2003/02/14 14:04:29  pelle
 * The New Source Classes work and NS resolution works as well.
 * I've renamed Target to TargetReference to prepare for the other main refactoring ahead. Implementation of
 * Senders.
 *
 * Revision 1.10  2003/02/10 22:30:06  pelle
 * Got rid of even further dependencies. In Particular OSCore
 *
 * Revision 1.9  2003/02/09 00:15:52  pelle
 * Fixed things so they now compile with r_0.7 of XMLSig
 *
 * Revision 1.8  2002/12/17 21:53:28  pelle
 * Final changes for refactoring.
 *
 * Revision 1.7  2002/12/17 20:34:39  pelle
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
 * Revision 1.6  2002/10/03 01:51:58  pelle
 * Bunch of smaller fixes for bugs found during deployment.
 * Also a bit more documentation.
 * I'm happy with this being called rev. 0.4
 *
 * Revision 1.5  2002/10/02 21:03:44  pelle
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
 * Revision 1.4  2002/09/25 19:20:15  pelle
 * Added various new schemas and updated most of the existing ones.
 * Added explanation interface for explaining the purpose of a
 * NamedObject to a user. We may want to use XSL instead.
 * Also made the signing webapp look a bit nicer.
 *
 * Revision 1.3  2002/09/21 23:11:13  pelle
 * A bunch of clean ups. Got rid of as many hard coded URL's as I could.
 *
 * Revision 1.2  2002/09/20 01:15:18  pelle
 * Added prototype webapplication under src/java
 * SOAPServlet appears to work
 * Any webservices taking named objects should subclass from ReceiverServlet
 * SigningServlet is not completely working right now, but
 * will be the main prototype of a web based signer.
 *
 * Other new features are GenericNamedObject for simple instantiation of
 * arbitrary named objects.
 *
 * Revision 1.1.1.1  2002/09/18 10:55:40  pelle
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
 * Revision 1.1.1.1  2002/05/29 10:02:22  pelle
 * Lets try one more time. This is the first rev of the next gen of Neudist
 *
 *
 */
package org.neuclear.id.builders;

import org.dom4j.*;
import org.neuclear.id.Identity;
import org.neuclear.id.NSTools;
import org.neuclear.id.Named;
import org.neuclear.id.resolver.NSResolver;
import org.neuclear.senders.Sender;
import org.neuclear.commons.time.TimeTools;
import org.neuclear.commons.NeuClearException;
import org.neuclear.commons.Utility;
import org.neuclear.xml.AbstractElementProxy;
import org.neuclear.xml.XMLException;
import org.neuclear.xml.xmlsec.SignedElement;
import org.neuclear.xml.xmlsec.XMLSecTools;
import org.neuclear.xml.xmlsec.XMLSecurityException;
import org.neuclear.commons.crypto.signers.Signer;

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * This simple wrapper takes most of the contents of a NamedObject and puts it into a Serializable form that can be signed.
 */
public class NamedObjectBuilder extends SignedElement implements Named {
    public NamedObjectBuilder(String name, String tagName, String prefix, String nsURI) {
        super(tagName, prefix, nsURI);
        createDocument();
        setName(name);
    }

    public NamedObjectBuilder(String name, String tagName, Namespace ns) {
        super(tagName, ns);
        createDocument();
        setName(name);
    }

    public NamedObjectBuilder(String name, String tagName) {
        super(tagName, NSTools.NS_NEUID);
        createDocument();
        setName(name);
    }

    public NamedObjectBuilder(String name, QName qname) {
        super(qname);
        createDocument();
        setName(name);
    }

    public NamedObjectBuilder(Element elem) throws XMLSecurityException {
        super(elem);
        //TODO Load targets
    }

    public String getTagName() {
        if (getElement() == null)
            return "Object";
        return getElement().getName();
    }

    public NamedObjectBuilder(Document doc) throws XMLSecurityException {
        super(doc.getRootElement());
    }

    final public void  sign(Signer signer) throws NeuClearException, XMLSecurityException {
        sign(getParent().getName(),signer);
    }
    /**
     * The full name (URI) of an object
     * @return String containing the fully qualified URI of an object
     */
    public String getName() {
        try {
            return NSTools.normalizeNameURI(getElement().attributeValue(getNameAttrQName()));
        } catch (NeuClearException e) {
            return "Unknown";
        }
    }

    /**
     * The Name of an object within it's parent NameSpace
     * @return Parent Name
     */
    public String getLocalName() {
        String fullName = getName();
        int i = fullName.lastIndexOf('/');
        return fullName.substring(i + 1);
    }

    private void setName(String name) {
        getElement().addAttribute(getNameAttrQName(), name);
    }

    private static QName getNameAttrQName() {
        return DocumentHelper.createQName("name", NSTools.NS_NEUID);

    }

    private void createDocument() {
        Element elem = getElement();
        if (elem.getDocument() == null) {
            Document doc = DocumentHelper.createDocument(elem);
        }
    }

    /**
     * @return the URI of the object. In this case the same as getName();
     */
    public final String getURI() throws XMLSecurityException {
        return getName();
    }

    /**
     * @return the XML NameSpace object
     */
    public Namespace getNS() {
        return NSTools.NS_NEUID;
    }

    protected void addElement(NamedObjectBuilder child) throws XMLException {
        addElement((AbstractElementProxy) child);
    }


    /**
     * Sign NamedObject using given PrivateKey. This also adds a timestamp to the root element prior to signing
     */
    protected final void preSign() throws XMLSecurityException {
        // We need to timestamp it before we sign it
        getElement().addAttribute(DocumentHelper.createQName("timestamp", NSTools.NS_NEUID), TimeTools.createTimeStamp());
    }

    /**
     * This is called after signing to handle any post signing tasks such as logging
     * @throws XMLSecurityException
     */
    protected void postSign() throws XMLSecurityException {
        try {
            log();
        } catch (NeuClearException e) {
            XMLSecTools.rethrowException(e);
        }
    }

    /**
     * Adds a TargetReference to a NamedObject.<br>
     * This can only be done if the object isn't signed.
     * @param target object
     */
    public void addTarget(TargetReference target) throws NeuClearException {
        if (target == null)
            return;
        QName targetsQN = DocumentHelper.createQName("Targets", NSTools.NS_NEUID);
        Element targetsElem = getElement().element(targetsQN);
        if (targetsElem == null) {
            targetsElem = DocumentHelper.createElement(targetsQN);
            getElement().add(targetsElem);
        }
        targetsElem.add(target.getElement());
        targetList().add(target);
    }

    private synchronized List targetList() {
        if (targets == null)
            targets = new LinkedList();
        return targets;
    }

    /**
     * Lists the targets within an object
     * @return Iterator of targets
     */
    public Iterator listTargets() throws NeuClearException {
        return targetList().iterator();
    }

    /**
     *   Sends copy of object to all targets within
     */
    public void sendObject() throws NeuClearException {
        System.out.println("NEUDIST: Sending Object " + getName());

        if (this.isSigned()) {
            Iterator iter = listTargets();
            while (iter.hasNext()) {
                TargetReference tg = ((TargetReference) iter.next());
                tg.send();
                System.out.println("NEUDIST: Sent to " + tg.getHref());
            }

        }

    }

    public Timestamp getTimeStamp() throws NeuClearException {
        String timeString = getElement().attributeValue(DocumentHelper.createQName("timestamp", NSTools.NS_NEUID));
        if (isSigned() && !Utility.isEmpty(timeString)) {
            try {
                return TimeTools.parseTimeStamp(timeString);
            } catch (NeuClearException e) {
                return null;
            }
        }


        return null;

    }

    public final void log() throws NeuClearException {
        Identity ns = getParent();
        if (ns != null && ns.getLogger() != null)
            Sender.quickSend(ns.getLogger(), this);
    }

    public Identity getParent() throws NeuClearException {
        return NSResolver.resolveIdentity(NSTools.getParentNSURI(getName()));
    }

    private List targets;


}
