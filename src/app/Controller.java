package app;

import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import org.w3c.dom.*;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.FileOutputStream;
import java.io.StringWriter;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    public BorderPane root;

    public MenuBar  menuBar;
    public MenuItem openMenuItem;
    public MenuItem saveMenuItem;
    public MenuItem saveAsMenuItem;

    public TreeView<Node> treeView;

    public ScrollPane editorScrollPane;
    public VBox       editorVBox;
    public WebView    previewWebView;

    private boolean  documentLoaded = false;
    private Document document;
    private File     currentFile;


    @Override
    public void initialize (URL location, ResourceBundle resources) {
        String os = System.getProperty ("os.name");

        if (os != null && (os.startsWith ("Mac"))) {
            menuBar.setUseSystemMenuBar (true);
            openMenuItem.setAccelerator (new KeyCodeCombination (KeyCode.O, KeyCombination.META_DOWN));
            saveMenuItem.setAccelerator (new KeyCodeCombination (KeyCode.S, KeyCombination.META_DOWN));
            saveAsMenuItem.setAccelerator (new KeyCodeCombination (KeyCode.S, KeyCombination.META_DOWN, KeyCombination.SHIFT_DOWN));
        }

        treeView.getSelectionModel ().selectedItemProperty ().addListener ((observable, oldValue, newValue) -> {
            updateEditor (newValue);
        });
    }

    private void updateEditor (TreeItem<Node> treeItem) {

        editorVBox.getChildren ().clear ();

        switch (treeItem.getValue ().getNodeType ()) {
            case Node.TEXT_NODE:
            case Node.COMMENT_NODE:
                Label textLabel = new Label ("Text");
                editorVBox.getChildren ().add (textLabel);

                TextArea textArea = new TextArea ();
                textArea.setText (treeItem.getValue ().getTextContent ().trim ());
                textArea.focusedProperty ().addListener ((observable, oldValue, newValue) -> {
                    if (!newValue) {
                        treeItem.getValue ().setTextContent (" " + textArea.getText () + " ");
                    }
                });

                textArea.setPrefHeight (Region.USE_COMPUTED_SIZE);
                textArea.setPrefWidth (Region.USE_COMPUTED_SIZE);
                editorVBox.getChildren ().add (textArea);
                break;
            case Node.ELEMENT_NODE:
                Label attributeLabel = new Label ("Attributes");
                editorVBox.getChildren ().add (attributeLabel);

                ListView<Attr> listView = new ListView<> ();
                listView.setEditable (false);
                listView.setPrefHeight (Region.USE_COMPUTED_SIZE);
                listView.setPrefWidth (Region.USE_COMPUTED_SIZE);


                NamedNodeMap attributes = treeItem.getValue ().getAttributes ();
                for (int i = 0; i < attributes.getLength (); i++) {
                    Attr attr = ((Attr) attributes.item (i));
                    listView.getItems ().add (attr);
                }

                editorVBox.getChildren ().add (listView);
                break;
            default:
                System.out.println (treeItem.getValue ().toString () + " has a node Type of " + treeItem.getValue ().getNodeType ());
                break;
        }
    }

    private void loadFile () {
        Main.getStage ().setTitle ("Dictionary Writer - [" + currentFile.getAbsolutePath () + "]");

        WebEngine webEngine = previewWebView.getEngine ();
        try {
            webEngine.load (currentFile.toURI ().toURL ().toString ());
        } catch (Exception e) {
            e.printStackTrace ();
        }

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance ();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder ();
            document = builder.parse (currentFile);
            Element  element = document.getDocumentElement ();
            NodeList nodes   = element.getChildNodes ();

            XPathFactory xpathFactory = XPathFactory.newInstance ();
            // XPath to find empty text nodes.
            XPathExpression xpathExp       = xpathFactory.newXPath ().compile ("//text()[normalize-space(.) = '']");
            NodeList        emptyTextNodes = (NodeList) xpathExp.evaluate (document, XPathConstants.NODESET);

            // Remove each empty text node from document.
            for (int i = 0; i < emptyTextNodes.getLength (); i++) {
                Node emptyTextNode = emptyTextNodes.item (i);
                emptyTextNode.getParentNode ().removeChild (emptyTextNode);
            }

            TreeItem<Node> treeRoot = new TreeItem<> (element);
            treeView.setRoot (treeRoot);


            for (int i = 0; i < nodes.getLength (); i++) {
                Node node = nodes.item (i);

                addNodeToTree (node, treeRoot);
            }

        } catch (Exception e) {
            e.printStackTrace ();
        } finally {
            documentLoaded = true;
        }
    }

    private void addNodeToTree (Node node, TreeItem<Node> root) {

        TreeItem<Node> item = new TreeItem<> (node);
        root.getChildren ().add (item);

        NodeList children = node.getChildNodes ();

        for (int i = 0; i < children.getLength (); i++) {
            Node child = children.item (i);
            addNodeToTree (child, item);
        }
    }

    public void openFile () {
        FileChooser fileChooser = new FileChooser ();
        fileChooser.setTitle ("Open");
        fileChooser.getExtensionFilters ().add (new ExtensionFilter ("XHTML/XML Files", "*.xhtml", "*.XHTML", "*.xml", "*.XML"));

        File file = fileChooser.showOpenDialog (root.getScene ().getWindow ());
        if (file != null) {
            currentFile = file;
            loadFile ();
        }
    }

    public void saveFile () {
        if (documentLoaded) {
            try {
                TransformerFactory factory     = TransformerFactory.newInstance ();
                Transformer        transformer = factory.newTransformer ();
                transformer.setOutputProperty (OutputKeys.INDENT, "yes");
                transformer.setOutputProperty ("{http://xml.apache.org/xslt}indent-amount", "4");

                DOMSource    source = new DOMSource (document);
                StringWriter writer = new StringWriter ();
                StreamResult result = new StreamResult (writer);

                transformer.transform (source, result);

                String outString = writer.toString ()
                        .replaceAll ("\\?><", "?>\n<")
                        .replaceFirst (" standalone=\"no\"", "")
                        .replaceFirst ("<d:dictionary", "\n<d:dictionary")
                        .replaceAll ("\\n[\\s]*<span d:pr", "<span d:pr");

                FileOutputStream out = new FileOutputStream (currentFile);

                out.write (outString.getBytes ("UTF-8"));

                System.out.println ("File Saved!");
            } catch (Exception e) {
                e.printStackTrace ();
            }
        } else {
            System.err.println ("No document is loaded! Doing nothing! ^^");
        }
    }

    public void saveFileAs () {
        FileChooser fileChooser = new FileChooser ();
        fileChooser.setTitle ("Save As");
        fileChooser.getExtensionFilters ().add (new ExtensionFilter ("XHTML/XML Files", "*.xhtml", "*.XHTML", "*.xml", "*.XML"));

        File file = fileChooser.showSaveDialog (root.getScene ().getWindow ());
        if (file != null) {
            currentFile = file;
            saveFile ();
            loadFile ();
        }
    }

    public void newFileTest () {
        FileChooser fileChooser = new FileChooser ();
        fileChooser.setTitle ("New File Test Export");
        fileChooser.getExtensionFilters ().add (new ExtensionFilter ("XHTML/XML Files", "*.xhtml", "*.XHTML", "*.xml", "*.XML"));

        File file = fileChooser.showSaveDialog (root.getScene ().getWindow ());

        if (file != null) {

            try {
                // Build new Document
                DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance ();

                DocumentBuilder builder;
                builder = builderFactory.newDocumentBuilder ();

                // Document
                Document              doc = builder.newDocument ();
                ProcessingInstruction css = doc.createProcessingInstruction ("xml-stylesheet", "type=\"text/css\" href=\"style.css\"");
                ProcessingInstruction xsl = doc.createProcessingInstruction ("xml-stylesheet", "type=\"text/xsl\" href=\"webtransform.xsl\"");

                // Root element
                Element rootElement = doc.createElement ("d:dictionary");
                rootElement.setAttribute ("xmlns", "http://www.w3.org/1999/xhtml");
                rootElement.setAttribute ("xmlns:d", "http://www.apple.com/DTDs/DictionaryService-1.0.rng");
                doc.appendChild (rootElement);
                doc.insertBefore (css, rootElement);
                doc.insertBefore (xsl, rootElement);

                // Test Entry
                Element entry = doc.createElement ("d:entry");
                entry.setAttribute ("id", "arma");
                entry.setAttribute ("d:title", "arma");
                rootElement.appendChild (entry);

                // Indices
                Element index1 = doc.createElement ("d:index");
                index1.setAttribute ("d:value", "arma");
                index1.setAttribute ("d:title", "arma");
                entry.appendChild (index1);
                Element index2 = doc.createElement ("d:index");
                index2.setAttribute ("d:value", "armōrum");
                index2.setAttribute ("d:title", "armōrum");
                entry.appendChild (index2);
                Element index3 = doc.createElement ("d:index");
                index3.setAttribute ("d:value", "armōrvm");
                index3.setAttribute ("d:title", "armōrum");
                entry.appendChild (index3);
                Element index4 = doc.createElement ("d:index");
                index4.setAttribute ("d:value", "armīs");
                index4.setAttribute ("d:title", "armīs");
                entry.appendChild (index4);

                // Head Group
                Element headGroup = doc.createElement ("span");
                headGroup.setAttribute ("class", "headGroup");
                entry.appendChild (headGroup);

                Element headWord = doc.createElement ("span");
                headWord.setAttribute ("class", "headWord");
                headGroup.appendChild (headWord);

                headWord.appendChild (doc.createTextNode ("arma"));

                Element pronunciation = doc.createElement ("span");
                pronunciation.setAttribute ("class", "pronunciation");
                headGroup.appendChild (pronunciation);

                pronunciation.appendChild (doc.createTextNode ("|"));

                Element la_ipa = doc.createElement ("span");
                la_ipa.setAttribute ("d:pr", "LA_IPA");
                pronunciation.appendChild (la_ipa);

                la_ipa.appendChild (doc.createTextNode ("ˈar.ma"));

                pronunciation.appendChild (doc.createTextNode ("|"));


                // Export File
                TransformerFactory factory     = TransformerFactory.newInstance ();
                Transformer        transformer = factory.newTransformer ();
                transformer.setOutputProperty (OutputKeys.INDENT, "yes");
                transformer.setOutputProperty ("{http://xml.apache.org/xslt}indent-amount", "4");

                DOMSource    source = new DOMSource (doc);
                StringWriter writer = new StringWriter ();
                StreamResult result = new StreamResult (writer);

                transformer.transform (source, result);

                String outString = writer.toString ()
                        .replaceAll ("\\?><", "?>\n<")
                        .replaceFirst (" standalone=\"no\"", "")
                        .replaceFirst ("<d:dictionary", "\n<d:dictionary");

                FileOutputStream out = new FileOutputStream (file);

                out.write (outString.getBytes ("UTF-8"));

                System.out.println ("Test File Exported!");
            } catch (Exception e) {
                e.printStackTrace ();
            }
        }
    }

    public void removeSelectedElement () {
        ObservableList<TreeItem<Node>> selection = treeView.getSelectionModel ().getSelectedItems ();

        for (TreeItem<Node> item : selection) {
            item.getValue ().getParentNode ().removeChild (item.getValue ());
            item.getParent ().getChildren ().remove (item);
        }
    }

    public void addNewElement () {

    }
}
