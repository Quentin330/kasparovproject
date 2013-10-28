import java.util.ArrayList;
import java.util.Iterator;


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

	protected abstract Piece clone();
	
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

	private void estMange(){
		this.heigth = 0;
		this.width = 0;
	}


	public void deplacerPiece(Board board, int heigth, int width) throws OutOfBoardException, NonPossibleMoveException, EchecException{
		int oldHeigth = this.heigth;
		int oldWidth = this.width;
		boolean mange = false;
		Piece pieceMange = new Pawn("blue", 0, 0);
		if ((heigth < 1) || (heigth > 8) || (width < 1) || (width > 8)){
			throw new OutOfBoardException("jeu hors des limites");
		}
		ArrayList<Square> listeCoups = this.possibleMoves(board);
		boolean peutJouer = false;
		Iterator<Square> it = listeCoups.iterator();
		while(it.hasNext()){
			Square s = it.next();
			if (s.isThisSquare(heigth, width)){
				peutJouer = true;
			}
		}
		if (!peutJouer){
			throw new NonPossibleMoveException("coup non possible");
		}
		if (board.isEmpty(heigth, width)){
			this.heigth = heigth;
			this.width = width;
		}
		else{
			mange = true;
			pieceMange = board.getPiece(heigth, width);
			pieceMange.estMange();
			this.heigth = heigth;
			this.width = width;
		}
		if (this.color.equals("black")){
			if (board.isEchec("black", board.getBlackKing().getHeigth(), board.getBlackKing().getWidth())){
				this.heigth = oldHeigth;
				this.width = oldWidth;
				if (mange){
					pieceMange.setHeigth(heigth);
					pieceMange.setWidth(width);
				}
				throw new EchecException("Ce mouvement met votre roi en echec");
			}
		}
		else{
			if (board.isEchec("white", board.getWhiteKing().getHeigth(), board.getWhiteKing().getWidth())){
				this.heigth = oldHeigth;
				this.width = oldWidth;
				if (mange){
					pieceMange.setHeigth(heigth);
					pieceMange.setWidth(width);
				}
				throw new EchecException("Ce mouvement met votre roi en echec");
			}
		}
	}

	abstract ArrayList<Square> possibleMoves(Board board);


}
