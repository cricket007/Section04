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
package com.android.meetingninja.meetings;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import objects.Meeting;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.meetingninja.R;
import com.android.meetingninja.extras.MyDateUtils;

/**
 * A class to display the meetings in a specific format for the items of the
 * list. This class uses the meeting_item XML file.
 * 
 * @author moorejm
 * 
 */
public class MeetingItemAdapter extends ArrayAdapter<Meeting> {
	// declaring our ArrayList of items
	private List<Meeting> meetings;
	private final Context context;

	/*
	 * Override the constructor to initialize the list to display
	 */
	public MeetingItemAdapter(Context context, int textViewResourceId,
			List<Meeting> meetings) {
		super(context, textViewResourceId, meetings);
		this.meetings = meetings;
		this.context = context;
	}

	// class for caching the views in a row
	private class ViewHolder {
		TextView title, timeSpan, location;
	}

	ViewHolder viewHolder;

	/*
	 * we are overriding the getView method here - this is what defines how each
	 * list item will look.
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (rowView == null) {
			rowView = inflater.inflate(R.layout.list_item_meeting, null);
			viewHolder = new ViewHolder();

			viewHolder.title = (TextView) rowView.findViewById(R.id.title);
			viewHolder.timeSpan = (TextView) rowView.findViewById(R.id.when);
			viewHolder.location = (TextView) rowView.findViewById(R.id.where);

			rowView.setTag(viewHolder);
		} else
			viewHolder = (ViewHolder) rowView.getTag();

		// Setup from the meeting_item XML file
		Meeting meeting = meetings.get(position);

		viewHolder.title.setText(meeting.getTitle());
		try {
			viewHolder.timeSpan.setText(getTimeSpan(
					meeting.getStartTime_Time(), meeting.getEndTime_Time(),
					false));
		} catch (ParseException e) {
			Log.e("MeetingItemAdapter", e.getLocalizedMessage());
		}
		viewHolder.location.setText(meeting.getLocation());

		return rowView;
	}

	private String getTimeSpan(long start, long end, boolean allDay) {
		StringBuilder spanBuilder = new StringBuilder();
		boolean is24 = android.text.format.DateFormat.is24HourFormat(context
				.getApplicationContext());
		SimpleDateFormat timeFormat = is24 ? MyDateUtils._24_TIME_FORMAT
				: MyDateUtils._12_TIME_FORMAT;
		SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy");
		spanBuilder.append(dateFormat.format(new Date(start)));
		if (!allDay) {
			spanBuilder.append(", " + timeFormat.format(new Date(start))
					+ " - ");
			spanBuilder.append(dateFormat.format(new Date(end)));
			spanBuilder.append(", " + timeFormat.format(new Date(end)));
		}

		return spanBuilder.toString();

	}

}
