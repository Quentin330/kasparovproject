import java.io.Serializable;

/**
 * Classe permettant la sauvegarde d'un coup.
 */
public class Coup implements Serializable {

	/**
	 * Default serial version id
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Pièce en cours de déplacement.
	 */
	private Piece movedPiece;

	/**
	 * Pièce précédente sauvegardée dans le cas d'une promotion. 
	 */
	private Piece oldPiece;

	/**
	 * Booleen vérifiant si une pièce a été mangé pendant un coup.
	 */
	private Boolean hasEaten;

	/**
	 * Booleen verifiant si le coup est un petit roque.
	 */
	private Boolean isPetitRoque;

	/**
	 * Booleen verifiant si le coup est un grand roque.
	 */
	private Boolean isGrandRoque;

	/**
	 * Pièce mangée sauvegardée.
	 */
	private Piece eatenPiece;

	/**
	 * Case de départ pour le déplacement.
	 */
	private Square caseDepart;

	/**
	 * Case d'arrivée pour le déplacement.
	 */
	private Square caseArrivee;

	/**
	 * Numéro du coup.
	 */
	private int numeroCoup;

	/**
	 * Booleen vérifiant si le coup est une prise en passant.
	 */
	private Boolean isPriseEnPassant;

	/**
	 * Booleen vérifiant si le coup est une promotion.
	 */
	private Boolean isPromotion;

	// dans le cas d'une promotion le pion devient eatenPiece et la nouvelle
	// piece devient movedPiece

	/**
	 * Getter de isPromotion.
	 * 
	 * @return vrai si le coup est une promotion.
	 */
	public Boolean getIsPromotion() {
		return isPromotion;
	}

	/**
	 * Setter de isPromotion.
	 * 
	 * @param isPromotion booleen si le coup est une promotion.
	 */
	public void setIsPromotion(Boolean isPromotion) {
		this.isPromotion = isPromotion;
	}

	/**
	 * Getter de movedPiece.
	 * 
	 * @return la pièce en encours de déplacement.
	 */
	public Piece getMovedPiece() {
		return movedPiece;
	}

	/**
	 * Setter de movedPiece.
	 * 
	 * @param movedPiece la pièce en encours de déplacement.
	 */
	public void setMovedPiece(Piece movedPiece) {
		this.movedPiece = movedPiece;
	}

	/**
	 * Getter de oldPiece.
	 * 
	 * @return la pièce précédente (avant le roque).
	 */
	public Piece getOldPiece() {
		return oldPiece;
	}

	/**
	 * Setter de oldPiece
	 * 
	 * @param oldPiece la pièce précédente (avant le roque).
	 */
	public void setOldPiece(Piece oldPiece) {
		this.oldPiece = oldPiece;
	}

	/**
	 * Getter de hasEaten.
	 * 
	 * @return vrai si une pièce a été mangée pendant le coup.
	 */
	public Boolean getHasEaten() {
		return hasEaten;
	}

	/**
	 * Setter de hasEaten.
	 * 
	 * @param hasEaten
	 */
	public void setHasEaten(Boolean hasEaten) {
		this.hasEaten = hasEaten;
	}

	/**
	 * Getter de isPetitRoque.
	 * 
	 * @return vrai si le coup est un petitRoque.
	 */
	public Boolean getIsPetitRoque() {
		return isPetitRoque;
	}

	/**
	 * Setter de isPetitRoque.
	 * 
	 * @param isPetitRoque
	 */
	public void setIsPetitRoque(Boolean isPetitRoque) {
		this.isPetitRoque = isPetitRoque;
	}

	/**
	 * Getter de isGrandRoque.
	 * 
	 * @return
	 */
	public Boolean getIsGrandRoque() {
		return isGrandRoque;
	}

	/**
	 * Setter de isGrandRoque.
	 * 
	 * @param isGrandRoque
	 */
	public void setIsGrandRoque(Boolean isGrandRoque) {
		this.isGrandRoque = isGrandRoque;
	}

	/**
	 * Getter de eatenPiece.
	 * 
	 * @return la pièce mangée.
	 */
	public Piece getEatenPiece() {
		return eatenPiece;
	}

	/**
	 * Setter de eatenPiece.
	 * 
	 * @param eatenPiece
	 */
	public void setEatenPiece(Piece eatenPiece) {
		this.eatenPiece = eatenPiece;
	}

	/**
	 * Getter de caseDepart.
	 * 
	 * @return la case de départ.
	 */
	public Square getCaseDepart() {
		return caseDepart;
	}

	/**
	 * Setter de caseDepart.
	 * 
	 * @param caseDepart
	 */
	public void setCaseDepart(Square caseDepart) {
		this.caseDepart = caseDepart;
	}

	/**
	 * Getter de caseArrivee.
	 * 
	 * @return la case d'arrivée.
	 */
	public Square getCaseArrivee() {
		return caseArrivee;
	}

	/**
	 * Setter de caseArrivee.
	 * 
	 * @param caseArrivee
	 */
	public void setCaseArrivee(Square caseArrivee) {
		this.caseArrivee = caseArrivee;
	}

	/**
	 * Getter du numero du coup.
	 */
	public int getNumeroCoup() {
		return numeroCoup;
	}

	/**
	 * Setter du numero du coup.
	 * 
	 * @param numeroCoup le numero du coup.
	 */
	public void setNumeroCoup(int numeroCoup) {
		this.numeroCoup = numeroCoup;
	}

	/**
	 * Getter de isPriseEnPassant.
	 * 
	 * @return true si le coup est une prise en passant.
	 */
	public Boolean getIsPriseEnPassant() {
		return isPriseEnPassant;
	}

	/**
	 * Setter de isPriseEnPassant.
	 * 
	 * @param isPriseEnPassant
	 */
	public void setIsPriseEnPassant(Boolean isPriseEnPassant) {
		this.isPriseEnPassant = isPriseEnPassant;
	}

	/**
	 * Constructeur du coup.
	 */
	public Coup() {
		this.isPromotion = false;
		this.isPriseEnPassant = false;
		this.hasEaten = false;
		this.isGrandRoque = false;
		this.isPetitRoque = false;
	}

	/**
	 * Affichage du coup.
	 * 
	 * @return 	un string contenant les informations
	 * 			sur son type sa case de départ et sa case d'arrivée.
	 */
	public String afficherCoup() {
		if (this.isPetitRoque)
			return "0-0";
		if (this.isGrandRoque)
			return "0-0-0";
		String s = "";
		s += this.caseDepart.getNomCase();
		if (this.hasEaten)
			s += "x";
		else
			s += "-";
		s += this.caseArrivee.getNomCase();
		if (this.isPromotion)
			s += "=" + this.movedPiece.getShortcut();
		return s;
	}

}
