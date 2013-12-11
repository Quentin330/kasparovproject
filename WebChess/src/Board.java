import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Classe instanciant une partie, à savoir le plateau, l'état et la position de
 * l'ensemble des pièces de la partie.
 */
public class Board implements Serializable {

	/**
	 * default serial version ID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Tableau de l'ensemble des pièces de la partie.
	 */
	private Piece[] pieces;

	/**
	 * Liste des pièces blanches mangées.
	 */
	private ArrayList<Piece> whiteEatenPieces;

	/**
	 * Liste des pièces noires mangées.
	 */
	private ArrayList<Piece> blackEatenPieces;

	/**
	 * Instance du Roi noir du joueur.
	 */
	private King blackKing;

	/**
	 * Instance du roi blanc du joueur.
	 */
	private King whiteKing;

	/**
	 * Chaîne de caractère indiquant à qui est la main. Ses valeurs possibles
	 * sont "Black" ou "White".
	 */
	private String currentPlayer;

	/**
	 * Chaîne de caractère décrivant la case en surbrillance, de "A" à "H" pour
	 * les abscisses et de 1 à 8 pour les ordonées.
	 */
	private String selectedCase;

	/**
	 * Indice indiquant l'indice du coup courant dans la liste des coup.
	 */
	private int indexMove;

	/**
	 * Indice indiquant l'indice maximum des coups (différent de "indexMove"
	 * après un ou plusieurs annulerCoup).
	 */
	private int indexMoveMax;

	/**
	 * Table de hashage contenantla liste des coups jouées.
	 */
	private HashMap<Integer, Coup> listeCoups;

	/**
	 * Getteur de la liste des pièces blanches prises.
	 * 
	 * @return La liste des pièces blanches prises.
	 */
	public ArrayList<Piece> getWhiteEatenPieces() {
		return whiteEatenPieces;
	}

	/**
	 * Getteur de la liste des pièces noires prises.
	 * 
	 * @return La liste des pièces noires prises.
	 */
	public ArrayList<Piece> getBlackEatenPieces() {
		return blackEatenPieces;
	}

	/**
	 * Ajoute une pièce à la liste des pièces prises.
	 * 
	 * @param p
	 *            Pièce à ajouter.
	 * @param color
	 *            Couleur de la pièce à ajouter.
	 */
	public void estMangee(Piece p, String color) {
		if (color.equals("white"))
			this.whiteEatenPieces.add(p);
		else
			this.blackEatenPieces.add(p);
	}

	/**
	 * Getteur de la liste des coups joués au cours de la partie.
	 * 
	 * @return La liste des coups joués au cours de la partie.
	 */
	public HashMap<Integer, Coup> getListeCoups() {
		return listeCoups;
	}

	/**
	 * Ajoute un coup dans la variable "listeCoups".
	 * 
	 * @param coup
	 *            Coup à ajouter.
	 */
	public void ajouterCoup(Coup coup) {
		for (int i = this.indexMove; i < this.indexMoveMax; ++i)
			listeCoups.remove(i);
		listeCoups.put(this.indexMove, coup);
		this.coupSuivant();
	}

	/**
	 * Met à jour le tableau des pièces mangées.
	 */
	public void piecesNonMangees() {
		for (int i = whiteEatenPieces.size() - 1; i >= 0; i--)
			if (!whiteEatenPieces.get(i).isDead())
				whiteEatenPieces.remove(whiteEatenPieces.get(i));
		for (int i = blackEatenPieces.size() - 1; i >= 0; i--)
			if (!blackEatenPieces.get(i).isDead())
				blackEatenPieces.remove(blackEatenPieces.get(i));
	}

	/**
	 * Annule le dernier coup joué dans board.
	 */
	public void annulerCoup() {
		this.indexMove--;
		this.annulerCoup(this.listeCoups.get(this.indexMove));
		this.selectedCase = "00";
		this.nextPlayer();
		/*
		 * Iterator<Piece> whiteIt = whiteEatenPieces.iterator(); while
		 * (whiteIt.hasNext()){ Piece p = whiteIt.next(); if (!p.isDead()){
		 * whiteEatenPieces.remove(p); } } Ceci est impossible Exception in
		 * thread "main" java.util.ConcurrentModificationException solution
		 * trouvée sur http://www.developpez.net/
		 * forums/d763054/java/general-java/debuter/
		 * probleme-type-java-util-concurrentmodificationexception
		 * -lors-suppression/
		 */
		this.piecesNonMangees();
	}

