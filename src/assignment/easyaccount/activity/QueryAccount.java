package assignment.easyaccount.activity;

import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import assignment.easyaccount.R;
import assignment.easyaccount.dialog.DateSelectorDialog;

public class QueryAccount extends TabActivity implements OnTabChangeListener
{
	private TabHost tabHost;
	private static final int MENU_DELETE = 1001;
	private static final int REQUEST_QUERYDATE = 2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		tabHost = getTabHost();
		tabHost.addTab(tabHost.newTabSpec("today").setIndicator("Bill Today",getResources().getDrawable(R.drawable.tab_today))
				.setContent(new Intent(this,TodayAccount.class)
				.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)));
		tabHost.addTab(tabHost.newTabSpec("history").setIndicator("Bill History",getResources().getDrawable(R.drawable.tab_history))
				.setContent(new Intent(this,HistoryAccount.class)));
		tabHost.setOnTabChangedListener(this);
				
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(Menu.NONE,MENU_DELETE,0,"Delete selected item").setIcon(android.R.drawable.ic_menu_delete);  
		return super.onCreateOptionsMenu(menu);
	}
	@Override
	public void onTabChanged(String tabId) {
		if(tabId.equals("history"))
		{
			Intent intent = new Intent(this,DateSelectorDialog.class);
			startActivityForResult(intent, REQUEST_QUERYDATE);
		}
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == REQUEST_QUERYDATE)
		{
			if(resultCode == RESULT_OK)
			{
				String startDate = data.getCharSequenceExtra("startDate").toString();
				String endDate = data.getCharSequenceExtra("endDate").toString();
				
				Intent queryIntent = new Intent();
				queryIntent.setAction(HistoryAccount.QUERY_HISTORY_ACCOUNT);
				queryIntent.putExtra("startDate", startDate);
				queryIntent.putExtra("endDate", endDate);
				sendBroadcast(queryIntent);
			}
		}
	}
}
