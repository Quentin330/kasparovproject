import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Classe instanciant une piece.
 * Elle comprend en variable de classe sa couleur, sa position, et son type (en chaine de caractère).
 */
abstract public class Piece implements Serializable{

	/**
	 * Default serial version id
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Couleur de la piece ("black" ou "white").
	 */
	private String color;

	/**
	 * Position de la pièce en hauteur (de 1 à 8 et 0 si pièce perdue).
	 */
	private int row;

	/**
	 * Position de la pièce en largeur (de 1 à 8 et 0 si pièce perdue).
	 */
	private int column;

	/**
	 * Chaine de caractère du type la pièce.
	 */
	private String nom;

	/**
	 * Caractère identifiant la pièce.
	 */
	private String shortcut;

	/**
	 * Booleen indiquant si la piece s'est deplacée au moins une fois dans la partie.
	 */
	private boolean hasMovedOnce;

	/**
	 * Numero du coup pour lequel un pion peut être mangé en prise en passant
	 */
	private int mangeableEnPrisePassant;

	/**
	 * TODO
	 * @return
	 */
	public int getMangeableEnPrisePassant() {
		return mangeableEnPrisePassant;
	}

	/**
	 * TODO
	 * @param mangeableEnPrisePassant
	 */
	public void setMangeableEnPrisePassant(int mangeableEnPrisePassant) {
		this.mangeableEnPrisePassant = mangeableEnPrisePassant;
	}

	/**
	 * Renvoie un clone de la pièce.
	 */
	protected abstract Piece clone();

	/**
	 * Retourne la liste des cases où la liste peut jouer, 
	 * (sans prendre en compte en compte les cases mettant se mettant en échec).
	 * @param	board 
	 * 				La partie en cours.
	 * @return	Un tableau de cases.
	 */
	abstract ArrayList<Square> possibleMoves(Board board);

	/**
	 * Setteur du nom de la pièce.
	 * @param	nom 
	 * 				Nom de la pièce.
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * Setteur du caractère identifiant la pièce.
	 * @param	shortcut
	 * 				Caractère identifiant la pièce.
	 */
	public void setShortcut(String shortcut) {
		this.shortcut = shortcut;
	}

	/**
	 * Getteur du nom de la pièce.
	 * @return	Le nom de la pièce
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * Getteur du caractère identifiant la pièce.
	 * @return	Le caractère identifiant la pièce.
	 */
	public String getShortcut() {
		return shortcut;
	}

	/**
	 * Retourne vrai si la pièce est noire, faux sinon.
	 * @return	un booleen indiquant si la pièce est noire.
	 */
	public boolean isBlack() {
		return (this.color.equals("black"));
	}

	/**
	 * Retourne vrai si la pièce est blanche, faux sinon.
	 * @return	un booleen indiquant si la pièce est blanche.
	 */
	public boolean isWhite() {
		return (this.color.equals("white"));
	}

	/**
	 * Getteur de la couleur de la pièce.
	 * @return	La couleur de la pièce.
	 */
	public String getColor() {
		return color;
	}

	/**
	 * Setteur de la couleur de la pièce.
	 * @param	color 
	 * 				La couleur de la pièce.
	 */
	public void setColor(String color) {
		this.color = color;
	}

	/**
	 * Getteur de la valeur en ordonnée de la pièce.
	 * @return	La valeur en ordonnée de la pièce.
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Setteur de la valeur en ordonnée de la pièce.
	 * @param	row
	 * 				La valeur en ordonnée de la pièce.	
	 */
	public void setRow(int row) {
		this.row = row;
	}

	/**
	 * Getteur de la valeur en abscisse de la pièce.
	 * @return	La valeur en abscisse de la pièce.
	 */
	public int getColumn() {
		return column;
	}

	/**
	 * Setteur de la valeur en abscisse de la pièce.
	 * @param	column
	 * 				La valeur en absisse de la pièce.
	 */
	public void setColumn(int column) {
		this.column = column;
	}

