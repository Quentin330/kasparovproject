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
	public int getRow() {
		return row;
	}
	
	/**
	 * TODO
	 * @return
	 */
	public int getColumn() {
		return column;
	}
	
	/**
	 * TODO
	 * @param Column
	 * @param row
	 */
	public Square(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
	public Boolean isRealSquare(){
		return (this.row>0 && this.row<9 && this.column>0 && this.column<9);
	}
	
	/**
	 * TODO
	 * @param column
	 * @param row
	 * @return
	 */
	public boolean isThisSquare(int row, int column) {
		return (column == this.getColumn() && row == this.row);
	}
	
	/**
	 * TODO
	 * @return
	 */
	public String getNomCase() {
		String nom = new String();
		if (this.getColumn()==1)
			nom += "A";
		else if (this.getColumn()==2)
			nom += "B";
		else if (this.getColumn()==3)
			nom += "C";
		else if (this.getColumn()==4)
			nom += "D";
		else if (this.getColumn()==5)
			nom += "E";
		else if (this.getColumn()==6)
			nom += "F";
		else if (this.getColumn()==7)
			nom += "G";
		else if (this.getColumn()==8)
			nom += "H";
		nom += Integer.toString(this.getRow());
		return nom;
	}

}
