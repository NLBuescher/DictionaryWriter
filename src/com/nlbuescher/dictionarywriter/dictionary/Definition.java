package com.nlbuescher.dictionarywriter.dictionary;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.ParserConfigurationException;

/**
 * Definition
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
public class Definition {
    private String definitionLabel = "";
    private String specification = "";
    private String definitionText = "";
    private ExampleGroup exampleGroup;
    private SubDefinitionGroup subDefinitionGroup;


    public String getDefinitionLabel() {
        return definitionLabel;
    }

    public String getSpecification() {
        return specification;
    }

    public String getDefinitionText() {
        return definitionText;
    }

    public ExampleGroup getExampleGroup() {
        return exampleGroup;
    }

    public SubDefinitionGroup getSubDefinitionGroup() {
        return subDefinitionGroup;
    }

    public void setDefinitionLabel(String definitionLabel) {
        this.definitionLabel = definitionLabel;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public void setDefinitionText(String definitionText) {
        this.definitionText = definitionText;
    }

    public void setExampleGroup(ExampleGroup exampleGroup) {
        this.exampleGroup = exampleGroup;
    }

    public void setSubDefinitionGroup(SubDefinitionGroup subDefinitionGroup) {
        this.subDefinitionGroup = subDefinitionGroup;
    }


    public static Definition fromElement(Element element) {
        Definition definition = new Definition();

        NodeList nodes = element.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals("span")) {
                Element span = ((Element) node);
                if (span.hasAttribute("class") && span.getAttribute("class").equals("definitionLabel")) {
                    if (definition.definitionLabel.equals("")) {
                        definition.definitionLabel = span.getTextContent();
                    } else {
                        System.err.println("Found additional 'Definition Label'! Keeping first input.");
                    }
                } else if (span.hasAttribute("class") && span.getAttribute("class").equals("specification")) {
                    if (definition.specification.equals("")) {
                        definition.specification = span.getTextContent();
                    } else {
                        System.err.println("Found additional 'Specification'! Keeping first input.");
                    }
                } else if (span.hasAttribute("class") && span.getAttribute("class").equals("definitionText")) {
                    if (definition.definitionText.equals("")) {
                        definition.definitionText = span.getTextContent();
                    } else {
                        System.err.println("Found additional 'Definition Text'! Keeping first input.");
                    }
                } else if (span.hasAttribute("class") && span.getAttribute("class").equals("exampleGroup")) {
                    if (definition.exampleGroup == null) {
                        definition.exampleGroup = ExampleGroup.fromElement(span);
                    } else {
                        System.err.println("Found additional 'Example Group'! Keeping first input.");
                    }
                } else if (span.hasAttribute("class") && span.getAttribute("class").equals("subDefinitionGroup")) {
                    if (definition.subDefinitionGroup == null) {
                        definition.subDefinitionGroup = SubDefinitionGroup.fromElement(span);

                    } else {
                        System.err.println("Found additional 'Sub-Definition Group'! Keeping first input.");
                    }
                } else {
                    System.err.println("Failed to create either 'Definition Label', 'Specification', 'Definition Text', 'Example Group', or 'Sub-Definition Group' from " + span + "! The span doesn't have a proper 'class' attribute.");
                }
            } else if (node.getNodeType() == Node.TEXT_NODE) {
            } else {
                System.err.println("Failed to create anything from " + node + "! The element could not be imported.");
            }
        }

        return definition;
    }

    public Element toElement(Document doc) throws ParserConfigurationException {
        Element element = doc.createElement("span");
        element.setAttribute("class", "definition");

        if (!definitionLabel.equals("")) {
            Element label = doc.createElement("span");
            label.setAttribute("class", "definitionLabel");
            label.appendChild(doc.createTextNode(definitionLabel));
            element.appendChild(label);
        }

        if (!specification.equals("")) {
            Element spec = doc.createElement("span");
            spec.setAttribute("class", "specification");
            spec.appendChild(doc.createTextNode(specification));
            element.appendChild(spec);
        }

        if (!definitionText.equals("")) {
            Element text = doc.createElement("span");
            text.setAttribute("class", "definitionText");
            text.appendChild(doc.createTextNode(definitionText));
            element.appendChild(text);
        }

        if (exampleGroup != null)
            element.appendChild(exampleGroup.toElement(doc));

        if (subDefinitionGroup != null)
            element.appendChild(subDefinitionGroup.toElement(doc));

        return element;
    }

    public String toString() {
        return "Definition";
    }
}
