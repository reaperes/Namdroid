package org.nhnnext.nhk.namdroid.widget;

public class WidgetAndService {
}

/*

widget info xml

<appwidget-provider xmlns:android="http://schemas.android.com/apk/res/android"
        android:minWidth="220dp"
        android:minHeight="72dp"
        android:updatePeriodMillis="86400000"
        android:initialLayout="@layout/appwidget" />

Widget layout xml

<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
              android:layout_width="match_parent"
              android:layout_height="match_parent"
              android:background="#dd55ff"
              android:padding="0.1dp">

  <TextView
      android:layout_width="0dp"
      android:layout_height="wrap_content"
      android:layout_gravity="center_vertical"
      android:layout_weight="0.8"
      android:text="@string/widgettext"
      android:textColor="#000000"/>

  <TextView
      android:id="@+id/widgetMood"
      android:layout_width="0dp"
      android:layout_height="wrap_content"
      android:layout_gravity="center_vertical"
      android:layout_weight="0.3"
      android:text="@string/widgetmoodtext"
      android:textColor="#000000" />

  <ImageButton
      android:id="@+id/widgetBtn"
      android:layout_width="0dp"
      android:layout_height="wrap_content"
      android:layout_gravity="center_vertical"
      android:layout_weight="0.5"
      android:src="@drawable/smile_icon"/>

</LinearLayout>

Manifests file

    <service android:name=".widget.CurrentMoodService" />

    <receiver android:name=".widget.WidgetProvider"
        android:label="example label">
      <intent-filter>
        <action android:name="android.appwidget.action.APPWIDGET_UPDATE" />
      </intent-filter>
      <meta-data android:name="android.appwidget.provider"
        android:resource="@xml/widgetinfo" />
    </receiver>
  </application>


Widget provider class

public class WidgetProvider extends AppWidgetProvider {
    public static final String TAG = "WidgetProvider";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Log.i(TAG, "onUpdate()");

        for (int appWidgetId : appWidgetIds) {
            Log.i(TAG, "updating widget[id] " + appWidgetId);

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.appwidget);

            Intent intent = new Intent(context, CurrentMoodService.class);
            intent.setAction(CurrentMoodService.UPDATEMOOD);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, 0);

            views.setOnClickPendingIntent(R.id.widgetBtn, pendingIntent);
            Log.i(TAG, "pending intent set");

            // Tell the AppWidgetManager to perform an update on the current App Widget
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }
}


Service class

public class CurrentMoodService extends Service {
    public static final String UPDATEMOOD = "UpdateMood";
    public static final String CURRENTMOOD = "CurrentMood";

    private String currentMood;
    private LinkedList<String> moods;

    public CurrentMoodService(){
        this.moods = new LinkedList<String>();
        fillMoodsList();
    }

    private void fillMoodsList() {
        this.moods.add(":)");
        this.moods.add(":(");
        this.moods.add(":D");
        this.moods.add(":X");
        this.moods.add(":S");
        this.moods.add(";)");

        this.currentMood = ";)";
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, startId, startId);

        Log.i(WidgetProvider.TAG, "onStartCommand()");

        updateMood(intent);

        stopSelf(startId);

        return START_STICKY;
    }

    private String getRandomMood() {
        Random r = new Random(Calendar.getInstance().getTimeInMillis());
        int pos = r.nextInt(moods.size());
        return moods.get(pos);
    }

    private void updateMood(Intent intent) {
        Log.i(WidgetProvider.TAG, "This is the intent " + intent);
        if (intent != null){
            String requestedAction = intent.getAction();
            Log.i(WidgetProvider.TAG, "This is the action " + requestedAction);
            if (requestedAction != null && requestedAction.equals(UPDATEMOOD)){
                this.currentMood = getRandomMood();

                int widgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, 0);

                Log.i(WidgetProvider.TAG, "This is the currentMood " + currentMood + " to widget " + widgetId);

                AppWidgetManager appWidgetMan = AppWidgetManager.getInstance(this);
                RemoteViews views = new RemoteViews(this.getPackageName(),R.layout.appwidget);
                views.setTextViewText(R.id.widgetMood, currentMood);
                appWidgetMan.updateAppWidget(widgetId, views);

                Log.i(WidgetProvider.TAG, "CurrentMood updated!");
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}


*/