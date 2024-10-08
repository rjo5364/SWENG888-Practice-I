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
        //calling onCreate superclass to hand defaults
        super.onCreate(savedInstancesState);
        EdgeToEdge.enable(this);
        //setting the user content view for this particluar activity
        setContentView(R.layout.activity_main);
        //using windowlistener to adjust padding on view based on users system
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            //getting the insets from nav and status bars
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            //applying revised padding to avoid overlapping on nav and staus bars
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
        //null chekcing for name values
        if (fName.isEmpty()){
            String nullAlert = "Please enter a first name, blank is not a valid input";
            Toast fNameNullToast = Toast.makeText(this,nullAlert,toastDuration);
            fNameNullToast.show();
            Warned  = true;
        }else if (lName.isEmpty()){

            String nullAlert = "Please enter a last name, blank is not a valid input";
            Toast lNameNullToast = Toast.makeText(this,nullAlert,toastDuration);
            lNameNullToast.show();
            Warned  = true;
        }
//null checking for values
        String DOB = DateEditText.getText().toString().trim();
        if (Warned == false & DOB.isEmpty()) {
            String nullAlert = "Please enter your birthdate in MM/dd/yyyy format. Blank or incomplete dates are not a valid input.";
            Toast DOBNullToast = Toast.makeText(this, nullAlert, toastDuration);
            DOBNullToast.show();
            Warned  = true;
        }

        // Define the expected date format
        SimpleDateFormat formattedDate = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        formattedDate.setLenient(false); // Strict date parsing

        Date birthDateFormatted;
        try {
            // Parse the date
            birthDateFormatted = formattedDate.parse(DOB);
        } catch (ParseException e) {
            String invalidDateAlert = "Invalid date format. Please enter date as MM/DD/YYYY.";
            Toast.makeText(this, invalidDateAlert, toastDuration).show();
            return; // Exit the method after showing the error
        }

                //checking to see if month is valid since calcs are only checking dates and years
                if (Warned == false & DOB.length() != 10) {
                    String nullAlert = "Please enter your birthdate in MM/dd/yyyy format. Incomplete dates are not a valid input.";
                    Toast DOBShortToast = Toast.makeText(this, nullAlert, toastDuration);
                    DOBShortToast.show();
                    Warned = true;
                }  else if (birthDateFormatted.after(new Date())) {
                    String invalidAfterDateAlert = "You entered a date in the future. Please enter a date in the past.";
                    Toast DOBAfterToast = Toast.makeText(this, invalidAfterDateAlert, toastDuration);
                    DOBAfterToast.show();
                } else if (Warned != true & !fName.isEmpty() & !lName.isEmpty()) {
                    //Final Toast message if all checks go through
                    int resultAge = calculatedAge(birthDateFormatted);
                    String resultMessage = fName + " " + lName + ", you are " + resultAge + " years old.";
                    Toast resultMessageToast = Toast.makeText(this, resultMessage, toastDuration);
                    resultMessageToast.show();
                }
    }
    private int calculatedAge(Date DOB) {
        // Getting the current date
        Calendar today = Calendar.getInstance();

        // Calendarizing DOB
        Calendar calendarizedDOB = Calendar.getInstance();
        calendarizedDOB.setTime(DOB);

        // Calculate initial age
        int age = today.get(Calendar.YEAR) - calendarizedDOB.get(Calendar.YEAR);

        // Adjust birth date to the current year
        calendarizedDOB.set(Calendar.YEAR, today.get(Calendar.YEAR));

        // If today's date is before the birthday this year, subtract one from age
        if (today.before(calendarizedDOB)) {
            age -= 1;
        }
        return age;
    }
}
