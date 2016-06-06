package dictionary;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.util.ArrayList;

/**
 * Dictionary: Top level member
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
public class Dictionary {
    private final String               XMLNS   = "http://www.w3.org/1999/xhtml";
    private final String               XMLNS_D = "http://www.apple.com/DTDs/DictionaryService-1.0.rng";
    private       ArrayList<DictEntry> entries = new ArrayList<> ();


    public ArrayList<DictEntry> getEntries () {
        return entries;
    }


    public static Dictionary fromElement (Element element) throws Exception {
        if (element.getTagName ().equals ("d:dictionary")) {
            Dictionary dictionary = new Dictionary ();
            NodeList nodes = element.getChildNodes ();
            for (int i = 0; i < nodes.getLength (); i++) {
                Node node = nodes.item (i);
                if (node.getNodeType () == Node.ELEMENT_NODE && node.getNodeName ().equals ("d:entry")) {
                    dictionary.entries.add (DictEntry.fromElement ((Element) node));

                } else if (node.getNodeType () == Node.TEXT_NODE) {
                } else {
                    System.err.println ("Failed to create 'DictEntry' from " + node.getNodeName () + "! The element could not be imported.");
                }
            }
            return dictionary;
        } else {
            System.err.println ("Failed to create top level member 'Dictionary' from " + element.getTagName () + "! The file could not be imported.");
            return null;
        }
    }

    public Element toElement (Document doc) throws ParserConfigurationException {
        Element element = doc.createElement ("d:dictionary");
        element.setAttribute ("xmlns", XMLNS);
        element.setAttribute ("xmlns:d", XMLNS_D);

        for (DictEntry dictEntry : entries)
            element.appendChild (dictEntry.toElement (doc));

        return element;
    }

    public String toString () {
        return "Dictionary";
    }
}
