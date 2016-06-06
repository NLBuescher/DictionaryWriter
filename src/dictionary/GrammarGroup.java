package dictionary;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.ParserConfigurationException;

/**
 * Grammar Group
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
public class GrammarGroup {
    private String grammar       = "";
    private String specification = "";
    private FormGroup formGroup;


    public String getGrammar () {
        return grammar;
    }

    public String getSpecification () {
        return specification;
    }

    public FormGroup getFormGroup () {
        return formGroup;
    }

    public void setGrammar (String grammar) {
        this.grammar = grammar;
    }

    public void setSpecification (String specification) {
        this.specification = specification;
    }

    public void setFormGroup (FormGroup formGroup) {
        this.formGroup = formGroup;
    }


    public static GrammarGroup fromElement (Element element) {
        GrammarGroup grammarGroup = new GrammarGroup ();

        NodeList nodes = element.getChildNodes ();
        for (int i = 0; i < nodes.getLength (); i++) {
            Node node = nodes.item (i);
            if (node.getNodeType () == Node.ELEMENT_NODE && node.getNodeName ().equals ("span")) {
                Element span = ((Element) node);
                if (span.hasAttribute ("class") && span.getAttribute ("class").equals ("grammar")) {
                    if (grammarGroup.grammar.equals ("")) {
                        grammarGroup.grammar = span.getTextContent ();
                    } else {
                        System.err.println ("Found additional 'Grammar'! Keeping first input.");
                    }
                } else if (span.hasAttribute ("class") && span.getAttribute ("class").equals ("specification")) {
                    if (grammarGroup.specification.equals ("")) {
                        grammarGroup.specification = span.getTextContent ();
                    } else {
                        System.err.println ("Found additional 'Specification'! Keeping first input.");
                    }
                } else if (span.hasAttribute ("class") && span.getAttribute ("class").equals ("formGroup")) {
                    if (grammarGroup.formGroup == null) {
                        grammarGroup.formGroup = FormGroup.fromElement (span);
                    } else {
                        System.err.println ("Found additional 'Form Group'! Keeping first input.");
                    }
                } else {
                    System.err.println ("Failed to create either 'Grammar', 'Specification', or 'Form Group' from " + span + "! The span doesn't have a proper 'class' attribute.");
                }
            } else if (node.getNodeType () == Node.TEXT_NODE) {
            } else {
                System.err.println ("Failed to create anything from " + node + "! The element could not be imported.");
            }
        }

        return grammarGroup;
    }

    public Element toElement (Document doc) throws ParserConfigurationException {
        Element element = doc.createElement ("span");
        element.setAttribute ("class", "grammarGroup");

        if (!grammar.equals ("")) {
            Element gram = doc.createElement ("span");
            gram.setAttribute ("class", "grammar");
            gram.appendChild (doc.createTextNode (grammar));
            element.appendChild (gram);
        }

        if (!specification.equals ("")) {
            Element spec = doc.createElement ("span");
            spec.setAttribute ("class", "specification");
            spec.appendChild (doc.createTextNode (specification));
            element.appendChild (spec);
        }

        if (formGroup != null)
            element.appendChild (formGroup.toElement (doc));

        return element;
    }

    public String toString () {
        return "Grammar Group";
    }
}
