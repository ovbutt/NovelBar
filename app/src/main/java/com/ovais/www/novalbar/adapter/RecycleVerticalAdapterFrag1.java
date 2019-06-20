package com.ovais.www.novalbar.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.ovais.www.novalbar.CustomMessageEvent;
import com.ovais.www.novalbar.InformationMessageEvent;
import com.ovais.www.novalbar.R;
import com.ovais.www.novalbar.SQLite.SQLiteHelper;
//import com.ovais.www.novalbar.testFragments.IngredientsQuantitiyHelper;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.MODE_PRIVATE;
import static android.view.View.GONE;

public class RecycleVerticalAdapterFrag1 extends RecyclerView.Adapter<RecycleVerticalAdapterFrag1.MyViewHolder> {
    private String[] mDataSet;
    private Context ctx;
    private static final String TAG = "HTAG";
    private static boolean defaultFlag = false;
    private String ingredientName;
    private InformationMessageEvent infoEvent = new InformationMessageEvent();
    //private int ingredientCount;
    private SQLiteHelper sqLiteHelper;
    String bitternessFlag, calories, carbCalorie, category, fatCalorie, name, portionFlag, portionSize, proteinCalorie, quantity="0", rda, servingSize, unhealthyFlag;
    private double calories1, carbCalorie1, fatCalorie1, portionSize1, proteinCalorie1, rda1;

    final String KEY_SAVED_1 = "SAVED_1";
    final String KEY_SAVED_2 = "SAVED_2";
    final String KEY_SAVED_3 = "SAVED_3";
    final String KEY_SAVED_4 = "SAVED_4";


    private int dialoguePosition;
    //private int position;

    private int [] quantity1 = new int[4];
    private String [] quantity3;

    //int quantity2 [] = {};

    //public List<String> quantity = new ArrayList<String>();

//    public String getTag(){
//        String tag = "Hello SearchDetailsActivity";
//        return tag;
//    }

    public RecycleVerticalAdapterFrag1(){}

