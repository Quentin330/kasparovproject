import java.util.ArrayList;
import java.util.Iterator;


// Pour creer des menus sur les cotés, voir http://pbnaigeon.developpez.com/tutoriel/CSS-HTML/mise-en-page-CSS/
// Faire un menu sur la droite séparé en 2 horizontalement pour les pieces mangées blanches et les pieces mangées noires.

/**
 * TODO
 *
 */
public class HTMLGen {

	/**
	 * TODO
	 */
	private String body;

	/**
	 * TODO
	 */
	private ArrayList<Square> possibleMoves;

	/**
	 * TODO
	 */
	private static String head = "<html>\n"
			+ "<head>\n"
			+ "<meta http-equiv=\"content-type\" content=\"text/html;charset=utf-8\" />\n"
			+ "<link rel=\"stylesheet\" href=\"stylesheet.css\" type=\"text/css\" />\n"
			+ "<link rel=\"shortcut icon\" type=\"image/x-icon\" href=\"favicon.ico\">\n"
			+ "<title>\n"
			+ "	WebChess Kasparov\n</title>\n"
			+ "</head>\n"
			+ "<body>\n";

	/**
	 * TODO
	 */
	private static String bottom1 = "</body>\n";

	/**
	 * TODO
	 */
	private String options = "";
	
	private String options2 = "";

	private String listeCoups = "";
	
	private String eatenPieces = "";

	/**
	 * TODO
	 */
	private static String bottom2 = "</html>";

	/**
	 * TODO
	 * @return
	 */
	public String getPage() {
		return HTMLGen.getHead()
				+ this.options
				+ this.listeCoups
				+ this.eatenPieces
				+ this.options2
				+ this.body
				+ HTMLGen.getBottom1()
				+ HTMLGen.getBottom2();
	}

	/**
	 * TODO
	 * @param b
	 * @throws OutOfBoardException
	 * @throws NonPossibleMoveException
	 */
	public HTMLGen(Board b) throws OutOfBoardException, NonPossibleMoveException {
		Boolean promotion = false;
		if (b.getNumeroCoupMax() > b.getNumeroCoup()){
			Coup c = b.getListeCoups().get(b.getNumeroCoup());
			if (c.getIsPromotion() && c.getMovedPiece() instanceof Pawn){
				promotion = true;
			}
		}
		Boolean finPartie =	this.remplirOptions(b, promotion);
		Boolean nonCliquable = (finPartie || promotion);
		this.remplirListe(b);
		this.remplirEatenPieces(b);
		if (!b.getSelectedCase().equals("00")) {
			this.possibleMoves = b.getPiece(b.getSelectedCase()).possibleMoves(b);
		}
		this.body = "<form>\n"
				+ "<table align=center>\n"
				+ "	<tr>\n"
				+ "		<td class=\"corner\"></td>\n"
				+ "		<td class=\"border\">A</td>\n"
				+ "		<td class=\"border\">B</td>\n"
				+ "		<td class=\"border\">C</td>\n"
				+ "		<td class=\"border\">D</td>\n"
				+ "		<td class=\"border\">E</td>\n"
				+ "		<td class=\"border\">F</td>\n"
				+ "		<td class=\"border\">G</td>\n"
				+ "		<td class=\"border\">H</td>\n"
				+ "		<td class=\"corner\"></td>\n"
				+ "	</tr>\n";
		for (int i = 8; i > 0; --i) {
			this.body += "</tr>\n";
			this.body += "<td class=\"border\">"+ i +"</td>\n";
			for (int j = 1; j < 9; ++j)
				this.body += printPiece(i, j, b, nonCliquable) + "\n";
			this.body += "<td class=\"border\">"+ i +"</td>\n";
			this.body += "<tr>\n";
		}
		this.body += "<tr>\n"
				+ "		<td class=\"corner\"></td>\n"
				+ "		<td class=\"border\">A</td>\n"
				+ "		<td class=\"border\">B</td>\n"
				+ "		<td class=\"border\">C</td>\n"
				+ "		<td class=\"border\">D</td>\n"
				+ "		<td class=\"border\">E</td>\n"
				+ "		<td class=\"border\">F</td>\n"
				+ "		<td class=\"border\">G</td>\n"
				+ "		<td class=\"border\">H</td>\n"
				+ "		<td class=\"corner\"></td>\n"
				+ "	</tr>\n"
				+ "</table>\n"
				+ "</form>\n";

	}