	/**
	 * Setteur du booleen indiquant si la pièce a bougé au moins une fois durant la partie.
	 * @param	b
	 * 				Le booleen true ou false. 
	 */
	public void moveOnce(boolean b) {
		this.hasMovedOnce = b;
	}

	/**
	 * Retourne vrai si la pièce a bougé au moins une fois durant la partie, faux sinon.
	 * @return 	Le booleen indiquant si la pièce a bougé au moins une fois durant la partie.
	 */
	public boolean hasMovedOnce() {
		return this.hasMovedOnce;
	}

	/**
	 * Retourne vrai si la pièce est prise faux sinon.
	 * @return 	Le booleen indiquant si la pièce est prise.
	 */
	public boolean isDead() {
		return ((this.getRow() == 0) && (this.getColumn() == 0));
	}

	/**
	 * Retourne vrai si la case décrite par les paramètres 
	 * "row" et "column contient une pièce adverse.
	 * @param	board 
	 * 				La partie en cours.
	 * @param	row
	 * 				La valeur en ordonnée de la case à évaluer.
	 * @param	column
	 * 				La valeur en abscisse de la case à évaluer.
	 * @return	Le booleen indiquant si la case décrite 
	 * 			contient une adverse.
	 */
	public boolean isOpponent(Board board, int row, int column) {
		if (this.isBlack())
			return board.isWhite(row, column);
		else
			return board.isBlack(row, column);
	}

	/**
	 * Retourne vrai si la case décrite par les paramètres 
	 * "row" et "column contient une pièce alliée.
	 * @param	board
	 * 				La partie en cours.
	 * @param	row
	 * 				La valeur en ordonnée de la case à évaluer.
	 * @param	column
	 * 				La valeur en abscisse de la case à évaluer.
	 * @return	Le booleen indiquant si la case décrite 
	 * 			contient une pièce alliée.
	 */
	public boolean isSameColor(Board board, int row, int column) {
		if (this.isBlack())
			return board.isBlack(row, column);
		else
			return board.isWhite(row, column);
	}

	/**
	 * Retourne la liste des cases jouables en diagonales
	 * jusqu'à la rencontre d'un obstacle.
	 * @param	board
	 * 				La partie en cours.
	 * @return	La liste des cases jouables en diagonales.
	 */
	public ArrayList<Square> possibleMovesDiagonale(Board board) {
		ArrayList<Square> movesList = new ArrayList<Square>();
		int i = 1;
		//Mouvement en haut à droite
		while ((this.getRow()+i < 9) && (this.getColumn()+i < 9) && 
				board.isEmpty(this.getRow() +i, this.getColumn() +i)) {
			movesList.add(new Square(this.getRow()+i, this.getColumn()+i));
			i++;
		}
		if (this.isOpponent(board, this.getRow()+i, this.getColumn()+i))
			movesList.add(new Square(this.getRow()+i, this.getColumn()+i));
		i = 1;
		//Mouvement en haut à gauche
		while ((this.getRow()+i < 9) && (this.getColumn()-i > 0) && 
				(board.isEmpty(this.getRow()+i, this.getColumn()-i))) {
			movesList.add(new Square(this.getRow()+i, this.getColumn()-i));
			i++;
		}
		if (this.isOpponent(board, this.getRow()+i, this.getColumn()-i))
			movesList.add(new Square(this.getRow()+i, this.getColumn()-i));
		i = 1;
		//Mouvement en bas à gauche
		while ((this.getRow()-i > 0) && (this.getColumn()-i > 0) && 
				(board.isEmpty(this.getRow()-i, this.getColumn()-i))) {
			movesList.add(new Square(this.getRow()-i, this.getColumn()-i));
			i++;
		}
		if (this.isOpponent(board, this.getRow()-i, this.getColumn()-i))
			movesList.add(new Square(this.getRow()-i, this.getColumn()-i));
		i = 1;
		//Mouvement en bas à droite
		while ((this.getRow()-i > 0) && (this.getColumn()+i < 9) && 
				(board.isEmpty(this.getRow()-i, this.getColumn()+i))) {
			movesList.add(new Square(this.getRow()-i, this.getColumn()+i));
			i++;
		}
		if (this.isOpponent(board, this.getRow()-i, this.getColumn()+i))
			movesList.add(new Square(this.getRow()-i, this.getColumn()+i));
		return movesList;
	}
	
