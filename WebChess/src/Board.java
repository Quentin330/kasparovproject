import java.util.ArrayList;
import java.util.Iterator;


public class Board {
	
	//private Square[] historique_;
	private Piece[] pieces;

	public Piece[] getPieces() {
		return pieces;
	}
	
	public Board(){
		this.pieces = new Piece[32];
		this.pieces[0] = new Rook("black", 8, 1);
		this.pieces[1] = new Knight("black", 8, 2);
		this.pieces[2] = new Bishop("black", 8, 3);
		this.pieces[3] = new Queen("black", 8, 4);
		this.pieces[4] = new King("black", 8, 5);
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
		this.pieces[28] = new King("white", 1, 5);
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
	
	
}
