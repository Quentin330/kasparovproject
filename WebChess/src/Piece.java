import java.util.ArrayList;
import java.util.Iterator;

/**
 * TODO
 *
 */
abstract public class Piece {

	/**
	 * black or white 
	 */
	private String color;

	/**
	 * position de la pièce en hauteur (de 1 à 8 et 0 si pièce perdue)
	 */
	private int row;

	/**
	 * position de la pièce en largeur (de 1 à 8 et 0 si pièce perdue)
	 */
	private int column;


	/**
	 * TODO
	 */
	protected abstract Piece clone();

	/**
	 * TODO
	 * @param board
	 * @return
	 */
	abstract ArrayList<Square> possibleMoves(Board board);


	/**
	 * TODO
	 * @return
	 */
	public boolean isBlack(){
		return (this.color.equals("black"));
	}
	
	/**
	 * TODO
	 * @return
	 */
	public boolean isWhite(){
		return (this.color.equals("white"));
	}

	/**
	 * TODO
	 * @return
	 */
	public String getColor() {
		return color;
	}

	/**
	 * TODO
	 * @param color
	 */
	public void setColor(String color) {
		this.color = color;
	}

	/**
	 * TODO
	 * @return
	 */
	public int getRow() {
		return row;
	}

	/**
	 * TODO
	 * @param row
	 */
	public void setRow(int row) {
		this.row = row;
	}

	/**
	 * TODO
	 * @return
	 */
	public int getColumn() {
		return column;
	}

	/**
	 * TODO
	 * @param column
	 */
	public void setColumn(int column) {
		this.column = column;
	}

	/**
	 * TODO
	 * @return
	 */
	public boolean isDead(){
		return (this.getRow()==0 && this.getColumn()==0);
	}

	/**
	 * TODO
	 * @param board
	 * @param row
	 * @param column
	 * @return
	 */
	public boolean isOpponent(Board board, int row, int column){
		if (this.isBlack()){
			return board.isWhite(row, column);
		}
		else{
			return board.isBlack(row, column);
		}
	}

	/**
	 * TODO
	 * @param board
	 * @param row
	 * @param column
	 * @return
	 */
	public boolean isSameColor(Board board, int row, int column){
		if (this.isBlack()){
			return board.isBlack(row, column);
		}
		else{
			return board.isWhite(row, column);
		}
	}

	/**
	 * TODO
	 * @param board
	 * @return
	 */
	public ArrayList<Square> possibleMovesDiagonale(Board board){
		ArrayList<Square> movesList = new ArrayList<Square>();
		int i = 1;
		//Mouvement en haut à droite
		while (this.getRow() +i<9 && this.getColumn() +i<9 && 
				board.isEmpty(this.getRow() +i, this.getColumn() +i)){
			movesList.add(new Square(this.getRow()+i, this.getColumn()+i));
			i++;
		}
		if (this.isOpponent(board, this.getRow()+i, this.getColumn()+i)){
			movesList.add(new Square(this.getRow()+i, this.getColumn()+i));
		}
		i = 1;
		//Mouvement en haut à gauche
		while (this.getRow() +i<9 && this.getColumn() -i>0 && 
				board.isEmpty(this.getRow() +i, this.getColumn() -i)){
			movesList.add(new Square(this.getRow()+i, this.getColumn()-i));
			i++;
		}
		if (this.isOpponent(board, this.getRow()+i, this.getColumn()-i)){
			movesList.add(new Square(this.getRow()+i, this.getColumn()-i));
		}
		i = 1;
		//Mouvement en bas à gauche
		while (this.getRow() -i>0 && this.getColumn() -i>0 && 
				board.isEmpty(this.getRow() -i, this.getColumn() -i)){
			movesList.add(new Square(this.getRow()-i, this.getColumn()-i));
			i++;
		}
		if (this.isOpponent(board, this.getRow()-i, this.getColumn()-i)){
			movesList.add(new Square(this.getRow()-i, this.getColumn()-i));
		}
		i = 1;
		//Mouvement en bas à droite
		while (this.getRow() -i>0 && this.getColumn() +i<9 && 
				board.isEmpty(this.getRow() -i, this.getColumn() +i)){
			movesList.add(new Square(this.getRow()-i, this.getColumn()+i));
			i++;
		}
		if (this.isOpponent(board, this.getRow()-i, this.getColumn()+i)){
			movesList.add(new Square(this.getRow()-i, this.getColumn()+i));
		}
		return movesList;
	}

