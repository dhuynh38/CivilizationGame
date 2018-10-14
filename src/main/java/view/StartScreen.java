package view;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


/**
 * Created by Tian-Yo Yang on 11/11/2016.
 * This class represents the Start Screen for the Civ applicatios. This is the
 * layout that should be displayed upon running the Civ application.
 *
 * This class should have and display
 * 1. a background
 * 2. a list of Civilizations
 * 3. a Start button
 */
public class StartScreen extends StackPane {
    private ListView<CivEnum> list;
    private Button start;
    private ListView<String> list2;

    /**
    * constuctor of the start screen. Should set the background
    * image and display a list of civilizations and a start button
    */
    public StartScreen() {
        //TODO
        ImageView background = new
            ImageView(new
                      Image("File:./src/main/java/view/civ_background.png"));
        Label label = new Label("Select a Civilization to Begin");
        label.setFont(new Font("Cambria", 35));
        label.setTextFill(Color.RED);
        label.setTranslateY(220);

        Label label2 = new Label("Select a Map Size");
        label2.setFont(new Font("Cambria", 35));
        label2.setTextFill(Color.RED);
        label2.setTranslateY(80);

        start = new Button("START");
        start.setPrefWidth(100);
        start.setPrefHeight(50);
        start.setTranslateY(375);
        start.setTranslateX(100);
        start.setId("gamebutton");

        list = new ListView<CivEnum>();
        ObservableList<CivEnum> civs = FXCollections
            .observableArrayList(CivEnum.ANCIENT_EGYPT,
                                CivEnum.QIN_DYNASTY,
                                CivEnum.ROMAN_EMPIRE);
        list.setItems(civs);
        list.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        list.setMaxSize(450, 100);
        list.setTranslateY(290);
        list.setOnMousePressed(c -> {
                SoundFX sound2 = new SoundFX("src/main/java/view/"
                                             + "sounds/mouse.wav");
                sound2.playSound();
            });

        list2 = new ListView<String>();
        ObservableList<String> size = FXCollections
            .observableArrayList("10 x 10", "10 x 15", "10 x 20");
        list2.setItems(size);
        list2.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        list2.setMaxSize(450, 100);
        list2.setTranslateY(150);
        list2.setOnMousePressed(c -> {
                SoundFX sound2 = new SoundFX("src/main/java/view/"
                                             + "sounds/mouse.wav");
                sound2.playSound();
            });

        Button exit = new Button("Exit");
        exit.setId("gamebutton");
        exit.setPrefWidth(100);
        exit.setPrefHeight(50);
        exit.setTranslateY(375);
        exit.setTranslateX(-100);
        exit.setOnMouseClicked(e -> {
                Platform.exit();
            });

        getChildren().add(background);
        getChildren().add(exit);
        getChildren().add(label);
        getChildren().add(label2);
        getChildren().add(getCivList());
        getChildren().add(getSizeList());
        getChildren().add(getStartButton());

    }
    /**
    * gets the start button
    * @return the start button
    */
    public Button getStartButton() {
        //TODO
        return start;
    }
    /**
    * return a ListView of CivEnums representing the list of
    * available civilizations to choose from
    * @return listview of CivEnum
    */
    public ListView<CivEnum> getCivList() {
        //TODO
        return list;
    }

    public ListView<String> getSizeList() {
        //TODO
        return list2;
    }

}
