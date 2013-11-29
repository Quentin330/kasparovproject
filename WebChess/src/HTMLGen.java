import java.util.ArrayList;
import java.util.Iterator;

/**
 * Classe servant � g�n�rer le code HTML de la page de jeu
 * 
 */
public class HTMLGen {

	/**
	 * Liste des coups possibles
	 */
	private ArrayList<Square> possibleMoves;

	/**
	 * Header statique
	 */
	private static String head = "<html>\n"
			+ "<head>\n"
			+ "	<meta http-equiv=\"content-type\" content=\"text/html;charset=utf-8\" />\n"
			+ "	<link rel=\"stylesheet\" href=\"stylesheet.css\" type=\"text/css\" />\n"
			+ "	<link rel=\"shortcut icon\" type=\"image/x-icon\" href=\"favicon.ico\">\n"
			+ "	<title>WebChess Kasparov</title>\n" + "</head>\n" + "<body>\n";

	/**
	 * Chaine de caract�re contenant le corps de la page
	 */
	private String body;

	/**
	 * Chaine de caract�re contenant la bani�re gauche de la page
	 */
	private String left = "";

	/**
	 * partie du HTML contenant les boutons UNDO et REDO
	 */
	private String boutonsUndoRedo = "";

	/**
	 * partie du HTML contenant la liste des coups
	 */
	private String listeCoups = "";

	/**
	 * partie du HTML contenant le cimeti�re
	 */
	private String eatenPieces = "";

	/**
	 * Partie du HTML contenant les messages d'�chec et pat et le choix des pi�ces pour la promotion
	 */
	private String legende = "";

	/**
	 * Fin de la page HTML (1e partie)
	 */
	private static String bottom1 = "<div id=\"basdepage\">Cette page vous a été presentée avec honneur par Andrea et Quentin.<BR>Nous vous souhaitons bonne chance dans votre partie.</div>\n</body>\n</html>";

	/**
	 * Constructeur du HTMLGen permettant de remplir les diff�rentes chaines de caract�re en fonction de l'�tat du plateau et des sauvegardes
	 * @param b le plateau de jeu
	 * @param saves la liste des sauvegardes
	 * @throws OutOfBoardException
	 * @throws NonPossibleMoveException
	 */
	public HTMLGen(Board b, ArrayList<String> saves, String partieASupprimer)
			throws OutOfBoardException, NonPossibleMoveException {
		Boolean promotion = false;
		if (b.getNumeroCoupMax() > b.getNumeroCoup()) {
			Coup c = b.getListeCoups().get(b.getNumeroCoup());
			if (c.getIsPromotion() && c.getMovedPiece() instanceof Pawn)
				promotion = true;
		}
		Boolean finPartie = this.remplirOptions(b, promotion, partieASupprimer);
		this.getLeft(b, saves);
		Boolean nonCliquable = (finPartie || promotion);
		this.remplirListe(b);
		this.remplirEatenPieces(b);
		if (!b.getSelectedCase().equals("00"))
			this.possibleMoves = b.getPiece(b.getSelectedCase())
			.possibleMovesSE(b);
		this.body = "<BR><BR>" + "<form>\n" + "<table align=center>\n"
				+ "	<tr>\n" + "		<td class=\"corner\"></td>\n"
				+ "		<td class=\"border\">A</td>\n"
				+ "		<td class=\"border\">B</td>\n"
				+ "		<td class=\"border\">C</td>\n"
				+ "		<td class=\"border\">D</td>\n"
				+ "		<td class=\"border\">E</td>\n"
				+ "		<td class=\"border\">F</td>\n"
				+ "		<td class=\"border\">G</td>\n"
				+ "		<td class=\"border\">H</td>\n"
				+ "		<td class=\"corner\"></td>\n" + "	</tr>\n";
		for (int i = 8; i > 0; --i) {
			this.body += "</tr>\n";
			this.body += "<td class=\"border\">" + i + "</td>\n";
			for (int j = 1; j < 9; ++j)
				this.body += printPiece(i, j, b, nonCliquable) + "\n";
			this.body += "<td class=\"border\">" + i + "</td>\n";
			this.body += "<tr>\n";
		}
		this.body += "	<tr>\n" + "		<td class=\"corner\"></td>\n"
				+ "		<td class=\"border\">A</td>\n"
				+ "		<td class=\"border\">B</td>\n"
				+ "		<td class=\"border\">C</td>\n"
				+ "		<td class=\"border\">D</td>\n"
				+ "		<td class=\"border\">E</td>\n"
				+ "		<td class=\"border\">F</td>\n"
				+ "		<td class=\"border\">G</td>\n"
				+ "		<td class=\"border\">H</td>\n"
				+ "		<td class=\"corner\"></td>\n" + "	</tr>\n" + "</table>\n"
				+ "</form>\n";

	}

