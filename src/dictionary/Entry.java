package dictionary;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.ParserConfigurationException;
import java.util.ArrayList;

/**
 * Entry
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
public class Entry {
    private GrammarGroup grammarGroup;
    private ArrayList<DefinitionGroup> definitionGroups = new ArrayList<> ();
    private SubEntryGroup subEntryGroup;


    public GrammarGroup getGrammarGroup () {
        return grammarGroup;
    }

    public ArrayList<DefinitionGroup> getDefinitionGroups () {
        return definitionGroups;
    }

    public SubEntryGroup getSubEntryGroup () {
        return subEntryGroup;
    }

    public void setGrammarGroup (GrammarGroup grammarGroup) {
        this.grammarGroup = grammarGroup;
    }

    public void setSubEntryGroup (SubEntryGroup subEntryGroup) {
        this.subEntryGroup = subEntryGroup;
    }


    public static Entry fromElement (Element element) {
        Entry entry = new Entry ();

        NodeList nodes = element.getChildNodes ();
        for (int i = 0; i < nodes.getLength (); i++) {
            Node node = nodes.item (i);
            if (node.getNodeType () == Node.ELEMENT_NODE && node.getNodeName ().equals ("span")) {
                Element span = ((Element) node);
                if (span.hasAttribute ("class") && span.getAttribute ("class").equals ("grammarGroup")) {
                    if (entry.grammarGroup == null) {
                        entry.grammarGroup = GrammarGroup.fromElement (span);
                    } else {
                        System.err.println ("Found additional 'Grammar Group'! Keeping first input.");
                    }
                } else if (span.hasAttribute ("class") && span.getAttribute ("class").equals ("definitionGroup")) {
                    entry.definitionGroups.add (DefinitionGroup.fromElement (span));

                } else if (span.hasAttribute ("class") && span.getAttribute ("class").equals ("subEntryGroup")) {
                    if (entry.subEntryGroup == null) {
                        entry.subEntryGroup = SubEntryGroup.fromElement (span);
                    } else {
                        System.err.println ("Found additional 'Sub-Entry Group'! Keeping first input.");
                    }
                } else {
                    System.err.println ("Failed to create either 'Grammar Group', 'Definition Group', or 'Sub-Entry Group' from " + span + "! The span doesn't have a proper 'class' attribute.");
                }
            } else if (node.getNodeType () == Node.TEXT_NODE) {
            } else {
                System.err.println ("Failed to create anything from " + node + "! The element could not be imported.");
            }
        }

        return entry;
    }

    public Element toElement (Document doc) throws ParserConfigurationException {
        Element element = doc.createElement ("span");
        element.setAttribute ("class", "entry");

        if (grammarGroup != null)
            element.appendChild (grammarGroup.toElement (doc));

        for (DefinitionGroup definitionGroup : definitionGroups)
            element.appendChild (definitionGroup.toElement (doc));

        if (subEntryGroup != null)
            element.appendChild (subEntryGroup.toElement (doc));

        return element;
    }

    public String toString () {
        return "Entry";
    }
}
