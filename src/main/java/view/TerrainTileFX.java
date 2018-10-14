package view;


import controller.GameController;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.TerrainTile;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;
/**
 * Created by RuYiMarone on 11/11/2016.
 */
public class TerrainTileFX extends StackPane {
    private Rectangle overlay;
    private Rectangle under;
    private ImageView background;
    private TerrainTile tile;
    private ImageView icon = new ImageView("File:./bologna");;
    private TranslateTransition transition;
    private TranslateTransition transition2;
    private int beforeRow;
    private int beforeCol;
    private int afterRow;
    private int afterCol;

    /**
     * Constructor for TerrainTileFX.
     * Creates a Rectangle for the highlighting and overlay
     * Creates ImageViews for the background terrain and icon
     * Transitions states when a tile is clicked
     * @param tile
     */
    public TerrainTileFX(TerrainTile tile) {
        this.tile = tile;
        overlay = new Rectangle(70, 70, Color.rgb(0, 0, 0, 0.0));
        overlay.setStroke(Color.BLACK);
        under = new Rectangle(70, 70, Color.rgb(0, 0, 0, 0.0));
        under.setStroke(Color.BLACK);
        this.background = new ImageView(tile.getImage());
        updateTileView();
        this.getChildren().addAll(background, under, overlay, icon);
        this.transition = new TranslateTransition(Duration.millis(300), icon);
        this.transition2
            = new TranslateTransition(Duration.millis(300), overlay);
        this.setOnMousePressed(event -> {
                if ((MilitaryMenu.getText().equals("true")
                    || WorkerMenu.getText().equals("true"))
                    && GameController.getLastClicked() != null
                    && !GameController.getLastClicked().getTile().isEmpty()) {
                    beforeRow = GameController
                        .getLastClicked().getTile().getRow();
                    beforeCol = GameController
                        .getLastClicked().getTile().getCol();
                    GameController.setLastClicked(this);
                    afterRow = GameController
                        .getLastClicked().getTile().getRow();
                    afterCol = GameController
                        .getLastClicked().getTile().getCol();
                    int x = afterCol - beforeCol;
                    int y = afterRow - beforeRow;
                    if (Math.abs(x) < 2 && Math.abs(y) < 2) {
                        this.transition.setFromX(-1 * x * 70);
                        this.transition.setFromY(-1 * y * 70);
                        this.transition.setToX(0);
                        this.transition.setToY(0);
                        this.transition2.setFromX(-1 * x * 70);
                        this.transition2.setFromY(-1 * y * 70);
                        this.transition2.setToX(0);
                        this.transition2.setToY(0);
                        this.transition.play();
                        this.transition2.play();
                    }
                } else {
                    GameController.setLastClicked(this);
                }
            });
    }
    /**
     * gets the TerrainTile of this TerrainTileFX
     * @return TerrainTile
     */
    public TerrainTile getTile() {
        return tile;
    }
    /**
     * this method updates the view of this TerrainTileFX.
     * It should check if the TerrainTile is empty. If it is empty,
     * set the overlay to be transparent. If it is not empty, fill
     * the overlay with the color of the occupant on the terrain tile
     * If the TerrainTileFX contains an icon, remove it. If the tile is
     * not empty, get the image of the occupant of the tile and add the
     *image of the occupant to the tile.
     */
    public void updateTileView() {
        //TODO
        if (tile.isEmpty()) {
            overlay.setFill(Color.TRANSPARENT);
            icon.setImage(null);
            overlay.setStroke(Color.BLACK);
            if (GameController.getLastClicked() == this) {
                overlay.setStroke(Color.RED);
            }
        } else {
            overlay.setFill(tile.getOccupant().getColor());
            icon.setImage(tile.getOccupant().getImage());
            overlay.setStroke(Color.BLACK);
            if (GameController.getLastClicked() == this) {
                overlay.setStroke(Color.RED);
            }
        }
    }
}
