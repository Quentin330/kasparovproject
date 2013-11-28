import java.io.Serializable;

/**
 * TODO
 */
public class Coup implements Serializable {

	/**
	 * Default serial version id
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * TODO
	 */
	private Piece movedPiece;

	/**
	 * TODO
	 */
	private Piece oldPiece;

	/**
	 * TODO
	 */
	private Boolean hasEaten;

	/**
	 * TODO
	 */
	private Boolean isPetitRoque;

	/**
	 * TODO
	 */
	private Boolean isGrandRoque;

	/**
	 * TODO
	 */
	private Piece eatenPiece;

	/**
	 * TODO
	 */
	private Square caseDepart;

	/**
	 * TODO
	 */
	private Square caseArrivee;

	/**
	 * TODO
	 */
	private int numeroCoup;

	/**
	 * TODO
	 */
	private Boolean isPriseEnPassant;

	/**
	 * TODO
	 */
	private Boolean isPromotion;

	// dans le cas d'une promotion le pion devient eatenPiece et la nouvelle
	// piece devient movedPiece

	/**
	 * TODO
	 * 
	 * @return
	 */
	public Boolean getIsPromotion() {
		return isPromotion;
	}

	/**
	 * TODO
	 * 
	 * @param isPromotion
	 */
	public void setIsPromotion(Boolean isPromotion) {
		this.isPromotion = isPromotion;
	}

	/**
	 * TODO
	 * 
	 * @return
	 */
	public Piece getMovedPiece() {
		return movedPiece;
	}

	/**
	 * TODO
	 * 
	 * @param movedPiece
	 */
	public void setMovedPiece(Piece movedPiece) {
		this.movedPiece = movedPiece;
	}

	/**
	 * TODO
	 * 
	 * @return
	 */
	public Piece getOldPiece() {
		return oldPiece;
	}

	/**
	 * TODO
	 * 
	 * @param oldPiece
	 */
	public void setOldPiece(Piece oldPiece) {
		this.oldPiece = oldPiece;
	}

	/**
	 * TODO
	 * 
	 * @return
	 */
	public Boolean getHasEaten() {
		return hasEaten;
	}

	/**
	 * TODO
	 * 
	 * @param hasEaten
	 */
	public void setHasEaten(Boolean hasEaten) {
		this.hasEaten = hasEaten;
	}

	/**
	 * TODO
	 * 
	 * @return
	 */
	public Boolean getIsPetitRoque() {
		return isPetitRoque;
	}

	/**
	 * TODO
	 * 
	 * @param isPetitRoque
	 */
	public void setIsPetitRoque(Boolean isPetitRoque) {
		this.isPetitRoque = isPetitRoque;
	}

	/**
	 * TODO
	 * 
	 * @return
	 */
	public Boolean getIsGrandRoque() {
		return isGrandRoque;
	}

	/**
	 * TODO
	 * 
	 * @param isGrandRoque
	 */
	public void setIsGrandRoque(Boolean isGrandRoque) {
		this.isGrandRoque = isGrandRoque;
	}

	/**
	 * TODO
	 * 
	 * @return
	 */
	public Piece getEatenPiece() {
		return eatenPiece;
	}

	/**
	 * TODO
	 * 
	 * @param eatenPiece
	 */
	public void setEatenPiece(Piece eatenPiece) {
		this.eatenPiece = eatenPiece;
	}

	/**
	 * TODO
	 * 
	 * @return
	 */
	public Square getCaseDepart() {
		return caseDepart;
	}

	/**
	 * TODO
	 * 
	 * @param caseDepart
	 */
	public void setCaseDepart(Square caseDepart) {
		this.caseDepart = caseDepart;
	}

	/**
	 * TODO
	 * 
	 * @return
	 */
	public Square getCaseArrivee() {
		return caseArrivee;
	}

	/**
	 * TODO
	 * 
	 * @param caseArrivee
	 */
	public void setCaseArrivee(Square caseArrivee) {
		this.caseArrivee = caseArrivee;
	}

	/**
	 * TODO
	 */
	public int getNumeroCoup() {
		return numeroCoup;
	}

	/**
	 * TODO
	 * 
	 * @param numeroCoup
	 */
	public void setNumeroCoup(int numeroCoup) {
		this.numeroCoup = numeroCoup;
	}

	/**
	 * TODO
	 * 
	 * @return
	 */
	public Boolean getIsPriseEnPassant() {
		return isPriseEnPassant;
	}

	/**
	 * TODO
	 * 
	 * @param isPriseEnPassant
	 */
	public void setIsPriseEnPassant(Boolean isPriseEnPassant) {
		this.isPriseEnPassant = isPriseEnPassant;
	}

	/**
	 * TODO
	 */
	public Coup() {
		this.isPromotion = false;
		this.isPriseEnPassant = false;
		this.hasEaten = false;
		this.isGrandRoque = false;
		this.isPetitRoque = false;
	}

	/**
	 * TODO
	 * 
	 * @return
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
