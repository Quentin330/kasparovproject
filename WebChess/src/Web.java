import java.io.*;
import java.net.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Classe contenant le main servant à lancer le serveur
 * 
 */
public class Web {

	/**
	 * Lis un fichier du package
	 * 
	 * @param fichier
	 * @return le contenu du fichier
	 * @throws IOException
	 */
	static String lireFichier(String fichier) throws IOException {
		BufferedReader lecteurAvecBuffer = null;
		String ligne;
		String fileContent = "";
		try {
			lecteurAvecBuffer = new BufferedReader(new FileReader(fichier));
		} catch (FileNotFoundException exc) {
			System.out.println("Erreur d'ouverture");
			return lireFichier("pageNotFound.html");
		}
		while ((ligne = lecteurAvecBuffer.readLine()) != null)
			fileContent += ligne + "\n";
		lecteurAvecBuffer.close();
		// System.out.println("::: Fichier lu :::");
		return fileContent;
	}

	/**
	 * permet de remplir automatiquement le content-type de l'en tête
	 * 
	 * @param objet
	 *            (nom du fichier à lire)
	 * @return le type du fichier
	 */
	static String getContentType(String objet) {
		if (objet.contains(".html"))
			return "text/html";
		if (objet.contains(".css"))
			return "text/css";
		if (objet.contains(".svg"))
			return "image/svg+xml";
		if (objet.contains(".ico"))
			return "image/ico";
		if (objet.contains(".jpg"))
			return "image";
		return "text/html";
	}

