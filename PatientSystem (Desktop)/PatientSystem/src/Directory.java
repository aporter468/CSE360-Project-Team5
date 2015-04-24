import java.awt.CardLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import javax.swing.JList;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTextPane;

public class Directory {
    //declaration of private variables
	private JFrame frmEsasSystem;
	private JPanel panelLogin; 
	private JPanel panelSignUp;
	private JPanel panelForgottenCredentials;
	private JPanel panelMainMenu;
	private JPanel panelCompleteSurvey;
	private JPanel panelViewCareProviderInfo;
	private JPanel panelViewHistory;
	private JPanel panelDoctorSignUp;
	private JPanel panelMainMenuDoctor;
	private JPanel panelViewHistoryDoctor;
	private JPanel panelViewPatientInfo;
	
	//textField and passwordField from Login Panel
	private JTextField textField_LoginUsername;
	private JPasswordField passwordField_Login;
	
	//textFields and PasswordField from Sign Up Panel
	private JTextField textField_SignUpFirstName;
	private JTextField textField_SignUpLastName;
	private JTextField textField_SignUpUsername;
	private JTextField textField_SignUpSecretQuestion;
	private JTextField textField_SignUpCareProvider;
	private JPasswordField passwordField_SignUpPassword;
	private JPasswordField passwordField_SignUpConfirmPassword;
	private JTextField textField_SignUpSecretAnswer;

	//textFields from panelForgottenCredentials
	private JTextField textField_ForgottenCredentialsUsername;
	private JTextField textField_ForgottenCredentialsSecretAnswer;
	private JTextField textField_ForgottenCredentialsSecretQuestion;
	
	//JSpinner, JList, and JTextArea from CompleteSurvey Panel
	private JSpinner spinnerCompleteSurveyDate;
	private JList <String>survey_list;
	private JTextArea textArea_ViewHistory_Surveys;
	private JScrollPane scroll_survey_list;
	
	//Patient List & Doctor List
	private ArrayList<Patient> PatientList = new ArrayList<Patient>();
	private ArrayList<Doctor> DoctorList = new ArrayList<Doctor>();
	private Patient currentPatient = new Patient();
	private Doctor currentDoctor = new Doctor();
	
	//textFields and PasswordFields for the Doctor SignUp Panel
	private JTextField textField_DoctorSignUp_FirstName;
	private JTextField textField_DoctorSignUp_LastName;
	private JTextField textField_DoctorSignUp_Username;
	private JTextField textField_DoctorSignUp_SecurityQuestion;
	private JTextField textField_DoctorSignUp_SecurityAnswer;
	private JPasswordField passwordField_DoctorSignUp_Password;
	private JPasswordField passwordField_DoctorSignUp_ConfirmPassword;
	
	//JList and JTextArea from ViewPatientHistory as a Doctor
	private JList <String>patient_list;
	private JTextArea textArea_ViewPatient_Names;
	private JScrollPane scroll_patient_list;
	private JList <String>survey_list_doctor;
	private JScrollPane scroll_survey_list_doctor;
	
	//main method
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Directory window = new Directory(); //instantiate a new Directory
					window.frmEsasSystem.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	//constructor method
	public Directory() {
		initialize();
	}

	//initialize contents of frame
	private void initialize() {
		//REMOVE+++++++++++++++++++++++++++++++++++++++++=================+++++++++++++++++++++++++++++
		Patient DummyUser = new Patient("John Smith", "jsmith", "password", "Favorite Color?", "Blue", "Drake Ramoray");
		PatientList.add(DummyUser);
		Doctor DummyDoctor = new Doctor("Drake Ramoray", "dramoray", "password", "Favorite Food?", "Pizza");
		DummyDoctor.addPatientName("John Smith");
		DoctorList.add(DummyDoctor);
		//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		frmEsasSystem = new JFrame();
		frmEsasSystem.setTitle("ESAS System");
		frmEsasSystem.setBounds(100, 100, 450, 300);
		frmEsasSystem.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   //Set Exit_On_close behavior when window is closed
		frmEsasSystem.getContentPane().setLayout(new CardLayout(0, 0)); //set CardLayout
		
		//Login Panel ************************************************************************************
		panelLogin = new JPanel();
		frmEsasSystem.getContentPane().add(panelLogin, "name_136526590665903");
		panelLogin.setLayout(null);
		
		JButton btnLogin = new JButton("Log In");  // Log In 
		btnLogin.addActionListener(new ActionListener() { //Action Listener for Log In Button
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
				//check if user name exists
				//call the function findUser that will find if the user name is in the Patient List
				int position = findPatient(textField_LoginUsername.getText());
				if (position >= 0){ //check to see if the user name was found and in the Patient List
					Patient temp = new Patient(); //make a temporary patient
					temp = PatientList.get(position); //retrieve the corresponding user
					if (passwordField_Login.getText().equals(temp.getPassword())){ //check that password matches stored password
						textField_LoginUsername.setText("");//clear the fields
						passwordField_Login.setText("");
						panelLogin.setVisible(false); //set the panelLogin to not visible
						panelMainMenu.setVisible(true);  //set the MainMenu panel to visible
						currentPatient = temp;  //save the currently logged patient in currentPatient
					}
				}
				else { //look for the user name in the Doctor List
						position = findDoctor(textField_LoginUsername.getText());
						Doctor tempDoctor = new Doctor();
						if (position >=0) {
							tempDoctor = DoctorList.get(position); //retrieve the corresponding user
							textField_LoginUsername.setText("");//clear the fields
							passwordField_Login.setText("");
							panelMainMenuDoctor.setVisible(true); //set the MainMenuDoctor panel to visible
							panelLogin.setVisible(false);//set the panelLogin to not visible
							currentDoctor = tempDoctor; //save the currently logged patient in currentDoctor
						}
						
					} if (position == -1){ //if the user is not in the Patient List, then check the Doctor List
						position = findDoctor(textField_LoginUsername.getText());
						if (position >=0 ){ //the user name was found in the Doctor List
							Doctor tempDoctor = new Doctor(); //make a temporary doctor
							tempDoctor = DoctorList.get(position); //retrieve the corresponding user
							if (passwordField_Login.getText().equals(tempDoctor.getPassword())){ //check that password matches stored password
								textField_LoginUsername.setText("");//clear the fields
								passwordField_Login.setText("");
								panelLogin.setVisible(false); //set the panelLogin to not visible
								panelMainMenu.setVisible(true);  //set the MainMenu panel to visible
								currentDoctor = tempDoctor;
							}
						}
						else if (position==-1){//display message if user name does not exist
							JOptionPane.showMessageDialog(null, "Username does not exist.", "Error", JOptionPane.ERROR_MESSAGE);
						}
					} 
				
				}
		});
		btnLogin.setBounds(207, 168, 89, 23);
		panelLogin.add(btnLogin); //add the Login button to the JPanel
		
