package libraryMgmt;

public class Volume {

	boolean preso;
	
	//ctor
	public Volume(){
		this.preso=false;
	}
	
	//get and set
	public boolean isPreso() {
		return preso;
	}
	public void setPreso(boolean preso) {
		this.preso = preso;
	}
	
	//volume preso o restituito
	void preso() {
		this.preso=true;
	}
	void restituito() {
		this.preso=false;
	}
}
