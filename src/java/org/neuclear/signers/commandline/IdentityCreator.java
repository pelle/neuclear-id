/* $Id: IdentityCreator.java,v 1.4 2003/11/05 18:50:33 pelle Exp $
 * $Log: IdentityCreator.java,v $
 * Revision 1.4  2003/11/05 18:50:33  pelle
 * Refactored org.neuclear.signers.source.Source and implementing classes to provide support for a local filesystem cache.
 * Also added Unit tests to make sure it actually works and modified IdentityCreator to write directly to the cache if no output filename is given.
 *
 * Revision 1.3  2003/10/31 23:58:53  pelle
 * The IdentityCreator now fully works with the new Signer architecture.
 *
 * Revision 1.2  2003/10/29 21:16:27  pelle
 * Refactored the whole signing process. Now we have an interface called Signer which is the old SignerStore.
 * To use it you pass a byte array and an alias. The sign method then returns the signature.
 * If a Signer needs a passphrase it uses a PassPhraseAgent to present a dialogue box, read it from a command line etc.
 * This new Signer pattern allows us to use secure signing hardware such as N-Cipher in the future for server applications as well
 * as SmartCards for end user applications.
 *
 * Revision 1.1  2003/10/25 00:39:54  pelle
 * Fixed SmtpSender it now sends the messages.
 * Refactored CommandLineSigner. Now it simply signs files read from command line. However new class IdentityCreator
 * is subclassed and creates new Identities. You can subclass CommandLineSigner to create your own variants.
 * Several problems with configuration. Trying to solve at the moment. Updated PicoContainer to beta-2
 *
 * Revision 1.7  2003/10/21 22:31:13  pelle
 * Renamed NeudistException to NeuClearException and moved it to org.neuclear.commons where it makes more sense.
 * Unhooked the XMLException in the xmlsig library from NeuClearException to make all of its exceptions an independent hierarchy.
 * Obviously had to perform many changes throughout the code to support these changes.
 *
 * Revision 1.6  2003/10/01 19:08:31  pelle
 * Changed XML Format. Now NameSpace has been modified to Identity also the
 * xml namespace prefix nsdl has been changed to neuid.
 * The standard constants for using these have been moved into NSTools.
 * The NamedObjectBuilder can also now take an Element, such as an unsigned template.
 *
 * Revision 1.5  2003/09/26 00:22:07  pelle
 * Cleanups and final changes to code for refactoring of the Verifier and Reader part.
 *
 * Revision 1.4  2003/09/24 23:56:48  pelle
 * Refactoring nearly done. New model for creating signed objects.
 * With view for supporting the xmlpull api shortly for performance reasons.
 * Currently still uses dom4j but that has been refactored out that it
 * should now be very quick to implement a xmlpull implementation.
 *
 * A side benefit of this is that the API has been further simplified. I still have some work
 * todo with regards to cleaning up some of the outlying parts of the code.
 *
 * Revision 1.3  2003/09/23 19:16:28  pelle
 * Changed NameSpace to Identity.
 * To cause less confusion in the future.
 *
 * Revision 1.2  2003/09/22 19:24:02  pelle
 * More fixes throughout to problems caused by renaming.
 *
 * Revision 1.1.1.1  2003/09/19 14:41:31  pelle
 * First import into the neuclear project. This was originally under the SF neudist
 * project. This marks a general major refactoring and renaming ahead.
 *
 * The new name for this code is NeuClear Identity and has the general package header of
 * org.neuclear.id
 * There are other areas within the current code which will be split out into other subprojects later on.
 * In particularly the signers will be completely seperated out as well as the contract types.
 *
 *
 * Revision 1.13  2003/02/18 14:57:21  pelle
 * Finished Cleaning up Receivers and Stores.
 * Also updated nsdl.xsd xml schema with latest changes.
 * The whole API is now very simple.
 *
 * Revision 1.12  2003/02/18 00:06:15  pelle
 * Moved the Signer's into xml-sig
 *
 * Revision 1.11  2003/02/16 00:26:18  pelle
 * Changed the hardcoded logger default to pick it up from LogSender
 *
 * Revision 1.10  2003/02/14 21:10:35  pelle
 * The email sender works. The LogSender and the SoapSender should work but havent been tested yet.
 * The SignedNamedObject has a new log() method that logs it's contents at it's parent Identity's logger.
 * The Identity object also has a new method send() which allows one to send a named object to the Identity's
 * default receiver.
 *
 * Revision 1.9  2003/02/14 05:10:13  pelle
 * New Source model is implemented.
 * It doesnt quite verify things correctly yet. I'm not yet sure why.
 * CommandLineSigner is simplified to make it easier to use.
 *
 * Revision 1.8  2003/02/10 22:30:14  pelle
 * Got rid of even further dependencies. In Particular OSCore
 *
 * Revision 1.7  2003/02/09 00:15:55  pelle
 * Fixed things so they now compile with r_0.7 of XMLSig
 *
 * Revision 1.6  2002/12/17 21:40:58  pelle
 * First part of refactoring of SignedNamedObject and SignedObject Interface/Class parings.
 *
 * Revision 1.5  2002/12/17 20:34:41  pelle
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
 * Revision 1.4  2002/10/10 21:29:25  pelle
 * Oops. XML-Signature's SignedInfo element I had coded as SignatureInfo
 * As I thought Canonicalisation doesnt seem to be standard.
 * Updated the SignedServlet to default to using ~/.neuclear/signers.ks
 *
 * Revision 1.3  2002/10/06 00:39:29  pelle
 * I have now expanded support for different types of Signers.
 * There is now a JCESigner which uses a JCE KeyStore for signing.
 * I have refactored the SigningServlet a bit, eliminating most of the demo code.
 * This has been moved into DemoSigningServlet.
 * I have expanded the CommandLineSigner, so it now also has an option for specifying a default signing service.
 * The default web application now contains two signers.
 * - The Demo one is still at /Signer
 * - There is a new one at /personal/Signer this uses the testkeys.ks for
 * signing anything under neu://test
 * Note neu://test now has a default interactive signer running on localhost.
 * So to play with this you must install the webapp on your own local machine.
 *
 * Revision 1.2  2002/10/02 21:03:45  pelle
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
 * Revision 1.1  2002/09/29 00:22:09  pelle
 * Several cosmetic changes.
 * First attempt at a new CommandLine tool for signing and creating namespace files.
 * This will be used by people to create requests for namespaces.
 *
 */
