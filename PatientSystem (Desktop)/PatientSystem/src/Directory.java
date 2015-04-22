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
//import javax.swing.AbstractListModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
//import javax.swing.ListSelectionModel;

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
	//Patient List
	private ArrayList<Patient> PatientList = new ArrayList<Patient>();
	private int CurrentUser = -1;
	
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
		Patient DummyUser = new Patient("John Smith", "jsmith", "bababa", "Favorite Color", "Blue", "Ramoray");
		PatientList.add(DummyUser);
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
				//call the function findUser that will return the position of the user in the ArrayList
				int position = findUser(textField_LoginUsername.getText());
				boolean found;
				if (position >= 0) //if position >=0 means that the user was found on the Patient List
					found = true;
				else //if the patient was not on the Patient List it was not found
					found = false;
				if(found==false){ //display message if user name does not exist
					JOptionPane.showMessageDialog(null, "Username does not exist.", "Error", JOptionPane.ERROR_MESSAGE);
				}
				else{ //if the user was found, check the password
					//check if the password matches
					Patient temp = new Patient(); //make a temporary patient
					temp = PatientList.get(position); //retrieve the corresponding user
					if (passwordField_Login.getText().equals(temp.getPassword())){ //check that password matches stored password
						textField_LoginUsername.setText("");//clear the fields
						passwordField_Login.setText("");
						panelLogin.setVisible(false); //set the panelLogin to not visible
						panelMainMenu.setVisible(true);  //set the MainMenu panel to visible
						CurrentUser = position; //save the current position of the user logged in
					}
					else { //show Incorrect password message
						JOptionPane.showMessageDialog(null, "Incorrect Password", "Error", JOptionPane.ERROR_MESSAGE);
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
		btnLoginForgottenCredentials.setBounds(108, 202, 188, 23);
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
				//check if user name exists
				int position = findUser(textField_ForgottenCredentialsUsername.getText());
				boolean found;
				if (position >= 0)
					found = true;
				else
					found = false;
				//if user name was not found display an error message
				if (found == false){
					JOptionPane.showMessageDialog(null, "Username was not found!", "Error", JOptionPane.ERROR_MESSAGE);
				}
				else { //if user name was found get secret question and display it to user
					Patient temp = new Patient();
					temp = PatientList.get(position);
					textField_ForgottenCredentialsSecretQuestion.setText(temp.getSecretQuestion());
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
				int position = findUser(textField_ForgottenCredentialsUsername.getText());
				Patient temp = new Patient();
				temp = PatientList.get(position);
				String answer = textField_ForgottenCredentialsSecretAnswer.getText();
				//compare the stored secret answer to the provided answer
				if (answer.equals(temp.getSecretAnswer())){
					String password = temp.getPassword();
					JOptionPane.showMessageDialog(null, "Password is: "+password, "Password Recovery", JOptionPane.WARNING_MESSAGE);
				}
				else {
					JOptionPane.showMessageDialog(null, "Incorrect Answer!", "Error", JOptionPane.ERROR_MESSAGE);
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
				Patient temp = new Patient();   //make a temporary patient
				temp = PatientList.get(CurrentUser); //set temporary equal to currentUser
				if (temp.surveyIsEmpty()==true){
					JOptionPane.showMessageDialog(null, "There are no surveys to view!", "Error", JOptionPane.ERROR_MESSAGE);
				}
				else {
					survey_list = new JList<String>(temp.listmodel);
					survey_list.setBorder(new LineBorder(new Color(0, 0, 0)));
					scroll_survey_list = new JScrollPane(survey_list);
					scroll_survey_list.setBounds(27, 77, 135, 94);
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
							Patient currentPatient = new Patient(); //make a new Patient to retrieve the Surveys
							currentPatient = PatientList.get(CurrentUser); //retrieve the Current User
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
		
		JButton btnMainMenuCompleteSurvey = new JButton("Complete Survey");
		btnMainMenuCompleteSurvey.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				spinnerCompleteSurveyDate.setModel(new SpinnerDateModel());
				spinnerCompleteSurveyDate.setEditor(new JSpinner.DateEditor(spinnerCompleteSurveyDate, "dd/MM/yyyy hh:mm:ss"));
				spinnerCompleteSurveyDate.setBounds(254, 123, 134, 20);
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
				String date = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").format(spinnerCompleteSurveyDate.getValue());
				//instantiate a temporary Patient and Survey to save the survey
				Patient temp = new Patient();
				temp = PatientList.get(CurrentUser);
				//add the survey to the Correct Patient
				Survey completedSurvey = new Survey(pain, tiredness, nausea, depression, anxiety, date);
//--------------completedSurvey.printAll();
				temp.addSurvey(completedSurvey);
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
		
		JButton btnViewHistoryPreviousScreen = new JButton("Previous Screen");
		btnViewHistoryPreviousScreen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelViewHistory.setVisible(false);
				panelMainMenu.setVisible(true);
				Patient temp = new Patient();
				temp = PatientList.get(CurrentUser);
				if (temp.surveyIsEmpty()==false){
					panelViewHistory.remove(textArea_ViewHistory_Surveys);
					panelViewHistory.remove(scroll_survey_list);
				}
				//textArea_ViewHistory_Surveys.setText("");
				//survey_list.removeAll();
				//panelViewHistory.remove(textArea_ViewHistory_Surveys);
				//panelViewHistory.remove(survey_list);
			}
		});

		btnViewHistoryPreviousScreen.setBounds(136, 193, 164, 23);
		panelViewHistory.add(btnViewHistoryPreviousScreen);
		
		JLabel lbl_View_History_Survey = new JLabel("Surveys:");
		lbl_View_History_Survey.setBounds(28, 63, 66, 14);
		panelViewHistory.add(lbl_View_History_Survey);
		
		//----------------------------------------------------------------------------------------------------
	}
	
	//method to find users 
	public int findUser(String pUsername){
		//check if user name exists
		boolean found = false;
		int length = PatientList.size(); //get the size of ArrayList
		Patient temp = new Patient();   //make a temporary Patient
		int position = -1;             //placeholder for found user
		int i = 0;                     //counter
		while ((found==false)&&(i<length)){ //go through ArrayList looking for match on user name
			temp = PatientList.get(i);
			if (pUsername.equals(temp.getUsername())){ //check if user name matches
				found = true;  // if it is found change condition and save the index
				position = i;
			}
			i++;  //increment counter
		}
		if (found == true)
			return position;
		else
			return -1;
	}
}
