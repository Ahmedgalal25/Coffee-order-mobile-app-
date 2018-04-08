package com.example.galal1.just_java;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the increment button is clicked.
     */

    public void increaseQuantity(View view) {
        quantity++;
        displayQuantity(quantity);

    }

    /**
     * This method is called when the decrement button is clicked.
     */
    public void decreaseQuantity(View view) {
        if(quantity<=0){
            quantity=0;
        }else{quantity--;}
        displayQuantity(quantity);

    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {



        //figure out the name and create an instance of *****EditText***
        EditText name = (EditText)findViewById(R.id.name_textEdit);
        String nameText = name.getText().toString();

        //figure out the checkBox of Whipped Cream
        CheckBox wippedCreamCheckBox = (CheckBox) findViewById(R.id.wipped_cream_checkbox);
        boolean hasWippedCream = wippedCreamCheckBox.isChecked();

        //figure out the checkBox of chocolate
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        // Calculate the price of coffee
        int price = caculatePrice(hasWippedCream, hasChocolate);

        Log.v("MainActivity","Name: "+nameText);


        String priceMessage = createOrderSummary(nameText,price,hasWippedCream,hasChocolate,nameText);

        // send the order summary int E-mail
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just_Java order from: "+ nameText);
        intent.putExtra(intent.EXTRA_TEXT,""+priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);

        }

        // Display the Order Summary

        displayMessage(priceMessage);

    }

    public void openMaps(View view){

    }

    /**
     *
     *
     * @param
     * @param
     * @return
     */
    private int caculatePrice(boolean hasWippedCream, boolean hasChocolate){
        int basePrice = 5;

        if(hasWippedCream){
            basePrice +=1;

        }
        if(hasChocolate){
            basePrice += 2;
        }

        return quantity * basePrice;
    }
    private String createOrderSummary(String name,int price, boolean addWippedCream, boolean addChocolate, String addNameText){
        String message = "\n"+getString(R.string.order_summary_name , name);
        message += "\n"+getString(R.string.order_summary_quantity,quantity);
        message +="\n"+ getString(R.string.order_summary_whipped_cream,addWippedCream);
        message +="\n"+ getString(R.string.order_summary_chocolate,addChocolate);
        message += "\n"+getString(R.string.order_summary_price, price);
        message +="\n" +getString(R.string.thank_you);
        return message;

    }


    /**
     * This method displays the given quantity value on the screen.
     *@param number is number of coffee cups
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);



    }
    /**
     * This method displays the given price on the screen.
     * @param
     */

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);

    }
}