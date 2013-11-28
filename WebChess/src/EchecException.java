
/**
 * Exception prévue pour un coup qui mettrai son roi en échec.
 */
public class EchecException extends NonPossibleMoveException {

	private static final long serialVersionUID = 1L;

	public EchecException(String s) {
		super(s);
	}

}
