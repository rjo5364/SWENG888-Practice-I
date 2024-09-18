package edu.psu.sweng888.rafaelorozco.agecalculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.text.ParseException;

public class MainActivity extends AppCompatActivity {

    // Declaring UI Elements
    private EditText firstNameET;
    private EditText lastNameET;
    private EditText DateEditText;
    private Button calcAgeBtn;
    Boolean Warned = false;

    //adjusting window padding due to overlapping issues with calendar integration
    protected void onCreate(Bundle savedInstancesState) {
        super.onCreate(savedInstancesState);
        EdgeToEdge.enable(this);
        //setting the user interface for this particluar activity
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //iniatilzing the UI elements and setting them for each view
        firstNameET = findViewById(R.id.firstNameEditText);
        lastNameET = findViewById(R.id.lastNameEditText);
        DateEditText = findViewById(R.id.DateEditText);
        calcAgeBtn = findViewById(R.id.calculateAgeButton);

        //Calculate Age Button Listener, implementation ref https://developer.android.com/reference/android/view/View.OnClickListener

        calcAgeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateAgeMethod();
                //Bool to avoid multiple warnings
               Warned = false;
            }
        });
    }

    //validating user input and calculating age
    public void calculateAgeMethod(){

        //getting fname and lname inputs
        String fName = firstNameET.getText().toString();
        String lName = lastNameET.getText().toString();

        //string validation and toast: https://developer.android.com/guide/topics/ui/notifiers/toasts#show-toast
        int toastDuration = Toast.LENGTH_SHORT;
        if (fName == ""){
            String nullAlert = "Please enter a first name, blank is not a valid input";
            Toast fNameNullToast = Toast.makeText(this,nullAlert,toastDuration);
            fNameNullToast.show();
        }else if (lName == ""){

            String nullAlert = "Please enter a last name, blank is not a valid input";
            Toast lNameNullToast = Toast.makeText(this,nullAlert,toastDuration);
            lNameNullToast.show();
        }

        String DOB = DateEditText.getText().toString();
        if (Warned == false & DOB == "") {
            String nullAlert = "Please enter your birthdate in MM/dd/yyyy format. Blank or incomplete dates are not a valid input.";
            Toast DOBNullToast = Toast.makeText(this, nullAlert, toastDuration);
            DOBNullToast.show();
            Warned  = true;
        }else if (Warned == false & DOB.length() != 10){
            String nullAlert = "Please enter your birthdate in MM/dd/yyyy format. Incomplete dates are not a valid input.";
            Toast DOBShortToast = Toast.makeText(this, nullAlert, toastDuration);
            DOBShortToast.show();
            Warned  = true;
        }

       SimpleDateFormat formattedDate = new SimpleDateFormat("MM/dd/yyyy", Locale.US);

        try{
            Date birthDateFormatted = formattedDate.parse(DOB);

        if (birthDateFormatted.after(new Date())){
            String invalidAfterDateAlert = "You entered a date in the future. Please enter a date in the past.";
            Toast DOBAfterToast = Toast.makeText(this, invalidAfterDateAlert, toastDuration);
            DOBAfterToast.show();
        }else if(Warned != true & birthDateFormatted.before(new Date())){
        int resultAge = calculatedAge(birthDateFormatted);

        //Final Toast message if all checks go through
        String resultMessage = fName + " " + lName + " you are " + resultAge + " years old.";
        Toast resultMessageToast = Toast.makeText(this, resultMessage,toastDuration);
        resultMessageToast.show();
        }
        }catch (ParseException e){
            //When an invalid date is entered devDocs: https://stackoverflow.com/questions/22248311/how-to-handle-try-catch-exception-android
            //https://stackoverflow.com/questions/16116652/parseexception-java
                String anyInvalidDateAlert = "Invalid date format. Please enter date as MM/DD/YYYY.";
                Toast anyInvalidDateToast = Toast.makeText(this, anyInvalidDateAlert, toastDuration);
                anyInvalidDateToast.show();
                Warned = true;

        }

    }

    private int calculatedAge(Date DOB){
        // Getting the current date
        Calendar today = Calendar.getInstance();

        //calendarizing DOB
        Calendar birthDayCalendar = Calendar.getInstance();
        birthDayCalendar.setTime(DOB);

        //calculating date dif
        int age = today.get(Calendar.YEAR) - birthDayCalendar.get(Calendar.YEAR);

        //Checking to see if user BDAY has occured in current year
        if (today.before(birthDayCalendar)){
            age -= 1;
        }
        return age;
    }


      //todos pending:
        //button listener for agecalculator
       //input validation for fname and lname
       //input validation for date? maybe depends on datapicker behaviour
       //calculate age logic
        //toast for invalid inputs
        //toast for resultant
}

