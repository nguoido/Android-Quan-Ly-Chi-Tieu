//package com.vivhp.qlct.broadcast;
//
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.support.v7.app.NotificationCompat;
//import android.widget.Toast;
//
//import com.vivhp.qlct.MainActivity;
//import com.vivhp.qlct.R;
//
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//
///**
// * Created by vivhp on 11/14/2016.
// */
//
//public class broadcastAlarmKeyNote extends BroadcastReceiver {
//
//
//    @Override
//    public void onReceive(Context context, Intent intent) {
//        Calendar calendar = Calendar.getInstance();
//        DateFormat dateFormat = SimpleDateFormat.getTimeInstance();
//        Toast.makeText(context, dateFormat.format(calendar.getTime()), Toast.LENGTH_SHORT).show();
//        Notification(context);
//    }
//
//    public void Notification(Context context){
//        // Set Notification Title
//        String arltitle = context.getString(R.string.alarm_titl);
//        String arlmess = context.getString(R.string.alarm_mes);
//        // Open NotificationView Class on Notification Click
//        Intent intent = new Intent(context, MainActivity.class);
//        // Send data to NotificationView Class
//        intent.putExtra("title", arltitle);
//        intent.putExtra("text", arlmess);
//        // Open NotificationView.java Activity
//        PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent,
//                PendingIntent.FLAG_UPDATE_CURRENT);
//
//        // Create Notification using NotificationCompat.Builder
//        NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat.Builder(
//                context)
//                .setAutoCancel(true)
//                // Set Icon
//                .setSmallIcon(R.drawable.ic_stat_name)
//                // Set Ticker Message
//                .setTicker(arlmess)
//                // Set Title
//                .setContentTitle(context.getString(R.string.alarm_titl))
//                // Set Text
//                .setContentText(arlmess)
//                // Add an Action Button below Notification
////                .addAction(R.drawable.ic_add_circle_24dp, "Thêm giao dịch", pIntent)
//                // Set PendingIntent into Notification
//                .setContentIntent(pIntent)
//                // Dismiss Notification
//                .setAutoCancel(true);
//
//        // Create Notification Manager
//        NotificationManager notificationmanager = (NotificationManager) context
//                .getSystemService(Context.NOTIFICATION_SERVICE);
//        // Build Notification with Notification Manager
//        notificationmanager.notify(1, builder.build());
//    }
//
//    private int getNotificationIcon() {
//        boolean useWhiteIcon = (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP);
//        return useWhiteIcon ? R.mipmap.ic_logo_icon : R.mipmap.ic_logo_icon;
//    }
//}
