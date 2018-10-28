package com.smartgenlabs.princequest;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;

import javax.annotation.Nullable;

/**
 * Created by Pankaj Vaghela on 18-06-2018.
 * Reused on 30-09-2018 for XFW
 */
public class M {


    public static String toFirstUpperCase(String str){
        return str.substring(0,1).toUpperCase()+str.substring(1);
    }

    public static void showNoticeAlert(Context context, String title, @Nullable String msg){
       showNoticeAlert(context,title,msg,false);
    }

    public static void showNoticeAlert(final Context context, String title, @Nullable String msg, final boolean exit){
        AlertDialog.Builder builder = new AlertDialog.Builder(context).setTitle(title);
        if(msg!=null)builder.setMessage(msg);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                arg0.dismiss();
                if(exit){
                    if(context instanceof  Activity){
                        ((Activity)context).finish();
                    }
                }
            }
        }).create().show();
    }

    public static void showNoticeAlert(final Context context, String title, String msg, boolean isReportable, final String problem){
        new AlertDialog.Builder(context).setTitle(title).setMessage(msg)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        arg0.dismiss();
                    }
                })
                .setNeutralButton("Report", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_SENDTO);
                        intent.setType("text/plain");

                        intent.putExtra(Intent.EXTRA_EMAIL, "smartgenlabs@gmail.com");
                        intent.putExtra(Intent.EXTRA_SUBJECT, "XFW - Error Report");
                        intent.putExtra(Intent.EXTRA_TEXT, "Error occurred during using app. Details are as follows : "+problem);

                        context.startActivity(Intent.createChooser(intent, "Report Error"));
                    }
                })
                .create().show();

    }

    static AlertDialog dialogold;
    static AlertDialog dialogNew;
    public static void showNoNetworkAlert(final Context context, boolean visible){

        dialogNew = new AlertDialog.Builder(context).setTitle("No Network!").setMessage("No Network available, please turn on your data connection and retry.")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ((Activity)context).finish();
                    }
                })
                .setCancelable(false)
                .create();

        if(visible){
            dialogNew.show();
            dialogold = dialogNew;
        }
        else{
          if(dialogold!=null)dialogold.dismiss();
        }

    }

    public static void storeSearchTermsFirebasePersistant(){
        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);
       // DatabaseReference searchTerms = FirebaseDatabase.getInstance().getReference("searchterms");
        //searchTerms.keepSynced(true);
    }

}

