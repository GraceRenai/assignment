package assignment.easyaccount;


import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import assignment.easyaccount.activity.QueryAccount;
import assignment.easyaccount.activity.WriteAccount;
import assignment.easyaccount.tabbar.TabBarActivity;

/**
 * @author Grace	
 * @data   2014-Oct-19
 */
public class Main extends TabBarActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		
		addTabButton("Record", R.drawable.tabbar_pen, new Intent(this,WriteAccount.class));	
		addTabButton("History", R.drawable.tabbar_query_account, new Intent(this,QueryAccount.class));

		commit();
	
	}
}
