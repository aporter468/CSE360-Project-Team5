import java.util.ArrayList;
import javax.swing.DefaultListModel;

//This is the Patient class that will server to record individual patients information
public class Patient {
	private String Name;
	private String Username;
	private String Password;
	private String SecretQuestion;
	private String SecretAnswer;
	private String CareProvider;
	private ArrayList<Survey> Surveys = new ArrayList<Survey>();
	private DefaultListModel<String> surveyList = new DefaultListModel<String>();
	
	//constructor methods
	public Patient(String pName, String pUsername, String pPassword, String pSecretQuestion, String pSecretAnswer,
			String pCareProvider){
		Name = pName;
		Username = pUsername;
		Password = pPassword;
		SecretQuestion = pSecretQuestion;
		SecretAnswer = pSecretAnswer;
		CareProvider = pCareProvider;
	}
	
	public Patient() {
		Name = "";
		Username = "";
		Password = "";
		SecretQuestion = "";
		SecretAnswer = "";
		CareProvider = "";
	}

	//setter methods
	public void setName(String pName){
		Name = pName;
	}
	
	public void setUsername(String pUsername){
		Username = pUsername;
	}
	
	public void setPassword(String pPassword){
		Password = pPassword;
	}
	
	public void setSecretAnswer(String pSecretAnswer){
		SecretAnswer = pSecretAnswer;
	}
	
	public void setSecretQuestion(String pSecretQuestion){
		SecretQuestion = pSecretQuestion;
	}
	
	public void setCareProvider(String pCareProvider){
		CareProvider = pCareProvider;
	}
	
	public void addSurvey(Survey pSurvey){
		String survey_date = pSurvey.getDate();
		surveyList.addElement(survey_date);
		Surveys.add(pSurvey);
	}
	
	//getter methods
	public String getName(){
		return Name;
	}
	
	public String getUsername(){
		return Username;
	}
	
	public String getPassword(){
		return Password;
	}
	
	public String getSecretQuestion(){
		return SecretQuestion;
	}
	
	public String getSecretAnswer(){
		return SecretAnswer;
	}
	
	public String getCareProvider(){
		return CareProvider;
	}
	
	//method to get single survey based on position
	public Survey getSurvey(int position){
		return Surveys.get(position);
	}
	
	//method to return all of the surveys
	public ArrayList<Survey> getAllSurveys(){
		return Surveys;
	}
	
	//method to check if the ArrayList of Surveys is empty
	public boolean surveyIsEmpty(){
		if (Surveys.isEmpty()==true)
			return true;
		else
			return false;
	}
	
	public DefaultListModel<String> getList(){
		return surveyList;
	}
	
}
