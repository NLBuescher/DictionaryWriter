package com.nlbuescher.dictionarywriter.dictionary;

import com.nlbuescher.dictionarywriter.parser.EntryLexer;
import com.nlbuescher.dictionarywriter.parser.EntryParser;
import com.nlbuescher.dictionarywriter.parser.EntryParser.*;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;

public class D_entry implements Serializable {
    private String title;
    private String entryText;
    private String indexText;


    public D_entry() {
        this.title = "";
        this.entryText = "";
        this.indexText = "";
    }

    public D_entry(Element element) throws Exception {
        this();
        if (!element.getTagName().equals("d:entry"))
            throw new Exception("The element is does not have the proper tag!");

        this.title = element.getAttribute("d:title"); // Replaced convoluted way of determining title from heading
                                                      // element with 'd:title' attribute retrieval
        getTextFromElement(element);
    }

    public String getEntryText() {
        return entryText;
    }

    public void setEntryText(String entryText) {
        this.entryText = entryText;
        updateTitle();
    }

    public String getIndexText() {
        return this.indexText;
    }

    public void setIndexText(String indexText) {
        this.indexText = indexText;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private void updateTitle() {
        if (!this.entryText.replaceAll("# (.*?) #.*", "$1").equals(entryText))
            this.title = this.entryText.replaceAll("# (.*?) #.*", "$1").replaceAll("%", "");
    }

    public Element toElementWithDocument(Document document) {

        final EntryLexer lexer = new EntryLexer(new ANTLRInputStream(entryText));
        final CommonTokenStream tokens = new CommonTokenStream(lexer);
        final EntryParser parser = new EntryParser(tokens);

        final D_entryContext context = parser.d_entry();

        return elementFromContextWithDocument(context, document);
    }

    private Element elementFromContextWithDocument(ParserRuleContext context, Document document) {

        // Determine the tag name of the Element
        String tag;

        if (context instanceof D_entryContext)
            tag = "d:entry";
        else
            tag = "span";

        // Create the Element
        Element element = document.createElement(tag);


        // d_entry
        if (context instanceof D_entryContext) {

            D_entryContext d_entryContext = (D_entryContext) context;


            if (d_entryContext.headGroup() != null) {
                String heading = d_entryContext.headGroup().heading().getText().replaceAll("#\\s+(.+?)\\s+#", "$1");
                element.setAttribute("d:title", heading);
                element.setAttribute("id", heading);

                // Parse headGroup
                element.appendChild(elementFromContextWithDocument(d_entryContext.headGroup(), document));
            }

            // Parse EntryGroup
            if (d_entryContext.entryGroup() != null)
                element.appendChild(elementFromContextWithDocument(d_entryContext.entryGroup(), document));

            insertIndicesToElementWithDocument(element, document);
        }
        
        // headGroup
        else if (context instanceof HeadGroupContext) {

            HeadGroupContext headGroupContext = (HeadGroupContext) context;

            element.setAttribute("class", "headGroup");

            // Parse heading (mandatory)
            element.appendChild(elementFromContextWithDocument(headGroupContext.heading(), document));

            // Parser pronunciationGroup
            if (headGroupContext.pronunciationGroup() != null)
                element.appendChild(elementFromContextWithDocument(headGroupContext.pronunciationGroup(), document));
        }

        // heading
        else if (context instanceof HeadingContext) {

            HeadingContext headingContext = (HeadingContext) context;

            element.setAttribute("class", "heading");

            // Parse entryText
            String heading = headingContext.getText().replaceAll("#\\s+(.+?\\s)\\s*#", "$1");
            element.appendChild(document.createTextNode(heading));
        }

        // pronunciationGroup
        else if (context instanceof PronunciationGroupContext) {

            PronunciationGroupContext pronunciationGroupContext = (PronunciationGroupContext) context;

            element.setAttribute("class", "pronunciationGroup");

            element.appendChild(document.createTextNode("|"));

            // Parse pronunciations
            for (PronunciationContext pronunciationContext : pronunciationGroupContext.pronunciation())
                element.appendChild(elementFromContextWithDocument(pronunciationContext, document));

            element.appendChild(document.createTextNode("|"));
        }

        // pronunciation
        else if (context instanceof PronunciationContext) {

            PronunciationContext pronunciationContext = (PronunciationContext) context;

            // Get d:pr
            String d_pr = pronunciationContext.getText().replaceAll("\\|.+?\\|\\((.+?)\\)", "$1");

            element.setAttribute("d:pr", d_pr);

            // Get pronunciation entryText
            String pronunciation = pronunciationContext.getText().replaceAll("\\|(.+?)\\|\\(.+?\\)", "$1");

            element.appendChild(document.createTextNode(pronunciation));
        }

        // entryGroup
        else if (context instanceof EntryGroupContext) {

            EntryGroupContext entryGroupContext = (EntryGroupContext) context;

            element.setAttribute("class", "entryGroup");

            // Parse entries
            for (EntryContext entryContext : entryGroupContext.entry())
                element.appendChild(elementFromContextWithDocument(entryContext, document));
        }

        // entry
        else if (context instanceof EntryContext) {

            EntryContext entryContext = (EntryContext) context;

            element.setAttribute("class", "entry");

            // Parse grammarGroup
            if (entryContext.grammarGroup() != null)
                element.appendChild(elementFromContextWithDocument(entryContext.grammarGroup(), document));

            // Parse definitionGroups
            for (DefinitionGroupContext definitionGroupContext : entryContext.definitionGroup())
                element.appendChild(elementFromContextWithDocument(definitionGroupContext, document));

            // Parse sub-entry group
            if (entryContext.subEntryGroup() != null)
                element.appendChild(elementFromContextWithDocument(entryContext.subEntryGroup(), document));
        }

        // grammarGroup
        else if (context instanceof GrammarGroupContext) {

            GrammarGroupContext grammarGroupContext = (GrammarGroupContext) context;

            element.setAttribute("class", "grammarGroup");

            // Parse grammer (mandatory) (intentionally misspelled [grammar is a keyword in ANTLR])
            element.appendChild(elementFromContextWithDocument(grammarGroupContext.grammer(), document));

            // Parse formGroup
            if (grammarGroupContext.formGroup() != null)
                element.appendChild(elementFromContextWithDocument(grammarGroupContext.formGroup(), document));
        }

        // grammar
        else if (context instanceof GrammerContext) {

            GrammerContext grammerContext = (GrammerContext) context;

            // Get grammar
            String grammar = grammerContext.getText().replaceAll("## (.+?) ##", "$1");
            element.setAttribute("class", "grammar");
            element.appendChild(document.createTextNode(grammar));
        }

        // formGroup
        else if (context instanceof FormGroupContext) {

            FormGroupContext formGroupContext = (FormGroupContext) context;

            element.setAttribute("class", "formGroup");

            element.appendChild(document.createTextNode("("));

            // Parse forms
            int size = formGroupContext.form().size();
            for (FormContext formContext : formGroupContext.form()) {
                element.appendChild(elementFromContextWithDocument(formContext, document));

                if (--size > 0)
                    element.appendChild(document.createTextNode(","));
            }

            element.appendChild(document.createTextNode(")"));
        }

        // form
        else if (context instanceof FormContext) {

            FormContext formContext = (FormContext) context;

            element.setAttribute("class", "form");

            // Get form label
            String formLabel = formContext.getText().replaceAll("\\{(.+?)\\}\\(.+?\\).*", "$1") + ": ";
            Element label = document.createElement("span");
            label.setAttribute("class", "formLabel");
            label.appendChild(document.createTextNode(formLabel));
            element.appendChild(label);

            // Get form entryText
            String formText = formContext.getText().replaceAll("\\{.+?\\}\\((.+?)\\).*", "$1") + " ";
            Element text = document.createElement("span");
            text.setAttribute("class", "formText");
            text.appendChild(document.createTextNode(formText));
            element.appendChild(text);

            // Parse pronunciationGroup
            if (formContext.pronunciationGroup() != null)
                element.appendChild(elementFromContextWithDocument(formContext.pronunciationGroup(), document));
        }

        // definitionGroup
        else if (context instanceof DefinitionGroupContext) {

            DefinitionGroupContext definitionGroupContext = (DefinitionGroupContext) context;

            element.setAttribute("class", "definitionGroup");

            // Parse definition group heading
            if (definitionGroupContext.definitionGroupHeading() != null)
                element.appendChild(elementFromContextWithDocument(definitionGroupContext.definitionGroupHeading(), document));

            // Parse definitions
            for (DefinitionContext definitionContext : definitionGroupContext.definition())
                element.appendChild(elementFromContextWithDocument(definitionContext, document));
        }

        // definitionGroupHeading
        else if (context instanceof DefinitionGroupHeadingContext) {

            DefinitionGroupHeadingContext definitionGroupHeadingContext = (DefinitionGroupHeadingContext) context;

            // Get definition group heading
            String definitionGroupHeading = definitionGroupHeadingContext.getText().replaceAll("###\\s+(.+?)\\s+###", "$1");
            element.setAttribute("class", "definitionGroupHeading");
            element.appendChild(document.createTextNode(definitionGroupHeading));
        }

        // definition
        else if (context instanceof DefinitionContext) {

            DefinitionContext definitionContext = (DefinitionContext) context;

            element.setAttribute("class", "definition");

            if (!definitionContext.Definition().getText().equals("<missing Definition>")) {
                // Get definition label
                String definitionLabel = definitionContext.Definition().getText().replaceAll("([0-9]+\\.|-)\\s+.*", "$1").replaceAll("([0-9]+)\\.", "$1").replaceAll("-", "•").trim();
                Element label = document.createElement("span");
                label.setAttribute("class", "definitionLabel");
                label.appendChild(document.createTextNode(definitionLabel));
                element.appendChild(label);

                // Get specification
                String specificationText = definitionContext.Definition().getText().replaceAll("([0-9]+\\.|-)\\s+(\\[.*\\]).*", "$2").trim();

                if (!specificationText.equals(definitionContext.Definition().getText().trim())) {
                    Element specification = document.createElement("span");
                    specification.setAttribute("class", "specification");
                    specification.appendChild(document.createTextNode(specificationText));
                    element.appendChild(specification);
                }

                // Get definition entryText
                String definitionText = definitionContext.Definition().getText().replaceAll("([0-9]+\\.|-)(\\s+\\[.*\\])?\\s+(.*)", "$3").trim();
                Element text = document.createElement("span");
                text.setAttribute("class", "definitionText");
                text.appendChild(document.createTextNode(definitionText));
                element.appendChild(text);

                // Parse example group
                if (definitionContext.exampleGroup() != null)
                    element.appendChild(elementFromContextWithDocument(definitionContext.exampleGroup(), document));

                // Parse sub-definition group
                if (definitionContext.subDefinitionGroup() != null)
                    element.appendChild(elementFromContextWithDocument(definitionContext.subDefinitionGroup(), document));
            }
        }

        // exampleGroup
        else if (context instanceof ExampleGroupContext) {

            ExampleGroupContext exampleGroupContext = (ExampleGroupContext) context;

            element.setAttribute("class", "exampleGroup");

            // Parse examples
            for (ExampleContext exampleContext : exampleGroupContext.example())
                element.appendChild(elementFromContextWithDocument(exampleContext, document));
        }

        // example
        else if (context instanceof ExampleContext) {

            ExampleContext exampleContext = (ExampleContext) context;

            element.setAttribute("class", "example");

            // Get example label
            String exampleLabel = ":";
            Element label = document.createElement("span");
            label.setAttribute("class", "exampleLabel");
            label.appendChild(document.createTextNode(exampleLabel));
            element.appendChild(label);

            // Get example text
            String exampleText = exampleContext.exampleText().getText().replaceAll("[\\s\\t]*: (.*)", "$1").trim();
            Element text = document.createElement("span");
            text.setAttribute("class", "exampleText");
            text.appendChild(document.createTextNode(exampleText));
            element.appendChild(text);

            // Get example translation
            if (exampleContext.exampleTranslation() != null) {
                String exampleTranslation = exampleContext.exampleTranslation().getText().replaceAll("[\\s\\t]*--- (.*)", " — $1").trim();
                Element translation = document.createElement("span");
                translation.setAttribute("class", "exampleTranslation");
                translation.appendChild(document.createTextNode(exampleTranslation));
                element.appendChild(translation);
            }
        }

        // subDefinitionGroup
        else if (context instanceof SubDefinitionGroupContext) {

            SubDefinitionGroupContext subDefinitionGroupContext = (SubDefinitionGroupContext) context;

            element.setAttribute("class", "subDefinitionGroup");

            // Parse sub-definitions
            for (SubDefinitionContext subDefinitionContext : subDefinitionGroupContext.subDefinition())
                element.appendChild(elementFromContextWithDocument(subDefinitionContext, document));
        }

        // subDefinition
        else if (context instanceof SubDefinitionContext) {

            SubDefinitionContext subDefinitionContext = (SubDefinitionContext) context;

            element.setAttribute("class", "subDefinition");

            // Get sub-definition label
            String subDefinitionLabel = "•";
            Element label = document.createElement("span");
            label.setAttribute("class", "subDefinitionLabel");
            label.appendChild(document.createTextNode(subDefinitionLabel));
            element.appendChild(label);

            // Get specification
            String specificationText = subDefinitionContext.SubDefinition().getText().replaceAll("[\\s\\t]*-\\s+(\\[.*\\]).*", "$1").trim();
            if (!specificationText.equals(subDefinitionContext.getText().trim())) {
                Element specification = document.createElement("span");
                specification.setAttribute("class", "specification");
                specification.appendChild(document.createTextNode(specificationText));
                element.appendChild(specification);
            }

            // Get definition entryText
            String subDefinitionText = subDefinitionContext.SubDefinition().getText().replaceAll("[\\s\\t]*-(\\s+\\[.*\\])?\\s+(.*)", "$2").trim();
            Element text = document.createElement("span");
            text.setAttribute("class", "subDefinitionText");
            text.appendChild(document.createTextNode(subDefinitionText));
            element.appendChild(text);

            // Parse example group
            if (subDefinitionContext.exampleGroup() != null)
                element.appendChild(elementFromContextWithDocument(subDefinitionContext.exampleGroup(), document));
        }

        // subEntryGroup
        else if (context instanceof SubEntryGroupContext) {

            SubEntryGroupContext subEntryGroupContext = (SubEntryGroupContext) context;

            element.setAttribute("class", "subEntryGroup");

            // Parse sub-entries
            for (SubEntryContext subEntryContext : subEntryGroupContext.subEntry())
                element.appendChild(elementFromContextWithDocument(subEntryContext, document));
        }

        // subEntry
        else if (context instanceof SubEntryContext) {

            SubEntryContext subEntryContext = (SubEntryContext) context;

            element.setAttribute("class", "subEntry");

            // Get sub-entry label
            String subEntryLabel = subEntryContext.SubEntryLabel().getText().replaceAll("(.+?)\\n[-]+\\n", "$1");
            Element label = document.createElement("span");
            label.setAttribute("class", "subEntryLabel");
            label.appendChild(document.createTextNode(subEntryLabel));
            element.appendChild(label);

            // Parse sub-entry list
            if (subEntryContext.subEntryList() != null)
                element.appendChild(elementFromContextWithDocument(subEntryContext.subEntryList(), document));

            // Get sub-entry entryText
            if (subEntryContext.subEntryList() == null) {
                String subEntryText = subEntryContext.SubEntryText().getText().replaceAll("'''[ \n](.+)[ \n]'''", "$1").trim();
                Element text = document.createElement("span");
                text.setAttribute("class", "subEntryText");
                text.appendChild(document.createTextNode(subEntryText));
                element.appendChild(text);
            }

            // Parse note group
            if (subEntryContext.noteGroup() != null)
                element.appendChild(elementFromContextWithDocument(subEntryContext.noteGroup(), document));
        }

        // subEntryList
        else if (context instanceof SubEntryListContext) {

            SubEntryListContext subEntryListContext = (SubEntryListContext) context;

            element.setAttribute("class", "subEntryList");

            // Parse sub-entry list items
            for (SubEntryListItemContext subEntryListItemContext : subEntryListContext.subEntryListItem())
                element.appendChild(elementFromContextWithDocument(subEntryListItemContext, document));
        }

        // subEntryListItem
        else if (context instanceof SubEntryListItemContext) {

            SubEntryListItemContext subEntryListItemContext = (SubEntryListItemContext) context;

            element.setAttribute("class", "subEntryListItem");

            // Get sub-entry list item label
            String subEntryListItemLabel = "•";
            Element label = document.createElement("span");
            label.setAttribute("class", "subEntryListItemLabel");
            label.appendChild(document.createTextNode(subEntryListItemLabel));
            element.appendChild(label);

            // Get sub-entry list item entryText
            String subEntryListItemText = subEntryListItemContext.getText().replaceAll("-- (.*)", "$1").trim();
            Element text = document.createElement("span");
            text.setAttribute("class", "subEntryListItemText");
            text.appendChild(document.createTextNode(subEntryListItemText));
            element.appendChild(text);
        }

        // noteGroup
        else if (context instanceof NoteGroupContext) {

            NoteGroupContext noteGroupContext = (NoteGroupContext) context;

            element.setAttribute("class", "noteGroup");

            // Parse notes
            for (NoteContext noteContext : noteGroupContext.note())
                element.appendChild(elementFromContextWithDocument(noteContext, document));
        }

        // note
        else if (context instanceof NoteContext) {

            NoteContext noteContext = (NoteContext) context;

            element.setAttribute("class", "note");

            // Get note
            String note = noteContext.getText().replaceAll("\"\"\"[\\n\\s]+(.*)[\\n\\s]+\"\"\"", "$1"). trim();
            element.appendChild(document.createTextNode(note));
        }

        return element;
    }

    private void insertIndicesToElementWithDocument(Element element, Document document) {

        String[] indices = indexText.split("\n");
        for (int i = indices.length - 1; i >= 0; i--) {

            String[] indexValues = indices[i].split(":");
            if (indexValues.length == 2) {
                Element index = document.createElement("d:index");

                index.setAttribute("d:title", indexValues[0].trim());
                index.setAttribute("d:value", indexValues[1].trim());

                element.insertBefore(index, element.getFirstChild());
            } else {
                System.err.println("The index (" + indices[i] +  ") was malformed! Ignoring it.");
            }
        }
    }

    private void getTextFromElement(Element element) {

        switch (element.getTagName()) {

            case "d:entry":
                processChildrenForElement(element);
                break;
            case "d:index":
                indexText += element.getAttribute("d:title")
                        + ":"
                        + element.getAttribute("d:value")
                        + "\n";
                break;
            case "span":
                switch (element.getAttribute("class")) {

                    case "headGroup":
                        processChildrenForElement(element);
                        entryText += "\n";
                        break;
                    case "heading":
                        entryText += "# " + title + " #\n";
                        break;
                    case "pronunciationGroup":
                        NodeList children = element.getChildNodes();
                        for (int i = 0; i < children.getLength(); i++) {
                            Node node = children.item(i);
                            if (node.getNodeType() == Node.ELEMENT_NODE
                                    && ((Element) node).hasAttribute("d:pr")) {

                                if (((Element) element.getParentNode()).getAttribute("class").equals("form"))
                                    entryText += "    ";

                                entryText += "|" + node.getTextContent() + "|(" + ((Element) node).getAttribute("d:pr") + ")\n";
                            }
                        }
                        break;
                    case "entryGroup":
                        processChildrenForElement(element);
                        break;
                    case "entry":
                        processChildrenForElement(element);
                        break;
                    case "grammarGroup":
                        processChildrenForElement(element);
                        entryText += "\n";
                        break;
                    case "grammar":
                        entryText += "## " + element.getTextContent().trim() + " ##\n";
                        break;
                    case "formGroup":
                        processChildrenForElement(element);
                        break;
                    case "form":
                        for (int i = 0; i < element.getChildNodes().getLength(); i++) {
                            Node node = element.getChildNodes().item(i);
                            if (node.getNodeType() == Node.ELEMENT_NODE) {
                                Element child = (Element) node;
                                switch (child.getAttribute("class")) {
                                    case "formLabel":
                                        entryText += "{" + child.getTextContent().replaceAll(": ", "").trim() + "}";
                                        break;
                                    case "formText":
                                        entryText += "(" + child.getTextContent().trim() + ")\n";
                                        break;
                                    default:
                                        getTextFromElement(child);
                                }
                            }
                        }
                        break;
                    case "definitionGroup":
                        processChildrenForElement(element);
                        entryText += "\n";
                        break;
                    case "definitionGroupHeading":
                        entryText += "### " + element.getTextContent().trim() + " ###\n";
                        break;
                    case "definition":
                        for (int i = 0; i < element.getChildNodes().getLength(); i++) {
                            Node node = element.getChildNodes().item(i);
                            if (node.getNodeType() == Node.ELEMENT_NODE) {
                                Element child = (Element) node;
                                switch (child.getAttribute("class")) {
                                    case "definitionLabel":
                                        if (child.getTextContent().trim().matches("[0-9]+"))
                                            entryText += child.getTextContent().trim() + ". ";
                                        else
                                            entryText += "- ";
                                        break;
                                    case "specification":
                                        entryText += child.getTextContent().trim() + " ";
                                        break;
                                    case "definitionText":
                                        entryText += child.getTextContent().trim() + "\n";
                                        break;
                                    default:
                                        getTextFromElement(child);
                                }
                            }
                        }
                        break;
                    case "exampleGroup":
                        processChildrenForElement(element);
                        break;
                    case "example":
                        String indent = "    ";
                        // Check if the example is inside a subDefinition and increase the indent if so
                        if (element.getParentNode().getParentNode().getNodeType() == Node.ELEMENT_NODE
                                && ((Element) element.getParentNode().getParentNode()).getAttribute("class").equals("subDefinition"))
                            indent += "    ";

                        for (int i = 0; i < element.getChildNodes().getLength(); i++) {
                            Node node = element.getChildNodes().item(i);
                            if (node.getNodeType() == Node.ELEMENT_NODE) {
                                Element child = (Element) node;
                                switch (child.getAttribute("class")) {
                                    case "exampleLabel":
                                        break;
                                    case "exampleText":
                                        entryText += indent + ": " + child.getTextContent().trim() + "\n";
                                        break;
                                    case "exampleTranslation":
                                        entryText += indent + "--- " + child.getTextContent().replaceAll(" — ", "").trim() + "\n";
                                        break;
                                    default:
                                        getTextFromElement(child);
                                }
                            }
                        }
                        break;
                    case "subDefinitionGroup":
                        processChildrenForElement(element);
                        break;
                    case "subDefinition":
                        for (int i = 0; i < element.getChildNodes().getLength(); i++) {
                            Node node = element.getChildNodes().item(i);
                            if (node.getNodeType() == Node.ELEMENT_NODE) {
                                Element child = (Element) node;
                                switch (child.getAttribute("class")) {
                                    case "subDefinitionLabel":
                                        entryText += "    - ";
                                        break;
                                    case "specification":
                                        entryText += child.getTextContent().trim() + " ";
                                        break;
                                    case "subDefinitionText":
                                        entryText += child.getTextContent().trim() + "\n";
                                        break;
                                    default:
                                        getTextFromElement(child);
                                }
                            }
                        }
                        break;
                    case "subEntryGroup":
                        processChildrenForElement(element);
                        break;
                    case "subEntry":
                        processChildrenForElement(element);
                        break;
                    case "subEntryLabel":
                        String underline = "";

                        for (int i = 0; i < element.getTextContent().trim().length(); i++)
                            underline += "-";

                        entryText += element.getTextContent().trim() + "\n" + underline + "\n\n";
                        break;
                    case "subEntryText":
                        entryText += "'''\n" + element.getTextContent().trim() + "\n'''\n\n";
                        break;
                    case "subEntryList":
                        processChildrenForElement(element);
                        entryText += "\n";
                        break;
                    case "subEntryListItem":
                        for (int i = 0; i < element.getChildNodes().getLength(); i++) {
                            Node node = element.getChildNodes().item(i);
                            if (node.getNodeType() == Node.ELEMENT_NODE) {
                                Element child = (Element) node;
                                switch (child.getAttribute("class")) {
                                    case "subEntryListItemLabel":
                                        entryText += "-- ";
                                        break;
                                    case "specification":
                                        entryText += child.getTextContent().trim() + " ";
                                        break;
                                    case "subEntryListItemText":
                                        entryText += child.getTextContent().trim() + "\n";
                                        break;
                                    default:
                                        getTextFromElement(child);
                                }
                            }
                        }
                        break;
                    case "noteGroup":
                        processChildrenForElement(element);
                        break;
                    case "note":
                        entryText += "\"\"\"\n" + element.getTextContent().trim() + "\n\"\"\"\n\n";
                        break;
                    default:
                        System.err.println("The span's class did not match any expected inputs! Ignoring.");
                }
                break;
            default:
                System.err.println("The element's tag did not match any expected inputs! Ignoring.");
        }
    }

    private void processChildrenForElement(Element element) {
        for (int i = 0; i < element.getChildNodes().getLength(); i++) {
            Node node = element.getChildNodes().item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE)
                getTextFromElement((Element) node);
        }
    }

