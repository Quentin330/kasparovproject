
public class TestFonctions {

	public static String affichageBoard(Board b){
		String s = "";
		Piece p;
		for (int i = 8; i > 0; --i){
			s+="|";
			for (int j = 1; j < 9; ++j){
				if (b.isEmpty(i, j))
					s += "  ";
				else {
					p = b.getPiece(i,j);
					if (p.isWhite()){
						s+="W";
					}
					else{
						s+="B";
					}
					if (p instanceof Pawn)
						s += "P";
					else if (p instanceof Rook)
						s += "T";
					else if (p instanceof Knight)
						s += "C";
					else if (p instanceof Bishop)
						s += "F";
					else if (p instanceof Queen)
						s += "Q";
					else if (p instanceof King)
						s += "K";
				}
				s+="|";
			}
			s += "\n";
		}
		return s;
	}

	public static void main(String argv[]){
		Board b = new Board();
		System.out.println(affichageBoard(b));
		try{
			b.deplacerPiece("C2", "C4");
			System.out.println(affichageBoard(b));
			b.deplacerPiece("E7", "E5");
			System.out.println(affichageBoard(b));
			b.deplacerPiece("D1", "A4");
			System.out.println(affichageBoard(b));
		}
		catch (NonPossibleMoveException e) {
			e.printStackTrace();
		}
	}

}