	/**
	 * Annule le dernier coup joué par rapprot à un coup.
	 * 
	 * @param coup
	 * 				dernier coup joué.
	 */
	private void annulerCoup(Coup coup) {
		if (coup.getIsPromotion()) {
			int pion = 0;
			for (int i = 0; i < 32; ++i) {
				if (this.pieces[i].equals(coup.getMovedPiece())) {
					pion = i;
					break;
				}
			}
			this.pieces[pion] = coup.getOldPiece();
			if (coup.getHasEaten()) {
				coup.getEatenPiece().setColumn(
						coup.getCaseArrivee().getColumn());
				coup.getEatenPiece().setRow(coup.getCaseArrivee().getRow());
			}
			this.pieces[pion].setRow(coup.getCaseDepart().getRow());
			this.pieces[pion].setColumn(coup.getCaseDepart().getColumn());
		} else {
			if (coup.getIsGrandRoque() || coup.getIsPetitRoque()) {
				if (coup.getIsGrandRoque())
					coup.getEatenPiece().setColumn(1);
				else if (coup.getIsPetitRoque())
					coup.getEatenPiece().setColumn(8);
				coup.getEatenPiece().moveOnce(false);
				coup.getMovedPiece().moveOnce(false);
			} else if (coup.getHasEaten()) {
				if (coup.getIsPriseEnPassant())
					coup.getEatenPiece().setRow(coup.getCaseDepart().getRow());
				else
					coup.getEatenPiece().setRow(coup.getCaseArrivee().getRow());
				coup.getEatenPiece().setColumn(
						coup.getCaseArrivee().getColumn());
			}
			coup.getMovedPiece().setRow(coup.getCaseDepart().getRow());
			coup.getMovedPiece().setColumn(coup.getCaseDepart().getColumn());
		}
	}

	/**
	 * Incrémente indexMove lors d'un coup d'un des joueurs.
	 */
	public void coupSuivant() {
		this.indexMove += 1;
		this.indexMoveMax = this.indexMove;
	}

	/**
	 * Décrémente indexMove.
	 */
	public void coupPrecedent() {
		this.indexMove -= 1;
	}

	/**
	 * Getteur de indexMove.
	 * 
	 * @return l'indice du coup courant dans la liste des coup.
	 */
	public int getNumeroCoup() {
		return indexMove;
	}

	/**
	 * Getteur de indexMoveMax.
	 * 
	 * @return l'indice maximum des coups.
	 */
	public int getNumeroCoupMax() {
		return indexMoveMax;
	}

	/**
	 * Affecte la valeur numeroCoup à indexMove.
	 * 
	 * @param numeroCoup
	 * 				valeur à affecter à indexMove.
	 */
	public void setNumeroCoup(int numeroCoup) {
		this.indexMove = numeroCoup;
	}

	/**
	 * Getteur indiquant à qui est la main.
	 * 
	 * @return "Black" ou "White" selon à qui le tour.
	 */
	public String getCurrentPlayer() {
		return currentPlayer;
	}

	/**
	 * Getteur indiquant la case en surbrillance.
	 * 
	 * @return Chaine de caractère de la case à évaluer, de "A" à "H" pour les
	 *         abscisses et de 1 à 8 pour les ordonnées.
	 */
	public String getSelectedCase() {
		return selectedCase;
	}

	/**
	 * Setteur indiquant la case en surbrillance.
	 * 
	 * @param Chaine
	 *            de caractère de la case, de "A" à "H" pour les abscisses et de
	 *            1 à 8 pour les ordonnées.
	 */
	public void setSelectedCase(String selectedCase) {
		this.selectedCase = selectedCase;
	}

	/**
	 * Getteur de la liste des pièces présentes dans board.
	 * 
	 * @return La liste des pièces présentes dans board.
	 * 			
	 */
	public Piece[] getPieces() {
		return pieces;
	}

