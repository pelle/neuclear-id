/* $Id: CommandLineSigner.java,v 1.10 2004/02/19 15:30:21 pelle Exp $
 * $Log: CommandLineSigner.java,v $
 * Revision 1.10  2004/02/19 15:30:21  pelle
 * Various cleanups and corrections
 *
 * Revision 1.9  2004/02/18 00:14:31  pelle
 * Many, many clean ups. I've readded Targets in a new method.
 * Gotten rid of NamedObjectBuilder and revamped Identity and Resolvers
 *
 * Revision 1.8  2004/01/19 23:49:44  pelle
 * Unit testing uncovered further issues with Base32
 * NSTools is now uptodate as are many other classes. All transactional builders habe been updated.
 * Well on the way towards full "green" on Junit.
 *
 * Revision 1.7  2003/12/22 22:15:26  pelle
 * Last minute cleanups and documentation prior to release 0.8.1
 *
 * Revision 1.6  2003/12/19 18:03:34  pelle
 * Revamped a lot of exception handling throughout the framework, it has been simplified in most places:
 * - For most cases the main exception to worry about now is InvalidNamedObjectException.
 * - Most lowerlevel exception that cant be handled meaningful are now wrapped in the LowLevelException, a
 *   runtime exception.
 * - Source and Store patterns each now have their own exceptions that generalizes the various physical
 *   exceptions that can happen in that area.
 *
 * Revision 1.5  2003/12/19 00:31:30  pelle
 * Lots of usability changes through out all the passphrase agents and end user tools.
 *
 * Revision 1.4  2003/12/18 17:40:19  pelle
 * You can now create keys that get stored with a X509 certificate in the keystore. These can be saved as well.
 * IdentityCreator has been modified to allow creation of keys.
 * Note The actual Creation of Certificates still have a problem that will be resolved later today.
 *
 * Revision 1.3  2003/12/12 00:13:11  pelle
 * This may actually work now. Need to put a few more test cases in to make sure.
 *
 * Revision 1.2  2003/12/10 23:58:51  pelle
 * Did some cleaning up in the builders
 * Fixed some stuff in IdentityCreator
 * New maven goal to create executable jarapp
 * We are close to 0.8 final of ID, 0.11 final of XMLSIG and 0.5 of commons.
 * Will release shortly.
 *
 * Revision 1.1  2003/12/09 23:41:44  pelle
 * IdentityCreator is now the default class of the uber jar.
 * It has many new features such as:
 * - Self signed certificates
 * - Unsigned Certificates (for external signing)
 * - Signing of Externally generated Certificates
 * - Command Line verification of an Identity name
 *
 * CachedSource now supports freshness. It needs to be tested a bit more thoroughly
 * though.
 *
 * Documentation including the bdg has been updated to reflect these changes.
 *
 * Revision 1.14  2003/11/21 04:45:13  pelle
 * EncryptedFileStore now works. It uses the PBECipher with DES3 afair.
 * Otherwise You will Finaliate.
 * Anything that can be final has been made final throughout everyting. We've used IDEA's Inspector tool to find all instance of variables that could be final.
 * This should hopefully make everything more stable (and secure).
 *
 * Revision 1.13  2003/11/13 23:26:42  pelle
 * The signing service and web authentication application is now almost working.
 *
 * Revision 1.12  2003/11/11 21:18:43  pelle
 * Further vital reshuffling.
 * org.neudist.crypto.* and org.neudist.utils.* have been moved to respective areas under org.neuclear.commons
 * org.neuclear.signers.* as well as org.neuclear.passphraseagents have been moved under org.neuclear.commons.crypto as well.
 * Did a bit of work on the Canonicalizer and changed a few other minor bits.
 *
 * Revision 1.11  2003/10/31 23:58:53  pelle
 * The IdentityCreator now fully works with the new Signer architecture.
 *
 * Revision 1.10  2003/10/29 21:16:27  pelle
 * Refactored the whole signing process. Now we have an interface called Signer which is the old SignerStore.
 * To use it you pass a byte array and an alias. The sign method then returns the signature.
 * If a Signer needs a passphrase it uses a PassPhraseAgent to present a dialogue box, read it from a command line etc.
 * This new Signer pattern allows us to use secure signing hardware such as N-Cipher in the future for server applications as well
 * as SmartCards for end user applications.
 *
 * Revision 1.9  2003/10/28 23:44:35  pelle
 * The GuiDialogAgent now works. It simply presents itself as a simple modal dialog box asking for a passphrase.
 * The two Signer implementations both use it for the passphrase.
 *
 * Revision 1.8  2003/10/25 00:39:54  pelle
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
 * First import into the neuclear project. This was originally under the SF neuclear
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
 * The Identity object also has a new method receive() which allows one to receive a named object to the Identity's
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
package org.neuclear.id.tools.commandline;

import org.dom4j.Document;
import org.neuclear.commons.LowLevelException;
import org.neuclear.commons.NeuClearException;
import org.neuclear.commons.Utility;
import org.neuclear.commons.crypto.CryptoTools;
import org.neuclear.commons.crypto.passphraseagents.ConsoleAgent;
import org.neuclear.commons.crypto.passphraseagents.GuiDialogAgent;
import org.neuclear.commons.crypto.passphraseagents.InteractiveAgent;
import org.neuclear.commons.crypto.passphraseagents.UserCancellationException;
import org.neuclear.commons.crypto.signers.DefaultSigner;
import org.neuclear.commons.crypto.signers.InvalidPassphraseException;
import org.neuclear.commons.crypto.signers.NonExistingSignerException;
import org.neuclear.commons.crypto.signers.Signer;
import org.neuclear.commons.time.TimeTools;
import org.neuclear.id.Identity;
import org.neuclear.id.builders.Builder;
import org.neuclear.id.resolver.NSResolver;
import org.neuclear.xml.XMLException;
import org.neuclear.xml.XMLTools;

import java.io.*;

/**
 * @author pelleb
 * @version $Revision: 1.10 $
 */
