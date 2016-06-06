package com.nlbuescher.dictionarywriter;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * DictionaryWriter Initialization class
 * Copyright (C) 2016  Nicola Buescher
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
public class DictionaryWriter extends Application {

    private static Stage stage;

    static Stage getStage () {
        return stage;
    }

    @Override
    public void start (Stage primaryStage) throws Exception {
        stage = primaryStage;

        BorderPane root = FXMLLoader.load (DictionaryWriter.class.getResource ("DictionaryWriter.fxml"));

        stage.setMinWidth (600);
        stage.setMinHeight (400);
        stage.setTitle ("Dictionary Writer");
        stage.setScene (new Scene (root, 800, 600));
        stage.show();
    }


    public static void main (String[] args) {
        launch(args);
    }
}
