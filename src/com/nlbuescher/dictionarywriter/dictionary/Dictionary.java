package com.nlbuescher.dictionarywriter.dictionary;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Dictionary implements Serializable {

    private final String XMLNS = "http://www.w3.org/1999/xhtml";
    private final String XMLNS_D = "http://www.apple.com/DTDs/DictionaryService-1.0.rng";

    private List<D_entry> entries;

    public Dictionary() {
        entries = new ArrayList<>();
    }

    /**
     * The text input is assumed to be from a MarkDown Dictionary file and therefore have a specific format
     * @param text the Dictionary text with which to initialize the entry
     */
    public Dictionary(String text) {
        this();
        String[] entryStrings = text.split("\n\n==========\n\n");

        for (String entry : entryStrings)
            entries.add(new D_entry(entry));
    }

    public Dictionary(Element element) throws Exception {
        if (!element.getTagName().equals("d:dictionary"))
            throw new Exception("The element is not a 'd:dictionary'");

        NodeList nodes = element.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals("d:entry"))
                entries.add(new D_entry((Element) node));
        }
    }

    public List<D_entry> getEntries() {
        return entries;
    }

    public Element toElementWithDocument(Document document) {
        Element element = document.createElement("d:dictionary");

        element.setAttribute("xmlns", XMLNS);
        element.setAttribute("xmlns:d", XMLNS_D);

        for (D_entry entry : entries)
            element.appendChild(entry.toElementWithDocument(document));

        return element;
    }

    public String getTextContent() {
        String text = "";

        int size = entries.size();
        for (D_entry entry : entries) {
            text += entry.getEntryText();

            if (--size != 0)
                text += "\n\n==========\n\n";
        }

        return text;
    }

    public String toString() {
        return "Dictionary";
    }
}
