package com.chemlab.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.chemlab.R;
import com.chemlab.activity.ContactActivity;
import com.chemlab.activity.SearchActivity;
import com.chemlab.adapter.ContactAdapter;
import com.chemlab.objs.Contact;
import com.chemlab.util.HttpCallbackListener;
import com.chemlab.util.HttpUtil;
import com.chemlab.util.LogUtil;
import com.chemlab.view.RefreshableView;
import com.chemlab.view.RefreshableView.PullToRefreshListener;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ContactFragment extends Fragment implements OnItemClickListener {

	private static final int UPDATED = 0;

	private View view;

	private static ContactAdapter adapter;
	private ListView contactListView;
	private static List<Contact> contactList;
	RefreshableView refreshableView;

	private static String argvs = "";

	private static Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case UPDATED:
				adapter.notifyDataSetChanged();
				break;

			default:
				break;
			}
		};
	};

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		contactList = new ArrayList<Contact>();
		adapter = new ContactAdapter(activity, R.layout.item_contact,
				contactList);

		updateContacts();

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// return super.onCreateView(inflater, container, savedInstanceState);
		view = inflater.inflate(R.layout.fragment_contacts, container, false);

		refreshableView = (RefreshableView) view
				.findViewById(R.id.refreshable_view);
		view.findViewById(R.id.searchbar).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent intent = new Intent(getActivity(),
								SearchActivity.class);
						startActivity(intent);
					}
				});

		contactListView = (ListView) view.findViewById(R.id.contacts_list);
		contactListView.setAdapter(adapter);
		contactListView.setOnItemClickListener(this);
		refreshableView.setOnRefreshListener(new PullToRefreshListener() {
			@Override
			public void onRefresh() {
				try {
					updateContacts();
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				refreshableView.finishRefreshing();
			}
		}, 0);
		return view;
	}

	public static void updateContacts() {
		argvs = HttpUtil.createJsonStr("GetInfoAll", "");
		LogUtil.d("ContactFragment", argvs);
		HttpUtil.sendHttpRequest(HttpUtil.ADDRESS_LOGIN_HANDLER, argvs,
				new HttpCallbackListener() {
					@Override
					public void onFinish(String response) {
						LogUtil.d("ContactFragment", response);
						getContactsWithResponse(response);
					}
					@Override
					public void onError(Exception e) {
						e.printStackTrace();
					}
				});
	}

	private static void getContactsWithResponse(String responseString) {
		//LogUtil.d("ContactFragment", responseString);
		
		try {
			JSONObject responseObject = new JSONObject(responseString);

			if (responseObject.getString("error").equals("0")) {
				JSONArray jsonObjArray = responseObject.getJSONArray("data");

				if (jsonObjArray.length() != 0) {
					contactList.clear();

					for (int i = 0; i < jsonObjArray.length(); i++) {
						JSONObject contactInfo = jsonObjArray.getJSONObject(i);

						Contact contact = new Contact(R.drawable.photo);
						
						contact.setId(contactInfo.getString("ID"));
						contact.setName(contactInfo.getString("name"));
						contact.setCreate_time(contactInfo.getString("creat_time"));
						contact.setIdebtity(contactInfo.getString("idebtity"));
						contact.setEmail(contactInfo.getString("email"));
						contact.setQQNumber(contactInfo.getString("QQ"));
						contact.setPhoneNumber(contactInfo.getString("phonenumber_long"));
						contact.setPhoneShort(contactInfo.getString("phonenumber_short"));
						contact.setAddress(contactInfo.getString("address"));
						contact.setLast_time(contactInfo.getString("last_time"));
						
						contactList.add(contact);
					}

				}

				sendHandleMessage(UPDATED);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private static void sendHandleMessage(int mark) {
		Message message = new Message();
		message.what = mark;
		handler.sendMessage(message);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Contact contact = contactList.get(position);
		ContactActivity.actionStart(getActivity(), contact);
	}
}
