/*
 * $Id: NamedObjectBuilder.java,v 1.20 2003/12/19 18:03:34 pelle Exp $
 * $Log: NamedObjectBuilder.java,v $
 * Revision 1.20  2003/12/19 18:03:34  pelle
 * Revamped a lot of exception handling throughout the framework, it has been simplified in most places:
 * - For most cases the main exception to worry about now is InvalidNamedObjectException.
 * - Most lowerlevel exception that cant be handled meaningful are now wrapped in the LowLevelException, a
 *   runtime exception.
 * - Source and Store patterns each now have their own exceptions that generalizes the various physical
 *   exceptions that can happen in that area.
 *
 * Revision 1.19  2003/12/18 17:40:19  pelle
 * You can now create keys that get stored with a X509 certificate in the keystore. These can be saved as well.
 * IdentityCreator has been modified to allow creation of keys.
 * Note The actual Creation of Certificates still have a problem that will be resolved later today.
 *
 * Revision 1.18  2003/12/11 23:57:29  pelle
 * Trying to test the ReceiverServlet with cactus. Still no luck. Need to return a ElementProxy of some sort.
 * Cleaned up some missing fluff in the ElementProxy interface. getTagName(), getQName() and getNameSpace() have been killed.
 *
 * Revision 1.17  2003/12/11 16:29:26  pelle
 * Updated various builders to use the new helper methods in AbstractElementProxy hopefully making them more readable.
 *
 * Revision 1.16  2003/12/10 23:58:51  pelle
 * Did some cleaning up in the builders
 * Fixed some stuff in IdentityCreator
 * New maven goal to create executable jarapp
 * We are close to 0.8 final of ID, 0.11 final of XMLSIG and 0.5 of commons.
 * Will release shortly.
 *
 * Revision 1.15  2003/12/08 19:32:31  pelle
 * Added support for the http scheme into ID. See http://neuclear.org/archives/000195.html
 *
 * Revision 1.14  2003/11/22 00:23:46  pelle
 * All unit tests in commons, id and xmlsec now work.
 * AssetController now successfully processes payments in the unit test.
 * Payment Web App has working form that creates a TransferRequest presents it to the signer
 * and forwards it to AssetControlServlet. (Which throws an XML Parser Exception) I think the XMLReaderServlet is bust.
 *
 * Revision 1.13  2003/11/21 17:55:16  pelle
 * misc fixes
 *
 * Revision 1.12  2003/11/21 13:57:27  pelle
 * Changed some mutable fields in immutable classes, making them truely immutable. Thus safer.
 *
 * Revision 1.11  2003/11/21 04:45:10  pelle
 * EncryptedFileStore now works. It uses the PBECipher with DES3 afair.
 * Otherwise You will Finaliate.
 * Anything that can be final has been made final throughout everyting. We've used IDEA's Inspector tool to find all instance of variables that could be final.
 * This should hopefully make everything more stable (and secure).
 *
 * Revision 1.10  2003/11/20 23:42:24  pelle
 * Getting all the tests to work in id
 * Removing usage of BC in CryptoTools as it was causing issues.
 * First version of EntityLedger that will use OFB's EntityEngine. This will allow us to support a vast amount databases without
 * writing SQL. (Yipee)
 *
 * Revision 1.9  2003/11/19 23:33:58  pelle
 * Signers now can generatekeys via the generateKey() method.
 * Refactored the relationship between SignedNamedObject and NamedObjectBuilder a bit.
 * SignedNamedObject now contains the full xml which is returned with getEncoded()
 * This means that it is now possible to further receive on or process a SignedNamedObject, leaving
 * NamedObjectBuilder for its original purposes of purely generating new Contracts.
 * NamedObjectBuilder.sign() now returns a SignedNamedObject which is the prefered way of processing it.
 * Updated all major interfaces that used the old model to use the new model.
 *
 * Revision 1.8  2003/11/15 01:58:16  pelle
 * More work all around on web applications.
 *
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
 * The NameSpace object also has a new method receive() which allows one to receive a named object to the NameSpace's
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
import org.neuclear.commons.NeuClearException;
import org.neuclear.commons.Utility;
import org.neuclear.commons.crypto.signers.Signer;
import org.neuclear.commons.crypto.signers.NonExistingSignerException;
import org.neuclear.commons.crypto.passphraseagents.UserCancellationException;
import org.neuclear.commons.time.TimeTools;
import org.neuclear.id.*;
import org.neuclear.id.resolver.NSResolver;
import org.neuclear.id.verifier.VerifyingReader;
import org.neuclear.xml.XMLException;
import org.neuclear.xml.xmlsec.SignedElement;
import org.neuclear.xml.xmlsec.XMLSecurityException;

import java.sql.Timestamp;
import java.text.ParseException;

/**
 * This simple wrapper takes most of the contents of a NamedObject and puts it into a Serializable form that can be signed.
 */
