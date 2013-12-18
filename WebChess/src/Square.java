import java.io.Serializable;

/**
 * Implementation d'une case (utile pour faire une fonction qui renvoie les 2 coordonnées d'une case)
 * 
 */
public class Square implements Serializable {

	/**
	 * Default serial version id
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * ordonnée de la case
	 */
	private int row;
	/**
	 * abscisse de la case
	 */
	private int column;

	/**
	 * Getter de l'abscisse
	 * 
	 * @return
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Getter de l'ordonnée
	 * 
	 * @return
	 */
	public int getColumn() {
		return column;
	}

	/**
	 * Constructeur de square
	 * 
	 * @param row
	 * @param column
	 */
	public Square(int row, int column) {
		this.row = row;
		this.column = column;
	}

	/**
	 * Verification que la case est dans le tableau
	 * 
	 * @return true si la case appartient au tableau
	 */
	public Boolean isRealSquare() {
		return (this.row > 0 && this.row < 9 && this.column > 0 && this.column < 9);
	}

	/**
	 * Verifie si la case instanciée a les coordonnées passées en paramètre
	 * 
	 * @param row
	 * @param column
	 * @return
	 */
	public boolean isThisSquare(int row, int column) {
		return (column == this.getColumn() && row == this.row);
	}

	/**
	 * Nom de la case instanciée
	 * 
	 * @return chaine de caractères correspondant au nom de la case
	 */
	public String getNomCase() {
		String nom = new String();
		if (this.getColumn() == 1)
			nom += "A";
		else if (this.getColumn() == 2)
			nom += "B";
		else if (this.getColumn() == 3)
			nom += "C";
		else if (this.getColumn() == 4)
			nom += "D";
		else if (this.getColumn() == 5)
			nom += "E";
		else if (this.getColumn() == 6)
			nom += "F";
		else if (this.getColumn() == 7)
			nom += "G";
		else if (this.getColumn() == 8)
			nom += "H";
		nom += Integer.toString(this.getRow());
		return nom;
	}

}
