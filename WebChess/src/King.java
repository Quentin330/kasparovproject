import java.util.ArrayList;

/**
 * TODO
 *
 */
public class King extends Piece{
	
	/**
	 * TODO
	 * @param color
	 * @param heigth
	 * @param width
	 */
	public King(String color, int heigth, int width){
		this.moveOnce(false);
		this.setNom("King");
		this.setColor(color);
		this.setRow(heigth);
		this.setColumn(width);
		if (color.equals("black")){
			this.setShortcut("r");
		}
		else{
			this.setShortcut("R");
		}
	}
	
	/**
	 * TODO
	 */
	public ArrayList<Square> possibleMoves(Board board){
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
		if (this.getRow()!=8 && this.getColumn()!=8){
			if (!this.isSameColor(board, this.getRow()+1, this.getColumn()+1)){
				movesList.add(new Square(this.getRow()+1, this.getColumn()+1));
			}
		}
		//haut gauche
		if (this.getRow()!=8 && this.getColumn()!=1){
			if (!this.isSameColor(board, this.getRow()+1, this.getColumn()-1)){
				movesList.add(new Square(this.getRow()+1, this.getColumn()-1));
			}
		}
		//bas droite
		if (this.getRow()!=1 && this.getColumn()!=8){
			if (!this.isSameColor(board, this.getRow()-1, this.getColumn()+1)){
				movesList.add(new Square(this.getRow()-1, this.getColumn()+1));
			}
		}
		//bas gauche
		if (this.getRow()!=1 && this.getColumn()!=1){
			if (!this.isSameColor(board, this.getRow()-1, this.getColumn()-1)){
				movesList.add(new Square(this.getRow()-1, this.getColumn()-1));
			}
		}
		//petit roque
		if (!this.hasMovedOnce()) { 
			if (this.isSameColor(board, 1, 8) && !board.getPiece(1, 8).hasMovedOnce()) {
				movesList.add(new Square(this.getRow(), this.getColumn()+2));
			} 
			if (this.isSameColor(board, 8, 8) && !board.getPiece(8, 8).hasMovedOnce()) {
				movesList.add(new Square(this.getRow(), this.getColumn()+2));
			}
		}
		//grand roque
		if (!this.hasMovedOnce()) { 
			if (this.isSameColor(board, 1, 1) && !board.getPiece(1, 1).hasMovedOnce()) {
				movesList.add(new Square(this.getRow(), this.getColumn()-2));
			} 
			if (this.isSameColor(board, 8, 1) && !board.getPiece(8, 1).hasMovedOnce()) {
				movesList.add(new Square(this.getRow()-2, this.getColumn()-2));
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
