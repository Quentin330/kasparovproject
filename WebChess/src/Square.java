
public class Square {
	
	private int width;
	private int heigth;
	
	public int getWidth() {
		return width;
	}
	/*public void setWidth(int width) {
		this.width = width;
	}*/
	public int getHeigth() {
		return heigth;
	}
	/*public void setHeigth(int heigth) {
		this.heigth = heigth;
	}*/
	
	
	public Square(int heigth, int width) {
		this.width = width;
		this.heigth = heigth;
	}
	
	public boolean isThisSquare(int heigth, int width){
		return (heigth == this.getHeigth() && width == this.width);
	}
	
	public String getNomCase(){
		String nom = new String();
		if (this.getWidth()==1){
			nom += "A";
		}
		else if (this.getWidth()==2){
			nom += "B";
		}
		else if (this.getWidth()==3){
			nom += "C";
		}
		else if (this.getWidth()==4){
			nom += "D";
		}
		else if (this.getWidth()==5){
			nom += "E";
		}
		else if (this.getWidth()==6){
			nom += "F";
		}
		else if (this.getWidth()==7){
			nom += "G";
		}
		else if (this.getWidth()==8){
			nom += "H";
		}
		nom += Integer.toString(this.getHeigth());
		return nom;
	}
	

}