    @Override public String toString() {
        return this.title;
    }

    public static void main(String[] args) {

        String text = "";

        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File("parserTest")));
            D_entry test = new D_entry();
            String line;
            while ((line = reader.readLine()) != null) {
                text += line + "\n";
            }

            System.out.println(text);

            test.entryText = text;


            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            Element element = test.toElementWithDocument(document);
            element.setAttribute("xmlns", "http://www.w3.org/1999/xhtml");
            element.setAttribute("xmlns:d", "http://www.apple.com/DTDs/DictionaryService-1.0.rng");
            document.appendChild(element);

            DOMSource source = new DOMSource(document);
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            transformer.transform(source, result);

            String outString = writer.toString()
                    .replaceAll("\\?><", "?>\n<")
                    .replaceFirst(" standalone=\"no\"", "")
                    .replaceFirst("<d:dictionary", "\n<d:dictionary")
                    .replaceAll("\\n[\\s]*<span d:pr", "<span d:pr")
                    .replaceAll(">\\(<", ">\n" + indent(5) + "(\n" + indent(5) + "<")
                    .replaceAll(">,<", ">\n" + indent(5) + ",\n" + indent(5) + "<")
                    .replaceAll(">\\)<", ">\n" + indent(5) + ")\n" + indent(4) + "<")
                    .replaceAll("%(.)", "<span id=\"$1\"/>")
                    .replaceAll("\\*\\*\\*(.*?)\\*\\*\\*", "<b><i>$1</i></b>")
                    .replaceAll("\\*\\*(.*?)\\*\\*", "<b>$1</b>")
                    .replaceAll("\\*(.*?)\\*", "<i>$1</i>")
                    .replaceAll("\\^\\((.*?)\\)", "<sup>$1</sup>")
                    .replaceAll("\\^(.)", "<sup>$1</sup>");

            System.out.println(outString);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String indent(int length) {
        String space = "    ";
        String indent = "";
        for (int i = 0; i < length; i++)
            indent += space;

        return indent;
    }
}
