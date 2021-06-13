package minesweeper;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class NormalCell implements Cell {
  private Board board;
  private Button btn;
  private Cell[] neighbourCells = new Cell[8];
  private String type = "normal";
  private Integer bombCount;
  private boolean flaged = false;

  public NormalCell(Board board) {
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

  public void buttonPress() {
    if (bombCount > 0) {
      btn.setText(bombCount.toString());
    } else {
      btn.getStyleClass().add("clicked-button");
    }
    if (bombCount == 1) {
      btn.getStyleClass().add("one-button");
    } else if (bombCount == 2) {
      btn.getStyleClass().add("two-button");
    } else if (bombCount == 3) {
      btn.getStyleClass().add("three-button");
    } else if (bombCount == 4) {
      btn.getStyleClass().add("four-button");
    } else if (bombCount == 5) {
      btn.getStyleClass().add("five-button");
    } else if (bombCount == 6) {
      btn.getStyleClass().add("six-button");
    }
    btn.setDisable(true);
    clickOnNeighbours();
    board.checkIfCleared();
  }

  private void clickOnNeighbours() {
    if (bombCount == 0) {
      if (neighbourCells[0] != null && !neighbourCells[0].getButton().isDisabled()) {
        neighbourCells[0].buttonPress();
      }
      if (neighbourCells[1] != null && !neighbourCells[1].getButton().isDisabled()) {
        neighbourCells[1].buttonPress();
      }
      if (neighbourCells[2] != null && !neighbourCells[2].getButton().isDisabled()) {
        neighbourCells[2].buttonPress();
      }
      if (neighbourCells[3] != null && !neighbourCells[3].getButton().isDisabled()) {
        neighbourCells[3].buttonPress();
      }
      if (neighbourCells[4] != null && !neighbourCells[4].getButton().isDisabled()) {
        neighbourCells[4].buttonPress();
      }
      if (neighbourCells[5] != null && !neighbourCells[5].getButton().isDisabled()) {
        neighbourCells[5].buttonPress();
      }
      if (neighbourCells[6] != null && !neighbourCells[6].getButton().isDisabled()) {
        neighbourCells[6].buttonPress();
      }
      if (neighbourCells[7] != null && !neighbourCells[7].getButton().isDisabled()) {
        neighbourCells[7].buttonPress();
      }
    }
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
    this.neighbourCells = neighbourCells.clone();
    countBombs();
  }

  public String getType() {
    return type;
  }

  private void countBombs() {
    bombCount = 0;
    for (Cell i : neighbourCells) {
      if (i != null && i.getType().equals("bomb")) {
        bombCount++;
      }
    }
  }
}