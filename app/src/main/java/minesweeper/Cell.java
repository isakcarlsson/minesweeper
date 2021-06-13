package minesweeper;

import javafx.scene.control.Button;

public interface Cell {
  
  public void buttonPress();

  public Button getButton();

  public void setNeighbours(Cell[] neighbourCells);

  public String getType();
}