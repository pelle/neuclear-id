package org.neuclear.id.verifier;

import junit.framework.TestCase;
import org.dom4j.DocumentException;
import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import org.neudist.utils.NeudistException;
import org.neudist.utils.RegexFileNameFilter;
import org.neudist.xml.xmlsec.XMLSecTools;
import org.neuclear.id.SignedNamedObject;

import java.io.*;

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

$Id: VerificationTest.java,v 1.1 2003/09/27 19:23:11 pelle Exp $
$Log: VerificationTest.java,v $
Revision 1.1  2003/09/27 19:23:11  pelle
Added Builders to create named objects from scratch.

*/

/**
 * 
 * User: pelleb
 * Date: Sep 27, 2003
 * Time: 11:38:01 AM
 */
public class VerificationTest extends TestCase {
    public VerificationTest(String string) {

        super(string);
        reader=VerifyingReader.getInstance();
    }
    public void testSimple() throws IOException, DocumentException, NeudistException {
        runDirectoryTest("src/testdata/simple");
    }

    public void runDirectoryTest(String path) throws DocumentException, IOException, FileNotFoundException, NeudistException {
        File dir=new File(path);
        if (!dir.exists()) {
            System.out.println("Doesnt exist");
            return;
        }
        FilenameFilter filter;
        filter=new FilenameFilter(){
            public boolean accept(File dirf, String name) {
                return name.endsWith(".neu");
            }
        };

        File xmlfiles[]=dir.listFiles(filter);
        System.out.println("There are "+xmlfiles.length+" files in the directory");
        for (int i = 0; i < xmlfiles.length; i++) {

            File xmlfile = xmlfiles[i];
            System.out.print("Testing file: "+xmlfile.getName()+"... ");
            try {
                SignedNamedObject obj=reader.read(new FileInputStream(xmlfile));
                System.out.println("Name : "+obj.getName()+" VERIFIED");
            } catch (Exception e) {
                    System.out.println("ERROR "+e.getMessage());
                    assertTrue(false);
            }
        }


    }

    VerifyingReader reader;

}