	/**
	 * Getteur du roi noir.
	 * 
	 * @return le roi du joueur en noir.
	 */
	public King getBlackKing() {
		return blackKing;
	}

	/**
	 * setteur du roi noir.
	 * 
	 * @param blackKing
	 * 				roi noir à initialiser.
	 */
	public void setBlackKing(King blackKing) {
		this.blackKing = blackKing;
	}

	/**
	 * Getteur du roi blanc.
	 * 
	 * @return el roi du joueur en blanc.
	 */
	public King getWhiteKing() {
		return whiteKing;
	}

	/**
	 * setteur du roi blanc.
	 * 
	 * @param whiteKing
	 * 				roi blanc à initialiser.
	 */
	public void setWhiteKing(King whiteKing) {
		this.whiteKing = whiteKing;
	}

	/**
	 * Fait passer le tour à l'adversaire (lors d'un coup joué).
	 */
	public void nextPlayer() {
		if (this.currentPlayer.equals("black"))
			this.currentPlayer = "white";
		else
			this.currentPlayer = "black";
	}

	/**
	 * Duplique une liste de pièces.
	 * 
	 * @param liste
	 * 				la liste des pièces à cloner.
	 * @return la liste dupliquée.
	 */
	public ArrayList<Piece> cloneListe(ArrayList<Piece> liste) {
		ArrayList<Piece> clone = new ArrayList<Piece>();
		for (int i = 0; i < liste.size(); ++i)
			clone.add(liste.get(i));
		return clone;
	}

	/**
	 * Constructeur de board. Initialise un début de partie
	 * si aucun paramètre n'est donné.
	 */
	public Board() {
		this.whiteEatenPieces = new ArrayList<Piece>();
		this.blackEatenPieces = new ArrayList<Piece>();
		this.listeCoups = new HashMap<Integer, Coup>();
		this.indexMove = 1;
		this.selectedCase = "00";
		this.currentPlayer = "white";
		this.blackKing = new King("black", 8, 5);
		this.whiteKing = new King("white", 1, 5);
		this.pieces = new Piece[32];
		this.pieces[0] = new Rook("black", 8, 1);
		this.pieces[1] = new Knight("black", 8, 2);
		this.pieces[2] = new Bishop("black", 8, 3);
		this.pieces[3] = new Queen("black", 8, 4);
		this.pieces[4] = this.blackKing;
		this.pieces[5] = new Bishop("black", 8, 6);
		this.pieces[6] = new Knight("black", 8, 7);
		this.pieces[7] = new Rook("black", 8, 8);
		for (int i = 0; i < 8; ++i) {
			this.pieces[i + 8] = new Pawn("black", 7, i + 1);
			this.pieces[i + 16] = new Pawn("white", 2, i + 1);
		}
		this.pieces[24] = new Rook("white", 1, 1);
		this.pieces[25] = new Knight("white", 1, 2);
		this.pieces[26] = new Bishop("white", 1, 3);
		this.pieces[27] = new Queen("white", 1, 4);
		this.pieces[28] = this.whiteKing;
		this.pieces[29] = new Bishop("white", 1, 6);
		this.pieces[30] = new Knight("white", 1, 7);
		this.pieces[31] = new Rook("white", 1, 8);
	}

	/**
	 * Constructeur d'un board initialisé avec certain paramètre.
	 * Peut être initialiser avec tous les paramètres, ou aucun.
	 * 
	 * @param pieces
	 * 				listes des pièces en jeu.
	 * @param blackKing
	 * 				roi du joueur en noir.
	 * @param whiteKing
	 * 				roi du joueur en blanc.
	 */
	public Board(Piece[] pieces, King blackKing, King whiteKing,
			ArrayList<Piece> whiteEatenPieces, ArrayList<Piece> blackEatenPieces) {
		this.listeCoups = new HashMap<Integer, Coup>();
		this.indexMove = 1;
		this.selectedCase = "00";
		this.currentPlayer = "white";
		this.whiteEatenPieces = this.cloneListe(whiteEatenPieces);
		this.blackEatenPieces = this.cloneListe(blackEatenPieces);
		this.pieces = new Piece[32];
		for (int i = 0; i < 32; ++i) {
			this.pieces[i] = pieces[i].clone();
			if (this.pieces[i] instanceof King)
				if (this.pieces[i].getColor().equals("black"))
					this.blackKing = (King) this.pieces[i];
				else
					this.whiteKing = (King) this.pieces[i];
		}
	}

