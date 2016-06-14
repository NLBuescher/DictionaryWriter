package com.nlbuescher.dictionarywriter.dictionary;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.ParserConfigurationException;

/**
 * Pronunciation
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
public class Pronunciation {
    private String CL_IPA = "";
    private String VA_IPA = "";


    public String getCL_IPA() {
        return CL_IPA;
    }

    public String getVA_IPA() {
        return VA_IPA;
    }

    public void setCL_IPA(String CL_IPA) {
        this.CL_IPA = CL_IPA;
    }

    public void setVA_IPA(String VA_IPA) {
        this.VA_IPA = VA_IPA;
    }


    public static Pronunciation fromElement(Element element) {
        Pronunciation pronunciation = new Pronunciation();

        NodeList nodes = element.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals("span")) {
                Element span = ((Element) node);
                if (span.hasAttribute("d:pr") && span.getAttribute("d:pr").equals("CL_IPA")) {
                    if (pronunciation.CL_IPA.equals(""))
                        pronunciation.CL_IPA = span.getTextContent();
                    else
                        System.err.println("Found additional 'CL_IPA'! Keeping first input.");

                } else if (span.hasAttribute("d:pr") && span.getAttribute("d:pr").equals("VA_IPA")) {
                    if (pronunciation.VA_IPA.equals(""))
                        pronunciation.VA_IPA = span.getTextContent();
                    else
                        System.err.println("Found additional 'VA_IPA'! Keeping first input.");

                } else if (span.hasAttribute("d:pr") && span.getAttribute("d:pr").equals("LA_IPA")) {
                    if (pronunciation.CL_IPA.equals("") || pronunciation.VA_IPA.equals(""))
                        pronunciation.CL_IPA = pronunciation.VA_IPA = span.getTextContent();
                    else
                        System.err.println("Found additional 'LA_IPA'! Keeping first input(s).");

                } else {
                    System.err.println("Failed to create either 'CL_IPA' or 'VA_IPA' from " + span + "! The span doesn't have a proper 'd:pr' attribute. " + span.getAttribute("d:pr"));
                }
            } else if (node.getNodeType() == Node.TEXT_NODE) {
            } else {
                System.err.println("Failed to create anything from " + node + "! The element could not be imported.");
            }
        }

        return pronunciation;
    }

    public Element toElement(Document doc) throws ParserConfigurationException {
        Element element = doc.createElement("span");
        element.setAttribute("class", "pronunciation");

        element.appendChild(doc.createTextNode("|"));

        if (CL_IPA.contentEquals(VA_IPA) && !CL_IPA.equals("")) {
            Element la = doc.createElement("span");
            la.setAttribute("d:pr", "LA_IPA");
            la.appendChild(doc.createTextNode(CL_IPA));
            element.appendChild(la);
        } else {
            if (!CL_IPA.equals("")) {
                Element cl = doc.createElement("span");
                cl.setAttribute("d:pr", "CL_IPA");
                cl.appendChild(doc.createTextNode(CL_IPA));
                element.appendChild(cl);
            }

            if (!VA_IPA.equals("")) {
                Element va = doc.createElement("span");
                va.setAttribute("d:pr", "VA_IPA");
                va.appendChild(doc.createTextNode(VA_IPA));
                element.appendChild(va);
            }
        }

        element.appendChild(doc.createTextNode("|"));

        return element;
    }

    public String toString() {
        return "Pronunciation";
    }
}
