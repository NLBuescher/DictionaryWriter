package com.nlbuescher.dictionarywriter.dictionary;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.ParserConfigurationException;

/**
 * Note
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
public class Note {
    private String noteText = "";


    public String getNoteText() {
        return noteText;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }


    public static Note fromElement(Element element) {
        Note note = new Note();

        note.noteText = element.getTextContent();

        return note;
    }

    public Element toElement(Document doc) throws ParserConfigurationException {
        Element element = doc.createElement("span");
        element.setAttribute("class", "note");
        element.appendChild(doc.createTextNode(noteText));

        return element;
    }

    public String toString() {
        return "Note";
    }
}
