/*
 * $Id: NamedObjectFactory.java,v 1.3 2003/09/23 19:16:27 pelle Exp $
 * $Log: NamedObjectFactory.java,v $
 * Revision 1.3  2003/09/23 19:16:27  pelle
 * Changed NameSpace to Identity.
 * To cause less confusion in the future.
 *
 * Revision 1.2  2003/09/22 19:24:01  pelle
 * More fixes throughout to problems caused by renaming.
 *
 * Revision 1.1.1.1  2003/09/19 14:40:53  pelle
 * First import into the neuclear project. This was originally under the SF neudist
 * project. This marks a general major refactoring and renaming ahead.
 *
 * The new name for this code is NeuClear Identity and has the general package header of
 * org.neuclear.id
 * There are other areas within the current code which will be split out into other subprojects later on.
 * In particularly the signers will be completely seperated out as well as the contract types.
 *
 *
 * Revision 1.9  2003/02/14 21:10:30  pelle
 * The email sender works. The LogSender and the SoapSender should work but havent been tested yet.
 * The NamedObject has a new log() method that logs it's contents at it's parent Identity's logger.
 * The Identity object also has a new method send() which allows one to send a named object to the Identity's
 * default receiver.
 *
 * Revision 1.8  2003/02/14 05:10:12  pelle
 * New Source model is implemented.
 * It doesnt quite verify things correctly yet. I'm not yet sure why.
 * CommandLineSigner is simplified to make it easier to use.
 *
 * Revision 1.7  2003/02/09 00:15:52  pelle
 * Fixed things so they now compile with r_0.7 of XMLSig
 *
 * Revision 1.6  2003/01/16 22:20:02  pelle
 * First Draft of new generalised Ledger Interface.
 * Currently we have a Book and Transaction class.
 * We also need a Ledger class and a Ledger Factory.
 *
 * Revision 1.5  2002/12/17 21:40:54  pelle
 * First part of refactoring of NamedObject and SignedObject Interface/Class parings.
 *
 * Revision 1.4  2002/12/17 20:34:39  pelle
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
 * Revision 1.3  2002/10/02 21:03:44  pelle
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
 * Revision 1.2  2002/09/21 23:11:13  pelle
 * A bunch of clean ups. Got rid of as many hard coded URL's as I could.
 *
 * Revision 1.1.1.1  2002/09/18 10:55:39  pelle
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
 * Revision 1.1.1.1  2002/05/29 10:02:20  pelle
 * Lets try one more time. This is the first rev of the next gen of Neudist
 *
 *
 */
package org.neuclear.id;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.QName;
import org.neuclear.contracts.nsauth.AuthenticationTicket;
import org.neuclear.id.resolver.NSResolver;
import org.neuclear.id.verifier.NSVerifier;
import org.neuclear.source.Source;
import org.neudist.utils.NeudistException;

public class NamedObjectFactory {


    /**
     * Used to Create a Named Object from an XML Document
     * @param doc Uses Root element to create named object
     * @return a NamedObject
     * @throws NeudistException
     */
    public static NamedObject createNamedObject(Document doc) throws NeudistException {
        return createNamedObject(doc.getRootElement());
    }

    /**
     * Used to Create a Named Object from an XML Element
     * @param elem Uses this element to create named object
     * @return a NamedObject
     * @throws NeudistException
     */
    public static NamedObject createNamedObject(Element elem) throws NeudistException {
        QName rootName = elem.getQName();
        if (rootName.getName().equals("Identity") && rootName.getNamespaceURI().equals(NamedObject.NSDL_NAMESPACE))
            return new Identity(elem);
        else if (rootName.getName().equals("AuthenticationTicket") && rootName.getNamespaceURI().equals(AuthenticationTicket.URI_NSAUTH))
            return new AuthenticationTicket(elem);
        else
            return new GenericNamedObject(elem);

    }

    public static NamedObject fetchNamedObject(String name) throws NeudistException {
        String parentName = NSTools.getParentNSURI(name);
        if (!parentName.equals("neu://")) {
            Identity parent = NSResolver.resolveNameSpace(parentName);
            if (parent == null)
                return null;

        }
        NamedObject nobj = Source.getInstance().fetch(NSResolver.NSROOTSTORE, name);
        if (!NSVerifier.isNameValid(nobj))
            throw new InvalidIdentityException(nobj.getName() + " is not valid");
        return nobj;
    }
}
