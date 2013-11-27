import java.util.ArrayList;

/**
 * Instance de la pièce représentant la Tour.
 */
public class Rook extends Piece {
	
	/**
	 * Constructeur de la pièce. Il s'initialise
	 * avec la couleur de la pièce, et doit être
	 * positionner aux coordonnées "row" et "column".
	 * @param 	color
	 * 				Couleur de la pièce à instancier "white" ou "black".
	 * @param 	row
	 * 				Coordonnée en abscisse.
	 * @param 	column
	 * 				Coordonnée en ordonnée.
	 */
	public Rook(String color, int row, int column) {
		this.setNom("Rook");
		this.setColor(color);
		this.setRow(row);
		this.setColumn(column);
		if (color.equals("black"))
			this.setShortcut("t");
		else
			this.setShortcut("T");
		this.moveOnce(false);
	}
	
	/**
	 * Retourne la liste des coups possibles de la
	 * Tour (sans prendre en considération les coups
	 * qui peuvent mettre son roi en échec).
	 */
	public ArrayList<Square> possibleMoves(Board board) {
		ArrayList<Square> movesList = new ArrayList<Square>();
		if (this.isDead()){
			return movesList;
		}
		return this.possibleMovesDroit(board);
	}
	
	/**
	 * Retourne un clone de la pièce.
	 */
	protected Rook clone() {
		return new Rook( this.getColor(), this.getRow(), this.getColumn());
	}

}
