package assignment.easyaccount.tabbar;


import java.util.ArrayList;
import java.util.List;

import android.app.ActivityGroup;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import assignment.easyaccount.R;
public class TabBarActivity extends ActivityGroup implements
		OnCheckedChangeListener
{
	private int btnWidth = 64;
	private LinearLayout contentViewLayout;
	private RadioGroup tabBar;
	private List<TabBarButton> buttonList;
	private RadioGroup.LayoutParams buttonLayoutParams;
	
	public static final String ACTION_CHANGE_TAB = "grace.android.view.tabbar.changeTab";
	private ChangeTabBroadcastReceiver receiver;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabbar);
		contentViewLayout = (LinearLayout) findViewById(R.id.contentViewLayout);
		tabBar = (RadioGroup) findViewById(R.id.tabBar);
		tabBar.setOnCheckedChangeListener(this);
		buttonList = new ArrayList<TabBarButton>();
	}
	
	@Override
	protected void onResume()
	{
		IntentFilter filter = new IntentFilter(ACTION_CHANGE_TAB);
		receiver = new ChangeTabBroadcastReceiver();
		registerReceiver(receiver, filter);
		
		super.onResume();
	}
	@Override
	public void onPause()
    {
    	unregisterReceiver(receiver);
    	super.onPause();
    }
	public void addTabButton(String label, int imageId, Intent intent)
	{
		TabBarButton btn = new TabBarButton(this);
		btn.setState(imageId, label, intent);
		buttonList.add(btn);
	}
	public void commit()
	{
		tabBar.removeAllViews();
		WindowManager windowManager = getWindowManager();
		int windowWidth = windowManager.getDefaultDisplay().getWidth();
		int btnNum = windowWidth / 64;
		if (buttonList.size() < btnNum)
		{
			btnWidth = windowWidth / buttonList.size();
		}
		ButtonStateDrawable.WIDTH = btnWidth;
		buttonLayoutParams = new RadioGroup.LayoutParams(btnWidth,
				LayoutParams.WRAP_CONTENT);
		for (int i = 0; i < buttonList.size(); i++)
		{
			TabBarButton btn = buttonList.get(i);
			btn.setId(i);
			tabBar.addView(btn, i, buttonLayoutParams);
		}
		setCurrentTab(0);
	}
	public void setCurrentTab(int index)
	{
		tabBar.check(index);
		contentViewLayout.removeAllViews();
		TabBarButton btn = (TabBarButton) tabBar.getChildAt(index);
		View tabView = getLocalActivityManager().startActivity(btn.getLabel(),
				btn.getIntent()).getDecorView();
		
		contentViewLayout.addView(tabView, new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
	}
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId)
	{
		setCurrentTab(checkedId);
	}
	class ChangeTabBroadcastReceiver extends BroadcastReceiver
	{
		@Override
		public void onReceive(Context context, Intent intent)
		{
			int curIndex = intent.getExtras().getInt("CurIndex");
			setCurrentTab(curIndex);
		}
		
	}
}