	/**
	 * Retourne la liste des cases jouables en lignes droites
	 * jusqu'à la rencontre d'un obstacle.
	 * @param	board
	 * 				La partie en cours.
	 * @return	La liste des cases jouables en lignes droites.
	 */
	public ArrayList<Square> possibleMovesDroit(Board board) {
		ArrayList<Square> movesList = new ArrayList<Square>();
		int i = 1;
		//Mouvement en haut
		while ((this.getRow()+i < 9) && 
				(board.isEmpty(this.getRow()+i, this.getColumn()))) {
			movesList.add(new Square(this.getRow()+i, this.getColumn()));
			i++;
		}
		if (this.isOpponent(board, this.getRow()+i, this.getColumn()))
			movesList.add(new Square(this.getRow()+i, this.getColumn()));
		i = 1;
		//Mouvement en bas
		while ((this.getRow()-i > 0) && 
				(board.isEmpty(this.getRow()-i, this.getColumn()))) {
			movesList.add(new Square(this.getRow()-i, this.getColumn()));
			i++;
		}
		if (this.isOpponent(board, this.getRow()-i, this.getColumn()))
			movesList.add(new Square(this.getRow()-i, this.getColumn()));
		i = 1;
		//Mouvement à gauche
		while ((this.getColumn()-i > 0) && 
				(board.isEmpty(this.getRow(), this.getColumn()-i))) {
			movesList.add(new Square(this.getRow(), this.getColumn()-i));
			i++;
		}
		if (this.isOpponent(board, this.getRow(), this.getColumn()-i))
			movesList.add(new Square(this.getRow(), this.getColumn()-i));
		i = 1;
		//Mouvement à droite
		while ((this.getColumn()+i < 9) && 
				(board.isEmpty(this.getRow(), this.getColumn()+i))) {
			movesList.add(new Square(this.getRow(), this.getColumn()+i));
			i++;
		}
		if (this.isOpponent(board, this.getRow(), this.getColumn()+i))
			movesList.add(new Square(this.getRow(), this.getColumn()+i));
		return movesList;
	}

	/**
	 * Retourne vrai si la pièce est de soustype "Roi".
	 * @return	un booleen indiquant si la pièce est de sous-type "Roi".
	 */
	public boolean isKing() {
		if (this instanceof King)
			return true;
		return false;
	}

	/**
	 * Affecte la valeur 0 aux coordonnées de la pièce, indiquant qu'elle est mangé. 
	 */
	private void isBeingEaten() {
		this.row = 0;
		this.column = 0;
	}

