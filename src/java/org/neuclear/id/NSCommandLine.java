/*
 * $Id: NSCommandLine.java,v 1.2 2003/09/23 19:16:27 pelle Exp $
 * $Log: NSCommandLine.java,v $
 * Revision 1.2  2003/09/23 19:16:27  pelle
 * Changed NameSpace to Identity.
 * To cause less confusion in the future.
 *
 * Revision 1.1.1.1  2003/09/19 14:40:59  pelle
 * First import into the neuclear project. This was originally under the SF neudist
 * project. This marks a general major refactoring and renaming ahead.
 *
 * The new name for this code is NeuClear Identity and has the general package header of
 * org.neuclear.id
 * There are other areas within the current code which will be split out into other subprojects later on.
 * In particularly the signers will be completely seperated out as well as the contract types.
 *
 *
 * Revision 1.4  2003/02/10 22:30:04  pelle
 * Got rid of even further dependencies. In Particular OSCore
 *
 * Revision 1.3  2003/02/09 00:15:52  pelle
 * Fixed things so they now compile with r_0.7 of XMLSig
 *
 * Revision 1.2  2003/01/16 22:20:02  pelle
 * First Draft of new generalised Ledger Interface.
 * Currently we have a Book and Transaction class.
 * We also need a Ledger class and a Ledger Factory.
 *
 * Revision 1.1.1.1  2002/09/18 10:55:40  pelle
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
 * Revision 1.4  2002/06/18 03:04:11  pelle
 * Just added all the necessary jars.
 * Fixed a few things in the framework and
 * started a GUI Application to manage Neu's.
 *
 * Revision 1.3  2002/06/17 20:48:33  pelle
 * The NS functionality should now work. FileStore is working properly.
 * The example .id objects in the neuspace folder have been updated with the
 * latest version of the format.
 * "neuspace/root.id" should now be considered the universal parent of the
 * neuclear system.
 * Still more to go, but we're getting there. I will now focus on a quick
 * Web interface. After which Contracts will be added.
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



public class NSCommandLine {

    public static void main(String args[]) {

        String name=null;
        String keystore="keys/demokeys.jks";
        String signer="root";
        String password="secret";
        String outputPath="neuspace/";
        String allowed[]=null;
        boolean enum=false;
        int j=0;
        if (args.length==0) {
            System.out.println("Usage: java org.neuclear.id.NSCommandLine [-ks <keystorename>] [-s <signeralias>] [-sp <keystorepassword>] [-op <outputpath>]\n       name [allowalias1 allowalias2 ...]");
            return;
        }
 /*       for (int i = 0; i < args.length; i++) {

            String arg = args[i];
            if (arg.equals("-ks")&&(i+1<args.length))
                keystore=args[++i];
            else if (arg.equals("-enum"))
                enum=true;
            else if (arg.equals("-s")&&(i+1<args.length))
                signer=args[++i];
            else if (arg.equals("-sp")&&(i+1<args.length))
                password=args[++i];
            else if (arg.equals("-op")&&(i+1<args.length))
                        outputPath=args[++i];
            else if (name==null)
                name=arg;

            else {
                if (allowed==null)
                    allowed=new String[args.length-i];
                 allowed[j++]=arg;
            }

        }

        try {
            Init.init();
            DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();
            KeyStore ks=KeyStore.getInstance("JKS");
            ks.load(new FileInputStream(new File(keystore)),password.toCharArray());
            Document doc=dbf.newDocumentBuilder().newDocument();

            String baseFile=name+".id";
            if (!name.startsWith("/"))
                name="/"+name;

            String baseURI = "neu:/"+name;

            Identity id=new Identity(baseURI,CryptoTools.getKeyPair(ks,signer,password.toCharArray()),
                    //CryptoTools.getKeyPair(ks,"bob",password.toCharArray()),
                    ks.getCertificate(allowed[0]).getPublicKey());
            Store st=new CachedStore(new FileStore(outputPath));
            st.receive(id);


//            System.out.println("Root pk:\n"+CryptoTools.formatKeyAsHex(ks.getCertificate("root").getPublicKey()));

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
*/

    }



}
