//Survey class that will be used to keep track of completed surveys
public class Survey {
	private int Pain;
	private int Drowsiness;
	private int Nausea;
	private int Appetite;
	private int ShortnessOfBreath;
	private int Depression;
	private int Anxiety;
	private int Wellbeing;
	private String Date;
	
	//constructor methods
	public Survey(){
		Pain = 0;
		Drowsiness = 0;
		Nausea = 0;
		Appetite = 0;
		ShortnessOfBreath = 0;
		Depression = 0;
		Anxiety = 0;
		Wellbeing = 0;
		Date = "";
	}
	
	public Survey(int pPain, int pDrowsiness, int pNausea, int pAppetite, int pShortnessOfBreath, int pDepression, int pAnxiety, int pWellbeing, String pDate){
		Pain = pPain;
		Drowsiness = pDrowsiness;
		Nausea = pNausea;
		Appetite = pAppetite;
		ShortnessOfBreath = pShortnessOfBreath;
		Depression = pDepression;
		Anxiety = pAnxiety;
		Wellbeing = pWellbeing;
		Date = pDate;
	}
	
	//getter methods
	public int getPain(){
		return Pain;
	}
	
	public int getDrowsiness(){
		return Drowsiness;
	}
	
	public int getNausea(){
		return Nausea;
	}
	
	public int getAppetite(){
		return Appetite;
	}
	
	public int getShortnessOfBreath(){
		return ShortnessOfBreath;
	}
	public int getDepression(){
		return Depression;
	}
	
	public int getAnxiety(){
		return Anxiety;
	}
	
	public int getWellbeing(){
		return Wellbeing;
	}
	
	public String getDate(){
		return Date;
	}
	
	//method to printAll attributes for debugging purposes
	public void printAll(){
		System.out.println("Pain is: "+Pain);
		System.out.println("Drowsiness is: "+Drowsiness);
		System.out.println("Nausea is: " +Nausea);
		System.out.println("Appetite is: "+Appetite);
		System.out.println("Shortness of Breath is:" +ShortnessOfBreath);
		System.out.println("Depression is: "+Depression);
		System.out.println("Anxiety is: "+Anxiety);
		System.out.println("Wellbeing is: "+Wellbeing);
		System.out.println("Date is: "+Date);
	}
	
	//method to generate String with all attributes' values
	public String getValuesOnString(){
	    String values = String.format(" Pain: \t            %s \n Drowsiness: \t            %s  \n Nausea: \t            %s \n Appetite: \t            %s \n Shortness of Breath:   %s \n Depression: \t            %s \n Anxiety \t            %s \n Wellbeing \t            %s "
	    		, Pain, Drowsiness, Nausea, Appetite, ShortnessOfBreath, Depression, Anxiety, Wellbeing);
	    return values;
	}
}
