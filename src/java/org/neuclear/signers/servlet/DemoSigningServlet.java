/*
 * $Id: DemoSigningServlet.java,v 1.16 2003/12/16 23:17:06 pelle Exp $
 * $Log: DemoSigningServlet.java,v $
 * Revision 1.16  2003/12/16 23:17:06  pelle
 * Work done on the SigningServlet. The two phase web model is now only an option.
 * Allowing much quicker signing, using the GuiDialogueAgent.
 * The screen has also been cleaned up and displays the xml to be signed.
 * The GuiDialogueAgent now optionally remembers passphrases and has a checkbox to support this.
 * The PassPhraseAgent's now have a UserCancelsException, which allows the agent to tell the application if the user specifically
 * cancels the signing process.
 *
 * Revision 1.15  2003/12/15 14:38:30  pelle
 * Added EnsureHostRequestFilter to commons, to only allow requests from a particular IP
 * Added a method to optionally show the passphrase box in the SigningServlet. As the default SigningServlet
 * is intended to be used with a gui passphrase agent, we dont want to display it.
 * The DemoSigningServlet does display the dialogue.
 * Added the new neuclear-signer package, which is a standalone web signer using Jetty. The project runs
 * when built with "maven javaapp". More testing needs to be done as well as a startup wizard.
 *
 * Revision 1.14  2003/12/14 20:53:04  pelle
 * Added ServletPassPhraseAgent which uses ThreadLocal to transfer the passphrase to the signer.
 * Added ServletSignerFactory, which builds Signers for use within servlets based on parameters in the Servlets
 * Init parameters in web.xml
 * Updated SQLContext to use ThreadLocal
 * Added jakarta cactus unit tests to neuclear-commons to test the 2 new features above.
 * Added use of the new features in neuclear-commons to the servilets within neuclear-id and added
 * configuration parameters in web.xml
 *
 * Revision 1.13  2003/12/12 19:28:03  pelle
 * All the Cactus tests now for signing servlet.
 * Added working AuthenticationFilterTest
 * Returned original functionality to DemoSigningServlet.
 * This is set up to use the test keys stored in neuclear-commons.
 * SigningServlet should now work for general use. It uses the default
 * keystore. Will add configurability later. It also uses the GUIDialogAgent.
 *
 * Revision 1.12  2003/12/11 23:57:29  pelle
 * Trying to test the ReceiverServlet with cactus. Still no luck. Need to return a ElementProxy of some sort.
 * Cleaned up some missing fluff in the ElementProxy interface. getTagName(), getQName() and getNameSpace() have been killed.
 *
 * Revision 1.11  2003/12/10 23:58:52  pelle
 * Did some cleaning up in the builders
 * Fixed some stuff in IdentityCreator
 * New maven goal to create executable jarapp
 * We are close to 0.8 final of ID, 0.11 final of XMLSIG and 0.5 of commons.
 * Will release shortly.
 *
 * Revision 1.10  2003/11/21 04:45:14  pelle
 * EncryptedFileStore now works. It uses the PBECipher with DES3 afair.
 * Otherwise You will Finaliate.
 * Anything that can be final has been made final throughout everyting. We've used IDEA's Inspector tool to find all instance of variables that could be final.
 * This should hopefully make everything more stable (and secure).
 *
 * Revision 1.9  2003/11/15 01:58:16  pelle
 * More work all around on web applications.
 *
 * Revision 1.8  2003/11/11 21:18:44  pelle
 * Further vital reshuffling.
 * org.neudist.crypto.* and org.neudist.utils.* have been moved to respective areas under org.neuclear.commons
 * org.neuclear.signers.* as well as org.neuclear.passphraseagents have been moved under org.neuclear.commons.crypto as well.
 * Did a bit of work on the Canonicalizer and changed a few other minor bits.
 *
 * Revision 1.7  2003/10/29 21:16:27  pelle
 * Refactored the whole signing process. Now we have an interface called Signer which is the old SignerStore.
 * To use it you pass a byte array and an alias. The sign method then returns the signature.
 * If a Signer needs a passphrase it uses a PassPhraseAgent to present a dialogue box, read it from a command line etc.
 * This new Signer pattern allows us to use secure signing hardware such as N-Cipher in the future for server applications as well
 * as SmartCards for end user applications.
 *
 * Revision 1.6  2003/10/21 22:31:13  pelle
 * Renamed NeudistException to NeuClearException and moved it to org.neuclear.commons where it makes more sense.
 * Unhooked the XMLException in the xmlsig library from NeuClearException to make all of its exceptions an independent hierarchy.
 * Obviously had to perform many changes throughout the code to support these changes.
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
 * Revision 1.3  2003/09/23 19:16:29  pelle
 * Changed NameSpace to Identity.
 * To cause less confusion in the future.
 *
 * Revision 1.2  2003/09/22 19:24:02  pelle
 * More fixes throughout to problems caused by renaming.
 *
 * Revision 1.1.1.1  2003/09/19 14:41:32  pelle
 * First import into the neuclear project. This was originally under the SF neuclear
 * project. This marks a general major refactoring and renaming ahead.
 *
 * The new name for this code is NeuClear Identity and has the general package header of
 * org.neuclear.id
 * There are other areas within the current code which will be split out into other subprojects later on.
 * In particularly the signers will be completely seperated out as well as the contract types.
 *
 *
 * Revision 1.8  2003/02/18 14:57:23  pelle
 * Finished Cleaning up Receivers and Stores.
 * Also updated nsdl.xsd xml schema with latest changes.
 * The whole API is now very simple.
 *
 * Revision 1.7  2003/02/18 13:58:41  pelle
 * *** empty log message ***
 *
 * Revision 1.6  2003/02/18 00:06:15  pelle
 * Moved the Signer's into xml-sig
 *
 * Revision 1.5  2003/02/14 21:10:36  pelle
 * The email sender works. The LogSender and the SoapSender should work but havent been tested yet.
 * The SignedNamedObject has a new log() method that logs it's contents at it's parent Identity's logger.
 * The Identity object also has a new method receive() which allows one to receive a named object to the Identity's
 * default receiver.
 *
 * Revision 1.4  2003/02/14 14:04:59  pelle
 * The New Source Classes work and NS resolution works as well.
 * I've renamed Target to TargetReference to prepare for the other main refactoring ahead. Implementation of
 * Senders.
 *
 * Revision 1.3  2003/02/10 22:30:15  pelle
 * Got rid of even further dependencies. In Particular OSCore
 *
 * Revision 1.2  2003/02/09 00:15:55  pelle
 * Fixed things so they now compile with r_0.7 of XMLSig
 *
 * Revision 1.1  2002/10/06 00:39:29  pelle
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
 * Revision 1.8  2002/10/02 21:03:45  pelle
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
 * Revision 1.7  2002/09/29 00:22:10  pelle
 * Several cosmetic changes.
 * First attempt at a new CommandLine tool for signing and creating namespace files.
 * This will be used by people to create requests for namespaces.
 *
 * Revision 1.6  2002/09/25 19:20:15  pelle
 * Added various new schemas and updated most of the existing ones.
 * Added explanation interface for explaining the purpose of a
 * SignedNamedObject to a user. We may want to use XSL instead.
 * Also made the signing webapp look a bit nicer.
 *
 * Revision 1.5  2002/09/23 15:09:18  pelle
 * Got the SimpleSigner working properly.
 * I couldn't get SealedObjects working with BouncyCastle's Symmetric keys.
 * Don't know what I was doing, so I reimplemented it. Encrypting
 * and decrypting it my self.
 *
 * Revision 1.4  2002/09/21 23:11:16  pelle
 * A bunch of clean ups. Got rid of as many hard coded URL's as I could.
 *
 * User: pelleb
 * Date: Sep 19, 2002
 * Time: 5:18:18 PM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package org.neuclear.signers.servlet;

import org.neuclear.commons.NeuClearException;
import org.neuclear.commons.Utility;
import org.neuclear.commons.crypto.passphraseagents.PassPhraseAgent;
import org.neuclear.commons.crypto.passphraseagents.ServletPassPhraseAgent;
import org.neuclear.commons.crypto.signers.Signer;
import org.neuclear.commons.crypto.signers.TestCaseSigner;
import org.neuclear.commons.crypto.Base64;
import org.neuclear.xml.XMLException;
import org.neuclear.id.SignatureRequest;

import javax.servlet.ServletConfig;
import javax.servlet.SingleThreadModel;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.security.GeneralSecurityException;

public final class DemoSigningServlet extends SigningServlet {
    public DemoSigningServlet(){
        agent=new ServletPassPhraseAgent();
    }
    protected Signer createSigner(ServletConfig config) throws GeneralSecurityException, NeuClearException {
        agent.set("neuclear");
        final TestCaseSigner signerd = new TestCaseSigner(agent);
        agent.clear();
        return signerd;
    }

    protected void handleInputStream(InputStream is, HttpServletRequest request, HttpServletResponse response) throws IOException, NeuClearException, XMLException {
        agent.setRequest(request);
        super.handleInputStream(is, request, response);
        agent.clear();
    }
    protected boolean isReadyToSign(HttpServletRequest request) {
        return !Utility.isEmpty(request.getParameter("sign"));
    }
    protected void printSecondStageForm(HttpServletRequest request, final PrintWriter out, SignatureRequest sigreq, final String endpoint) {
        out.println("<table><tr><td ><h4>Do you wish to sign this?</h4></td></tr>");
        out.print("<tr><td><form action=\"");
        out.print(request.getRequestURL());
        out.print("\" method=\"POST\"><input name=\"neuclear-request\" value=\"");
        out.print(Base64.encode(sigreq.getEncoded().getBytes()));
        out.print("\" type=\"hidden\">\n <input name=\"endpoint\" value=\"");
        out.print(endpoint);
        out.println("\" type=\"hidden\"/>\n");
        out.println("Passphrase: <input name=\"passphrase\" type=\"password\" size=\"40\">");
        out.println(" <input type=\"submit\" name=\"sign\" value=\"Sign\"></form></td></tr></table>");
    }

     private final ServletPassPhraseAgent agent;
}
