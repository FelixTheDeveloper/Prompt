package nz.prompt.ui.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import nz.prompt.R;

public class BudgetActivity extends AppCompatActivity {


    private Button Subtract;
    private EditText TotalBudget;
    private EditText Expense;
    private EditText RemainingBudget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);

        Subtract = findViewById(R.id.subBtn);
        TotalBudget = findViewById(R.id.enterBudget);
        Expense = findViewById(R.id.enterExpense);
        RemainingBudget = findViewById(R.id.remainingBudget);

        //Subtracts the expenses to the budget
        Subtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double budget,expense,remaining;
                budget = Double.parseDouble(TotalBudget.getText().toString());
                expense = Double.parseDouble(Expense.getText().toString());
                remaining = budget-expense;
                RemainingBudget.setText(Double.toString(remaining));
            }
        });
    }
}
