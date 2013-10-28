import java.util.ArrayList;
import java.util.Iterator;


public class Board {
	
	//private Square[] historique_;
	private Piece[] pieces;
	private King blackKing;
	private King whiteKing;

	public Piece[] getPieces() {
		return pieces;
	}
	
	public King getBlackKing() {
		return blackKing;
	}

	public void setBlackKing(King blackKing) {
		this.blackKing = blackKing;
	}

	public King getWhiteKing() {
		return whiteKing;
	}

	public void setWhiteKing(King whiteKing) {
		this.whiteKing = whiteKing;
	}

	public Board(Piece[] pieces, King blackKing, King whiteKing) {
		this.pieces = pieces.clone();
		this.blackKing = blackKing;
		this.whiteKing = whiteKing;
	}

	public Board(){
		this.pieces = new Piece[32];
		this.pieces[0] = new Rook("black", 8, 1);
		this.pieces[1] = new Knight("black", 8, 2);
		this.pieces[2] = new Bishop("black", 8, 3);
		this.pieces[3] = new Queen("black", 8, 4);
		this.blackKing = new King("black", 8, 5);
		this.pieces[4] = this.blackKing;
		this.pieces[5] = new Bishop("black", 8, 6);
		this.pieces[6] = new Knight("black", 8, 7);
		this.pieces[7] = new Rook("black", 8, 8);
		for (int i = 0; i<8; ++i){
			this.pieces[i+8] = new Pawn("black", 7, i+1);
			this.pieces[i+16] = new Pawn("white", 2, i+1);
		}
		this.pieces[24] = new Rook("white", 1, 1);
		this.pieces[25] = new Knight("white", 1, 2);
		this.pieces[26] = new Bishop("white", 1, 3);
		this.pieces[27] = new Queen("white", 1, 4);
		this.whiteKing = new King("white", 1, 5);
		this.pieces[28] = this.whiteKing;
		this.pieces[29] = new Bishop("white", 1, 6);
		this.pieces[30] = new Knight("white", 1, 7);
		this.pieces[31] = new Rook("white", 1, 8);
	}
	
	public boolean isEmpty(int heigth, int width){
		for (int i=0; i<32; ++i){
			if (this.pieces[i].getHeigth()==heigth){
				if (this.pieces[i].getWidth()==width){
					return false;
				}
			}
		}
		return true;
	}
	
	public boolean isWhite(int heigth, int width){
		for (int i=0; i<32; ++i){
			if (this.pieces[i].getHeigth()==heigth){
				if (this.pieces[i].getWidth()==width){
					return this.pieces[i].isWhite();
				}
			}
		}
		return false;
	}
	
	public boolean isBlack(int heigth, int width){
		for (int i=0; i<32; ++i){
			if (this.pieces[i].getHeigth()==heigth){
				if (this.pieces[i].getWidth()==width){
					return this.pieces[i].isBlack();
				}
			}
		}
		return false;
	}

	public ArrayList<Square> echec(String color){
		ArrayList<Square> echecList = new ArrayList<Square>();
		if (color == "black"){
			for (int i=0; i<32; ++i){
				if (this.pieces[i].isWhite()){
					echecList.addAll(this.pieces[i].possibleMoves(this));
				}
			}
		}
		if (color == "white"){
			for (int i=0; i<32; ++i){
				if (this.pieces[i].isBlack()){
					echecList.addAll(this.pieces[i].possibleMoves(this));
				}
			}
		}
		return echecList;
	}
	
	public boolean isEchec(String color, int heigth, int width){
		ArrayList<Square> echecList = this.echec(color);
		return this.isEchec(echecList, heigth, width);
	}
	
	public boolean isEchec(ArrayList<Square> echecList, int heigth, int width){
		Iterator<Square> it = echecList.iterator();
		while (it.hasNext()){
			Square s = it.next();
			if (s.isThisSquare(heigth, width)){
				return true;
			}
		}
		return false;
	}
	
	public Piece getPiece(int heigth, int width){
		assert (!this.isEmpty(heigth, width));
		int i=0;
		while(i<32 && !(this.getPieces()[i].getHeigth()== heigth && this.getPieces()[i].getWidth() == width)){
			i++;
		}
		return this.getPieces()[i];
	}
	
	public void deplacerPiece(int heigth1, int width1, int heigth2, int width2) throws OutOfBoardException, NonPossibleMoveException{
		this.getPiece(heigth1, width1).deplacerPiece(this, heigth2, width2);
	}
	
	public void deplacerPiece(String caseDepart, String caseArrivee) throws OutOfBoardException, NonPossibleMoveException{
		assert(!(caseDepart.charAt(0)<'A') && !(caseDepart.charAt(0)>'H') && !(caseDepart.charAt(1)<'1') && !(caseDepart.charAt(1)>'8') && !(caseArrivee.charAt(0)<'A') && !(caseArrivee.charAt(0)>'H') && !(caseArrivee.charAt(1)<'1') && !(caseArrivee.charAt(1)>'8'));
		int width1 = caseDepart.charAt(0)-'A'+'1'-48;
		int width2 = caseArrivee.charAt(0)-'A'+'1'-48;
		int heigth1 = caseDepart.charAt(1)-48;
		int heigth2 = caseArrivee.charAt(1)-48;
		this.deplacerPiece(heigth1, width1, heigth2, width2);
	}
}
