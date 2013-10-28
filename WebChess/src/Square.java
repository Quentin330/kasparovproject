
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
	

}
