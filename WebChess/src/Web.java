import java.io.*;
import java.net.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * TODO
 *
 */
public class Web {

	/**
	 * Lis un fichier du package
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
		} catch(FileNotFoundException exc) {
			System.out.println("Erreur d'ouverture");
			return lireFichier("pageNotFound.html");
		}
		while ((ligne = lecteurAvecBuffer.readLine()) != null)
			fileContent+=ligne +"\n";
		lecteurAvecBuffer.close();
		//System.out.println("::: Fichier lu :::");
		return fileContent;
	}

	/**
	 * permet de remplir automatiquement le content-type de l'en tête
	 * @param objet (nom du fichier à lire)
	 * @return le type du fichier
	 */
	static String getContentType(String objet) {
		if(objet.contains(".html"))
			return "text/html";
		if(objet.contains(".css"))
			return "text/css";
		if(objet.contains(".svg"))
			return "image/svg+xml";
		if(objet.contains(".ico"))
			return "image/ico";
		if(objet.contains(".jpg"))
			return "image";
		return "text/html";		
	}

	/**
	 * runner
	 * @param args
	 */
	public static void main(String[] args){
		ServerSocket socketserver;
		try {
			socketserver = new ServerSocket(7777);
			Socket socket;
			InputStream istream;
			OutputStream ostream;
			Board b = new Board();
			while(true) {
				Boolean notModified = false;
				socket = socketserver.accept();
				istream = socket.getInputStream();
				ostream = socket.getOutputStream();
				byte[] buffer = new byte[1024]; 
				int nb = -1;
				String input = "";
				//System.out.println("====================== Nouvelle requete ====================\n");
				do {
					nb = istream.read(buffer);
					String tmp = new String(buffer,"UTF-8");
					input += tmp;
				} while(nb == 1024);
				//System.out.println(input);
				if (input.startsWith("GET ")) {
					String objet = "";
					int j = 5;
					while (input.charAt(j)!=' ') {
						objet+=input.charAt(j);
						++j;
					}
					String fichier = "";
					String parametres= "";
					boolean passe = false;
					for(int i=0; i<objet.length(); ++i)
						if (passe)
							parametres += objet.charAt(i);
						else if (objet.charAt(i)=='?')
							passe=true;
						else
							fichier += objet.charAt(i);
					//System.out.println("::: Lecture de " + fichier + " avec comme parametres : " + parametres +" :::");
					try {
						String content = "";
						if (fichier.length()==0 || fichier.equals("index.html")) {
							//avec parametres
							if (parametres.length()>0) {
								if (parametres.equals("Rook") || parametres.equals("Knight") || parametres.equals("Bishop") || parametres.equals("Queen")){
									Boolean promotion = false;
									if (b.getNumeroCoupMax() > b.getNumeroCoup()){
										Coup c = b.getListeCoups().get(b.getNumeroCoup());
										if (c.getIsPromotion() && c.getMovedPiece() instanceof Pawn){
											promotion = true;
										}
									}
									if (promotion){
										b.setPromotion(parametres);
									}
									else {
										//TODO page HTML pour les tricheurs
									}
								}
								else if (parametres.equals("NewGame"))
									b = new Board();
								else if (parametres.equals("Undo"))
									if (b.getNumeroCoup()>1){
										b.annulerCoup();
									}
									else {
										//TODO Message d'erreur "annulation Impossible" (page html)
									}
								else if (parametres.equals("Redo"))
									if (b.getNumeroCoup()<b.getNumeroCoupMax()){
										try{
											b.retablirCoup();
											b.nextPlayer();
										} catch (Exception e) {
											e.printStackTrace();
										}
									}
									else {
										//TODO Message d'erreur "Redo Impossible" (page html)
									}
								//case ou l'on veut aller
								else if (parametres.startsWith("to")){
									if ( (parametres.charAt(2) < 'A')
											|| (parametres.charAt(2) > 'H')
											|| (parametres.charAt(3) < '1')
											|| (parametres.charAt(3) > '8')) {
										//TODO page HTML pour les tricheurs
									}
									else {
										String nomCase = "";
										nomCase += parametres.charAt(2);
										nomCase += parametres.charAt(3);
										try {
											if(b.getPiece(b.getSelectedCase()).isPlayable(nomCase, b)){
												try {
													b.deplacerPiece(b.getSelectedCase(), nomCase);
													b.setSelectedCase("00");
												} catch (NonPossibleMoveException e){
													e.printStackTrace();
													b.nextPlayer();
												}
												b.nextPlayer();
											}
											else {
												//TODO page HTML pour les tricheurs
											}
										} catch (Exception e) {
											e.printStackTrace();
										}
									}
								}
								//pion que l'on selectionne
								else {
									if ((parametres.charAt(0)<'A')
											|| (parametres.charAt(0)>'H')
											|| (parametres.charAt(1)<'1')
											|| (parametres.charAt(1)>'8')) {
										//TODO page HTML pour les tricheurs
									}
									else {
										String nomCase = "";
										nomCase += parametres.charAt(0);
										nomCase += parametres.charAt(1);
										if(b.isEmpty(nomCase)) {
											//TODO page HTML pour les tricheurs
										}
										else {
											try {
												if(b.getPiece(nomCase).getColor().equals(b.getCurrentPlayer()))
													b.setSelectedCase(nomCase);
												else {
													//TODO page HTML pour les tricheurs
												}
											} catch (Exception e) {
												e.printStackTrace();
											}
										}
									}
								}
							}
							try {
								HTMLGen html = new HTMLGen(b);
								content += html.getPage();
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						else{
							if (input.contains("If-Modified-Since")){
								String input2 = new String(input);
								while (!input2.startsWith("If-Modified-Since")){
									input2 = input2.substring(1);
								}
								input2 = input2.substring(19, 47);
								System.out.println("\n------------------\n" + input2 + "\n------------------\n");
								// trouv� sur http://stackoverflow.com/questions/1930158/how-to-parse-date-from-http-last-modified-header
								SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
								try {
									Date d = format.parse(input2);
									Date fileDate = new Date();
									if (getContentType(fichier).contains("image")){
										String dateImage = "Tue Nov 19 20:35:08 CET 2013";
										fileDate = format.parse(dateImage);
									}
									if (fileDate.before(d)){
										notModified = true;
									}
								} catch (ParseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							if (!notModified){
								content = lireFichier(fichier);
							}
						}
						String header = "";
						String output = "";
						if (notModified){
							header = "HTTP/1.1 304 OK";
							output = header;
						}
						else{
							header = "HTTP/1.1 200 OK" +
									"\nServer: WebChess localhost:7777" +
									"\nContent-Length: " + content.length() +
									"\nConnection: close" +
									"\nContent-Type: " + getContentType(fichier) + "; charset=UTF-8";
							header += "\nLast-Modified: ";
							if (getContentType(fichier).contains("image")){
								header += "Tue, 19 Nov 2013 20:35:08 GMT";
							}
							else {
								Date date = new Date();
								header += date;
							}
							header += "\n\n";

							output = header + content;
						}
						System.out.println(header);

						byte[] temp = output.getBytes();
						ostream.write(temp);
						/*for(int i=0; i<output.length() ; ++i){
							int temp = (int) output.charAt(i);
							ostream.write(temp);
						}*/
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				//socket.close();
				//socketserver.close();
			}
			//socketserver.close();
			//System.out.println("::: Deconnexion");



		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}