public class CommandLineSigner {
    private final String executable;

    public CommandLineSigner(final String[] args) throws UserCancellationException {
        CryptoTools.ensureProvider();
        executable = Utility.getExecutable(getClass());
        options = createOptions();
        cmd = parseOptions(args);
        checkArguments();
        if (cmd.hasOption('v')) {
            String name = cmd.getOptionValue('v');
            System.out.println("Resolving and Verifying: " + name);
            try {
                Identity id = NSResolver.resolveIdentity(name);
                if (id != null) {
                    System.out.println("Signed Object: " + id.getName() + " is verified");
                    System.out.println("was signed at: " + TimeTools.formatTimeStamp(id.getTimeStamp()));
                    System.out.println("Is of type: " + id.getClass().getName());
                    System.out.println("signer: " + id.getSigner());
                } else {
                    System.out.println("Couldnt Resolve or Verify the object.");
                }
            } catch (NeuClearException e) {
                System.out.println("Couldnt Resolve or Verify the object.");
            }

            System.exit(0);
        }
//        agent=(PassPhraseAgent)Configuration.getComponent(PassPhraseAgent.class,"neuclear-id");
        final InteractiveAgent agent = cmd.hasOption('g') ? (InteractiveAgent) new GuiDialogAgent() : new ConsoleAgent();
        sig = createSigner(agent);
        alias = cmd.getOptionValue("a");
        of = cmd.getOptionValue("o");
        if (Utility.isEmpty(of) && cmd.hasOption('i')) {
            of = cmd.getOptionValue('i') + ".id";
        }
    }

