import java.util.ArrayList;

/**
 * TODO
 *
 */
public class Queen extends Piece {

	/**
	 * TODO
	 * @param color
	 * @param heigth
	 * @param width
	 */
	public Queen(String color, int heigth, int width) {
		this.setNom("Queen");
		this.setColor(color);
		this.setRow(heigth);
		this.setColumn(width);
		if (color.equals("black"))
			this.setShortcut("d");
		else
			this.setShortcut("D");
	}

	/**
	 * TODO
	 */
	public ArrayList<Square> possibleMoves(Board board) {
		ArrayList<Square> movesList = new ArrayList<Square>();
		if (this.isDead())
			return movesList;
		ArrayList<Square> movesList1 = this.possibleMovesDiagonale(board);
		ArrayList<Square> movesList2 = this.possibleMovesDroit(board);
		movesList1.addAll(movesList2);
		return movesList1;
	}

	/**
	 * TODO
	 */
	protected Queen clone() {
		return new Queen( this.getColor(), this.getRow(), this.getColumn());
	}


}