		JButton btnLoginSignUp = new JButton("Sign Up"); //SignUp Button
		btnLoginSignUp.addActionListener(new ActionListener() { //Action Listener for SignUp Button
			public void actionPerformed(ActionEvent e) {
				textField_LoginUsername.setText(""); //clear the user name and password fields
				passwordField_Login.setText("");
				panelLogin.setVisible(false); //set the Login panel to not visible
				panelSignUp.setVisible(true); //set the sign up panel to visible
			}
		});
		btnLoginSignUp.setBounds(108, 168, 89, 23);
		panelLogin.add(btnLoginSignUp);    //add the sign up button to the JPanel
		
		textField_LoginUsername = new JTextField(); //textField for Login User name
		textField_LoginUsername.setBounds(181, 85, 112, 20);
		panelLogin.add(textField_LoginUsername); //add the textField to the panel Login
		textField_LoginUsername.setColumns(10);
		
		JButton btnLoginForgottenCredentials = new JButton("Forgot you password?"); //Forgot Password Button
		btnLoginForgottenCredentials.addActionListener(new ActionListener() { //Action Listener for Forgot Pass button
			public void actionPerformed(ActionEvent e) {
				textField_LoginUsername.setText(""); //clear the textField and passwordField 
				passwordField_Login.setText("");
				panelLogin.setVisible(false); //set Login panel to not visible
				panelForgottenCredentials.setVisible(true); //set forgotten credentials panel to visible
			}
		});
		btnLoginForgottenCredentials.setBounds(108, 198, 188, 23);
		panelLogin.add(btnLoginForgottenCredentials);  //add the Forgotten Password button to the JPanel
		
		JLabel lblLoginTitle = new JLabel("Welcome to the ESAS System"); //Window Title
		lblLoginTitle.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblLoginTitle.setBounds(73, 23, 272, 23);
		panelLogin.add(lblLoginTitle);
		
		JLabel lblLoginUsername = new JLabel("Username:");  //User name Label
		lblLoginUsername.setHorizontalAlignment(SwingConstants.RIGHT);
		lblLoginUsername.setBounds(108, 88, 65, 14);
		panelLogin.add(lblLoginUsername);
		
		JLabel lblLoginPassword = new JLabel("Password:");  //password label
		lblLoginPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		lblLoginPassword.setBounds(108, 119, 65, 14);
		panelLogin.add(lblLoginPassword);
		
		passwordField_Login = new JPasswordField();   //JPasswordField for the password
		passwordField_Login.setBounds(181, 116, 112, 20);
		panelLogin.add(passwordField_Login);
		
