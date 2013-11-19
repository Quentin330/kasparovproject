import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Classe instanciant une partie, à savoir le plateau, 
 * l'état et la position de l'ensemble des pièces de la partie.
 */
public class Board {

	//private Square[] historique_;

	/**
	 * Tableau de l'ensemble des pièces de la partie.
	 */
	private Piece[] pieces;

	/**
	 * Instance du Roi noir du joueur.
	 */
	private King blackKing;

	/**
	 * Instance du roi blanc du joueur.
	 */
	private King whiteKing;

	/**
	 * Chaîne de caractère indiquant à qui est la main.
	 * Ses valeurs possibles sont "Black" ou "White".
	 */
	private String currentPlayer;

	/**
	 * Chaîne de caractère décrivant la case en surbrillance,
	 * de "A" à "H" pour les abscisses 
	 * et de 1 à 8 pour les ordonées.
	 */
	private String selectedCase;

	private int numeroCoup;

	private int numeroCoupMax;

	private HashMap<Integer, Coup> listeCoups;

	public HashMap<Integer, Coup> getListeCoups() {
		return listeCoups;
	}

	public void ajouterCoup (Coup coup){
		for (int i=this.numeroCoup; i<this.numeroCoupMax; ++i){
			listeCoups.remove(i);
		}
		listeCoups.put(this.numeroCoup, coup);
		this.coupSuivant();
	}

	public void annulerCoup(){
		this.numeroCoup --;
		this.annulerCoup(this.listeCoups.get(this.numeroCoup));
		this.nextPlayer();
	}

	private void annulerCoup(Coup coup){
		if (coup.getIsGrandRoque() || coup.getIsPetitRoque()){
			if (coup.getIsGrandRoque()){
				coup.getEatenPiece().setColumn(1);
			}
			else if (coup.getIsPetitRoque()){
				coup.getEatenPiece().setColumn(8);		
			}
			coup.getEatenPiece().moveOnce(false);
			coup.getMovedPiece().moveOnce(false);
		}
		else if (coup.getHasEaten()){
			if (coup.getIsPriseEnPassant()){
				coup.getEatenPiece().setRow(coup.getCaseDepart().getRow());
			}
			else{
				coup.getEatenPiece().setRow(coup.getCaseArrivee().getRow());
			}
			coup.getEatenPiece().setColumn(coup.getCaseArrivee().getColumn());
		}
		coup.getMovedPiece().setRow(coup.getCaseDepart().getRow());
		coup.getMovedPiece().setColumn(coup.getCaseDepart().getColumn());
	}

	public void coupSuivant(){
		this.numeroCoup += 1;
		this.numeroCoupMax = this.numeroCoup;
	}

	public void coupPrecedent(){
		this.numeroCoup -= 1;
	}


	public int getNumeroCoup() {
		return numeroCoup;
	}

	public int getNumeroCoupMax() {
		return numeroCoupMax;
	}

	public void setNumeroCoup(int numeroCoup) {
		this.numeroCoup = numeroCoup;
	}

	/**
	 * Getteur indiquant à qui est la main.
	 * @return	"Black" ou "White" selon à qui le tour.
	 */
	public String getCurrentPlayer() {
		return currentPlayer;
	}

	/**
	 * Getteur indiquant la case en surbrillance.
	 * @return	Chaine de caractère de la case à évaluer,
	 * 			de "A" à "H" pour les abscisses et de 
	 * 			1 à 8 pour les ordonnées.
	 */
	public String getSelectedCase() {
		return selectedCase;
	}

	/**
	 * Setteur indiquant la case en surbrillance.
	 * @param 	Chaine de caractère de la case,
	 * 			de "A" à "H" pour les abscisses et de 
	 * 			1 à 8 pour les ordonnées.
	 */
	public void setSelectedCase(String selectedCase) {
		this.selectedCase = selectedCase;
	}

	/**
	 * TODO
	 * @return
	 */
	public Piece[] getPieces() {
		return pieces;
	}

	/**
	 * TODO
	 * @return
	 */
	public King getBlackKing() {
		return blackKing;
	}

	/**
	 * TODO
	 * @param blackKing
	 */
	public void setBlackKing(King blackKing) {
		this.blackKing = blackKing;
	}

	/**
	 * TODO
	 * @return
	 */
	public King getWhiteKing() {
		return whiteKing;
	}

