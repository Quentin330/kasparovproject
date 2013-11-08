import java.util.ArrayList;

/**
 * TODO
 *
 */
public class Pawn extends Piece{
	
	/**
	 * TODO
	 * @param color
	 * @param heigth
	 * @param width
	 */
	public Pawn(String color, int heigth, int width){
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
		if (this.isBlack()){
			if (this.getRow()==7){
				if (board.isEmpty(6, this.getColumn())){
					movesList.add(new Square(6, this.getColumn()));
					if (board.isEmpty(5, this.getColumn())){
						movesList.add(new Square(5, this.getColumn()));
					}
				}
				if (this.getColumn()!= 1 && board.isWhite(6, this.getColumn()-1)){
					movesList.add(new Square(6, this.getColumn()-1));
				}
				if (this.getColumn()!= 8 && board.isWhite(6, this.getColumn()+1)){
					movesList.add(new Square(6, this.getColumn()+1));
				}
			}
			else {
				if (this.getRow()!=1 && board.isEmpty(this.getRow()-1, this.getColumn())){
					movesList.add(new Square(this.getRow()-1, this.getColumn()));
				}
				if (this.getRow()!=1 && this.getColumn()!= 1 && board.isWhite(this.getRow()-1, this.getColumn()-1)){
					movesList.add(new Square(this.getRow()-1, this.getColumn()-1));
				}
				if (this.getRow()!=1 && this.getColumn()!= 8 && board.isWhite(this.getRow()-1, this.getColumn()+1)){
					movesList.add(new Square(this.getRow()-1, this.getColumn()+1));
				}
			}
		}
		else{
			if (this.getRow()==2){
				if (board.isEmpty(3, this.getColumn())){
					movesList.add(new Square(3, this.getColumn()));
					if (board.isEmpty(4, this.getColumn())){
						movesList.add(new Square(4, this.getColumn()));
					}
				}
				if (this.getColumn()!= 1 && board.isBlack(2, this.getColumn()-1)){
					movesList.add(new Square(2, this.getColumn()-1));
				}
				if (this.getColumn()!= 8 && board.isBlack(2, this.getColumn()+1)){
					movesList.add(new Square(2, this.getColumn()+1));
				}
			}
			else {
				if (this.getRow()!=8 && board.isEmpty(this.getRow()+1, this.getColumn())){
					movesList.add(new Square(this.getRow()+1, this.getColumn()));
				}
				if (this.getRow()!=8 && this.getColumn()!= 1 && board.isBlack(this.getRow()+1, this.getColumn()-1)){
					movesList.add(new Square(this.getRow()+1, this.getColumn()-1));
				}
				if (this.getRow()!=8 && this.getColumn()!= 8 && board.isBlack(this.getRow()+1, this.getColumn()+1)){
					movesList.add(new Square(this.getRow()+1, this.getColumn()+1));
				}
			}
		}
		return movesList;
	}
	
	/**
	 * TODO
	 */
	protected Pawn clone() {
		return new Pawn( this.getColor(), this.getRow(), this.getColumn());
	}
}