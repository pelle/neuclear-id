package org.neuclear.id.auth;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/*
 *  The NeuClear Project and it's libraries are
 *  (c) 2002-2004 Antilles Software Ventures SA
 *  For more information see: http://neuclear.org
 *
 *  This library is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public
 *  License as published by the Free Software Foundation; either
 *  version 2.1 of the License, or (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *  Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public
 *  License along with this library; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

/**
 * User: pelleb
 * Date: Jun 12, 2004
 * Time: 10:32:30 AM
 */
public class LoginTag extends TagSupport {
    public LoginTag() {
        InputStream is = getClass().getClassLoader().getResourceAsStream("org/neuclear/id/auth/login.html");
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuffer buffy = new StringBuffer(2000);
        try {
            String line = reader.readLine();
            while (line != null) {
                buffy.append(line);
                line = reader.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        html = buffy.toString();
    }

    public int doStartTag() throws JspException {
        try {
            pageContext.getOut().print(html);
        } catch (Exception ex) {
            throw new JspTagException("SimpleTag: " +
                    ex.getMessage());
        }
        return SKIP_BODY;
    }

    public int doEndTag() {
        return EVAL_PAGE;
    }

    private final String html;
}
