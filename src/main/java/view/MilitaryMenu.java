package view;

import controller.GameController;
import model.Unit;
import model.MapObject;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.scene.input.MouseEvent;

/**
 * Created by William on 11/11/2016.
 */

public class MilitaryMenu extends AbstractMenu {
    private static Text moving = new Text();
    private static Button attack = new Button("Attack");
    private static Button move = new Button("Move");
    private static Button fake = new Button("Fake");
    /**
    * Implement the buttons and actions associated with
    * the buttons for the military menu
    */
    public MilitaryMenu() {
        super();
        attack.setId("gamebutton");
        move.setId("gamebutton");
        setAttack();
        setMove();
        addMenuItem(attack);
        addMenuItem(move);
    }

    public static String getText() {
        return moving.getText();
    }

    private void setMove() {
        move.setOnMousePressed(a -> {
                SoundFX begin = new SoundFX("src/main/java/"
                                                + "view/sounds"
                                                + "/mouse.wav");
                begin.playSound();
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
    }

    private void setAttack() {
        attack.setOnMousePressed(x -> {
                SoundFX begin = new SoundFX("src/main/java/"
                                                + "view/sounds"
                                                + "/mouse.wav");
                begin.playSound();
                TerrainTileFX beforeAttack = GameController.getLastClicked();
                if (GameController.getLastClicked() != null) {
                    Unit beforeU = (Unit) GameController
                        .getLastClicked().getTile().getOccupant();
                    boolean attackable = beforeU.getCanAttack();
                    GameController.attacking();
                    Text attacking = new Text("true");
                    GridFX.getInstance().addEventHandler(
                                                         MouseEvent
                                                         .MOUSE_PRESSED,
                                                         y -> {
                            if (attacking.getText().equals("true")) {
                                TerrainTileFX afterAttack =
                                    GameController.getLastClicked();
                                boolean attackable2 = beforeU.getCanAttack();
                                SoundFX sound = new SoundFX("src/main/java/"
                                                                + "view/sounds"
                                                                + "/error.wav");
                                if (!attackable) {
                                    Alert newAlert =
                                        new Alert(Alert.AlertType.ERROR);
                                    newAlert
                                        .setHeaderText("You cannot "
                                                       + "attack right now!");
                                    newAlert.setTitle("Unable To Attack");
                                    sound.playSound();
                                    newAlert.showAndWait();
                                } else if (!GridFX.adjacent(beforeAttack
                                                            , afterAttack)) {
                                    Alert newAlert =
                                        new Alert(Alert.AlertType.ERROR);
                                    newAlert
                                        .setHeaderText("Target too far away!");
                                    newAlert.setTitle("Unable To Attack");
                                    sound.playSound();
                                    newAlert.showAndWait();
                                } else if (!afterAttack.getTile().isEmpty()
                                           && afterAttack.getTile()
                                           .getOccupant().isFriendly()) {
                                    Alert newAlert =
                                        new Alert(Alert.AlertType.ERROR);
                                    newAlert.setHeaderText("You cannot "
                                                           + "attack friendly "
                                                           + "unit!");
                                    newAlert.setTitle("Unable To Attack");
                                    sound.playSound();
                                    newAlert.showAndWait();
                                } else if (attackable2
                                       && afterAttack.getTile().isEmpty()) {
                                    Alert newAlert =
                                        new Alert(Alert.AlertType.ERROR);
                                    newAlert.setHeaderText("No target exist!");
                                    newAlert.setTitle("Unable To Attack");
                                    sound.playSound();
                                    newAlert.showAndWait();
                                } else {
                                    SoundFX sound2 =
                                        new SoundFX("src/main/java"
                                                    + "/view/sound"
                                                    + "s/attack.wav");
                                    sound2.playSound();
                                }
                                attacking.setText("false");
                            }
                        });
                }
            });
    }


}
