package minesweeper;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class BombCell implements Cell {
  private Button btn;
  private Board board;
  private String type = "bomb";
  private boolean flaged = false;

  public BombCell(Board board) {
    this.board = board;
    btn = new Button();
    btn.setPrefSize(30, 30);
    btn.setMaxSize(30, 30);
    btn.setMinSize(30, 30);
  
    btn.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        MouseButton button = event.getButton();
        if (button==MouseButton.PRIMARY) {
          if (!flaged) {
            buttonPress();
          }
        } else if (button==MouseButton.SECONDARY) {
          rightClick();
        }
      }
    });
  }

  @Override
  public void buttonPress() {
    ImageView iv = new ImageView(new Image(getClass().getResourceAsStream("/bomb.png")));
    iv.setFitHeight(25);
    iv.setFitWidth(25);
    btn.setGraphic(iv);
    btn.getStyleClass().add("bomb-button");
    board.disableAll();
    App.playSound("/bomb.wav");
  }

  public void rightClick() {
    if (flaged) {
      flaged = false;
      btn.setGraphic(null);
    } else {
      ImageView iv = new ImageView(new Image(getClass().getResourceAsStream("/flag.png")));
      iv.setFitHeight(25);
      iv.setFitWidth(25);
      btn.setGraphic(iv);
      flaged = true;
    }
  }

  public Button getButton() {
    return btn;
  }

  public void setNeighbours(Cell[] neighbourCells) {
    //this.neighbourCells = neighbourCells.clone();
  }

  public String getType() {
    return type;
  }
}