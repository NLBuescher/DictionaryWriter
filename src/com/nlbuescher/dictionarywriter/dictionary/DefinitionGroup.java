package com.nlbuescher.dictionarywriter.dictionary;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.ParserConfigurationException;
import java.util.ArrayList;

/**
 * Definition Group
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
public class DefinitionGroup {
    private String                definitionGroupLabel = "";
    private ArrayList<Definition> definitions          = new ArrayList<> ();


    public String getDefinitionGroupLabel () {
        return definitionGroupLabel;
    }

    public ArrayList<Definition> getDefinitions () {
        return definitions;
    }

    public void setDefinitionGroupLabel (String definitionGroupLabel) {
        this.definitionGroupLabel = definitionGroupLabel;
    }


    public static DefinitionGroup fromElement (Element element) {
        DefinitionGroup definitionGroup = new DefinitionGroup ();

        NodeList nodes = element.getChildNodes ();
        for (int i = 0; i < nodes.getLength (); i++) {
            Node node = nodes.item (i);
            if (node.getNodeType () == Node.ELEMENT_NODE && node.getNodeName ().equals ("span")) {
                Element span = ((Element) node);
                if (span.hasAttribute ("class") && span.getAttribute ("class").equals ("definitionGroupLabel")) {
                    if (definitionGroup.definitionGroupLabel.equals ("")) {
                        definitionGroup.definitionGroupLabel = span.getTextContent ();
                    } else {
                        System.err.println ("Found additional 'Definition Group Label'! Keeping first input.");
                    }
                } else if (span.hasAttribute ("class") && span.getAttribute ("class").equals ("definition")) {
                    definitionGroup.definitions.add (Definition.fromElement (span));
                } else {
                    System.err.println ("Failed to create either 'Definition Group Label' or 'Definition' from " + span + "! The span doesn't have a proper 'class' attribute.");
                }
            } else if (node.getNodeType () == Node.TEXT_NODE) {
            } else {
                System.err.println ("Failed to create anything from " + node + "! The element could not be imported.");
            }
        }

        return definitionGroup;
    }

    public Element toElement (Document doc) throws ParserConfigurationException {
        Element element = doc.createElement ("span");
        element.setAttribute ("class", "definitionGroup");

        if (!definitionGroupLabel.equals ("")) {
            Element label = doc.createElement ("span");
            label.setAttribute ("class", "definitionGroupLabel");
            label.appendChild (doc.createTextNode (definitionGroupLabel));
            element.appendChild (label);
        }

        for (Definition definition : definitions)
            element.appendChild (definition.toElement (doc));

        return element;
    }

    public String toString () {
        return "Definition Group";
    }
}
