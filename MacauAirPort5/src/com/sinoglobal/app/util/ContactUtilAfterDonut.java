package com.sinoglobal.app.util;

import java.util.ArrayList;

import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.RawContacts;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.CommonDataKinds.Note;
import android.provider.ContactsContract.CommonDataKinds.Organization;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.CommonDataKinds.Website;

import com.sinoglobal.app.beans.ContactVo;

/**
 * 用于android2.0及以后的通讯录访问
 * @author Administrator
 *·
 */
public class ContactUtilAfterDonut {
	public static ArrayList<ContactVo> getContactList(Context context) {
		ArrayList<ContactVo> list = new ArrayList<ContactVo>();
		ContentResolver cr = context.getContentResolver();
		Cursor cursor = null;
		try {
			cursor = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
					new String[] {Phone._ID, Phone.DISPLAY_NAME, Phone.NUMBER}, null, null, null);
			
			while(cursor.moveToNext()) {
				String name = cursor.getString(1);
				String phoneNumber = cursor.getString(2);
				list.add(new ContactVo(name, phoneNumber));
			}
		} finally {
			if(cursor != null) {
				cursor.close();
			}
		}
		return list;
	}
	
	public static String queryNameByNumber(Context context, String number) {
		ContentResolver cr = context.getContentResolver();
		Cursor cursor = null;
		String name = "";
		try {
			cursor = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, 
					new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME},
					ContactsContract.CommonDataKinds.Phone.NUMBER + "=?", new String[]{number}, null);
			if(cursor.getCount()>0 && cursor.moveToFirst()) {
				name = cursor.getString(0);
			}
		} finally {
			if(cursor != null) {
				cursor.close();
			}
		}

		return name;
	}
	
	public static void delContact(Context context, String name) {
		Cursor cursor = context.getContentResolver().query(Data.CONTENT_URI,new String[] { Data.RAW_CONTACT_ID },
				ContactsContract.Contacts.DISPLAY_NAME + "=?",new String[] { name }, null);
		ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
		if (cursor.moveToFirst()) {
			do {
				long Id = cursor.getLong(cursor.getColumnIndex(Data.RAW_CONTACT_ID));
//				ops.add(ContentProviderOperation.newDelete(
//						ContentUris.withAppendedId(RawContacts.CONTENT_URI,Id)).build());
//				删除联系人的方法不对，应该改成如下路径才可以：
				ops.add(ContentProviderOperation.newDelete(RawContacts.CONTENT_URI)
				                                  .withSelection(RawContacts._ID + "=?", new String[]{String.valueOf(Id)})
				                                  .build()); 
				try {
					context.getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
				} 
				catch (Exception e){}
			} while (cursor.moveToNext());
			cursor.close();
		}
	}

	public static void addContact(Context context, String name,
			String organisation,String phone, String fax, String email, String address,String website){
		ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
		//在名片表插入一个新名片
		ops.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
				.withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
				.withValue(ContactsContract.RawContacts._ID, 0)//大神啊，联系人只能添加一次，怎么解决啊 去掉改行代码即可多次添加
				.withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
				.withValue(
						ContactsContract.RawContacts.AGGREGATION_MODE,ContactsContract.RawContacts.AGGREGATION_MODE_DISABLED).build());
		// add name
		//添加一条新名字记录；对应RAW_CONTACT_ID为0的名片
		if (!name.equals("")) {
			ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
					.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0).withValue(
							ContactsContract.Data.MIMETYPE,ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE).withValue(
									ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,name).build());
		}
		//添加昵称
		ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
				.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0).withValue(
						ContactsContract.Data.MIMETYPE,ContactsContract.CommonDataKinds.Nickname.CONTENT_ITEM_TYPE).withValue(ContactsContract.CommonDataKinds.Nickname.NAME,"Anson昵称").build());
		// add company
		if (!organisation.equals("")) {
			ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI).withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0).withValue(ContactsContract.Data.MIMETYPE,
					ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE).withValue(
							ContactsContract.CommonDataKinds.Organization.COMPANY,organisation).withValue(
									ContactsContract.CommonDataKinds.Organization.TYPE,ContactsContract.CommonDataKinds.Organization.TYPE_WORK).build());
		}
		// add phone
		if (!phone.equals("")) {
			ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
					.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
					.withValue(ContactsContract.Data.MIMETYPE,
							ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE).withValue(ContactsContract.CommonDataKinds.Phone.NUMBER,phone).withValue(ContactsContract.CommonDataKinds.Phone.TYPE,1).build());
		}
		// add Fax
		if (!fax.equals("")) {
			ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI).withValueBackReference(
					ContactsContract.Data.RAW_CONTACT_ID, 0).withValue(
							ContactsContract.Data.MIMETYPE,
							ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE).withValue(
									ContactsContract.CommonDataKinds.Phone.NUMBER,fax)
									.withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
											ContactsContract.CommonDataKinds.Phone.TYPE_FAX_WORK).build());
		}
		// add email
		if (!email.equals("")) {
			ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
					.withValueBackReference(
							ContactsContract.Data.RAW_CONTACT_ID, 0).withValue(
									ContactsContract.Data.MIMETYPE,ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE).withValue(ContactsContract.CommonDataKinds.Email.DATA,email).withValue(ContactsContract.CommonDataKinds.Email.TYPE,1).build());
		}
		// add address
		if (!address.equals("")) {
			ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI).withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0).withValue(
					ContactsContract.Data.MIMETYPE,ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE).withValue(
							ContactsContract.CommonDataKinds.StructuredPostal.STREET,address)
							.withValue(ContactsContract.CommonDataKinds.StructuredPostal.TYPE,
									ContactsContract.CommonDataKinds.StructuredPostal.TYPE_WORK).build());
		}
		// add website
		if (!website.equals("")) {
			ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
					.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
					.withValue(ContactsContract.Data.MIMETYPE,
							ContactsContract.CommonDataKinds.Website.CONTENT_ITEM_TYPE).withValue(
									ContactsContract.CommonDataKinds.Website.URL,website)
									.withValue(
											ContactsContract.CommonDataKinds.Website.TYPE,
											ContactsContract.CommonDataKinds.Website.TYPE_WORK).build());
		}
		// add IM InstantMessaging（即时通讯、实时传讯）的缩写是IM
		String qq1="452824089";
		ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI).withValueBackReference(
				ContactsContract.Data.RAW_CONTACT_ID, 0).withValue(
						ContactsContract.Data.MIMETYPE,
						ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE).withValue(
								ContactsContract.CommonDataKinds.Im.DATA1,qq1)
								.withValue(
										ContactsContract.CommonDataKinds.Im.PROTOCOL,
										ContactsContract.CommonDataKinds.Im.PROTOCOL_QQ).build());
		// add logo image
		// Bitmap bm = logo;
		// if (bm != null) {
		// ByteArrayOutputStream baos = new ByteArrayOutputStream();
		// bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
		// byte[] photo = baos.toByteArray();
		// if (photo != null) {
		//
		// ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
		// .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
		// .withValue(ContactsContract.Data.MIMETYPE,
		// ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE)
		// .withValue(ContactsContract.CommonDataKinds.Photo.PHOTO, photo)
		// .build());
		// }
		// }
		try {
			context.getContentResolver().applyBatch(
					ContactsContract.AUTHORITY, ops);
		} catch (Exception e){
		}
	}


	public static void updateContact(Context context,String oldname, String name, String phone, String email,String website, String organization, String note) {
		Cursor cursor = context.getContentResolver().query(Data.CONTENT_URI,new String[] { Data.RAW_CONTACT_ID },
				ContactsContract.Contacts.DISPLAY_NAME + "=?",new String[] { oldname }, null);
		cursor.moveToFirst();
		String id = cursor.getString(cursor.getColumnIndex(Data.RAW_CONTACT_ID));
		cursor.close();
		ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();

		ops.add(ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
				.withSelection(
						Data.RAW_CONTACT_ID + "=?" + " AND "+ ContactsContract.Data.MIMETYPE + " = ?" +
								" AND " + Phone.TYPE + "=?",new String[] { String.valueOf(id),Phone.CONTENT_ITEM_TYPE,
								String.valueOf(Phone.TYPE_HOME) }).withValue(Phone.NUMBER, phone).build());
		// 更新email
		ops.add(ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
				.withSelection(Data.RAW_CONTACT_ID + "=?" + " AND "+ ContactsContract.Data.MIMETYPE + " = ?" +" AND " + Email.TYPE + "=?",new String[] { String.valueOf(id),Email.CONTENT_ITEM_TYPE,
						String.valueOf(Email.TYPE_HOME) }).withValue(Email.DATA, email).build());
		// 更新姓名
		ops.add(ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
				.withSelection(Data.RAW_CONTACT_ID + "=?" + " AND "+ ContactsContract.Data.MIMETYPE + " = ?",new String[] { String.valueOf(id),StructuredName.CONTENT_ITEM_TYPE }).withValue(StructuredName.DISPLAY_NAME, name).build());
		// 更新网站
		ops.add(ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
				.withSelection(Data.RAW_CONTACT_ID + "=?" + " AND "+ ContactsContract.Data.MIMETYPE + " = ?",new String[] { String.valueOf(id),Website.CONTENT_ITEM_TYPE }).withValue(Website.URL, website).build());
		// 更新公司
		ops.add(ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
				.withSelection(Data.RAW_CONTACT_ID + "=?" + " AND "+ ContactsContract.Data.MIMETYPE + " = ?",new String[] { String.valueOf(id),Organization.CONTENT_ITEM_TYPE })
				.withValue(Organization.COMPANY, organization).build());
		// 更新note
		ops.add(ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
				.withSelection(Data.RAW_CONTACT_ID + "=?" + " AND "+ ContactsContract.Data.MIMETYPE + " = ?",new String[] { String.valueOf(id),Note.CONTENT_ITEM_TYPE }).withValue(Note.NOTE, note).build());
		try{
			context.getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
		} catch (Exception e) {
		}
	}
}
