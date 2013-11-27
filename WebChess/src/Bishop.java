import java.util.ArrayList;

/**
 * Instance de la pièce représentant le Fou.
 */
public class Bishop extends Piece {

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
	public Bishop(String color, int row, int column) {
		this.setNom("Bishop");
		this.setColor(color);
		this.setRow(row);
		this.setColumn(column);
		if (color.equals("black"))
			this.setShortcut("f");
		else
			this.setShortcut("F");
	}

	/**
	 * Retourne la liste des coups possibles du fou
	 * (sans prendre en considération les coups qui
	 * peuvent mettre son roi en échec).
	 */
	public ArrayList<Square> possibleMoves(Board board) {
		ArrayList<Square> movesList = new ArrayList<Square>();
		if (this.isDead())
			return movesList;
		return this.possibleMovesDiagonale(board);
	}

	/**
	 * Retourne un clone de la pièce.
	 */
	protected Bishop clone() {
		return new Bishop( this.getColor(), this.getRow(), this.getColumn());
	}
	
}
