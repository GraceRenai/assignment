
package assignment.easyaccount.entity;

import assignment.easyaccount.R;


public enum AccountEnum
{

	Daily(0),
	Eatery(1),
	Shirt(2),
	Traffic(3),
	Electricity(4),
	Amusement(5),
	Sport(6),
	Company(7),
	Other(8);
	private int typeId;
	private AccountEnum(int typeId)
	{
		this.typeId = typeId;
	}
	public int getValue()
	{
		return this.typeId;
	}
	public static AccountEnum getAccountEnum(int typeId)
	{
		switch (typeId) {
		case 0:
			return AccountEnum.Daily;
		case 1:
			return AccountEnum.Eatery;
		case 2:
			return AccountEnum.Shirt;
		case 3:
			return AccountEnum.Traffic;
		case 4:
			return AccountEnum.Electricity;
		case 5:
			return AccountEnum.Amusement;
		case 6:
			return AccountEnum.Sport;
		case 7:
			return AccountEnum.Company;
		case 8:
			return AccountEnum.Other;
		default:
			return null;
		}
	}
	public static int getAccountEnumImage(AccountEnum accountEnum)
	{
		switch (accountEnum) {
		case Daily:
			return R.drawable.tabbar_food;
		case Eatery:
			return R.drawable.tabbar_eatery;
		case Shirt:
			return R.drawable.tabbar_shirt;
		case Traffic:
			return R.drawable.tabbar_wireless;
		case Electricity:
			return R.drawable.tabbar_lamp;
		case Amusement:
			return R.drawable.tabbar_music;
		case Sport:
			return R.drawable.tabbar_sport;
		case Company:
			return R.drawable.tabbar_friend;
		case Other:
			return R.drawable.tabbar_bell;
		default:
			return R.drawable.tabbar_bell;
		}
	}
	@Override
	public String toString()
	{
		
		switch (typeId)
		{
			case 0:
				return "Daily";
			case 1:
				return "Eatery";
			case 2:
				return "Shirt";
			case 3:
				return "Traffic";
			case 4:
				return "Electricity";
			case 5:
				return "Amusement";
			case 6:
				return "Sport";
			case 7:
				return "Company";
			case 8:
				return "Others";
			default:
				return "";
		}
	}
}
