import java.util.ArrayList;

/**
 * TODO
 *
 */
public class Rook extends Piece{
	
	/**
	 * TODO
	 * @param color
	 * @param heigth
	 * @param width
	 */
	public Rook(String color, int heigth, int width){
		this.setColor(color);
		this.setRow(heigth);
		this.setColumn(width);
	}
	
	/**
	 * TODO
	 */
	public ArrayList<Square> possibleMoves(Board board){
		ArrayList<Square> movesList = new ArrayList<Square>();
		if (this.isDead()){
			return movesList;
		}
		return this.possibleMovesDroit(board);
	}
	
	/**
	 * TODO
	 */
	protected Rook clone() {
		return new Rook( this.getColor(), this.getRow(), this.getColumn());
	}

}
