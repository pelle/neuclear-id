/*
 * $Id: DemoSigningServlet.java,v 1.3 2003/09/23 19:16:29 pelle Exp $
 * $Log: DemoSigningServlet.java,v $
 * Revision 1.3  2003/09/23 19:16:29  pelle
 * Changed NameSpace to Identity.
 * To cause less confusion in the future.
 *
 * Revision 1.2  2003/09/22 19:24:02  pelle
 * More fixes throughout to problems caused by renaming.
 *
 * Revision 1.1.1.1  2003/09/19 14:41:32  pelle
 * First import into the neuclear project. This was originally under the SF neudist
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
 * Moved the SignerStore's into xml-sig
 *
 * Revision 1.5  2003/02/14 21:10:36  pelle
 * The email sender works. The LogSender and the SoapSender should work but havent been tested yet.
 * The NamedObject has a new log() method that logs it's contents at it's parent Identity's logger.
 * The Identity object also has a new method send() which allows one to send a named object to the Identity's
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
 * There is now a JCESignerStore which uses a JCE KeyStore for signing.
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
 * NamedObject to a user. We may want to use XSL instead.
 * Also made the signing webapp look a bit nicer.
 *
 * Revision 1.5  2002/09/23 15:09:18  pelle
 * Got the SimpleSignerStore working properly.
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

import org.neuclear.id.InvalidIdentityException;
import org.neuclear.id.NSTools;
import org.neuclear.id.Identity;
import org.neudist.crypto.signerstores.SignerStore;
import org.neudist.crypto.signerstores.SimpleSignerStore;
import org.neudist.utils.NeudistException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;

public class DemoSigningServlet extends SigningServlet {

    private void buildTree() throws GeneralSecurityException, NeudistException, IOException {
        System.out.println("NEUDIST: Creating Identity Tree");
        kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(2048, new SecureRandom("Cartagena".getBytes()));

        PrivateKey signer = getTestKey();
        createNS("/test/one", "password", signer);
        createNS("/test/two", "password", signer);
    }

    private RSAPrivateKey getTestKey() throws GeneralSecurityException, IOException {
        KeyStore ks = KeyStore.getInstance("Uber");
        FileInputStream in = new FileInputStream(context.getRealPath("/WEB-INF/testkeys.ks"));
        ks.load(in, "neuclear".toCharArray());
        return (RSAPrivateKey) ks.getKey("neu://test", "neuclear".toCharArray());
    }

    private void createNS(String name, String newPassword, PrivateKey signer) throws IOException, NeudistException, GeneralSecurityException {
        name = NSTools.normalizeNameURI(name);
        System.out.println("NEUDIST: Generating key and Identity for: " + name);
        KeyPair kp = kpg.generateKeyPair();
        ((SimpleSignerStore) getKeyStore()).addKey(name, newPassword.toCharArray(), kp.getPrivate());
        System.out.println("NEUDIST: Creating Identity");
        Identity ns = new Identity(name, kp.getPublic(), "http://neuclear.org:8080/neudistframework/Store", "http://neuclear.org:8080/neudistframework/Signer", "http://neuclear.org:8080/neudistframework/Logger", "");//TODO Fix these values
//        id.addTarget(new TargetReference(id,,"store"));
        System.out.println("NEUDIST: Signing");
        ns.sign(signer);

        try {
            System.out.println("NEUDIST: Storing");
//            id.store();
//            getStore().receive(id);//Test locally first
            ns.sendObject();
        } catch (InvalidIdentityException e) {
            System.out.println("NEUDIST: Identity Error: " + e.getLocalizedMessage());
        }
    }

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try {
            buildTree();
        } catch (GeneralSecurityException e) {
            e.printStackTrace(System.out);
        } catch (IOException e) {
            e.printStackTrace(System.out);
        } catch (NeudistException e) {
            e.printStackTrace(System.out);
        }
    }

    protected static SignerStore getKeyStore(File keyStoreFile, Object kspassword) throws GeneralSecurityException, IOException, NeudistException {
        return new SimpleSignerStore(keyStoreFile);
    }

    private KeyPairGenerator kpg;

}
