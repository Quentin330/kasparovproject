/**
 * TODO
 *
 */
public class Square {
	
	/**
	 * TODO
	 */
	private int row;
	/**
	 * TODO
	 */
	private int column;
	
	/**
	 * TODO
	 * @return
	 */
	public int getWidth() {
		return row;
	}
	
	/**
	 * TODO
	 * @return
	 */
	public int getHeigth() {
		return column;
	}
	
	/**
	 * TODO
	 * @param heigth
	 * @param width
	 */
	public Square(int heigth, int width) {
		this.row = width;
		this.column = heigth;
	}
	
	/**
	 * TODO
	 * @param heigth
	 * @param width
	 * @return
	 */
	public boolean isThisSquare(int heigth, int width){
		return (heigth == this.getHeigth() && width == this.row);
	}
	
	/**
	 * TODO
	 * @return
	 */
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