	/**
	 * Constructeur d'un board initialisé avec tous les paramètres.
	 * Peut être initialiser avec certains paramètres, ou aucun.
	 * 
	 * @param pieces
	 * 				listes des pièces en jeu.
	 * @param blackKing
	 * 				roi du joueur en noir.
	 * @param whiteKing
	 * 				roi du joueur en blanc.
	 * @param whiteEatenPieces
	 * 				liste des pièces blanche prises.
	 * @param blackEatenPieces
	 * 				liste des pièces noires prises.
	 * @param currentPlayer
	 * 				joueur font c'est le tour.
	 * @param selectedCase
	 * 				case en surbrillance.
	 * @param numeroCoup
	 * 				indice du coup courant dans la liste des coups.
	 * @param numeroCoupMax
	 * 				indice maximum d'un coup dans la liste des coups.
	 * @param lC
	 * 				liste des coups joués au cours de la partie.
	 */
	@SuppressWarnings("unchecked")
	public Board(Piece[] pieces, King blackKing, King whiteKing,
			ArrayList<Piece> whiteEatenPieces,
			ArrayList<Piece> blackEatenPieces, String currentPlayer,
			String selectedCase, int numeroCoup, int numeroCoupMax,
			HashMap<Integer, Coup> lC) {
		this.listeCoups = (HashMap<Integer, Coup>) lC.clone();
		this.indexMove = numeroCoup;
		this.indexMoveMax = numeroCoupMax;
		this.selectedCase = selectedCase;
		this.currentPlayer = currentPlayer;
		this.whiteEatenPieces = this.cloneListe(whiteEatenPieces);
		this.blackEatenPieces = this.cloneListe(blackEatenPieces);
		this.pieces = new Piece[32];
		for (int i = 0; i < 32; ++i) {
			this.pieces[i] = pieces[i].clone();
			if (this.pieces[i] instanceof King)
				if (this.pieces[i].getColor().equals("black"))
					this.blackKing = (King) this.pieces[i];
				else
					this.whiteKing = (King) this.pieces[i];
		}
	}

	/**
	 * retourne un clone de board.
	 */
	public Board clone() {
		return new Board(this.pieces, this.blackKing, this.whiteKing,
				this.whiteEatenPieces, this.blackEatenPieces,
				this.currentPlayer, this.selectedCase, this.indexMove,
				this.indexMoveMax, this.listeCoups);
	}

	/**
	 * vérifie si la case (row,column) est vide.
	 * 
	 * @param row
	 * 				position en abscisse de la case.
	 * @param column
	 * 				position en ordonnée de la case.
	 * @return 	vrai si la case est vide, faux si la case est
	 * 			occupée par une pièce.
	 */
	public boolean isEmpty(int row, int column) {
		for (int i = 0; i < 32; ++i)
			if (this.pieces[i].getRow() == row)
				if (this.pieces[i].getColumn() == column)
					return false;
		return true;
	}

	/**
	 * vérifie si la case (caseJeu) est vide.
	 * 
	 * @param caseJeu
	 * 					coordonnees en chaine de caractères 
	 * 					de la case.
	 * @return vrai si la case est vide, faux si la case est
	 * 			occupée par une pièce.
	 */
	public boolean isEmpty(String caseJeu) {
		assert (!(caseJeu.charAt(0) < 'A') && !(caseJeu.charAt(0) > 'H')
				&& !(caseJeu.charAt(1) < '1') && !(caseJeu.charAt(1) > '8'));
		int column = caseJeu.charAt(0) - 'A' + '1' - 48;
		int row = caseJeu.charAt(1) - 48;
		return this.isEmpty(row, column);
	}

	/**
	 * vérifie si la case (row,column) contient une pièce blanche.
	 * 
	 * @param row
	 * 				position en abscisse de la case.
	 * @param column
	 * 				position en ordonnée de la case.
	 * @return 	vrai si la case contient une pièce blanche,
	 * 			faux si elle est noire ou si elle est vide.
	 */
	public boolean isWhite(int row, int column) {
		for (int i = 0; i < 32; ++i)
			if (this.pieces[i].getRow() == row
					&& this.pieces[i].getColumn() == column)
				return this.pieces[i].isWhite();
		return false;
	}

