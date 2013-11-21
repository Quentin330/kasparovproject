
public class Coup {
	
	private Piece movedPiece;
	private Boolean hasEaten;
	private Boolean isPetitRoque;
	private Boolean isGrandRoque;
	private Piece eatenPiece;
	private Square caseDepart;
	private Square caseArrivee;
	private int numeroCoup;
	private Boolean isPriseEnPassant;
	private Boolean isPromotion;
	
	//dans le cas d'une promotion le pion devient eatenPiece et la nouvelle piece devient movedPiece
	
	public Boolean getIsPromotion() {
		return isPromotion;
	}
	public void setIsPromotion(Boolean isPromotion) {
		this.isPromotion = isPromotion;
	}
	public Piece getMovedPiece() {
		return movedPiece;
	}
	public void setMovedPiece(Piece movedPiece) {
		this.movedPiece = movedPiece;
	}
	public Boolean getHasEaten() {
		return hasEaten;
	}
	public void setHasEaten(Boolean hasEaten) {
		this.hasEaten = hasEaten;
	}
	public Boolean getIsPetitRoque() {
		return isPetitRoque;
	}
	public void setIsPetitRoque(Boolean isPetitRoque) {
		this.isPetitRoque = isPetitRoque;
	}
	public Boolean getIsGrandRoque() {
		return isGrandRoque;
	}
	public void setIsGrandRoque(Boolean isGrandRoque) {
		this.isGrandRoque = isGrandRoque;
	}
	public Piece getEatenPiece() {
		return eatenPiece;
	}
	public void setEatenPiece(Piece eatenPiece) {
		this.eatenPiece = eatenPiece;
	}
	public Square getCaseDepart() {
		return caseDepart;
	}
	public void setCaseDepart(Square caseDepart) {
		this.caseDepart = caseDepart;
	}
	public Square getCaseArrivee() {
		return caseArrivee;
	}
	public void setCaseArrivee(Square caseArrivee) {
		this.caseArrivee = caseArrivee;
	}
	public int getNumeroCoup() {
		return numeroCoup;
	}
	public void setNumeroCoup(int numeroCoup) {
		this.numeroCoup = numeroCoup;
	}	
	
	public Boolean getIsPriseEnPassant() {
		return isPriseEnPassant;
	}
	public void setIsPriseEnPassant(Boolean isPriseEnPassant) {
		this.isPriseEnPassant = isPriseEnPassant;
	}
	public Coup(){
		this.isPromotion = false;
		this.isPriseEnPassant = false;
		this.hasEaten = false;
		this.isGrandRoque = false;
		this.isPetitRoque = false;
	}
	
	public String afficherCoup(){
		if (this.isPetitRoque){
			return "0-0";
		}
		if (this.isGrandRoque){
			return "0-0-0";
		}
		String s = "";
		s += this.caseDepart.getNomCase();
		if (this.hasEaten){
			s += "x";
		}
		else{
			s += "-";
		}
		s += this.caseArrivee.getNomCase();
		if (this.isPromotion){
			s += "=" + this.movedPiece.getShortcut();
		}
		return s;
	}
	
}