public class NamedObjectBuilder extends SignedElement implements Named, Cloneable {
    protected NamedObjectBuilder(final String name, final String tagName, final String prefix, final String nsURI) throws InvalidNamedObjectException {
        super(tagName, prefix, nsURI);
        createDocument();
        setName(name);
    }

    protected NamedObjectBuilder(final String name, final String tagName, final Namespace ns) throws InvalidNamedObjectException {
        super(tagName, ns);
        createDocument();
        setName(name);
    }

    protected NamedObjectBuilder(final String name, final String tagName) throws InvalidNamedObjectException {
        super(tagName, NSTools.NS_NEUID);
        createDocument();
        setName(name);
    }

    protected NamedObjectBuilder(final String name, final QName qname) throws InvalidNamedObjectException {
        super(qname);
        createDocument();
        setName(name);
    }

    public NamedObjectBuilder(final Element elem) throws XMLSecurityException,InvalidNamedObjectException {
        super(elem);
        final String name=getName();
        if (!NSTools.isValidName(name))
            throw new InvalidNamedObjectException(name);

    }


    public NamedObjectBuilder(final Document doc) throws XMLSecurityException,InvalidNamedObjectException {
        super(doc.getRootElement());
    }


    final public SignedNamedObject sign(final Signer signer) throws InvalidNamedObjectException,  NonExistingSignerException, UserCancellationException, NameResolutionException {
        try {
            sign(getSignatory().getName(), signer); //Sign with parent key
            return convert();
        } catch (XMLSecurityException e) {
            throw new InvalidNamedObjectException(getName(),e);
        }
    }

    final public SignedNamedObject convert() throws InvalidNamedObjectException, NameResolutionException{

        return VerifyingReader.getInstance().read(getElement());
    }

    /**
     * The full name (URI) of an object
     * 
     * @return String containing the fully qualified URI of an object
     */
    public final String getName() {
        QName q=getNameAttrQName();
        if (getElement()!=null&&getElement().attribute(q)!=null)
            return getElement().attributeValue(q);
        return null;

    }

    /**
     * The Name of an object within it's parent NameSpace
     * 
     * @return Parent Name
     */
    public final String getLocalName() throws InvalidNamedObjectException {
        return NSTools.getLocalName(getName());
    }

    private void setName(final String name) throws InvalidNamedObjectException {
        getElement().addAttribute(getNameAttrQName(), NSTools.normalizeNameURI(name));
    }

    private static QName getNameAttrQName() {
        return createNEUIDQName("name");

    }

    /**
     * Helper method to create and add an attribute to this element within the NEUID namespace
     * 
     * @param name 
     */
    protected final void createNEUIDAttribute(String name, String value) {
        getElement().addAttribute(createNEUIDQName(name), value);
    }

    /**
     * Helper method to create a QName within the NEUID namespace
     * 
     * @param name 
     * @return 
     */
    protected static QName createNEUIDQName(String name) {
        return DocumentHelper.createQName(name, NSTools.NS_NEUID);
    }

    private void createDocument() {
        final Element elem = getElement();
        if (elem.getDocument() == null) {
            final Document doc = DocumentHelper.createDocument(elem);
        }
    }

    /**
     * @return the URI of the object. In this case the same as getName();
     */
    public final String getURI() throws XMLSecurityException {
        return getName();
    }


    /**
     * Sign NamedObject using given PrivateKey. This also adds a timestamp to the root element prior to signing
     */
    protected final void preSign() throws XMLSecurityException {
        // We need to timestamp it before we sign it
        getElement().addAttribute(DocumentHelper.createQName("timestamp", NSTools.NS_NEUID), TimeTools.createTimeStamp());
    }


    /**
     * Adds a TargetReference to a NamedObject.<br>
     * This can only be done if the object isn't signed.
     * 
     * @param target object
     */
/*
    public final void addTarget(final TargetReference target) throws NeuClearException {
        if (target == null)
            return;
        final QName targetsQN = DocumentHelper.createQName("Targets", NSTools.NS_NEUID);
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
*/