		JButton btnDoctorSignUp = new JButton("Doctor Sign Up");
		btnDoctorSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				panelLogin.setVisible(false);
				panelDoctorSignUp.setVisible(true);
			}
		});
		btnDoctorSignUp.setBounds(108, 230, 188, 23);
		panelLogin.add(btnDoctorSignUp);
		
		panelLogin.setVisible(true); //Set the panelLogin to be visible
		//----------------------------------------------------------------------------------------------------
		
		//SignUp Panel ***************************************************************************************
		panelSignUp = new JPanel();
		frmEsasSystem.getContentPane().add(panelSignUp, "name_136909708117179");
		panelSignUp.setLayout(null);
		
		JLabel lblSignUpTitle = new JLabel("Create a New User");
		lblSignUpTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblSignUpTitle.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblSignUpTitle.setBounds(110, 25, 188, 28);
		panelSignUp.add(lblSignUpTitle);
		
		JLabel lblSignUpFirstname = new JLabel("First Name:");
		lblSignUpFirstname.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSignUpFirstname.setBounds(10, 64, 84, 14);
		panelSignUp.add(lblSignUpFirstname);
		
		JLabel lblSignUpLastName = new JLabel("Last Name:");
		lblSignUpLastName.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSignUpLastName.setBounds(20, 91, 73, 14);
		panelSignUp.add(lblSignUpLastName);
		
		JLabel lblSignUpUsername = new JLabel("Username:");
		lblSignUpUsername.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSignUpUsername.setBounds(246, 64, 73, 14);
		panelSignUp.add(lblSignUpUsername);
		
		JLabel lblSignUpPassword = new JLabel("Password:");
		lblSignUpPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSignUpPassword.setBounds(256, 91, 63, 14);
		panelSignUp.add(lblSignUpPassword);
		
		textField_SignUpFirstName = new JTextField();
		textField_SignUpFirstName.setBounds(103, 64, 86, 20);
		panelSignUp.add(textField_SignUpFirstName);
		textField_SignUpFirstName.setColumns(10);
		
		textField_SignUpLastName = new JTextField();
		textField_SignUpLastName.setBounds(103, 88, 86, 20);
		panelSignUp.add(textField_SignUpLastName);
		textField_SignUpLastName.setColumns(10);
		
		textField_SignUpUsername = new JTextField();
		textField_SignUpUsername.setBounds(329, 61, 86, 20);
		panelSignUp.add(textField_SignUpUsername);
		textField_SignUpUsername.setColumns(10);
		
		textField_SignUpSecretQuestion = new JTextField();
		textField_SignUpSecretQuestion.setBounds(125, 181, 124, 20);
		panelSignUp.add(textField_SignUpSecretQuestion);
		textField_SignUpSecretQuestion.setColumns(10);
		
		JButton btnSignUpCreateNewUser = new JButton("Register");
		btnSignUpCreateNewUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String firstName = textField_SignUpFirstName.getText();
				String lastName = textField_SignUpLastName.getText();
				String name = firstName + " " + lastName;
				String userName = textField_SignUpUsername.getText();
				String careProvider = textField_SignUpCareProvider.getText();
				String secretQuestion = textField_SignUpSecretQuestion.getText();
				String secretAnswer = textField_SignUpSecretAnswer.getText();
				@SuppressWarnings("deprecation")
				String password = passwordField_SignUpPassword.getText();
				@SuppressWarnings("deprecation")
				String passwordConfirmation = passwordField_SignUpConfirmPassword.getText();
				//check if passwords match
				if (!password.equals(passwordConfirmation)){
					JOptionPane.showMessageDialog(null, "Passwords do not match!", "Error", JOptionPane.ERROR_MESSAGE);
				} //check if password is at least 6 characters
				else if(password.length()<=5){
					JOptionPane.showMessageDialog(null, "Password must be at least 6 characters", "Error", JOptionPane.ERROR_MESSAGE);
				} //check if name is at least 3 characters long
				else if(name.length() <= 3){
					JOptionPane.showMessageDialog(null, "Invalid name", "Error", JOptionPane.ERROR_MESSAGE);
				} //check that user name is at least 6 characters long
				else if(userName.length()<= 5){
					JOptionPane.showMessageDialog(null, "Username too short", "Error", JOptionPane.ERROR_MESSAGE);
				}
				else{//create a new user and add it to the ArrayList
					Patient newUser = new Patient(name, userName, password, secretQuestion, secretAnswer, careProvider);
					PatientList.add(newUser);
					JOptionPane.showMessageDialog(null, "User: " + userName +" was created!");
					System.out.println("New user: " + name + " was added!");
					//clear textFields
					textField_SignUpFirstName.setText("");
					textField_SignUpLastName.setText("");
					textField_SignUpUsername.setText("");
					textField_SignUpCareProvider.setText("");
					textField_SignUpSecretQuestion.setText("");
					textField_SignUpSecretAnswer.setText("");
					passwordField_SignUpPassword.setText("");
					passwordField_SignUpConfirmPassword.setText("");
				}
			}
		});
		btnSignUpCreateNewUser.setBounds(259, 180, 146, 23);
		panelSignUp.add(btnSignUpCreateNewUser);
		
		JButton btnSignUpGoBack = new JButton("Previous Screen");
		btnSignUpGoBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//clear all of the textFields and passwordFields
				textField_SignUpFirstName.setText("");
				textField_SignUpLastName.setText("");
				textField_SignUpUsername.setText("");
				textField_SignUpSecretQuestion.setText("");
				textField_SignUpCareProvider.setText("");
				passwordField_SignUpPassword.setText("");
				passwordField_SignUpConfirmPassword.setText("");
				textField_SignUpSecretAnswer.setText("");
				panelSignUp.setVisible(false);
				panelLogin.setVisible(true);
			}
		});
		btnSignUpGoBack.setBounds(259, 214, 146, 23);
		panelSignUp.add(btnSignUpGoBack); //add the previous screen button
		
		textField_SignUpCareProvider = new JTextField();
		textField_SignUpCareProvider.setBounds(103, 119, 86, 20);
		panelSignUp.add(textField_SignUpCareProvider);
		textField_SignUpCareProvider.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Care Provider:");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setBounds(10, 122, 84, 14);
		panelSignUp.add(lblNewLabel);
		
		passwordField_SignUpPassword = new JPasswordField();
		passwordField_SignUpPassword.setBounds(329, 88, 86, 20);
		panelSignUp.add(passwordField_SignUpPassword);
		
		passwordField_SignUpConfirmPassword = new JPasswordField();
		passwordField_SignUpConfirmPassword.setBounds(329, 119, 86, 20);
		panelSignUp.add(passwordField_SignUpConfirmPassword);
		
		JLabel lblSignUpConfirmPassword = new JLabel("Confirm Password:");
		lblSignUpConfirmPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSignUpConfirmPassword.setBounds(200, 122, 119, 14);
		panelSignUp.add(lblSignUpConfirmPassword);
		
		JLabel lblSignUpSecretQuestion = new JLabel("Secret Question:");
		lblSignUpSecretQuestion.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSignUpSecretQuestion.setBounds(10, 184, 105, 14);
		panelSignUp.add(lblSignUpSecretQuestion);
		
		textField_SignUpSecretAnswer = new JTextField();
		textField_SignUpSecretAnswer.setBounds(125, 215, 124, 20);
		panelSignUp.add(textField_SignUpSecretAnswer);
		textField_SignUpSecretAnswer.setColumns(10);
		
		JLabel lblSignUpSecretAnswer = new JLabel("Secret Answer:");
		lblSignUpSecretAnswer.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSignUpSecretAnswer.setBounds(10, 218, 105, 14);
		panelSignUp.add(lblSignUpSecretAnswer);
		//----------------------------------------------------------------------------------------------------
		
		//Forgotten Credentials Panel*************************************************************************
		panelForgottenCredentials = new JPanel();
		frmEsasSystem.getContentPane().add(panelForgottenCredentials, "name_136911632557425");
		panelForgottenCredentials.setLayout(null);
		//declaration of JButtons and JLabels for the panel Forgotten credentials
		JButton btnForgottenCredentialsGenerateQuestion;
		JButton btnForgottenCredentialsAnswer;
		JLabel lblRecoverPassword;
		JLabel lblForgottenCredentialsInstructions;
		JLabel lblForgottenCredentialsUsername;
		JButton btnForgottenCredentialsPreviousScreen;
		
		lblRecoverPassword = new JLabel("Recover Your Password");
		lblRecoverPassword.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblRecoverPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblRecoverPassword.setBounds(98, 23, 215, 35);
		panelForgottenCredentials.add(lblRecoverPassword);
		
		btnForgottenCredentialsGenerateQuestion = new JButton("Generate Secret Question");
		btnForgottenCredentialsGenerateQuestion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//check if user name exists in the Patient List
				int position = findPatient(textField_ForgottenCredentialsUsername.getText());
				if (position >= 0) { //if it is on the Patient List then retrieve the secret question
					Patient temp = new Patient();
					temp = PatientList.get(position);
					textField_ForgottenCredentialsSecretQuestion.setText(temp.getSecretQuestion());
				} else {
					//if user was not found on the Patient List, look for it on the Doctor List
					position = findDoctor(textField_ForgottenCredentialsUsername.getText());
					if (position >= 0){
						Doctor tempDoctor = new Doctor();
						tempDoctor = DoctorList.get(position);
						textField_ForgottenCredentialsSecretQuestion.setText(tempDoctor.getSecurityQ());
					} else {
						JOptionPane.showMessageDialog(null, "Username was not found!", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		btnForgottenCredentialsGenerateQuestion.setBounds(58, 139, 280, 23);
		panelForgottenCredentials.add(btnForgottenCredentialsGenerateQuestion);
		
		textField_ForgottenCredentialsSecretAnswer = new JTextField();
		textField_ForgottenCredentialsSecretAnswer.setBounds(236, 173, 102, 20);
		panelForgottenCredentials.add(textField_ForgottenCredentialsSecretAnswer);
		textField_ForgottenCredentialsSecretAnswer.setColumns(10);
		
		btnForgottenCredentialsAnswer = new JButton("Check Answer");
		btnForgottenCredentialsAnswer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//check if the answer provided matches the Secret Answer stored
				//get the position of the user in the ArrayList to get the information stored
				int position = findPatient(textField_ForgottenCredentialsUsername.getText());
				if (position == -1){
					position = findDoctor(textField_ForgottenCredentialsUsername.getText());
					if (position >= 0){
						String answer = textField_ForgottenCredentialsSecretAnswer.getText();
						Doctor tempDoctor = new Doctor();
						tempDoctor = DoctorList.get(position);
						if (answer.equals(tempDoctor.getSecurityA())){
							String password = tempDoctor.getPassword();
							JOptionPane.showMessageDialog(null, "Password is: "+password, "Password Recovery", JOptionPane.WARNING_MESSAGE);
						}
						else {
							JOptionPane.showMessageDialog(null, "Incorrect Answer!", "Error", JOptionPane.ERROR_MESSAGE);
						}
					}
				}
				else {
					String answer = textField_ForgottenCredentialsSecretAnswer.getText();
					Patient temp = new Patient();
					temp = PatientList.get(position);
					if (answer.equals(temp.getSecretAnswer())){
						String password = temp.getPassword();
						JOptionPane.showMessageDialog(null, "Password is: "+password, "Password Recovery", JOptionPane.WARNING_MESSAGE);
					}
					else {
						JOptionPane.showMessageDialog(null, "Incorrect Answer!", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		btnForgottenCredentialsAnswer.setBounds(58, 204, 131, 23);
		panelForgottenCredentials.add(btnForgottenCredentialsAnswer);
		
		lblForgottenCredentialsInstructions = new JLabel("Enter your username and click on the Generate Secret Question Button");
		lblForgottenCredentialsInstructions.setBounds(37, 83, 353, 14);
		panelForgottenCredentials.add(lblForgottenCredentialsInstructions);
		
		textField_ForgottenCredentialsUsername = new JTextField();
		textField_ForgottenCredentialsUsername.setBounds(179, 108, 134, 20);
		panelForgottenCredentials.add(textField_ForgottenCredentialsUsername);
		textField_ForgottenCredentialsUsername.setColumns(10);
		
		lblForgottenCredentialsUsername = new JLabel("Username: ");
		lblForgottenCredentialsUsername.setHorizontalAlignment(SwingConstants.RIGHT);
		lblForgottenCredentialsUsername.setBounds(73, 108, 103, 14);
		panelForgottenCredentials.add(lblForgottenCredentialsUsername);
		
		btnForgottenCredentialsPreviousScreen = new JButton("Previous Screen");
		btnForgottenCredentialsPreviousScreen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//clear textFields
				textField_ForgottenCredentialsUsername.setText("");
				textField_ForgottenCredentialsSecretAnswer.setText("");
				textField_ForgottenCredentialsSecretQuestion.setText("");
				panelForgottenCredentials.setVisible(false);
				panelLogin.setVisible(true);
			}
		});
		btnForgottenCredentialsPreviousScreen.setBounds(204, 204, 134, 23);
		panelForgottenCredentials.add(btnForgottenCredentialsPreviousScreen);
		
		textField_ForgottenCredentialsSecretQuestion = new JTextField();
		textField_ForgottenCredentialsSecretQuestion.setHorizontalAlignment(SwingConstants.CENTER);
		textField_ForgottenCredentialsSecretQuestion.setEditable(false);
		textField_ForgottenCredentialsSecretQuestion.setBounds(58, 173, 156, 20);
		panelForgottenCredentials.add(textField_ForgottenCredentialsSecretQuestion);
		textField_ForgottenCredentialsSecretQuestion.setColumns(10);
		//----------------------------------------------------------------------------------------------------
		
		//MainMenu Panel *************************************************************************************
		panelMainMenu = new JPanel();
		frmEsasSystem.getContentPane().add(panelMainMenu, "name_136916106062093");
		panelMainMenu.setLayout(null);
		
		JLabel lblMainMenuWelcome = new JLabel("Welcome ");
		lblMainMenuWelcome.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblMainMenuWelcome.setHorizontalAlignment(SwingConstants.CENTER);
		lblMainMenuWelcome.setBounds(129, 31, 179, 22);
		panelMainMenu.add(lblMainMenuWelcome);
		//The View History Button checks if the user has surveys or not before going to 
		//the View History Panel
		JButton btnMainMenuViewHistory = new JButton("View History"); //View History button
		btnMainMenuViewHistory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelMainMenu.setVisible(false);  //hide Main Menu panel
				panelViewHistory.setVisible(true); //show the View History panel
				if (currentPatient.surveyIsEmpty()==true){
					JOptionPane.showMessageDialog(null, "There are no surveys to view!", "Error", JOptionPane.ERROR_MESSAGE);
				}
				else {
					survey_list = new JList<String>(currentPatient.getList());
					survey_list.setBorder(new LineBorder(new Color(0, 0, 0)));
					scroll_survey_list = new JScrollPane(survey_list);
					scroll_survey_list.setBounds(27, 77, 155, 94);
					panelViewHistory.add(scroll_survey_list);
					
					//add the TextArea
					textArea_ViewHistory_Surveys = new JTextArea();
					textArea_ViewHistory_Surveys.setBorder(new LineBorder(new Color(0, 0, 0)));
					textArea_ViewHistory_Surveys.setEditable(false);
					textArea_ViewHistory_Surveys.setBounds(211, 73, 183, 109);
					panelViewHistory.add(textArea_ViewHistory_Surveys);
					survey_list.addMouseListener(new MouseAdapter() { //mouse Listener for clicking on List
						@Override
						public void mouseClicked(MouseEvent e) { 
							int index = survey_list.locationToIndex(e.getPoint()); //get index where user clicked on
							Survey selectedSurvey = new Survey();	//make a new Survey to hold the information
							selectedSurvey = currentPatient.getSurvey(index); //retrieve the corresponding Survey
							String info = selectedSurvey.getValuesOnString(); //Obtain String with values
							textArea_ViewHistory_Surveys.setText(info); //display info on textArea
						}
					});
				}
				
			}
		});
		btnMainMenuViewHistory.setBounds(108, 93, 200, 23);
		panelMainMenu.add(btnMainMenuViewHistory);
		
		JButton btnMainMenuAccessCPInformation = new JButton("Access Care Provider Info");
		btnMainMenuAccessCPInformation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelMainMenu.setVisible(false);
				panelViewCareProviderInfo.setVisible(true);
			}
		});
		btnMainMenuAccessCPInformation.setBounds(108, 127, 200, 23);
		panelMainMenu.add(btnMainMenuAccessCPInformation);
		
		//Button to Complete Survey from the Main Menu
		JButton btnMainMenuCompleteSurvey = new JButton("Complete Survey");
		btnMainMenuCompleteSurvey.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				spinnerCompleteSurveyDate.setModel(new SpinnerDateModel());
				spinnerCompleteSurveyDate.setEditor(new JSpinner.DateEditor(spinnerCompleteSurveyDate, "dd/MM/yyyy hh:mm:ss a"));
				spinnerCompleteSurveyDate.setBounds(254, 123, 159, 20);
				panelCompleteSurvey.add(spinnerCompleteSurveyDate);
				panelMainMenu.setVisible(false);
				panelCompleteSurvey.setVisible(true);
			}
		});
		btnMainMenuCompleteSurvey.setBounds(108, 160, 200, 23);
		panelMainMenu.add(btnMainMenuCompleteSurvey);
		
		JLabel lblMainMenuSelectOption = new JLabel("Select of one the options below:");
		lblMainMenuSelectOption.setHorizontalAlignment(SwingConstants.CENTER);
		lblMainMenuSelectOption.setBounds(108, 64, 200, 14);
		panelMainMenu.add(lblMainMenuSelectOption);
		
		JButton btnMainMenuPreviousScreen = new JButton("Previous Screen");
		btnMainMenuPreviousScreen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelMainMenu.setVisible(false);
				panelLogin.setVisible(true);
			}
		});
		btnMainMenuPreviousScreen.setBounds(108, 194, 200, 23);
		panelMainMenu.add(btnMainMenuPreviousScreen);
		//----------------------------------------------------------------------------------------------------
		
		//Complete Survey Panel*******************************************************************************
		panelCompleteSurvey = new JPanel();
		frmEsasSystem.getContentPane().add(panelCompleteSurvey, "name_136918142667310");
		panelCompleteSurvey.setLayout(null);
		
		JLabel lblCompleteSurvey = new JLabel("Complete Survey");
		lblCompleteSurvey.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblCompleteSurvey.setHorizontalAlignment(SwingConstants.CENTER);
		lblCompleteSurvey.setBounds(111, 24, 216, 30);
		panelCompleteSurvey.add(lblCompleteSurvey);
		
		JLabel lblCompleteSurveyPain = new JLabel("Pain");
		lblCompleteSurveyPain.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCompleteSurveyPain.setBounds(48, 81, 46, 14);
		panelCompleteSurvey.add(lblCompleteSurveyPain);
		
		JLabel lblCompleteSurveyTiredness = new JLabel("Tiredness");
		lblCompleteSurveyTiredness.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCompleteSurveyTiredness.setBounds(27, 113, 67, 14);
		panelCompleteSurvey.add(lblCompleteSurveyTiredness);
		
		JLabel lblCompleteSurveyNausea = new JLabel("Nausea");
		lblCompleteSurveyNausea.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCompleteSurveyNausea.setBounds(48, 144, 46, 14);
		panelCompleteSurvey.add(lblCompleteSurveyNausea);
		
		JLabel lblCompleteSurveyDepression = new JLabel("Depression");
		lblCompleteSurveyDepression.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCompleteSurveyDepression.setBounds(27, 175, 67, 14);
		panelCompleteSurvey.add(lblCompleteSurveyDepression);
		
		JLabel lblCompleteSurveyAnxiety = new JLabel("Anxiety");
		lblCompleteSurveyAnxiety.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCompleteSurveyAnxiety.setBounds(48, 209, 46, 14);
		panelCompleteSurvey.add(lblCompleteSurveyAnxiety);
		
		JLabel lblCompleteSurveyDate = new JLabel("Date:");
		lblCompleteSurveyDate.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCompleteSurveyDate.setBounds(201, 126, 46, 14);
		panelCompleteSurvey.add(lblCompleteSurveyDate);
		
		spinnerCompleteSurveyDate = new JSpinner();
		
		JSpinner spinnerCompleteSurveyTiredness = new JSpinner();
		spinnerCompleteSurveyTiredness.setModel(new SpinnerNumberModel(1, 1, 10, 1));
		spinnerCompleteSurveyTiredness.setBounds(111, 110, 40, 20);
		panelCompleteSurvey.add(spinnerCompleteSurveyTiredness);
		
		JSpinner spinnerCompleteSurveyNausea = new JSpinner();
		spinnerCompleteSurveyNausea.setModel(new SpinnerNumberModel(1, 1, 10, 1));
		spinnerCompleteSurveyNausea.setBounds(111, 141, 40, 20);
		panelCompleteSurvey.add(spinnerCompleteSurveyNausea);
		
		JSpinner spinnerCompleteSurveyDepression = new JSpinner();
		spinnerCompleteSurveyDepression.setModel(new SpinnerNumberModel(1, 1, 10, 1));
		spinnerCompleteSurveyDepression.setBounds(111, 172, 40, 20);
		panelCompleteSurvey.add(spinnerCompleteSurveyDepression);
		
		JSpinner spinnerCompleteSurveyAnxiety = new JSpinner();
		spinnerCompleteSurveyAnxiety.setModel(new SpinnerNumberModel(1, 1, 10, 1));
		spinnerCompleteSurveyAnxiety.setBounds(111, 203, 40, 20);
		panelCompleteSurvey.add(spinnerCompleteSurveyAnxiety);
		
		JSpinner spinnerCompleteSurveyPain = new JSpinner();
		spinnerCompleteSurveyPain.setModel(new SpinnerNumberModel(1, 1, 10, 1));
		spinnerCompleteSurveyPain.setBounds(111, 78, 40, 20);
		panelCompleteSurvey.add(spinnerCompleteSurveyPain);
		
		JButton btnCompleteSurveySaveSurvey = new JButton("Save Survey");
		btnCompleteSurveySaveSurvey.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//collect current values from JSpinner
				int tiredness = (int)spinnerCompleteSurveyTiredness.getValue();
				int pain = (int)spinnerCompleteSurveyPain.getValue();
				int nausea = (int)spinnerCompleteSurveyNausea.getValue();
				int depression = (int)spinnerCompleteSurveyDepression.getValue();
				int anxiety = (int)spinnerCompleteSurveyAnxiety.getValue();
				String date = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a").format(spinnerCompleteSurveyDate.getValue());
				//add the survey to the Correct Patient
				Survey completedSurvey = new Survey(pain, tiredness, nausea, depression, anxiety, date);
