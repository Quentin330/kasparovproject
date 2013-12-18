//import java.util.ArrayList;

/**
 * Classe contenant un main qui teste nos fonctions de jeu en mode console
 * 
 */
public class TestFonctions {

	/**
	 * Affichage du plateau de jeu en console
	 * 
	 * @param b
	 * @return
	 */
	public static String affichageBoard(Board b) {
		String s = "";
		Piece p;
		for (int i = 8; i > 0; --i) {
			s += "|";
			for (int j = 1; j < 9; ++j) {
				if (b.isEmpty(i, j))
					s += "  ";
				else {
					p = b.getPiece(i, j);
					if (p.isWhite()) {
						s += "W";
					} else {
						s += "B";
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
				s += "|";
			}
			s += "\n";
		}
		return s;
	}

	/**
	 * main testant des fonctions en mode console
	 * 
	 * @param argv
	 * @throws NonPossibleMoveException
	 * @throws OutOfBoardException
	 */
	public static void main(String argv[]) throws OutOfBoardException,
			NonPossibleMoveException {

		Board b = new Board();
		// HTMLGen html = new HTMLGen(b);

		// System.out.println(html.getPage());

		System.out.println(affichageBoard(b));
		try {
			b.deplacerPiece("E2", "E4");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(affichageBoard(b));
		try {
			b.deplacerPiece("A7", "A5");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(affichageBoard(b));
		try {
			b.deplacerPiece("F1", "B5");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(affichageBoard(b));
		try {
			b.deplacerPiece("D7", "D5");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(affichageBoard(b));
		try {
			b.deplacerPiece("C7", "C6");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(affichageBoard(b));

	}

}
