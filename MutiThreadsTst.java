package Geyang;

import java.util.*;

public class MutiThreadsTst {
	public static void main(String[] args){
		MutiThreadsTst test = new MutiThreadsTst();
		//test.MutiTreadsNodes();
		test.tstNodesManger();
	}
	
	private void MutiTreadsNodes(){
		Vector<GoodNode_Runnable> vec = new Vector<>();
		//GoodNode_Runnable.nodesGroup = vec;
		GoodNode_Runnable goodNode0 = new GoodNode_Runnable(0, vec);
		GoodNode_Runnable goodNode1 = new GoodNode_Runnable(1, vec);
		GoodNode_Runnable goodNode2 = new GoodNode_Runnable(2, vec);
		GoodNode_Runnable goodNode3 = new GoodNode_Runnable(3, vec);
		GoodNode_Runnable goodNode4 = new GoodNode_Runnable(4, vec);
		GoodNode_Runnable goodNode5 = new GoodNode_Runnable(5, vec);
		GoodNode_Runnable goodNode6 = new GoodNode_Runnable(6, vec);
		GoodNode_Runnable goodNode7 = new GoodNode_Runnable(7, vec);
		GoodNode_Runnable goodNode8 = new GoodNode_Runnable(8, vec);
		GoodNode_Runnable goodNode9 = new GoodNode_Runnable(9, vec);
		GoodNode_Runnable goodNode10 = new GoodNode_Runnable(10, vec);
		
		
		//goodnode1 does not connect to any of them
		goodNode0.getConnect(goodNode2);
		goodNode0.getConnect(goodNode3);
		goodNode3.getConnect(goodNode2);
		goodNode2.getConnect(goodNode4);
		goodNode4.getConnect(goodNode5);
		goodNode6.getConnect(goodNode5);
		goodNode6.getConnect(goodNode7);
		goodNode6.getConnect(goodNode8);
		goodNode7.getConnect(goodNode8);
		goodNode7.getConnect(goodNode9);
		//goodNode9.getConnect(goodNode3);
		goodNode10.getConnect(goodNode1);
		goodNode4.disConnect(goodNode5);
		goodNode8.getConnect(goodNode9);
		
		for(int i =0; i<vec.size() ;i++){
			GoodNode goodNode = vec.elementAt(i);
			System.out.println(goodNode.label + " : ");
			for(int j =0; j<goodNode.neighbors.size();j++){
				System.out.print(goodNode.neighbors.elementAt(j).label +" ");
			}
			System.out.println();
		}
		System.out.println("Connection ID of each node: ");
		for(int i =0; i<vec.size() ;i++){
			GoodNode goodNode = vec.elementAt(i);
			System.out.println("Node " + vec.elementAt(i).label+ " : " + goodNode.connectID );
		}
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		
		/*for(int i =0;i<vec.size();i++){
			GoodNode goodNode = vec.elementAt(i);
			for(int j=0;j<goodNode.nodesGroup.size();j++){
				System.out.println(goodNode.nodesGroup.elementAt(j).label+"");
			}
		}*/
		
		for(int i =0;i<vec.size();i++ ){
			new Thread(vec.elementAt(i)).start();
		}
	}
	
	public void tstNodesManger(){
		//NodesManger ndsMg= new NodesManger(10,15);
		NodesManger ndsMg= new NodesManger(10);
		ndsMg.test();
	}
}
