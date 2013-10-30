import java.util.ArrayList;



public class Bishop extends Piece{

		public Bishop(String color, int heigth, int width){
			this.setColor(color);
			this.setRow(heigth);
			this.setColumn(width);
		}
		
		public ArrayList<Square> possibleMoves(Board board){
			ArrayList<Square> movesList = new ArrayList<Square>();
			if (this.isDead()){
				return movesList;
			}
			return this.possibleMovesDiagonale(board);
		}
		
		protected Bishop clone() {
			return new Bishop( this.getColor(), this.getRow(), this.getColumn());
		}
}
