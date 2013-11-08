import java.io.*;
import java.net.*;




public class Web {

	public static void main(String[] args) throws IOException {
		
		ServerSocket socket = new ServerSocket(7777);
		
		try{
			while(true) {
				Socket socketC = socket.accept();
				System.out.println("=================Connection================\n");
				InputStream is = socketC.getInputStream();
				byte[] buffer = new byte[1024]; 
				OutputStream os = socketC.getOutputStream();
				String input = "";
				int nb = -1;
				System.out.println("debut lecture");
				do{
					nb = is.read(buffer);
					String tmp = new String(buffer,"UTF-8");
					input += tmp;
				}while(nb == 1024);
				System.out.println("fin lecture");
				System.out.println(input);
				if (input.startsWith("GET ")){
					String objet = "";
					int j = 5;
					while (input.charAt(j)!=' '){
						objet+=input.charAt(j);
						++j;
					}
					System.out.println("lecture de "+objet+"\n");
					String content = lireFichier(objet);
					//TODO envoyer ligne par ligne
					String output = "HTTP 200 OK\nContent-type: "+getContentType(objet)+"\nContent-Length: "+content.length()+"\nConnection: close\n\n"+content;
					for(int i=0; i<output.length(); ++i){
						int temp = (int) output.charAt(i);
						os.write(temp);
					}
				}
				
				
				socket.close();
			}
		}
		catch(Exception err){
			System.exit(0);
		}
		//socket.close();
	}
	
	static String lireFichier(String fichier) throws IOException{
		BufferedReader lecteurAvecBuffer = null;
	    String ligne;
	    String fileContent = "";

	    try
	      {
		lecteurAvecBuffer = new BufferedReader(new FileReader(fichier));
	      }
	    catch(FileNotFoundException exc)
	      {
		System.out.println("Erreur d'ouverture");
	      }
	    while ((ligne = lecteurAvecBuffer.readLine()) != null)
	      fileContent+=ligne +"\n";
	    lecteurAvecBuffer.close();
	    return fileContent;
	}
	
	static String getContentType(String objet){
		if(objet.contains(".html"))
			return "text/html";
		if(objet.contains(".css"))
			return "text/css";
		if(objet.contains(".svg"))
			return "image";
		if(objet.contains(".ico"))
			return "image/ico";
		return "text/plain";		
	}
}
