/*
 * $Id: GenericNamedObject.java,v 1.2 2003/09/22 19:24:01 pelle Exp $
 * $Log: GenericNamedObject.java,v $
 * Revision 1.2  2003/09/22 19:24:01  pelle
 * More fixes throughout to problems caused by renaming.
 *
 * Revision 1.1.1.1  2003/09/19 14:41:03  pelle
 * First import into the neuclear project. This was originally under the SF neudist
 * project. This marks a general major refactoring and renaming ahead.
 *
 * The new name for this code is NeuClear Identity and has the general package header of
 * org.neuclear.id
 * There are other areas within the current code which will be split out into other subprojects later on.
 * In particularly the signers will be completely seperated out as well as the contract types.
 *
 *
 * Revision 1.3  2002/12/17 21:40:53  pelle
 * First part of refactoring of NamedObject and SignedObject Interface/Class parings.
 *
 * Revision 1.2  2002/09/21 23:11:13  pelle
 * A bunch of clean ups. Got rid of as many hard coded URL's as I could.
 *
 * User: pelleb
 * Date: Sep 19, 2002
 * Time: 5:03:39 PM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package org.neuclear.id;

import org.dom4j.Element;
import org.dom4j.Namespace;
import org.neudist.utils.NeudistException;

public class GenericNamedObject extends NamedObject {
    public GenericNamedObject(Element elem) throws NeudistException {
        super(elem);
    }

    public Namespace getNS() {
        return getElement().getQName().getNamespace();
    }

    public String getTagName() {
        return getElement().getQName().getName();
    }

}
