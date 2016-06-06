package dictionary;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.ParserConfigurationException;

/**
 * Head Group
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
public class HeadGroup {
    private String headWord = "";
    private Pronunciation pronunciation;


    public String getHeadWord () {
        return headWord;
    }

    public Pronunciation getPronunciation () {
        return pronunciation;
    }

    public void setHeadWord (String headWord) {
        this.headWord = headWord;
    }

    public void setPronunciation (Pronunciation pronunciation) {
        this.pronunciation = pronunciation;
    }


    public static HeadGroup fromElement (Element element) {
        HeadGroup headGroup = new HeadGroup ();

        NodeList nodes = element.getChildNodes ();
        for (int i = 0; i < nodes.getLength (); i++) {
            Node node = nodes.item (i);
            if (node.getNodeType () == Node.ELEMENT_NODE && node.getNodeName ().equals ("span")) {
                Element span = ((Element) node);
                if (span.hasAttribute ("class") && span.getAttribute ("class").equals ("headWord")) {
                    if (headGroup.headWord.equals ("")) {
                        headGroup.headWord = span.getTextContent ();
                    } else {
                        System.err.println ("Found additional 'Head Word'! Keeping first input.");
                    }
                } else if (span.hasAttribute ("class") && span.getAttribute ("class").equals ("pronunciation")) {
                    if (headGroup.pronunciation == null) {
                        headGroup.pronunciation = Pronunciation.fromElement (span);
                    } else {
                        System.err.println ("Found additional 'Pronunciation'! Keeping first input.");
                    }
                } else {
                    System.err.println ("Failed to create either 'Head Word' or 'Pronunciation' from " + span + "! The element doesn't have a proper 'class' attribute.");
                }
            } else if (node.getNodeType () == Node.TEXT_NODE) {
            } else {
                System.err.println ("Failed to create anything from " + node + "! The element could not be imported.\n");
            }
        }

        return headGroup;
    }

    public Element toElement (Document doc) throws ParserConfigurationException {
        Element element = doc.createElement ("span");
        element.setAttribute ("class", "headGroup");

        if (!headWord.equals ("")) {
            Element hw = doc.createElement ("span");
            hw.setAttribute ("class", "headWord");
            hw.appendChild (doc.createTextNode (headWord));
            element.appendChild (hw);
        }

        if (pronunciation != null)
            element.appendChild (pronunciation.toElement (doc));

        return element;
    }

    public String toString () {
        return "Head Group";
    }
}