package org.neuclear.signers.commandline;

import org.apache.commons.cli.Options;
import org.neuclear.commons.NeuClearException;
import org.neuclear.id.NSTools;
import org.neuclear.id.builders.IdentityBuilder;
import org.neuclear.id.builders.NamedObjectBuilder;
import org.neuclear.id.resolver.NSResolver;
import org.neuclear.senders.LogSender;
import org.neuclear.signers.PublicKeySource;
import org.neudist.crypto.CryptoException;
import org.neudist.utils.Utility;

import java.io.File;
import java.security.PublicKey;

/**
 * @author pelleb
 * @version $Revision: 1.4 $
 */
public class IdentityCreator extends CommandLineSigner {
    public IdentityCreator(String args[]) throws Exception {
        super(args);
        if (!(sig instanceof PublicKeySource))
            throw new NeuClearException("The default signer has to include public keys");
        pksource = (PublicKeySource) sig;
        identity = cmd.getOptionValue("n");
        String cachedirpath = System.getProperty("user.home") + "/.neuclear/cache";
        File cachedir = new File(cachedirpath);
        if (!cachedir.exists())
            cachedir.mkdirs();

        of = Utility.denullString(of, cachedirpath + NSTools.url2path(identity) + "/root.id");
        alias = Utility.denullString(alias, NSTools.getParentNSURI(identity));


    }

    public NamedObjectBuilder build() throws Exception {
        String allow = Utility.denullString(cmd.getOptionValue("w"), identity);
        String defaultstore = Utility.denullString(cmd.getOptionValue("r"), NSResolver.NSROOTSTORE);
        String defaultsigner = Utility.denullString(cmd.getOptionValue("i"), "http://localhost:11870/signer");
        String defaultlogger = Utility.denullString(cmd.getOptionValue("l"), LogSender.LOGGER);
        String defaultreceiver = cmd.getOptionValue("b");
        final PublicKey newkid = pksource.getPublicKey(allow);
        if (newkid == null)
            throw new CryptoException("PublicKey not available for: " + allow);
        return new IdentityBuilder(identity, newkid, defaultstore, defaultsigner, defaultlogger, defaultreceiver);

    }

    public static void main(String args[]) {
        try {
            IdentityCreator signer = new IdentityCreator(args);
            signer.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.exit(0);
    }

    protected String getExtraHelp() {
        return "[--name neu://neu/one --allow neuone]";
    }

    protected boolean hasArguments() {
        return (cmd.hasOption("n") && cmd.hasOption("b"));
    }

    protected void getLocalOptions(Options options) {
        options.addOption("n", "name", true, "specify name of new Identity");
        options.addOption("w", "allow", true, "specify alias of owner of new namespace");
        options.addOption("r", "defaultrepository", true, "Identity's default Repository");
        options.addOption("i", "defaultsigner", true, "Identity's default Interactive Signer");
        options.addOption("l", "defaultlogger", true, "Identity's default Logging Service");
        options.addOption("b", "defaultreceiver", true, "Identity's default Receiver");
    }


    private final String identity;
    private final PublicKeySource pksource;
}