    private final CommandLine parseOptions(final String[] args) {
        final CommandLineParser clparser = new GnuParser();
        try {
            return clparser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getLocalizedMessage());
            printHelp();
            System.exit(1);
        }
        return null;
    }

    private DefaultSigner createSigner(final InteractiveAgent agent) throws UserCancellationException {
        try {
            return new DefaultSigner(agent);
        } catch (InvalidPassphraseException e) {
            return createSigner(agent);
        }
    }

    public static void main(final String[] args) {
        try {
            System.out.println();
            final CommandLineSigner signer = new CommandLineSigner(args);
            signer.execute();
        } catch (UserCancellationException e) {
            System.out.println("Bye");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final void checkArguments() {
        if (!hasArguments() || cmd.hasOption('h')) {
            printHelp();
            System.exit(1);
        }
    }

    private void printHelp() {
        final HelpFormatter help = new HelpFormatter();
        help.setDescPadding(10);
        help.printHelp("\n" + executable + getExtraHelp() +
                "  [--outputfile signed/test.id] \n" +
                executable + " --verify neu://neuclear.org\n" +
                executable + " --inputfile joe@yourdomain.xml \n", options);
    }

    protected String getExtraHelp() {
        return " --inputfile unsigned/test.id";
    }

    protected boolean hasArguments() {
        return cmd.hasOption("i") || cmd.hasOption('v');
    }

    public final void execute() throws UserCancellationException {
        final Builder subject = build();

        try {
            if (!sig.canSignFor(alias)) {
                if (!Utility.isEmpty(of))
                    of = "signthis.xml";
                System.out.println("Key with alias: " + alias + " doesnt exist in our keystore. \nSaving unsigned Identity as: " + of);
            } else if (!subject.isSigned()) {
                System.out.println("Signing by " + alias + " ...");
                subject.sign(alias, sig);
            }

            OutputStream dest = System.out;
            if (!Utility.isEmpty(of)) {
                final File outFile = new File(of);
                if (outFile.getParentFile() != null)
                    outFile.getParentFile().mkdirs();
                dest = new FileOutputStream(of);
                System.out.println("Outputting to: " + of);
            }
            XMLTools.writeFile(dest, subject.getElement());
            System.out.println();
            System.out.println("You now need to copy the file: " + of + " to your webserver so it is visible at a given location");

//            System.out.println("\nOnce this is done you will be able to verify your new Identity like this:");
//            System.out.println(executable+" -v "+subject.getName());
/*  We need to be able to send an unsigned object before I can enable this
            if (!sig.canSignFor(alias)) {
                System.out.println("Do you wish to send the contract to the signer of "+alias+"?");
                if (Utility.getAffirmative(true)){
                    try {
                        Identity id=NSResolver.resolveIdentity(alias);
                        id.receive(subject);
                    } catch (NameResolutionException e) {
                        System.err.println(e.getLocalizedMessage());
                    } catch (InvalidNamedObjectException e) {
                        System.err.println(e.getLocalizedMessage());
                    }
                }
            }
*/
        } catch (NonExistingSignerException e) {
            throw new LowLevelException(e);
        } catch (FileNotFoundException e) {
            System.err.println("Couldnt find file: " + of);
            System.exit(1);
        } catch (XMLException e) {
            System.err.println("Error generatoing xml file: " + of + "\n" + e.getLocalizedMessage());
            System.exit(1);
        }

    }

    protected Builder build() throws UserCancellationException {
        final String sf = cmd.getOptionValue("i");
        Builder subject = null;
        try {
            InputStream source = System.in;
            if (!Utility.isEmpty(sf)) {
                source = new FileInputStream(sf);
                if (Utility.isEmpty(of)) {
                    int loc = sf.lastIndexOf(".");
                    of = sf.substring(0, loc) + ".id";
                }
            }
            final Document doc = XMLTools.loadDocument(source);
            subject = new Builder(doc.getRootElement());

            if (!sig.canSignFor(alias)) {
                System.err.println("You can not sign as " + alias + " with your current keystore.");
                System.exit(1);
            }

            System.out.println("You are about to sign the following Contract. Please make sure that is what you want.");
            System.out.println("Type: " + subject.getElement().getName());
            System.out.println("Raw XML:\n===================");
            System.out.println(subject.asXML());
            System.out.print("===================\nAre you shure you wish to sign this? (y/N) ");

            if (!Utility.getAffirmative(false)) {
                System.out.println("Aborted Signing Process");
                System.exit(0);
            }

            return subject;
        } catch (FileNotFoundException e) {
            System.err.println("Couldnt find file: " + sf);
            System.exit(1);
        } catch (XMLException e) {
            System.err.println("Error parsing file: " + sf + "\n" + e.getLocalizedMessage());
            System.exit(1);
        }
        return null;
    }

    private Options createOptions() {
        // create Options object
        final Options options = new Options();
        options.addOption(new Option("o", "outputfile", true, "specify output file \n[ --outputfile bob.id ]"));
        options.addOption(new Option("i", "inputfile", true, "specify Input File \n[ --inputfile bob.xml ]"));
        options.addOption(new Option("v", "verify", true, "Specify NEU ID to verify \n[ --verify neu://bob@yourdomain.com ]"));
        options.addOption(new Option("h", "help", false, "Help"));
        options.addOption(new Option("g", "gui", false, "Use GUI Passphrase Dialog"));
        getLocalOptions(options);


        return options;
    }

    protected void getLocalOptions(final Options options) {
    }

    protected final CommandLine cmd;
    protected final Options options;
    protected final Signer sig;
    protected String alias;
    protected String of;

}
