package org.neuclear.source;

import org.dom4j.io.SAXReader;
import org.neuclear.id.NSTools;
import org.neuclear.id.SignedNamedObject;
import org.neuclear.id.verifier.VerifyingReader;
import org.neuclear.commons.NeuClearException;
import org.neudist.xml.XMLException;

import java.net.URL;

/**
 * (C) 2003 Antilles Software Ventures SA
 * User: pelleb
 * Date: Feb 10, 2003
 * Time: 8:35:33 PM
 * $Id: HttpSource.java,v 1.5 2003/10/21 22:31:13 pelle Exp $
 * $Log: HttpSource.java,v $
 * Revision 1.5  2003/10/21 22:31:13  pelle
 * Renamed NeudistException to NeuClearException and moved it to org.neuclear.commons where it makes more sense.
 * Unhooked the XMLException in the xmlsig library from NeuClearException to make all of its exceptions an independent hierarchy.
 * Obviously had to perform many changes throughout the code to support these changes.
 *
 * Revision 1.4  2003/09/26 23:53:10  pelle
 * Changes mainly in receiver and related fun.
 * First real neuclear stuff in the payment package. Added TransferContract and PaymentReceiver.
 *
 * Revision 1.3  2003/09/24 23:56:49  pelle
 * Refactoring nearly done. New model for creating signed objects.
 * With view for supporting the xmlpull api shortly for performance reasons.
 * Currently still uses dom4j but that has been refactored out that it
 * should now be very quick to implement a xmlpull implementation.
 *
 * A side benefit of this is that the API has been further simplified. I still have some work
 * todo with regards to cleaning up some of the outlying parts of the code.
 *
 * Revision 1.2  2003/09/22 19:24:02  pelle
 * More fixes throughout to problems caused by renaming.
 *
 * Revision 1.1.1.1  2003/09/19 14:41:49  pelle
 * First import into the neuclear project. This was originally under the SF neudist
 * project. This marks a general major refactoring and renaming ahead.
 *
 * The new name for this code is NeuClear Identity and has the general package header of
 * org.neuclear.id
 * There are other areas within the current code which will be split out into other subprojects later on.
 * In particularly the signers will be completely seperated out as well as the contract types.
 *
 *
 * Revision 1.2  2003/02/18 14:57:30  pelle
 * Finished Cleaning up Receivers and Stores.
 * Also updated nsdl.xsd xml schema with latest changes.
 * The whole API is now very simple.
 *
 * Revision 1.1  2003/02/14 05:10:13  pelle
 * New Source model is implemented.
 * It doesnt quite verify things correctly yet. I'm not yet sure why.
 * CommandLineSigner is simplified to make it easier to use.
 *
 */
public class HttpSource extends Source {
    public HttpSource() {
        reader = new SAXReader();
    }

    public SignedNamedObject fetch(String endpoint, String name) throws NeuClearException {
        try {
            String urlstring = endpoint + NSTools.url2path(name);
            URL url = new URL(urlstring);

            return VerifyingReader.getInstance().read(url.openStream());
        } catch (java.io.IOException e) {
            throw new NeuClearException(e);
        } catch (XMLException e) {
            throw new NeuClearException(e);
        }
    }

    public static void main(String args[]) {
        Source source = new HttpSource();
        try {
            SignedNamedObject obj = source.fetch("http://repository.neuclear.org", "/pelle");
            System.out.println("Got: " + obj.getName());

        } catch (NeuClearException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        }
    }

    private SAXReader reader;
}