	/**
	 * getter de la chaine de caract�re head
	 * 
	 * @return head
	 */
	public static String getHead() {
		return head;
	}

	/**
	 * setter de la chaine de caract�re head
	 * 
	 * @param head
	 */
	public static void setHead(String head) {
		HTMLGen.head = head;
	}

	/**
	 * getter de la chaine de caract�re bottom1
	 * 
	 * @return bottom1
	 */
	public static String getBottom1() {
		return bottom1;
	}

	/**
	 * setter de la chaine de caract�re bottom1
	 * 
	 * @param bottom1
	 */
	public static void setBottom1(String bottom1) {
		HTMLGen.bottom1 = bottom1;
	}

	/**
	 * Fonction permettant l'acc�s au code html de la page compl�te
	 * 
	 * @return chaine de caract�re contenant la page enti�re en html
	 */
	public String getPage() {
		return HTMLGen.getHead() + this.boutonsUndoRedo + this.left + this.body
				+ this.legende + HTMLGen.getBottom1() +"\n\n";
	}

	/**
	 * Fonction permettant de remplir la partie de la page contenant la pi�ce dans le plateau
	 * 
	 * @param row
	 * @param column
	 * @param b
	 * @return chaine de caract�re contenant le nom de la pi�ce et son raccourci
	 */
	public String getNomPiece(int row, int column, Board b) {
		if (b.isEmpty(row, column))
			return "blank.svg\" alt=\" \"";
		Piece p = b.getPiece(row, column);
		return getNomPiece(p);
	}

	/**
	 * Fonction permettant de remplir la partie de la page contenant la pi�ce dans le plateau
	 * 
	 * @param p
	 * @return chaine de caract�re contenant le nom de la pi�ce et son raccourci
	 */
	public String getNomPiece(Piece p) {
		String nom = "";
		nom += p.getColor() + p.getNom() + ".svg\" alt=\"" + p.getShortcut()
				+ "\"";
		return nom;
	}

	/**
	 * Fonction permettant l'acc�s du code HTML complet d'affichage d'une pi�ce
	 * 
	 * @param row
	 * @param column
	 * @param b
	 * @return code HTML complet d'affichage d'une pi�ce
	 */
	public String printPiece(int row, int column, Board b, Boolean promotion) {
		String pieceLine = "<td class=\"";
		int somme = row + column;
		int wkRow = b.getWhiteKing().getRow();
		int wkColumn = b.getWhiteKing().getColumn();
		int bkRow = b.getBlackKing().getRow();
		int bkColumn = b.getBlackKing().getColumn();
		if (((b.isEchec("white", wkRow, wkColumn) && row == wkRow && column == wkColumn) || (b
				.isEchec("black", bkRow, bkColumn) && row == bkRow && column == bkColumn))
				&& (!nameCase(row, column).equals(b.getSelectedCase())))
			pieceLine += "echec";
		else {
			if (somme % 2 == 1)
				pieceLine += "even";
			else
				pieceLine += "odd";
		}
		if (!b.getSelectedCase().equals("00")) {
			if (isPlayable(row, column, b) && !promotion) {
				pieceLine += "Select\"><input type=\"image\" name=\"to"
						+ nameCase(row, column) + "\" src=\"pieces/"
						+ getNomPiece(row, column, b) + " width=32 /></td>";
				return pieceLine;
			}
		}
		if (b.isEmpty(row, column)) {
			pieceLine += "\" ><img src=\"pieces/blank.svg\" alt=\" \" width=32 /></td>";
			return pieceLine;
		}
		Piece p = b.getPiece(row, column);
		if (p.getColor().equals(b.getCurrentPlayer()) && !promotion) {
			if (nameCase(row, column).equals(b.getSelectedCase())) {
				pieceLine += "CurrentSelect\"><input type=\"image\" name=\""
						+ nameCase(row, column) + "\" src=\"pieces/"
						+ getNomPiece(row, column, b) + " width=32 /></td>";
				return pieceLine;
			} else {
				pieceLine += "\"><input type=\"image\" name=\""
						+ nameCase(row, column) + "\" src=\"pieces/"
						+ getNomPiece(row, column, b) + " width=32 /></td>";
				return pieceLine;
			}
		}
		pieceLine += "\"><img src=\"pieces/" + getNomPiece(row, column, b)
				+ " width=32 /></td>";
		return pieceLine;
	}

