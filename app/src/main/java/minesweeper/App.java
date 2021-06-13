package minesweeper;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioInputStream;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class App extends Application {
  private Scene scene;
  private Stage primaryStage;
  
  @Override
  public void start(Stage primaryStage) throws Exception {
    VBox root = new VBox();
    this.primaryStage = primaryStage;
    root.setPadding(new Insets(5));
    
    Start start = new Start(this);
    root.getChildren().addAll(start.getView());
    root.setAlignment(Pos.CENTER);
    scene = new Scene(root, 550, 310);
    primaryStage.setScene(scene);
    scene.getStylesheets().add(getClass().getClassLoader().getResource("app.css").toExternalForm());
    primaryStage.setTitle("Minesweeper");
    primaryStage.show();
    primaryStage.centerOnScreen();
  }

  public void updateScene(Scene scene) {
    this.scene = scene;
    scene.getStylesheets().add(getClass().getClassLoader().getResource("app.css").toExternalForm());
    primaryStage.setScene(scene);
    primaryStage.centerOnScreen();
  }

  public static synchronized void playSound(final String url) {
    new Thread(new Runnable() {
    // The wrapper thread is unnecessary, unless it blocks on the
    // Clip finishing; see comments.
      public void run() {
        try {
          Clip clip = AudioSystem.getClip();
          AudioInputStream inputStream = AudioSystem.getAudioInputStream(
          getClass().getResourceAsStream(url));
          clip.open(inputStream);
          clip.start(); 
        } catch (Exception e) {
          System.err.println(e.getMessage());
        }
      }
    }).start();
  }
}
