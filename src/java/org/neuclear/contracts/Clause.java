package org.neuclear.contracts;

/*
NeuClear Distributed Transaction Clearing Platform
(C) 2003 Pelle Braendgaard

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 2.1 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library; if not, write to the Free Software
Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

$Id: Clause.java,v 1.1 2004/01/05 23:47:32 pelle Exp $
$Log: Clause.java,v $
Revision 1.1  2004/01/05 23:47:32  pelle
Create new Document classification "order", which is really just inherint in the new
package layout.
Got rid of much of the inheritance that was lying around and thought a bit further about the format of the exchange orders.

*/

/**
 * User: pelleb
 * Date: Jan 5, 2004
 * Time: 7:21:00 PM
 */
public abstract class Clause {
    protected Clause(String title){
        this.title=title;
    }
    abstract public String getText();

    public String getTitle() {
        return title;
    }

    private final String title;

}