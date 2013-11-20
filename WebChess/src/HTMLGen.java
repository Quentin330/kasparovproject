import java.util.ArrayList;
import java.util.Iterator;

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
			+ "<body>\n"
			+ "<form>\n"
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

	/**
	 * TODO
	 */
	private static String bottom1 = "<tr>\n"
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
			+ "</form>\n"
			+ "</body>\n";

	/**
	 * TODO
	 */
	private String options = "";

	private String listeCoups = "";

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
				+ this.body
				+ HTMLGen.getBottom1()
				+ this.options
				+ this.listeCoups
				+ HTMLGen.getBottom2();
	}

	/**
	 * TODO
	 * @param b
	 * @throws OutOfBoardException
	 * @throws NonPossibleMoveException
	 */
	public HTMLGen(Board b) throws OutOfBoardException, NonPossibleMoveException {
		this.remplirOptions(b);
		this.remplirListe(b);
		if (!b.getSelectedCase().equals("00")) {
			this.possibleMoves = b.getPiece(b.getSelectedCase()).possibleMoves(b);
		}
		this.body = "";
		for (int i = 8; i > 0; --i) {
			this.body += "</tr>\n";
			this.body += "<td class=\"border\">"+ i +"</td>\n";
			for (int j = 1; j < 9; ++j)
				this.body += printPiece(i, j, b) + "\n";
			this.body += "<td class=\"border\">"+ i +"</td>\n";
			this.body += "<tr>\n";
		}
	}

	/**
	 * TODO
	 * @param row
	 * @param column
	 * @param b
	 * @return
	 */
	public String printPiece(int row, int column, Board b) {
		String pieceLine = "<td class=\"";
		int somme = row + column;
		if (somme % 2 == 1)
			pieceLine += "even";
		else
			pieceLine += "odd";
		if (!b.getSelectedCase().equals("00")) {
			if (isPlayable(row, column, b)) {
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
		if (p.getColor().equals(b.getCurrentPlayer())) {
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
		this.listeCoups += "<center>";
		String debutFont = "<font color =\"black\">";
		String finFont = "</font>";
		for (int i=1; i<b.getNumeroCoupMax(); ++i){
			if (i>=b.getNumeroCoup()){
				debutFont = "<font color =\"grey\">";
			}
			if (i%2 == 0){
				this.listeCoups += " " + debutFont + b.getListeCoups().get(i).afficherCoup() + finFont + "<BR>";
			}
			else{
				this.listeCoups += i+1/2 + ". " + debutFont + b.getListeCoups().get(i).afficherCoup() + finFont;			
			}
		}
		this.listeCoups += "</center>";
	}

	public void remplirOptions(Board b){
		this.options += "<center>";
		String player = b.getCurrentPlayer();
		Piece roi = b.getWhiteKing();
		String colorEnFrancais = "blanc";
		if (player.equals("black")){
			roi = b.getBlackKing();
			colorEnFrancais = "noir";
		}
		if (b.isEchec(player, roi.getRow(), roi.getColumn())){
			this.options += "<font color =\"red\"> /!\\ Roi " + colorEnFrancais + " en Echec ";
			try {
				if (b.isEchecEtMat(player)){
					this.options += "et Mat ";
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			this.options += "/!\\ <BR></font>";
		}
		else{
			this.options += "<BR>";
		}
		if (b.getNumeroCoup()>1){
			this.options += "<a href=\"?Undo\">Annuler Coup</a><BR>";
		}
		else{
			this.options += "Annuler Coup<BR>";
		}
		if (b.getNumeroCoup()<b.getNumeroCoupMax()){
			this.options += "<a href=\"?Redo\">Retablir Coup</a><BR>";
		}
		else{
			this.options += "Retablir Coup<BR>";
		}
		this.options += "<a href=\"?NewGame\">Nouvelle Partie</a><BR>";
		this.options += "</center><BR><BR>";
	}

}