//--------------completedSurvey.printAll();
				currentPatient.addSurvey(completedSurvey);
				//reset JSpinners to default values
				spinnerCompleteSurveyTiredness.setValue(1);
				spinnerCompleteSurveyPain.setValue(1);
				spinnerCompleteSurveyNausea.setValue(1);
				spinnerCompleteSurveyDepression.setValue(1);
				spinnerCompleteSurveyAnxiety.setValue(1);
				JOptionPane.showMessageDialog(null, "Survey was saved!");
			}
		});
		btnCompleteSurveySaveSurvey.setBounds(254, 166, 134, 23);
		panelCompleteSurvey.add(btnCompleteSurveySaveSurvey);
		
		JButton btnCompleSurveyPreviousScreen = new JButton("Previous Screen");
		btnCompleSurveyPreviousScreen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelCompleteSurvey.setVisible(false);
				panelMainMenu.setVisible(true);
			}
		});
		btnCompleSurveyPreviousScreen.setBounds(254, 205, 134, 23);
		panelCompleteSurvey.add(btnCompleSurveyPreviousScreen);
		
		JLabel lblCompleteSurveyDescription1 = new JLabel("1 is lowest level of symptom possible");
		lblCompleteSurveyDescription1.setBounds(174, 65, 242, 30);
		panelCompleteSurvey.add(lblCompleteSurveyDescription1);
		
		JLabel lblCompleteSurveyDescription2 = new JLabel("10 is highest level of symptom possible");
		lblCompleteSurveyDescription2.setHorizontalAlignment(SwingConstants.LEFT);
		lblCompleteSurveyDescription2.setBounds(174, 93, 242, 14);
		panelCompleteSurvey.add(lblCompleteSurveyDescription2);

		//----------------------------------------------------------------------------------------------------
		
		//View CareProvider Info Panel************************************************************************
		panelViewCareProviderInfo = new JPanel();
		frmEsasSystem.getContentPane().add(panelViewCareProviderInfo, "name_136919613899853");
		panelViewCareProviderInfo.setLayout(null);
		
		JLabel lblViewCPInfoTitle = new JLabel("Care Provider Information");
		lblViewCPInfoTitle.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblViewCPInfoTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblViewCPInfoTitle.setBounds(85, 39, 252, 22);
		panelViewCareProviderInfo.add(lblViewCPInfoTitle);
		
		JButton btnCareProviderInfoPreviousScreen = new JButton("Previous Screen");
		btnCareProviderInfoPreviousScreen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelViewCareProviderInfo.setVisible(false);
				panelMainMenu.setVisible(true);
			}
		});
		btnCareProviderInfoPreviousScreen.setBounds(128, 189, 158, 23);
		panelViewCareProviderInfo.add(btnCareProviderInfoPreviousScreen);
		
		JTextPane textPane = new JTextPane();
		textPane.setBounds(106, 93, 200, 50);
		panelViewCareProviderInfo.add(textPane);
		//----------------------------------------------------------------------------------------------------
		
		//View History Panel**********************************************************************************
		panelViewHistory = new JPanel();
		panelViewHistory.setBorder(new LineBorder(new Color(0, 0, 0)));
		frmEsasSystem.getContentPane().add(panelViewHistory, "name_136921281711804");
		panelViewHistory.setLayout(null);
		
		JLabel lblViewHistoryTitle = new JLabel("View History");
		lblViewHistoryTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblViewHistoryTitle.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblViewHistoryTitle.setBounds(125, 41, 175, 22);
		panelViewHistory.add(lblViewHistoryTitle);
		
		//Previous Screen Button for Main Menu 
		JButton btnViewHistoryPreviousScreen = new JButton("Previous Screen");
		btnViewHistoryPreviousScreen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelViewHistory.setVisible(false);
				panelMainMenu.setVisible(true);
				if (currentPatient.surveyIsEmpty()==false){
					panelViewHistory.remove(textArea_ViewHistory_Surveys);
					panelViewHistory.remove(scroll_survey_list);
				}
			}
		});

		btnViewHistoryPreviousScreen.setBounds(136, 193, 164, 23);
		panelViewHistory.add(btnViewHistoryPreviousScreen);
		
		JLabel lbl_View_History_Survey = new JLabel("Surveys:");
		lbl_View_History_Survey.setBounds(28, 63, 66, 14);
		panelViewHistory.add(lbl_View_History_Survey);
