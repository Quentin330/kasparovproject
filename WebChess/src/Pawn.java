import java.io.Serializable;
import java.util.ArrayList;

/**
 * Instance de la pièce représentant le Pion.
 */
public class Pawn extends Piece implements Serializable{

	/**
	 * Default serial version id
	 */
	private static final long serialVersionUID = 1L;

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
	public Pawn(String color, int row, int column) {
		this.setNom("Pawn");
		this.setColor(color);
		this.setRow(row);
		this.setColumn(column);
		if (color.equals("black"))
			this.setShortcut("p");
		else
			this.setShortcut("P");
		this.setMangeableEnPrisePassant(0);
	}

	/**
	 * Retourne la liste des coups possibles du pion
	 * (sans prendre en considération les coups qui
	 * peuvent mettre son roi en échec).
	 */
	public ArrayList<Square> possibleMoves(Board board) {
		ArrayList<Square> movesList = new ArrayList<Square>();
		if (this.isDead())
			return movesList;
		if (this.isBlack()) {
			if (this.getRow()==7) {
				if (board.isEmpty(6, this.getColumn())) {
					movesList.add(new Square(6, this.getColumn()));
					if (board.isEmpty(5, this.getColumn()))
						movesList.add(new Square(5, this.getColumn()));
				}
				if (this.getColumn()!= 1 && board.isWhite(6, this.getColumn()-1))
					movesList.add(new Square(6, this.getColumn()-1));
				if (this.getColumn()!= 8 && board.isWhite(6, this.getColumn()+1))
					movesList.add(new Square(6, this.getColumn()+1));
			}
			else {
				if (this.getRow()!=1 && board.isEmpty(this.getRow()-1, this.getColumn()))
					movesList.add(new Square(this.getRow()-1, this.getColumn()));
				if (this.getRow()!=1 && this.getColumn()!= 1 && board.isWhite(this.getRow()-1, this.getColumn()-1))
					movesList.add(new Square(this.getRow()-1, this.getColumn()-1));
				if (this.getRow()!=1 && this.getColumn()!= 8 && board.isWhite(this.getRow()-1, this.getColumn()+1))
					movesList.add(new Square(this.getRow()-1, this.getColumn()+1));
			}
		}
		else {
			if (this.getRow()==2) {
				if (board.isEmpty(3, this.getColumn())) {
					movesList.add(new Square(3, this.getColumn()));
					if (board.isEmpty(4, this.getColumn()))
						movesList.add(new Square(4, this.getColumn()));
				}
				if (this.getColumn()!= 1 && board.isBlack(3, this.getColumn()-1))
					movesList.add(new Square(3, this.getColumn()-1));
				if (this.getColumn()!= 8 && board.isBlack(3, this.getColumn()+1))
					movesList.add(new Square(3, this.getColumn()+1));
			}
			else {
				if (this.getRow()!=8 && board.isEmpty(this.getRow()+1, this.getColumn()))
					movesList.add(new Square(this.getRow()+1, this.getColumn()));
				if (this.getRow()!=8 && this.getColumn()!= 1 && board.isBlack(this.getRow()+1, this.getColumn()-1))
					movesList.add(new Square(this.getRow()+1, this.getColumn()-1));
				if (this.getRow()!=8 && this.getColumn()!= 8 && board.isBlack(this.getRow()+1, this.getColumn()+1))
					movesList.add(new Square(this.getRow()+1, this.getColumn()+1));
			}
		}
		//Prise en passant
		if ((this.getColor().equals("black") && this.getRow()==4) ||
				(this.getColor().equals("white") && this.getRow()==5)) {
	
			if (this.getColumn()!=1) {
				if (!board.isEmpty(this.getRow(), this.getColumn()-1)) {
					Piece p = board.getPiece(this.getRow(), this.getColumn()-1);
					if (p instanceof Pawn) {
						if (p.getMangeableEnPrisePassant()==board.getNumeroCoup()) {
							if (this.getColor().equals("black") && board.isEmpty(3, this.getColumn()-1))
								movesList.add(new Square (3, this.getColumn()-1));
							else if (this.getColor().equals("white") && board.isEmpty(6, this.getColumn()-1))
								movesList.add(new Square (6, this.getColumn()-1));
						}
					}
				}
			}
			if (this.getColumn()!=8) {
				if (!board.isEmpty(this.getRow(), this.getColumn()+1)) {
					Piece p = board.getPiece(this.getRow(), this.getColumn()+1);
					if (p instanceof Pawn) {
						if (p.getMangeableEnPrisePassant()==board.getNumeroCoup()){
							if (this.getColor().equals("black") && board.isEmpty(3, this.getColumn()+1))
								movesList.add(new Square (3, this.getColumn()+1));
							else if (this.getColor().equals("white") && board.isEmpty(6, this.getColumn()+1))
								movesList.add(new Square (6, this.getColumn()+1));
						}
					}
				}
			}
		}
		return movesList;
	}

	/**
	 * Retourne un clone de la pièce.
	 */
	protected Pawn clone() {
		Pawn p = new Pawn( this.getColor(), this.getRow(), this.getColumn());
		p.setMangeableEnPrisePassant(this.getMangeableEnPrisePassant());
		return p;
	}
	
}
