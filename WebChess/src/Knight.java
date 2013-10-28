import java.util.ArrayList;


public class Knight extends Piece{
	
	public Knight(String color, int heigth, int width){
		this.setColor(color);
		this.setHeigth(heigth);
		this.setWidth(width);
	}
	
	public ArrayList<Square> possibleMoves(Board board){
		ArrayList<Square> movesList = new ArrayList<Square>();
		if (this.isDead()){
			return movesList;
		}
		//Move haut haut droit
		if (this.getHeigth()<7 && this.getWidth()<8){
			if (!this.isSameColor(board, this.getHeigth()+2, this.getWidth()+1)){
				movesList.add(new Square(this.getHeigth()+2, this.getWidth()+1));
			}
		}
		//Move haut haut gauche
		if (this.getHeigth()<7 && this.getWidth()>1){
			if (!this.isSameColor(board, this.getHeigth()+2, this.getWidth()-1)){
				movesList.add(new Square(this.getHeigth()+2, this.getWidth()-1));
			}
		}
		//Move bas bas gauche
		if (this.getHeigth()>2 && this.getWidth()>1){
			if (!this.isSameColor(board, this.getHeigth()-2, this.getWidth()-1)){
				movesList.add(new Square(this.getHeigth()-2, this.getWidth()-1));
			}
		}
		//Move bas bas droit
		if (this.getHeigth()>2 && this.getWidth()<8){
			if (!this.isSameColor(board, this.getHeigth()-2, this.getWidth()+1)){
				movesList.add(new Square(this.getHeigth()-2, this.getWidth()+1));
			}
		}
		//Move droit droit haut
		if (this.getHeigth()<8 && this.getWidth()<7){
			if (!this.isSameColor(board, this.getHeigth()+1, this.getWidth()+2)){
				movesList.add(new Square(this.getHeigth()+1, this.getWidth()+2));
			}
		}
		//Move droit droit bas
		if (this.getHeigth()>1 && this.getWidth()<7){
			if (!this.isSameColor(board, this.getHeigth()-1, this.getWidth()+2)){
				movesList.add(new Square(this.getHeigth()-1, this.getWidth()+2));
			}
		}
		//Move gauche gauche haut
		if (this.getHeigth()<8 && this.getWidth()>2){
			if (!this.isSameColor(board, this.getHeigth()+1, this.getWidth()-2)){
				movesList.add(new Square(this.getHeigth()+1, this.getWidth()-2));
			}
		}
		//Move gauche gauche bas
		if (this.getHeigth()>1 && this.getWidth()>2){
			if (!this.isSameColor(board, this.getHeigth()-1, this.getWidth()-2)){
				movesList.add(new Square(this.getHeigth()-1, this.getWidth()-2));
			}
		}
		return movesList;
	}

	protected Piece clone() {
		return new Knight( this.getColor(), this.getHeigth(), this.getWidth());
	}

}
