import java.io.Serializable;
import java.util.ArrayList;

public class SavedGamesList implements Serializable {

	/**
	 * Default serial version id
	 */
	private static final long serialVersionUID = 1L;

	private ArrayList<String> savedGames;

	public ArrayList<String> getSavedGames() {
		return savedGames;
	}

	public void setSavedGames(ArrayList<String> savedGames) {
		this.savedGames = savedGames;
	}

	public void newSave(String s) {
		savedGames.add(s);
	}

	public SavedGamesList() {
		this.savedGames = new ArrayList<String>();
	}

	public Boolean isSave(String s) {
		return savedGames.contains(s);
	}

}