    /**
     * Lists the targets within an object
     * 
     * @return Iterator of targets
     */
/*
    public final Iterator listTargets() throws NeuClearException {
        return targetList().iterator();
    }
*/

    /**
     * Sends copy of object to all targets within
     * <p/>
     * public final void sendObject() throws NeuClearException {
     * System.out.println("NEUDIST: Sending Object " + getName());
     * <p/>
     * if (this.isSigned()) {
     * final Iterator iter = listTargets();
     * while (iter.hasNext()) {
     * final TargetReference tg = ((TargetReference) iter.next());
     * System.out.println("NEUDIST: Sent to " + tg.getHref());
     * }
     * <p/>
     * }
     * <p/>
     * }
     */
/*
    public final void sendObject() throws NeuClearException {
        System.out.println("NEUDIST: Sending Object " + getName());

        if (this.isSigned()) {
            final Iterator iter = listTargets();
            while (iter.hasNext()) {
                final TargetReference tg = ((TargetReference) iter.next());
                System.out.println("NEUDIST: Sent to " + tg.getHref());
            }

        }

    }
*/

    public final Timestamp getTimeStamp()  {
        final String timeString = getElement().attributeValue(DocumentHelper.createQName("timestamp", NSTools.NS_NEUID));
        if (isSigned() && !Utility.isEmpty(timeString)) {
            try {
                return TimeTools.parseTimeStamp(timeString);
            } catch (ParseException e) {
                return null;
            }
        }


        return null;

    }


    public final Identity getSignatory() throws InvalidNamedObjectException, NameResolutionException  {
        return NSResolver.resolveIdentity(NSTools.getSignatoryURI(getName()));
    }

    /**
     * Creates and returns a copy of this object.  The precise meaning
     * of "copy" may depend on the class of the object. The general
     * intent is that, for any object <tt>x</tt>, the expression:
     * <blockquote>
     * <pre>
     * x.clone() != x</pre></blockquote>
     * will be true, and that the expression:
     * <blockquote>
     * <pre>
     * x.clone().getClass() == x.getClass()</pre></blockquote>
     * will be <tt>true</tt>, but these are not absolute requirements.
     * While it is typically the case that:
     * <blockquote>
     * <pre>
     * x.clone().equals(x)</pre></blockquote>
     * will be <tt>true</tt>, this is not an absolute requirement.
     * <p/>
     * By convention, the returned object should be obtained by calling
     * <tt>super.clone</tt>.  If a class and all of its superclasses (except
     * <tt>Object</tt>) obey this convention, it will be the case that
     * <tt>x.clone().getClass() == x.getClass()</tt>.
     * <p/>
     * By convention, the object returned by this method should be independent
     * of this object (which is being cloned).  To achieve this independence,
     * it may be necessary to modify one or more fields of the object returned
     * by <tt>super.clone</tt> before returning it.  Typically, this means
     * copying any mutable objects that comprise the internal "deep structure"
     * of the object being cloned and replacing the references to these
     * objects with references to the copies.  If a class contains only
     * primitive fields or references to immutable objects, then it is usually
     * the case that no fields in the object returned by <tt>super.clone</tt>
     * need to be modified.
     * <p/>
     * The method <tt>clone</tt> for class <tt>Object</tt> performs a
     * specific cloning operation. First, if the class of this object does
     * not implement the interface <tt>Cloneable</tt>, then a
     * <tt>CloneNotSupportedException</tt> is thrown. Note that all arrays
     * are considered to implement the interface <tt>Cloneable</tt>.
     * Otherwise, this method creates a new instance of the class of this
     * object and initializes all its fields with exactly the contents of
     * the corresponding fields of this object, as if by assignment; the
     * contents of the fields are not themselves cloned. Thus, this method
     * performs a "shallow copy" of this object, not a "deep copy" operation.
     * <p/>
     * The class <tt>Object</tt> does not itself implement the interface
     * <tt>Cloneable</tt>, so calling the <tt>clone</tt> method on an object
     * whose class is <tt>Object</tt> will result in throwing an
     * exception at run time.
     * 
     * @return a clone of this instance.
     * @see Cloneable
     */
    public Object clone() {
        try {
            final Element elem = (Element) getElement().clone();
            DocumentHelper.createDocument(elem);
            return new NamedObjectBuilder(elem);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
