/*******************************************************************************
 * Copyright (C) 2014 The Android Open Source Project
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package com.android.meetingninja;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import objects.Agenda;
import objects.Event;
import objects.Meeting;
import objects.ObjectMocker;
import objects.Schedule;
import objects.Task;
import objects.Topic;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.meetingninja.database.AgendaDatabaseAdapter;
import com.android.meetingninja.database.JsonNodeRequest;
import com.android.meetingninja.database.UserDatabaseAdapter;
import com.android.meetingninja.meetings.MeetingItemAdapter;
import com.android.meetingninja.meetings.MeetingsFragment;
import com.android.meetingninja.notes.CreateNoteActivity;
import com.android.meetingninja.notes.NotesFragment;
import com.android.meetingninja.schedule.ScheduleAdapter;
import com.android.meetingninja.tasks.TasksFragment;
import com.android.meetingninja.user.LoginActivity;
import com.android.meetingninja.user.ProfileFragment;
import com.android.meetingninja.user.SessionManager;
import com.android.meetingninja.user.UserListFragment;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.foound.widget.AmazingListView;
import com.parse.ParseAnalytics;

/**
 * Main Activity Window.
 * 
 * @author moorejm
 * 
 */
public class MainActivity extends FragmentActivity {

	private static final String TAG = MainActivity.class.getSimpleName();

	/**
	 * Fields for the navigation drawer(s)
	 */
	private String[] leftDrawerItemNames; // labels
	private TypedArray leftDrawerItemIcons; // icons next to text
	private DrawerLayout drawerLayout; // the "frame" of main activity
	private ListView leftDrawerList; // the listviews for the
										// drawers
	private AmazingListView rightDrawerList;
	private ActionBarDrawerToggle drawerToggle; // open&close toggle
	private CharSequence mDrawerTitle; // title for the drawer
	private ArrayList<NavDrawerItem> leftDrawerItems; // object wrapper for left
														// drawer
	private NavDrawerListAdapter leftDrawerAdapter;
	private ScheduleAdapter rightDrawerAdapter;

	public enum DrawerLabel {
		MEETINGS(0), NOTES(1), TASKS(2), PROFILE(3), GROUPS(4), PROJECTS(5), SETTINGS(
				6), ABOUT(7), LOGOUT(8);

		private int position;

		private DrawerLabel(int position) {
			this.position = position;
		}

		public int getPosition() {
			return this.position;
		}

	}

	private Bundle icicle;
	private CharSequence mTitle;
	private SessionManager session;
	private static ProfileFragment profFrag;
	private static MeetingsFragment meetingsFrag;
	private static NotesFragment notesFrag;
	private static TasksFragment tasksFrag;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		icicle = new Bundle();
		if (savedInstanceState != null)
			this.icicle.putAll(savedInstanceState);

		SessionManager.getInstance().init(MainActivity.this);
		session = SessionManager.getInstance();

		// session.createLoginSession("749");

		// Check if logged in
		if (!session.isLoggedIn()) {
			Log.v(TAG, "User is not logged in");
			Intent login = new Intent(this, LoginActivity.class);
			// Bring login to front
			login.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			// User cannot go back to this activity
			// login.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
			// Show no animation when launching login page
			login.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			startActivity(login);
			finish(); // close main activity
		} else
			Log.v(TAG, "UserID " + session.getUserID() + " is logged in");

		// Else continue
		setContentView(R.layout.activity_main);
		setupActionBar();
		setupViews();
		setupLeftDrawer();
		// on first time display view for first nav item
		selectItem(session.getPage());

		setupRightDrawer();

