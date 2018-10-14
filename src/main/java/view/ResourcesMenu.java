package view;

import controller.GameController;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
//import model.Civilization;


public class ResourcesMenu {
    private int strategy;
    private int resources;
    private int settlements;
    private int money;
    private int food;
    private int happiness;
    private HBox bar;
    private Text one;
    private Text two;
    private Text three;
    private Text four;
    private Text five;
    private Text six;
    /**
    * creates a resource bar and display the current state of
    * your civilization's resouces
    */
    public ResourcesMenu() {
        //TODO
        bar = new HBox(10);
        bar.setPrefHeight(100);
        one = new Text("Strat Level: " + strategy);
        two = new Text("Resources: " + resources);
        three = new Text("Settlements: " + settlements);
        four = new Text("Money: " + money);
        five = new Text("Food: " + food);
        six = new Text("Happiness: " + happiness);
        one.setFont(new Font("Cambria", 20));
        two.setFont(new Font("Cambria", 20));
        three.setFont(new Font("Cambria", 20));
        four.setFont(new Font("Cambria", 20));
        five.setFont(new Font("Cambria", 20));
        six.setFont(new Font("Cambria", 20));
        one.setTranslateX(315);
        one.setTranslateY(60);
        two.setTranslateX(315);
        two.setTranslateY(60);
        three.setTranslateX(315);
        three.setTranslateY(60);
        four.setTranslateX(315);
        four.setTranslateY(60);
        five.setTranslateX(315);
        five.setTranslateY(60);
        six.setTranslateX(315);
        six.setTranslateY(60);
        one.setId("gametext");
        two.setId("gametext");
        three.setId("gametext");
        four.setId("gametext");
        five.setId("gametext");
        six.setId("gametext");
        one.setFill(Color.RED);
        two.setFill(Color.RED);
        three.setFill(Color.RED);
        four.setFill(Color.RED);
        five.setFill(Color.RED);
        six.setFill(Color.RED);
        bar.getChildren().addAll(one, two, three, four, five, six);
    }
    /**
    * should update all the resouce values to the current
    * state of your resource values
    */
    public void update() {
        //TODO
        strategy = GameController.getCivilization()
            .getStrategy().getStrategyLevel();
        resources = GameController.getCivilization().getResources();
        settlements = GameController.getCivilization().getNumSettlements();
        money = GameController.getCivilization().getTreasury().getCoins();
        food = GameController.getCivilization().getFood();
        happiness = GameController.getCivilization().getHappiness();
        one.setText("Strat Level: " + strategy);
        two.setText("Resources: " + resources);
        three.setText("Settlements: " + settlements);
        four.setText("Money: " + money);
        five.setText("Food: " + food);
        six.setText("Happiness: " + happiness);
    }
    /**
    * updates the resource bar and returns the resource bar
    * @return a hbox representation of the resource bar
    */
    public HBox getRootNode() {
        //TODO
        update();
        return bar;
    }
}
