package dictionary;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.ParserConfigurationException;

/**
 * Index [d:index]
 * Copyright (C) 2016  Nicola Buescher
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
public class Index {
    private String d_title = "";
    private String d_value = "";


    public String getTitle () {
        return d_title;
    }

    public String getValue () {
        return d_value;
    }

    public void setTitle (String title) {
        this.d_title = title;
    }

    public void setValue (String value) {
        this.d_value = value;
    }


    public static Index fromElement (Element element) {
        Index index = new Index ();

        if (element.hasAttribute ("d:title"))
            index.d_title = element.getAttribute ("d:title");
        else
            System.err.println ("Failed to get attribute 'd:title' from " + element.getTagName () + "! Left empty.");

        if (element.hasAttribute ("d:value"))
            index.d_value = element.getAttribute ("d:value");
        else
            System.err.println ("Failed to get attribute 'd:value' from " + element.getTagName () + "! Left empty.");

        return index;
    }

    public Element toElement (Document doc) throws ParserConfigurationException {
        Element element = doc.createElement ("d:index");
        element.setAttribute ("d:title", d_title);
        element.setAttribute ("d:value", d_value);

        return element;
    }

    public String toString () {
        return "Index: " + d_value;
    }
}