		// Track the usage of the application with Parse SDK
		ParseAnalytics.trackAppOpened(getIntent());

	}

	private void setupActionBar() {
		// Set up the action bar.
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
	}

	private void setupViews() {
		drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		leftDrawerList = (ListView) findViewById(R.id.left_drawer);
		rightDrawerList = (AmazingListView) findViewById(R.id.right_drawer);
	}

	private void setupLeftDrawer() {
		// Get the strings
		leftDrawerItemNames = getResources().getStringArray(
				R.array.nav_drawer_items);
		// Get the icons
		leftDrawerItemIcons = getResources().obtainTypedArray(
				R.array.nav_drawer_icons);

		// Get the views

		leftDrawerItems = new ArrayList<NavDrawerItem>();
		for (int i = 0; i < leftDrawerItemNames.length; i++) {
			leftDrawerItems.add(new NavDrawerItem(leftDrawerItemNames[i],
					leftDrawerItemIcons.getResourceId(i, -1)));
		}
		// recycle the typed array
		leftDrawerItemIcons.recycle();

		leftDrawerList.setOnItemClickListener(new LeftDrawerClickListener());

		// setup the list
		leftDrawerAdapter = new NavDrawerListAdapter(getApplicationContext(),
				leftDrawerItems);
		leftDrawerList.setAdapter(this.leftDrawerAdapter);

		drawerToggle = new ActionBarDrawerToggle(this, // host activity
				drawerLayout, // DrawerLayout object
				R.drawable.ic_drawer, // nav drawer icon
				R.string.drawer_open, // "open drawer" description
				R.string.drawer_close) // "closed drawer" description
		{
			/** Called when a drawer has settled in a completely closed state. */
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}

			/** Called when a drawer has settled in a completely open state. */
			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}
		};

		drawerLayout.setDrawerListener(drawerToggle);
		// drawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
		// GravityCompat.END);

	}

	private void setupRightDrawer() {
		rightDrawerList.setPinnedHeaderView(LayoutInflater.from(this).inflate(
				R.layout.list_item_schedule_header, rightDrawerList, false));
		Schedule sched = new Schedule();
		try {
			sched = UserDatabaseAdapter.getSchedule("");
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Meeting m = new Meeting();
		m.setStartTime(0L);
		sched.addMeeting(m);
		m = new Meeting();
		m.setStartTime(918723912L);
		sched.addMeeting(m);
		m = new Meeting();
		m.setStartTime(928374983274L);
		sched.addMeeting(m);
		m = new Meeting();
		m.setStartTime(38501234L);
		for (int i = 0; i < 5; i++) {
			sched.addMeeting(m);
		}
		m = new Meeting();
		m.setStartTime(12321387L);
		sched.addMeeting(m);
		for (int i = 0; i < 3; i++) {
			sched.addMeeting(m);
		}
		sched.sort();
		rightDrawerAdapter = new ScheduleAdapter(MainActivity.this, sched);
		rightDrawerList.setAdapter(rightDrawerAdapter);
		rightDrawerAdapter.notifyDataSetChanged();
	}

	/** Swaps fragments in the main content view */
	private void selectItem(int position) {
		// Create a new fragment and specify the planet to show based on
		// position
		session.setPage(position);
		Fragment fragment = null;
		Bundle args = new Bundle();
		DrawerLabel clickedLabel = DrawerLabel.values()[position];
		switch (clickedLabel) {
		case MEETINGS:
			fragment = new MeetingsFragment();
			meetingsFrag = (MeetingsFragment) fragment;
			break;
		case NOTES:
			fragment = new NotesFragment();
			notesFrag = (NotesFragment) fragment;
			break;
		case TASKS:
			fragment = new TasksFragment();
			break;
		case PROFILE:
			fragment = new ProfileFragment();
			profFrag = (ProfileFragment) fragment;
			break;
		case GROUPS:
			// fragment = new DummyFragment();
			// args.putString("Content", "TODO: Groups Page");
			// fragment.setArguments(args);
			fragment = new UserListFragment();
			break;
		case PROJECTS:
			fragment = new DummyFragment();
			args.putString("Content", "TODO: Projects Page");
			fragment.setArguments(args);
			break;
		case SETTINGS:
			fragment = new DummyFragment();
			args.putString("Content", "TODO: Settings Page");
			fragment.setArguments(args);
			break;
		case ABOUT:
			fragment = new DummyFragment();
			args.putString("Content", "TODO: About Page");
			fragment.setArguments(args);
			break;
		case LOGOUT:
			logout();
			break;
		default:
			Log.e(TAG + "drawerClicked", clickedLabel.toString()
					+ " is not a valid page");
			break;
		}

		if (fragment != null) {
			// Insert the fragment by replacing any existing fragment
			FragmentManager fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.content_frame, fragment).commit();

			// Highlight the selected item, update the title, and close the
			// drawer
			leftDrawerList.setItemChecked(position, true);
			leftDrawerList.setSelection(position);
			setTitle(leftDrawerItemNames[position]);
			drawerLayout.closeDrawer(leftDrawerList);
		} else {
			// error in creating fragment
			Log.e(TAG, "Error in creating fragment");
		}
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// if (data != null) {
		// Log.v(TAG, "Result returned");
		// }
	}

	private void logout() {
		session.logoutUser();
		// clear local database
		// SQLiteHelper mySQLiteHelper = SQLiteHelper
		// .getInstance(MainActivity.this);
		// mySQLiteHelper.onUpgrade(mySQLiteHelper.getReadableDatabase(), 1,
		// 1);
		finish();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		Log.v(TAG, "SaveInstance...");
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.v(TAG, "Pausing");
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.v(TAG, "Resuming");
		if (session == null) {
			session = SessionManager.getInstance();
		}
		selectItem(session.getPage());

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		// Pass the event to ActionBarDrawerToggle, if it returns
		// true, then it has handled the app icon touch event
		if (drawerToggle.onOptionsItemSelected(item)) {
			return true;
		}

		// Handle other action bar items...
		switch (item.getItemId()) {
		case R.id.action_refresh:
			switch (DrawerLabel.values()[session.getPage()]) {
			case MEETINGS:
				Toast.makeText(this, "Refreshing Meetings", Toast.LENGTH_SHORT)
						.show();
				// meetingsFrag.fetchMeetings();
				meetingsFrag.populateList();
				return true;
			case NOTES:
				Toast.makeText(this, "Refreshing Notes", Toast.LENGTH_SHORT)
						.show();
				// notesFrag.fetchNotes();
				notesFrag.populateList();
				return true;
			default:
				return super.onOptionsItemSelected(item);
			}

		case R.id.action_new_meeting:
			meetingsFrag.editMeeting(null);
			return true;
		case R.id.action_new_note:
			Intent createNote = new Intent(this, CreateNoteActivity.class);
			startActivityForResult(createNote, 3);
			return true;
		case R.id.action_logout:
			logout();
			return true;
		case R.id.action_settings:
			Agenda go = Test.createAgenda();
			AgendaTask task = new AgendaTask();
			task.execute(go); // send
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}

	}

	/* Called whenever we call invalidateOptionsMenu() */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		boolean drawerOpen = drawerLayout.isDrawerOpen(leftDrawerList)
				|| drawerLayout.isDrawerOpen(rightDrawerList);
		for (int i = 0; i < menu.size(); i++) {
			menu.getItem(i).setVisible(!drawerOpen);
		}
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		drawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		drawerToggle.onConfigurationChanged(newConfig);
	}

	private class LeftDrawerClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView parent, View view, int position,
				long id) {
			selectItem(position);
		}
	}

	private static class Test {

		private static Agenda createAgenda() {
			Agenda ag = new Agenda();
			ag.setAttachedMeetingID("");
			ag.setTitle("Android Agenda");
			Topic topic1 = new Topic("1");
			topic1.addTopic(new Topic("1.1"));
			topic1.addTopic(new Topic("1.2"));
			topic1.addTopic(0, new Topic("1.01"));
			Topic topic2 = new Topic("2");
			Topic topic3 = new Topic("3");
			Topic subTopic3 = new Topic("3.1");
			subTopic3.addTopic(new Topic("3.1.1"));
			topic3.addTopic(subTopic3);
			ag.addTopic(topic1);
			ag.addTopic(topic2);
			ag.addTopic(topic3);
			return ag;
		}
	}

	class AgendaTask extends AsyncTask<Agenda, Void, Agenda> {

		@Override
		protected Agenda doInBackground(Agenda... params) {
			Agenda create = null;
			try {
				ObjectMapper maper = new ObjectMapper();
				
				System.out.println(maper.writeValueAsString(params[0]));
			
//				create = AgendaDatabaseAdapter.createAgenda(params[0]);
			} catch (IOException e) {
				Log.e(TAG, e.getLocalizedMessage());
			}
			return create;
		}

		@Override
		protected void onPostExecute(Agenda result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result != null)
				Log.v(TAG, result.getID());
		}

	}
}
