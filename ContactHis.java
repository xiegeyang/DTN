package Geyang;
import java.io.Serializable;

public class ContactHis implements Serializable {
	/**
	 * 
	 */
//	private static final long serialVersionUID = 1L;
	private int times;

	public int getTimes() {
		return times;
	}

	public void setTimes(int time) {
		this.times = time;
	}
	
	public ContactHis(int time){
		this.times = time;
	}
	
}
