package application;

public class Peg {
	private int isAlive; //-1 -> invalid, 0 -> not alive, 1-> alive
	private boolean isSelected; //if the peg is currently selected
	private int x; //x position for row postion
	private int y; //y position for col position

	
	public Peg(int isAlive, int x, int y) {
		this.isAlive = isAlive;//-1,0,1
		this.x = x;
		this.y = y;
		isSelected = false;
	}
	//setters
	public void setIsSelected(boolean isSelected){
		this.isSelected = isSelected;
	}
	public void setAlive(int isAlive) {
		//no exception error expected, input comes from logic not user
		this.isAlive = isAlive;
	}
	
	//getters
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getIsAlive() {
		return isAlive;
	}
	public boolean getIsSelected() {
		return isSelected;
	}
	public void flip() {
		//change peg state to opposite value
		if(isAlive == 0) {
			isAlive = 1;
		}
		else if(isAlive == 1) {
			isAlive = 0;
		}
	}
	
	
}
