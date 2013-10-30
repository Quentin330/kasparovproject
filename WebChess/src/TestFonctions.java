import java.util.ArrayList;

/**
 * 
 * @author aramdria
 *
 */
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

	public static void main(String argv[]) {
		Board b = new Board();
		System.out.println(affichageBoard(b));
		try{
			/*b.deplacerPiece("E2", "E4");
			System.out.println(affichageBoard(b));
			b.deplacerPiece("A7", "A5");
			System.out.println(affichageBoard(b));
			b.deplacerPiece("F1", "B5");
			System.out.println(affichageBoard(b));
			b.deplacerPiece("D7", "D5");
			System.out.println(affichageBoard(b));*/
			
			Piece[] pieces = new Piece[32];
			King roi = new King("black", 8, 5);
			pieces[0] = roi;
			King roiInutile = new King("white", 1, 1);
			pieces[1] = roiInutile;
			Queen reine = new Queen("white", 2, 5);
			pieces[2] = reine;
			Pawn pionNoir = new Pawn("black", 6, 5);
			pieces[3] = pionNoir;
			Bishop fouBlanc = new Bishop("white", 5, 6);
			pieces[4] = fouBlanc;
			Pawn pionMort = new Pawn("yellow", 0, 0);
			for (int i = 5; i<32; ++i){
				pieces[i] = pionMort;
			}
			Board board = new Board(pieces, roi, roiInutile);
			board.deplacerPiece("E6", "F5");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
