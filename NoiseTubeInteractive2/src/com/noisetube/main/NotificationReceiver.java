package com.noisetube.main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class NotificationReceiver extends BroadcastReceiver
{
      
    @Override
    public void onReceive(Context context, Intent intent)
    {
    	Log.d("NotifictionReceiver", "Receiver onReceive");
       Intent service = new Intent(context, MyAlarmService.class);
       context.startService(service);
  
    }   
}
