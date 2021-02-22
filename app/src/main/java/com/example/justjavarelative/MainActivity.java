package com.example.justjavarelative;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity = 1;
    int price = 5;
    int creamToppingPrice = 1;
    int chocolateToppingPrice = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView coffeePrice = (TextView) findViewById(R.id.coffeePrice2);
        coffeePrice.setText("$" + price);
        TextView creamTopping = (TextView) findViewById(R.id.creamToppingPrice2);
        creamTopping.setText("$" + creamToppingPrice);
        TextView chocolateTopping = (TextView) findViewById(R.id.chocolateToppingPrice2);
        chocolateTopping.setText("$" + chocolateToppingPrice);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox creamCheckBox = (CheckBox) findViewById(R.id.whippedCream_checkBox);
        boolean hasTopping = creamCheckBox.isChecked();
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolateCream_checkBox);
        boolean hasChocolate = chocolateCheckBox.isChecked();
        EditText nameEditBox = (EditText) findViewById(R.id.name);
        Editable name = nameEditBox.getText();
        int total = calculateTotal();
        String priceMessage = createOrdersummary(total, hasTopping, hasChocolate, name);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java purchase order for"+name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

   /**
     * @param total
     * @param hasTopping
     * @return the message to be displayed
     */
    private String createOrdersummary(int total, boolean hasTopping, boolean hasChocolate, Editable name) {
        String orderSummary;
        int total2 = total;
        if (hasTopping) {
            total2 = total2 + creamToppingPrice;
            if (hasChocolate) {
                total2 = total2 + chocolateToppingPrice;
            }
        }
        orderSummary = "Name: " + name;
        orderSummary += "\nAdd whip cream? " + hasTopping;
        orderSummary += "\nAdd chocolate cream? " + hasChocolate;
        orderSummary += "\nQuantity:" + quantity;
        orderSummary += "\nTotal: $" + total2;
        orderSummary += "\nThank you!";
        return orderSummary;
    }

    /**
     * @return total amount
     */
    private int calculateTotal() {
        return quantity * price;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display() {
        TextView quantityTextView = findViewById(R.id.quantity_text_view);
        quantityTextView.setText(String.valueOf(quantity));
    }

    /**
     * increase the quantity
     */
    public void increase(View view) {
        if (quantity == 100) {
            display();
        } else {
            quantity = quantity + 1;
            display();
        }
    }

    /**
     * decrease the quantity
     */
    public void decrease(View view) {
        if (quantity == 1) {
            display();
        } else {
            quantity = quantity - 1;
            display();
        }
    }
}