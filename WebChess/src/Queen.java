import java.io.Serializable;
import java.util.ArrayList;

/**
 * Instance de la pièce représentant la Dame.
 */
public class Queen extends Piece implements Serializable {

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
	public Queen(String color, int row, int column) {
		this.setNom("Queen");
		this.setColor(color);
		this.setRow(row);
		this.setColumn(column);
		if (color.equals("black"))
			this.setShortcut("d");
		else
			this.setShortcut("D");
	}

	/**
	 * Retourne la liste des coups possibles de la Dame (sans prendre en
	 * considération les coups qui peuvent mettre son roi en échec).
	 */
	public ArrayList<Square> possibleMoves(Board board) {
		ArrayList<Square> movesList = new ArrayList<Square>();
		if (this.isDead())
			return movesList;
		ArrayList<Square> movesList1 = this.possibleMovesDiagonale(board);
		ArrayList<Square> movesList2 = this.possibleMovesDroit(board);
		movesList1.addAll(movesList2);
		return movesList1;
	}

	/**
	 * Retourne un clone de la pièce.
	 */
	protected Queen clone() {
		return new Queen(this.getColor(), this.getRow(), this.getColumn());
	}

}
