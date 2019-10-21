package nz.prompt.ui.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import nz.prompt.R;
import nz.prompt.controllers.UserController;

/**
 * @author Carlo
 */
public class BudgetActivity extends AppCompatActivity {


    private Button Subtract;
    private TextView TotalBudget;
    private EditText Expense;
    private EditText RemainingBudget;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);

        Subtract = findViewById(R.id.budget_substractButton);
        TotalBudget = findViewById(R.id.budget_budgetInput);
        Expense = findViewById(R.id.budget_expenseTextBox);
        RemainingBudget = findViewById(R.id.budget_remainingBudgetTextBox);
        button = findViewById(R.id.budget_backbutton);

        button.setOnClickListener(v -> finish());

        TotalBudget.setText(String.valueOf(UserController.currentUser.getBudget()));

        //Subtracts the expenses to the budget
        Subtract.setOnClickListener(v -> {
            double budget, expense, remaining;
            budget = Double.parseDouble(TotalBudget.getText().toString());
            expense = Double.parseDouble(Expense.getText().toString());
            remaining = budget - expense;
            RemainingBudget.setText(Double.toString(remaining));
        });
    }
}
