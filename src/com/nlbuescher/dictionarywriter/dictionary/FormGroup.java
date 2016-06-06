package com.nlbuescher.dictionarywriter.dictionary;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.ParserConfigurationException;
import java.util.ArrayList;

/**
 * Form Group
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
public class FormGroup {
    private ArrayList<Form> forms = new ArrayList<> ();


    public ArrayList<Form> getForms () {
        return forms;
    }


    public static FormGroup fromElement (Element element) {
        FormGroup formGroup = new FormGroup ();

        NodeList nodes = element.getChildNodes ();
        for (int i = 0; i < nodes.getLength (); i++) {
            Node node = nodes.item (i);
            if (node.getNodeType () == Node.ELEMENT_NODE && node.getNodeName ().equals ("span")) {
                Element span = ((Element) node);
                if (span.hasAttribute ("class") && span.getAttribute ("class").equals ("form")) {
                    formGroup.forms.add (Form.fromElement (span));
                } else {
                    System.err.println ("Failed to create 'Form' from " + span + "! The span doesn't have the proper 'class' attribute.");
                }
            } else if (node.getNodeType () == Node.TEXT_NODE) {
            } else {
                System.err.println ("Failed to create anything from " + node + "! The element could not be imported.");
            }
        }

        return formGroup;
    }

    public Element toElement (Document doc) throws ParserConfigurationException {
        Element element = doc.createElement ("span");
        element.setAttribute ("class", "formGroup");

        element.appendChild (doc.createTextNode ("("));

        int i = 0;
        for (Form form : forms) {
            element.appendChild (form.toElement (doc));

            if (i < forms.size () - 1)
                element.appendChild (doc.createTextNode (","));

            i++;
        }

        element.appendChild (doc.createTextNode (")"));

        return element;
    }

    public String toString () {
        return "Form Group";
    }
}
