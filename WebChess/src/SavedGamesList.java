import java.io.Serializable;
import java.util.ArrayList;

/**
 * Liste des sauvegardes
 *
 */
public class SavedGamesList implements Serializable {

	/**
	 * Default serial version id
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Liste de sauvegardes
	 */
	private ArrayList<String> savedGames;

	/**
	 * getter de la liste des sauvegardes
	 * @return liste des sauvegardes
	 */
	public ArrayList<String> getSavedGames() {
		return savedGames;
	}

	/**
	 * setter de la liste des sauvegardes
	 * @param savedGames liste des sauvegardes
	 */
	public void setSavedGames(ArrayList<String> savedGames) {
		this.savedGames = savedGames;
	}

	/**
	 * Ajout d'une sauvegarde à la liste
	 * @param s une sauvegarde
	 */
	public void newSave(String s) {
		savedGames.add(s);
	}

	/**
	 * constructeur sans paramètre
	 */
	public SavedGamesList() {
		this.savedGames = new ArrayList<String>();
	}

	/**
	 * Verification qu'une sauvegarde existe
	 * @param s nom de sauvegarde
	 * @return true si la sauvegarde est dans la liste
	 */
	public Boolean isSave(String s) {
		return savedGames.contains(s);
	}

}
