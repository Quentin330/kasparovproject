import java.util.ArrayList;


public class King extends Piece{
	
	public King(String color, int heigth, int width){
		this.setColor(color);
		this.setRow(heigth);
		this.setColumn(width);
	}
	
	
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
		return movesList;
	}


	protected King clone() {
		return new King( this.getColor(), this.getRow(), this.getColumn());
	}
}