	/**
	 * Fonction permettant l'acc�s au nom d'une case � partir de ses coordonn�es
	 * 
	 * @param row
	 * @param column
	 * @return nom d'une case
	 */
	public String nameCase(int row, int column) {
		String name = "";
		switch (column) {
		case 1:
			name += 'A';
			break;
		case 2:
			name += 'B';
			break;
		case 3:
			name += 'C';
			break;
		case 4:
			name += 'D';
			break;
		case 5:
			name += 'E';
			break;
		case 6:
			name += 'F';
			break;
		case 7:
			name += 'G';
			break;
		case 8:
			name += 'H';
			break;
		}
		name += row;
		return name;
	}

	/**
	 * Fonction permettant de savoir si une case est jouable par la pi�ce actuellement selectionn�e
	 * 
	 * @param row
	 * @param column
	 * @param b
	 * @return bool�en
	 */
	public boolean isPlayable(int row, int column, Board b) {
		Iterator<Square> it = this.possibleMoves.iterator();
		while (it.hasNext()) {
			Square s = it.next();
			if (s.isThisSquare(row, column))
				return true;
		}
		return false;
	}

	/**
	 * Fonction remplissant la liste des coups
	 * 
	 * @param b
	 */
	public void remplirListe(Board b) {
		String debut = "<b><font face =\"courier\" size =\"2\">\n";
		String fin = "</font></b>\n";
		String debutFont = "<font color =\"white\">";
		String finFont = "</font>\n";

		this.listeCoups += debut;
		for (int i = 1; i < b.getNumeroCoupMax(); ++i) {
			if (i >= b.getNumeroCoup())
				debutFont = "<font color =\"grey\">\n";
			if (i % 2 == 0)
				this.listeCoups += debutFont + " - "
						+ b.getListeCoups().get(i).afficherCoup() + finFont
						+ "<BR>";
			else {
				this.listeCoups += debutFont;
				if ((i + 1) / 2 < 10)
					this.listeCoups += "0";
				this.listeCoups += (i + 1) / 2 + ". "
						+ b.getListeCoups().get(i).afficherCoup() + finFont;
			}
		}
		this.listeCoups += fin;
	}

