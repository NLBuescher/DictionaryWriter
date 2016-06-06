package dictionary;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.ParserConfigurationException;
import java.util.ArrayList;

/**
 * Dictionary Entry [d:entry]
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
public class DictEntry {
    private String           id      = "";
    private String           d_title = "";
    private ArrayList<Index> indices = new ArrayList<> ();
    private HeadGroup  headGroup;
    private EntryGroup entryGroup;


    public String getId () {
        return id;
    }

    public String getTitle () {
        return d_title;
    }

    public ArrayList<Index> getIndices () {
        return indices;
    }

    public HeadGroup getHeadGroup () {
        return headGroup;
    }

    public EntryGroup getEntryGroup () {
        return entryGroup;
    }

    public void setId (String id) {
        this.id = id;
    }

    public void setTitle (String title) {
        this.d_title = title;
    }

    public void setHeadGroup (HeadGroup headGroup) {
        this.headGroup = headGroup;
    }

    public void setEntryGroup (EntryGroup entryGroup) {
        this.entryGroup = entryGroup;
    }


    public static DictEntry fromElement (Element element) {
        DictEntry entry = new DictEntry ();

        if (element.hasAttribute ("id"))
            entry.id = element.getAttribute ("id");
        else
            System.err.println ("Failed to get attribute 'id' from " + element + "! Left empty.");

        if (element.hasAttribute ("d:title"))
            entry.d_title = element.getAttribute ("d:title");
        else
            System.err.println ("Failed to get attribute 'd:title' from " + element + "! Left empty.");

        NodeList nodes = element.getChildNodes ();
        for (int i = 0; i < nodes.getLength (); i++) {
            Node node = nodes.item (i);
            if (node.getNodeType () == Node.ELEMENT_NODE && node.getNodeName ().equals ("d:index")) {
                entry.indices.add (Index.fromElement ((Element) node));

            } else if (node.getNodeType () == Node.ELEMENT_NODE && node.getNodeName ().equals ("span")) {
                Element span = ((Element) node);
                if (span.hasAttribute ("class") && span.getAttribute ("class").equals ("headGroup")) {
                    if (entry.headGroup == null) {
                        entry.headGroup = HeadGroup.fromElement (span);
                    } else {
                        System.err.println ("Found additional 'Head Group'! Keeping first input.");
                    }
                } else if (span.hasAttribute ("class") && span.getAttribute ("class").equals ("entryGroup")) {
                    if (entry.entryGroup == null) {
                        entry.entryGroup = EntryGroup.fromElement (span);
                    } else {
                        System.err.println ("Found additional 'Entry Group'! Keeping first input.");
                    }
                } else {
                    System.err.println ("Failed to create either a 'Head Group' or 'Entry Group' from " + span + "! The element doesn't have a proper 'class' attribute.");
                }
            } else if (node.getNodeType () == Node.TEXT_NODE) {
            } else {
                System.err.println ("Failed to create anything from " + node + "! The element could not be imported.");
            }
        }
        return entry;
    }

    public Element toElement (Document doc) throws ParserConfigurationException {
        Element element = doc.createElement ("d:entry");
        element.setAttribute ("id", id);
        element.setAttribute ("d:title", d_title);

        for (Index index : indices)
            element.appendChild (index.toElement (doc));

        if (headGroup != null)
            element.appendChild (headGroup.toElement (doc));

        if (entryGroup != null)
            element.appendChild (entryGroup.toElement (doc));

        return element;
    }

    public String toString () {
        return "Entry: " + d_title;
    }
}
