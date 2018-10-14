package runner;

import controller.GameController;
import view.StartScreen;
import view.CivEnum;
import view.GameScreen;
import view.SoundFX;
import view.GridFX;
import model.Map;
import model.QinDynasty;
import model.RomanEmpire;
import model.Egypt;
import model.Bandit;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.control.ScrollPane;

/**
 * Created by Tian-Yo Yang on 11/11/2016.
 */
public class CivilizationGame extends Application {

    /**
     * this method is called upon running/launching the application
     * this method should display a scene on the stage
     */
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Civilization V");
        primaryStage.setScene(startGame());
        primaryStage.getScene().getStylesheets()
            .add(getClass().getResource("gui.css").toString());
        SoundFX.setBackground();
        Rectangle2D userScreenSize = Screen.getPrimary().getVisualBounds();
        primaryStage.setX(userScreenSize.getMinX());
        primaryStage.setY(userScreenSize.getMinY());
        primaryStage.setWidth(userScreenSize.getWidth());
        primaryStage.setHeight(userScreenSize.getHeight());
        primaryStage.show();
    }
    /**
     * This is the main method that launches the javafx application
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
    * This method is responsible for setting the scene to the corresponding
    * layout.
    * and returning the scene.
    * @return Scene
    */
    public Scene startGame() {
        GameController.setCivilization(new Egypt());
        StartScreen startScreen = new StartScreen();
        Scene scene = new Scene(startScreen, 900, 800);
        Button start = startScreen.getStartButton();
        ListView<CivEnum> list = startScreen.getCivList();
        ListView<String> size = startScreen.getSizeList();
        ScrollPane scroll = new ScrollPane();
        scroll.setContent(startScreen);
        scroll.setFitToHeight(true);
        scroll.setFitToWidth(true);
        scene.setRoot(scroll);
        start.setOnAction(x -> {
                SoundFX sound = new SoundFX("src/main/java/view/"
                                            + "sounds/mouse.wav");
                sound.playSound();
                TextInputDialog ask = new TextInputDialog();
                ask.setTitle("A New Settlement");
                ask.setHeaderText("You have built a Settlement!");
                ask.setContentText("Enter the Name of your first town:");
                Button close = (Button) ask.getDialogPane()
                    .lookupButton(ButtonType.OK);
                Button cancel = (Button) ask.getDialogPane()
                    .lookupButton(ButtonType.CANCEL);
                cancel.setOnAction(y -> {
                        SoundFX sound2 = new SoundFX("src/main/java/view/"
                                                     + "sounds/mouse.wav");
                        sound2.playSound();
                    });
                close.addEventFilter(ActionEvent.ACTION, e -> {
                        if (ask.getEditor().getText().trim().isEmpty()) {
                            e.consume();
                        }
                    });
                close.setOnAction(y -> {
                        SoundFX sound2 = new SoundFX("src/main/java/view/"
                                                     + "sounds/mouse.wav");
                        sound2.playSound();
                        String civName = list.getSelectionModel()
                            .getSelectedItem().toString();
                        if (civName.equals("Ancient Egypt")) {
                            Egypt egypt = new Egypt();
                            GameController.setCivilization(egypt);
                        } else if (civName.equals("Qin Dynasty")) {
                            QinDynasty qin = new QinDynasty();
                            GameController.setCivilization(qin);
                        } else {
                            RomanEmpire roman = new RomanEmpire();
                            GameController.setCivilization(roman);
                        }

                        Bandit evil = new Bandit();
                        GameController.setBandits(evil);
                        String name = ask.getEditor().getText();

                        String gridSize = size.getSelectionModel()
                            .getSelectedItem();
                        int i = 0;

                        if (gridSize.equals("10 x 10")) {
                            GameScreen.setGrid(1);
                            i = 1;
                        } else if (gridSize.equals("10 x 15")) {
                            GameScreen.setGrid(2);
                            i = 2;
                        } else if (gridSize.equals("10 x 20")) {
                            GameScreen.setGrid(3);
                            i = 3;
                        }
                        GameScreen gamescreen = new GameScreen();
                        Map.addEnemies(evil, i);
                        Map.putSettlement(name,
                                          GameController.getCivilization(),
                                          0,
                                          GridFX.getMap().getColumns() - 1);

                        scroll.setContent(gamescreen);
                        scene.setRoot(scroll);
                        gamescreen.update();
                    });
                ask.showAndWait();

            });
        start.addEventFilter(ActionEvent.ACTION, e -> {
                if (list.getSelectionModel().getSelectedItem() == null) {
                    e.consume();
                } else if (size.getSelectionModel().getSelectedItem() == null) {
                    e.consume();
                }
            });

        return scene;
    }




}