//--------------------------------------------------------------------------------------------------------------		
		
//Doctor Sign Up Panel *****************************************************************************************		
		panelDoctorSignUp = new JPanel();
		frmEsasSystem.getContentPane().add(panelDoctorSignUp, "name_27245650935221");
		panelDoctorSignUp.setLayout(null);
		
		JLabel lblCreateANew = new JLabel("Create A New User");
		lblCreateANew.setHorizontalAlignment(SwingConstants.CENTER);
		lblCreateANew.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblCreateANew.setBounds(109, 40, 211, 14);
		panelDoctorSignUp.add(lblCreateANew);
		
		textField_DoctorSignUp_FirstName = new JTextField();
		textField_DoctorSignUp_FirstName.setBounds(96, 70, 89, 20);
		panelDoctorSignUp.add(textField_DoctorSignUp_FirstName);
		textField_DoctorSignUp_FirstName.setColumns(10);
		
		textField_DoctorSignUp_LastName = new JTextField();
		textField_DoctorSignUp_LastName.setBounds(296, 70, 95, 20);
		panelDoctorSignUp.add(textField_DoctorSignUp_LastName);
		textField_DoctorSignUp_LastName.setColumns(10);
		
		textField_DoctorSignUp_Username = new JTextField();
		textField_DoctorSignUp_Username.setBounds(99, 101, 86, 20);
		panelDoctorSignUp.add(textField_DoctorSignUp_Username);
		textField_DoctorSignUp_Username.setColumns(10);
		
		textField_DoctorSignUp_SecurityQuestion = new JTextField();
		textField_DoctorSignUp_SecurityQuestion.setBounds(127, 177, 128, 20);
		panelDoctorSignUp.add(textField_DoctorSignUp_SecurityQuestion);
		textField_DoctorSignUp_SecurityQuestion.setColumns(10);
		
		textField_DoctorSignUp_SecurityAnswer = new JTextField();
		textField_DoctorSignUp_SecurityAnswer.setBounds(127, 208, 128, 20);
		panelDoctorSignUp.add(textField_DoctorSignUp_SecurityAnswer);
		textField_DoctorSignUp_SecurityAnswer.setColumns(10);
		
		JLabel lbl_DoctorSignUp_FirstName = new JLabel("First Name:");
		lbl_DoctorSignUp_FirstName.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_DoctorSignUp_FirstName.setBounds(0, 72, 86, 14);
		panelDoctorSignUp.add(lbl_DoctorSignUp_FirstName);
		
		JLabel lbl_DoctorSignUp_LastName = new JLabel("Last Name:");
		lbl_DoctorSignUp_LastName.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_DoctorSignUp_LastName.setBounds(209, 72, 76, 14);
		panelDoctorSignUp.add(lbl_DoctorSignUp_LastName);
		
		JLabel lbl_DoctorSignUp_Username = new JLabel("Username:");
		lbl_DoctorSignUp_Username.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_DoctorSignUp_Username.setBounds(23, 104, 66, 14);
		panelDoctorSignUp.add(lbl_DoctorSignUp_Username);
		
		passwordField_DoctorSignUp_Password = new JPasswordField();
		passwordField_DoctorSignUp_Password.setBounds(297, 101, 94, 20);
		panelDoctorSignUp.add(passwordField_DoctorSignUp_Password);
		
		passwordField_DoctorSignUp_ConfirmPassword = new JPasswordField();
		passwordField_DoctorSignUp_ConfirmPassword.setBounds(297, 132, 96, 20);
		panelDoctorSignUp.add(passwordField_DoctorSignUp_ConfirmPassword);
		
		JLabel lbl_DoctorSignUp_Password = new JLabel("Password:");
		lbl_DoctorSignUp_Password.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_DoctorSignUp_Password.setBounds(222, 104, 63, 14);
		panelDoctorSignUp.add(lbl_DoctorSignUp_Password);
		
		JLabel lbl_DoctorSignUp_ConfirmPassword = new JLabel("Confirm Password:");
		lbl_DoctorSignUp_ConfirmPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_DoctorSignUp_ConfirmPassword.setBounds(157, 135, 128, 14);
		panelDoctorSignUp.add(lbl_DoctorSignUp_ConfirmPassword);
		
		JLabel lbl_DoctorSignUp_SecurityQuestion = new JLabel("Security Question:");
		lbl_DoctorSignUp_SecurityQuestion.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_DoctorSignUp_SecurityQuestion.setBounds(10, 180, 112, 14);
		panelDoctorSignUp.add(lbl_DoctorSignUp_SecurityQuestion);
		
		JLabel lbl_DoctorSignUp_SecurityAnswer = new JLabel("Security Answer:");
		lbl_DoctorSignUp_SecurityAnswer.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_DoctorSignUp_SecurityAnswer.setBounds(23, 211, 99, 14);
		panelDoctorSignUp.add(lbl_DoctorSignUp_SecurityAnswer);
		
		JButton btn_DoctorSignUp_Register = new JButton("Register");
		btn_DoctorSignUp_Register.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String firstName = textField_DoctorSignUp_FirstName.getText();
				String lastName = textField_DoctorSignUp_LastName.getText();
				String name = firstName + " " + lastName;
				String userName = textField_DoctorSignUp_Username.getText();
				String secretQuestion = textField_DoctorSignUp_SecurityQuestion.getText();
				String secretAnswer = textField_DoctorSignUp_SecurityAnswer.getText();
				@SuppressWarnings("deprecation")
				String password = passwordField_DoctorSignUp_Password.getText();
				@SuppressWarnings("deprecation")
				String passwordConfirmation = passwordField_DoctorSignUp_ConfirmPassword.getText();
				//check if passwords match
				if (!password.equals(passwordConfirmation)){
					JOptionPane.showMessageDialog(null, "Passwords do not match!", "Error", JOptionPane.ERROR_MESSAGE);
				} //check if password is at least 6 characters
				else if(password.length()<=5){
					JOptionPane.showMessageDialog(null, "Password must be at least 6 characters", "Error", JOptionPane.ERROR_MESSAGE);
				} //check if name is at least 3 characters long
				else if(name.length() <= 3){
					JOptionPane.showMessageDialog(null, "Invalid name", "Error", JOptionPane.ERROR_MESSAGE);
				} //check that user name is at least 6 characters long
				else if(userName.length()<= 5){
					JOptionPane.showMessageDialog(null, "Username too short", "Error", JOptionPane.ERROR_MESSAGE);
				}
				else{//create a new user and add it to the ArrayList
					Doctor newUser = new Doctor(name, userName, password, secretQuestion, secretAnswer);
					DoctorList.add(newUser);
					JOptionPane.showMessageDialog(null, "User: " + userName +" was created!");
					System.out.println("New Doctor user: " + name + " was added!");
					//clear textFields
					textField_DoctorSignUp_FirstName.setText("");
					textField_DoctorSignUp_LastName.setText("");
					textField_DoctorSignUp_Username.setText("");
					textField_DoctorSignUp_SecurityQuestion.setText("");
					textField_DoctorSignUp_SecurityAnswer.setText("");
					passwordField_DoctorSignUp_Password.setText("");
					passwordField_DoctorSignUp_ConfirmPassword.setText("");
				}
			}
		});
		btn_DoctorSignUp_Register.setBounds(277, 176, 135, 23);
		panelDoctorSignUp.add(btn_DoctorSignUp_Register);
		
		JButton btn_DoctorSignUp_PreviousScreen = new JButton("Previous Screen");
		btn_DoctorSignUp_PreviousScreen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField_DoctorSignUp_FirstName.setText("");
				textField_DoctorSignUp_LastName.setText("");
				textField_DoctorSignUp_Username.setText("");
				textField_DoctorSignUp_SecurityQuestion.setText("");
				textField_DoctorSignUp_SecurityAnswer.setText("");
				passwordField_DoctorSignUp_Password.setText("");
				passwordField_DoctorSignUp_ConfirmPassword.setText("");
				panelDoctorSignUp.setVisible(false);
				panelLogin.setVisible(true);
			}
		});
		btn_DoctorSignUp_PreviousScreen.setBounds(279, 208, 133, 25);
		panelDoctorSignUp.add(btn_DoctorSignUp_PreviousScreen);
		//-----------------------------------------------------------------------------------------------------
		
		// Main Menu Doctor panel *****************************************************************************
		panelMainMenuDoctor = new JPanel();
		frmEsasSystem.getContentPane().add(panelMainMenuDoctor, "name_41199396829377");
		panelMainMenuDoctor.setLayout(null);
		
		JLabel lbl_MainMenuDoctor_Welcome = new JLabel("Welcome");
		lbl_MainMenuDoctor_Welcome.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_MainMenuDoctor_Welcome.setFont(new Font("Tahoma", Font.BOLD, 18));
		lbl_MainMenuDoctor_Welcome.setBounds(108, 25, 200, 33);
		panelMainMenuDoctor.add(lbl_MainMenuDoctor_Welcome);
		
		JButton btn_MainMenuDoctor_ViewPatientHistory = new JButton("View Patient History");
		btn_MainMenuDoctor_ViewPatientHistory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelMainMenuDoctor.setVisible(false);
				panelViewHistoryDoctor.setVisible(true);
				if (currentDoctor.isPatientListEmpty()==true){
					JOptionPane.showMessageDialog(null, "There are no patients to view!", "Error", JOptionPane.ERROR_MESSAGE);
				}
				else {
					//make and add the JList containing the patient's name associated with the doctor
					patient_list = new JList<String>(currentDoctor.getList());
					patient_list.setBorder(new LineBorder(new Color(0, 0, 0)));
					scroll_patient_list = new JScrollPane(patient_list);
					scroll_patient_list.setBounds(10, 77, 125, 95);
					panelViewHistoryDoctor.add(scroll_patient_list);

					patient_list.addMouseListener(new MouseAdapter() { //mouse Listener for clicking on List
						@Override
						public void mouseClicked(MouseEvent e) { 
							//use the click action to select and retrieve the surveys from a single patient user
							int index = patient_list.locationToIndex(e.getPoint()); //get index where user clicked on
							String patientName = currentDoctor.getPatientName(index);
							int position = findPatientByName(patientName);
							Patient tempPatient = PatientList.get(position);
							System.out.println("The index is: "+index);
							System.out.println("The patient is: "+tempPatient.getName());
							
							//add the second JList object
							survey_list_doctor = new JList<String>(tempPatient.getList());
							survey_list_doctor.setBorder(new LineBorder(new Color(0, 0, 0)));
							scroll_survey_list_doctor = new JScrollPane(survey_list_doctor);
							scroll_survey_list_doctor.setBounds(145, 77, 125, 95);
							panelViewHistoryDoctor.add(scroll_survey_list_doctor);
			
							//add the TextArea
							textArea_ViewPatient_Names = new JTextArea();
							textArea_ViewPatient_Names.setBorder(new LineBorder(new Color(0, 0, 0)));
							textArea_ViewPatient_Names.setEditable(false);
							textArea_ViewPatient_Names.setBounds(275, 77, 150, 110);
							panelViewHistoryDoctor.add(textArea_ViewPatient_Names);
							
							//refresh the panel to show both JList objects and the TextArea
							panelViewHistoryDoctor.setVisible(false);
							panelViewHistoryDoctor.setVisible(true);				
							
							//add the action listener that will retrieve the surveys for a specific user
							survey_list_doctor.addMouseListener(new MouseAdapter() { //mouse Listener for clicking on List
								@Override
								public void mouseClicked(MouseEvent e) { 
									int index = survey_list_doctor.locationToIndex(e.getPoint()); //get index where user clicked on
									Survey selectedSurvey = new Survey();	//make a new Survey to hold the information
									selectedSurvey = tempPatient.getSurvey(index); //retrieve the corresponding Survey
									String info = selectedSurvey.getValuesOnString(); //Obtain String with values
									textArea_ViewPatient_Names.setText(info); //display info on textArea
								}
							});
							
							
						}
					});
				}//end of the else
				
			}
		});
		btn_MainMenuDoctor_ViewPatientHistory.setBounds(108, 79, 200, 33);
		panelMainMenuDoctor.add(btn_MainMenuDoctor_ViewPatientHistory);
		
		JButton btn_MainMenuDoctor_ViewPatientInfo = new JButton("View Patient Contact Info");
		btn_MainMenuDoctor_ViewPatientInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelMainMenuDoctor.setVisible(false);
				panelViewPatientInfo.setVisible(true);
			}
		});
		btn_MainMenuDoctor_ViewPatientInfo.setBounds(108, 123, 200, 33);
		panelMainMenuDoctor.add(btn_MainMenuDoctor_ViewPatientInfo);
		
		JButton btn_View = new JButton("Previous Screen");
		btn_View.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelMainMenuDoctor.setVisible(false);
				panelLogin.setVisible(true);
			}
		});
		btn_View.setBounds(108, 169, 200, 33);
		panelMainMenuDoctor.add(btn_View);
		//-------------------------------------------------------------------------------------------------------------------
		
		//View History Doctor Panel************************************************************************************************
		panelViewHistoryDoctor = new JPanel();
		frmEsasSystem.getContentPane().add(panelViewHistoryDoctor, "name_44443382672325");
		panelViewHistoryDoctor.setLayout(null);
		
		JLabel lbl_ViewPatientHistory_title = new JLabel("View Patient History");
		lbl_ViewPatientHistory_title.setFont(new Font("Tahoma", Font.BOLD, 18));
		lbl_ViewPatientHistory_title.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_ViewPatientHistory_title.setBounds(97, 31, 207, 28);
		panelViewHistoryDoctor.add(lbl_ViewPatientHistory_title);
		
		JButton btn_ViewHistoryDoctor_PreviousScreen = new JButton("Previous Screen");
		btn_ViewHistoryDoctor_PreviousScreen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelViewHistoryDoctor.setVisible(false);
				panelMainMenuDoctor.setVisible(true);
			}
		});
		btn_ViewHistoryDoctor_PreviousScreen.setBounds(137, 223, 133, 28);
		panelViewHistoryDoctor.add(btn_ViewHistoryDoctor_PreviousScreen);
		//--------------------------------------------------------------------------------------------------------------------
		
		//View Patient Info as a Doctor panel************************************************************************************
		panelViewPatientInfo = new JPanel();
		frmEsasSystem.getContentPane().add(panelViewPatientInfo, "name_44471001791997");
		panelViewPatientInfo.setLayout(null);
		
		JLabel lblViewPatientInfo = new JLabel("View Patient Info");
		lblViewPatientInfo.setHorizontalAlignment(SwingConstants.CENTER);
		lblViewPatientInfo.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblViewPatientInfo.setBounds(107, 26, 201, 45);
		panelViewPatientInfo.add(lblViewPatientInfo);
		
		JButton btn_ViewPatientInfo_PreviousScreen = new JButton("Previous Screen");
		btn_ViewPatientInfo_PreviousScreen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelViewPatientInfo.setVisible(false);
				panelMainMenuDoctor.setVisible(true);
			}
		});
		btn_ViewPatientInfo_PreviousScreen.setBounds(148, 178, 136, 30);
		panelViewPatientInfo.add(btn_ViewPatientInfo_PreviousScreen);
		
		//----------------------------------------------------------------------------------------------------
	}
	
	//method to find user in either the PatientList or DoctorList 
	public int findPatient(String pUsername){
		//check if user name exists
		boolean found = false;
		int length = PatientList.size(); //get the size of ArrayList
		Patient tempPatient = new Patient();   //make a temporary Patient
		int position = -1;
		int i = 0;                     //counter
		while ((found==false)&&(i<length)){ //go through ArrayList looking for match on user name
			tempPatient = PatientList.get(i);
			if (pUsername.equalsIgnoreCase(tempPatient.getUsername())){ //check if user name matches
				found = true;  // if it is found change condition and save the index
				position = i; //save the current position 
			}
			i++;  //increment counter
		}		
		if (found==true)
			return position;
		else
		return -1;
	}
	
	public int findDoctor(String pUsername){
		//check if user name exists
		boolean found = false;
		int length = DoctorList.size(); //get the size of ArrayList
		Doctor tempDoctor = new Doctor();   //make a temporary Patient
		int position = -1;
		int i = 0;                     //counter
		while ((found==false)&&(i<length)){ //go through ArrayList looking for match on user name
			tempDoctor = DoctorList.get(i);
			if (pUsername.equalsIgnoreCase(tempDoctor.getUsername())){ //check if user name matches
				found = true;  // if it is found change condition and save the index
				position = i; //save the current position 
			}
			i++;  //increment counter
		}		
		if (found == true)
			return position;
		else
			return -1;
	}
	
	public int findPatientByName(String pName){
		//check if the name exists in the PatientList
		boolean found = false;
		int length = PatientList.size();
		Patient temp = new Patient();
		int position = -1; 
		int i = 0;
		while ((found==false)&&(i<length)){
			temp = PatientList.get(i);
			if (pName.equalsIgnoreCase(temp.getName())){
				found = true;
				position = i;
			}
			i++;
		}
		if (found == true)
			return position;
		else 
			return -1;
	}
}
