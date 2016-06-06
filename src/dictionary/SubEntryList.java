package dictionary;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.ParserConfigurationException;
import java.util.ArrayList;

/**
 * Sub-Entry List
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
public class SubEntryList {
    private ArrayList<SubEntryListItem> subEntryListItems = new ArrayList<> ();


    public ArrayList<SubEntryListItem> getSubEntryListItems () {
        return subEntryListItems;
    }


    public static SubEntryList fromElement (Element element) {
        SubEntryList subEntryList = new SubEntryList ();

        NodeList nodes = element.getChildNodes ();
        for (int i = 0; i < nodes.getLength (); i++) {
            Node node = nodes.item (i);
            if (node.getNodeType () == Node.ELEMENT_NODE && node.getNodeName ().equals ("span")) {
                Element span = ((Element) node);
                if (span.hasAttribute ("class") && span.getAttribute ("class").equals ("subEntryListItem")) {
                    subEntryList.subEntryListItems.add (SubEntryListItem.fromElement (span));
                } else {
                    System.err.println ("Failed to create 'Sub-Entry List Item' from " + span + "! The span doesn't have the proper 'class' attribute.");
                }
            } else if (node.getNodeType () == Node.TEXT_NODE) {
            } else {
                System.err.println ("Failed to create anything from " + node + "! The element could not be imported.");
            }
        }

        return subEntryList;
    }

    public Element toElement (Document doc) throws ParserConfigurationException {
        Element element = doc.createElement ("span");
        element.setAttribute ("class", "subEntryList");

        for (SubEntryListItem subEntryListItem : subEntryListItems)
            element.appendChild (subEntryListItem.toElement (doc));

        return element;
    }

    public String toString () {
        return "Sub-Entry List";
    }
}