	/**
	 * vérifie si la case (row,column) contient une pièce noire.
	 * 
	 * @param row
	 * 				position en abscisse de la case.
	 * @param column
	 * 				position en ordonnée de la case.
	 * @return	vrai si la case contient une pièce noire,
	 * 			faux si elle est blanche ou si elle est vide.
	 */
	public boolean isBlack(int row, int column) {
		for (int i = 0; i < 32; ++i)
			if (this.pieces[i].getRow() == row
					&& this.pieces[i].getColumn() == column)
				return this.pieces[i].isBlack();
		return false;
	}

	/**
	 * Ajoute dans une liste toutes les cases contigües à une pièce.
	 * 
	 * @param p
	 * 				pièce à évaluer
	 * @return la liste des cases contigües à la pièce.
	 */
	public ArrayList<Square> arroundSquares(Piece p) {
		ArrayList<Square> arround = new ArrayList<Square>();
		Square haut = new Square(p.getRow() + 1, p.getColumn());
		if (haut.isRealSquare())
			arround.add(haut);
		Square bas = new Square(p.getRow() - 1, p.getColumn());
		if (bas.isRealSquare())
			arround.add(bas);
		Square droite = new Square(p.getRow(), p.getColumn() + 1);
		if (droite.isRealSquare())
			arround.add(droite);
		Square gauche = new Square(p.getRow(), p.getColumn() - 1);
		if (gauche.isRealSquare())
			arround.add(gauche);
		Square hautdroite = new Square(p.getRow() + 1, p.getColumn() + 1);
		if (hautdroite.isRealSquare())
			arround.add(hautdroite);
		Square hautgauche = new Square(p.getRow() + 1, p.getColumn() - 1);
		if (hautgauche.isRealSquare())
			arround.add(hautgauche);
		Square basdroite = new Square(p.getRow() - 1, p.getColumn() + 1);
		if (basdroite.isRealSquare())
			arround.add(basdroite);
		Square basgauche = new Square(p.getRow() - 1, p.getColumn() - 1);
		if (basgauche.isRealSquare())
			arround.add(basgauche);
		return arround;
	}

	/**
	 * Retourne la liste des cases où l'adversaire peut prendre au 
	 * prochain coup, en d'autres termes les cases qui mettrait le
	 * roi de la couleur indiqué en echec.
	 * 
	 * @param color
	 * 				couleur du roi qui serait en echec.
	 * @return la liste des cases.
	 */
	public ArrayList<Square> echec(String color) {
		ArrayList<Square> echecList = new ArrayList<Square>();
		if (color.equals("black"))
			for (int i = 0; i < 32; ++i)
				if (this.pieces[i].isWhite())
					if (this.pieces[i] instanceof King)
						echecList.addAll(this.arroundSquares(this.pieces[i]));
					else
						echecList.addAll(this.pieces[i].possibleMoves(this));
		if (color.equals("white"))
			for (int i = 0; i < 32; ++i)
				if (this.pieces[i].isBlack())
					if (this.pieces[i] instanceof King)
						echecList.addAll(this.arroundSquares(this.pieces[i]));
					else
						echecList.addAll(this.pieces[i].possibleMoves(this));
		return echecList;
	}

	/**
	 * Détermine s'il y a echec et mat du joueur jouant color .
	 * 
	 * @param color
	 * 				couleur du joueur à évaluer
	 * @return vrai si le joueur color est en echec et mat, faux sinon.
	 */
	public boolean isEchecEtMat(String color) {
		for (int i = 0; i < 32; ++i) {
			if (this.pieces[i].getColor().equals(this.currentPlayer)) {
				ArrayList<Square> coups = this.pieces[i].possibleMovesSE(this);
				if (coups.size() > 0)
					return false;
			}
		}
		return true;
	}

