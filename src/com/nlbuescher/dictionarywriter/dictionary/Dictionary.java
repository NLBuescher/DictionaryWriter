package com.nlbuescher.dictionarywriter.dictionary;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * DictionaryWriter Dictionary
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
public class Dictionary implements Serializable {

    private final String XMLNS = "http://www.w3.org/1999/xhtml";
    private final String XMLNS_D = "http://www.apple.com/DTDs/DictionaryService-1.0.rng";

    private List<D_entry> entries;

    public Dictionary() {
        entries = new ArrayList<>();
    }

    public Dictionary(Element element) throws Exception {
        this();
        if (!element.getTagName().equals("d:dictionary"))
            throw new Exception("The element is not a 'd:dictionary'");

        NodeList nodes = element.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals("d:entry"))
                entries.add(new D_entry((Element) node));
            else if (node.getNodeType() != Node.TEXT_NODE)
                System.err.println("The node is either not an element, not a text node, or not a 'd:entry'! Ignoring.");
        }
    }

    public List<D_entry> getEntries() {
        return entries;
    }

    public Element toElementWithDocument(Document document) {
        Element element = document.createElement("d:dictionary");

        element.setAttribute("xmlns", XMLNS);
        element.setAttribute("xmlns:d", XMLNS_D);

        for (D_entry entry : entries)
            element.appendChild(entry.toElementWithDocument(document));

        return element;
    }

    public String toString() {
        return "Dictionary";
    }
}
