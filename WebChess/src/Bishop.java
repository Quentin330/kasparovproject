import java.util.ArrayList;



public class Bishop extends Piece{

		public Bishop(String color, int heigth, int width){
			this.setColor(color);
			this.setHeigth(heigth);
			this.setWidth(width);
		}
		
		public ArrayList<Square> possibleMoves(Board board){
			ArrayList<Square> movesList = new ArrayList<Square>();
			if (this.isDead()){
				return movesList;
			}
			return this.possibleMovesDiagonale(board);
		}
}
