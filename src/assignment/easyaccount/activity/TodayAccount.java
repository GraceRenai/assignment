package assignment.easyaccount.activity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CompoundButton.OnCheckedChangeListener;
import assignment.easyaccount.R;
import assignment.easyaccount.entity.Account;
import assignment.easyaccount.entity.AccountEnum;
import assignment.easyaccount.sql.DBHelper;

public class TodayAccount extends Activity implements OnItemClickListener
{
	private DBHelper dbHelper;
	private ArrayList<Account> todayAccounts;
	private float sumMoney = 0.0f;
	private ListView list;
	private TextView viewSumMoney;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.today_account_activity);

		list = (ListView) findViewById(R.id.lstTodayAccount);
		list.setOnItemClickListener(this);
		viewSumMoney = (TextView) findViewById(R.id.viewSumMoney);
		dbHelper = new DBHelper(this);
	}
	@Override
	protected void onResume() {
		getTodayAccount();
		super.onResume();
	}
	@Override
	public void onPause()
    {
//    	unregisterReceiver(receiver);
    	super.onPause();
    }
	
	@SuppressLint("SimpleDateFormat")
	private void getTodayAccount()
	{
		sumMoney = 0.0f;
		Calendar calendar = Calendar.getInstance();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String today = format.format(calendar.getTime());
		
		StringBuilder selectBuilder = new StringBuilder();
		selectBuilder.append("select * from ").append(DBHelper.TABLE_NAME).append(" where ")
		.append(DBHelper.COLUMN_DATE).append(" =?").append(" order by ").append(DBHelper.COLUMN_TYPE);
		
		todayAccounts =  dbHelper.getQueryAccountList(selectBuilder.toString(), new String[]{today});
		for (Account account:todayAccounts) {
			sumMoney+=account.getMoney();
		}
		list.setAdapter(new ListItemAdapter(this));
		viewSumMoney.setText("TotalSum: "+sumMoney);
	}
	
	
	
	class ListItemAdapter extends BaseAdapter 
	{
		private LayoutInflater  layoutInflater;

		ListItemAdapter(Context context)
		{
			this.layoutInflater = LayoutInflater.from(context);
		}
		@Override
		public int getCount() {
			return todayAccounts.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			ListItemView itemView = null;
			if(convertView == null)
			{
				itemView = new ListItemView();
				convertView = layoutInflater.inflate(R.layout.today_account_listview_item, null);
				itemView.image = (ImageView) convertView.findViewById(R.id.imgItemType);
				itemView.view = (TextView) convertView.findViewById(R.id.viewItemMoney);
				itemView.typeNameView = (TextView)convertView.findViewById(R.id.viewItemTypeName);
				itemView.checkBox = (CheckBox) convertView.findViewById(R.id.chkTodayAccountItem);
				
				itemView.image.setImageResource(AccountEnum.getAccountEnumImage(todayAccounts.get(position).getType()));
				itemView.view.setText(String.valueOf(todayAccounts.get(position).getMoney()));
				itemView.typeNameView.setText(todayAccounts.get(position).getType().toString());
				itemView.checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener(){

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						todayAccounts.get(position).setSelected(isChecked);
					}});
				convertView.setTag(itemView);
			}
			else
			{
				itemView = (ListItemView) convertView.getTag();
			}
			return convertView;
		}
		class ListItemView 
		{
			public ImageView image;
			public TextView view;
			public TextView typeNameView;
			public CheckBox checkBox;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
	}
	
}

