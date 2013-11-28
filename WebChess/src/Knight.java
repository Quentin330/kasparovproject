import java.io.Serializable;
import java.util.ArrayList;

/**
 * Instance de la pièce représentant le Cavalier.
 */
public class Knight extends Piece implements Serializable {

	/**
	 * Default serial version id
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructeur de la pièce. Il s'initialise avec la couleur de la pièce, et
	 * doit être positionner aux coordonnées "row" et "column".
	 * 
	 * @param color
	 *            Couleur de la pièce à instancier "white" ou "black".
	 * @param row
	 *            Coordonnée en abscisse.
	 * @param column
	 *            Coordonnée en ordonnée.
	 */
	public Knight(String color, int row, int column) {
		this.setNom("Knight");
		this.setColor(color);
		this.setRow(row);
		this.setColumn(column);
		if (color.equals("black"))
			this.setShortcut("c");
		else
			this.setShortcut("C");
	}

	/**
	 * Retourne la liste des coups possibles du cavalier (sans prendre en
	 * considération les coups qui peuvent mettre son roi en échec).
	 */
	public ArrayList<Square> possibleMoves(Board board) {
		ArrayList<Square> movesList = new ArrayList<Square>();
		if (this.isDead())
			return movesList;
		// haut haut droit
		if (this.getRow() < 7 || this.getColumn() < 8)
			if (!this.isSameColor(board, this.getRow() + 2,
					this.getColumn() + 1))
				movesList.add(new Square(this.getRow() + 2,
						this.getColumn() + 1));
		// haut haut gauche
		if (this.getRow() < 7 || this.getColumn() > 1)
			if (!this.isSameColor(board, this.getRow() + 2,
					this.getColumn() - 1))
				movesList.add(new Square(this.getRow() + 2,
						this.getColumn() - 1));
		// bas bas gauche
		if (this.getRow() > 2 || this.getColumn() > 1)
			if (!this.isSameColor(board, this.getRow() - 2,
					this.getColumn() - 1))
				movesList.add(new Square(this.getRow() - 2,
						this.getColumn() - 1));
		// bas bas droit
		if (this.getRow() > 2 || this.getColumn() < 8)
			if (!this.isSameColor(board, this.getRow() - 2,
					this.getColumn() + 1))
				movesList.add(new Square(this.getRow() - 2,
						this.getColumn() + 1));
		// droit droit haut
		if (this.getRow() < 8 || this.getColumn() < 7)
			if (!this.isSameColor(board, this.getRow() + 1,
					this.getColumn() + 2))
				movesList.add(new Square(this.getRow() + 1,
						this.getColumn() + 2));
		// droit droit bas
		if (this.getRow() > 1 || this.getColumn() < 7)
			if (!this.isSameColor(board, this.getRow() - 1,
					this.getColumn() + 2))
				movesList.add(new Square(this.getRow() - 1,
						this.getColumn() + 2));
		// gauche gauche haut
		if (this.getRow() < 8 || this.getColumn() > 2)
			if (!this.isSameColor(board, this.getRow() + 1,
					this.getColumn() - 2))
				movesList.add(new Square(this.getRow() + 1,
						this.getColumn() - 2));
		// gauche gauche bas
		if (this.getRow() > 1 || this.getColumn() > 2)
			if (!this.isSameColor(board, this.getRow() - 1,
					this.getColumn() - 2))
				movesList.add(new Square(this.getRow() - 1,
						this.getColumn() - 2));
		return movesList;
	}

	/**
	 * Retourne un clone de la pièce.
	 */
	protected Knight clone() {
		return new Knight(this.getColor(), this.getRow(), this.getColumn());
	}

}
