package org.neuclear.source;

/**
 * (C) 2003 Antilles Software Ventures SA
 * User: pelleb
 * Date: Feb 10, 2003
 * Time: 8:35:33 PM
 * $Id: HttpSource.java,v 1.10 2003/12/04 21:50:36 pelle Exp $
 * $Log: HttpSource.java,v $
 * Revision 1.10  2003/12/04 21:50:36  pelle
 * Mainly documentation changes.
 *
 * Revision 1.9  2003/11/21 04:45:14  pelle
 * EncryptedFileStore now works. It uses the PBECipher with DES3 afair.
 * Otherwise You will Finaliate.
 * Anything that can be final has been made final throughout everyting. We've used IDEA's Inspector tool to find all instance of variables that could be final.
 * This should hopefully make everything more stable (and secure).
 *
 * Revision 1.8  2003/11/11 21:18:44  pelle
 * Further vital reshuffling.
 * org.neudist.crypto.* and org.neudist.utils.* have been moved to respective areas under org.neuclear.commons
 * org.neuclear.signers.* as well as org.neuclear.passphraseagents have been moved under org.neuclear.commons.crypto as well.
 * Did a bit of work on the Canonicalizer and changed a few other minor bits.
 *
 * Revision 1.7  2003/11/09 03:27:19  pelle
 * More house keeping and shuffling about mainly pay
 *
 * Revision 1.6  2003/11/05 18:50:34  pelle
 * Refactored org.neuclear.signers.source.Source and implementing classes to provide support for a local filesystem cache.
 * Also added Unit tests to make sure it actually works and modified IdentityCreator to write directly to the cache if no output filename is given.
 *
 * Revision 1.5  2003/10/21 22:31:13  pelle
 * Renamed NeudistException to NeuClearException and moved it to org.neuclear.commons where it makes more sense.
 * Unhooked the XMLException in the xmlsig library from NeuClearException to make all of its exceptions an independent hierarchy.
 * Obviously had to perform many changes throughout the code to support these changes.
 * <p/>
 * Revision 1.4  2003/09/26 23:53:10  pelle
 * Changes mainly in receiver and related fun.
 * First real neuclear stuff in the payment package. Added TransferContract and AssetControllerReceiver.
 * <p/>
 * Revision 1.3  2003/09/24 23:56:49  pelle
 * Refactoring nearly done. New model for creating signed objects.
 * With view for supporting the xmlpull api shortly for performance reasons.
 * Currently still uses dom4j but that has been refactored out that it
 * should now be very quick to implement a xmlpull implementation.
 * <p/>
 * A side benefit of this is that the API has been further simplified. I still have some work
 * todo with regards to cleaning up some of the outlying parts of the code.
 * <p/>
 * Revision 1.2  2003/09/22 19:24:02  pelle
 * More fixes throughout to problems caused by renaming.
 * <p/>
 * Revision 1.1.1.1  2003/09/19 14:41:49  pelle
 * First import into the neuclear project. This was originally under the SF neuclear
 * project. This marks a general major refactoring and renaming ahead.
 * <p/>
 * The new name for this code is NeuClear Identity and has the general package header of
 * org.neuclear.id
 * There are other areas within the current code which will be split out into other subprojects later on.
 * In particularly the signers will be completely seperated out as well as the contract types.
 * <p/>
 * <p/>
 * Revision 1.2  2003/02/18 14:57:30  pelle
 * Finished Cleaning up Receivers and Stores.
 * Also updated nsdl.xsd xml schema with latest changes.
 * The whole API is now very simple.
 * <p/>
 * Revision 1.1  2003/02/14 05:10:13  pelle
 * New Source model is implemented.
 * It doesnt quite verify things correctly yet. I'm not yet sure why.
 * CommandLineSigner is simplified to make it easier to use.
 */

import org.neuclear.commons.NeuClearException;
import org.neuclear.id.NSTools;
import org.neuclear.id.SignedNamedObject;

import java.io.InputStream;
import java.net.URL;

/**
 * Implementation of Source that uses HTTP to fetch an XMLStream from a repository on a web server.
 * <p/>
 * The following points apply to storing the XML on the web site:
 * <dl>
 * <dt>The Endpoint provided should be the root of the repository of the webserver.</dt>
 * <dd>Such as <tt>http://repository.neuclear.org</tt> and not <tt>http://repository.neuclear.org/test/bob@</tt>
 * <dt>The files are to be stored like this on the web server:</dt>
 * <dd>
 * <table border="2"><tr><th>NeuClear ID</th><th>Relative path to root of repository on web server</th></tr>
 * <tr><td><tt>neu://</tt></td><td>./root.id</td></tr>
 * <tr><td><tt>neu://test</tt></td><td>./test/root.id</td></tr>
 * <tr><td><tt>neu://bob@test</tt></td><td>./test/bob@/root.id</td></tr>
 * <tr><td><tt>neu://test/alice</tt></td><td>./test/alice/root.id</td></tr>
 * <tr><td><tt>neu://bob@test/funds</tt></td><td>./test/bob@/funds/root.id</td></tr>
 * </table>
 * </dd>
 * </dl>
 */
public final class HttpSource extends Source {

    protected final InputStream getStream(final String endpoint, final String name) throws NeuClearException {
        try {
            final String urlstring = endpoint + NSTools.url2path(name);
            final URL url = new URL(urlstring);

            return url.openStream();
        } catch (java.io.IOException e) {
            throw new NeuClearException(e);
        }
    }

    public static void main(final String[] args) {
        final Source source = new HttpSource();
        try {
            final SignedNamedObject obj = source.fetch("http://repository.neuclear.org", "/pelle");
            System.out.println("Got: " + obj.getName());

        } catch (NeuClearException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        }
    }

}
