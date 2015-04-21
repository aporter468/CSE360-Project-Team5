//Survey class that will be used to keep track of completed surveys
public class Survey {
	int Pain;
	int Tiredness;
	int Nausea;
	int Depression;
	int Anxiety;
	String Date;
	
	//constructor methods
	public Survey(){
		Pain = 0;
		Tiredness = 0;
		Nausea = 0;
		Depression = 0;
		Anxiety = 0;
		Date = "";
	}
	
	public Survey(int pPain, int pTiredness, int pNausea, int pDepression, int pAnxiety, String pDate){
		Pain = pPain;
		Tiredness = pTiredness;
		Nausea = pNausea;
		Depression = pDepression;
		Anxiety = pAnxiety;
		Date = pDate;
	}
	
	//getter methods
	public int getPain(){
		return Pain;
	}
	
	public int getTiredness(){
		return Tiredness;
	}
	
	public int getNausea(){
		return Nausea;
	}
	
	public int getDepression(){
		return Depression;
	}
	
	public int getAnxiety(){
		return Anxiety;
	}
	
	public String getDate(){
		return Date;
	}
	
	public void printAll(){
		System.out.println("Pain is: "+Pain);
		System.out.println("Tiredness is:" +Tiredness);
		System.out.println("Nausea is: " +Nausea);
		System.out.println("Depression is: "+Depression);
		System.out.println("Anxiety is: "+Anxiety);
		System.out.println("Date is: "+Date);
	}
	
	public String getValuesOnString(){
	    String values = String.format("Pain: %s \nTiredness: %s  \nNausea: %s \nDepression: %s "
	    		+ "\nAnxiety: %s", Pain, Tiredness, Nausea, Depression, Anxiety);
	    return values;
	}
}
