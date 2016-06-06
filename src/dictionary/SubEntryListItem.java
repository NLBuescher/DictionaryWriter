package dictionary;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.ParserConfigurationException;

/**
 * Sub-Entry List Item
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
public class SubEntryListItem {
    private String subEntryListItemLabel = "";
    private String specification         = "";
    private String subEntryListItemText  = "";


    public String getSubEntryListItemLabel () {
        return subEntryListItemLabel;
    }

    public String getSpecification () {
        return specification;
    }

    public String getSubEntryListItemText () {
        return subEntryListItemText;
    }

    public void setSubEntryListItemLabel (String subEntryListItemLabel) {
        this.subEntryListItemLabel = subEntryListItemLabel;
    }

    public void setSpecification (String specification) {
        this.specification = specification;
    }

    public void setSubEntryListItemText (String subEntryListItemText) {
        this.subEntryListItemText = subEntryListItemText;
    }


    public static SubEntryListItem fromElement (Element element) {
        SubEntryListItem subEntryListItem = new SubEntryListItem ();

        NodeList nodes = element.getChildNodes ();
        for (int i = 0; i < nodes.getLength (); i++) {
            Node node = nodes.item (i);
            if (node.getNodeType () == Node.ELEMENT_NODE && node.getNodeName ().equals ("span")) {
                Element span = ((Element) node);
                if (span.hasAttribute ("class") && span.getAttribute ("class").equals ("subEntryListItemLabel")) {
                    if (subEntryListItem.subEntryListItemLabel.equals ("")) {
                        subEntryListItem.subEntryListItemLabel = span.getTextContent ();
                    } else {
                        System.err.println ("Found additional 'Sub-Entry List Item Label'! Keeping first input.");
                    }
                } else if (span.hasAttribute ("class") && span.getAttribute ("class").equals ("specification")) {
                    if (subEntryListItem.specification.equals ("")) {
                        subEntryListItem.specification = span.getTextContent ();
                    } else {
                        System.err.println ("Found additional 'Specification'! Keeping first input.");
                    }
                } else if (span.hasAttribute ("class") && span.getAttribute ("class").equals ("subEntryListItemText")) {
                    if (subEntryListItem.subEntryListItemText.equals ("")) {
                        subEntryListItem.subEntryListItemText = span.getTextContent ();
                    } else {
                        System.err.println ("Found additional 'Sub-Entry List Item Text'! Keeping first input.");
                    }
                } else {
                    System.err.println ("Failed to create either 'Sub-Entry List Item Label', 'Specification', or 'Sub-Entry List Item Text' from " + span + "! The span doesn't have a proper 'class' attribute.");
                }
            } else if (node.getNodeType () == Node.TEXT_NODE) {
            } else {
                System.err.println ("Failed to create anything from " + node + "! The element could not be imported.");
            }
        }

        return subEntryListItem;
    }

    public Element toElement (Document doc) throws ParserConfigurationException {
        Element element = doc.createElement ("span");
        element.setAttribute ("class", "subEntryListItem");

        if (!subEntryListItemLabel.equals ("")) {
            Element label = doc.createElement ("span");
            label.setAttribute ("class", "subEntryListItemLabel");
            label.appendChild (doc.createTextNode (subEntryListItemLabel));
            element.appendChild (label);
        }

        if (!specification.equals ("")) {
            Element spec = doc.createElement ("span");
            spec.setAttribute ("class", "specification");
            spec.appendChild (doc.createTextNode ("[" + specification + "]"));
            element.appendChild (spec);
        }

        if (!subEntryListItemText.equals ("")) {
            Element text = doc.createElement ("span");
            text.setAttribute ("class", "subEntryListItemText");
            text.appendChild (doc.createTextNode (subEntryListItemText));
            element.appendChild (text);
        }

        return element;
    }

    public String toString () {
        return "Sub-Entry List Item";
    }
}
