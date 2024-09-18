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

public class MainActivity extends AppCompatActivity {

    // Declaring UI Elements
    private EditText firstNameET;
    private EditText lastNameET;
    private EditText DateEditText;
    private Button calcAgeBtn;

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
        if (DOB == "") {
            String nullAlert = "Please enter your birthdate in MM/dd/yyyy format. Blank or incomplete dates are not a valid input.";
            Toast DOBNullToast = Toast.makeText(this, nullAlert, toastDuration);
            DOBNullToast.show();
        }else if (DOB.length() != 10){
            String nullAlert = "Please enter your birthdate in MM/dd/yyyy format. Incomplete dates are not a valid input.";
            Toast DOBShortToast = Toast.makeText(this, nullAlert, toastDuration);
            DOBShortToast.show();
        }

        //if input is good proceed to parse with simpledateformat.parse

        //new SimpleDateFormat();

        // Getting the current date
        Calendar today = Calendar.getInstance();

    }

      //todos pending:
        //button listener for agecalculator
       //input validation for fname and lname
       //input validation for date? maybe depends on datapicker behaviour
       //calculate age logic
        //toast for invalid inputs
        //toast for resultant
}