	/**
	 * runner du serveur
	 * 
	 * @param args (ne s'en sert pas)
	 */
	public static void main(String[] args) {
		ServerSocket socketserver;
		try {
			socketserver = new ServerSocket(7777);
			Socket socket;
			InputStream istream;
			OutputStream ostream;
			// trouv� sur
			// http://stackoverflow.com/questions/1930158/how-to-parse-date-from-http-last-modified-header
			SimpleDateFormat format = new SimpleDateFormat(
					"EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
			SimpleDateFormat format2 = new SimpleDateFormat(
					"EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH);
			Board b = new Board();
			SavedGamesList save = new SavedGamesList();
			ObjectInputStream ois1;
			try {
				ois1 = new ObjectInputStream(new BufferedInputStream(
						new FileInputStream(new File("ListeSauvegardes.txt"))));
				save = (SavedGamesList) ois1.readObject();
				ois1.close();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e2) {
				e2.printStackTrace();
			}
			while (true) {
				String partieASupprimer = "";
				Boolean notModified = false;
				socket = socketserver.accept();
				istream = socket.getInputStream();
				ostream = socket.getOutputStream();
				byte[] buffer = new byte[1024];
				int nb = -1;
				String input = "";
				// System.out.println("====================== Nouvelle requete ====================\n");
				do {
					nb = istream.read(buffer);
					String tmp = new String(buffer, "UTF-8");
					input += tmp;
				} while (nb == 1024);
				// System.out.println(input);
				if (input.startsWith("GET ")) {
					String objet = "";
					int j = 5;
					while (input.charAt(j) != ' ') {
						objet += input.charAt(j);
						++j;
					}
					String fichier = "";
					String parametres = "";
					boolean passe = false;
					for (int i = 0; i < objet.length(); ++i)
						if (passe)
							parametres += objet.charAt(i);
						else if (objet.charAt(i) == '?')
							passe = true;
						else
							fichier += objet.charAt(i);
					// System.out.println("::: Lecture de " + fichier +
					// " avec comme parametres : " + parametres +" :::");
					try {
						String content = "";
						if (fichier.length() == 0
								|| fichier.equals("index.html")) {
							// avec parametres
							if (parametres.length() > 0) {
								// trouvé sur
								// http://www.tomsguide.fr/forum/id-553824/ecrire-lire-fichier-txt-java.html
								if (parametres.startsWith("Save")) {
									ObjectOutputStream oos;
									String file = parametres.substring(5);
									if (file.equals("ListeSauvegardes")
											|| save.isSave(file)) {
										// ne fait rien si le nom est déjà pris
									} else {
										try {
											oos = new ObjectOutputStream(
													new BufferedOutputStream(
															new FileOutputStream(
																	new File(
																			file
																					+ ".txt"))));
											oos.writeObject(b);
											oos.close();
											save.newSave(file);
											ObjectOutputStream oos2;
											oos2 = new ObjectOutputStream(
													new BufferedOutputStream(
															new FileOutputStream(
																	new File(
																			"ListeSauvegardes.txt"))));
											oos2.writeObject(save);
											oos2.close();
										} catch (java.io.IOException e) {
											e.printStackTrace();
										}
									}
								} else if (parametres.startsWith("Load")) {
									ObjectInputStream ois;
									String file = parametres.substring(5);
									if (save.isSave(file)) {
										try {
											ois = new ObjectInputStream(
													new BufferedInputStream(
															new FileInputStream(
																	new File(
																			file
																					+ ".txt"))));
											b = (Board) ois.readObject();
											ois.close();
										} catch (IOException e) {
											e.printStackTrace();
										} catch (ClassNotFoundException e2) {
											e2.printStackTrace();
										}
									} else {
										// ne fait rien si la sauvegarde est inexistante
									}
								} else if (parametres.startsWith("Suppr")) {
									String partie = parametres.substring(6);
									save.getSavedGames().remove(partie);
									File partieFile = new File(partie + ".txt");
									partieFile.delete();
									ObjectOutputStream oos2;
									oos2 = new ObjectOutputStream(
											new BufferedOutputStream(
													new FileOutputStream(
															new File(
																	"ListeSauvegardes.txt"))));
									oos2.writeObject(save);
									oos2.close();
								} else if (parametres.startsWith("delete")){
									partieASupprimer = parametres.substring(7);
									
								} else if (parametres.equals("Rook")
										|| parametres.equals("Knight")
										|| parametres.equals("Bishop")
										|| parametres.equals("Queen")) {
									Boolean promotion = false;
									if (b.getNumeroCoupMax() > b
											.getNumeroCoup()) {
										Coup c = b.getListeCoups().get(
												b.getNumeroCoup());
										if (c.getIsPromotion()
												&& c.getMovedPiece() instanceof Pawn) {
											promotion = true;
										}
									}
									if (promotion) {
										b.setPromotion(parametres);
									} else {
										// Ne fait rien si une piece est en parametre et que le coup n'est pas une promotion
									}
								} else if (parametres.equals("NewGame"))
									b = new Board();
								else if (parametres.equals("Undo"))
									if (b.getNumeroCoup() > 1) {
										b.annulerCoup();
									} else {
										// annulation Impossible car aucun coup n'a été joué
									}
								else if (parametres.equals("Redo"))
									if (b.getNumeroCoup() < b
											.getNumeroCoupMax()) {
										try {
											b.retablirCoup();
											b.nextPlayer();
										} catch (Exception e) {
											e.printStackTrace();
										}
									} else {
										// Redo Impossible car aucun coup n'a été annulé
									}
								// case ou l'on veut aller
								else if (parametres.startsWith("to")) {
									if ((parametres.charAt(2) < 'A')
											|| (parametres.charAt(2) > 'H')
											|| (parametres.charAt(3) < '1')
											|| (parametres.charAt(3) > '8')) {
										// Ne fait rien si les parametres sont hors du tableau
									} else {
										String nomCase = "";
										nomCase += parametres.charAt(2);
										nomCase += parametres.charAt(3);
										try {
											if (b.getPiece(b.getSelectedCase())
													.isPlayable(nomCase, b)) {
												try {
													b.deplacerPiece(
															b.getSelectedCase(),
															nomCase);
													b.setSelectedCase("00");
												} catch (NonPossibleMoveException e) {
													e.printStackTrace();
													b.nextPlayer();
												}
												b.nextPlayer();
											} else {
												// ne fait rien si la case n'est pas jouable
											}
										} catch (Exception e) {
											e.printStackTrace();
										}
									}
								}
								// pion que l'on selectionne
								else {
									if ((parametres.charAt(0) < 'A')
											|| (parametres.charAt(0) > 'H')
											|| (parametres.charAt(1) < '1')
											|| (parametres.charAt(1) > '8')) {
										// Ne fait rien si les parametres sont hors du tableau
									} else {
										String nomCase = "";
										nomCase += parametres.charAt(0);
										nomCase += parametres.charAt(1);
										if (b.isEmpty(nomCase)) {
											// Ne fait rien si on selectionne une case vide
										} else {
											try {
												if (b.getPiece(nomCase)
														.getColor()
														.equals(b
																.getCurrentPlayer()))
													b.setSelectedCase(nomCase);
												else {
													// Ne fait rien si la piece de la case en parametre n'est pas de la bonne couleur
												}
											} catch (Exception e) {
												e.printStackTrace();
											}
										}
									}
								}
							}
							// géneration de la page html
							try {
								HTMLGen html = new HTMLGen(b,
										save.getSavedGames(),
										partieASupprimer);
								content += html.getPage();
							} catch (Exception e) {
								e.printStackTrace();
							}
						} else {
							// gestion code 304
							if (input.contains("If-Modified-Since")) {
								String input2 = new String(input);
								while (!input2.startsWith("If-Modified-Since")) {
									input2 = input2.substring(1);
								}
								input2 = input2.substring(19, 48);
								{
									if (!input2.contains(",")) {
										input2 = input2.substring(0,
												input2.length() - 1);
									}
								}
								// System.out.println("\n------------------\n" +
								// input2 + "\n------------------\n");
								try {
									Date d = new Date();
									if (input2.contains(",")) {
										d = format2.parse(input2);
									} else {
										d = format.parse(input2);
									}
									Date fileDate = new Date();
									if (getContentType(fichier).contains(
											"image")) {
										String dateImage = "Tue Nov 19 20:35:08 CET 2013";
										fileDate = format.parse(dateImage);
									}
									if (fileDate.before(d)) {
										notModified = true;
									}
								} catch (ParseException e) {
									e.printStackTrace();
								}
							}
							if (!notModified) {
								content = lireFichier(fichier);
							}
						}
						String header = "";
						String output = "";
						Date date = new Date();
						if (notModified) {
							header = "HTTP/1.1 304 Not Modified"
									+ "\nServer: WebChess localhost:7777"
									+ "\nDate: " + format2.format(date)
									+ "\n\n";
							output = header;
						} else {
							// envoi de la page html au navigateur
							header = "HTTP/1.1 200 OK"
									+ "\nServer: WebChess localhost:7777"
									// Différente longueur de string et byte avec les accents en UTF-8
									+ "\nContent-Length: " + content.getBytes("UTF-8").length
									+ "\nConnection: close"
									+ "\nContent-Type: "
									+ getContentType(fichier)
									+ "; charset=UTF-8";
							header += "\nLast-Modified: ";
							if (getContentType(fichier).contains("image")) {
								header += "Tue, 19 Nov 2013 20:35:08 GMT";
							} else {
								header += date;
							}
							header += "\n\n";

							output = header + content;
						}
						// System.out.println(header);
						byte[] temp = output.getBytes("UTF-8");
						ostream.write(temp);
						ostream.flush();
					} catch (IOException e) {
						//  Auto-generated catch block
						e.printStackTrace();
					}
				}
				// socket.close();
				// socketserver.close();
			}
			// socketserver.close();
			// System.out.println("::: Deconnexion");

		} catch (IOException e1) {
			//  Auto-generated catch block
			e1.printStackTrace();
		}
	}

}
