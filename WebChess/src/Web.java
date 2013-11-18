import java.io.*;
import java.net.*;

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
		System.out.println("::: Fichier lu :::");
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
				socket = socketserver.accept();
				istream = socket.getInputStream();
				ostream = socket.getOutputStream();
				byte[] buffer = new byte[1024]; 
				int nb = -1;
				String input = "";
				System.out.println("====================== Nouvelle requete ====================\n");
				do {
					nb = istream.read(buffer);
					String tmp = new String(buffer,"UTF-8");
					input += tmp;
				} while(nb == 1024);
				System.out.println(input);
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
					System.out.println("::: Lecture de " + fichier + " avec comme parametres : " + parametres +" :::");
					try {
						String content = "";
						if (fichier.length()==0 || fichier.equals("index.html")) {
							//avec parametres
							if (parametres.length()>0) {
								if (parametres.equals("NewGame"))
									b = new Board();
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
						else
							content = lireFichier(fichier);
						String header = "HTTP/1.1 200 OK" +
								"\nServer: WebChess localhost:7777" +
								"\nContent-Length: " + content.length() +
								"\nConnection: close" +
								"\nContent-Type: " + getContentType(fichier) + "; charset=UTF-8" +
								"\n\n";
						String output = header + content;

						System.out.println(header);

						for(int i=0; i<output.length() ; ++i){
							int temp = (int) output.charAt(i);
							ostream.write(temp);
						}
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
