import java.util.ArrayList;


public class King extends Piece{
	
	public King(String color, int heigth, int width){
		this.setColor(color);
		this.setHeigth(heigth);
		this.setWidth(width);
	}
	
	
	public ArrayList<Square> possibleMoves(Board board){
		ArrayList<Square> movesList = new ArrayList<Square>();
		//haut
		if (this.getHeigth()!=8){
			if (!this.isSameColor(board, this.getHeigth()+1, this.getWidth())){
				movesList.add(new Square(this.getHeigth()+1, this.getWidth()));
			}
		}
		//bas
		if (this.getHeigth()!=1){
			if (!this.isSameColor(board, this.getHeigth()-1, this.getWidth())){
				movesList.add(new Square(this.getHeigth()-1, this.getWidth()));
			}
		}
		//gauche
		if (this.getWidth()!=1){
			if (!this.isSameColor(board, this.getHeigth(), this.getWidth()-1)){
				movesList.add(new Square(this.getHeigth(), this.getWidth()-1));
			}
		}
		//droite
		if (this.getWidth()!=8){
			if (!this.isSameColor(board, this.getHeigth(), this.getWidth()+1)){
				movesList.add(new Square(this.getHeigth(), this.getWidth()+1));
			}
		}
		//haut droite
		if (this.getHeigth()!=8 && this.getWidth()!=8){
			if (!this.isSameColor(board, this.getHeigth()+1, this.getWidth()+1)){
				movesList.add(new Square(this.getHeigth()+1, this.getWidth()+1));
			}
		}
		//haut gauche
		if (this.getHeigth()!=8 && this.getWidth()!=1){
			if (!this.isSameColor(board, this.getHeigth()+1, this.getWidth()-1)){
				movesList.add(new Square(this.getHeigth()+1, this.getWidth()-1));
			}
		}
		//bas droite
		if (this.getHeigth()!=1 && this.getWidth()!=8){
			if (!this.isSameColor(board, this.getHeigth()-1, this.getWidth()+1)){
				movesList.add(new Square(this.getHeigth()-1, this.getWidth()+1));
			}
		}
		//bas gauche
		if (this.getHeigth()!=1 && this.getWidth()!=1){
			if (!this.isSameColor(board, this.getHeigth()-1, this.getWidth()-1)){
				movesList.add(new Square(this.getHeigth()-1, this.getWidth()-1));
			}
		}
		return movesList;
	}


	protected King clone() {
		return new King( this.getColor(), this.getHeigth(), this.getWidth());
	}
}
