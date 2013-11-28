import java.io.Serializable;
import java.util.ArrayList;

/**
 * Instance de la pièce représentant le Roi.
 */
public class King extends Piece implements Serializable{

	/**
	 * Default serial version id
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructeur de la pièce. Il s'initialise
	 * avec la couleur de la pièce, et doit être
	 * positionner aux coordonnées "row" et "column".
	 * @param	color
	 * 				Couleur de la pièce à instancier "white" ou "black".
	 * @param	row
	 * 				Coordonnée en abscisse.
	 * @param	column
	 * 				Coordonnée en ordonnée.
	 */
	public King(String color, int row, int column) {
		this.setNom("King");
		this.setColor(color);
		this.setRow(row);
		this.setColumn(column);
		if (color.equals("black"))
			this.setShortcut("r");
		else
			this.setShortcut("R");
		this.moveOnce(false);
	}

	/**
	 * Retourne la liste des coups possibles du roi
	 * (sans prendre en considération les coups qui
	 * peuvent le mettre en échec).
	 */
	public ArrayList<Square> possibleMoves(Board board) {
		ArrayList<Square> movesList = new ArrayList<Square>();
		//haut
		if (this.getRow()!=8)
			if (!this.isSameColor(board, this.getRow()+1, this.getColumn()))
				movesList.add(new Square(this.getRow()+1, this.getColumn()));
		//bas
		if (this.getRow()!=1)
			if (!this.isSameColor(board, this.getRow()-1, this.getColumn()))
				movesList.add(new Square(this.getRow()-1, this.getColumn()));
		//gauche
		if (this.getColumn()!=1)
			if (!this.isSameColor(board, this.getRow(), this.getColumn()-1))
				movesList.add(new Square(this.getRow(), this.getColumn()-1));
		//droite
		if (this.getColumn()!=8)
			if (!this.isSameColor(board, this.getRow(), this.getColumn()+1))
				movesList.add(new Square(this.getRow(), this.getColumn()+1));
		//haut droite
		if (this.getRow()!=8 || this.getColumn()!=8)
			if (!this.isSameColor(board, this.getRow()+1, this.getColumn()+1))
				movesList.add(new Square(this.getRow()+1, this.getColumn()+1));
		//haut gauche
		if (this.getRow()!=8 || this.getColumn()!=1)
			if (!this.isSameColor(board, this.getRow()+1, this.getColumn()-1))
				movesList.add(new Square(this.getRow()+1, this.getColumn()-1));
		//bas droite
		if (this.getRow()!=1 || this.getColumn()!=8)
			if (!this.isSameColor(board, this.getRow()-1, this.getColumn()+1))
				movesList.add(new Square(this.getRow()-1, this.getColumn()+1));
		//bas gauche
		if (this.getRow()!=1 || this.getColumn()!=1)
			if (!this.isSameColor(board, this.getRow()-1, this.getColumn()-1))
				movesList.add(new Square(this.getRow()-1, this.getColumn()-1));
		//petit roque
		if (!this.hasMovedOnce()) { 
			if (this.canShortCastle(board, 1))
				movesList.add(new Square(1, 7));
			if (this.canShortCastle(board, 8))
				movesList.add(new Square(8, 7));
		}
		//grand roque
		if (!this.hasMovedOnce()) {
			if (this.canLongCastle(board, 1))
				movesList.add(new Square(1, 3));
			if (this.canLongCastle(board, 8))
				movesList.add(new Square(8, 3));
		}
		return movesList;
	}

	/**
	 * Retourne le booleen remplissant les conditions
	 * nécessaires pour effectuer le petit roque.
	 * @param 	board
	 * 				La partie en cours.
	 * @param 	row
	 * 				Prend la valeur 1 ou 8.
	 * @return	Le booleen remplissant les conditions
	 * 			nécessaires pour effectuer le petit roque.
	 */
	private boolean canShortCastle(Board board, int row) {
		assert (row == 1 || row == 8);
		return this.isSameColor(board, row, 8) &&
				!board.getPiece(row, 8).hasMovedOnce() &&
				board.isEmpty(row, 6) &&
				board.isEmpty(row, 7) &&
				!board.isEchec(this.getColor(), row, 5) &&
				!board.isEchec(this.getColor(), row, 6) &&
				!board.isEchec(this.getColor(), row, 7);
	}
	
	/**
	 * Retourne le booleen remplissant les conditions
	 * nécessaires pour effectuer le grand roque.
	 * @param 	board
	 * 				La partie en cours.
	 * @param 	row
	 * 				Prend la valeur 1 ou 8.
	 * @return	Le booleen remplissant les conditions
	 * 			nécessaires pour effectuer le grand roque.
	 */
	private boolean canLongCastle(Board board, int row) {
		assert (row == 1 || row == 8);
		return this.isSameColor(board, row, 1) &&
				!board.getPiece(row, 1).hasMovedOnce() &&
				board.isEmpty(row, 4) &&
				board.isEmpty(row, 3) &&
				board.isEmpty(row, 2) &&
				!board.isEchec(this.getColor(), row, 5) &&
				!board.isEchec(this.getColor(), row, 4) &&
				!board.isEchec(this.getColor(), row, 3);
	}
	
	/**
	 * Retourne un clone de la pièce.
	 */
	protected King clone() {
		return new King( this.getColor(), this.getRow(), this.getColumn());
	}
	
}
