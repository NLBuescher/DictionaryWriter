package dictionary;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.ParserConfigurationException;

/**
 * Form
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
public class Form {
    private String formLabel = "";
    private String formText  = "";
    private Pronunciation pronunciation;


    public String getFormLabel () {
        return formLabel;
    }

    public String getFormText () {
        return formText;
    }

    public Pronunciation getPronunciation () {
        return pronunciation;
    }

    public void setFormLabel (String formLabel) {
        this.formLabel = formLabel;
    }

    public void setFormText (String formText) {
        this.formText = formText;
    }

    public void setPronunciation (Pronunciation pronunciation) {
        this.pronunciation = pronunciation;
    }


    public static Form fromElement (Element element) {
        Form form = new Form ();

        NodeList nodes = element.getChildNodes ();
        for (int i = 0; i < nodes.getLength (); i++) {
            Node node = nodes.item (i);
            if (node.getNodeType () == Node.ELEMENT_NODE && node.getNodeName ().equals ("span")) {
                Element span = ((Element) node);
                if (span.hasAttribute ("class") && span.getAttribute ("class").equals ("formLabel")) {
                    if (form.formLabel.equals ("")) {
                        form.formLabel = span.getTextContent ();
                    } else {
                        System.err.println ("Found additional 'Form Label'! Keeping first input.");
                    }
                } else if (span.hasAttribute ("class") && span.getAttribute ("class").equals ("formText")) {
                    if (form.formText.equals ("")) {
                        form.formText = span.getTextContent ();
                    } else {
                        System.err.println ("Found additional 'Form Text'! Keeping first input.");
                    }
                } else if (span.hasAttribute ("class") && span.getAttribute ("class").equals ("pronunciation")) {
                    if (form.pronunciation == null) {
                        form.pronunciation = Pronunciation.fromElement (span);
                    } else {
                        System.err.println ("Found additional 'Pronunciation'! Keeping first input.");
                    }
                } else {
                    System.err.println ("Failed to create either 'Grammar', 'Specification', or 'Form Group' from " + span + "! The span doesn't have a proper 'class' attribute.");
                }
            } else if (node.getNodeType () == Node.TEXT_NODE) {
            } else {
                System.err.println ("Failed to create anything from " + node + "! The element could not be imported.");
            }
        }

        return form;
    }

    public Element toElement (Document doc) throws ParserConfigurationException {
        Element element = doc.createElement ("span");
        element.setAttribute ("class", "form");

        if (!formLabel.equals ("")) {
            Element label = doc.createElement ("span");
            label.setAttribute ("class", "formLabel");
            label.appendChild (doc.createTextNode (formLabel));
            element.appendChild (label);
        }

        if (!formText.equals ("")) {
            Element text = doc.createElement ("span");
            text.setAttribute ("class", "formText");
            text.appendChild (doc.createTextNode (formText));
            element.appendChild (text);
        }

        if (pronunciation != null)
            element.appendChild (pronunciation.toElement (doc));

        return element;
    }

    public String toString () {
        return "Form";
    }
}