	/**
	 * Déplace la pièce de la partie "board", depuis ses coordonnées initiales (de ses
	 * variables de classes) vers les coordonnées "row" et "column".
	 * @param 	board
	 * 				La partie en cours.
	 * @param 	row
	 * 				Valeur en ordonnées de la case vers laquelle déplacer la pièce.
	 * @param 	column
	 * 				Valeur en abscisse de la case vers laquelle déplacer la pièce.
	 * @throws OutOfBoardException
	 * @throws NonPossibleMoveException
	 * @throws EchecException
	 */
	public void deplacerPiece(Board board, int row, int column) 
			throws OutOfBoardException, NonPossibleMoveException, EchecException {
		Boolean priseEnPassant = false;
		if (this instanceof Pawn)
			if (board.isEmpty(row, column))
				if (this.column!=column)
					priseEnPassant = true;
		Coup coup = new Coup();
		coup.setIsPriseEnPassant(priseEnPassant);
		coup.setMovedPiece(this);
		coup.setNumeroCoup(board.getNumeroCoup());
		Square caseDepart = new Square(this.row, this.column);
		coup.setCaseDepart(caseDepart);
		Square caseArrivee = new Square(row, column);
		coup.setCaseArrivee(caseArrivee);
		int oldRow = this.row;
		int oldColumn = this.column;
		boolean mange = false;
		board.setSelectedCase("00");
		Piece pieceMange = new Pawn("blue", 0, 0);
		if ((row < 1) || (row > 8) || (column < 1) || (column > 8))
			throw new OutOfBoardException("jeu hors des limites");
		ArrayList<Square> listeCoups = this.possibleMoves(board);
		boolean peutJouer = false;
		Iterator<Square> it = listeCoups.iterator();
		while(it.hasNext()) {
			Square s = it.next();
			if (s.isThisSquare(row, column))
				peutJouer = true;
		}
		if (!peutJouer)
			throw new NonPossibleMoveException("coup non possible");
		if (board.isEmpty(row, column)&&!priseEnPassant) {
			this.row = row;
			this.column = column;
		}
		else {
			mange = true;
			coup.setHasEaten(mange);
			if (priseEnPassant)
				pieceMange = board.getPiece(oldRow, column);
			else
				pieceMange = board.getPiece(row, column);
			coup.setEatenPiece(pieceMange);
			pieceMange.isBeingEaten();
			board.estMangee(pieceMange, pieceMange.getColor());
			this.row = row;
			this.column = column;
		}
		if (this.color.equals("black")) {
			if (board.isEchec("black", 
					board.getBlackKing().getRow(), 
					board.getBlackKing().getColumn())) {
				this.row = oldRow;
				this.column = oldColumn;
				if (mange) {
					if (priseEnPassant){
						pieceMange.setRow(oldRow);
						pieceMange.setColumn(column);
					}
					else {
						pieceMange.setRow(row);
						pieceMange.setColumn(column);
					}
				}
				board.piecesNonMangees();
				throw new EchecException("Ce mouvement met votre roi en echec");
			}
		}
		else {
			if (board.isEchec("white", 
					board.getWhiteKing().getRow(), 
					board.getWhiteKing().getColumn())) {
				this.row = oldRow;
				this.column = oldColumn;
				if (mange) {
					if (priseEnPassant){
						pieceMange.setRow(oldRow);
						pieceMange.setColumn(column);
					}
					else {
						pieceMange.setRow(row);
						pieceMange.setColumn(column);
					}
				}
				board.piecesNonMangees();
				throw new EchecException("Ce mouvement met votre roi en echec");
			}
		}
		if (this instanceof Pawn){
			if (oldRow - row == 2 || row - oldRow == 2){
				this.setMangeableEnPrisePassant(board.getNumeroCoup()+1);
			}
			else if (row == 1 || row == 8){
				coup.setIsPromotion(true);
				coup.setOldPiece(this);
			}
		}
		board.ajouterCoup(coup);
		if (coup.getIsPromotion())
			board.setNumeroCoup(board.getNumeroCoup()-1);
		if ((this instanceof Rook) || (this instanceof King)) {
			if (this instanceof King) {
				if (!this.hasMovedOnce() && row == 1 && column == 7 && !board.isEmpty(1, 8)) {
					Piece rook = board.getPiece(1, 8);
					coup.setEatenPiece(rook);
					rook.setColumn(6);
					coup.setIsPetitRoque(true);
				}
				if (!this.hasMovedOnce() && row == 8 && column == 7 && !board.isEmpty(8, 8)) {
					Piece rook = board.getPiece(8, 8);
					coup.setEatenPiece(rook);
					rook.setColumn(6);
					coup.setIsPetitRoque(true);
				}
				if (!this.hasMovedOnce() && row == 1 && column == 3 && !board.isEmpty(1, 1)) {
					Piece rook = board.getPiece(1, 1);
					coup.setEatenPiece(rook);
					rook.setColumn(4);
					coup.setIsGrandRoque(true);
				}
				if (!this.hasMovedOnce() && row == 8 && column == 3 && !board.isEmpty(8, 1)) {
					Piece rook = board.getPiece(8, 1);
					coup.setEatenPiece(rook);
					rook.setColumn(4);
					coup.setIsGrandRoque(true);
				}
			}
			this.hasMovedOnce = true;
		}
	}

