import java.util.ArrayList;


public class Pawn extends Piece{
	
	public Pawn(String color, int heigth, int width){
		this.setColor(color);
		this.setHeigth(heigth);
		this.setWidth(width);
	}

	
	public ArrayList<Square> possibleMoves(Board board){
		ArrayList<Square> movesList = new ArrayList<Square>();
		if (this.isDead()){
			return movesList;
		}
		if (this.isBlack()){
			if (this.getHeigth()==7){
				if (board.isEmpty(6, this.getWidth())){
					movesList.add(new Square(6, this.getWidth()));
					if (board.isEmpty(5, this.getWidth())){
						movesList.add(new Square(5, this.getWidth()));
					}
				}
				if (this.getWidth()!= 1 && board.isWhite(6, this.getWidth()-1)){
					movesList.add(new Square(6, this.getWidth()-1));
				}
				if (this.getWidth()!= 8 && board.isWhite(6, this.getWidth()+1)){
					movesList.add(new Square(6, this.getWidth()+1));
				}
			}
			else {
				if (this.getHeigth()!=1 && board.isEmpty(this.getHeigth()-1, this.getWidth())){
					movesList.add(new Square(this.getHeigth()-1, this.getWidth()));
				}
				if (this.getHeigth()!=1 && this.getWidth()!= 1 && board.isWhite(this.getHeigth()-1, this.getWidth()-1)){
					movesList.add(new Square(this.getHeigth()-1, this.getWidth()-1));
				}
				if (this.getHeigth()!=1 && this.getWidth()!= 8 && board.isWhite(this.getHeigth()-1, this.getWidth()+1)){
					movesList.add(new Square(this.getHeigth()-1, this.getWidth()+1));
				}
			}
		}
		else{
			if (this.getHeigth()==2){
				if (board.isEmpty(3, this.getWidth())){
					movesList.add(new Square(3, this.getWidth()));
					if (board.isEmpty(4, this.getWidth())){
						movesList.add(new Square(4, this.getWidth()));
					}
				}
				if (this.getWidth()!= 1 && board.isBlack(2, this.getWidth()-1)){
					movesList.add(new Square(2, this.getWidth()-1));
				}
				if (this.getWidth()!= 8 && board.isBlack(2, this.getWidth()+1)){
					movesList.add(new Square(2, this.getWidth()+1));
				}
			}
			else {
				if (this.getHeigth()!=8 && board.isEmpty(this.getHeigth()+1, this.getWidth())){
					movesList.add(new Square(this.getHeigth()+1, this.getWidth()));
				}
				if (this.getHeigth()!=8 && this.getWidth()!= 1 && board.isBlack(this.getHeigth()+1, this.getWidth()-1)){
					movesList.add(new Square(this.getHeigth()+1, this.getWidth()-1));
				}
				if (this.getHeigth()!=8 && this.getWidth()!= 8 && board.isBlack(this.getHeigth()+1, this.getWidth()+1)){
					movesList.add(new Square(this.getHeigth()+1, this.getWidth()+1));
				}
			}
		}
		return movesList;
	}
	
	protected Pawn clone() {
		return new Pawn( this.getColor(), this.getHeigth(), this.getWidth());
	}
}