	/**
	 * Détermine si la case (row,column) est prenable par
	 * l'adversaire au coup suivant. (le joueur considéré
	 * correspond à la couleur passée en paramètres).
	 * 
	 * @param color
	 * 				couleur du joueur
	 * @param row
	 * 				coordonnée en abscisse de la case.
	 * @param column
	 * 				coordonnée en ordonnée de la case.
	 * @return vrai si la case met le joueur en echec, faux sinon
	 */
	public boolean isEchec(String color, int row, int column) {
		ArrayList<Square> echecList = this.echec(color);
		return this.isEchec(echecList, row, column);
	}

	/**
	 * Détermine si la case (row,column) est prenable par
	 * l'adversaire au coup suivant. (le joueur considéré
	 * dépend de la liste des case passée). 
	 * 
	 * @param echecList
	 * 				liste des cases mettant le joueur en echec.
	 * @param row
	 * 				coordonnée en abscisse de la case.
	 * @param column
	 * 				coordonnée en ordonnée de la case.
	 * @return vrai si la case met le joueur en echec, faux sinon
	 */
	public boolean isEchec(ArrayList<Square> echecList, int row, int column) {
		Iterator<Square> it = echecList.iterator();
		while (it.hasNext()) {
			Square s = it.next();
			if (s.isThisSquare(row, column))
				return true;
		}
		return false;
	}

	/**
	 * getteur de la pièce contenu dans la case (row,column).
	 * 
	 * @param row
	 * 				coordonnée en abscisse de la case.
	 * @param column
	 * 				coordonnée en ordonnée de la case.
	 * @return la pièce contenu dans la case.
	 */
	public Piece getPiece(int row, int column) {
		assert (!this.isEmpty(row, column));
		int i = 0;
		while (i < 32
				&& !(this.getPieces()[i].getRow() == row && this.getPieces()[i]
						.getColumn() == column))
			i++;
		return this.getPieces()[i];
	}

	/**
	 * getteur de la pièce contenu dans la case (caseJeu).
	 * 
	 * @param caseJeu
	 * 				coordonnée en chaîne de caractère de la case.
	 * @return la pièce contenu dans la case.
	 * @throws OutOfBoardException
	 * @throws NonPossibleMoveException
	 */
	public Piece getPiece(String caseJeu) throws OutOfBoardException,
			NonPossibleMoveException {
		assert (!(caseJeu.charAt(0) < 'A') && !(caseJeu.charAt(0) > 'H')
				&& !(caseJeu.charAt(1) < '1') && !(caseJeu.charAt(1) > '8'));
		int column = caseJeu.charAt(0) - 'A' + '1' - 48;
		int row = caseJeu.charAt(1) - 48;
		return this.getPiece(row, column);
	}

	/**
	 * Déplace la pièce contenu dans la case (row1,column1) vers 
	 * la case (row2,column2).
	 * 
	 * @param row1
	 * 				coordonnée en abscisse de la case initiale.
	 * @param column1
	 * 				coordonnée en ordonnée de la case initiale.
	 * @param row2
	 * 				coordonnée en abscisse de la case de destination.
	 * @param column2
	 * 				coordonnée en ordonnée de la case de destination.
	 * @throws OutOfBoardException
	 * 				exception renvoyée si les coordonnées explicitées
	 * 				ne corresondent pas à une case du plateau.
	 * @throws NonPossibleMoveException
	 * 				exception renvoyée si le coup n'est pas jouable.
	 */
	public void deplacerPiece(int row1, int column1, int row2, int column2)
			throws OutOfBoardException, NonPossibleMoveException {
		this.getPiece(row1, column1).deplacerPiece(this, row2, column2);
	}

