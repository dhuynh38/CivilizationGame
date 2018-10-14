package view;

import controller.GameController;
import model.Convertable;
import model.TerrainTile;
import model.Unit;
import model.MapObject;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
/**
 * Created by RuYiMarone on 11/11/2016.
 */
public class WorkerMenu extends AbstractMenu {
    private static Text moving = new Text();
    /**
    * There should be a convert and move button
    * as well as the functions associated with those
    * buttons
    */
    public WorkerMenu() {
        //TODO
        super();
        Button convert = new Button("Convert");
        Button move = new Button("Move");
        convert.setId("gamebutton");
        move.setId("gamebutton");
        convert.setOnMousePressed(x -> {
                if (GameController.getLastClicked() != null) {
                    TerrainTile click =
                        GameController.getLastClicked().getTile();
                    if (click.getOccupant()
                        .isWorker()
                        && ((Convertable) click.getOccupant())
                        .canConvert(click.getType())) {
                        click.setOccupant(((Convertable) click
                                       .getOccupant())
                                          .convert());
                        SoundFX sound = new SoundFX("src/main/java/"
                                                    + "view/sounds/"
                                                    + "convert.mp3");
                        sound.playSound();
                    } else {
                        Alert newAlert = new Alert(Alert.AlertType.ERROR);
                        newAlert.setHeaderText("Your unit cannot "
                                           + "convert this tile!");
                        newAlert
                            .setTitle("Unable to Convert");
                        SoundFX sound = new SoundFX("src/main/java/"
                                                    + "view/sounds/"
                                                    + "error.wav");
                        sound.playSound();
                        newAlert.showAndWait();
                    }

                    GameController.updateResourcesBar();
                    GridFX.getInstance().update();
                }
            });
        move.setOnMousePressed(a -> {
                TerrainTileFX beforeMove = GameController.getLastClicked();
                if (GameController.getLastClicked() != null) {
                    MapObject beforeUnit = (MapObject) GameController
                        .getLastClicked().getTile().getOccupant();
                    moving.setText("true");
                    GameController.moving();
                    SoundFX sound = new SoundFX("src/main/java/"
                                                + "view/sounds"
                                                + "/error.wav");
                    GridFX.getInstance().addEventHandler(
                                                         MouseEvent
                                                         .MOUSE_PRESSED,
                                                         y -> {
                            if (moving.getText().equals("true")
                                && (GameController.getLastClicked().getTile()
                                .getOccupant() instanceof Unit
                                    || GameController.getLastClicked().getTile()
                                    .isEmpty())) {
                                TerrainTileFX afterMove =
                                    GameController.getLastClicked();
                                Unit afterUnit = (Unit) GameController
                                    .getLastClicked().getTile().getOccupant();
                                Unit newUnit = (Unit) beforeUnit;
                                if (afterMove.getTile().isEmpty()
                                    && !newUnit.canMove(GameController
                                                          .getLastClicked()
                                                          .getTile()
                                                        .getType().getCost())) {
                                    Alert newAlert =
                                        new Alert(Alert.AlertType.ERROR);
                                    newAlert
                                        .setHeaderText("Your "
                                                   + "unit does not have "
                                                   + "enough endurance!");
                                    newAlert.setTitle("Unable To Move");
                                    sound.playSound();
                                    newAlert.showAndWait();
                                } else if (afterMove.getTile().isEmpty()
                                           && !GridFX
                                           .adjacent(beforeMove, afterMove)) {
                                    Alert newAlert =
                                        new Alert(Alert.AlertType.ERROR);
                                    newAlert.setHeaderText("Location is too"
                                                           + " far away!");
                                    newAlert.setTitle("Unable to Move");
                                    sound.playSound();
                                    newAlert.showAndWait();
                                } else if (!afterMove.getTile().isEmpty()
                                           && !beforeMove.getTile().isEmpty()
                                           && beforeUnit == afterUnit) {
                                    Alert newAlert =
                                        new Alert(Alert.AlertType.ERROR);
                                    newAlert.setHeaderText("You cannot "
                                                           + "move to your cur"
                                                           + "rent location!");
                                    newAlert.setTitle("Unable to Move");
                                    sound.playSound();
                                    newAlert.showAndWait();
                                } else if (!afterMove.getTile().isEmpty()
                                           && beforeUnit != afterUnit) {
                                    Alert newAlert =
                                        new Alert(Alert.AlertType.ERROR);
                                    newAlert.
                                        setHeaderText("A unit is "
                                                      + "already there!");
                                    newAlert.setTitle("Unable to Move");
                                    sound.playSound();
                                    newAlert.showAndWait();
                                } else {
                                    SoundFX sound2 =
                                        new SoundFX("src/main/java"
                                                    + "/view/sound"
                                                    + "s/move.wav");
                                    sound2.playSound();
                                }

                            } else if (moving.getText().equals("true")) {
                                TerrainTileFX afterMove =
                                    GameController.getLastClicked();
                                MapObject afterUnit = (MapObject) GameController
                                    .getLastClicked().getTile().getOccupant();

                                if (!afterMove.getTile().isEmpty()
                                    && beforeUnit != afterUnit) {
                                    Alert newAlert =
                                        new Alert(Alert.AlertType.ERROR);
                                    newAlert.
                                        setHeaderText("A building is "
                                                      + "already there!");
                                    newAlert.setTitle("Unable to Move");
                                    sound.playSound();
                                    newAlert.showAndWait();
                                }
                            }
                            moving.setText("false");
                        });
                }
            });
        addMenuItem(convert);
        addMenuItem(move);
    }

    public static String getText() {
        return moving.getText();
    }

}