	/**
	 * TODO
	 * @param row
	 * @param column
	 * @param b
	 * @return
	 */
	public String printPiece(int row, int column, Board b, Boolean promotion) {
		String pieceLine = "<td class=\"";
		int somme = row + column;
		if (somme % 2 == 1)
			pieceLine += "even";
		else
			pieceLine += "odd";
		if (!b.getSelectedCase().equals("00")) {
			if (isPlayable(row, column, b) && !promotion) {
				pieceLine += "Select\"><input type=\"image\" name=\"to"
						+ nameCase(row, column)
						+"\" src=\"pieces/"
						+ getNomPiece(row, column, b)
						+ " width=32 /></td>";
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
				pieceLine += "Select\"><input type=\"image\" name=\""
						+ nameCase(row, column)
						+ "\" src=\"pieces/"
						+ getNomPiece(row, column, b)
						+ " width=32 /></td>";
				return pieceLine;
			}
			else {
				pieceLine += "\"><input type=\"image\" name=\""
						+ nameCase(row, column)
						+ "\" src=\"pieces/"
						+ getNomPiece(row, column, b)
						+ " width=32 /></td>";
				return pieceLine;
			}
		}
		pieceLine += "\"><img src=\"pieces/"
				+ getNomPiece(row, column, b)
				+ " width=32 /></td>";
		return pieceLine;
	}

	/**
	 * TODO
	 * @param row
	 * @param column
	 * @return
	 */
	public String nameCase(int row, int column){
		String name = "";
		switch (column) {
		case 1: name += 'A'; break;
		case 2: name += 'B'; break;
		case 3: name += 'C'; break;
		case 4: name += 'D'; break;
		case 5: name += 'E'; break;
		case 6: name += 'F'; break;
		case 7: name += 'G'; break;
		case 8: name += 'H'; break;
		}
		name += row;
		return name;
	}

	/**
	 * TODO
	 * @param row
	 * @param column
	 * @param b
	 * @return
	 */
	public String getNomPiece(int row, int column, Board b) {
		if (b.isEmpty(row, column))
			return "blank.svg\" alt=\" \"";
		Piece p = b.getPiece(row, column);
		return getNomPiece(p);
	}
	
	public String getNomPiece(Piece p){
		String nom = "";
		nom += p.getColor()
				+ p.getNom()
				+ ".svg\" alt=\""
				+ p.getShortcut()
				+ "\"";
		return nom;
	}

	/**
	 * TODO
	 * @param row
	 * @param column
	 * @param b
	 * @return
	 */
	public boolean isPlayable(int row, int column, Board b) {
		Iterator<Square> it = this.possibleMoves.iterator();
		while(it.hasNext()) {
			Square s = it.next();
			if (s.isThisSquare(row, column))
				return true;
		}
		return false;
	}

	/**
	 * TODO
	 * @return
	 */
	public static String getHead() {
		return head;
	}

	/**
	 * TODO
	 * @param head
	 */
	public static void setHead(String head) {
		HTMLGen.head = head;
	}

	/**
	 * TODO
	 * @return
	 */
	public static String getBottom1() {
		return bottom1;
	}

	/**
	 * TODO
	 * @param bottom1
	 */
	public static void setBottom1(String bottom1) {
		HTMLGen.bottom1 = bottom1;
	}

	/**
	 * TODO
	 * @return
	 */
	public static String getBottom2() {
		return bottom2;
	}

	/**
	 * TODO
	 * @param bottom2
	 */
	public static void setBottom2(String bottom2) {
		HTMLGen.bottom2 = bottom2;
	}

	public void remplirListe(Board b){
		String debutFont = "<font color =\"white\" face=\"courier\">";
		String finFont = "</font>";
		this.listeCoups += "<div id=\"menu\"><BR><BR>" + debutFont +"Liste des coups :<BR>" + finFont;
		for (int i=1; i<b.getNumeroCoupMax(); ++i){
			if (i>=b.getNumeroCoup()){
				debutFont = "<font color =\"grey\">";
			}
			if (i%2 == 0){
				this.listeCoups += debutFont + " - " + b.getListeCoups().get(i).afficherCoup() + finFont + "<BR>";
			}
			else{
				this.listeCoups += debutFont;
				if ((i+1)/2 < 10){
					this.listeCoups += "0";
				}
				this.listeCoups += (i+1)/2 + ". " + b.getListeCoups().get(i).afficherCoup() + finFont;			
			}
		}
		this.listeCoups += "</div>";
	}