	/**
	 * Fonction permettant de remplir le code html de la l�gende et des boutons undo et redo
	 * 
	 * @param b
	 * @param promotion
	 * @return
	 */
	public Boolean remplirOptions(Board b, Boolean promotion, String partieASupprimer) {
		Boolean finPartie = false;
		String player = b.getCurrentPlayer();
		Piece roi = b.getWhiteKing();
		// String colorEnFrancais = "blanc";
		String adversaire = "black";
		if (player.equals("black")) {
			roi = b.getBlackKing();
			adversaire = "white";
			// colorEnFrancais = "noir";
		}
		this.boutonsUndoRedo += "<center>\n";
		if (b.getNumeroCoup() > 1 && !promotion)
			this.boutonsUndoRedo += "<a href=\"?Undo\" class=\"boutonpage\">UNDO</a>\n";
		else
			this.boutonsUndoRedo += "<a class=\"boutonpagegris\">UNDO</a>\n";
		if (b.getNumeroCoup() < b.getNumeroCoupMax() && !promotion)
			this.boutonsUndoRedo += "<a href=\"?Redo\" class=\"boutonpage\">REDO</a>\n";
		else
			this.boutonsUndoRedo += "<a class=\"boutonpagegris\">REDO</a>\n";
		this.boutonsUndoRedo += "</center>\n";

		this.legende += "<BR>\n\n";
		this.legende += "<center>\n";
		if (!partieASupprimer.equals("")){
			this.legende += "<a class=\"bottom\">Delete " + partieASupprimer + " ?</a><BR>" + "<a class=\"bouton404\" href=\"?Suppr=" + partieASupprimer + "\"> YES </a>" + "<a class=\"bouton404\" href=\"?\"> NO </a>";
		}
		else if (promotion)
			this.legende += "<td class=\"bottom\">\n"
					+ "<a href=\"?Rook\"><img src=\"pieces/" + adversaire
					+ "Rook.svg\"   alt=\"T\" width=32 /></a>\n"
					+ "<a href=\"?Knight\"><img src=\"pieces/" + adversaire
					+ "Knight.svg\" alt=\"C\" width=32 /></a>\n"
					+ "<a href=\"?Bishop\"><img src=\"pieces/" + adversaire
					+ "Bishop.svg\" alt=\"F\" width=32 /></a>\n"
					+ "<a href=\"?Queen\"><img src=\"pieces/" + adversaire
					+ "Queen.svg\"  alt=\"D\" width=32 /></a>\n" + "</td>\n";
		else if (b.isEchec(player, roi.getRow(), roi.getColumn())) {
			try {
				if (b.isEchecEtMat(player)) {
					this.legende += "<a class=\"bottom\">CHECKMATE</a>\n";
					finPartie = true;
				} else {
					if (b.getCurrentPlayer().equals("white")){
						this.legende += "<a class=\"bottomwhite\">White</a>\n";
					}
					else{
						this.legende += "<a class=\"bottomblack\">Black</a>\n";
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (b.isPat()) {
			this.legende += "<a class=\"bottom\">STALEMATE</a>\n";
			finPartie = true;
		} else {
			if (b.getCurrentPlayer().equals("white")){
				this.legende += "<a class=\"bottomwhite\">White</a>\n";
			}
			else{
				this.legende += "<a class=\"bottomblack\">Black</a>\n";
			}
		}
		this.legende += "</center><BR>\n\n";
		return finPartie;
	}

	/**
	 * Fonction permettant de remplir le code HTML du cimeti�re de pi�ces
	 * 
	 * @param b
	 */
	public void remplirEatenPieces(Board b) {
		this.eatenPieces += "<div id=\"menuright\">\n";
		this.eatenPieces += "<div id=\"menuhaut\">\n";
		this.eatenPieces += "<font color = \"black\">Black eaten pieces :<BR>\n";
		this.eatenPieces += "<table align=\"center\">";
		ArrayList<Piece> blackEatenPieces = b.getBlackEatenPieces();
		Iterator<Piece> blackIt = blackEatenPieces.iterator();
		int tr = 0;
		while (blackIt.hasNext()) {
			if (tr == 3) {
				this.eatenPieces += "</tr>\n";
				tr = 0;
			} else {
				if (tr == 0)
					this.eatenPieces += "<tr>\n";
				Piece p = blackIt.next();
				this.eatenPieces += "<td><img src=\"pieces/" + getNomPiece(p)
						+ " width=32 /></td>\n";
				tr++;
			}
		}
		if (tr != 0) {
			this.eatenPieces += "</tr>\n";
			tr = 0;
		}
		this.eatenPieces += "</table>";
		this.eatenPieces += "</font>\n";
		this.eatenPieces += "</div>\n";
		this.eatenPieces += "<div id=\"menubas\">\n";
		this.eatenPieces += "<font color = \"white\">White eaten pieces :<BR>\n";
		this.eatenPieces += "<table align=\"center\">";
		ArrayList<Piece> whiteEatenPieces = b.getWhiteEatenPieces();
		Iterator<Piece> whiteIt = whiteEatenPieces.iterator();
		this.eatenPieces += "</tr>\n";
		while (whiteIt.hasNext()) {
			if (tr == 3) {
				this.eatenPieces += "</tr>\n";
				tr = 0;
			} else {
				if (tr == 0)
					this.eatenPieces += "<tr>\n";
				Piece p = whiteIt.next();
				this.eatenPieces += "<td><img src=\"pieces/" + getNomPiece(p)
						+ " width=32 /></td>\n";
				tr++;
			}
		}
		if (tr != 0) {
			this.eatenPieces += "</tr>\n";
			tr = 0;
		}
		this.eatenPieces += "</table>";
		this.eatenPieces += "</font>\n";
		this.eatenPieces += "</div>\n";
		this.eatenPieces += "</div>\n";
	}

	/**
	 * Fonction permettant de remplir le code HTML du bandeau de gauche
	 * 
	 * @param b
	 */
	public void getLeft(Board b, ArrayList<String> saves) {
		this.left += "<div id=\"menu\">\n";
		this.left += "<a class= \"bouton\" href= \"?NewGame\">NEW GAME</a>\n";
		this.left += "<form class= \"deroulant\" action=\"\" method=\"GET\">\n SAVE GAME<BR><BR><input type=\"text\" name=\"Save\"/>\n <input type=\"submit\" value=\"Save Game\"/>\n</form>";
		this.left += "<p class= \"deroulant\">LOAD GAME<BR><BR>";
		Iterator<String> it = saves.iterator();
		while (it.hasNext()) {
			String s = it.next();
			this.left += "<a href= \"?Load=" + s + "\">" + s + "</a><BR><BR>\n";
		}
		this.left += "</p>\n";
		this.left += "<p class= \"deroulantred\">DELETE SAVE<BR><BR>";
		Iterator<String> it2 = saves.iterator();
		while (it2.hasNext()) {
			String s2 = it2.next();
			this.left += "<a href= \"?delete=" + s2 + "\">" + s2
					+ "</a><BR><BR>\n";
		}
		this.left += "</p>\n";
		this.remplirListe(b);
		this.remplirEatenPieces(b);
		this.left += "<a class= \"deroulant\">\nMOVE LIST<BR><BR>\n";
		this.left += this.listeCoups;
		this.left += "</a>\n";
		this.left += "<a class= \"deroulant\">\nCIMETERY<BR><BR>\n";
		this.left += eatenPieces;
		this.left += "</a>\n";
		this.left += "</div>\n";
	}

}