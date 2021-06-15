package minesweeper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Board {
  private Cell[][] field;
  private int width;
  private int height;
  private BorderPane fieldView = new BorderPane();
  private int bombsLeft;
  private int bombs;
  private Label lblBombs = new Label();
  private Label lblTime = new Label("Time: 0");
  private Button btnReset = new Button("Reset");
  private Button btnMenu = new Button("Main Menu");
  private int time = 0;
  private Timer myTimer = new Timer();
  private TimerTask task = new TimerTask() {
    public void run() {
      time += 1;
      Platform.runLater(() -> lblTime.setText("Time: " + time / 10f));
    }
  };
  

  public Board(int width, int height, App app) {
    btnReset.getStyleClass().add("normal-button");
    btnReset.setOnAction(e -> {
      generateField();
      setAllNeighbours();
      createView();
    });
    btnMenu.getStyleClass().add("normal-button");
    btnMenu.setOnAction(e -> {
      Start start = new Start(app);
      start.updateView();
    });
    this.width = width;
    this.height = height;
    fieldView.setPadding(new Insets(5));
    field = new Cell[width][height];  
    generateField();
    setAllNeighbours();
    createView();
    myTimer.scheduleAtFixedRate(task , 100, 100);
  }

  private void generateField() {
    List<Integer> freeCells = new ArrayList<Integer>();
    List<Integer> bombPos = new ArrayList<Integer>();

    bombs = width * height / 6;
    bombsLeft = bombs;

    for (int i = 0; i < width * height; i++) {
      freeCells.add(i);
    }

    Random rdm = new Random();
    for (int i = 0; i < bombs; i++) {
      int j = rdm.nextInt(freeCells.size());
      bombPos.add(freeCells.get(j));
      freeCells.remove(j);
    }

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        if (bombPos.contains(i * width + j)) {
          field[j][i] = new BombCell(this);
        } else {
          field[j][i] = new NormalCell(this);
        }
      }
    }
  }

  private void setAllNeighbours() {
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        Cell[] neighbours = new Cell[8];

        if (i == 0 && j == 0) {
          //Upper left corner
          neighbours[0] = null;
          neighbours[1] = null;
          neighbours[2] = null;
          neighbours[3] = null;
          neighbours[4] = field[j + 1][i];
          neighbours[5] = null;
          neighbours[6] = field[j][i + 1];
          neighbours[7] = field[j + 1][i + 1];
        } else if (i == 0 && j < width - 1) {
          //Upper row
          neighbours[0] = null;
          neighbours[1] = null;
          neighbours[2] = null;
          neighbours[3] = field[j - 1][i];
          neighbours[4] = field[j + 1][i];
          neighbours[5] = field[j - 1][i + 1];
          neighbours[6] = field[j][i + 1];
          neighbours[7] = field[j + 1][i + 1];
        } else if (i == 0) {
          //Upper right corner
          neighbours[0] = null;
          neighbours[1] = null;
          neighbours[2] = null;
          neighbours[3] = field[j - 1][i];
          neighbours[4] = null;
          neighbours[5] = field[j - 1][i + 1];
          neighbours[6] = field[j][i + 1];
          neighbours[7] = null;
        } else if (j == 0 && i == height - 1) {
          //Lower left corner
          neighbours[0] = null;
          neighbours[1] = field[j][i - 1];
          neighbours[2] = field[j + 1][i - 1];
          neighbours[3] = null;
          neighbours[4] = field[j + 1][i];
          neighbours[5] = null;
          neighbours[6] = null;
          neighbours[7] = null;
        } else if (j == 0) {
          //Left row
          neighbours[0] = null;
          neighbours[1] = field[j][i - 1];
          neighbours[2] = field[j + 1][i - 1];
          neighbours[3] = null;
          neighbours[4] = field[j + 1][i];
          neighbours[5] = null;
          neighbours[6] = field[j][i + 1];
          neighbours[7] = field[j + 1][i + 1];
        } else if (i == height - 1 && j == width -1) {
          //Lower right corner
          neighbours[0] = field[j - 1][i - 1];
          neighbours[1] = field[j][i - 1];
          neighbours[2] = null;
          neighbours[3] = field[j - 1][i];
          neighbours[4] = null;
          neighbours[5] = null;
          neighbours[6] = null;
          neighbours[7] = null;
        } else if (i == height -1) {
          //Lower row
          neighbours[0] = field[j - 1][i - 1];
          neighbours[1] = field[j][i - 1];
          neighbours[2] = field[j + 1][i - 1];
          neighbours[3] = field[j - 1][i];
          neighbours[4] = field[j + 1][i];
          neighbours[5] = null;
          neighbours[6] = null;
          neighbours[7] = null;
        } else if (j == width - 1) { 
          //Right row
          neighbours[0] = field[j - 1][i - 1];
          neighbours[1] = field[j][i - 1];
          neighbours[2] = null;
          neighbours[3] = field[j - 1][i];
          neighbours[4] = null;
          neighbours[5] = field[j - 1][i + 1];
          neighbours[6] = field[j][i + 1];
          neighbours[7] = null;
        } else {
          neighbours[0] = field[j - 1][i - 1];
          neighbours[1] = field[j][i - 1];
          neighbours[2] = field[j + 1][i - 1];
          neighbours[3] = field[j - 1][i];
          neighbours[4] = field[j + 1][i];
          neighbours[5] = field[j - 1][i + 1];
          neighbours[6] = field[j][i + 1];
          neighbours[7] = field[j + 1][i + 1];
        }

        field[j][i].setNeighbours(neighbours);
      }
    }
  }
  
  private void createView() {
    VBox vb = new VBox();
    for (int i = 0; i < height; i ++) {
      HBox hb = new HBox();
      for (int j = 0; j < width; j++) {
        hb.getChildren().addAll(field[i][j].getButton());
      }
      vb.getChildren().addAll(hb);
    }
    fieldView.setLeft(vb);
    vb = new VBox();
    vb.setSpacing(5);
    vb.setAlignment(Pos.CENTER);
    vb.getChildren().addAll(btnReset, btnMenu);
    BorderPane bp = new BorderPane();
    bp.setBottom(vb);
    vb = new VBox();
    vb.setSpacing(5);
    vb.setAlignment(Pos.CENTER);
    vb.getChildren().addAll(lblBombs, lblTime);
    bp.setTop(vb);
    fieldView.setCenter(bp);

    time = 0;
    lblTime.setText("Time: 0");
    lblBombs.setText("Bombs left: " + bombsLeft);
  }

  public void checkIfCleared() {
    int k = 0;
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        if (!field[j][i].getButton().isDisabled()) {
          k++;
        }
      }
    }
    if (k <= bombs) {
      myTimer.cancel();
      App.playSound("/snyggt.wav");
      Button btn;

      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          btn = field[j][i].getButton();
          if (!btn.isDisabled()) {
            ImageView iv = new ImageView(new Image(getClass().getResourceAsStream("/bomb.png")));
            iv.setFitHeight(25);
            iv.setFitWidth(25);
            btn.setGraphic(iv);
          }
        }
      }
      disableAll();
    } 
  }

  public void changeBombsLeft(int i) {
    bombsLeft += i;
    lblBombs.setText("Bombs left: " + bombsLeft);
  }

  public void disableAll() {
    for (int i = 0; i < height; i++) {
      for (int j = 0; j <  width; j++) {
        field[j][i].getButton().setDisable(true);
      }
    }
  }

  public Parent getView() {
    return fieldView;
  }
}