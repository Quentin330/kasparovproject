import java.util.ArrayList;


public class Rook extends Piece{
	
	public Rook(String color, int heigth, int width){
		this.setColor(color);
		this.setHeigth(heigth);
		this.setWidth(width);
	}
	
	public ArrayList<Square> possibleMoves(Board board){
		ArrayList<Square> movesList = new ArrayList<Square>();
		if (this.isDead()){
			return movesList;
		}
		return this.possibleMovesDroit(board);
	}
	
	protected Piece clone() {
		return new Rook( this.getColor(), this.getHeigth(), this.getWidth());
	}

}
