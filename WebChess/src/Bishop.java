import java.util.ArrayList;


/**
 * TODO
 *
 */
public class Bishop extends Piece{

		/**
		 * TODO
		 * @param color
		 * @param heigth
		 * @param width
		 */
		public Bishop(String color, int heigth, int width){
			this.setColor(color);
			this.setRow(heigth);
			this.setColumn(width);
		}
		
		/**
		 * TODO
		 */
		public ArrayList<Square> possibleMoves(Board board){
			ArrayList<Square> movesList = new ArrayList<Square>();
			if (this.isDead()){
				return movesList;
			}
			return this.possibleMovesDiagonale(board);
		}
		
		/**
		 * TODO
		 */
		protected Bishop clone() {
			return new Bishop( this.getColor(), this.getRow(), this.getColumn());
		}
}
