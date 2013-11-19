
public class Coup {
	
	private Piece movedPiece;
	private Boolean hasEaten;
	private Piece eatenPiece;
	private Square caseDepart;
	private Square caseArrivee;
	private int numeroCoup;
	
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
	
	public Coup(){
		this.hasEaten = false;
	}
	
	public String afficherCoup(){
		String s = "";
		s += this.caseDepart.getNomCase();
		if (this.hasEaten){
			s += "x";
		}
		else{
			s += "-";
		}
		s += this.caseArrivee.getNomCase();
		return s;
	}
	
}
