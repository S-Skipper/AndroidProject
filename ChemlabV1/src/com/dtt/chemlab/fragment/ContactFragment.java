package com.dtt.chemlab.fragment;

import java.util.ArrayList;
import java.util.List;

import com.dtt.chemlab.R;
import com.dtt.chemlab.activity.ContactActivity;
import com.dtt.chemlab.activity.UpdateContactActivity;
import com.dtt.chemlab.adapter.ContactAdapter;
import com.dtt.chemlab.objs.Contact;
import com.dtt.chemlab.util.HttpUtil;
import com.dtt.chemlab.view.RefreshableView;
import com.dtt.chemlab.view.RefreshableView.PullToRefreshListener;

import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

public class ContactFragment extends Fragment implements OnItemClickListener {

	private static final int UPDATED = 0;
	private View view;
	private Button setInfoButton;

	private ContactAdapter adapter;
	private ListView contactListView;
	private List<Contact> contactList;
	RefreshableView refreshableView;
	
	private List<String> resultList;

	private Handler handler = new Handler(){
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
		contactList = getContacts();
		adapter = new ContactAdapter(activity, R.layout.item_contact,
				contactList);
	}

	private List<Contact> getContacts() {
		List<Contact> contactList = new ArrayList<Contact>();
		
		updateContacts();
		
		return contactList;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// return super.onCreateView(inflater, container, savedInstanceState);
		view = inflater.inflate(R.layout.fragment_contacts, container, false);
		
		refreshableView = (RefreshableView) view.findViewById(R.id.refreshable_view);
		setInfoButton = (Button) view.findViewById(R.id.button_contact);
		setInfoButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				UpdateContactActivity.actionStart(getActivity(), "1");
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
					Thread.sleep(3000);
					//Toast.makeText(getActivity(), "Refreshed!", Toast.LENGTH_SHORT).show();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				refreshableView.finishRefreshing();
			}
		}, 0);

		return view;
	}
	
	private void updateContacts() {
		AsyncTask.execute(new Runnable(){
			
			@Override
			public void run() {
				String contactXML = HttpUtil.requestWebservice(null,"GetAllContacts", null, null);
				//Log.d("Tag", contactXML);
				getContactsWithResponse(contactXML);
				contactList.clear();
				int i=1;
				while (i < resultList.size()) {
				    Contact contact = new Contact(R.drawable.photo, resultList.get(i++), resultList.get(i++));
					contact.setQQNumber(resultList.get(i++));
					contact.setEmail(resultList.get(i++));
					contactList.add(contact);
					i++;
				}
				
				Message message = new Message();
				message.what = UPDATED;
				handler.sendMessage(message);
			}
		});
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Contact contact = contactList.get(position);
		ContactActivity.actionStart(getActivity(), contact);
	}
	
	private void getContactsWithResponse(String data){
		if (resultList==null) {
			resultList = new ArrayList<String>();
		}
		resultList.clear();
		String[] temp = data.split("string=");
		for (int i = 1; i < temp.length; i++) {
			temp[i] = temp[i].split(";")[0].trim();
			Log.d("Tag", temp[i]);
			resultList.add(temp[i]);
		}
		
	}
}
