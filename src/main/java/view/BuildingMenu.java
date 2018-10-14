package view;

import controller.GameController;
import model.Building;
import model.Settlement;

import javafx.scene.control.Button;
import javafx.scene.control.Alert;
/**
 * This class should represents the bulding menu
 */
public class BuildingMenu extends AbstractMenu {
    /**
    * there should be an invest and demolish button for this menu
    * as well as functions associated with the buttons
    */
    public BuildingMenu() {
        //TODO
        super();
        Button invest = new Button("Invest");
        Button demolish = new Button("Demolish");
        invest.setId("gamebutton");
        demolish.setId("gamebutton");
        invest.setOnAction(x -> {
                if (GameController.getLastClicked() != null) {
                    if (GameController.getCivilization()
                        .getTreasury().getCoins() >= 25) {
                        GameController.getCivilization()
                            .getTreasury().spend(25);
                        ((Building) GameController.getLastClicked()
                         .getTile().getOccupant()).invest();
                        GameController.updateResourcesBar();
                        SoundFX sound = new SoundFX("src/main/java/view"
                                                    + "/sounds/invest.wav");
                        sound.playSound();
                    } else {
                        Alert newAlert = new Alert(Alert.AlertType.ERROR);
                        newAlert.setHeaderText("You must have 25 gold!");
                        newAlert.setTitle("Unable to Invest");
                        SoundFX sound = new SoundFX("src/main/java/view/"
                                                    + "sounds/error.wav");
                        sound.playSound();
                        newAlert.showAndWait();
                    }
                }
            });
        demolish.setOnAction(x -> {
                SoundFX begin = new SoundFX("src/main/java/"
                                                + "view/sounds"
                                                + "/mouse.wav");
                begin.playSound();
                if (GameController.getLastClicked() != null) {
                    if (!(GameController.getLastClicked()
                          .getTile().getOccupant() instanceof Settlement)) {
                        ((Building) GameController.getLastClicked()
                         .getTile().getOccupant()).demolish();
                        GameController.getLastClicked()
                            .getTile().setOccupant(null);
                        SoundFX sound = new SoundFX("src/main/java/view/"
                                                    + "sounds/explosion.wav");
                        sound.playSound();
                    } else if (GameController.getCivilization()
                               .getNumSettlements() > 1
                               && GameController.getLastClicked()

                               .getTile().getOccupant() instanceof Settlement) {
                        ((Settlement) GameController.getLastClicked()
                         .getTile().getOccupant()).demolish();
                        GameController.getLastClicked()
                            .getTile().setOccupant(null);
                        SoundFX sound = new SoundFX("src/main/java/view/"
                                                    + "sounds/explosion.wav");
                        sound.playSound();
                    } else if (GameController.getCivilization()
                               .getNumSettlements() <= 1) {
                        Alert newAlert = new Alert(Alert.AlertType.ERROR);
                        newAlert.setHeaderText("You must have more "
                                               + "than one settlement!");
                        newAlert.setTitle("Unable To Demolish");
                        SoundFX sound = new SoundFX("src/main/java/view"
                                                    + "/sounds/error.wav");
                        sound.playSound();
                        newAlert.showAndWait();
                    }

                    GridFX.getInstance().update();
                }
            });
        addMenuItem(invest);
        addMenuItem(demolish);
    }
}