	public Boolean remplirOptions(Board b, Boolean promotion){
		Boolean finPartie = false;
		this.options += "<div id=\"bandeau\"><table align=center>";
		// <td class=\"button\"></td>
		String player = b.getCurrentPlayer();
		Piece roi = b.getWhiteKing();
		String colorEnFrancais = "blanc";
		String adversaire = "black";
		if (player.equals("black")){
			roi = b.getBlackKing();
			adversaire = "white";
			colorEnFrancais = "noir";
		}

		this.options += "<td class=\"button\"><a href=\"?NewGame\">Nouvelle Partie</a></td>";
		this.options += "<td class=\"button\">";
		if (b.getNumeroCoup()>1 && !promotion){
			this.options += "<a href=\"?Undo\">Annuler Coup</a>";
		}
		else{
			this.options += "Annuler Coup";
		}
		this.options += "</td>";
		this.options += "<td class=\"button\">";
		if (b.getNumeroCoup()<b.getNumeroCoupMax() && !promotion){
			this.options += "<a href=\"?Redo\">Retablir Coup</a>";
		}
		else{
			this.options += "Retablir Coup";
		}
		this.options += "</td>";
		this.options += "</table></div>";
		this.options2 += "<BR>\n\n";
		this.options2 += "<center>";
		if (promotion){
			this.options2 += "<td class=\"button\"><a href=\"?Rook\"><img src=\"pieces/" + adversaire + "Rook.svg\"   alt=\"T\" width=32 /></a>\n"
					+ "<a href=\"?Knight\"><img src=\"pieces/" + adversaire + "Knight.svg\" alt=\"C\" width=32 /></a>\n"
					+ "<a href=\"?Bishop\"><img src=\"pieces/" + adversaire + "Bishop.svg\" alt=\"F\" width=32 /></a>\n"
					+ "<a href=\"?Queen\"><img src=\"pieces/" + adversaire + "Queen.svg\"  alt=\"D\" width=32 /></a>\n</td>";
		}
		else if (b.isEchec(player, roi.getRow(), roi.getColumn())){
			this.options2 += "<td class=\"button\"><font color =\"red\"> /!\\ Roi " + colorEnFrancais + " en Echec ";
			try {
				if (b.isEchecEtMat(player)){
					this.options2 += "et Mat ";
					finPartie = true;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			this.options2 += "/!\\ </font></td>";
		}
		else if (b.isPat()){
			this.options2 += "<td class=\"button\"> Egalité par Pat </td>";
			finPartie = true;
		}
		else{
			this.options2 += "<BR>";
		}
		this.options2 += "</center><BR>";
		return finPartie;
	}
	
	public void remplirEatenPieces(Board b){
		this.eatenPieces += "<div id=\"menuright\">\n";
		this.eatenPieces += "<div id=\"menuhaut\">\n";
		this.eatenPieces += "<font color = \"black\">Pieces mangées noires :<BR>\n";
		this.eatenPieces += "<table align=\"center\">";
		ArrayList<Piece> blackEatenPieces = b.getBlackEatenPieces();
		Iterator<Piece> blackIt = blackEatenPieces.iterator();
		int tr = 0;
		while (blackIt.hasNext()){
			if (tr==3){
				this.eatenPieces += "</tr>\n";
				tr = 0;
			}
			else{
				if (tr==0){
					this.eatenPieces += "<tr>\n";
				}
				Piece p = blackIt.next();
				this.eatenPieces += "<td><img src=\"pieces/"
				+ getNomPiece(p)
				+ " width=32 /></td>\n";
				tr++;
			}
		}
		if (tr != 0){
			this.eatenPieces += "</tr>\n";
			tr = 0;
		}
		this.eatenPieces += "</table>";
		this.eatenPieces += "</font>\n";
		this.eatenPieces += "</div>\n";
		this.eatenPieces += "<div id=\"menubas\">\n";
		this.eatenPieces += "<font color = \"white\">Pieces mangées blanches :<BR>\n";
		this.eatenPieces += "<table align=\"center\">";
		ArrayList<Piece> whiteEatenPieces = b.getWhiteEatenPieces();
		Iterator<Piece> whiteIt = whiteEatenPieces.iterator();
		this.eatenPieces += "</tr>\n";
		while (whiteIt.hasNext()){
			if (tr==3){
				this.eatenPieces += "</tr>\n";
				tr = 0;
			}
			else{
				if (tr==0){
					this.eatenPieces += "<tr>\n";
				}
				Piece p = whiteIt.next();
				this.eatenPieces += "<td><img src=\"pieces/"
				+ getNomPiece(p)
				+ " width=32 /></td>\n";
				tr++;
			}
		}
		if (tr != 0){
			this.eatenPieces += "</tr>\n";
			tr = 0;
		}
		this.eatenPieces += "</table>";
		this.eatenPieces += "</font>\n";
		this.eatenPieces += "</div>\n";		
		this.eatenPieces += "</div>\n";
	}

}
