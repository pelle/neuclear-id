package org.neuclear.source;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.neuclear.id.NSTools;
import org.neuclear.id.NamedObject;
import org.neuclear.id.NamedObjectFactory;
import org.neuclear.utils.NeudistException;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * (C) 2003 Antilles Software Ventures SA
 * User: pelleb
 * Date: Feb 10, 2003
 * Time: 8:35:33 PM
 * $Id: HttpSource.java,v 1.1 2003/09/19 14:41:49 pelle Exp $
 * $Log: HttpSource.java,v $
 * Revision 1.1  2003/09/19 14:41:49  pelle
 * Initial revision
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
        reader=new SAXReader();
    }

    public NamedObject fetch(String endpoint, String name) throws NeudistException {
        try {
            String urlstring = endpoint+NSTools.url2path(name);
            URL url=new URL(urlstring);
            Document doc=reader.read(url);
            return NamedObjectFactory.createNamedObject(doc);
        } catch (DocumentException e) {
            throw new NeudistException(name+ " not found");
        } catch (MalformedURLException e) {
            throw new NeudistException(e);
        }
    }

    public static void main(String args[]){
        Source source=new HttpSource();
        try {
            NamedObject obj=source.fetch("http://repository.neuclear.org","/pelle");
            System.out.println("Got: "+obj.getName());
            System.out.println(obj.getElement().asXML());
        } catch (NeudistException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        }
    }
    private SAXReader reader;
}
