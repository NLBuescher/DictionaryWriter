package com.nlbuescher.dictionarywriter;

import com.nlbuescher.dictionarywriter.dictionary.*;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
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
    public MenuItem newMenuItem;
    public MenuItem openMenuItem;
    public MenuItem saveMenuItem;
    public MenuItem saveAsMenuItem;

    public Button addItemButton;

    public TreeView<Object> treeView;

    public ScrollPane editorScrollPane;
    public VBox       editorVBox;
    public WebView    previewWebView;

    public Label statusBarLabel;

    private boolean documentLoaded = false;
    private boolean newFile        = false;
    private File       currentFile;
    private Dictionary dictionary;


    @Override
    public void initialize (URL location, ResourceBundle resources) {
        String os = System.getProperty ("os.name");

        if (os != null && (os.startsWith ("Mac"))) {
            menuBar.setUseSystemMenuBar (true);
            newMenuItem.setAccelerator (new KeyCodeCombination (KeyCode.N, KeyCombination.META_DOWN));
            openMenuItem.setAccelerator (new KeyCodeCombination (KeyCode.O, KeyCombination.META_DOWN));
            saveMenuItem.setAccelerator (new KeyCodeCombination (KeyCode.S, KeyCombination.META_DOWN));
            saveAsMenuItem.setAccelerator (new KeyCodeCombination (KeyCode.S, KeyCombination.META_DOWN, KeyCombination.SHIFT_DOWN));
        }

        treeView.getSelectionModel ().selectedItemProperty ().addListener ((observable, oldValue, newValue) -> {
            updateEditor (newValue);
            updatePreview (newValue);
        });
    }

    private void updateEditor (TreeItem<Object> treeItem) {
        editorVBox.getChildren ().clear ();

        if (treeItem == null) {
        } else if (treeItem.getValue () == null) {

        } else if (treeItem.getValue () instanceof Definition) {

            Label definitionLabel = new Label ("Definition");
            definitionLabel.setFont (Font.font (18));
            editorVBox.getChildren ().add (definitionLabel);

            Label definitionLabelLabel = new Label ("Definition Label");
            definitionLabelLabel.setPadding (new Insets (10, 0, 0, 0));
            editorVBox.getChildren ().add (definitionLabelLabel);

            TextField definitionLabelTextField = new TextField (((Definition) treeItem.getValue ()).getDefinitionLabel ());
            definitionLabelTextField.focusedProperty ().addListener ((observable, oldValue, newValue) -> {
                if (!newValue) {
                    ((Definition) treeItem.getValue ()).setDefinitionLabel (definitionLabelTextField.getText ());
                    treeItem.getParent ().getChildren ().set (treeItem.getParent ().getChildren ().indexOf (treeItem), treeItem);
                    updatePreview (treeItem);
                }
            });
            editorVBox.getChildren ().add (definitionLabelTextField);

            Label specificationLabel = new Label ("Specification");
            specificationLabel.setPadding (new Insets (10, 0, 0, 0));
            editorVBox.getChildren ().add (specificationLabel);

            TextField specificationTextField = new TextField (((Definition) treeItem.getValue ()).getSpecification ());
            specificationTextField.focusedProperty ().addListener ((observable, oldValue, newValue) -> {
                if (!newValue) {
                    ((Definition) treeItem.getValue ()).setSpecification (specificationTextField.getText ());
                    treeItem.getParent ().getChildren ().set (treeItem.getParent ().getChildren ().indexOf (treeItem), treeItem);
                    updatePreview (treeItem);
                }
            });
            editorVBox.getChildren ().add (specificationTextField);

            Label definitionTextLabel = new Label ("Definition Text");
            definitionTextLabel.setPadding (new Insets (10, 0, 0, 0));
            editorVBox.getChildren ().add (definitionTextLabel);

            TextField definitionTextTextField = new TextField (((Definition) treeItem.getValue ()).getDefinitionText ());
            definitionTextTextField.focusedProperty ().addListener ((observable, oldValue, newValue) -> {
                if (!newValue) {
                    ((Definition) treeItem.getValue ()).setDefinitionText (definitionTextTextField.getText ());
                    treeItem.getParent ().getChildren ().set (treeItem.getParent ().getChildren ().indexOf (treeItem), treeItem);
                    updatePreview (treeItem);
                }
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
            definitionGroupLabelTextField.focusedProperty ().addListener ((observable, oldValue, newValue) -> {
                if (!newValue) {
                    ((DefinitionGroup) treeItem.getValue ()).setDefinitionGroupLabel (definitionGroupLabelTextField.getText ());
                    treeItem.getParent ().getChildren ().set (treeItem.getParent ().getChildren ().indexOf (treeItem), treeItem);
                    updatePreview (treeItem);
                }
            });
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
                if (!newValue) {
                    ((DictEntry) treeItem.getValue ()).setId (entryIDTextField.getText ());
                    treeItem.getParent ().getChildren ().set (treeItem.getParent ().getChildren ().indexOf (treeItem), treeItem);
                    updatePreview (treeItem);
                }
            });
            editorVBox.getChildren ().add (entryIDTextField);

            Label entryTitleLabel = new Label ("Title");
            entryTitleLabel.setPadding (new Insets (10, 0, 0, 0));
            editorVBox.getChildren ().add (entryTitleLabel);

            TextField entryTitleTextField = new TextField (((DictEntry) treeItem.getValue ()).getTitle ());
            entryTitleTextField.focusedProperty ().addListener ((observable, oldValue, newValue) -> {
                if (!newValue) {
                    ((DictEntry) treeItem.getValue ()).setTitle (entryTitleTextField.getText ());
                    treeItem.getParent ().getChildren ().set (treeItem.getParent ().getChildren ().indexOf (treeItem), treeItem);
                    updatePreview (treeItem);
                }
            });
            editorVBox.getChildren ().add (entryTitleTextField);


            Label indicesLabel = new Label ("Indices");
            indicesLabel.setPadding (new Insets (10, 0, 0, 0));
            editorVBox.getChildren ().add (indicesLabel);

            TextField indexTitleTextField = new TextField ();
            TextField indexValueTextField = new TextField ();

            ListView<Index> indexListView = new ListView<> ();
            indexListView.setPrefHeight (120);
            for (Index index : ((DictEntry) treeItem.getValue ()).getIndices ())
                indexListView.getItems ().add (index);
            indexListView.getSelectionModel ().selectedItemProperty ().addListener ((observable, oldValue, newValue) -> {
                indexTitleTextField.setText (newValue.getTitle ());
                indexValueTextField.setText (newValue.getValue ());
            });
            editorVBox.getChildren ().add (indexListView);

            HBox buttons = new HBox ();
            buttons.setAlignment (Pos.CENTER_RIGHT);

            Button addButton = new Button ("+");
            addButton.setOnAction (actionEvent -> {
                Index index = new Index ();
                indexListView.getItems ().add (index);
                indexListView.getSelectionModel ().select (index);
            });
            buttons.getChildren ().add (addButton);

            Button removeButton = new Button ("-");
            removeButton.setOnAction (actionEvent -> {
                indexListView.getItems ().remove (indexListView.getSelectionModel ().getSelectedItem ());
            });
            buttons.getChildren ().add (removeButton);

            editorVBox.getChildren ().add (buttons);

            Label indexValueLabel = new Label ("Index Value");
            indexValueLabel.setPadding (new Insets (10, 0, 0, 0));
            editorVBox.getChildren ().add (indexValueLabel);

            indexValueTextField.focusedProperty ().addListener ((observable, oldValue, newValue) -> {
                if (!newValue) {
                    Index index = indexListView.getSelectionModel ().getSelectedItem ();
                    index.setValue (indexValueTextField.getText ());
                    indexListView.getItems ().set (indexListView.getItems ().indexOf (index), index);
                }
            });
            editorVBox.getChildren ().add (indexValueTextField);

            Label indexTitleLabel = new Label ("Index Title");
            indexTitleLabel.setPadding (new Insets (10, 0, 0, 0));
            editorVBox.getChildren ().add (indexTitleLabel);

            indexTitleTextField.focusedProperty ().addListener ((observable, oldValue, newValue) -> {
                if (!newValue) {
                    Index index = indexListView.getSelectionModel ().getSelectedItem ();
                    index.setTitle (indexTitleTextField.getText ());
                    indexListView.getItems ().set (indexListView.getItems ().indexOf (index), index);
                }
            });
            editorVBox.getChildren ().add (indexTitleTextField);

        } else if (treeItem.getValue () instanceof Dictionary) {

            Label dictionaryLabel = new Label ("Dictionary");
            dictionaryLabel.setFont (Font.font (18));
            editorVBox.getChildren ().add (dictionaryLabel);

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
                if (!newValue) {
                    ((Example) treeItem.getValue ()).setExampleLabel (exampleLabelTextField.getText ());
                    treeItem.getParent ().getChildren ().set (treeItem.getParent ().getChildren ().indexOf (treeItem), treeItem);
                    updatePreview (treeItem);
                }
            });
            editorVBox.getChildren ().add (exampleLabelTextField);

            Label specificationLabel = new Label ("Specification");
            specificationLabel.setPadding (new Insets (10, 0, 0, 0));
            editorVBox.getChildren ().add (specificationLabel);

            TextField specificationTextField = new TextField (((Example) treeItem.getValue ()).getSpecification ());
            specificationTextField.focusedProperty ().addListener ((observable, oldValue, newValue) -> {
                if (!newValue) {
                    ((Example) treeItem.getValue ()).setSpecification (specificationTextField.getText ());
                    treeItem.getParent ().getChildren ().set (treeItem.getParent ().getChildren ().indexOf (treeItem), treeItem);
                    updatePreview (treeItem);
                }
            });
            editorVBox.getChildren ().add (specificationTextField);

            Label exampleTextLabel = new Label ("Example Text");
            exampleTextLabel.setPadding (new Insets (10, 0, 0, 0));
            editorVBox.getChildren ().add (exampleTextLabel);

            TextField exampleTextTextField = new TextField (((Example) treeItem.getValue ()).getExampleText ());
            exampleTextTextField.focusedProperty ().addListener ((observable, oldValue, newValue) -> {
                if (!newValue) {
                    ((Example) treeItem.getValue ()).setExampleText (exampleTextTextField.getText ());
                    treeItem.getParent ().getChildren ().set (treeItem.getParent ().getChildren ().indexOf (treeItem), treeItem);
                    updatePreview (treeItem);
                }
            });
            editorVBox.getChildren ().add (exampleTextTextField);

            Label exampleTranslationLabel = new Label ("Example Translation");
            exampleTranslationLabel.setPadding (new Insets (10, 0, 0, 0));
            editorVBox.getChildren ().add (exampleTranslationLabel);

            TextField exampleTranslationTextField = new TextField (((Example) treeItem.getValue ()).getExampleTranslation ().replaceFirst ("\\s—\\s", ""));
            exampleTranslationTextField.focusedProperty ().addListener ((observable, oldValue, newValue) -> {
                if (!newValue) {
                    ((Example) treeItem.getValue ()).setExampleTranslation (" — " + exampleTranslationTextField.getText ());
                    treeItem.getParent ().getChildren ().set (treeItem.getParent ().getChildren ().indexOf (treeItem), treeItem);
                    updatePreview (treeItem);
                }
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

            TextField formLabelTextField = new TextField (((Form) treeItem.getValue ()).getFormLabel ().trim ());
            formLabelTextField.focusedProperty ().addListener ((observable, oldValue, newValue) -> {
                if (!newValue) {
                    ((Form) treeItem.getValue ()).setFormLabel (formLabelTextField.getText () + " ");
                    treeItem.getParent ().getChildren ().set (treeItem.getParent ().getChildren ().indexOf (treeItem), treeItem);
                    updatePreview (treeItem);
                }
            });
            editorVBox.getChildren ().add (formLabelTextField);

            Label formTextLabel = new Label ("Form Text");
            formTextLabel.setPadding (new Insets (10, 0, 0, 0));
            editorVBox.getChildren ().add (formTextLabel);

            TextField formTextTextField = new TextField (((Form) treeItem.getValue ()).getFormText ().trim ());
            formTextTextField.focusedProperty ().addListener ((observable, oldValue, newValue) -> {
                if (!newValue) {
                    ((Form) treeItem.getValue ()).setFormText (formTextTextField.getText () + " ");
                    treeItem.getParent ().getChildren ().set (treeItem.getParent ().getChildren ().indexOf (treeItem), treeItem);
                    updatePreview (treeItem);
                }
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
                if (!newValue) {
                    ((GrammarGroup) treeItem.getValue ()).setGrammar (grammarTextField.getText ());
                    treeItem.getParent ().getChildren ().set (treeItem.getParent ().getChildren ().indexOf (treeItem), treeItem);
                    updatePreview (treeItem);
                }
            });
            editorVBox.getChildren ().add (grammarTextField);

            Label specificationLabel = new Label ("Specification");
            specificationLabel.setPadding (new Insets (10, 0, 0, 0));
            editorVBox.getChildren ().add (specificationLabel);

            TextField specificationTextField = new TextField (((GrammarGroup) treeItem.getValue ()).getSpecification ());
            specificationTextField.focusedProperty ().addListener ((observable, oldValue, newValue) -> {
                if (!newValue) {
                    ((GrammarGroup) treeItem.getValue ()).setSpecification (specificationTextField.getText ());
                    treeItem.getParent ().getChildren ().set (treeItem.getParent ().getChildren ().indexOf (treeItem), treeItem);
                    updatePreview (treeItem);
                }
            });
            editorVBox.getChildren ().add (specificationTextField);

        } else if (treeItem.getValue () instanceof HeadGroup) {

            Label headGroupLabel = new Label ("Head Group");
            headGroupLabel.setFont (Font.font (18));
            editorVBox.getChildren ().add (headGroupLabel);

            Label headWordLabel = new Label ("Head Word");
            headWordLabel.setPadding (new Insets (10, 0, 0, 0));
            editorVBox.getChildren ().add (headWordLabel);

            TextField headWordTextField = new TextField (((HeadGroup) treeItem.getValue ()).getHeadWord ().trim ());
            headWordTextField.focusedProperty ().addListener ((observable, oldValue, newValue) -> {
                if (!newValue) {
                    ((HeadGroup) treeItem.getValue ()).setHeadWord (headWordTextField.getText () + " ");
                    treeItem.getParent ().getChildren ().set (treeItem.getParent ().getChildren ().indexOf (treeItem), treeItem);
                    updatePreview (treeItem);
                }
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
                if (!newValue) {
                    ((Index) treeItem.getValue ()).setTitle (indexTitleTextField.getText ());
                    treeItem.getParent ().getChildren ().set (treeItem.getParent ().getChildren ().indexOf (treeItem), treeItem);
                    updatePreview (treeItem);
                }
            });
            editorVBox.getChildren ().add (indexTitleTextField);

            Label indexValueLabel = new Label ("Value");
            indexValueLabel.setPadding (new Insets (10, 0, 0, 0));
            editorVBox.getChildren ().add (indexValueLabel);

            TextField indexValueTextField = new TextField (((Index) treeItem.getValue ()).getValue ());
            indexValueTextField.focusedProperty ().addListener ((observable, oldValue, newValue) -> {
                if (!newValue) {
                    ((Index) treeItem.getValue ()).setValue (indexValueTextField.getText ());
                    treeItem.getParent ().getChildren ().set (treeItem.getParent ().getChildren ().indexOf (treeItem), treeItem);
                    updatePreview (treeItem);
                }
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
                if (!newValue) {
                    ((Note) treeItem.getValue ()).setNoteText (noteTextTextArea.getText ());
                    treeItem.getParent ().getChildren ().set (treeItem.getParent ().getChildren ().indexOf (treeItem), treeItem);
                    updatePreview (treeItem);
                }
            });
            noteTextTextArea.setWrapText (true);
            noteTextTextArea.setMaxWidth (280);
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
                if (!newValue) {
                    ((Pronunciation) treeItem.getValue ()).setCL_IPA (clTextField.getText ());
                    treeItem.getParent ().getChildren ().set (treeItem.getParent ().getChildren ().indexOf (treeItem), treeItem);
                    updatePreview (treeItem);
                }
            });
            editorVBox.getChildren ().add (clTextField);

            Label vaLabel = new Label ("Ecclesiastical Pronunciation (IPA)");
            vaLabel.setPadding (new Insets (10, 0, 0, 0));
            editorVBox.getChildren ().add (vaLabel);

            TextField vaTextField = new TextField (((Pronunciation) treeItem.getValue ()).getVA_IPA ());
            vaTextField.focusedProperty ().addListener ((observable, oldValue, newValue) -> {
                if (!newValue) {
                    ((Pronunciation) treeItem.getValue ()).setVA_IPA (vaTextField.getText ());
                    treeItem.getParent ().getChildren ().set (treeItem.getParent ().getChildren ().indexOf (treeItem), treeItem);
                    updatePreview (treeItem);
                }
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
                if (!newValue) {
                    ((SubDefinition) treeItem.getValue ()).setSubDefinitionLabel (subDefinitionLabelTextField.getText ());
                    treeItem.getParent ().getChildren ().set (treeItem.getParent ().getChildren ().indexOf (treeItem), treeItem);
                    updatePreview (treeItem);
                }
            });
            editorVBox.getChildren ().add (subDefinitionLabelTextField);

            Label specificationLabel = new Label ("Specification");
            specificationLabel.setPadding (new Insets (10, 0, 0, 0));
            editorVBox.getChildren ().add (specificationLabel);

            TextField specificationTextField = new TextField (((SubDefinition) treeItem.getValue ()).getSpecification ());
            specificationTextField.focusedProperty ().addListener ((observable, oldValue, newValue) -> {
                if (!newValue) {
                    ((SubDefinition) treeItem.getValue ()).setSpecification (specificationTextField.getText ());
                    treeItem.getParent ().getChildren ().set (treeItem.getParent ().getChildren ().indexOf (treeItem), treeItem);
                    updatePreview (treeItem);
                }
            });
            editorVBox.getChildren ().add (specificationTextField);

            Label subDefinitionTextLabel = new Label ("Sub-Definition Text");
            subDefinitionTextLabel.setPadding (new Insets (10, 0, 0, 0));
            editorVBox.getChildren ().add (subDefinitionTextLabel);

            TextField subDefinitionTextTextField = new TextField (((SubDefinition) treeItem.getValue ()).getSubDefinitionText ());
            subDefinitionTextTextField.focusedProperty ().addListener ((observable, oldValue, newValue) -> {
                if (!newValue) {
                    ((SubDefinition) treeItem.getValue ()).setSubDefinitionText (subDefinitionTextTextField.getText ());
                    treeItem.getParent ().getChildren ().set (treeItem.getParent ().getChildren ().indexOf (treeItem), treeItem);
                    updatePreview (treeItem);
                }
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
                if (!newValue) {
                    ((SubEntry) treeItem.getValue ()).setSubEntryLabel (subEntryLabelTextField.getText ());
                    treeItem.getParent ().getChildren ().set (treeItem.getParent ().getChildren ().indexOf (treeItem), treeItem);
                    updatePreview (treeItem);
                }
            });
            editorVBox.getChildren ().add (subEntryLabelTextField);

            Label subEntryTextLabel = new Label ("Sub-Entry Text");
            subEntryTextLabel.setPadding (new Insets (10, 0, 0, 0));
            editorVBox.getChildren ().add (subEntryTextLabel);

            TextField subEntryTextTextField = new TextField (((SubEntry) treeItem.getValue ()).getSubEntryText ());
            subEntryTextTextField.focusedProperty ().addListener ((observable, oldValue, newValue) -> {
                if (!newValue) {
                    ((SubEntry) treeItem.getValue ()).setSubEntryText (subEntryTextTextField.getText ());
                    treeItem.getParent ().getChildren ().set (treeItem.getParent ().getChildren ().indexOf (treeItem), treeItem);
                    updatePreview (treeItem);
                }
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
                if (!newValue) {
                    ((SubEntryListItem) treeItem.getValue ()).setSubEntryListItemLabel (subEntryListItemLabelTextField.getText ());
                    treeItem.getParent ().getChildren ().set (treeItem.getParent ().getChildren ().indexOf (treeItem), treeItem);
                    updatePreview (treeItem);
                }
            });
            editorVBox.getChildren ().add (subEntryListItemLabelTextField);

            Label specificationLabel = new Label ("Specification");
            specificationLabel.setPadding (new Insets (10, 0, 0, 0));
            editorVBox.getChildren ().add (specificationLabel);

            TextField specificationTextField = new TextField (((SubEntryListItem) treeItem.getValue ()).getSpecification ());
            specificationTextField.focusedProperty ().addListener ((observable, oldValue, newValue) -> {
                if (!newValue) {
                    ((SubEntryListItem) treeItem.getValue ()).setSpecification (specificationTextField.getText ());
                    treeItem.getParent ().getChildren ().set (treeItem.getParent ().getChildren ().indexOf (treeItem), treeItem);
                    updatePreview (treeItem);
                }
            });
            editorVBox.getChildren ().add (specificationTextField);

            Label subEntryListItemTextLabel = new Label ("Sub-Entry List Item Text");
            subEntryListItemTextLabel.setPadding (new Insets (10, 0, 0, 0));
            editorVBox.getChildren ().add (subEntryListItemTextLabel);

            TextField subEntryListItemTextTextField = new TextField (((SubEntryListItem) treeItem.getValue ()).getSubEntryListItemText ());
            subEntryListItemTextTextField.focusedProperty ().addListener ((observable, oldValue, newValue) -> {
                if (!newValue) {
                    ((SubEntryListItem) treeItem.getValue ()).setSubEntryListItemText (subEntryListItemTextTextField.getText ());
                    treeItem.getParent ().getChildren ().set (treeItem.getParent ().getChildren ().indexOf (treeItem), treeItem);
                    updatePreview (treeItem);
                }
            });
            editorVBox.getChildren ().add (subEntryListItemTextTextField);

        }
    }

    private void updatePreview (TreeItem<Object> treeItem) {
        TreeItem<Object> item = treeItem;

        if (!(item.getValue () instanceof Dictionary))
            while (!(item.getValue () instanceof DictEntry))
                item = item.getParent ();

        try {
            Document document = DocumentBuilderFactory.newInstance ().newDocumentBuilder ().newDocument ();

            Element root;
            if (item.getValue () instanceof Dictionary) {
                root = ((Dictionary) item.getValue ()).toElement (document);
            } else {
                Dictionary dict = new Dictionary ();
                dict.getEntries ().add ((DictEntry) item.getValue ());
                root = dict.toElement (document);
            }

            document.appendChild (root);
            document.insertBefore (document.createProcessingInstruction ("xml-stylesheet", "type=\"text/css\" href=\"style.css\""), root);
            document.insertBefore (document.createProcessingInstruction ("xml-stylesheet", "type=\"text/xsl\" href=\"webtransform.xsl\""), root);

            DOMSource    source = new DOMSource (document);
            StringWriter writer = new StringWriter ();
            StreamResult result = new StreamResult (writer);

            TransformerFactory.newInstance ().newTransformer ().transform (source, result);

            String previewString = writer.toString ()
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

            File preview = new File ("./~preview.xhtml").getCanonicalFile ();
            FileOutputStream out = new FileOutputStream (preview);

            out.write (previewString.getBytes ("UTF-8"));

            WebEngine webEngine = previewWebView.getEngine ();
            webEngine.load (preview.toURI ().toURL ().toString ());
        } catch (Exception e) {
            e.printStackTrace ();
        }
    }

    private void loadFile () {
        DictionaryWriter.getStage ().setTitle ("Dictionary Writer - [" + currentFile.getAbsolutePath () + "]");

        try {
            String inputString = new Scanner (currentFile).useDelimiter ("\\Z").next ()
                    .replaceAll ("<span id=\"(.)\"/>", "@@$1")
                    .replaceAll ("<b>(.*?)</b>", "**$1**")
                    .replaceAll ("<i>(.*?)</i>", "*$1*")
                    .replaceAll ("<sup>(.*?)</sup>", "^($1)");

            Element element = DocumentBuilderFactory.newInstance ().newDocumentBuilder ().parse (new InputSource (new StringReader (inputString))).getDocumentElement ();
            dictionary = Dictionary.fromElement (element);
            TreeItem<Object> treeRoot = new TreeItem<> (dictionary);
            treeRoot.setExpanded (true);
            treeRoot.expandedProperty ().addListener ((observable, oldValue, newValue) -> {
                if (!newValue)
                    treeRoot.setExpanded (true);
            });

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
            treeItem.setExpanded (true);
            root.getChildren ().add (treeItem);

            addToTree (definition.getExampleGroup (), treeItem);

            addToTree (definition.getSubDefinitionGroup (), treeItem);

        } else if (item instanceof DefinitionGroup) {
            DefinitionGroup  definitionGroup = ((DefinitionGroup) item);
            TreeItem<Object> treeItem        = new TreeItem<> (definitionGroup);
            treeItem.setExpanded (true);
            root.getChildren ().add (treeItem);

            for (Definition definition : definitionGroup.getDefinitions ())
                addToTree (definition, treeItem);

        } else if (item instanceof DictEntry) {
            DictEntry        entry    = ((DictEntry) item);
            TreeItem<Object> treeItem = new TreeItem<> (entry);
            root.getChildren ().add (treeItem);

//            for (Index index : entry.getIndices ())
//                addToTree (index, treeItem);

            addToTree (entry.getHeadGroup (), treeItem);

            addToTree (entry.getEntryGroup (), treeItem);

        } else if (item instanceof Entry) {
            Entry            entry    = ((Entry) item);
            TreeItem<Object> treeItem = new TreeItem<> (entry);
            treeItem.setExpanded (true);
            root.getChildren ().add (treeItem);

            addToTree (entry.getGrammarGroup (), treeItem);

            for (DefinitionGroup definitionGroup : entry.getDefinitionGroups ())
                addToTree (definitionGroup, treeItem);

            addToTree (entry.getSubEntryGroup (), treeItem);

        } else if (item instanceof EntryGroup) {
            EntryGroup       entryGroup = ((EntryGroup) item);
            TreeItem<Object> treeItem   = new TreeItem<> (entryGroup);
            treeItem.setExpanded (true);
            root.getChildren ().add (treeItem);

            for (Entry entry : entryGroup.getEntries ())
                addToTree (entry, treeItem);

        } else if (item instanceof Example) {
            Example          example  = ((Example) item);
            TreeItem<Object> treeItem = new TreeItem<> (example);
            treeItem.setExpanded (true);
            root.getChildren ().add (treeItem);

        } else if (item instanceof ExampleGroup) {
            ExampleGroup     exampleGroup = ((ExampleGroup) item);
            TreeItem<Object> treeItem     = new TreeItem<> (exampleGroup);
            treeItem.setExpanded (true);
            root.getChildren ().add (treeItem);

            for (Example example : exampleGroup.getExamples ())
                addToTree (example, treeItem);

        } else if (item instanceof Form) {
            Form             form     = ((Form) item);
            TreeItem<Object> treeItem = new TreeItem<> (form);
            treeItem.setExpanded (true);
            root.getChildren ().add (treeItem);

            addToTree (form.getPronunciation (), treeItem);

        } else if (item instanceof FormGroup) {
            FormGroup        formGroup = ((FormGroup) item);
            TreeItem<Object> treeItem  = new TreeItem<> (formGroup);
            treeItem.setExpanded (true);
            root.getChildren ().add (treeItem);

            for (Form form : formGroup.getForms ())
                addToTree (form, treeItem);

        } else if (item instanceof GrammarGroup) {
            GrammarGroup     grammarGroup = ((GrammarGroup) item);
            TreeItem<Object> treeItem     = new TreeItem<> (grammarGroup);
            treeItem.setExpanded (true);
            root.getChildren ().add (treeItem);

            addToTree (grammarGroup.getFormGroup (), treeItem);

        } else if (item instanceof HeadGroup) {
            HeadGroup        headGroup = ((HeadGroup) item);
            TreeItem<Object> treeItem  = new TreeItem<> (headGroup);
            treeItem.setExpanded (true);
            root.getChildren ().add (treeItem);

            addToTree (headGroup.getPronunciation (), treeItem);

        } else if (item instanceof Index) {
            Index            index    = ((Index) item);
            TreeItem<Object> treeItem = new TreeItem<> (index);
            treeItem.setExpanded (true);
            root.getChildren ().add (treeItem);

        } else if (item instanceof Note) {
            Note             note     = ((Note) item);
            TreeItem<Object> treeItem = new TreeItem<> (note);
            treeItem.setExpanded (true);
            root.getChildren ().add (treeItem);

        } else if (item instanceof NoteGroup) {
            NoteGroup        noteGroup = ((NoteGroup) item);
            TreeItem<Object> treeItem  = new TreeItem<> (noteGroup);
            treeItem.setExpanded (true);
            root.getChildren ().add (treeItem);

            for (Note note : noteGroup.getNotes ())
                addToTree (note, treeItem);

        } else if (item instanceof Pronunciation) {
            Pronunciation    pronunciation = ((Pronunciation) item);
            TreeItem<Object> treeItem      = new TreeItem<> (pronunciation);
            treeItem.setExpanded (true);
            root.getChildren ().add (treeItem);

        } else if (item instanceof SubDefinition) {
            SubDefinition    subDefinition = ((SubDefinition) item);
            TreeItem<Object> treeItem      = new TreeItem<> (subDefinition);
            treeItem.setExpanded (true);
            root.getChildren ().add (treeItem);

            addToTree (subDefinition.getExampleGroup (), treeItem);

        } else if (item instanceof SubDefinitionGroup) {
            SubDefinitionGroup subDefinitionGroup = ((SubDefinitionGroup) item);
            TreeItem<Object>   treeItem           = new TreeItem<> (subDefinitionGroup);
            treeItem.setExpanded (true);
            root.getChildren ().add (treeItem);

            for (SubDefinition subDefinition : subDefinitionGroup.getSubDefinitions ())
                addToTree (subDefinition, treeItem);

        } else if (item instanceof SubEntry) {
            SubEntry         subEntry = ((SubEntry) item);
            TreeItem<Object> treeItem = new TreeItem<> (subEntry);
            treeItem.setExpanded (true);
            root.getChildren ().add (treeItem);

            addToTree (subEntry.getSubEntryList (), treeItem);

            addToTree (subEntry.getNoteGroup (), treeItem);

        } else if (item instanceof SubEntryGroup) {
            SubEntryGroup    subEntryGroup = ((SubEntryGroup) item);
            TreeItem<Object> treeItem      = new TreeItem<> (subEntryGroup);
            treeItem.setExpanded (true);
            root.getChildren ().add (treeItem);

            for (SubEntry subEntry : subEntryGroup.getSubEntries ())
                addToTree (subEntry, treeItem);

        } else if (item instanceof SubEntryList) {
            SubEntryList     subEntryList = ((SubEntryList) item);
            TreeItem<Object> treeItem     = new TreeItem<> (subEntryList);
            treeItem.setExpanded (true);
            root.getChildren ().add (treeItem);

            for (SubEntryListItem subEntryListItem : subEntryList.getSubEntryListItems ())
                addToTree (subEntryListItem, treeItem);

        } else if (item instanceof SubEntryListItem) {
            SubEntryListItem subEntryListItem = ((SubEntryListItem) item);
            TreeItem<Object> treeItem         = new TreeItem<> (subEntryListItem);
            treeItem.setExpanded (true);
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
                DictionaryWriter.getStage ().setTitle ("Dictionary Writer - [" + currentFile.getAbsolutePath () + "]");

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

        if (!(treeItem.getValue () instanceof Dictionary)) {
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
    } 

    public void addNewItem () {

        Scene scene = addItemButton.getScene ();

        Point2D windowCoord = new Point2D (scene.getWindow ().getX (), scene.getWindow ().getY ());

        Point2D sceneCoord = new Point2D (scene.getX (), scene.getY ());

        Point2D nodeCoord = addItemButton.localToScene (0.0, 0.0);

        double clickX = Math.round (windowCoord.getX () + sceneCoord.getY () + nodeCoord.getX ());

        double clickY = Math.round (windowCoord.getY () + sceneCoord.getY () + nodeCoord.getY ());

        setupPopOver (clickX - 18, clickY - 22);
    }

    private void setupPopOver (double x, double y) {
        VBox column = new VBox ();
        column.setPadding (new Insets (10, 10, 10, 10));

        PopOver popOver = new PopOver (column);
        popOver.setHideOnEscape (true);
        popOver.setArrowLocation (PopOver.ArrowLocation.LEFT_TOP);
        popOver.setCornerRadius (4);
        popOver.setDetachable (false);
        popOver.setTitle ("Add");
        popOver.setHeaderAlwaysVisible (true);

        TreeItem<Object> treeItem = treeView.getSelectionModel ().getSelectedItem ();

        if (treeItem == null) {
        } else if (treeItem.getValue () instanceof Definition) {

            HBox exampleGroupRow = new HBox ();
            exampleGroupRow.setAlignment (Pos.CENTER_LEFT);
            exampleGroupRow.setSpacing (10);

            Button exampleGroupButton = new Button ("+");
            exampleGroupButton.setOnAction (actionEvent -> {
                if (((Definition) treeItem.getValue ()).getExampleGroup () == null) {
                    ExampleGroup exampleGroup = new ExampleGroup ();
                    ((Definition) treeItem.getValue ()).setExampleGroup (exampleGroup);

                    int index = findIndexFor (ExampleGroup.class, treeItem.getChildren ());

                    TreeItem<Object> exampleGroupTreeItem = new TreeItem<> (exampleGroup);
                    treeItem.getChildren ().add (index, exampleGroupTreeItem);
                    treeView.getSelectionModel ().select (exampleGroupTreeItem);

                    popOver.hide ();

                } else {
                    System.err.println ("The selected item already has an Example Group!");
                }
            });
            exampleGroupRow.getChildren ().add (exampleGroupButton);

            Label exampleGroupLabel = new Label ("Example Group");
            exampleGroupRow.getChildren ().add (exampleGroupLabel);

            column.getChildren ().add (exampleGroupRow);


            HBox subDefinitionGroupRow = new HBox ();
            subDefinitionGroupRow.setAlignment (Pos.CENTER_LEFT);
            subDefinitionGroupRow.setSpacing (10);

            Button subDefinitionGroupButton = new Button ("+");
            subDefinitionGroupButton.setOnAction (actionEvent -> {
                if (((Definition) treeItem.getValue ()).getSubDefinitionGroup () == null) {
                    SubDefinitionGroup subDefinitionGroup = new SubDefinitionGroup ();
                    ((Definition) treeItem.getValue ()).setSubDefinitionGroup (subDefinitionGroup);

                    TreeItem<Object> subDefinitionGroupTreeItem = new TreeItem<> (subDefinitionGroup);
                    treeItem.getChildren ().add (subDefinitionGroupTreeItem);
                    treeView.getSelectionModel ().select (subDefinitionGroupTreeItem);

                    popOver.hide ();

                } else {
                    System.err.println ("The selected item already has a Sub-Definition Group!");
                }
            });
            subDefinitionGroupRow.getChildren ().add (subDefinitionGroupButton);

            Label subDefinitionGroupLabel = new Label ("Sub-Definition Group");
            subDefinitionGroupRow.getChildren ().add (subDefinitionGroupLabel);

            column.getChildren ().add (subDefinitionGroupRow);

            popOver.show (addItemButton.getScene ().getWindow (), x, y);

        } else if (treeItem.getValue () instanceof DefinitionGroup) {

            HBox definitionRow = new HBox ();
            definitionRow.setAlignment (Pos.CENTER_LEFT);
            definitionRow.setSpacing (10);

            Button definitionButton = new Button ("+");
            definitionButton.setOnAction (actionEvent -> {
                Definition definition = new Definition ();
                ((DefinitionGroup) treeItem.getValue ()).getDefinitions ().add (definition);

                TreeItem<Object> definitionTreeItem = new TreeItem<> (definition);
                treeItem.getChildren ().add (definitionTreeItem);
                treeView.getSelectionModel ().select (definitionTreeItem);

                popOver.hide ();
            });
            definitionRow.getChildren ().add (definitionButton);

            Label definitionLabel = new Label ("Definition");
            definitionRow.getChildren ().add (definitionLabel);

            column.getChildren ().add (definitionRow);

            popOver.show (addItemButton.getScene ().getWindow (), x, y);

        } else if (treeItem.getValue () instanceof DictEntry) {

//            HBox indexRow = new HBox ();
//            indexRow.setAlignment (Pos.CENTER_LEFT);
//            indexRow.setSpacing (10);
//
//            Button indexButton = new Button ("+");
//            indexButton.setOnAction (actionEvent -> {
//                Index index = new Index ();
//                ((DictEntry) treeItem.getValue ()).getIndices ().add (index);
//
//                int i = findIndexFor (Index.class, treeItem.getChildren ());
//
//                TreeItem<Object> indexTreeItem = new TreeItem<> (index);
//                treeItem.getChildren ().add (i, indexTreeItem);
//                treeView.getSelectionModel ().select (indexTreeItem);
//
//                popOver.hide ();
//            });
//            indexRow.getChildren ().add (indexButton);
//
//            Label indexLabel = new Label ("Index");
//            indexRow.getChildren ().add (indexLabel);
//
//            column.getChildren ().add (indexRow);


            HBox headGroupRow = new HBox ();
            headGroupRow.setAlignment (Pos.CENTER_LEFT);
            headGroupRow.setSpacing (10);

            Button headGroupButton = new Button ("+");
            headGroupButton.setOnAction (actionEvent -> {
                if (((DictEntry) treeItem.getValue ()).getHeadGroup () == null) {
                    HeadGroup headGroup = new HeadGroup ();
                    ((DictEntry) treeItem.getValue ()).setHeadGroup (headGroup);

                    int index = findIndexFor (HeadGroup.class, treeItem.getChildren ());

                    TreeItem<Object> headGroupTreeItem = new TreeItem<> (headGroup);
                    treeItem.getChildren ().add (index, headGroupTreeItem);
                    treeView.getSelectionModel ().select (headGroupTreeItem);

                    popOver.hide ();

                } else {
                    System.err.println ("The selected item already has a Head Group!");
                }
            });
            headGroupRow.getChildren ().add (headGroupButton);

            Label headGroupLabel = new Label ("Head Group");
            headGroupRow.getChildren ().add (headGroupLabel);

            column.getChildren ().add (headGroupRow);


            HBox entryGroupRow = new HBox ();
            entryGroupRow.setAlignment (Pos.CENTER_LEFT);
            entryGroupRow.setSpacing (10);

            Button entryGroupButton = new Button ("+");
            entryGroupButton.setOnAction (actionEvent -> {
                if (((DictEntry) treeItem.getValue ()).getEntryGroup () == null) {
                    EntryGroup entryGroup = new EntryGroup ();
                    ((DictEntry) treeItem.getValue ()).setEntryGroup (entryGroup);

                    TreeItem<Object> entryGroupTreeItem = new TreeItem<> (entryGroup);
                    treeItem.getChildren ().add (entryGroupTreeItem);
                    treeView.getSelectionModel ().select (entryGroupTreeItem);

                    popOver.hide ();

                } else {
                    System.err.println ("The selected item already has an Entry Group!");
                }
            });
            entryGroupRow.getChildren ().add (entryGroupButton);

            Label entryGroupLabel = new Label ("Entry Group");
            entryGroupRow.getChildren ().add (entryGroupLabel);

            column.getChildren ().add (entryGroupRow);

            popOver.show (addItemButton.getScene ().getWindow (), x, y);

        } else if (treeItem.getValue () instanceof Dictionary) {

            HBox entryRow = new HBox ();
            entryRow.setAlignment (Pos.CENTER_LEFT);
            entryRow.setSpacing (10);

            Button entryButton = new Button ("+");
            entryButton.setOnAction (actionEvent -> {
                DictEntry entry = new DictEntry ();
                ((Dictionary) treeItem.getValue ()).getEntries ().add (entry);

                TreeItem<Object> entryTreeItem = new TreeItem<> (entry);
                treeItem.getChildren ().add (entryTreeItem);
                treeView.getSelectionModel ().select (entryTreeItem);

                popOver.hide ();
            });
            entryRow.getChildren ().add (entryButton);

            Label entryLabel = new Label ("Dictionary Entry");
            entryRow.getChildren ().add (entryLabel);

            column.getChildren ().add (entryRow);

            popOver.show (addItemButton.getScene ().getWindow (), x, y);

        } else if (treeItem.getValue () instanceof Entry) {

            HBox grammarGroupRow = new HBox ();
            grammarGroupRow.setAlignment (Pos.CENTER_LEFT);
            grammarGroupRow.setSpacing (10);

            Button grammarGroupButton = new Button ("+");
            grammarGroupButton.setOnAction (actionEvent -> {
                if (((Entry) treeItem.getValue ()).getGrammarGroup () == null) {
                    GrammarGroup grammarGroup = new GrammarGroup ();
                    ((Entry) treeItem.getValue ()).setGrammarGroup (grammarGroup);

                    int index = findIndexFor (GrammarGroup.class, treeItem.getChildren ());

                    TreeItem<Object> grammarGroupTreeItem = new TreeItem<> (grammarGroup);
                    treeItem.getChildren ().add (index, grammarGroupTreeItem);
                    treeView.getSelectionModel ().select (grammarGroupTreeItem);

                    popOver.hide ();

                } else {
                    System.err.println ("The selected item already has a Grammar Group!");
                }
            });
            grammarGroupRow.getChildren ().add (grammarGroupButton);

            Label grammarGroupLabel = new Label ("Grammar Group");
            grammarGroupRow.getChildren ().add (grammarGroupLabel);

            column.getChildren ().add (grammarGroupRow);


            HBox definitionGroupRow = new HBox ();
            definitionGroupRow.setAlignment (Pos.CENTER_LEFT);
            definitionGroupRow.setSpacing (10);

            Button definitionGroupButton = new Button ("+");
            definitionGroupButton.setOnAction (actionEvent -> {
                DefinitionGroup definitionGroup = new DefinitionGroup ();
                ((Entry) treeItem.getValue ()).getDefinitionGroups ().add (definitionGroup);

                int index = findIndexFor (DefinitionGroup.class, treeItem.getChildren ());

                TreeItem<Object> definitionGroupTreeItem = new TreeItem<> (definitionGroup);
                treeItem.getChildren ().add (index, definitionGroupTreeItem);
                treeView.getSelectionModel ().select (definitionGroupTreeItem);

                popOver.hide ();
            });
            definitionGroupRow.getChildren ().add (definitionGroupButton);

            Label definitionGroupLabel = new Label ("Definition Group");
            definitionGroupRow.getChildren ().add (definitionGroupLabel);

            column.getChildren ().add (definitionGroupRow);


            HBox subEntryGroupRow = new HBox ();
            subEntryGroupRow.setAlignment (Pos.CENTER_LEFT);
            subEntryGroupRow.setSpacing (10);

            Button subEntryGroupButton = new Button ("+");
            subEntryGroupButton.setOnAction (actionEvent -> {
                if (((Entry) treeItem.getValue ()).getSubEntryGroup () == null) {
                    SubEntryGroup subEntryGroup = new SubEntryGroup ();
                    ((Entry) treeItem.getValue ()).setSubEntryGroup (subEntryGroup);

                    TreeItem<Object> subEntryGroupTreeItem = new TreeItem<> (subEntryGroup);
                    treeItem.getChildren ().add (subEntryGroupTreeItem);
                    treeView.getSelectionModel ().select (subEntryGroupTreeItem);

                    popOver.hide ();

                } else {
                    System.err.println ("The selected item already has a Sub-Entry Group!");
                }
            });
            subEntryGroupRow.getChildren ().add (subEntryGroupButton);

            Label subEntryGroupLabel = new Label ("Sub-Entry Group");
            subEntryGroupRow.getChildren ().add (subEntryGroupLabel);

            column.getChildren ().add (subEntryGroupRow);

            popOver.show (addItemButton.getScene ().getWindow (), x, y);

        } else if (treeItem.getValue () instanceof EntryGroup) {

            HBox entryRow = new HBox ();
            entryRow.setAlignment (Pos.CENTER_LEFT);
            entryRow.setSpacing (10);

            Button entryButton = new Button ("+");
            entryButton.setOnAction (actionEvent -> {
                Entry entry = new Entry ();
                ((EntryGroup) treeItem.getValue ()).getEntries ().add (entry);

                TreeItem<Object> entryTreeItem = new TreeItem<> (entry);
                treeItem.getChildren ().add (entryTreeItem);
                treeView.getSelectionModel ().select (entryTreeItem);

                popOver.hide ();
            });
            entryRow.getChildren ().add (entryButton);

            Label entryLabel = new Label ("Entry");
            entryRow.getChildren ().add (entryLabel);

            column.getChildren ().add (entryRow);

            popOver.show (addItemButton.getScene ().getWindow (), x, y);

        } else if (treeItem.getValue () instanceof ExampleGroup) {

            HBox exampleRow = new HBox ();
            exampleRow.setAlignment (Pos.CENTER_LEFT);
            exampleRow.setSpacing (10);

            Button exampleButton = new Button ("+");
            exampleButton.setOnAction (actionEvent -> {
                Example example = new Example ();
                ((ExampleGroup) treeItem.getValue ()).getExamples ().add (example);

                TreeItem<Object> exampleTreeItem = new TreeItem<> (example);
                treeItem.getChildren ().add (exampleTreeItem);
                treeView.getSelectionModel ().select (exampleTreeItem);

                popOver.hide ();
            });
            exampleRow.getChildren ().add (exampleButton);

            Label exampleLabel = new Label ("Example");
            exampleRow.getChildren ().add (exampleLabel);

            column.getChildren ().add (exampleRow);

            popOver.show (addItemButton.getScene ().getWindow (), x, y);

        } else if (treeItem.getValue () instanceof Form) {

            HBox pronunciationRow = new HBox ();
            pronunciationRow.setAlignment (Pos.CENTER_LEFT);
            pronunciationRow.setSpacing (10);

            Button pronunciationButton = new Button ("+");
            pronunciationButton.setOnAction (actionEvent -> {
                if (((Form) treeItem.getValue ()).getPronunciation () == null) {
                    Pronunciation pronunciation = new Pronunciation ();
                    ((Form) treeItem.getValue ()).setPronunciation (pronunciation);

                    TreeItem<Object> pronunciationTreeItem = new TreeItem<> (pronunciation);
                    treeItem.getChildren ().add (pronunciationTreeItem);
                    treeView.getSelectionModel ().select (pronunciationTreeItem);

                    popOver.hide ();
                } else {
                    System.err.println ("The selected item already has a Pronunciation!");
                }
            });
            pronunciationRow.getChildren ().add (pronunciationButton);

            Label pronunciationLabel = new Label ("Pronunciation");
            pronunciationRow.getChildren ().add (pronunciationLabel);

            column.getChildren ().add (pronunciationRow);

            popOver.show (addItemButton.getScene ().getWindow (), x, y);

        } else if (treeItem.getValue () instanceof FormGroup) {

            HBox formRow = new HBox ();
            formRow.setAlignment (Pos.CENTER_LEFT);
            formRow.setSpacing (10);

            Button formButton = new Button ("+");
            formButton.setOnAction (actionEvent -> {
                Form form = new Form ();
                ((FormGroup) treeItem.getValue ()).getForms ().add (form);

                TreeItem<Object> formTreeItem = new TreeItem<> (form);
                treeItem.getChildren ().add (formTreeItem);
                treeView.getSelectionModel ().select (formTreeItem);

                popOver.hide ();
            });
            formRow.getChildren ().add (formButton);

            Label formLabel = new Label ("Form");
            formRow.getChildren ().add (formLabel);

            column.getChildren ().add (formRow);

            popOver.show (addItemButton.getScene ().getWindow (), x, y);

        } else if (treeItem.getValue () instanceof GrammarGroup) {

            HBox formGroupRow = new HBox ();
            formGroupRow.setAlignment (Pos.CENTER_LEFT);
            formGroupRow.setSpacing (10);

            Button formGroupButton = new Button ("+");
            formGroupButton.setOnAction (actionEvent -> {
                if (((GrammarGroup) treeItem.getValue ()).getFormGroup () == null) {
                    FormGroup formGroup = new FormGroup ();
                    ((GrammarGroup) treeItem.getValue ()).setFormGroup (formGroup);

                    TreeItem<Object> formGroupTreeItem = new TreeItem<> (formGroup);
                    treeItem.getChildren ().add (formGroupTreeItem);
                    treeView.getSelectionModel ().select (formGroupTreeItem);

                    popOver.hide ();
                } else {
                    System.err.println ("The selected item already has a Form Group!");
                }
            });
            formGroupRow.getChildren ().add (formGroupButton);

            Label formGroupLabel = new Label ("Form Group");
            formGroupRow.getChildren ().add (formGroupLabel);

            column.getChildren ().add (formGroupRow);

            popOver.show (addItemButton.getScene ().getWindow (), x, y);

        } else if (treeItem.getValue () instanceof HeadGroup) {

            HBox pronunciationRow = new HBox ();
            pronunciationRow.setAlignment (Pos.CENTER_LEFT);
            pronunciationRow.setSpacing (10);

            Button pronunciationButton = new Button ("+");
            pronunciationButton.setOnAction (actionEvent -> {
                if (((HeadGroup) treeItem.getValue ()).getPronunciation () == null) {
                    Pronunciation pronunciation = new Pronunciation ();
                    ((HeadGroup) treeItem.getValue ()).setPronunciation (pronunciation);

                    TreeItem<Object> pronunciationTreeItem = new TreeItem<> (pronunciation);
                    treeItem.getChildren ().add (pronunciationTreeItem);
                    treeView.getSelectionModel ().select (pronunciationTreeItem);

                    popOver.hide ();
                } else {
                    System.err.println ("The selected item already has a Pronunciation!");
                }
            });
            pronunciationRow.getChildren ().add (pronunciationButton);

            Label pronunciationLabel = new Label ("Pronunciation");
            pronunciationRow.getChildren ().add (pronunciationLabel);

            column.getChildren ().add (pronunciationRow);

            popOver.show (addItemButton.getScene ().getWindow (), x, y);

        } else if (treeItem.getValue () instanceof NoteGroup) {

            HBox noteRow = new HBox ();
            noteRow.setAlignment (Pos.CENTER_LEFT);
            noteRow.setSpacing (10);

            Button noteButton = new Button ("+");
            noteButton.setOnAction (actionEvent -> {
                Note note = new Note ();
                ((NoteGroup) treeItem.getValue ()).getNotes ().add (note);

                TreeItem<Object> noteTreeItem = new TreeItem<> (note);
                treeItem.getChildren ().add (noteTreeItem);
                treeView.getSelectionModel ().select (noteTreeItem);

                popOver.hide ();
            });
            noteRow.getChildren ().add (noteButton);

            Label noteLabel = new Label ("Note");
            noteRow.getChildren ().add (noteLabel);

            column.getChildren ().add (noteRow);

            popOver.show (addItemButton.getScene ().getWindow (), x, y);

        } else if (treeItem.getValue () instanceof SubDefinition) {

            HBox exampleGroupRow = new HBox ();
            exampleGroupRow.setAlignment (Pos.CENTER_LEFT);
            exampleGroupRow.setSpacing (10);

            Button exampleGroupButton = new Button ("+");
            exampleGroupButton.setOnAction (actionEvent -> {
                if (((SubDefinition) treeItem.getValue ()).getExampleGroup () == null) {
                    ExampleGroup exampleGroup = new ExampleGroup ();
                    ((SubDefinition) treeItem.getValue ()).setExampleGroup (exampleGroup);

                    TreeItem<Object> exampleGroupTreeItem = new TreeItem<> (exampleGroup);
                    treeItem.getChildren ().add (exampleGroupTreeItem);
                    treeView.getSelectionModel ().select (exampleGroupTreeItem);

                    popOver.hide ();
                } else {
                    System.err.println ("The selected item already has am Example Group!");
                }
            });
            exampleGroupRow.getChildren ().add (exampleGroupButton);

            Label exampleGroupLabel = new Label ("Example Group");
            exampleGroupRow.getChildren ().add (exampleGroupLabel);

            column.getChildren ().add (exampleGroupRow);

            popOver.show (addItemButton.getScene ().getWindow (), x, y);

        } else if (treeItem.getValue () instanceof SubDefinitionGroup) {

            HBox subDefinitionRow = new HBox ();
            subDefinitionRow.setAlignment (Pos.CENTER_LEFT);
            subDefinitionRow.setSpacing (10);

            Button subDefinitionButton = new Button ("+");
            subDefinitionButton.setOnAction (actionEvent -> {
                SubDefinition subDefinition = new SubDefinition ();
                ((SubDefinitionGroup) treeItem.getValue ()).getSubDefinitions ().add (subDefinition);

                TreeItem<Object> subDefinitionTreeItem = new TreeItem<> (subDefinition);
                treeItem.getChildren ().add (subDefinitionTreeItem);
                treeView.getSelectionModel ().select (subDefinitionTreeItem);

                popOver.hide ();
            });
            subDefinitionRow.getChildren ().add (subDefinitionButton);

            Label subDefinitionLabel = new Label ("Sub-Definition");
            subDefinitionRow.getChildren ().add (subDefinitionLabel);

            column.getChildren ().add (subDefinitionRow);

            popOver.show (addItemButton.getScene ().getWindow (), x, y);

        } else if (treeItem.getValue () instanceof SubEntry) {

            HBox subEntryListRow = new HBox ();
            subEntryListRow.setAlignment (Pos.CENTER_LEFT);
            subEntryListRow.setSpacing (10);

            Button subEntryListButton = new Button ("+");
            subEntryListButton.setOnAction (actionEvent -> {
                if (((SubEntry) treeItem.getValue ()).getSubEntryList () == null) {
                    SubEntryList subEntryList = new SubEntryList ();
                    ((SubEntry) treeItem.getValue ()).setSubEntryList (subEntryList);

                    int index = findIndexFor (SubEntryList.class, treeItem.getChildren ());

                    TreeItem<Object> subEntryListTreeItem = new TreeItem<> (subEntryList);
                    treeItem.getChildren ().add (index, subEntryListTreeItem);
                    treeView.getSelectionModel ().select (subEntryListTreeItem);

                    popOver.hide ();
                } else {
                    System.err.println ("The selected item already has a Sub-Entry List!");
                }
            });
            subEntryListRow.getChildren ().add (subEntryListButton);

            Label subEntryListLabel = new Label ("Sub-Entry List");
            subEntryListRow.getChildren ().add (subEntryListLabel);

            column.getChildren ().add (subEntryListRow);


            HBox noteGroupRow = new HBox ();
            noteGroupRow.setAlignment (Pos.CENTER_LEFT);
            noteGroupRow.setSpacing (10);

            Button noteGroupButton = new Button ("+");
            noteGroupButton.setOnAction (actionEvent -> {
                if (((SubEntry) treeItem.getValue ()).getNoteGroup () == null) {
                    NoteGroup noteGroup = new NoteGroup ();
                    ((SubEntry) treeItem.getValue ()).setNoteGroup (noteGroup);

                    TreeItem<Object> noteGroupTreeItem = new TreeItem<> (noteGroup);
                    treeItem.getChildren ().add (noteGroupTreeItem);
                    treeView.getSelectionModel ().select (noteGroupTreeItem);

                    popOver.hide ();
                } else {
                    System.err.println ("The selected item already has a Note Group!");
                }
            });
            noteGroupRow.getChildren ().add (noteGroupButton);

            Label noteGroupLabel = new Label ("Note");
            noteGroupRow.getChildren ().add (noteGroupLabel);

            column.getChildren ().add (noteGroupRow);

            popOver.show (addItemButton.getScene ().getWindow (), x, y);

        } else if (treeItem.getValue () instanceof SubEntryGroup) {

            HBox subEntryRow = new HBox ();
            subEntryRow.setAlignment (Pos.CENTER_LEFT);
            subEntryRow.setSpacing (10);

            Button subEntryButton = new Button ("+");
            subEntryButton.setOnAction (actionEvent -> {
                SubEntry subEntry = new SubEntry ();
                ((SubEntryGroup) treeItem.getValue ()).getSubEntries ().add (subEntry);

                TreeItem<Object> subEntryTreeItem = new TreeItem<> (subEntry);
                treeItem.getChildren ().add (subEntryTreeItem);
                treeView.getSelectionModel ().select (subEntryTreeItem);

                popOver.hide ();
            });
            subEntryRow.getChildren ().add (subEntryButton);

            Label subEntryLabel = new Label ("Sub-Entry");
            subEntryRow.getChildren ().add (subEntryLabel);

            column.getChildren ().add (subEntryRow);

            popOver.show (addItemButton.getScene ().getWindow (), x, y);

        } else if (treeItem.getValue () instanceof SubEntryList) {

            HBox subEntryListItemRow = new HBox ();
            subEntryListItemRow.setAlignment (Pos.CENTER_LEFT);
            subEntryListItemRow.setSpacing (10);

            Button subEntryListItemButton = new Button ("+");
            subEntryListItemButton.setOnAction (actionEvent -> {
                SubEntryListItem subEntryListItem = new SubEntryListItem ();
                ((SubEntryList) treeItem.getValue ()).getSubEntryListItems ().add (subEntryListItem);

                TreeItem<Object> subEntryListItemTreeItem = new TreeItem<> (subEntryListItem);
                treeItem.getChildren ().add (subEntryListItemTreeItem);
                treeView.getSelectionModel ().select (subEntryListItemTreeItem);

                popOver.hide ();
            });
            subEntryListItemRow.getChildren ().add (subEntryListItemButton);

            Label subEntryListItemLabel = new Label ("Sub-Entry List Item");
            subEntryListItemRow.getChildren ().add (subEntryListItemLabel);

            column.getChildren ().add (subEntryListItemRow);

            popOver.show (addItemButton.getScene ().getWindow (), x, y);
        }
    }

    private <T> int findIndexFor (Class<T> type, ObservableList<TreeItem<Object>> list) {

        if (type == null) {
            return -1; // This shouldn't happen
        } else if (type == ExampleGroup.class) {

            for (int i = 0; i < list.size (); i++)
                if (list.get (i).getValue () instanceof SubDefinitionGroup)
                    return i;
            return list.size ();

        } else if (type == HeadGroup.class) {

            for (int i = 0; i < list.size (); i++)
                if (list.get (i).getValue () instanceof EntryGroup)
                    return i;
            return list.size ();

        } else if (type == Index.class) {

            for (int i = 0; i < list.size (); i++)
                if (list.get (i).getValue () instanceof HeadGroup)
                    return i;
            for (int i = 0; i < list.size (); i++)
                if (list.get (i).getValue () instanceof EntryGroup)
                    return i;
            return list.size ();

        } else if (type == GrammarGroup.class) {

            for (int i = 0; i < list.size (); i++)
                if (list.get (i).getValue () instanceof DefinitionGroup)
                    return i;
            for (int i = 0; i < list.size (); i++)
                if (list.get (i).getValue () instanceof SubEntryGroup)
                    return i;
            return list.size ();

        } else if (type == DefinitionGroup.class) {

            for (int i = 0; i < list.size (); i++)
                if (list.get (i).getValue () instanceof SubEntryGroup)
                    return i;
            return list.size ();

        } else if (type == SubEntryList.class) {

            for (int i = 0; i < list.size (); i++)
                if (list.get (i).getValue () instanceof NoteGroup)
                    return i;
            return list.size ();

        } else {
            System.err.println ("Invalid Class!"); // This should NEVER happen
            return -1;
        }
    }

    public void newFile () {
        DictionaryWriter.getStage ().setTitle ("Dictionary Writer - New Dictionary");

        dictionary = new Dictionary ();

        TreeItem<Object> treeRoot = new TreeItem<> (dictionary);
        treeRoot.setExpanded (true);
        treeRoot.expandedProperty ().addListener ((observable, oldValue, newValue) -> {
            if (!newValue)
                treeRoot.setExpanded (true);
        });

        treeView.setRoot (treeRoot);
        treeView.getSelectionModel ().select (treeRoot);

        documentLoaded = true;

        newFile = true;
    }
}
