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
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

/**
 * Class which creates a basic UI using JavaFX
 * 
 * @author Michael Goldstein
 */
public class Main extends Application {
  /**
   * Method called automatically upon launch by JavaFX. Sets the UI
   * 
   * @param primaryStage JavaFX stage layout manager(s) will be added to
   */
  @Override
  public void start(Stage primaryStage) {
    try {
      // sets up a border pane layout manager
      BorderPane root = new BorderPane(); // border pane layout manager elements will be added to
      addLabel(root); // adds label
      addComboBox(root); // adds combo box
      addImageView(root); // adds image
      addButton(root); // adds button
      addTextArea(root); // adds textArea

      // creates scene
      Scene scene = new Scene(root, 450, 450);
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
    Button doneButton = new Button("Done");
    doneButton.setOnAction(event -> {
      if (doneButton.getText().equals("Done")) {
        doneButton.setText("Not Done");
      } else {
        doneButton.setText("Done");
      }
    });
    pane.setBottom(doneButton);
  }

  /**
   * Adds a TextArea to a BorderPane
   * 
   * @param pane the BorderPane the TextArea will be added to
   */
  public void addTextArea(BorderPane pane) {
    // creates and adds text area on right
    TextArea testArea = new TextArea("This is a test \nfor TextArea");
    pane.setRight(testArea);
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
