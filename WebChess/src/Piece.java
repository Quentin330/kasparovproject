import java.util.ArrayList;


abstract public class Piece {

	/**
	 * black or white 
	 */
	private String color;

	/**
	 * position de la pièce en hauteur (de 1 à 8 et 0 si pièce perdue)
	 */
	private int heigth;

	/**
	 * position de la pièce en largeur (de 1 à 8 et 0 si pièce perdue)
	 */
	private int width;

	public boolean isBlack(){
		return (this.color.equals("black"));
	}

	public boolean isWhite(){
		return (this.color.equals("white"));
	}
	
	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public int getHeigth() {
		return heigth;
	}

	public void setHeigth(int heigth) {
		this.heigth = heigth;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}
	
	public boolean isDead(){
		return (this.getHeigth()==0 && this.getWidth()==0);
	}
	
	public boolean isOpponent(Board board, int heigth, int width){
		if (this.isBlack()){
			return board.isWhite(heigth, width);
		}
		else{
			return board.isBlack(heigth, width);
		}
	}
	
	public boolean isSameColor(Board board, int heigth, int width){
		if (this.isBlack()){
			return board.isBlack(heigth, width);
		}
		else{
			return board.isWhite(heigth, width);
		}
	}

	public ArrayList<Square> possibleMovesDiagonale(Board board){
		ArrayList<Square> movesList = new ArrayList<Square>();
		int i = 1;
		//Mouvement en haut à droite
		while (this.getHeigth() +i<9 && this.getWidth() +i<9 && board.isEmpty(this.getHeigth() +i, this.getWidth() +i)){
			movesList.add(new Square(this.getHeigth()+i, this.getWidth()+i));
			i++;
		}
		if (this.isOpponent(board, this.getHeigth()+i, this.getWidth()+i)){
			movesList.add(new Square(this.getHeigth()+i, this.getWidth()+i));
		}
		i = 1;
		//Mouvement en haut à gauche
		while (this.getHeigth() +i<9 && this.getWidth() -i>0 && board.isEmpty(this.getHeigth() +i, this.getWidth() -i)){
			movesList.add(new Square(this.getHeigth()+i, this.getWidth()-i));
			i++;
		}
		if (this.isOpponent(board, this.getHeigth()+i, this.getWidth()-i)){
			movesList.add(new Square(this.getHeigth()+i, this.getWidth()-i));
		}
		i = 1;
		//Mouvement en bas à gauche
		while (this.getHeigth() -i>0 && this.getWidth() -i>0 && board.isEmpty(this.getHeigth() -i, this.getWidth() -i)){
			movesList.add(new Square(this.getHeigth()-i, this.getWidth()-i));
			i++;
		}
		if (this.isOpponent(board, this.getHeigth()-i, this.getWidth()-i)){
			movesList.add(new Square(this.getHeigth()-i, this.getWidth()-i));
		}
		i = 1;
		//Mouvement en bas à droite
		while (this.getHeigth() -i>0 && this.getWidth() +i<9 && board.isEmpty(this.getHeigth() -i, this.getWidth() +i)){
			movesList.add(new Square(this.getHeigth()-i, this.getWidth()+i));
			i++;
		}
		if (this.isOpponent(board, this.getHeigth()-i, this.getWidth()+i)){
			movesList.add(new Square(this.getHeigth()-i, this.getWidth()+i));
		}
		return movesList;
	}
	
	public ArrayList<Square> possibleMovesDroit(Board board){
		ArrayList<Square> movesList = new ArrayList<Square>();
		int i = 1;
		//Mouvement en haut
		while (this.getHeigth() +i <9 && board.isEmpty(this.getHeigth()+i, this.getWidth())){
			movesList.add(new Square(this.getHeigth()+i, this.getWidth()));
			i++;
		}
		if (this.isOpponent(board, this.getHeigth()+i, this.getWidth())){
			movesList.add(new Square(this.getHeigth()+i, this.getWidth()));
		}
		i = 1;
		//Mouvement en bas
		while (this.getHeigth() -i >0 && board.isEmpty(this.getHeigth()-i, this.getWidth())){
			movesList.add(new Square(this.getHeigth()-i, this.getWidth()));
			i++;
		}
		if (this.isOpponent(board, this.getHeigth()-i, this.getWidth())){
			movesList.add(new Square(this.getHeigth()-i, this.getWidth()));
		}
		i = 1;
		//Mouvement à gauche
		while (this.getWidth()-i>0 && board.isEmpty(this.getHeigth(), this.getWidth()-i)){
			movesList.add(new Square(this.getHeigth(), this.getWidth()-i));
			i++;
		}
		if (this.isOpponent(board, this.getHeigth(), this.getWidth()-i)){
			movesList.add(new Square(this.getHeigth(), this.getWidth()-i));
		}
		i = 1;
		//Mouvement à droite
		while (this.getWidth()+i<9 && board.isEmpty(this.getHeigth(), this.getWidth()+i)){
			movesList.add(new Square(this.getHeigth(), this.getWidth()+i));
			i++;
		}
		if (this.isOpponent(board, this.getHeigth(), this.getWidth()+i)){
			movesList.add(new Square(this.getHeigth(), this.getWidth()+i));
		}
		return movesList;
	}

	public boolean isKing(){
		if (this instanceof King){
			return true;
		}
		return false;
	}
	
	abstract ArrayList<Square> possibleMoves(Board board);
	
	
}
