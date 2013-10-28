import java.util.ArrayList;


public class Queen extends Piece{

	public Queen(String color, int heigth, int width){
		this.setColor(color);
		this.setHeigth(heigth);
		this.setWidth(width);
	}
	
	public ArrayList<Square> possibleMoves(Board board){
		ArrayList<Square> movesList = new ArrayList<Square>();
		if (this.isDead()){
			return movesList;
		}
		ArrayList<Square> movesList1 = this.possibleMovesDiagonale(board);
		ArrayList<Square> movesList2 = this.possibleMovesDroit(board);
		movesList1.addAll(movesList2);
		return movesList1;
	}
	
	protected Queen clone() {
		return new Queen( this.getColor(), this.getHeigth(), this.getWidth());
	}


}
