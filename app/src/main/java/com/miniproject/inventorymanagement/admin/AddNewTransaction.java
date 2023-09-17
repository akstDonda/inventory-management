package com.miniproject.inventorymanagement.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.Timestamp;
import com.miniproject.inventorymanagement.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddNewTransaction extends AppCompatActivity {

    private TextInputEditText textInputEditTextDate;
    TextInputEditText transactionIdEditBox,transactionDateEditBox,transactionQtyEditBox,transactionPriceEditBox;

    String transactionId;
    Integer transactionQuantity,transactionPrice;
    Timestamp transactionDate;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_transaction);

        textInputEditTextDate = findViewById(R.id.textInputEditTextDate);
        transactionIdEditBox = findViewById(R.id.transactionProductIdTextInputLayout);
        transactionDateEditBox=findViewById(R.id.productDateTextInputLayout);
        transactionQtyEditBox=findViewById(R.id.productQtyTextInputLayout);
        transactionPriceEditBox=findViewById(R.id.transactionPriceTextInputLayout);

        transactionId=transactionIdEditBox.getText().toString().trim();
        transactionQuantity=Integer.parseInt(transactionQtyEditBox.getText().toString().trim());
        transactionPrice=Integer.parseInt(transactionPriceEditBox.getText().toString().trim());





        // Get today's date
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0); // Set the time to midnight to include the whole day

        // Create a MaterialDatePicker for date selection
        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select a Date")
                .setCalendarConstraints(buildCalendarConstraints(today))
                .build();

        // Set the selected date in the TextInputEditText when a date is chosen
        datePicker.addOnPositiveButtonClickListener(selection -> {
            // Format the selected date
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
            textInputEditTextDate.setText(dateFormat.format(selection));
        });

        // Show the MaterialDatePicker when the TextInputEditText is clicked
        textInputEditTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker.show(getSupportFragmentManager(), "DATE_PICKER");
            }
        });


    }

    private CalendarConstraints buildCalendarConstraints(Calendar today) {
        CalendarConstraints.Builder constraintsBuilder = new CalendarConstraints.Builder();

        // Set a lower bound (e.g., January 1, 1900)
        Calendar lowerBound = Calendar.getInstance();
        lowerBound.set(1900, Calendar.JANUARY, 1);

        // Set an upper bound (today's date)
        constraintsBuilder.setStart(lowerBound.getTimeInMillis());
        constraintsBuilder.setEnd(today.getTimeInMillis());

        return constraintsBuilder.build();
    }
}