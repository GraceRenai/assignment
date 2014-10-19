package assignment.easyaccount.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import assignment.easyaccount.R;
import assignment.easyaccount.calendar.CalendarView;
import assignment.easyaccount.dialog.CanlendarDialog;
import assignment.easyaccount.entity.AccountEnum;
import assignment.easyaccount.sql.DBHelper;

public class WriteAccount extends Activity 
{
	private static final int REQUEST_CALENDAR = 1;
	
	private CalendarView calendarView;
	private Spinner spinner;
	private EditText txtMoney;
	private ImageButton btnAddAccount;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.write_account_activity);
		// Add new item type
		ArrayAdapter<AccountEnum> adapter = new ArrayAdapter<AccountEnum>(this, android.R.layout.simple_spinner_item);
		adapter.add(AccountEnum.Daily);
		adapter.add(AccountEnum.Eatery);
		adapter.add(AccountEnum.Shirt);
		adapter.add(AccountEnum.Traffic);
		adapter.add(AccountEnum.Electricity);
		adapter.add(AccountEnum.Amusement);
		adapter.add(AccountEnum.Sport);
		adapter.add(AccountEnum.Company);
		adapter.add(AccountEnum.Other);


		//set item profile
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		//sublist
		spinner = (Spinner) findViewById(R.id.spinType);
		spinner.setAdapter(adapter);
		
		spinner.setPrompt("Cost Type");
		
		calendarView = (CalendarView)findViewById(R.id.calendar_view);
		calendarView.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(WriteAccount.this,CanlendarDialog.class);
				startActivityForResult(intent, REQUEST_CALENDAR);
			}
		});
		
		txtMoney = (EditText)findViewById(R.id.txtMoney);

		btnAddAccount = (ImageButton)findViewById(R.id.btnAddAccount);
		btnAddAccount.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v)
			{
				AlertDialog.Builder dialog = new AlertDialog.Builder(WriteAccount.this);
				dialog.setTitle("Add Bill");
				final AccountEnum accountEnum = (AccountEnum)spinner.getSelectedItem();
				Spanned spannable = Html.fromHtml("<a>"+calendarView.getDateFormatString()+"</a><br><a>"+accountEnum+"\tSum:"+txtMoney.getText()+"</a><br><a>Dear, sure to add?</a>");
				dialog.setMessage(spannable);
				if(txtMoney.getText().length() > 0)
				{
					dialog.setPositiveButton("Sure!", new DialogInterface.OnClickListener(){
	
						@Override
						public void onClick(DialogInterface dialog, int which)
						{
							DBHelper dbHelper = new DBHelper(WriteAccount.this);
							dbHelper.open();
							ContentValues insertItem = new ContentValues();
							insertItem.put(DBHelper.COLUMN_TYPE, accountEnum.getValue());
							insertItem.put(DBHelper.COLUMN_MONEY, Float.parseFloat(txtMoney.getText().toString()));
							insertItem.put(DBHelper.COLUMN_DATE, calendarView.getDate());
							dbHelper.insert(insertItem);
							dbHelper.close();
							clearState();
						}
					});
				}
				dialog.setNegativeButton("CancelTT", new DialogInterface.OnClickListener(){

					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						clearState();
					}
				});
				
				dialog.create().show();
			}
			
		});
	}
	private void clearState()
	{
		spinner.setSelection(0);
		txtMoney.setText("");
	}
	//Deal with startActivityForResult
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if(requestCode == REQUEST_CALENDAR)
		{
			
			if(resultCode == RESULT_OK)
			{
				calendarView.setCalendar(data.getIntExtra("year", 1900), data.getIntExtra("month", 0), data.getIntExtra("day", 1));
			}
		}
	}
}
