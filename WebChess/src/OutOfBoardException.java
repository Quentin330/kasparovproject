
/**
 * Exception prévue pour un coup qui serait en dehors du plateau de jeu.
 */
public class OutOfBoardException extends NonPossibleMoveException {

	private static final long serialVersionUID = 1L;

	public OutOfBoardException(String s) {
		super(s);
	}

}