	/**
	 * Retourne vrai si la case de la partie "b" décrite par les 
	 * coordonnées "column" et "row" est jouable par la pièce.
	 * @param 	row
	 * 				La valeur en ordonnées de la case à évaluer.
	 * @param 	column
	 * 				La valuer en abscisse de la case à évaluer.
	 * @param 	b
	 * 				La partie en cours.
	 * @return	Le booleen indiquant si la cas décrite est jouable par la pièce.
	 */
	public boolean isPlayable(int row, int column, Board b) {
		ArrayList<Square> possibleMoves = possibleMoves(b);
		Iterator<Square> it = possibleMoves.iterator();
		while(it.hasNext()){
			Square s = it.next();
			if (s.isThisSquare(row, column))
				return true;
		}
		return false;
	}

	/**
	 * Retourne vrai si la case de la partie "b" décrite par la 
	 * chaine de caractère "caseJeu" est jouable par la pièce.
	 * @param 	row
	 * 				Chaine de caractère de la case à évaluer,
	 * 				de "A" à "H" pour les abscisses et de 
	 * 				1 à 8 pour les ordonées.
	 * @param 	b
	 * 				La partie en cours.
	 * @return 	Le booleen indiquant si la cas décrite est jouable par la pièce.
	 */
	public boolean isPlayable(String caseJeu, Board b) {
		assert(!(caseJeu.charAt(0)<'A') && 
				!(caseJeu.charAt(0)>'H') && 
				!(caseJeu.charAt(1)<'1') && 
				!(caseJeu.charAt(1)>'8'));
		int column = caseJeu.charAt(0)-'A'+'1'-48;
		int row = caseJeu.charAt(1)-48;
		return this.isPlayable(row, column, b);
	}

	/**
	 * TODO
	 * @param board
	 * @return
	 */
	public ArrayList<Square> possibleMovesSE(Board board) {
		Board boardSimu;
		ArrayList<Square> movesList = new ArrayList<>();
		for(int i = 1; i <= 8; i++) {
			for(int j = 1; j <= 8; j++) {
				if (this.isPlayable(i, j, board)) {
					try {
						boardSimu = board.clone();
						boardSimu.deplacerPiece(this.getRow(), this.getColumn(), i, j);
						if (this.color == "white"){
							ArrayList<Square> echecList = boardSimu.echec("white");
							int row = boardSimu.getWhiteKing().getRow();
							int column = boardSimu.getWhiteKing().getColumn();
							boolean isEchec = false;
							Iterator<Square> it = echecList.iterator();
							while (it.hasNext()){
								Square s = it.next();
								if (s.isThisSquare(row, column))
									isEchec = true;
							}
							if (!isEchec)
								movesList.add(new Square (i, j));
						}
						else {
							ArrayList<Square> echecList = boardSimu.echec("black");
							int row = boardSimu.getBlackKing().getRow();
							int column = boardSimu.getBlackKing().getColumn();
							boolean isEchec = false;
							Iterator<Square> it = echecList.iterator();
							while (it.hasNext()){
								Square s = it.next();
								if (s.isThisSquare(row, column))
									isEchec = true;
							}
							if (!isEchec)
								movesList.add(new Square (i, j));
						}
					} catch (OutOfBoardException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						
					} catch (NonPossibleMoveException e) {
						// TODO Auto-generated catch block
						System.out.println("Raw : " + this.row + " Column : " + this.column + " vers Raw : " + i + " Column : " + j);
						e.printStackTrace();
					}
				}

			}
		}
		return movesList;
	}
	
	
}