	/**
	 * TODO
	 * @param whiteKing
	 */
	public void setWhiteKing(King whiteKing) {
		this.whiteKing = whiteKing;
	}

	/**
	 * TODO
	 */
	public void nextPlayer() {
		if (this.currentPlayer.equals("black"))
			this.currentPlayer = "white";
		else
			this.currentPlayer = "black";
	}

	/**
	 * TODO
	 * @param pieces
	 * @param blackKing
	 * @param whiteKing
	 */
	public Board(Piece[] pieces, King blackKing, King whiteKing) {
		this.listeCoups = new HashMap<Integer, Coup>();
		this.numeroCoup = 1;
		this.selectedCase = "00";
		this.currentPlayer = "white";
		this.pieces = new Piece[32];
		for (int i = 0; i < 32; ++i)
			this.pieces[i] = pieces[i].clone();
		this.blackKing = blackKing.clone();
		this.whiteKing = whiteKing.clone();
	}

	/**
	 * TODO
	 */
	public Board clone(){
		return new Board(this.pieces, this.blackKing, this.whiteKing);
	}

	/**
	 * TODO
	 */
	public Board(){
		this.listeCoups = new HashMap<Integer, Coup>();
		this.numeroCoup = 1;
		this.selectedCase = "00";
		this.currentPlayer = "white";
		this.blackKing 	= new King	("black", 8, 5);
		this.whiteKing 	= new King	("white", 1, 5);
		this.pieces = new Piece[32];
		this.pieces[0] = new Rook	("black", 8, 1);
		this.pieces[1] = new Knight	("black", 8, 2);
		this.pieces[2] = new Bishop	("black", 8, 3);
		this.pieces[3] = new Queen	("black", 8, 4);
		this.pieces[4] = this.blackKing;
		this.pieces[5] = new Bishop	("black", 8, 6);
		this.pieces[6] = new Knight	("black", 8, 7);
		this.pieces[7] = new Rook	("black", 8, 8);
		for (int i = 0; i<8; ++i) {
			this.pieces[i+8] 	= new Pawn("black", 7, i+1);
			this.pieces[i+16] 	= new Pawn("white", 2, i+1);
		}
		this.pieces[24] = new Rook	("white", 1, 1);
		this.pieces[25] = new Knight("white", 1, 2);
		this.pieces[26] = new Bishop("white", 1, 3);
		this.pieces[27] = new Queen	("white", 1, 4);
		this.pieces[28] = this.whiteKing;
		this.pieces[29] = new Bishop("white", 1, 6);
		this.pieces[30] = new Knight("white", 1, 7);
		this.pieces[31] = new Rook	("white", 1, 8);
	}

