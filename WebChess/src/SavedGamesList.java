import java.io.Serializable;
import java.util.ArrayList;

/**
 * TODO
 *
 */
public class SavedGamesList implements Serializable {

	/**
	 * Default serial version id
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * TODO
	 */
	private ArrayList<String> savedGames;

	/**
	 * TODO
	 * @return
	 */
	public ArrayList<String> getSavedGames() {
		return savedGames;
	}

	/**
	 * TODO
	 * @param savedGames
	 */
	public void setSavedGames(ArrayList<String> savedGames) {
		this.savedGames = savedGames;
	}

	/**
	 * TODO
	 * @param s
	 */
	public void newSave(String s) {
		savedGames.add(s);
	}

	/**
	 * TODO
	 */
	public SavedGamesList() {
		this.savedGames = new ArrayList<String>();
	}

	/**
	 * TODO
	 * @param s
	 * @return
	 */
	public Boolean isSave(String s) {
		return savedGames.contains(s);
	}

}
