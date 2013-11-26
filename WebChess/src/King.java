import java.util.ArrayList;

/**
 * TODO
 *
 */
public class King extends Piece {
	
	/**
	 * TODO
	 * @param color
	 * @param heigth
	 * @param width
	 */
	public King(String color, int heigth, int width) {
		this.moveOnce(false);
		this.setNom("King");
		this.setColor(color);
		this.setRow(heigth);
		this.setColumn(width);
		if (color.equals("black"))
			this.setShortcut("r");
		else
			this.setShortcut("R");
	}
	
	/**
	 * TODO
	 */
	public ArrayList<Square> possibleMoves(Board board) {
		ArrayList<Square> movesList = new ArrayList<Square>();
		//haut
		if (this.getRow()!=8){
			if (!this.isSameColor(board, this.getRow()+1, this.getColumn())){
				movesList.add(new Square(this.getRow()+1, this.getColumn()));
			}
		}
		//bas
		if (this.getRow()!=1){
			if (!this.isSameColor(board, this.getRow()-1, this.getColumn())){
				movesList.add(new Square(this.getRow()-1, this.getColumn()));
			}
		}
		//gauche
		if (this.getColumn()!=1){
			if (!this.isSameColor(board, this.getRow(), this.getColumn()-1)){
				movesList.add(new Square(this.getRow(), this.getColumn()-1));
			}
		}
		//droite
		if (this.getColumn()!=8){
			if (!this.isSameColor(board, this.getRow(), this.getColumn()+1)){
				movesList.add(new Square(this.getRow(), this.getColumn()+1));
			}
		}
		//haut droite
		if (this.getRow()!=8 || this.getColumn()!=8){
			if (!this.isSameColor(board, this.getRow()+1, this.getColumn()+1)){
				movesList.add(new Square(this.getRow()+1, this.getColumn()+1));
			}
		}
		//haut gauche
		if (this.getRow()!=8 || this.getColumn()!=1){
			if (!this.isSameColor(board, this.getRow()+1, this.getColumn()-1)){
				movesList.add(new Square(this.getRow()+1, this.getColumn()-1));
			}
		}
		//bas droite
		if (this.getRow()!=1 || this.getColumn()!=8){
			if (!this.isSameColor(board, this.getRow()-1, this.getColumn()+1)){
				movesList.add(new Square(this.getRow()-1, this.getColumn()+1));
			}
		}
		//bas gauche
		if (this.getRow()!=1 || this.getColumn()!=1){
			if (!this.isSameColor(board, this.getRow()-1, this.getColumn()-1)){
				movesList.add(new Square(this.getRow()-1, this.getColumn()-1));
			}
		}
		//petit roque
		if (!this.hasMovedOnce()) { 
			if (this.isSameColor(board, 1, 8) && !board.getPiece(1, 8).hasMovedOnce()
					&& board.isEmpty(1, 6) && board.isEmpty(1, 7) && !board.isEchec(this.getColor(), 1, 5) && !board.isEchec(this.getColor(), 1, 6) && !board.isEchec(this.getColor(), 1, 7)) {
				movesList.add(new Square(1, 7));
			} 
			if (this.isSameColor(board, 8, 8) && !board.getPiece(8, 8).hasMovedOnce()
					&& board.isEmpty(8, 6) && board.isEmpty(8, 7) && !board.isEchec(this.getColor(), 8, 5) && !board.isEchec(this.getColor(), 8, 6) && !board.isEchec(this.getColor(), 8, 7)) {
				movesList.add(new Square(8, 7));
			}
		}
		//grand roque
		if (!this.hasMovedOnce()) { 
			if (this.isSameColor(board, 1, 1) && !board.getPiece(1, 1).hasMovedOnce()
					&& board.isEmpty(1, 4) && board.isEmpty(1, 3) && board.isEmpty(1, 2) && !board.isEchec(this.getColor(), 1, 5) && !board.isEchec(this.getColor(), 1, 4) && !board.isEchec(this.getColor(), 1, 3)) {
				movesList.add(new Square(1, 3));
			} 
			if (this.isSameColor(board, 8, 1) && !board.getPiece(8, 1).hasMovedOnce()
					&& board.isEmpty(8, 4) && board.isEmpty(8, 3) && board.isEmpty(8, 2) && !board.isEchec(this.getColor(), 8, 5) && !board.isEchec(this.getColor(), 8, 4) && !board.isEchec(this.getColor(), 8, 3)) {
				movesList.add(new Square(8, 3));
			}
		}
		return movesList;
	}

	/**
	 * TODO
	 */
	protected King clone() {
		return new King( this.getColor(), this.getRow(), this.getColumn());
	}
}
