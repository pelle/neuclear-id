package org.neuclear.id.builders;

import org.dom4j.Element;
import org.neuclear.commons.crypto.Base32;
import org.neuclear.commons.crypto.Base64;
import org.neuclear.commons.crypto.CryptoTools;
import org.neuclear.id.InvalidNamedObjectException;
import org.neuclear.xml.xmlsec.KeyInfo;

import java.security.PublicKey;

/*
$Id: ServiceBuilder.java,v 1.3 2004/04/20 23:33:07 pelle Exp $
$Log: ServiceBuilder.java,v $
Revision 1.3  2004/04/20 23:33:07  pelle
All unit tests (junit and cactus) work. The AssetControllerServlet is operational.

Revision 1.2  2004/04/17 19:28:21  pelle
Identity is now fully html based as is the ServiceBuilder.
VerifyingReader correctly identifies html files and parses them as such.
Targets and Target now parse html link tags
AssetBuilder and ExchangeAgentBuilder have been updated to support it and provide html formatted contracts.
The Asset.Reader and ExchangeAgent.Reader still need to be updated.

Revision 1.1  2004/04/05 16:32:52  pelle
Created new ServiceBuilder class for creating services. A service is an identity that has a seperate service URL and Service Public Key.

*/

/**
 * User: pelleb
 * Date: Apr 5, 2004
 * Time: 9:28:55 AM
 */
public class ServiceBuilder extends IdentityBuilder {
    public ServiceBuilder(final String type, final String title, final String serviceUrl, PublicKey serviceKey) throws InvalidNamedObjectException {
        super(type);
        addTarget(serviceUrl, "controller");

        Element style = head.addElement("style");
        style.setText("th { background:#EEF;}\n" +
                "table {background:#CCC;font-size:small;border:1px;}\n" +
                "pre {font-size:xx-small}\n" +
                "td {vertical-align:top;border:thin;background:#eee}\n");
        body.addElement("h1").setText(title);
        description = body.addElement("div");
        description.addAttribute("id", "rules");

        body.addElement("h3").setText("Features");

        proptable = body.addElement("table");
        Element tr = proptable.addElement("tr");
        tr.addElement("th").setText("Type");
        tr.addElement("th").setText("Value");
        tr.addElement("th").setText("Description");

        body.addElement("h3").setText("Keys");
        pktable = body.addElement("table");
//        pktable.addAttribute("class","pktable");
        tr = pktable.addElement("tr");
//        tr.addAttribute("class","tablehead");
        tr.addElement("th").setText("Type");
        tr.addElement("th").setText("Key ID");
        tr.addElement("th").setText("PublicKey");


        body.addElement("h3").setText("Rules");
        rules = body.addElement("div");
        rules.addAttribute("id", "rules");
        addKeyInfo("controller.publickey", serviceKey, "Service Controllers Public Key");
        addFeature("controller.url", "Controller", serviceUrl, "The address of the service control server.");
    }

    protected void addKeyInfo(final String label, final PublicKey pub, final String description) {
        KeyInfo ki = new KeyInfo(pub);
        ki.getElement().addAttribute("id", label);
        ki.getElement().addAttribute("style", "display:none");
        body.add(ki.getElement());
        Element tr = pktable.addElement("tr");
//        tr.addAttribute("class","pktablehead");
        tr.addElement("td").setText(description);
        tr.addElement("td").addElement("pre").setText(Base32.encode(CryptoTools.digest(pub.getEncoded())));
        tr.addElement("td").addElement("pre").setText(Base64.encodeClean(pub.getEncoded()));
    }

    protected void addFeature(final String id, final String name, final String value, final String description) {
        Element tr = proptable.addElement("tr");
//        tr.addAttribute("class","pktablehead");
        tr.addElement("td").setText(name);
        final Element val = tr.addElement("td");
        val.setText(value);
        val.addAttribute("id", id);
        tr.addElement("td").setText(description);
    }

    public Element getDescription() {
        return description;
    }

    public Element getRules() {
        return rules;
    }

    protected final Element pktable;
    protected final Element proptable;
    protected final Element description;
    protected final Element rules;
}

