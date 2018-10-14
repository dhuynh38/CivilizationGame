package view;

import javafx.application.Platform;
import controller.GameController;
import javafx.scene.layout.BorderPane;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.control.Button;

/**
 * This class represents the GameScreen class
 */
public class GameScreen extends BorderPane {
    private static ResourcesMenu resMenu;
    private static GridFX grid;
    private static AbstractMenu actMenu = new StatusMenu();
    private static int gridSize = 1;
    /**
     * Creates a new view into the game. this layout should include
     * the rescource bar, grid map, and action menus
     *
     */
    public GameScreen() {
        //TODO
        Button exit = new Button("Exit");
        exit.setId("gamebutton");
        exit.setPrefWidth(100);
        exit.setPrefHeight(50);
        exit.setOnMouseClicked(e -> {
                Platform.exit();
            });

        ImageView background = new
            ImageView(new
                      Image("File:./src/main/java/view/gamescreen.jpg"));
        getChildren().add(background);

        if (gridSize == 1) {
            grid = GridFX.getInstance();
        } else if (gridSize == 2) {
            grid = GridFX.getInstance(10, 15);
        } else if (gridSize == 3) {
            grid = GridFX.getInstance(10, 20);
        }
        resMenu = new ResourcesMenu();
        //ScrollPane scroll = new ScrollPane(grid);
        //scroll.setId("scroll");

        setCenter(grid);
        setTop(resMenu.getRootNode());
        setLeft(actMenu.getRootNode());
        setBottom(exit);

        this.setOnMousePressed(x -> {
                setLeft(actMenu.getRootNode());
                update();
            });

    }

    /**
     * This method should update the gridfx and the resouce bar
     */
    public void update() {
      //TODO
        resMenu.update();
        grid.update();
    }
    /**
    * this method should return the resource menu
    * @return reosuce menu
    */
    public static ResourcesMenu getResources() {
      //TODO
        return resMenu;
    }


    /**
     * This method switches menus based on passed in game state.
     * Game.java calls this to selectively control which menus are displayed
     * @param state
     */
    public static void switchMenu(GameController.GameState state) {
       //TODO
        if (state == GameController.GameState.NEUTRAL) {
            actMenu = new StatusMenu();
        } else if (state == GameController.GameState.WORKER) {
            actMenu = new WorkerMenu();
        } else if (state == GameController.GameState.RECRUITING) {
            actMenu = new RecruitMenu();
        } else if (state == GameController.GameState.BUILDING) {
            actMenu = new BuildingMenu();
        } else if (state == GameController.GameState.MILITARY) {
            actMenu = new MilitaryMenu();
        }
    }

    public static void setGrid(int x) {
        gridSize = x;
    }

}
