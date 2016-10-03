package com.nlbuescher.dictionarywriter;

import com.nlbuescher.dictionarywriter.dictionary.D_entry;
import com.nlbuescher.dictionarywriter.dictionary.Dictionary;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

import netscape.javascript.JSObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.*;
import java.net.URL;
import java.util.*;

/**
 * DictionaryWriter Controller
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
public class Controller implements Initializable {
    private boolean documentLoaded = false;
    private boolean newFile = false;
    private File currentFile;
    private Dictionary dictionary;


    @FXML private BorderPane root;

    @FXML private MenuBar menuBar;
    @FXML private MenuItem newMenuItem;
    @FXML private MenuItem openMenuItem;
    @FXML private MenuItem importMenuItem;
    @FXML private MenuItem saveMenuItem;
    @FXML private MenuItem saveAsMenuItem;
    @FXML private MenuItem exportMenuItem;

    @FXML private TreeView<Object> treeView;

    @FXML private WebView entryEditor;
    @FXML private WebView indexEditor;
    @FXML private WebView cssEditor;

    @FXML private WebView preview;

    @FXML private Label statusBarLabel;


    @Override public void initialize(URL location, ResourceBundle resources) {
        String os = System.getProperty("os.name");

        if (os != null && (os.startsWith("Mac"))) {
            menuBar.setUseSystemMenuBar(true);
            newMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.META_DOWN));
            openMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.META_DOWN));
            importMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.I, KeyCombination.META_DOWN));
            saveMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.META_DOWN));
            saveAsMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.META_DOWN, KeyCombination.SHIFT_DOWN));
            exportMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.E, KeyCombination.META_DOWN));
        }

        String editorInitString = "<pre id=\"editor\" contenteditable=\"true\" style=\"outline: none; font-family: 'Menlo', monospace; font-size: 80%;\"></pre>";

        entryEditor.getEngine().loadContent(editorInitString);
        JSObject entryJS = (JSObject) entryEditor.getEngine().executeScript("window");
        entryJS.setMember("controller", this);
        entryEditor.getEngine().getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == Worker.State.SUCCEEDED) {
                entryEditor.getEngine().executeScript(""
                        + "document.getElementById('editor').addEventListener('input', function() {"
                        + "    controller.editorChanged(document.getElementById('editor').innerText + '\\n', 'entry')"
                        + "}, false)");
            }
        });

        indexEditor.getEngine().loadContent(editorInitString);
        JSObject indexJS = (JSObject) indexEditor.getEngine().executeScript("window");
        indexJS.setMember("controller", this);
        indexEditor.getEngine().getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == Worker.State.SUCCEEDED) {
                indexEditor.getEngine().executeScript(""
                        + "document.getElementById('editor').addEventListener('input', function() {"
                        + "    controller.editorChanged(document.getElementById('editor').innerText + '\\n', 'index')"
                        + "}, false)");
            }
        });

        cssEditor.getEngine().loadContent(editorInitString);

        treeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) { // Does not execute with null
                updateEntryEditor(newValue);
                updateIndexEditor(newValue);
                updatePreview(newValue);
            }
        });
    }

    @SuppressWarnings("unused") public void editorChanged(String newText, String source) {
        switch (source) {
            case "entry":
                ((D_entry) treeView.getSelectionModel().getSelectedItem().getValue()).setEntryText(newText);
                TreeItem<Object> treeItem = treeView.getSelectionModel().getSelectedItem();
                treeItem.getParent().getChildren().set(treeItem.getParent().getChildren().indexOf(treeItem), treeItem);
                updatePreview(treeView.getSelectionModel().getSelectedItem());
                break;
            case "index":
                ((D_entry) treeView.getSelectionModel().getSelectedItem().getValue()).setIndexText(newText);
                break;
        }
    }


    private void loadMD() {
        DictionaryWriter.getStage().setTitle("Dictionary Writer - [" + currentFile.getAbsolutePath().replaceAll("/Users/.+?/", "~/") + "]");

        try {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(currentFile));

            dictionary = (Dictionary) inputStream.readObject();

            TreeItem<Object> treeRoot = new TreeItem<>(dictionary);
            treeRoot.setExpanded(true);
            treeRoot.expandedProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue)
                    treeRoot.setExpanded(true);
            });

            for (D_entry entry : dictionary.getEntries())
                treeRoot.getChildren().add(new TreeItem<>(entry));

            treeView.setRoot(treeRoot);
            treeView.setShowRoot(false);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            documentLoaded = true;
        }
    }

    private void loadXML() {
        DictionaryWriter.getStage().setTitle("Dictionary Writer - Imported Dictionary [" + currentFile.getAbsolutePath().replaceAll("/Users/.+?/", "~/") + "]");

        try {
            String inputString = new Scanner(currentFile).useDelimiter("\\Z").next()
                    .replaceAll("<span id=\"(.)\"/>", "%$1")
                    .replaceAll("<b>(.*?)</b>", "**$1**")
                    .replaceAll("<i>(.*?)</i>", "*$1*")
                    .replaceAll("<sup>(.*?)</sup>", "^($1)");

            Element element = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(inputString))).getDocumentElement();
            dictionary = new Dictionary(element);

            TreeItem<Object> treeRoot = new TreeItem<>(dictionary);
            treeRoot.setExpanded(true);
            treeRoot.expandedProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue)
                    treeRoot.setExpanded(true);
            });

            for (D_entry entry : dictionary.getEntries())
                treeRoot.getChildren().add(new TreeItem<>(entry));

            treeView.setRoot(treeRoot);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            documentLoaded = true;
            currentFile = null;
        }
    }

    private void updateEntryEditor(TreeItem treeItem) {
        if (treeItem.getValue() instanceof D_entry) {
            D_entry entry = (D_entry) treeItem.getValue();
            entryEditor.getEngine().executeScript("document.getElementById('editor').innerText = '" + entry.getEntryText().trim().replace("\n", "\\n").replace("'", "\\'") + "';");
        }
    }

    private void updateIndexEditor(TreeItem treeItem) {
        if (treeItem.getValue() instanceof D_entry) {
            D_entry entry = (D_entry) treeItem.getValue();
            indexEditor.getEngine().executeScript("document.getElementById('editor').innerText = '" + entry.getIndexText().trim().replace("\n", "\\n").replace("'", "\\'") + "';");
        }
    }

    private void updatePreview(TreeItem treeItem) {
        if (treeItem != null) {
            TreeItem<Object> item = treeItem;

            if (!(item.getValue() instanceof Dictionary))
                while (!(item.getValue() instanceof D_entry))
                    item = item.getParent();

            if (item.getValue() instanceof D_entry) {
                try {
                    Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();

                    Dictionary dict = new Dictionary();
                    dict.getEntries().add((D_entry) item.getValue());
                    Element root = dict.toElementWithDocument(document);

                    document.appendChild(root);
                    document.insertBefore(document.createProcessingInstruction("xml-stylesheet", "type=\"text/css\" href=\"style.css\""), root);
                    document.insertBefore(document.createProcessingInstruction("xml-stylesheet", "type=\"text/xsl\" href=\"webtransform.xsl\""), root);

                    DOMSource source = new DOMSource(document);
                    StringWriter writer = new StringWriter();
                    StreamResult result = new StreamResult(writer);

                    TransformerFactory.newInstance().newTransformer().transform(source, result);

                    String previewString = filterMD(writer.toString());

                    File preview = new File("./~preview.xhtml").getCanonicalFile();
                    FileOutputStream out = new FileOutputStream(preview);
                    out.write(previewString.getBytes("UTF-8"));

                    WebEngine webEngine = this.preview.getEngine();
                    webEngine.load(preview.toURI().toURL().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    @FXML private void addNewEntry() {
        if (documentLoaded) {
            TextInputDialog dialog = new TextInputDialog("");
            dialog.setTitle("New Entry");
            dialog.setHeaderText(null);
            dialog.setContentText("Please enter an entry title:");

            D_entry newEntry = new D_entry();

            dialog.showAndWait().ifPresent(title -> {
                newEntry.setEntryText("# " + title + " #");
                newEntry.setTitle(title);
            });

            TreeItem<Object> selectedItem = treeView.getSelectionModel().getSelectedItem();

            // Entry List
            Dictionary dictionary = ((Dictionary) treeView.getRoot().getValue());
            D_entry selectedEntry = ((D_entry) selectedItem.getValue());
            int selectedEntryIndex = dictionary.getEntries().indexOf(selectedEntry);
            dictionary.getEntries().add(selectedEntryIndex+1, newEntry);

            // Tree View
            TreeItem<Object> newTreeItem = new TreeItem<>(newEntry);
            int selectedItemIndex = treeView.getRoot().getChildren().indexOf(selectedItem);
            treeView.getRoot().getChildren().add(selectedItemIndex+1, newTreeItem);
            treeView.getSelectionModel().select(newTreeItem);
        }
    }

    @FXML private void export() {
        if (documentLoaded) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Export...");
            fileChooser.getExtensionFilters().add(new ExtensionFilter("XHTML files", "*.xhtml", "*.XHTML"));

            File exportFile = fileChooser.showSaveDialog(root.getScene().getWindow());

            if (exportFile != null) {
                try {
                    Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();

                    Element root = dictionary.toElementWithDocument(document);

                    document.appendChild(root);
                    document.insertBefore(document.createProcessingInstruction("xml-stylesheet", "type=\"text/css\" href=\"style.css\""), root);
                    document.insertBefore(document.createProcessingInstruction("xml-stylesheet", "type=\"text/xsl\" href=\"webtransform.xsl\""), root);

                    DOMSource source = new DOMSource(document);
                    StringWriter writer = new StringWriter();
                    StreamResult result = new StreamResult(writer);

                    Transformer transformer = TransformerFactory.newInstance().newTransformer();
                    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                    transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

                    transformer.transform(source, result);

                    String outString = filterMD(writer.toString());

                    FileOutputStream out = new FileOutputStream(exportFile);
                    out.write(outString.getBytes("UTF-8"));

                    statusBarLabel.setText("Exported dictionary to " + exportFile.getAbsolutePath().replaceAll("/Users/.+?/", "~/") + ".");

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                System.err.println("No file specified! Doing nothing. ^^");
            }
        } else {
            System.err.println("No document is loaded! Doing nothing. ^^");
        }
    }

    @FXML private void importFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Import");
        fileChooser.getExtensionFilters().add(new ExtensionFilter("XHTML/XML Files", "*.xhtml", "*.XHTML", "*.xml", "*.XML"));

        File file = fileChooser.showOpenDialog(root.getScene().getWindow());
        if (file != null) {
            currentFile = file;
            newFile = true;
            loadXML();
        }
    }

    @FXML private void moveItemDown() {
        TreeItem<Object> treeItem = treeView.getSelectionModel().getSelectedItem();

        if (treeItem == null) { // null check to avoid null pointer exception
            System.err.println("The item is null!");

        } else if (treeItem.getValue() instanceof D_entry) {
            ObservableList<TreeItem<Object>> list = treeItem.getParent().getChildren();

            int i = list.indexOf(treeItem);

            if (i < list.size()) {
                int j = i + 1;

                if (list.get(j).getValue() instanceof D_entry) {
                    List model = ((Dictionary) treeItem.getParent().getValue()).getEntries();

                    int k = model.indexOf(treeItem.getValue());
                    int l = k + 1;

                    Collections.swap(list, i, j);
                    Collections.swap(model, k, l);
                }
            }
        }
        treeView.requestFocus();
        treeView.getSelectionModel().select(treeItem);
    }

    @FXML private void moveItemUp() {
        TreeItem<Object> treeItem = treeView.getSelectionModel().getSelectedItem();

        if (treeItem == null) { // null check to avoid null pointer exception
            System.err.println("The item is null!");

        } else if (treeItem.getValue() instanceof D_entry) {
            ObservableList<TreeItem<Object>> list = treeItem.getParent().getChildren();

            int i = list.indexOf(treeItem);

            if (i > 0) {
                int j = i - 1;

                if (list.get(j).getValue() instanceof D_entry) {
                    List model = ((Dictionary) treeItem.getParent().getValue()).getEntries();

                    int k = model.indexOf(treeItem.getValue());
                    int l = k - 1;

                    Collections.swap(list, i, j);
                    Collections.swap(model, k, l);
                }
            }
        }
        treeView.requestFocus();
        treeView.getSelectionModel().select(treeItem);
    }

    @FXML private void newFile() {
        DictionaryWriter.getStage().setTitle("Dictionary Writer - New Dictionary");

        dictionary = new Dictionary();

        TreeItem<Object> treeRoot = new TreeItem<>(dictionary);
        treeRoot.setExpanded(true);
        treeRoot.expandedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue)
                treeRoot.setExpanded(true);
        });

        treeView.setRoot(treeRoot);
        treeView.getSelectionModel().select(treeRoot);
        treeView.setShowRoot(false);

        documentLoaded = true;

        newFile = true;
    }

    @FXML private void openFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open");
        fileChooser.getExtensionFilters().add(new ExtensionFilter("Markdown Dictionary Files (.mddict)", "*.mddict", "*.MDDICT"));

        File file = fileChooser.showOpenDialog(root.getScene().getWindow());
        if (file != null) {
            currentFile = file;
            newFile = false;
            loadMD();
        }
    }

    @FXML private void removeSelectedEntry() {
        TreeItem<Object> treeItem = treeView.getSelectionModel().getSelectedItem();

        if (treeItem != null && !(treeItem.getValue() instanceof Dictionary) && treeItem.getValue() instanceof D_entry) {
            ((Dictionary) treeItem.getParent().getValue()).getEntries().remove(treeItem.getValue());

            treeItem.getParent().getChildren().remove(treeItem);
        }
    }

    @FXML private void saveFile() {
        treeView.requestFocus();
        if (documentLoaded) {
            if (newFile) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Save As...");
                fileChooser.getExtensionFilters().add(new ExtensionFilter("Markdown Dictionary Files (.mddict)", "*.mddict", "*.MDDICT"));

                currentFile = fileChooser.showSaveDialog(root.getScene().getWindow());
            }
            if (currentFile != null) {
                DictionaryWriter.getStage().setTitle("Dictionary Writer - [" + currentFile.getAbsolutePath().replaceAll("/Users/.+?/", "~/") + "]");

                try {
                    ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(currentFile));
                    stream.writeObject(dictionary);

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    newFile = false;
                }
            } else {
                System.err.println("No file specified! Doing nothing. ^^");
            }
        } else {
            System.err.println("No document is loaded! Doing nothing. ^^");
        }
    }

    @FXML private void saveFileAs() {
        newFile = true;
        saveFile();
    }


    // Helper Methods
    private String filterMD(String text) {
        return text
                .replaceAll("\\?><", "?>\n<")
                .replaceFirst(" standalone=\"no\"", "")
                .replaceFirst("<d:dictionary", "\n<d:dictionary")
                .replaceAll("\\n[\\s]*<span d:pr", "<span d:pr")
                .replaceAll(">\\(<", ">\n" + indent(6) + "(\n" + indent(6) + "<")
                .replaceAll(">,<", ">\n" + indent(6) + ",\n" + indent(6) + "<")
                .replaceAll(">\\)<", ">\n" + indent(6) + ")\n" + indent(5) + "<")
                .replaceAll("%(.)", "<span id=\"$1\"/>")
                .replaceAll("\\*\\*\\*(.*?)\\*\\*\\*", "<b><i>$1</i></b>")
                .replaceAll("\\*\\*(.*?)\\*\\*", "<b>$1</b>")
                .replaceAll("\\*(.*?)\\*", "<i>$1</i>")
                .replaceAll("\\^\\((.*?)\\)", "<sup>$1</sup>")
                .replaceAll("\\^(.)", "<sup>$1</sup>");
    }

    private static String indent(int length) {
        String space = "    ";
        String indent = "";
        for (int i = 0; i < length; i++)
            indent += space;

        return indent;
    }
}