package dictionary;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.ParserConfigurationException;

/**
 * Sub-Entry
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
public class SubEntry {
    private String subEntryLabel = "";
    private String subEntryText  = "";
    private SubEntryList subEntryList;
    private NoteGroup    noteGroup;


    public String getSubEntryLabel () {
        return subEntryLabel;
    }

    public String getSubEntryText () {
        return subEntryText;
    }

    public SubEntryList getSubEntryList () {
        return subEntryList;
    }

    public NoteGroup getNoteGroup () {
        return noteGroup;
    }

    public void setSubEntryLabel (String subEntryLabel) {
        this.subEntryLabel = subEntryLabel;
    }

    public void setSubEntryText (String subEntryText) {
        this.subEntryText = subEntryText;
    }

    public void setSubEntryList (SubEntryList subEntryList) {
        this.subEntryList = subEntryList;
    }

    public void setNoteGroup (NoteGroup noteGroup) {
        this.noteGroup = noteGroup;
    }


    public static SubEntry fromElement (Element element) {
        SubEntry subEntry = new SubEntry ();

        NodeList nodes = element.getChildNodes ();
        for (int i = 0; i < nodes.getLength (); i++) {
            Node node = nodes.item (i);
            if (node.getNodeType () == Node.ELEMENT_NODE && node.getNodeName ().equals ("span")) {
                Element span = ((Element) node);
                if (span.hasAttribute ("class") && span.getAttribute ("class").equals ("subEntryLabel")) {
                    if (subEntry.subEntryLabel.equals ("")) {
                        subEntry.subEntryLabel = span.getTextContent ();
                    } else {
                        System.err.println ("Found additional 'Sub-Entry Label'! Keeping first input.");
                    }
                } else if (span.hasAttribute ("class") && span.getAttribute ("class").equals ("subEntryText")) {
                    if (subEntry.subEntryText.equals ("")) {
                        subEntry.subEntryText = span.getTextContent ();
                    } else {
                        System.err.println ("Found additional 'Sub-Entry Text'! Keeping first input.");
                    }
                } else if (span.hasAttribute ("class") && span.getAttribute ("class").equals ("subEntryList")) {
                    if (subEntry.subEntryList == null) {
                        subEntry.subEntryList = SubEntryList.fromElement (span);
                    } else {
                        System.err.println ("Found additional 'Sub-Entry List'! Keeping first input.");
                    }
                } else if (span.hasAttribute ("class") && span.getAttribute ("class").equals ("noteGroup")) {
                    if (subEntry.noteGroup == null) {
                        subEntry.noteGroup = NoteGroup.fromElement (span);
                    } else {
                        System.err.println ("Found additional 'Note Group'! Keeping first input.");
                    }
                } else {
                    System.err.println ("Failed to create either 'Sub-Entry Label', 'Sub-Entry Text', 'Sub-Entry List', or 'Note Group' from " + span + "! The span doesn't have a proper 'class' attribute.");
                }
            } else if (node.getNodeType () == Node.TEXT_NODE) {
            } else {
                System.err.println ("Failed to create anything from " + node + "! The element could not be imported.");
            }
        }

        return subEntry;
    }

    public Element toElement (Document doc) throws ParserConfigurationException {
        Element element = doc.createElement ("span");
        element.setAttribute ("class", "subEntry");

        if (!subEntryLabel.equals ("")) {
            Element label = doc.createElement ("span");
            label.setAttribute ("class", "subEntryLabel");
            label.appendChild (doc.createTextNode (subEntryLabel));
        }

        if (!subEntryText.equals ("")) {
            Element text = doc.createElement ("span");
            text.setAttribute ("class", "subEntryLabel");
            text.appendChild (doc.createTextNode (subEntryText));
        }

        if (subEntryList != null)
            element.appendChild (subEntryList.toElement (doc));

        if (noteGroup != null)
            element.appendChild (noteGroup.toElement (doc));

        return element;
    }

    public String toString () {
        return "Sub-Entry";
    }
}
