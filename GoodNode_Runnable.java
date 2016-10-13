package Geyang;

import java.util.*;


public class GoodNode_Runnable extends GoodNode implements Runnable{
	

	public GoodNode_Runnable(int label) {
		// TODO Auto-generated constructor stub
		this.label = label;
		this.connectID = label;
		neighbors = new Vector<>();
		GoodNode_Runnable.nodesGroup.add(this);
	}
	
	public GoodNode_Runnable(int label, Vector<GoodNode_Runnable> vec, int numOfNds){
		this.label = label;
		this.connectID = label;
		this.neighbors = new Vector<>();
		GoodNode_Runnable.nodesGroup = vec;
		this.matrix = new int[numOfNds][numOfNds];
		randomMatrix(this.matrix);
		nodesGroup.add(this);
	}

	public void run(){
		while(true){
			try{
				Thread.sleep((int)( Math.random() * 10000));
				
			}catch(Exception e){
				 e.printStackTrace();
			}
			sendMessage(randomNeighbors());
		}
	}
	
	private Node randomNode(){
		int index = (int)(Math.random() * nodesGroup.size());
		
		return nodesGroup.elementAt(index) == this ? null: nodesGroup.elementAt(index);
	}
	
	private Node randomNeighbors(){
		int index = (int)(Math.random() * this.neighbors.size());
		
		return this.neighbors.elementAt(index) == this ? null: this.neighbors.elementAt(index);
	}
}
