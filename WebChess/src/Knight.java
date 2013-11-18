import java.util.ArrayList;

/**
 * TODO
 *
 */
public class Knight extends Piece {

	/**
	 * TODO
	 * @param color
	 * @param heigth
	 * @param width
	 */
	public Knight(String color, int heigth, int width) {
		this.setNom("Knight");
		this.setColor(color);
		this.setRow(heigth);
		this.setColumn(width);
		if (color.equals("black"))
			this.setShortcut("c");
		else
			this.setShortcut("C");
	}

	/**
	 * TODO
	 */
	public ArrayList<Square> possibleMoves(Board board) {
		ArrayList<Square> movesList = new ArrayList<Square>();
		if (this.isDead()){
			return movesList;
		}
		//Move haut haut droit
		if (this.getRow()<7 && this.getColumn()<8){
			if (!this.isSameColor(board, this.getRow()+2, this.getColumn()+1)){
				movesList.add(new Square(this.getRow()+2, this.getColumn()+1));
			}
		}
		//Move haut haut gauche
		if (this.getRow()<7 && this.getColumn()>1){
			if (!this.isSameColor(board, this.getRow()+2, this.getColumn()-1)){
				movesList.add(new Square(this.getRow()+2, this.getColumn()-1));
			}
		}
		//Move bas bas gauche
		if (this.getRow()>2 && this.getColumn()>1){
			if (!this.isSameColor(board, this.getRow()-2, this.getColumn()-1)){
				movesList.add(new Square(this.getRow()-2, this.getColumn()-1));
			}
		}
		//Move bas bas droit
		if (this.getRow()>2 && this.getColumn()<8){
			if (!this.isSameColor(board, this.getRow()-2, this.getColumn()+1)){
				movesList.add(new Square(this.getRow()-2, this.getColumn()+1));
			}
		}
		//Move droit droit haut
		if (this.getRow()<8 && this.getColumn()<7){
			if (!this.isSameColor(board, this.getRow()+1, this.getColumn()+2)){
				movesList.add(new Square(this.getRow()+1, this.getColumn()+2));
			}
		}
		//Move droit droit bas
		if (this.getRow()>1 && this.getColumn()<7){
			if (!this.isSameColor(board, this.getRow()-1, this.getColumn()+2)){
				movesList.add(new Square(this.getRow()-1, this.getColumn()+2));
			}
		}
		//Move gauche gauche haut
		if (this.getRow()<8 && this.getColumn()>2){
			if (!this.isSameColor(board, this.getRow()+1, this.getColumn()-2)){
				movesList.add(new Square(this.getRow()+1, this.getColumn()-2));
			}
		}
		//Move gauche gauche bas
		if (this.getRow()>1 && this.getColumn()>2){
			if (!this.isSameColor(board, this.getRow()-1, this.getColumn()-2)){
				movesList.add(new Square(this.getRow()-1, this.getColumn()-2));
			}
		}
		return movesList;
	}

	/**
	 * TODO
	 */
	protected Knight clone() {
		return new Knight( this.getColor(), this.getRow(), this.getColumn());
	}

}