    public RecycleVerticalAdapterFrag1(Context ctx, String[] mDataSet) {
        this.mDataSet = mDataSet;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_row_vertical, viewGroup, false);
        final MyViewHolder viewHolder = new MyViewHolder(view);
        //viewHolder.btnImageMinus.setVisibility(View.GONE);
        sqLiteHelper = new SQLiteHelper(ctx);
        quantity3 = new String[mDataSet.length];
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {

       // position = i;
        getInfo();
        myViewHolder.txtName.setText(mDataSet[i]);
        myViewHolder.editNumber.setText(String.valueOf(quantity));
        myViewHolder.btnImageMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myViewHolder.btnImagePluse.setVisibility(View.VISIBLE);

                final String numberCount;
                numberCount = myViewHolder.editNumber.getText().toString();
                int myCount = Integer.parseInt(numberCount);
                myCount = myCount - 1;
//                mListener.onMessageReadListener(myCount);
                quantity1 [i] = myCount;

                String myCountString = String.valueOf(myCount);
                quantity3[i] = myCountString;
                StringBuilder stringBuilder = new StringBuilder();
                for (String s: quantity3 ){
                    stringBuilder.append(s);
                    stringBuilder.append(",");

                    Log.d(TAG, "onClick: StringBuilder: " + s);
                }

                SharedPreferences sharedPreferences1 = ctx.getSharedPreferences("Pref", 0);
                SharedPreferences.Editor editor = sharedPreferences1.edit();
                editor.putString("words", stringBuilder.toString());
                editor.apply();

//                Log.d(TAG, "getQuantity: Values are: " + quantity1[0] + quantity1[1] + quantity1 [2] + quantity1[3]);

                switch (i)
                {

                    case 0:
                        CustomMessageEvent event = new CustomMessageEvent();
                        event.setCustomMessage(String.valueOf(-4));
                        event.setCustomMessage2(String.valueOf(-4));
                        EventBus.getDefault().post(event);
                        ingredientName = myViewHolder.txtName.getText().toString();
                        sqLiteHelper.addQuantity(ingredientName, myCount);
                        getInfo();
                        infoEvent = new InformationMessageEvent();
                        infoEvent.setCalories(String.valueOf(calories1*-1));
                        infoEvent.setCarbCalories(String.valueOf(carbCalorie1*-1));
                        infoEvent.setFatCalories(String.valueOf(fatCalorie1*-1));
                        infoEvent.setProteinCalories(String.valueOf(proteinCalorie1*-1));
                        infoEvent.setRda(String.valueOf(rda1*-1));
                        infoEvent.setPortionSize(String.valueOf(portionSize1*-1));
                        EventBus.getDefault().post(infoEvent);
                        break;
                    case 1:
                        event = new CustomMessageEvent();
                        event.setCustomMessage(String.valueOf(-1));
                        event.setCustomMessage2(String.valueOf(-1));
                        EventBus.getDefault().post(event);
                        ingredientName = myViewHolder.txtName.getText().toString();
                        sqLiteHelper.addQuantity(ingredientName, myCount);
                        getInfo();
                        infoEvent = new InformationMessageEvent();
                        infoEvent.setCalories(String.valueOf(calories1*-1));
                        infoEvent.setCarbCalories(String.valueOf(carbCalorie1*-1));
                        infoEvent.setFatCalories(String.valueOf(fatCalorie1*-1));
                        infoEvent.setProteinCalories(String.valueOf(proteinCalorie1*-1));
                        infoEvent.setRda(String.valueOf(rda1*-1));
                        infoEvent.setPortionSize(String.valueOf(portionSize1*-1));
                        EventBus.getDefault().post(infoEvent);
                        break;
                    case 2:
                        event = new CustomMessageEvent();
                        event.setCustomMessage(String.valueOf(-4));
                        event.setCustomMessage2(String.valueOf(-4));
                        EventBus.getDefault().post(event);
                        ingredientName = myViewHolder.txtName.getText().toString();
                        sqLiteHelper.addQuantity(ingredientName, myCount);
                        getInfo();
                        infoEvent.setCalories(String.valueOf(calories1*-1));
                        infoEvent.setCarbCalories(String.valueOf(carbCalorie1*-1));
                        infoEvent.setFatCalories(String.valueOf(fatCalorie1*-1));
                        infoEvent.setProteinCalories(String.valueOf(proteinCalorie1*-1));
                        infoEvent.setRda(String.valueOf(rda1*-1));
                        infoEvent.setPortionSize(String.valueOf(portionSize1*-1));
                        EventBus.getDefault().post(infoEvent);
                        break;
                    case 3:
                        event = new CustomMessageEvent();
                        event.setCustomMessage(String.valueOf(-1));
                        event.setCustomMessage2(String.valueOf(-1));
                        EventBus.getDefault().post(event);
                        ingredientName = myViewHolder.txtName.getText().toString();
                        sqLiteHelper.addQuantity(ingredientName, myCount);
                        getInfo();
                        infoEvent.setCalories(String.valueOf(calories1*-1));
                        infoEvent.setCarbCalories(String.valueOf(carbCalorie1*-1));
                        infoEvent.setFatCalories(String.valueOf(fatCalorie1*-1));
                        infoEvent.setProteinCalories(String.valueOf(proteinCalorie1*-1));
                        infoEvent.setRda(String.valueOf(rda1*-1));
                        infoEvent.setPortionSize(String.valueOf(portionSize1*-1));
                        EventBus.getDefault().post(infoEvent);
                        break;
                }

                Log.d(TAG, "onComplete: Value Posted");
                if (myCount <= 0) {
                    myViewHolder.editNumber.setText("0");
                    myViewHolder.btnImageMinus.setVisibility(View.GONE);
                    myCount =0;

                } else {
                    String value = Integer.toString(myCount);
                    myViewHolder.editNumber.setText(value);
                }
            }
        });

        myViewHolder.btnImagePluse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(TAG, "onClick: At position" + i);

                myViewHolder.btnImageMinus.setVisibility(View.VISIBLE);

                final String numberCount;
                numberCount = myViewHolder.editNumber.getText().toString();
                int myCount = Integer.parseInt(numberCount);

                ingredientName = myViewHolder.txtName.getText().toString().toLowerCase();

                myCount = myCount + 1;

                //ingredientCount = myCount;
                //updateQuantity();

                quantity1 [i] = myCount;

                String myCountString = String.valueOf(myCount);
                quantity3[i] = myCountString;
                StringBuilder stringBuilder = new StringBuilder();
                for (String s: quantity3 ){
                    stringBuilder.append(s);
                    stringBuilder.append(",");

                    Log.d(TAG, "onClick: StringBuilder: " + s);
                }

                SharedPreferences sharedPreferences1 = ctx.getSharedPreferences("Pref", 0);
                SharedPreferences.Editor editor = sharedPreferences1.edit();
                editor.putString("words", stringBuilder.toString());
                editor.apply();

                //Log.d(TAG, "onClick: SharedPreference: " + editor.toString());
                Log.d(TAG, "onClick: StringBuilder1: " + stringBuilder);

                Log.d(TAG, "onClick: StringBuilder2: " + stringBuilder.toString());
               // ingredientsQuantitiyHelper.setArrayString(stringBuilder.toString());

                switch (i)
                {
                    case 0:
                        CustomMessageEvent event = new CustomMessageEvent();
                        event.setCustomMessage(String.valueOf(4));
                        event.setCustomMessage2(String.valueOf(4));
                        EventBus.getDefault().post(event);

                         ingredientName = myViewHolder.txtName.getText().toString();
                        sqLiteHelper.addQuantity(ingredientName, myCount);
                        getInfo();
                        infoEvent = new InformationMessageEvent();
                        infoEvent.setCalories(String.valueOf(calories));
                        infoEvent.setCarbCalories(String.valueOf(carbCalorie));
                        infoEvent.setFatCalories(String.valueOf(fatCalorie));
                        infoEvent.setProteinCalories(String.valueOf(proteinCalorie));
                        infoEvent.setRda(String.valueOf(rda));
                        infoEvent.setPortionSize(String.valueOf(portionSize));

                        EventBus.getDefault().post(infoEvent);


                        break;
                    case 1:
                        event = new CustomMessageEvent();
                        event.setCustomMessage(String.valueOf(1));
                        event.setCustomMessage2(String.valueOf(1));
                        EventBus.getDefault().post(event);
                        ingredientName = myViewHolder.txtName.getText().toString();
                        sqLiteHelper.addQuantity(ingredientName, myCount);
                        getInfo();
                        infoEvent = new InformationMessageEvent();
                        infoEvent.setCalories(String.valueOf(calories));
                        infoEvent.setCarbCalories(String.valueOf(carbCalorie));
                        infoEvent.setFatCalories(String.valueOf(fatCalorie));
                        infoEvent.setProteinCalories(String.valueOf(proteinCalorie));
                        infoEvent.setRda(String.valueOf(rda));
                        infoEvent.setPortionSize(String.valueOf(portionSize));
                        EventBus.getDefault().post(infoEvent);

                        break;
                    case 2:
                        event = new CustomMessageEvent();
                        event.setCustomMessage(String.valueOf(4));
                        event.setCustomMessage2(String.valueOf(4));
                        EventBus.getDefault().post(event);
                        ingredientName = myViewHolder.txtName.getText().toString();
                        sqLiteHelper.addQuantity(ingredientName, myCount);
                        getInfo();
                        infoEvent = new InformationMessageEvent();
                        infoEvent.setCalories(String.valueOf(calories));
                        infoEvent.setCarbCalories(String.valueOf(carbCalorie));
                        infoEvent.setFatCalories(String.valueOf(fatCalorie));
                        infoEvent.setProteinCalories(String.valueOf(proteinCalorie));
                        infoEvent.setRda(String.valueOf(rda));
                        infoEvent.setPortionSize(String.valueOf(portionSize));
                        EventBus.getDefault().post(infoEvent);

                        break;
                    case 3:
                        event = new CustomMessageEvent();
                        event.setCustomMessage(String.valueOf(1));
                        event.setCustomMessage2(String.valueOf(1));
                        EventBus.getDefault().post(event);
                        ingredientName = myViewHolder.txtName.getText().toString();
                        sqLiteHelper.addQuantity(ingredientName, myCount);
                        getInfo();
                        infoEvent = new InformationMessageEvent();
                        infoEvent.setCalories(String.valueOf(calories));
                        infoEvent.setCarbCalories(String.valueOf(carbCalorie));
                        infoEvent.setFatCalories(String.valueOf(fatCalorie));
                        infoEvent.setProteinCalories(String.valueOf(proteinCalorie));
                        infoEvent.setRda(String.valueOf(rda));
                        infoEvent.setPortionSize(String.valueOf(portionSize));
                        EventBus.getDefault().post(infoEvent);

                        break;
                }

                if (defaultFlag) {
                    myViewHolder.btnImagePluse.setVisibility(View.GONE);
                    defaultFlag = false;
                    return;
                }

                if (myCount >= 10) {
                    myViewHolder.editNumber.setText("10");
                    myViewHolder.btnImagePluse.setVisibility(View.GONE);
                    myCount = 10;
                } else {
                    String value = Integer.toString(myCount);
                    myViewHolder.editNumber.setText(value);
                }
            }
        });

        myViewHolder.btnPluseShowDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Bbbbb");
                dialoguePosition = i;
                testDialogue();
            }
        });

        switch (i)
        {
            case 0:
                ingredientName = myViewHolder.txtName.getText().toString();
                getInfo();
                myViewHolder.editNumber.setText(quantity);
                break;
            case 1:
                ingredientName = myViewHolder.txtName.getText().toString();
                getInfo();
                myViewHolder.editNumber.setText(quantity);
                break;
            case 2:
                ingredientName = myViewHolder.txtName.getText().toString();
                getInfo();
                myViewHolder.editNumber.setText(quantity);
                break;
            case 3:
                ingredientName = myViewHolder.txtName.getText().toString();
                getInfo();
                myViewHolder.editNumber.setText(quantity);
                break;
        }

        int countX = Integer.parseInt(myViewHolder.editNumber.getText().toString());
        if ( countX ==0 )
        {
            myViewHolder.btnImageMinus.setVisibility(GONE);
        }
    }

    private void testDialogue() {
        final Dialog dialog = new Dialog(ctx);
        dialog.setContentView(R.layout.custom_dialogue_box);

        TextView tvHeading = dialog.findViewById(R.id.textViewHeading);
        TextView caloriesTV = dialog.findViewById(R.id.caloriesTextView_CustomDialogue);
        TextView carbCaloriesTV = dialog.findViewById(R.id.carbCaloriesTextView_CustomDialogue);
        TextView fatCaloriesTV = dialog.findViewById(R.id.fatCaloriesTextView_CustomDialogue);
        TextView proteinCaloriesTV = dialog.findViewById(R.id.proteinCaloriesTextView_CustomDialogue);
        TextView rdaTV = dialog.findViewById(R.id.rdaTextView_CustomDialogue);
        TextView portionSizeTV = dialog.findViewById(R.id.portionSizeTextView_CustomDialogue);

        switch (dialoguePosition)
        {
            case 0:
                tvHeading.setText("Oatmeal");
                caloriesTV.setText("133");
                carbCaloriesTV.setText("106");
                fatCaloriesTV.setText("9.3");
                proteinCaloriesTV.setText("18.1");
                rdaTV.setText("7");
                portionSizeTV.setText("4");
                break;
            case 1:

                tvHeading.setText("Wheat Germ");
                caloriesTV.setText("51");
                carbCaloriesTV.setText("21");
                fatCaloriesTV.setText("12");
                proteinCaloriesTV.setText("17.6");
                rdaTV.setText("3");
                portionSizeTV.setText("1");
                break;

            case 2:
                tvHeading.setText("Corn Flakes");
                caloriesTV.setText("101");
                carbCaloriesTV.setText("93");
                fatCaloriesTV.setText("0.3");
                proteinCaloriesTV.setText("7.5");
                rdaTV.setText("5");
                portionSizeTV.setText("4");
                break;
            case 3:

                tvHeading.setText("Rice Cereals");
                caloriesTV.setText("128");
                carbCaloriesTV.setText("116");
                fatCaloriesTV.setText("2.9");
                proteinCaloriesTV.setText("9");
                rdaTV.setText("6");
                portionSizeTV.setText("1");
                break;
        }

        ImageButton btnCross = dialog.findViewById(R.id.btn_iamge_cross_dialogue_custom_dialogue_box);
        btnCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    @Override
    public int getItemCount() {
        return mDataSet.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView txtName;
        private CircleImageView btnImagePluse;
        /*private ImageButton btnImageMinus;*/
        private CircleImageView btnImageMinus;
        private EditText editNumber;
        private ImageButton btnPluseShowDetail;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txt_show_detail_custom_row_vertical);  // change id of text for just click
            // expandableTextView = itemView.findViewById(R.id.expand_text_view);
            btnImagePluse = itemView.findViewById(R.id.btn_image_pluse_custom_row_vertical);
            btnImageMinus = itemView.findViewById(R.id.btn_image_minus_custom_row_vertical);
            editNumber = itemView.findViewById(R.id.edit_number_custom_row_vertical);
            btnPluseShowDetail = itemView.findViewById(R.id.btnImage_pluse_show_detail_custom_row_vertical);
        }
    }

    void shwoCustomDialogue() {
        final AlertDialog.Builder builder = new AlertDialog.Builder((ctx));
        View view = LayoutInflater.from(ctx).inflate(R.layout.custom_dialogue_box, null);

        builder.setView(view);
        builder.show();
    }

    public void showWarningDialogue() {
        final Dialog dialog = new Dialog(ctx);
        dialog.setContentView(R.layout.custom_warning_dialogue_box);
        Button btnDefault = dialog.findViewById(R.id.btn_default_custom_warning_dialogue_box);
        btnDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Default Button Clicked");
                defaultFlag = true;
                dialog.dismiss();
            }
        });

        Button btnIgnore = dialog.findViewById(R.id.btn_ignore_custom_warning_dialogue_box);
        btnIgnore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    public void getInfo (){
        Cursor res = sqLiteHelper.infoData(ingredientName);
        if (res.getCount() == 0)
        {

        }
        else {
            while (res.moveToNext()) {
                bitternessFlag = res.getString(1);
                calories = res.getString(2);
                carbCalorie = res.getString(3);
                category = res.getString(4);
                fatCalorie = res.getString(5);
                name = res.getString(6);
                portionFlag = res.getString(7);
                portionSize = res.getString(8);
                proteinCalorie = res.getString(9);
                quantity = res.getString(10);
                rda = res.getString(11);
                servingSize = res.getString(12);
                unhealthyFlag = res.getString(13);

                calories1 = Double.parseDouble(calories);
                carbCalorie1 = Double.parseDouble(carbCalorie);
                fatCalorie1 = Double.parseDouble(fatCalorie);
                proteinCalorie1 = Double.parseDouble(proteinCalorie);
                rda1 = Double.parseDouble(rda);
                portionSize1 = Double.parseDouble(portionSize);
            }
        }
        }

}