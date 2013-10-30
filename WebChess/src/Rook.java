import java.util.ArrayList;


public class Rook extends Piece{
	
	public Rook(String color, int heigth, int width){
		this.setColor(color);
		this.setRow(heigth);
		this.setColumn(width);
	}
	
	public ArrayList<Square> possibleMoves(Board board){
		ArrayList<Square> movesList = new ArrayList<Square>();
		if (this.isDead()){
			return movesList;
		}
		return this.possibleMovesDroit(board);
	}
	
	protected Rook clone() {
		return new Rook( this.getColor(), this.getRow(), this.getColumn());
	}

}
