package view;

import controller.GameController;
import model.Civilization;
import model.Unit;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

/**
 * Created by RuYiMarone on 11/11/2016.
 */
public class RecruitMenu extends AbstractMenu {
    /**
    * recuit menu should have a list of worker/units
    * to choose from. There should also be a select button
    * and the function of the button should be implemented
    *here
    */
    public RecruitMenu() {
        //TODO
        super();
        ListView<String> list = new ListView<String>();
        ObservableList<String> units = FXCollections
            .observableArrayList("Melee Unit", "Ranged Unit",
                                 "Hybrid Unit", "Siege Unit",
                                 "Settlers", "Farmers",
                                 "Coal Miners", "Anglers", "Master Builders");
        list.setOnMousePressed(c -> {
                SoundFX sound2 = new SoundFX("src/main/java/view"
                                             + "/sounds/mouse.wav");
                sound2.playSound();
            });
        list.setItems(units);
        list.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        Button select = new Button("Select");
        select.setId("gamebutton");
        select.setOnMousePressed(x -> {
                String unitType = list.getSelectionModel().getSelectedItem();
                Civilization civ = GameController.getCivilization();
                Unit newUnit = null;
                Text name = new Text("null");
                if (unitType == null) {
                    Alert newAlert = new Alert(Alert.AlertType.ERROR);
                    newAlert.setHeaderText("You must select a unit!");
                    newAlert.setTitle("Unable to Recruit");
                    SoundFX sound = new SoundFX("src/main/java/view"
                                                + "/sounds/error.wav");
                    sound.playSound();
                    newAlert.showAndWait();
                } else if (unitType.equals("Melee Unit")) {
                    newUnit = civ.getMeleeUnit();
                } else if (unitType.equals("Ranged Unit")) {
                    newUnit = civ.getRangedUnit();
                } else if (unitType.equals("Hybrid Unit")) {
                    newUnit = civ.getHybridUnit();
                } else if (unitType.equals("Siege Unit")) {
                    newUnit = civ.getSiegeUnit();
                } else if (unitType.equals("Settlers")) {
                    TextInputDialog ask = new TextInputDialog();
                    ask.setTitle("New Settler Unit");
                    ask.setHeaderText("You have recruited a new Settler Unit!");
                    ask.setContentText("Enter the Name "
                                       + "of your new Settler Unit:");
                    Button ok = (Button) ask.getDialogPane()
                        .lookupButton(ButtonType.OK);
                    Button cancel = (Button) ask.getDialogPane()
                        .lookupButton(ButtonType.CANCEL);
                    ok.addEventFilter(ActionEvent.ACTION, e -> {
                            if (ask.getEditor().getText().trim().isEmpty()) {
                                e.consume();
                            }
                        });
                    ok.setOnAction(a -> {
                            SoundFX sound = new SoundFX("src/main/java/view"
                                                        + "/sounds/mouse.wav");
                            sound.playSound();
                            name.setText(ask.getEditor().getText());
                        });
                    cancel.setOnAction(a -> {
                            SoundFX sound = new SoundFX("src/main/java/view"
                                                        + "/sounds/mouse.wav");
                            sound.playSound();
                        });
                    ask.showAndWait();
                    if (!name.getText().equals("null")) {
                        newUnit = civ.getSettlerUnit(name.getText());
                    }
                } else if (unitType.equals("Farmers")) {
                    newUnit = civ.getFarmerUnit();
                } else if (unitType.equals("Coal Miners")) {
                    newUnit = civ.getCoalMinerUnit();
                } else if (unitType.equals("Anglers")) {
                    newUnit = civ.getAnglerUnit();
                } else if (unitType.equals("Master Builders")) {
                    newUnit = civ.getMasterBuilderUnit();
                }

                if (GameController.getLastClicked() == null) {
                    Alert newAlert = new Alert(Alert.AlertType.ERROR);
                    newAlert.setHeaderText("Pick A Location First");
                    newAlert.setTitle("Unable to Recruit");
                    SoundFX sound = new SoundFX("src/main/java/view"
                                                + "/sounds/error.wav");
                    sound.playSound();
                    newAlert.showAndWait();
                } else if ((newUnit != null) && newUnit.isAffordable()) {
                    GameController.getLastClicked().getTile()
                        .setOccupant(newUnit);
                    ((Unit) GameController.getLastClicked().getTile()
                        .getOccupant()).applyInitialCosts();
                    SoundFX sound = new SoundFX("src/main/java/view"
                                                + "/sounds/recruit.wav");
                    sound.setTime(1000);
                    sound.playSound();
                } else if ((newUnit != null) && !newUnit.isAffordable()) {
                    Alert newAlert = new Alert(Alert.AlertType.ERROR);
                    newAlert.setHeaderText("You do not have enough resources!");
                    newAlert.setTitle("Unable to Recruit");
                    SoundFX sound = new SoundFX("src/main/java/view"
                                                + "/sounds/error.wav");
                    sound.playSound();
                    newAlert.showAndWait();
                } else if ((unitType != null)
                           && name.getText().equals("null")) {
                    Alert newAlert = new Alert(Alert.AlertType.ERROR);
                    newAlert.setHeaderText("You cancelled the action!");
                    newAlert.setTitle("Unable to Recruit");
                    SoundFX sound = new SoundFX("src/main/java/view"
                                                + "/sounds/error.wav");
                    sound.playSound();
                    newAlert.showAndWait();
                }

                GridFX.getInstance().update();
                GameController.updateResourcesBar();
            });
        select.addEventFilter(MouseEvent.MOUSE_PRESSED, a -> {
                if (GameController.getLastClicked() != null) {
                    if (!GameController.getLastClicked().getTile().isEmpty()) {
                        a.consume();
                    }
                }
            });

        addMenuItem(list);
        addMenuItem(select);
    }
}
