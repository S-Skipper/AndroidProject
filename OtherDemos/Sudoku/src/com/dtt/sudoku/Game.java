package com.dtt.sudoku;

public class Game {

	private final String puzzleString = "360000000004230800000004200"
			+"070460003820000014500013020"
			+"001900000007048300000000045";

	private int sudoku[] = new int [9*9];
	private int used[][][] = new int [9][9][10];
	
	public Game() {
		initSudoku();
		ComputeAllUsedNumbers();
	}
	
	private void initSudoku() {
		String s = getPuzzleString();
		for(int i=0;i<s.length();i++)
		{
			sudoku[i]=s.charAt(i)-'0';
		}
	}
	
	private String getPuzzleString() {
		return puzzleString;
	}

	private int getNumber(int x,int y)
	{
		return sudoku[x+y*9];
	}
	
	private void setNumber(int x,int y,int Num)
	{
		sudoku[x+y*9] = Num;
	}
	
	public boolean setNumberValid(int x,int y,int Num){
		int numbers[] = getUsedNumbers(x, y);
		
		if (Num != 0) {
			for (int i = 0; i < numbers.length; i++) {
				if (Num==numbers[i]) {
					return false;
				}
			}
		}
		
		setNumber(x, y, Num);
		ComputeAllUsedNumbers();
		return true;
	}
	
	public String getNumberStr(int x,int y)
	{
		int value = getNumber(x, y);
		if(value!=0) return String.valueOf(value); //value+"";
		else return "";
	}
	
	public int[] getUsedNumbers(int x,int y){
		return used[x][y];
	}
	
	public int[] computeUsedNumbers(int x,int y){
		int temp[] = new int[9];
		
		for(int i=0;i<9;i++)
		{
			if (i!=y) {
				int number=getNumber(x, i);
				if (number!=0) {
					temp[number-1]=1;
				}
			}
		}
		
		for(int i=0;i<9;i++)
		{
			if (i!=x) {
				int number=getNumber(i, y);
				if (number!=0) {
					temp[number-1]=1;
				}
			}
		}
		
		int startX = (x/3)*3;
		int startY = (y/3)*3;
		for(int i=startX;i<startX+3;i++)
		for(int j=startY;j<startY+3;j++)
		{
			if (i==x&&j==y) continue;
			
			int number = getNumber(i, j);	
			if (number!=0) {
				temp[number-1]=1;
			}
		}
		
		//compress
		int index=0;
		for (int i = 0; i < 9; i++) {
			if (temp[i]==1) {
				index++;
			}
		}
		int temp2[] = new int[index];
		index=0;
		for (int i = 0; i < 9; i++) {
			if (temp[i]==1) {
				temp2[index++]=i+1;
			}
		}
		return temp2;
	}
	
	public void ComputeAllUsedNumbers(){
		for(int i=0;i<9;i++)
		for(int j=0;j<9;j++)
		{
			if (getNumber(i, j)!=0) {
				used[i][j]=null;
				continue;
			}
			used[i][j] = computeUsedNumbers(i, j);
		}
	}
	
	
}
