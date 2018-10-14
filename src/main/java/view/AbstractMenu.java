package view;

import controller.GameController;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * Created by RuYiMarone on 11/12/2016.
 */
public class AbstractMenu {
    public static final int PREFWIDTH = 300;
    private Button exploreButton = new Button("Explore");
    private Button endTurnButton = new Button("End Turn");
    private Text terrain = new Text();
    private Text unitStatus = new Text();
    private VBox menu = new VBox(10, terrain, unitStatus,
            exploreButton, endTurnButton);

    public AbstractMenu() {
        terrain.setFill(Color.RED);
        unitStatus.setFill(Color.RED);
        terrain.setId("gametext");
        unitStatus.setId("gametext");
        menu.setPrefWidth(PREFWIDTH);
        unitStatus.setWrappingWidth(320);
        exploreButton.setId("gamebutton");
        endTurnButton.setId("gamebutton");

        exploreButton.setOnMousePressed(e -> {
                GameController.getCivilization().explore();
                if (endTurn()) {
                    Alert newAlert = new Alert(Alert.AlertType.CONFIRMATION);
                    newAlert.setHeaderText("Congratulations");
                    newAlert.setTitle("You Won!");
                    newAlert.showAndWait();
                    System.exit(0);
                }
                SoundFX sound = new SoundFX("src/main/java/view"
                                            + "/sounds/explore.wav");
                sound.setTime(1500);
                sound.playSound();
            });

        endTurnButton.setOnAction(event -> {
                if (endTurn()) {
                    Alert newAlert = new Alert(Alert.AlertType.CONFIRMATION);
                    newAlert.setHeaderText("Congratulations");
                    newAlert.setTitle("You Won!");
                    newAlert.showAndWait();
                    System.exit(0);
                }
                SoundFX sound = new SoundFX("src/main/java/view"
                                            + "/sounds/endturn.mp3");
                sound.playSound();
            });
        menu.setPrefWidth(PREFWIDTH);
        updateItems();
    }
    /**
     * This method updates the items and return the vbox as
     * the menu
     */
    public VBox getRootNode() {
        updateItems();
        return menu;
    }
    /**
     * This method takes in a node and added the node as
     * a child of the vbox menu
     */
    public void addMenuItem(Node node) {
        menu.getChildren().add(node);
    }
    /**
     * ends the player's turn and check for winning condition
     */
    public boolean endTurn() {
        GameController.setLastClicked(null);
        GameController.tick();
        GameController.ai();
        GridFX.update();
        GameController.updateResourcesBar();
        if (GameController.getCivilization().getNumSettlements() <= 0) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Your last settlement has been destroyed!");
            alert.setTitle("Game Over");
            alert.showAndWait();
            System.exit(0);
        }
        return GameController.getCivilization()
                .getStrategy().conqueredTheWorld()
                || GameController.getCivilization()
                .getTechnology().hasTechnologyWin();
    }

    private void updateItems() {
        unitStatus.setText("");
        if (GameController.getLastClicked() != null) {
            terrain.setText(GameController.getLastClicked()
                    .getTile().getType().toString());
            if (!GameController.getLastClicked().getTile().isEmpty()) {
                unitStatus.setText(GameController.getLastClicked()
                        .getTile().getOccupant().getStatusString());
            }
        }
    }
}
