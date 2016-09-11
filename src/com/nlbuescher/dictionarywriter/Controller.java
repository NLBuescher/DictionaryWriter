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
    @FXML private MenuItem saveMenuItem;
    @FXML private MenuItem saveAsMenuItem;

    @FXML private Button addItemButton;

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
            saveMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.META_DOWN));
            saveAsMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.META_DOWN, KeyCombination.SHIFT_DOWN));
        }

        entryEditor.getEngine().loadContent("" +
                "<div id=\"editor\" contenteditable=\"true\" style=\"outline: none; font-family: 'Menlo', monospace; font-size: 80%;\">" +
                "Entry Editor" +
                "</div>");
        JSObject js = (JSObject) entryEditor.getEngine().executeScript("window");
        js.setMember("controller", this);
        entryEditor.getEngine().getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == Worker.State.SUCCEEDED) {
                entryEditor.getEngine().executeScript("" +
                        "document.getElementById('editor').addEventListener('input', function() {" +
                        "   controller.editorChanged(document.getElementById('editor').innerText.trim(), 'entry')" +
                        "}, false)");
            }
        });

        indexEditor.getEngine().loadContent("<div id=\"editor\" contenteditable=\"false\" style=\"outline: none; font-family: 'Menlo', monospace; font-size: 80%\">Index Editor</div>");
        cssEditor.getEngine().loadContent("<div id=\"editor\" contenteditable=\"false\" style=\"outline: none; font-family: 'Menlo', monospace; font-size: 80%\">CSS Editor</div>");

        treeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) { // Does not execute with null
                                    // (null pointer exceptions don't break anything, but are annoying)
                updateEntryEditor(newValue);
                updateIndexEditor(newValue);
                updatePreview(newValue);
            }
        });
    }

    public void editorChanged(String newText, String source) {
        System.out.println(newText);
        switch (source) {
            case "entry":
                ((D_entry) treeView.getSelectionModel().getSelectedItem().getValue()).setEntryText(newText);
                updatePreview(treeView.getSelectionModel().getSelectedItem());
                break;
            case "index":
                ((D_entry) treeView.getSelectionModel().getSelectedItem().getValue()).setIndexText(newText);
                break;
        }
    }

    private void updateEntryEditor(TreeItem treeItem) {
        if (treeItem.getValue() instanceof D_entry) {
            D_entry entry = (D_entry) treeItem.getValue();
            System.out.println(entry.getEntryText());
            entryEditor.getEngine().executeScript("document.getElementById('editor').innerText = '" + entry.getEntryText().trim() + "';");

        }
    }

    private void updateIndexEditor(TreeItem treeItem) {
        if (treeItem.getValue() instanceof D_entry) {
            D_entry entry = (D_entry) treeItem.getValue();
            System.out.println(entry.getIndexText());
            indexEditor.getEngine().executeScript("document.getElementById('editor').innerText = '" + entry.getIndexText().trim() + "';");
        }
    }


    private void loadXML() {
        DictionaryWriter.getStage().setTitle("Dictionary Writer - [" + currentFile.getAbsolutePath() + "]");

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
        }
    }

    @FXML private void importFile() {

    }

    private void updatePreview(TreeItem<Object> treeItem) {if (treeItem != null) {
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

                    String previewString = filterHTML(writer.toString());

                    WebEngine webEngine = this.preview.getEngine();
                    webEngine.loadContent(previewString);
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

            D_entry entry = new D_entry();

            dialog.showAndWait().ifPresent(title -> {
                entry.setEntryText("# " + title + " #\n\n");
                entry.setTitle(title);
            });

            ((Dictionary) treeView.getRoot().getValue()).getEntries().add(entry);
            TreeItem<Object> treeItem = new TreeItem<>(entry);
            treeView.getRoot().getChildren().add(treeItem);
            treeView.getSelectionModel().select(treeItem);
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
        fileChooser.getExtensionFilters().add(new ExtensionFilter("Markdown Dictionary Files", "*.mdd", "*.MDD"));

        File file = fileChooser.showOpenDialog(root.getScene().getWindow());
        if (file != null) {
            currentFile = file;
            loadMD();
        }
    }

    @FXML private void removeSelectedItem() {
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
                fileChooser.getExtensionFilters().add(new ExtensionFilter("Markdown Dictionary Files", "*.mdd", "*.MDD"));

                currentFile = fileChooser.showSaveDialog(root.getScene().getWindow());
            }
            if (currentFile != null) {
                DictionaryWriter.getStage().setTitle("Dictionary Writer - [" + currentFile.getAbsolutePath().replaceAll("/Users/.+?/", "~/") + "]");

                try {
                    BufferedWriter writer = new BufferedWriter(new FileWriter(currentFile));
                    writer.write(dictionary.getTextContent());

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

    @FXML private void saveFileAs() {
        newFile = true;
        saveFile();
    }


    private void loadMD() {
        DictionaryWriter.getStage().setTitle("Dictionary Writer - [" + currentFile.getAbsolutePath().replaceAll("/Users/.+?/", "~/") + "]");

        try {
            String inputString = new Scanner(currentFile).useDelimiter("\\Z").next();

            dictionary = new Dictionary(inputString);

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
        }
    }


    private String filterHTML(String text) {
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