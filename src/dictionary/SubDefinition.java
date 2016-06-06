package dictionary;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.ParserConfigurationException;

/**
 * Sub-Definition
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
public class SubDefinition {
    private String subDefinitionLabel = "";
    private String specification      = "";
    private String subDefinitionText  = "";
    private ExampleGroup exampleGroup;


    public String getSubDefinitionLabel () {
        return subDefinitionLabel;
    }

    public String getSpecification () {
        return specification;
    }

    public String getSubDefinitionText () {
        return subDefinitionText;
    }

    public ExampleGroup getExampleGroup () {
        return exampleGroup;
    }

    public void setSubDefinitionLabel (String subDefinitionLabel) {
        this.subDefinitionLabel = subDefinitionLabel;
    }

    public void setSpecification (String specification) {
        this.specification = specification;
    }

    public void setSubDefinitionText (String subDefinitionText) {
        this.subDefinitionText = subDefinitionText;
    }

    public void setExampleGroup (ExampleGroup exampleGroup) {
        this.exampleGroup = exampleGroup;
    }


    public static SubDefinition fromElement (Element element) {
        SubDefinition subDefinition = new SubDefinition ();

        NodeList nodes = element.getChildNodes ();
        for (int i = 0; i < nodes.getLength (); i++) {
            Node node = nodes.item (i);
            if (node.getNodeType () == Node.ELEMENT_NODE && node.getNodeName ().equals ("span")) {
                Element span = ((Element) node);
                if (span.hasAttribute ("class") && span.getAttribute ("class").equals ("subDefinitionLabel")) {
                    if (subDefinition.subDefinitionLabel.equals ("")) {
                        subDefinition.subDefinitionLabel = span.getTextContent ();
                    } else {
                        System.err.println ("Found additional 'Sub-Definition Label'! Keeping first input.");
                    }
                } else if (span.hasAttribute ("class") && span.getAttribute ("class").equals ("specification")) {
                    if (subDefinition.specification.equals ("")) {
                        subDefinition.specification = span.getTextContent ();
                    } else {
                        System.err.println ("Found additional 'Specification'! Keeping first input.");
                    }
                } else if (span.hasAttribute ("class") && span.getAttribute ("class").equals ("subDefinitionText")) {
                    if (subDefinition.subDefinitionText.equals ("")) {
                        subDefinition.subDefinitionText = span.getTextContent ();
                    } else {
                        System.err.println ("Found additional 'Sub-Definition Text'! Keeping first input.");
                    }
                } else if (span.hasAttribute ("class") && span.getAttribute ("class").equals ("exampleGroup")) {
                    if (subDefinition.exampleGroup == null) {
                        subDefinition.exampleGroup = ExampleGroup.fromElement (span);
                    } else {
                        System.err.println ("Found additional 'Example Group'! Keeping first input.");
                    }
                } else {
                    System.err.println ("Failed to create either 'Sub-Definition Label', 'Specification', 'Sub-Definition Text', or 'Example Group' from " + span + "! The span doesn't have a proper 'class' attribute.");
                }
            } else if (node.getNodeType () == Node.TEXT_NODE) {
            } else {
                System.err.println ("Failed to create anything from " + node + "! The element could not be imported.");
            }
        }

        return subDefinition;
    }

    public Element toElement (Document doc) throws ParserConfigurationException {
        Element element = doc.createElement ("span");
        element.setAttribute ("class", "subDefinition");

        if (!subDefinitionLabel.equals ("")) {
            Element label = doc.createElement ("span");
            label.setAttribute ("class", "subDefinitionLabel");
            label.appendChild (doc.createTextNode (subDefinitionLabel));
            element.appendChild (label);
        }

        if (!specification.equals ("")) {
            Element spec = doc.createElement ("span");
            spec.setAttribute ("class", "specification");
            spec.appendChild (doc.createTextNode (specification));
            element.appendChild (spec);
        }

        if (!subDefinitionText.equals ("")) {
            Element text = doc.createElement ("span");
            text.setAttribute ("class", "subDefinitionText");
            text.appendChild (doc.createTextNode (subDefinitionText));
            element.appendChild (text);
        }

        if (exampleGroup != null)
            element.appendChild (exampleGroup.toElement (doc));

        return element;
    }

    public String toString () {
        return "Sub-Definition";
    }
}