	/**
	 * TODO
	 * @param row
	 * @param column
	 * @return
	 */
	public boolean isEmpty(int row, int column){
		for (int i=0; i<32; ++i){
			if (this.pieces[i].getRow()==row){
				if (this.pieces[i].getColumn()==column){
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * TODO
	 * @param row
	 * @param column
	 * @return
	 */
	public boolean isWhite(int row, int column){
		for (int i=0; i<32; ++i)
			if (this.pieces[i].getRow()==row && this.pieces[i].getColumn()==column)
				return this.pieces[i].isWhite();
		return false;
	}

	/**
	 * TODO
	 * @param row
	 * @param column
	 * @return
	 */
	public boolean isBlack(int row, int column){
		for (int i=0; i<32; ++i)
			if (this.pieces[i].getRow()==row && this.pieces[i].getColumn()==column)
				return this.pieces[i].isBlack();
		return false;
	}

	/**
	 * TODO
	 * @param color
	 * @return
	 */
	public ArrayList<Square> echec(String color){
		ArrayList<Square> echecList = new ArrayList<Square>();
		if (color.equals("black"))
			for (int i=0; i<32; ++i)
				if (this.pieces[i].isWhite())
					echecList.addAll(this.pieces[i].possibleMoves(this));
		if (color.equals("white"))
			for (int i=0; i<32; ++i)
				if (this.pieces[i].isBlack())
					echecList.addAll(this.pieces[i].possibleMoves(this));
		return echecList;
	}

	/**
	 * TODO
	 * @param color
	 * @param row
	 * @param column
	 * @return
	 */
	public boolean isEchec(String color, int row, int column){
		ArrayList<Square> echecList = this.echec(color);
		return this.isEchec(echecList, row, column);
	}

	/**
	 * TODO
	 * @param echecList
	 * @param row
	 * @param column
	 * @return
	 */
	public boolean isEchec(ArrayList<Square> echecList, int row, int column) {
		Iterator<Square> it = echecList.iterator();
		while (it.hasNext()){
			Square s = it.next();
			if (s.isThisSquare(row, column))
				return true;
		}
		return false;
	}

	/**
	 * TODO
	 * @param row
	 * @param column
	 * @return
	 */
	public Piece getPiece(int row, int column) {
		assert (!this.isEmpty(row, column));
		int i = 0;
		while(i<32 && 
				!(this.getPieces()[i].getRow()== row && 
				this.getPieces()[i].getColumn() == column))
			i++;
		return this.getPieces()[i];
	}

	/**
	 * TODO
	 * @param row1
	 * @param column1
	 * @param row2
	 * @param column2
	 * @throws OutOfBoardException
	 * @throws NonPossibleMoveException
	 */
	public void deplacerPiece(int row1, int column1, int row2, int column2) 
			throws OutOfBoardException, NonPossibleMoveException{
		this.getPiece(row1, column1).deplacerPiece(this, row2, column2);
	}

	public void retablirCoup() throws OutOfBoardException, NonPossibleMoveException{
		int max = this.numeroCoupMax;
		HashMap<Integer, Coup> hash = new HashMap<Integer, Coup>();
		for (int i=this.numeroCoup; i<this.numeroCoupMax; ++i){
			hash.put(i, this.listeCoups.get(i));
		}
		this.deplacerPiece(this.listeCoups.get(this.numeroCoup).getCaseDepart().getNomCase(), this.listeCoups.get(this.numeroCoup).getCaseArrivee().getNomCase());
		this.numeroCoupMax = max;
		for (int i=this.numeroCoup; i<this.numeroCoupMax; ++i){
			this.listeCoups.put(i, hash.get(i));
		}
	}

	/**
	 * TODO
	 * @param caseDepart
	 * @param caseArrivee
	 * @throws OutOfBoardException
	 * @throws NonPossibleMoveException
	 */
	public void deplacerPiece(String caseDepart, String caseArrivee) 
			throws OutOfBoardException, NonPossibleMoveException{
		assert(!(caseDepart.charAt(0)<'A') && 
				!(caseDepart.charAt(0)>'H') && 
				!(caseDepart.charAt(1)<'1') && 
				!(caseDepart.charAt(1)>'8') && 
				!(caseArrivee.charAt(0)<'A') && 
				!(caseArrivee.charAt(0)>'H') && 
				!(caseArrivee.charAt(1)<'1') && 
				!(caseArrivee.charAt(1)>'8'));
		int column1 = caseDepart.charAt(0)-'A'+'1'-48;
		int column2 = caseArrivee.charAt(0)-'A'+'1'-48;
		int row1 = caseDepart.charAt(1)-48;
		int row2 = caseArrivee.charAt(1)-48;
		this.deplacerPiece(row1, column1, row2, column2);
	}

	/**
	 * TODO
	 * @param caseJeu
	 * @return
	 * @throws OutOfBoardException
	 * @throws NonPossibleMoveException
	 */
	public Piece getPiece(String caseJeu) throws OutOfBoardException, NonPossibleMoveException{
		assert(!(caseJeu.charAt(0)<'A') && 
				!(caseJeu.charAt(0)>'H') && 
				!(caseJeu.charAt(1)<'1') && 
				!(caseJeu.charAt(1)>'8'));
		int column = caseJeu.charAt(0)-'A'+'1'-48;
		int row = caseJeu.charAt(1)-48;
		return this.getPiece(row, column);
	}

	/**
	 * TODO
	 * @param caseJeu
	 * @return
	 */
	public boolean isEmpty(String caseJeu){
		assert(!(caseJeu.charAt(0)<'A') && 
				!(caseJeu.charAt(0)>'H') && 
				!(caseJeu.charAt(1)<'1') && 
				!(caseJeu.charAt(1)>'8'));
		int column = caseJeu.charAt(0)-'A'+'1'-48;
		int row = caseJeu.charAt(1)-48;
		return this.isEmpty(row, column);
	}
}
