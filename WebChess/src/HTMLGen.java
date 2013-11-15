import java.util.ArrayList;
import java.util.Iterator;


public class HTMLGen {

	private String body;
	private ArrayList<Square> possibleMoves;
	private static String head =
			"<html>\n"
					+ "<head>\n"
					+ "<meta http-equiv=\"content-type\" content=\"text/html;charset=utf-8\" />\n"
					+ "<link rel=\"stylesheet\" href=\"stylesheet.css\" type=\"text/css\" />\n"
					+ "<link rel=\"shortcut icon\" type=\"image/x-icon\" href=\"favicon.ico\">\n"
					+ "<title>\n"
					+ "	WebChess Kasparov\n</title>\n"
					+ "</head>\n<body>\n<form>\n<table align=center>\n	<tr>\n		<td class=\"corner\"></td>\n		<td class=\"border\">A</td>\n		<td class=\"border\">B</td>\n		<td class=\"border\">C</td>\n		<td class=\"border\">D</td>\n		<td class=\"border\">E</td>\n		<td class=\"border\">F</td>\n		<td class=\"border\">G</td>\n		<td class=\"border\">H</td>\n		<td class=\"corner\"></td>\n	</tr>\n";
	private static String bottom = 
			"<tr>\n		<td class=\"corner\"></td>\n		<td class=\"border\">A</td>\n		<td class=\"border\">B</td>\n		<td class=\"border\">C</td>\n		<td class=\"border\">D</td>\n		<td class=\"border\">E</td>\n		<td class=\"border\">F</td>\n		<td class=\"border\">G</td>\n		<td class=\"border\">H</td>\n		<td class=\"corner\"></td>\n	</tr>\n</table>\n</form>\n</body>\n</html>";

	public String getPage (){
		return HTMLGen.getHead() + this.body + HTMLGen.getBottom();
	}
	
	public HTMLGen (Board b) throws OutOfBoardException, NonPossibleMoveException{
		if (b.getSelectedCase().length()==2){
			this.possibleMoves = b.getPiece(b.getSelectedCase()).possibleMoves(b);
		}
		this.body = "";
		for (int i = 8; i > 0; --i){
			this.body +=  "</tr>\n";
			this.body += "<td class=\"border\">"+ i +"</td>\n";
			for(int j = 1; j < 9; ++j){
				this.body += printPiece(i, j, b) + "\n";
			}
			this.body += "<td class=\"border\">"+ i +"</td>\n";
			this.body +=  "<tr>\n";

		}
	}
	
	public String printPiece(int row, int column, Board b){
		String pieceLine = "<td class=\"";
		int somme = row + column;
		if (somme%2 == 1){
			pieceLine += "even";
		}
		else{
			pieceLine += "odd";
		}
		if(b.getSelectedCase().length()==2){
			if (isPlayable(row, column, b)){
				pieceLine += "Select\"><input type=\"image\" name=\"to"+ nameCase(row, column) +"\" src=\"pieces/" + getNomPiece(row, column, b) + " width=32 /></td>";
				return pieceLine;
			}
		}
		if(b.isEmpty(row, column)){
			pieceLine += "\" ><img src=\"pieces/blank.svg\" alt=\" \" width=32 /></td>";
			return pieceLine;
		}
		Piece p = b.getPiece(row, column);
		if (p.getColor().equals(b.getCurrentPlayer())){
			pieceLine += "><input type=\"image\" name=\"" + nameCase(row, column) + "\" src=\"pieces/" + getNomPiece(row, column, b) + " width=32 /></td>";
			return pieceLine;
		}
		pieceLine += "\"><img src=\"pieces/" + getNomPiece(row, column, b) + " width=32 /></td>";
		return pieceLine;
		
	}
	
	public String nameCase(int row, int column){
		String name = "";
		switch (column){
		case 1: name += 'A';
		case 2: name += 'B';
		case 3: name += 'C';
		case 4: name += 'D';
		case 5: name += 'E';
		case 6: name += 'F';
		case 7: name += 'G';
		case 8: name += 'H';
		}
		name += row;
		return name;
	}
	
	// jusqu'à la fin du alt
	public String getNomPiece(int row, int column, Board b){
		if (b.isEmpty(row, column)){
			return "blank.svg\" alt=\" \"";
		}
		Piece p = b.getPiece(row, column);
		String nom = "";
		nom += p.getColor() + p.getNom() + ".svg\" alt=\""+ p.getShortcut() +"\"";
		return nom;
	}
	
	
	public boolean isPlayable(int row, int column, Board b){
		Iterator<Square> it = this.possibleMoves.iterator();
		while(it.hasNext()){
			Square s = it.next();
			if (s.isThisSquare(row, column)){
				return true;
			}
		}
		return false;
	}

	public static String getBottom() {
		return bottom;
	}

	public static void setBottom(String bottom) {
		HTMLGen.bottom = bottom;
	}

	public static String getHead() {
		return head;
	}

	public static void setHead(String head) {
		HTMLGen.head = head;
	}

}