	/**
	 * Déplace la pièce contenu dans la case (caseDepart) vers 
	 * la case (caseArrivee).
	 * 
	 * @param caseDepart 
	 * 					coordonnees en chaine de caractères 
	 * 					de la case de départ.
	 * @param caseArrivee
	 * 					coordonnees en chaine de caractères 
	 * 					de la case d'arrivée.
	 * @throws OutOfBoardException
	 * 				exception renvoyée si les coordonnées explicitées
	 * 				ne corresondent pas à une case du plateau.
	 * @throws NonPossibleMoveException
	 * 				exception renvoyée si le coup n'est pas jouable.
	 */
	public void deplacerPiece(String caseDepart, String caseArrivee)
			throws OutOfBoardException, NonPossibleMoveException {
		assert (!(caseDepart.charAt(0) < 'A') && !(caseDepart.charAt(0) > 'H')
				&& !(caseDepart.charAt(1) < '1')
				&& !(caseDepart.charAt(1) > '8')
				&& !(caseArrivee.charAt(0) < 'A')
				&& !(caseArrivee.charAt(0) > 'H')
				&& !(caseArrivee.charAt(1) < '1') && !(caseArrivee.charAt(1) > '8'));
		int column1 = caseDepart.charAt(0) - 'A' + '1' - 48;
		int column2 = caseArrivee.charAt(0) - 'A' + '1' - 48;
		int row1 = caseDepart.charAt(1) - 48;
		int row2 = caseArrivee.charAt(1) - 48;
		this.deplacerPiece(row1, column1, row2, column2);
	}

	/**
	 * initialise une promotion
	 * 
	 * @param piece
	 * 				pièce à remplacer lors de la promotion
	 * 				les choix possibles sont "Rook", "Knight",
	 * 				"Bishop" ou "Queen".
	 */
	public void setPromotion(String piece) {
		Boolean promotion = false;
		Coup c = this.getListeCoups().get(this.getNumeroCoup());
		if (c.getIsPromotion() && c.getMovedPiece() instanceof Pawn)
			promotion = true;
		assert (promotion);
		Piece pion = c.getMovedPiece();
		Piece p = null;
		if (piece.equals("Rook"))
			p = new Rook(pion.getColor(), pion.getRow(), pion.getColumn());
		else if (piece.equals("Knight"))
			p = new Knight(pion.getColor(), pion.getRow(), pion.getColumn());
		else if (piece.equals("Bishop"))
			p = new Bishop(pion.getColor(), pion.getRow(), pion.getColumn());
		else if (piece.equals("Queen"))
			p = new Queen(pion.getColor(), pion.getRow(), pion.getColumn());
		int numPiece = 0;
		for (int i = 0; i < 32; ++i) {
			if (this.pieces[i].equals(c.getMovedPiece())) {
				numPiece = i;
				break;
			}
		}
		this.pieces[numPiece] = p;
		c.setMovedPiece(p);
		this.coupSuivant();
	}

	/**
	 * Rétablit un coup, (possible après un ou plusieurs annulerCoup)
	 * 
	 * @throws OutOfBoardException
	 * @throws NonPossibleMoveException
	 */
	public void retablirCoup() throws OutOfBoardException,
			NonPossibleMoveException {
		int max = this.indexMoveMax;
		HashMap<Integer, Coup> hash = new HashMap<Integer, Coup>();
		for (int i = this.indexMove; i < this.indexMoveMax; ++i)
			hash.put(i, this.listeCoups.get(i));
		Coup c = this.listeCoups.get(this.indexMove);
		Piece saveNewPiece = c.getMovedPiece();
		this.deplacerPiece(c.getCaseDepart().getNomCase(), c.getCaseArrivee()
				.getNomCase());
		if (c.getIsPromotion()) {
			if (saveNewPiece instanceof Rook)
				this.setPromotion("Rook");
			else if (saveNewPiece instanceof Knight)
				this.setPromotion("Knight");
			else if (saveNewPiece instanceof Bishop)
				this.setPromotion("Bishop");
			else if (saveNewPiece instanceof Queen)
				this.setPromotion("Queen");
		}
		this.indexMoveMax = max;
		for (int i = this.indexMove; i < this.indexMoveMax; ++i)
			this.listeCoups.put(i, hash.get(i));
	}

	/**
	 * Evalue s'il y a un cas de pat sur le plateau.
	 * 
	 * @return
	 */
	public Boolean isPat() {
		for (int i = 0; i < 32; ++i) {
			if (this.pieces[i].getColor().equals(this.currentPlayer)) {
				if (this.pieces[i] instanceof King)
					if (this.isEchec(this.pieces[i].getColor(),
							this.pieces[i].getRow(), this.pieces[i].getColumn()))
						return false;
				ArrayList<Square> coups = this.pieces[i].possibleMovesSE(this);
				if (coups.size() > 0)
					return false;
			}
		}
		return true;
	}
}
