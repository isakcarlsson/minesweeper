package minesweeper;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class Start {
  private App app;
  private Button btnTen = new Button("10 x 10");
  private Button btnTwenty = new Button("20 x 20");
  private Button btnThirty = new Button("30 x 30");
  private Button btnCustom = new Button("Custom board");
  private VBox view = new VBox();

  public Start(App app) {
    this.app = app;
    btnTen.getStyleClass().add("normal-button");
    btnTwenty.getStyleClass().add("normal-button");
    btnThirty.getStyleClass().add("normal-button");
    btnCustom.getStyleClass().add("normal-button");
    btnTen.setOnAction(e -> {
      Board board = new Board(10, 10, app);
      app.updateScene(new Scene(board.getView(), 500, 310));
    });
    btnTwenty.setOnAction(e -> {
      Board board = new Board(20, 20, app);
      app.updateScene(new Scene(board.getView(), 900, 610));
    });
    btnThirty.setOnAction(e -> {
      Board board = new Board(30, 30, app);
      app.updateScene(new Scene(board.getView(), 1200, 910));
    });
    view.setSpacing(20);
    view.setAlignment(Pos.CENTER);
    view.getChildren().addAll(btnTen, btnTwenty,btnThirty, btnCustom);
  }

  public void updateView() {
    app.updateScene(new Scene(view, 550, 310));
  }

  public Parent getView() {
    return view;
  }
}
