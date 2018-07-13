package org.androidtown.anywhere.any_05_myinfo;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import org.androidtown.anywhere.R;
import org.androidtown.anywhere.any_06_myinfo_modify.InfomationCheckDialog;

/**
 * Created by user on 2017-08-16.
 */

public class InfomationDialog {
    private Context context;

    private String user_email;
    private String user_nick;
    private String user_division;

    public InfomationDialog(Context context){
        this.context = context;

        SharedPreferences pref = context.getSharedPreferences("anywhere", Context.MODE_PRIVATE);
        user_email = pref.getString("user_email", "");
        user_nick = pref.getString("user_nick", "");
        user_division = pref.getString("user_division", "");


    }


    public void setInfoDialog(){

        LayoutInflater inflater = LayoutInflater.from(context);
        final View dialogView = inflater.inflate(R.layout.anywhere05_myinfo_dialog,null);

        TextView email = (TextView) dialogView.findViewById(R.id.email);
        TextView nick = (TextView) dialogView.findViewById(R.id.nick);
        TextView infoModify = (TextView) dialogView.findViewById(R.id.infoModify);
        TextView infoOut = (TextView) dialogView.findViewById(R.id.infoOut);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("내 정보 보기");
        builder.setIcon(R.drawable.logo_blue);
        builder.setView(dialogView);

        email.setText(user_email);
        nick.setText(user_nick);

        builder.setPositiveButton("닫기", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        final Dialog dialog = builder.create();

        dialog.show();


        if(user_division.equals("0")){

            infoModify.setVisibility(View.VISIBLE);
            infoOut.setVisibility(View.VISIBLE);

        }else if(user_division.equals("1")){

            infoModify.setVisibility(View.VISIBLE);
            infoOut.setVisibility(View.GONE);

        }



        infoModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                InfomationCheckDialog infoCheckDialog = new InfomationCheckDialog(context);
                infoCheckDialog.setInfoCheckSelect(1);
                infoCheckDialog.setinfoCheckDialog();
            }
        });

        infoOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                InfomationCheckDialog infoCheckDialog = new InfomationCheckDialog(context);
                infoCheckDialog.setInfoCheckSelect(2);
                infoCheckDialog.setinfoCheckDialog();
            }
        });


    }


}