	/**
	 * TODO
	 * @param board
	 * @return
	 */
	public ArrayList<Square> possibleMovesDroit(Board board){
		ArrayList<Square> movesList = new ArrayList<Square>();
		int i = 1;
		//Mouvement en haut
		while (this.getRow() +i <9 && 
				board.isEmpty(this.getRow()+i, this.getColumn())){
			movesList.add(new Square(this.getRow()+i, this.getColumn()));
			i++;
		}
		if (this.isOpponent(board, this.getRow()+i, this.getColumn())){
			movesList.add(new Square(this.getRow()+i, this.getColumn()));
		}
		i = 1;
		//Mouvement en bas
		while (this.getRow() -i >0 && 
				board.isEmpty(this.getRow()-i, this.getColumn())){
			movesList.add(new Square(this.getRow()-i, this.getColumn()));
			i++;
		}
		if (this.isOpponent(board, this.getRow()-i, this.getColumn())){
			movesList.add(new Square(this.getRow()-i, this.getColumn()));
		}
		i = 1;
		//Mouvement à gauche
		while (this.getColumn()-i>0 && 
				board.isEmpty(this.getRow(), this.getColumn()-i)){
			movesList.add(new Square(this.getRow(), this.getColumn()-i));
			i++;
		}
		if (this.isOpponent(board, this.getRow(), this.getColumn()-i)){
			movesList.add(new Square(this.getRow(), this.getColumn()-i));
		}
		i = 1;
		//Mouvement à droite
		while (this.getColumn()+i<9 && 
				board.isEmpty(this.getRow(), this.getColumn()+i)){
			movesList.add(new Square(this.getRow(), this.getColumn()+i));
			i++;
		}
		if (this.isOpponent(board, this.getRow(), this.getColumn()+i)){
			movesList.add(new Square(this.getRow(), this.getColumn()+i));
		}
		return movesList;
	}

	/**
	 * TODO
	 * @return
	 */
	public boolean isKing(){
		if (this instanceof King){
			return true;
		}
		return false;
	}

	/**
	 * TODO
	 */
	private void estMange(){
		this.row = 0;
		this.column = 0;
	}

	/**
	 * TODO
	 * @param board
	 * @param row
	 * @param column
	 * @throws OutOfBoardException
	 * @throws NonPossibleMoveException
	 * @throws EchecException
	 */
	public void deplacerPiece(Board board, int row, int column) 
			throws OutOfBoardException, NonPossibleMoveException, EchecException{

		int oldRow = this.row;
		int oldColumn = this.column;
		boolean mange = false;
		Piece pieceMange = new Pawn("blue", 0, 0);
		if ((row < 1) || (row > 8) || (column < 1) || (column > 8)){
			throw new OutOfBoardException("jeu hors des limites");
		}
		ArrayList<Square> listeCoups = this.possibleMoves(board);
		boolean peutJouer = false;
		Iterator<Square> it = listeCoups.iterator();
		while(it.hasNext()){
			Square s = it.next();
			if (s.isThisSquare(row, column)){
				peutJouer = true;
			}
		}
		if (!peutJouer){
			throw new NonPossibleMoveException("coup non possible");
		}
		if (board.isEmpty(row, column)){
			this.row = row;
			this.column = column;
		}
		else{
			mange = true;
			pieceMange = board.getPiece(row, column);
			pieceMange.estMange();
			this.row = row;
			this.column = column;
		}
		if (this.color.equals("black")){
			if (board.isEchec("black", 
					board.getBlackKing().getRow(), 
					board.getBlackKing().getColumn())){
				this.row = oldRow;
				this.column = oldColumn;
				if (mange){
					pieceMange.setRow(row);
					pieceMange.setColumn(column);
				}
				throw new EchecException("Ce mouvement met votre roi en echec");
			}
		}
		else{
			if (board.isEchec("white", 
					board.getWhiteKing().getRow(), 
					board.getWhiteKing().getColumn())){
				this.row = oldRow;
				this.column = oldColumn;
				if (mange){
					pieceMange.setRow(row);
					pieceMange.setColumn(column);
				}
				throw new EchecException("Ce mouvement met votre roi en echec");
			}
		}
	}

}
