package com.nlbuescher.dictionarywriter.dictionary;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.ParserConfigurationException;

/**
 * Example
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
public class Example {
    private String exampleLabel = "";
    private String specification = "";
    private String exampleText = "";
    private String exampleTranslation = "";


    public String getExampleLabel() {
        return exampleLabel;
    }

    public String getSpecification() {
        return specification;
    }

    public String getExampleText() {
        return exampleText;
    }

    public String getExampleTranslation() {
        return exampleTranslation;
    }

    public void setExampleLabel(String exampleLabel) {
        this.exampleLabel = exampleLabel;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public void setExampleText(String exampleText) {
        this.exampleText = exampleText;
    }

    public void setExampleTranslation(String exampleTranslation) {
        this.exampleTranslation = exampleTranslation;
    }


    public static Example fromElement(Element element) {
        Example example = new Example();

        NodeList nodes = element.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals("span")) {
                Element span = ((Element) node);
                if (span.hasAttribute("class") && span.getAttribute("class").equals("exampleLabel")) {
                    if (example.exampleLabel.equals("")) {
                        example.exampleLabel = span.getTextContent();
                    } else {
                        System.err.println("Found additional 'Example Label'! Keeping first input.");
                    }
                } else if (span.hasAttribute("class") && span.getAttribute("class").equals("specification")) {
                    if (example.specification.equals("")) {
                        example.specification = span.getTextContent();
                    } else {
                        System.err.println("Found additional 'Specification'! Keeping first input.");
                    }
                } else if (span.hasAttribute("class") && span.getAttribute("class").equals("exampleText")) {
                    if (example.exampleText.equals("")) {
                        example.exampleText = span.getTextContent();
                    } else {
                        System.err.println("Found additional 'Example Text'! Keeping first input.");
                    }
                } else if (span.hasAttribute("class") && span.getAttribute("class").equals("exampleTranslation")) {
                    if (example.exampleTranslation.equals("")) {
                        example.exampleTranslation = span.getTextContent();
                    } else {
                        System.err.println("Found additional 'Example Translation'! Keeping first input.");
                    }
                } else {
                    System.err.println("Failed to create either 'Example Label', 'Specification', 'Example Text', or 'Example Translation' from " + span + "! The span doesn't have a proper 'class' attribute.");
                }
            } else if (node.getNodeType() == Node.TEXT_NODE) {
            } else {
                System.err.println("Failed to create anything from " + node + "! The element could not be imported.");
            }
        }

        return example;
    }

    public Element toElement(Document doc) throws ParserConfigurationException {
        Element element = doc.createElement("span");
        element.setAttribute("class", "example");

        if (!exampleLabel.equals("")) {
            Element label = doc.createElement("span");
            label.setAttribute("class", "exampleLabel");
            label.appendChild(doc.createTextNode(exampleLabel));
            element.appendChild(label);
        }

        if (!specification.equals("")) {
            Element spec = doc.createElement("span");
            spec.setAttribute("class", "specification");
            spec.appendChild(doc.createTextNode(specification));
            element.appendChild(spec);
        }

        if (!exampleText.equals("")) {
            Element text = doc.createElement("span");
            text.setAttribute("class", "exampleText");
            text.appendChild(doc.createTextNode(exampleText));
            element.appendChild(text);
        }

        if (!exampleTranslation.equals("")) {
            Element translation = doc.createElement("span");
            translation.setAttribute("class", "exampleTranslation");
            translation.appendChild(doc.createTextNode(exampleTranslation));
            element.appendChild(translation);
        }

        return element;
    }

    public String toString() {
        return "Example";
    }
}
