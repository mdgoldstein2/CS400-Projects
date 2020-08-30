// Title: Main
// Files: JavaFX, Application
// Course: CS400 Spring 2019
//
// Author: Michael Goldstein
// Email: mdgoldstein2@wisc.edu
// Lecturer's Name: Deb Deppeler
// Due Date: 4/12/19

package application;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 * Class which creates a basic UI using JavaFX
 * 
 * @author Michael Goldstein
 */
public class Main extends Application {
  Scene scene;
  BorderPane root1;
  BorderPane root2;

  /**
   * Method called automatically upon launch by JavaFX. Sets the UI
   * 
   * @param primaryStage JavaFX stage layout manager(s) will be added to
   */
  @Override
  public void start(Stage primaryStage) {
    try {
      // sets up a border pane layout manager
      root1 = new BorderPane(); // border pane layout manager elements will be added to
      addLabel(root1); // adds label
      addComboBox(root1); // adds combo box
      // addImageView(root1); // adds image
      addButton(root1); // adds button
      addTextArea(root1); // adds textArea

      root2 = new BorderPane();
      addTextArea(root2); // adds textArea
      addButton(root2); // adds button
      addRadioButton(root2);

      // creates scene1
      scene = new Scene(root1, 450, 450);
      scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

      // sets primary stage's scene, displaying the created UI
      primaryStage.setScene(scene);
      primaryStage.setTitle("Michael's First JavaFX program");
      primaryStage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Adds a label to a BorderPane
   * 
   * @param pane the BorderPane the label will be added to
   */
  public void addLabel(BorderPane pane) {
    // adds label with text "CS400 MyFirstJavaFX" to top pane
    Label infoLabel = new Label("CS400 MyFirstJavaFX");
    pane.setTop(infoLabel);
  }

  /**
   * Adds a combobox to a BorderPane
   * 
   * @param pane the BorderPane the combobox will be added to
   */
  public void addComboBox(BorderPane pane) {
    // creates combobox with 3 options
    ObservableList<String> optionsList = // list of options
        FXCollections.observableArrayList("Image 1", "Image 2", "Image 3");
    final ComboBox<String> optionsBox = new ComboBox<String>(optionsList);

    // adds combobox on left
    pane.setLeft(optionsBox);
  }

  /**
   * Adds an image to a BorderPane
   * 
   * @param pane the BorderPane the image will be added to
   */
  public void addImageView(BorderPane pane) {
    // creates image and ImageView
    Image me = new Image("me.png");
    ImageView selfie = new ImageView(me);

    // adds image in center
    pane.setCenter(selfie);
  }

  /**
   * Adds a button to a BorderPane
   * 
   * @param pane the BorderPane the button will be added to
   */
  public void addButton(BorderPane pane) {
    // creates button and adds it on bottom
    Button doneButton = new Button("Change View");
    doneButton.setOnAction(event -> {
      if (scene.getRoot().equals(root1)) {
        scene.setRoot(root2);
      } else {
        scene.setRoot(root1);
      }
    });
    pane.setBottom(doneButton);
  }

  public void addRadioButton(BorderPane pane) {
    ToggleGroup group = new ToggleGroup();
    RadioButton rb1 = new RadioButton("Scene 1");
    rb1.setToggleGroup(group);
    RadioButton rb2 = new RadioButton("Scene 2");
    rb2.setToggleGroup(group);

    VBox box = new VBox();
    box.getChildren().add(rb1);
    box.getChildren().add(rb2);

    Button switchButton = new Button("Change View");
    switchButton.setOnAction(event -> {
      if (group.getSelectedToggle().equals(rb1)) {
        scene.setRoot(root1);
      } else {
        scene.setRoot(root2);
      }
    });

    box.getChildren().add(switchButton);
    pane.setRight(box);
  }

  /**
   * Adds a TextArea to a BorderPane
   * 
   * @param pane the BorderPane the TextArea will be added to
   */
  public void addTextArea(BorderPane pane) {
    // creates and adds text area on right
    if (pane == root1) {
      TextArea testArea = new TextArea("This is a test \nfor TextArea");
      pane.setRight(testArea);
    } else {
      TextArea testArea = new TextArea("This is the other border pane");
      pane.setCenter(testArea);
    }
  }

  /**
   * Main method which launches the GUI
   * 
   * @param args command line input
   */
  public static void main(String[] args) {
    launch(args);
  }
}
