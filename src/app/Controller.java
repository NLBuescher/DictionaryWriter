package app;

import dictionary.*;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.PopupWindow;
import org.controlsfx.control.PopOver;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;


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

    public BorderPane root;

    public MenuBar  menuBar;
    public MenuItem openMenuItem;
    public MenuItem saveMenuItem;
    public MenuItem saveAsMenuItem;

    public Button addItemButton;

    public TreeView<Object> treeView;

    public ScrollPane editorScrollPane;
    public VBox       editorVBox;
    public WebView    previewWebView;

    private boolean documentLoaded = false;
    private boolean newFile        = false;
    private File       currentFile;
    private Dictionary dictionary;


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

    private void updateEditor (TreeItem<Object> treeItem) {
        editorVBox.getChildren ().clear ();

        if (treeItem.getValue () == null) {

        } else if (treeItem.getValue () instanceof Definition) {

            Label definitionLabel = new Label ("Definition");
            definitionLabel.setFont (Font.font (18));
            editorVBox.getChildren ().add (definitionLabel);

            Label definitionLabelLabel = new Label ("Definition Label");
            definitionLabelLabel.setPadding (new Insets (10, 0, 0, 0));
            editorVBox.getChildren ().add (definitionLabelLabel);

            TextField definitionLabelTextField = new TextField (((Definition) treeItem.getValue ()).getDefinitionLabel ());
            definitionLabelTextField.focusedProperty ().addListener ((observable, oldValue, newValue) -> {
                if (!newValue)
                    ((Definition) treeItem.getValue ()).setDefinitionLabel (definitionLabelTextField.getText ());
            });
            editorVBox.getChildren ().add (definitionLabelTextField);

            Label specificationLabel = new Label ("Specification");
            specificationLabel.setPadding (new Insets (10, 0, 0, 0));
            editorVBox.getChildren ().add (specificationLabel);

            TextField specificationTextField = new TextField (((Definition) treeItem.getValue ()).getSpecification ());
            specificationTextField.focusedProperty ().addListener ((observable, oldValue, newValue) -> {
                if (!newValue)
                    ((Definition) treeItem.getValue ()).setSpecification (specificationTextField.getText ());
            });
            editorVBox.getChildren ().add (specificationTextField);

            Label definitionTextLabel = new Label ("Definition Text");
            definitionTextLabel.setPadding (new Insets (10, 0, 0, 0));
            editorVBox.getChildren ().add (definitionTextLabel);

            TextField definitionTextTextField = new TextField (((Definition) treeItem.getValue ()).getDefinitionText ());
            definitionTextTextField.focusedProperty ().addListener ((observable, oldValue, newValue) -> {
                if (!newValue)
                    ((Definition) treeItem.getValue ()).setDefinitionText (definitionTextTextField.getText ());
            });
            editorVBox.getChildren ().add (definitionTextTextField);

        } else if (treeItem.getValue () instanceof DefinitionGroup) {

            Label definitionGroupLabel = new Label ("Definition Group");
            definitionGroupLabel.setFont (Font.font (18));
            editorVBox.getChildren ().add (definitionGroupLabel);

            Label definitionGroupLabelLabel = new Label ("Definition Group Label");
            definitionGroupLabelLabel.setPadding (new Insets (10, 0, 0, 0));
            editorVBox.getChildren ().add (definitionGroupLabelLabel);

            TextField definitionGroupLabelTextField = new TextField (((DefinitionGroup) treeItem.getValue ()).getDefinitionGroupLabel ());
            definitionGroupLabelTextField.focusedProperty ().addListener (((observable, oldValue, newValue) -> {
                if (!newValue)
                    ((DefinitionGroup) treeItem.getValue ()).setDefinitionGroupLabel (definitionGroupLabelTextField.getText ());
            }));
            editorVBox.getChildren ().add (definitionGroupLabelTextField);

        } else if (treeItem.getValue () instanceof DictEntry) {

            Label entryLabel = new Label ("Dictionary Entry");
            entryLabel.setFont (Font.font (18));
            editorVBox.getChildren ().add (entryLabel);

            Label entryIDLabel = new Label ("ID");
            entryIDLabel.setPadding (new Insets (10, 0, 0, 0));
            editorVBox.getChildren ().add (entryIDLabel);

            TextField entryIDTextField = new TextField (((DictEntry) treeItem.getValue ()).getId ());
            entryIDTextField.focusedProperty ().addListener ((observable, oldValue, newValue) -> {
                if (!newValue)
                    ((DictEntry) treeItem.getValue ()).setId (entryIDTextField.getText ());
            });
            editorVBox.getChildren ().add (entryIDTextField);

            Label entryTitleLabel = new Label ("Title");
            entryTitleLabel.setPadding (new Insets (10, 0, 0, 0));
            editorVBox.getChildren ().add (entryTitleLabel);

            TextField entryTitleTextField = new TextField (((DictEntry) treeItem.getValue ()).getTitle ());
            entryTitleTextField.focusedProperty ().addListener ((observable, oldValue, newValue) -> {
                if (!newValue)
                    ((DictEntry) treeItem.getValue ()).setTitle (entryTitleTextField.getText ());
            });
            editorVBox.getChildren ().add (entryTitleTextField);

        } else if (treeItem.getValue () instanceof Entry) {

            Label entryLabel = new Label ("Entry");
            entryLabel.setFont (Font.font (18));
            editorVBox.getChildren ().add (entryLabel);

        } else if (treeItem.getValue () instanceof EntryGroup) {

            Label entryGroupLabel = new Label ("Entry Group");
            entryGroupLabel.setFont (Font.font (18));
            editorVBox.getChildren ().add (entryGroupLabel);

        } else if (treeItem.getValue () instanceof Example) {

            Label exampleLabel = new Label ("Example");
            exampleLabel.setFont (Font.font (18));
            editorVBox.getChildren ().add (exampleLabel);

            Label exampleLabelLabel = new Label ("Example Label");
            exampleLabelLabel.setPadding (new Insets (10, 0, 0, 0));
            editorVBox.getChildren ().add (exampleLabelLabel);

            TextField exampleLabelTextField = new TextField (((Example) treeItem.getValue ()).getExampleLabel ());
            exampleLabelTextField.focusedProperty ().addListener ((observable, oldValue, newValue) -> {
                if (!newValue)
                    ((Example) treeItem.getValue ()).setExampleLabel (exampleLabelTextField.getText ());
            });
            editorVBox.getChildren ().add (exampleLabelTextField);

            Label specificationLabel = new Label ("Specification");
            specificationLabel.setPadding (new Insets (10, 0, 0, 0));
            editorVBox.getChildren ().add (specificationLabel);

            TextField specificationTextField = new TextField (((Example) treeItem.getValue ()).getSpecification ());
            specificationTextField.focusedProperty ().addListener ((observable, oldValue, newValue) -> {
                if (!newValue)
                    ((Example) treeItem.getValue ()).setSpecification (specificationTextField.getText ());
            });
            editorVBox.getChildren ().add (specificationTextField);

            Label exampleTextLabel = new Label ("Example Text");
            exampleTextLabel.setPadding (new Insets (10, 0, 0, 0));
            editorVBox.getChildren ().add (exampleTextLabel);

            TextField exampleTextTextField = new TextField (((Example) treeItem.getValue ()).getExampleText ());
            exampleTextTextField.focusedProperty ().addListener ((observable, oldValue, newValue) -> {
                if (!newValue)
                    ((Example) treeItem.getValue ()).setExampleText (exampleTextTextField.getText ());
            });
            editorVBox.getChildren ().add (exampleTextTextField);

            Label exampleTranslationLabel = new Label ("Example Translation");
            exampleTranslationLabel.setPadding (new Insets (10, 0, 0, 0));
            editorVBox.getChildren ().add (exampleTranslationLabel);

            TextField exampleTranslationTextField = new TextField (((Example) treeItem.getValue ()).getExampleTranslation ().replaceFirst ("\\s–\\s", ""));
            exampleTranslationTextField.focusedProperty ().addListener ((observable, oldValue, newValue) -> {
                if (!newValue)
                    ((Example) treeItem.getValue ()).setExampleTranslation (" – " + exampleTranslationTextField.getText ());
            });
            editorVBox.getChildren ().add (exampleTranslationTextField);

        } else if (treeItem.getValue () instanceof ExampleGroup) {

            Label exampleGroupLabel = new Label ("Example Group");
            exampleGroupLabel.setFont (Font.font (18));
            editorVBox.getChildren ().add (exampleGroupLabel);

        } else if (treeItem.getValue () instanceof Form) {

            Label formLabel = new Label ("Form");
            formLabel.setFont (Font.font (18));
            editorVBox.getChildren ().add (formLabel);

            Label formLabelLabel = new Label ("Form Label");
            formLabelLabel.setPadding (new Insets (10, 0, 0, 0));
            editorVBox.getChildren ().add (formLabelLabel);

            TextField formLabelTextField = new TextField (((Form) treeItem.getValue ()).getFormLabel ());
            formLabelTextField.focusedProperty ().addListener ((observable, oldValue, newValue) -> {
                if (!newValue)
                    ((Form) treeItem.getValue ()).setFormLabel (formLabelTextField.getText ());
            });
            editorVBox.getChildren ().add (formLabelTextField);

            Label formTextLabel = new Label ("Form Text");
            formTextLabel.setPadding (new Insets (10, 0, 0, 0));
            editorVBox.getChildren ().add (formTextLabel);

            TextField formTextTextField = new TextField (((Form) treeItem.getValue ()).getFormText ());
            formTextTextField.focusedProperty ().addListener ((observable, oldValue, newValue) -> {
                if (!newValue)
                    ((Form) treeItem.getValue ()).setFormText (formTextTextField.getText ());
            });
            editorVBox.getChildren ().add (formTextTextField);

        } else if (treeItem.getValue () instanceof FormGroup) {

            Label formGroupLabel = new Label ("Form Group");
            formGroupLabel.setFont (Font.font (18));
            editorVBox.getChildren ().add (formGroupLabel);

        } else if (treeItem.getValue () instanceof GrammarGroup) {

            Label grammarGroupLabel = new Label ("Grammar Group");
            grammarGroupLabel.setFont (Font.font (18));
            editorVBox.getChildren ().add (grammarGroupLabel);

            Label grammarLabel = new Label ("Grammar");
            grammarLabel.setPadding (new Insets (10, 0, 0, 0));
            editorVBox.getChildren ().add (grammarLabel);

            TextField grammarTextField = new TextField (((GrammarGroup) treeItem.getValue ()).getGrammar ());
            grammarTextField.focusedProperty ().addListener ((observable, oldValue, newValue) -> {
                if (!newValue)
                    ((GrammarGroup) treeItem.getValue ()).setGrammar (grammarTextField.getText ());
            });
            editorVBox.getChildren ().add (grammarTextField);

            Label specificationLabel = new Label ("Specification");
            specificationLabel.setPadding (new Insets (10, 0, 0, 0));
            editorVBox.getChildren ().add (specificationLabel);

            TextField specificationTextField = new TextField (((GrammarGroup) treeItem.getValue ()).getSpecification ());
            specificationTextField.focusedProperty ().addListener ((observable, oldValue, newValue) -> {
                if (!newValue)
                    ((GrammarGroup) treeItem.getValue ()).setSpecification (specificationTextField.getText ());
            });
            editorVBox.getChildren ().add (specificationTextField);

        } else if (treeItem.getValue () instanceof HeadGroup) {

            Label headGroupLabel = new Label ("Head Group");
            headGroupLabel.setFont (Font.font (18));
            editorVBox.getChildren ().add (headGroupLabel);

            Label headWordLabel = new Label ("Head Word");
            headWordLabel.setPadding (new Insets (10, 0, 0, 0));
            editorVBox.getChildren ().add (headWordLabel);

            TextField headWordTextField = new TextField (((HeadGroup) treeItem.getValue ()).getHeadWord ());
            headWordTextField.focusedProperty ().addListener ((observable, oldValue, newValue) -> {
                if (!newValue)
                    ((HeadGroup) treeItem.getValue ()).setHeadWord (headWordTextField.getText ());
            });
            editorVBox.getChildren ().add (headWordTextField);

        } else if (treeItem.getValue () instanceof Index) {

            Label indexLabel = new Label ("Index");
            indexLabel.setFont (Font.font (18));
            editorVBox.getChildren ().add (indexLabel);

            Label indexTitleLabel = new Label ("Title");
            indexTitleLabel.setPadding (new Insets (10, 0, 0, 0));
            editorVBox.getChildren ().add (indexTitleLabel);

            TextField indexTitleTextField = new TextField (((Index) treeItem.getValue ()).getTitle ());
            indexTitleTextField.focusedProperty ().addListener ((observable, oldValue, newValue) -> {
                if (!newValue)
                    ((Index) treeItem.getValue ()).setTitle (indexTitleTextField.getText ());
            });
            editorVBox.getChildren ().add (indexTitleTextField);

            Label indexValueLabel = new Label ("Value");
            indexValueLabel.setPadding (new Insets (10, 0, 0, 0));
            editorVBox.getChildren ().add (indexValueLabel);

            TextField indexValueTextField = new TextField (((Index) treeItem.getValue ()).getValue ());
            indexValueTextField.focusedProperty ().addListener ((observable, oldValue, newValue) -> {
                if (!newValue)
                    ((Index) treeItem.getValue ()).setValue (indexValueTextField.getText ());
            });
            editorVBox.getChildren ().add (indexValueTextField);

        } else if (treeItem.getValue () instanceof Note) {

            Label noteLabel = new Label ("Note");
            noteLabel.setFont (Font.font (18));
            editorVBox.getChildren ().add (noteLabel);

            Label noteTextLabel = new Label ("Note Text");
            noteTextLabel.setPadding (new Insets (10, 0, 0, 0));
            editorVBox.getChildren ().add (noteTextLabel);

            TextArea noteTextTextArea = new TextArea (((Note) treeItem.getValue ()).getNoteText ());
            noteTextTextArea.focusedProperty ().addListener ((observable, oldValue, newValue) -> {
                if (!newValue)
                    ((Note) treeItem.getValue ()).setNoteText (noteTextTextArea.getText ());
            });
            editorVBox.getChildren ().add (noteTextTextArea);

        } else if (treeItem.getValue () instanceof NoteGroup) {

            Label noteGroupLabel = new Label ("Note Group");
            noteGroupLabel.setFont (Font.font (18));
            editorVBox.getChildren ().add (noteGroupLabel);

        } else if (treeItem.getValue () instanceof Pronunciation) {

            Label pronunciationLabel = new Label ("Pronunciation");
            pronunciationLabel.setFont (Font.font (18));
            editorVBox.getChildren ().add (pronunciationLabel);

            Label clLabel = new Label ("Classical Pronunciation (IPA)");
            clLabel.setPadding (new Insets (10, 0, 0, 0));
            editorVBox.getChildren ().add (clLabel);

            TextField clTextField = new TextField (((Pronunciation) treeItem.getValue ()).getCL_IPA ());
            clTextField.focusedProperty ().addListener ((observable, oldValue, newValue) -> {
                if (!newValue)
                    ((Pronunciation) treeItem.getValue ()).setCL_IPA (clTextField.getText ());
            });
            editorVBox.getChildren ().add (clTextField);

            Label vaLabel = new Label ("Ecclesiastical Pronunciation (IPA)");
            vaLabel.setPadding (new Insets (10, 0, 0, 0));
            editorVBox.getChildren ().add (vaLabel);

            TextField vaTextField = new TextField (((Pronunciation) treeItem.getValue ()).getVA_IPA ());
            vaTextField.focusedProperty ().addListener ((observable, oldValue, newValue) -> {
                if (!newValue)
                    ((Pronunciation) treeItem.getValue ()).setVA_IPA (vaTextField.getText ());
            });
            editorVBox.getChildren ().add (vaTextField);

        } else if (treeItem.getValue () instanceof SubDefinition) {

            Label subDefinitionLabel = new Label ("Sub-Definition");
            subDefinitionLabel.setFont (Font.font (18));
            editorVBox.getChildren ().add (subDefinitionLabel);

            Label subDefinitionLabelLabel = new Label ("Sub-Definition Label");
            subDefinitionLabelLabel.setPadding (new Insets (10, 0, 0, 0));
            editorVBox.getChildren ().add (subDefinitionLabelLabel);

            TextField subDefinitionLabelTextField = new TextField (((SubDefinition) treeItem.getValue ()).getSubDefinitionLabel ());
            subDefinitionLabelTextField.focusedProperty ().addListener ((observable, oldValue, newValue) -> {
                if (!newValue)
                    ((SubDefinition) treeItem.getValue ()).setSubDefinitionLabel (subDefinitionLabelTextField.getText ());
            });
            editorVBox.getChildren ().add (subDefinitionLabelTextField);

            Label specificationLabel = new Label ("Specification");
            specificationLabel.setPadding (new Insets (10, 0, 0, 0));
            editorVBox.getChildren ().add (specificationLabel);

            TextField specificationTextField = new TextField (((SubDefinition) treeItem.getValue ()).getSpecification ());
            specificationTextField.focusedProperty ().addListener ((observable, oldValue, newValue) -> {
                if (!newValue)
                    ((SubDefinition) treeItem.getValue ()).setSpecification (specificationTextField.getText ());
            });
            editorVBox.getChildren ().add (specificationTextField);

            Label subDefinitionTextLabel = new Label ("Sub-Definition Text");
            subDefinitionTextLabel.setPadding (new Insets (10, 0, 0, 0));
            editorVBox.getChildren ().add (subDefinitionTextLabel);

            TextField subDefinitionTextTextField = new TextField (((SubDefinition) treeItem.getValue ()).getSubDefinitionText ());
            subDefinitionTextTextField.focusedProperty ().addListener ((observable, oldValue, newValue) -> {
                if (!newValue)
                    ((SubDefinition) treeItem.getValue ()).setSubDefinitionText (subDefinitionTextTextField.getText ());
            });
            editorVBox.getChildren ().add (subDefinitionTextTextField);

        } else if (treeItem.getValue () instanceof SubDefinitionGroup) {

            Label subDefinitionGroupLabel = new Label ("Sub-Definition Group");
            subDefinitionGroupLabel.setFont (Font.font (18));
            editorVBox.getChildren ().add (subDefinitionGroupLabel);

        } else if (treeItem.getValue () instanceof SubEntry) {

            Label subEntryLabel = new Label ("Sub-Entry");
            subEntryLabel.setFont (Font.font (18));
            editorVBox.getChildren ().add (subEntryLabel);

            Label subEntryLabelLabel = new Label ("Sub-Entry Label");
            subEntryLabelLabel.setPadding (new Insets (10, 0, 0, 0));
            editorVBox.getChildren ().add (subEntryLabelLabel);

            TextField subEntryLabelTextField = new TextField (((SubEntry) treeItem.getValue ()).getSubEntryLabel ());
            subEntryLabelTextField.focusedProperty ().addListener ((observable, oldValue, newValue) -> {
                if (!newValue)
                    ((SubEntry) treeItem.getValue ()).setSubEntryLabel (subEntryLabelTextField.getText ());
            });
            editorVBox.getChildren ().add (subEntryLabelTextField);

            Label subEntryTextLabel = new Label ("Sub-Entry Text");
            subEntryTextLabel.setPadding (new Insets (10, 0, 0, 0));
            editorVBox.getChildren ().add (subEntryTextLabel);

            TextField subEntryTextTextField = new TextField (((SubEntry) treeItem.getValue ()).getSubEntryText ());
            subEntryTextTextField.focusedProperty ().addListener ((observable, oldValue, newValue) -> {
                if (!newValue)
                    ((SubEntry) treeItem.getValue ()).setSubEntryText (subEntryTextTextField.getText ());
            });
            editorVBox.getChildren ().add (subEntryTextTextField);

        } else if (treeItem.getValue () instanceof SubEntryGroup) {

            Label subEntryGroupLabel = new Label ("Sub-Entry Group");
            subEntryGroupLabel.setFont (Font.font (18));
            editorVBox.getChildren ().add (subEntryGroupLabel);

        } else if (treeItem.getValue () instanceof SubEntryList) {

            Label subEntryListLabel = new Label ("Sub-Entry List");
            subEntryListLabel.setFont (Font.font (18));
            editorVBox.getChildren ().add (subEntryListLabel);

        } else if (treeItem.getValue () instanceof SubEntryListItem) {

            Label subEntryListItemLabel = new Label ("Sub-Entry List Item");
            subEntryListItemLabel.setFont (Font.font (18));
            editorVBox.getChildren ().add (subEntryListItemLabel);

            Label subEntryListItemLabelLabel = new Label ("Sub-Entry List Item Label");
            subEntryListItemLabelLabel.setPadding (new Insets (10, 0, 0, 0));
            editorVBox.getChildren ().add (subEntryListItemLabelLabel);

            TextField subEntryListItemLabelTextField = new TextField (((SubEntryListItem) treeItem.getValue ()).getSubEntryListItemLabel ());
            subEntryListItemLabelTextField.focusedProperty ().addListener ((observable, oldValue, newValue) -> {
                if (!newValue)
                    ((SubEntryListItem) treeItem.getValue ()).setSubEntryListItemLabel (subEntryListItemLabelTextField.getText ());
            });
            editorVBox.getChildren ().add (subEntryListItemLabelTextField);

            Label specificationLabel = new Label ("Specification");
            specificationLabel.setPadding (new Insets (10, 0, 0, 0));
            editorVBox.getChildren ().add (specificationLabel);

            TextField specificationTextField = new TextField (((SubEntryListItem) treeItem.getValue ()).getSpecification ());
            specificationTextField.focusedProperty ().addListener ((observable, oldValue, newValue) -> {
                if (!newValue)
                    ((SubEntryListItem) treeItem.getValue ()).setSpecification (specificationTextField.getText ());
            });
            editorVBox.getChildren ().add (specificationTextField);

            Label subEntryListItemTextLabel = new Label ("Sub-Entry List Item Text");
            subEntryListItemTextLabel.setPadding (new Insets (10, 0, 0, 0));
            editorVBox.getChildren ().add (subEntryListItemTextLabel);

            TextField subEntryListItemTextTextField = new TextField (((SubEntryListItem) treeItem.getValue ()).getSubEntryListItemText ());
            subEntryListItemTextTextField.focusedProperty ().addListener ((observable, oldValue, newValue) -> {
                if (!newValue)
                    ((SubEntryListItem) treeItem.getValue ()).setSubEntryListItemText (subEntryListItemTextTextField.getText ());
            });
            editorVBox.getChildren ().add (subEntryListItemTextTextField);

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

        try {
            String inputString = new Scanner (currentFile).useDelimiter ("\\Z").next ()
                    .replaceAll ("<span id=\"(.)\"/>", "@@$1")
                    .replaceAll ("<b>(.*?)</b>", "**$1**")
                    .replaceAll ("<i>(.*?)</i>", "*$1*")
                    .replaceAll ("<sup>(.*?)</sup>", "^($1)");

            Element element = DocumentBuilderFactory.newInstance ().newDocumentBuilder ().parse (new InputSource (new StringReader (inputString))).getDocumentElement ();
            dictionary = Dictionary.fromElement (element);
            TreeItem<Object> treeRoot = new TreeItem<> (dictionary);

            addToTree (dictionary, treeRoot);

            treeView.setRoot (treeRoot);
        } catch (Exception e) {
            e.printStackTrace ();
        } finally {
            documentLoaded = true;
        }
    }

    private void addToTree (Object item, TreeItem<Object> root) {
        if (item == null) {

        } else if (item instanceof Dictionary) {
            Dictionary dictionary = ((Dictionary) item);

            for (DictEntry entry : dictionary.getEntries ())
                addToTree (entry, root);

        } else if (item instanceof Definition) {
            Definition       definition = ((Definition) item);
            TreeItem<Object> treeItem   = new TreeItem<> (definition);
            root.getChildren ().add (treeItem);

            addToTree (definition.getExampleGroup (), treeItem);

            addToTree (definition.getSubDefinitionGroup (), treeItem);

        } else if (item instanceof DefinitionGroup) {
            DefinitionGroup  definitionGroup = ((DefinitionGroup) item);
            TreeItem<Object> treeItem        = new TreeItem<> (definitionGroup);
            root.getChildren ().add (treeItem);

            for (Definition definition : definitionGroup.getDefinitions ())
                addToTree (definition, treeItem);

        } else if (item instanceof DictEntry) {
            DictEntry        entry    = ((DictEntry) item);
            TreeItem<Object> treeItem = new TreeItem<> (entry);
            root.getChildren ().add (treeItem);

            for (Index index : entry.getIndices ())
                addToTree (index, treeItem);

            addToTree (entry.getHeadGroup (), treeItem);

            addToTree (entry.getEntryGroup (), treeItem);

        } else if (item instanceof Entry) {
            Entry            entry    = ((Entry) item);
            TreeItem<Object> treeItem = new TreeItem<> (entry);
            root.getChildren ().add (treeItem);

            addToTree (entry.getGrammarGroup (), treeItem);

            for (DefinitionGroup definitionGroup : entry.getDefinitionGroups ())
                addToTree (definitionGroup, treeItem);

            addToTree (entry.getSubEntryGroup (), treeItem);

        } else if (item instanceof EntryGroup) {
            EntryGroup       entryGroup = ((EntryGroup) item);
            TreeItem<Object> treeItem   = new TreeItem<> (entryGroup);
            root.getChildren ().add (treeItem);

            for (Entry entry : entryGroup.getEntries ())
                addToTree (entry, treeItem);

        } else if (item instanceof Example) {
            Example          example  = ((Example) item);
            TreeItem<Object> treeItem = new TreeItem<> (example);
            root.getChildren ().add (treeItem);

        } else if (item instanceof ExampleGroup) {
            ExampleGroup     exampleGroup = ((ExampleGroup) item);
            TreeItem<Object> treeItem     = new TreeItem<> (exampleGroup);
            root.getChildren ().add (treeItem);

            for (Example example : exampleGroup.getExamples ())
                addToTree (example, treeItem);

        } else if (item instanceof Form) {
            Form             form     = ((Form) item);
            TreeItem<Object> treeItem = new TreeItem<> (form);
            root.getChildren ().add (treeItem);

            addToTree (form.getPronunciation (), treeItem);

        } else if (item instanceof FormGroup) {
            FormGroup        formGroup = ((FormGroup) item);
            TreeItem<Object> treeItem  = new TreeItem<> (formGroup);
            root.getChildren ().add (treeItem);

            for (Form form : formGroup.getForms ())
                addToTree (form, treeItem);

        } else if (item instanceof GrammarGroup) {
            GrammarGroup     grammarGroup = ((GrammarGroup) item);
            TreeItem<Object> treeItem     = new TreeItem<> (grammarGroup);
            root.getChildren ().add (treeItem);

            addToTree (grammarGroup.getFormGroup (), treeItem);

        } else if (item instanceof HeadGroup) {
            HeadGroup        headGroup = ((HeadGroup) item);
            TreeItem<Object> treeItem  = new TreeItem<> (headGroup);
            root.getChildren ().add (treeItem);

            addToTree (headGroup.getPronunciation (), treeItem);

        } else if (item instanceof Index) {
            Index            index    = ((Index) item);
            TreeItem<Object> treeItem = new TreeItem<> (index);
            root.getChildren ().add (treeItem);

        } else if (item instanceof Note) {
            Note             note     = ((Note) item);
            TreeItem<Object> treeItem = new TreeItem<> (note);
            root.getChildren ().add (treeItem);

        } else if (item instanceof NoteGroup) {
            NoteGroup        noteGroup = ((NoteGroup) item);
            TreeItem<Object> treeItem  = new TreeItem<> (noteGroup);
            root.getChildren ().add (treeItem);

            for (Note note : noteGroup.getNotes ())
                addToTree (note, treeItem);

        } else if (item instanceof Pronunciation) {
            Pronunciation    pronunciation = ((Pronunciation) item);
            TreeItem<Object> treeItem      = new TreeItem<> (pronunciation);
            root.getChildren ().add (treeItem);

        } else if (item instanceof SubDefinition) {
            SubDefinition    subDefinition = ((SubDefinition) item);
            TreeItem<Object> treeItem      = new TreeItem<> (subDefinition);
            root.getChildren ().add (treeItem);

            addToTree (subDefinition.getExampleGroup (), treeItem);

        } else if (item instanceof SubDefinitionGroup) {
            SubDefinitionGroup subDefinitionGroup = ((SubDefinitionGroup) item);
            TreeItem<Object>   treeItem           = new TreeItem<> (subDefinitionGroup);
            root.getChildren ().add (treeItem);

            for (SubDefinition subDefinition : subDefinitionGroup.getSubDefinitions ())
                addToTree (subDefinition, treeItem);

        } else if (item instanceof SubEntry) {
            SubEntry         subEntry = ((SubEntry) item);
            TreeItem<Object> treeItem = new TreeItem<> (subEntry);
            root.getChildren ().add (treeItem);

            addToTree (subEntry.getSubEntryList (), treeItem);

            addToTree (subEntry.getNoteGroup (), treeItem);

        } else if (item instanceof SubEntryGroup) {
            SubEntryGroup    subEntryGroup = ((SubEntryGroup) item);
            TreeItem<Object> treeItem      = new TreeItem<> (subEntryGroup);
            root.getChildren ().add (treeItem);

            for (SubEntry subEntry : subEntryGroup.getSubEntries ())
                addToTree (subEntry, treeItem);

        } else if (item instanceof SubEntryList) {
            SubEntryList     subEntryList = ((SubEntryList) item);
            TreeItem<Object> treeItem     = new TreeItem<> (subEntryList);
            root.getChildren ().add (treeItem);

            for (SubEntryListItem subEntryListItem : subEntryList.getSubEntryListItems ())
                addToTree (subEntryListItem, treeItem);

        } else if (item instanceof SubEntryListItem) {
            SubEntryListItem subEntryListItem = ((SubEntryListItem) item);
            TreeItem<Object> treeItem         = new TreeItem<> (subEntryListItem);
            root.getChildren ().add (treeItem);

        } else {
            System.err.println ("The Object was not recognized! Ignoring.");
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
            if (newFile) {
                FileChooser fileChooser = new FileChooser ();
                fileChooser.setTitle ("Save As...");
                fileChooser.getExtensionFilters ().add (new ExtensionFilter ("XHTML/XML Files", "*.xhtml", "*.XHTML", "*.xml", "*.XML"));

                currentFile = fileChooser.showSaveDialog (root.getScene ().getWindow ());
            }
            if (currentFile != null) {
                try {
                    Transformer transformer = TransformerFactory.newInstance ().newTransformer ();
                    transformer.setOutputProperty (OutputKeys.INDENT, "yes");
                    transformer.setOutputProperty ("{http://xml.apache.org/xslt}indent-amount", "4");

                    Document document = DocumentBuilderFactory.newInstance ().newDocumentBuilder ().newDocument ();
                    Element  root     = dictionary.toElement (document);
                    document.appendChild (root);
                    document.insertBefore (document.createProcessingInstruction ("xml-stylesheet", "type=\"text/css\" href=\"style.css\""), root);
                    document.insertBefore (document.createProcessingInstruction ("xml-stylesheet", "type=\"text/xsl\" href=\"webtransform.xsl\""), root);

                    DOMSource    source = new DOMSource (document);
                    StringWriter writer = new StringWriter ();
                    StreamResult result = new StreamResult (writer);

                    transformer.transform (source, result);

                    String outString = writer.toString ()
                            .replaceAll ("\\?><", "?>\n<")
                            .replaceFirst (" standalone=\"no\"", "")
                            .replaceFirst ("<d:dictionary", "\n<d:dictionary")
                            .replaceAll ("\\n[\\s]*<span d:pr", "<span d:pr")
                            .replaceAll (">\\(<", ">\n\t\t\t\t\t\t(\n\t\t\t\t\t\t<")
                            .replaceAll (">,<", ">\n\t\t\t\t\t\t,\t\t\t\t\t\t\n<")
                            .replaceAll (">\\)<", ">\n\t\t\t\t\t\t)\n\t\t\t\t\t<")
                            .replaceAll ("@@(.)", "<span id=\"$1\"/>")
                            .replaceAll ("\\*\\*\\*(.*?)\\*\\*\\*", "<b><i>$1</i></b>")
                            .replaceAll ("\\*\\*(.*?)\\*\\*", "<b>$1</b>")
                            .replaceAll ("\\*(.*?)\\*", "<i>$1</i>")
                            .replaceAll ("\\^\\((.*?)\\)", "<sup>$1</sup>")
                            .replaceAll ("\\^(.)", "<sup>$1</sup>");

                    FileOutputStream out = new FileOutputStream (currentFile);

                    out.write (outString.getBytes ("UTF-8"));

                    System.out.println ("File Saved!");

                    loadFile ();
                } catch (Exception e) {
                    e.printStackTrace ();
                }
            } else {
                System.err.println ("No file specified! Doing nothing. ^^");
            }
        } else {
            System.err.println ("No document is loaded! Doing nothing. ^^");
        }
    }

    public void saveFileAs () {
        newFile = true;
        saveFile ();
    }

    public void removeSelectedItem () {
        TreeItem<Object> treeItem = treeView.getSelectionModel ().getSelectedItem ();

        if (treeItem.getValue () == null) {

        } else if (treeItem.getValue () instanceof Definition) {
            ((DefinitionGroup) treeItem.getParent ().getValue ()).getDefinitions ().remove (treeItem.getValue ());

        } else if (treeItem.getValue () instanceof DefinitionGroup) {
            ((Entry) treeItem.getParent ().getValue ()).getDefinitionGroups ().remove (treeItem.getValue ());

        } else if (treeItem.getValue () instanceof DictEntry) {
            ((Dictionary) treeItem.getParent ().getValue ()).getEntries ().remove (treeItem.getValue ());

        } else if (treeItem.getValue () instanceof Entry) {
            ((EntryGroup) treeItem.getParent ().getValue ()).getEntries ().remove (treeItem.getValue ());

        } else if (treeItem.getValue () instanceof EntryGroup) {
            ((DictEntry) treeItem.getParent ().getValue ()).setEntryGroup (null);

        } else if (treeItem.getValue () instanceof Example) {
            ((ExampleGroup) treeItem.getParent ().getValue ()).getExamples ().remove (treeItem.getValue ());

        } else if (treeItem.getValue () instanceof ExampleGroup) {
            if (treeItem.getParent ().getValue () instanceof Definition) {
                ((Definition) treeItem.getParent ().getValue ()).setExampleGroup (null);

            } else if (treeItem.getParent ().getValue () instanceof SubDefinition) {
                ((SubDefinition) treeItem.getParent ().getValue ()).setExampleGroup (null);

            }
        } else if (treeItem.getValue () instanceof Form) {
            ((FormGroup) treeItem.getParent ().getValue ()).getForms ().remove (treeItem.getValue ());

        } else if (treeItem.getValue () instanceof FormGroup) {
            ((GrammarGroup) treeItem.getParent ().getValue ()).setFormGroup (null);

        } else if (treeItem.getValue () instanceof GrammarGroup) {
            ((Entry) treeItem.getParent ().getValue ()).setGrammarGroup (null);

        } else if (treeItem.getValue () instanceof HeadGroup) {
            ((DictEntry) treeItem.getParent ().getValue ()).setHeadGroup (null);

        } else if (treeItem.getValue () instanceof Index) {
            ((DictEntry) treeItem.getParent ().getValue ()).getIndices ().remove (treeItem.getValue ());

        } else if (treeItem.getValue () instanceof Note) {
            ((NoteGroup) treeItem.getParent ().getValue ()).getNotes ().remove (treeItem.getValue ());

        } else if (treeItem.getValue () instanceof NoteGroup) {
            ((SubEntry) treeItem.getParent ().getValue ()).setNoteGroup (null);

        } else if (treeItem.getValue () instanceof Pronunciation) {
            if (treeItem.getParent ().getValue () instanceof HeadGroup) {
                ((HeadGroup) treeItem.getParent ().getValue ()).setPronunciation (null);

            } else if (treeItem.getParent ().getValue () instanceof Form) {
                ((Form) treeItem.getParent ().getValue ()).setPronunciation (null);

            }
        } else if (treeItem.getValue () instanceof SubDefinition) {
            ((SubDefinitionGroup) treeItem.getParent ().getValue ()).getSubDefinitions ().remove (treeItem.getValue ());

        } else if (treeItem.getValue () instanceof SubDefinitionGroup) {
            ((Definition) treeItem.getParent ().getValue ()).setSubDefinitionGroup (null);

        } else if (treeItem.getValue () instanceof SubEntry) {
            ((SubEntryGroup) treeItem.getParent ().getValue ()).getSubEntries ().remove (treeItem.getValue ());

        } else if (treeItem.getValue () instanceof SubEntryGroup) {
            ((Entry) treeItem.getParent ().getValue ()).setSubEntryGroup (null);

        } else if (treeItem.getValue () instanceof SubEntryList) {
            ((SubEntry) treeItem.getParent ().getValue ()).setSubEntryList (null);

        } else if (treeItem.getValue () instanceof SubEntryListItem) {
            ((SubEntryList) treeItem.getParent ().getValue ()).getSubEntryListItems ().remove (treeItem.getValue ());

        }
        treeItem.getParent ().getChildren ().remove (treeItem);
    }

    public void addNewItem () {

        Scene scene = addItemButton.getScene ();

        Point2D windowCoord = new Point2D (scene.getWindow ().getX (), scene.getWindow ().getY ());

        Point2D sceneCoord = new Point2D (scene.getX (), scene.getY ());

        Point2D nodeCoord = addItemButton.localToScene (0.0, 0.0);

        double clickX = Math.round (windowCoord.getX () + sceneCoord.getY () + nodeCoord.getX ());

        double clickY = Math.round (windowCoord.getY () + sceneCoord.getY () + nodeCoord.getY ());

        setupPopOver (clickX - 36, clickY + 20);

    }

    public void setupPopOver (double x, double y) {
        VBox column = new VBox ();
        PopOver popOver = new PopOver (column);

        popOver.setHideOnEscape (true);
        popOver.setArrowLocation (PopOver.ArrowLocation.TOP_CENTER);
        popOver.setCornerRadius (4);
        popOver.setDetachable (false);

        TreeItem<Object> treeItem = treeView.getSelectionModel ().getSelectedItem ();

        if (treeItem != null) {
            if (treeItem.getValue () instanceof Definition) {

                HBox row = new HBox ();
                column.getChildren ().add (row);

                

            } else if (treeItem.getValue () instanceof DefinitionGroup) {

            } else if (treeItem.getValue () instanceof DictEntry) {

            } else if (treeItem.getValue () instanceof Entry) {

            } else if (treeItem.getValue () instanceof EntryGroup) {

            } else if (treeItem.getValue () instanceof Example) {

            } else if (treeItem.getValue () instanceof ExampleGroup) {

            } else if (treeItem.getValue () instanceof Form) {

            } else if (treeItem.getValue () instanceof FormGroup) {

            } else if (treeItem.getValue () instanceof GrammarGroup) {

            } else if (treeItem.getValue () instanceof HeadGroup) {

            } else if (treeItem.getValue () instanceof Index) {

            } else if (treeItem.getValue () instanceof Note) {

            } else if (treeItem.getValue () instanceof NoteGroup) {

            } else if (treeItem.getValue () instanceof Pronunciation) {

            } else if (treeItem.getValue () instanceof SubDefinition) {

            } else if (treeItem.getValue () instanceof SubDefinitionGroup) {

            } else if (treeItem.getValue () instanceof SubEntry) {

            } else if (treeItem.getValue () instanceof SubEntryGroup) {

            } else if (treeItem.getValue () instanceof SubEntryList) {

            } else if (treeItem.getValue () instanceof SubEntryListItem) {

            }
            popOver.show (addItemButton, x, y);
        }
    }
}
