/*
 * $Id: IdentityBuilder.java,v 1.19 2004/02/18 00:14:30 pelle Exp $
 * $Log: IdentityBuilder.java,v $
 * Revision 1.19  2004/02/18 00:14:30  pelle
 * Many, many clean ups. I've readded Targets in a new method.
 * Gotten rid of NamedObjectBuilder and revamped Identity and Resolvers
 *
 * Revision 1.18  2004/01/13 15:11:35  pelle
 * Now builds.
 * Now need to do unit tests
 *
 * Revision 1.17  2004/01/07 23:12:20  pelle
 * XMLSig now has various added features:
 * -  KeyInfo supports X509v3 (untested)
 * -  KeyInfo supports KeyName
 * -  When creating a XMLSignature and signing it with a Signer, it adds the alias to the KeyName
 * Added KeyResolver interface and KeyResolverFactory Class. At the moment no implementations.
 *
 * Revision 1.16  2003/12/19 18:03:34  pelle
 * Revamped a lot of exception handling throughout the framework, it has been simplified in most places:
 * - For most cases the main exception to worry about now is InvalidNamedObjectException.
 * - Most lowerlevel exception that cant be handled meaningful are now wrapped in the LowLevelException, a
 *   runtime exception.
 * - Source and Store patterns each now have their own exceptions that generalizes the various physical
 *   exceptions that can happen in that area.
 *
 * Revision 1.15  2003/12/18 17:40:19  pelle
 * You can now create keys that get stored with a X509 certificate in the keystore. These can be saved as well.
 * IdentityCreator has been modified to allow creation of keys.
 * Note The actual Creation of Certificates still have a problem that will be resolved later today.
 *
 * Revision 1.14  2003/12/16 15:04:59  pelle
 * Added SignedMessage contract for signing simple textual contracts.
 * Added NeuSender, updated SmtpSender and Sender to take plain email addresses (without the mailto:)
 * Added AbstractObjectCreationTest to make it quicker to write unit tests to verify
 * NamedObjectBuilder/SignedNamedObject Pairs.
 * Sample application has been expanded with a basic email application.
 * Updated docs for sample web app.
 * Added missing LGPL LICENSE.txt files to signer and sample app
 *
 * Revision 1.13  2003/12/11 23:57:29  pelle
 * Trying to test the ReceiverServlet with cactus. Still no luck. Need to return a ElementProxy of some sort.
 * Cleaned up some missing fluff in the ElementProxy interface. getTagName(), getQName() and getNameSpace() have been killed.
 *
 * Revision 1.12  2003/12/11 16:29:26  pelle
 * Updated various builders to use the new helper methods in AbstractElementProxy hopefully making them more readable.
 *
 * Revision 1.11  2003/12/11 16:16:14  pelle
 * Some changes to make the xml a bit more readable.
 * Also added some helper methods in AbstractElementProxy to make it easier to build objects.
 *
 * Revision 1.10  2003/12/10 23:58:51  pelle
 * Did some cleaning up in the builders
 * Fixed some stuff in IdentityCreator
 * New maven goal to create executable jarapp
 * We are close to 0.8 final of ID, 0.11 final of XMLSIG and 0.5 of commons.
 * Will release shortly.
 *
 * Revision 1.9  2003/12/08 19:32:31  pelle
 * Added support for the http scheme into ID. See http://neuclear.org/archives/000195.html
 *
 * Revision 1.8  2003/11/21 04:45:10  pelle
 * EncryptedFileStore now works. It uses the PBECipher with DES3 afair.
 * Otherwise You will Finaliate.
 * Anything that can be final has been made final throughout everyting. We've used IDEA's Inspector tool to find all instance of variables that could be final.
 * This should hopefully make everything more stable (and secure).
 *
 * Revision 1.7  2003/11/11 21:18:42  pelle
 * Further vital reshuffling.
 * org.neudist.crypto.* and org.neudist.utils.* have been moved to respective areas under org.neuclear.commons
 * org.neuclear.signers.* as well as org.neuclear.passphraseagents have been moved under org.neuclear.commons.crypto as well.
 * Did a bit of work on the Canonicalizer and changed a few other minor bits.
 *
 * Revision 1.6  2003/11/10 21:08:49  pelle
 * More JavaDoc
 *
 * Revision 1.5  2003/10/29 21:16:27  pelle
 * Refactored the whole signing process. Now we have an interface called Signer which is the old SignerStore.
 * To use it you pass a byte array and an alias. The sign method then returns the signature.
 * If a Signer needs a passphrase it uses a PassPhraseAgent to present a dialogue box, read it from a command line etc.
 * This new Signer pattern allows us to use secure signing hardware such as N-Cipher in the future for server applications as well
 * as SmartCards for end user applications.
 *
 * Revision 1.4  2003/10/21 22:31:12  pelle
 * Renamed NeudistException to NeuClearException and moved it to org.neuclear.commons where it makes more sense.
 * Unhooked the XMLException in the xmlsig library from NeuClearException to make all of its exceptions an independent hierarchy.
 * Obviously had to perform many changes throughout the code to support these changes.
 *
 * Revision 1.3  2003/10/02 23:29:03  pelle
 * Updated Root Key. This will be the root key for the remainder of the beta period. With version 1.0 I will update it with a new key.
 * VerifyingTest works now and also does a pass for fake ones. Will have to think of better ways of making fake Identities to break it.
 * Cleaned up much of the tests and they all pass now.
 * The FileStoreTests need to be rethought out, by adding a test key.
 *
 * Revision 1.2  2003/10/01 19:08:30  pelle
 * Changed XML Format. Now NameSpace has been modified to Identity also the
 * xml namespace prefix nsdl has been changed to neuid.
 * The standard constants for using these have been moved into NSTools.
 * The NamedObjectBuilder can also now take an Element, such as an unsigned template.
 *
 * Revision 1.1  2003/09/27 19:23:11  pelle
 * Added Builders to create named objects from scratch.
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
 * The NamedObject has a new log() method that logs it's contents at it's parent NameSpace's logger.
 * The NameSpace object also has a new method receive() which allows one to receive a named object to the NameSpace's
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
 * The example .ns objects in the neuspace folder have been updated with the
 * latest version of the format.
 * "neuspace/root.ns" should now be considered the universal parent of the
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
package org.neuclear.id.builders;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.QName;
import org.neuclear.commons.NeuClearException;
import org.neuclear.commons.Utility;
import org.neuclear.id.InvalidNamedObjectException;
import org.neuclear.id.NSTools;
import org.neuclear.xml.xmlsec.XMLSecTools;
import org.neuclear.xml.xmlsec.XMLSecurityException;

import java.security.PublicKey;

public class IdentityBuilder extends Builder {
    /**
     * It creates a Standard Identity document, but doesn't sign it.
     * 
     * @param allow      PublicKey allowed to sign in here
     * @param signer     URL of default interactive signing service for namespace. If null it doesnt allow interactive signing
     * @param receiver   URL of default receiver for namespace
     */

    public IdentityBuilder( final PublicKey allow, final String signer, final String logger, final String receiver) throws InvalidNamedObjectException {
        this(createNEUIDQName(TAGNAME), allow, signer, logger, receiver);
    }

    /**
     * This constructor should be used by subclasses of Identity. It creates a Standard Identity document, but doesn't sign it.
     * 
     * @param tag        The Tag used by this sub class
     * @param allow      PublicKey allowed to sign in here
     * @param signer     URL of default interactive signing service for namespace. If null it doesnt allow interactive signing
     * @param receiver   URL of default receiver for namespace
     */
    protected IdentityBuilder(final QName tag, final PublicKey allow,  final String signer, final String logger, final String receiver) throws InvalidNamedObjectException {
        super(tag);

        final Element root = getElement();
        addLineBreak();
        if (!Utility.isEmpty(signer))
            addElement("Signer",signer);
        addTarget(logger,"logger");
        addTarget(receiver,"inbox");

        final QName allowName = DocumentHelper.createQName("Allow", NSTools.NS_NEUID);
        Element pub=getElement().element(allowName);
        if (pub==null)
            pub = getElement().addElement(allowName);
        else
            pub.clearContent();
        pub.addText("\n");
        pub.add(XMLSecTools.createKeyInfo(allow));

    }

    private void addTarget(final String href,final String type) {
        if (!Utility.isEmpty(href))
            addElement("Target",href).addAttribute("type",type);
    }



    public IdentityBuilder(final PublicKey allow) throws XMLSecurityException, NeuClearException {
        this( allow, null,null,null);
    }


    private static final String TAGNAME = "Identity";



}
