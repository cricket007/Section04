package objects;

import java.io.IOException;
import java.util.ArrayList;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonNode;
import com.meetingninja.csse.database.AsyncResponse;
import com.meetingninja.csse.database.Keys;
import com.meetingninja.csse.database.volley.UserVolleyAdapter;
import com.meetingninja.csse.extras.NinjaTextUtils;

@JsonInclude(JsonInclude.Include.NON_NULL)
// Generated by http://www.jsonschema2pojo.org/
@JsonPropertyOrder({ "meetingID", "title", "location", "datetime",
		"endDatetime", "description", "attendance" })
public class Meeting extends Event implements Parcelable, IJSONObject<Meeting> {
	private String meetingID;
	protected String location;

	private ArrayList<User> attendance = new ArrayList<User>();

	private enum Attendance_Status {
		YES(1), MAYBE(0), NO(-1), NO_RESPONSE(-2);

		Attendance_Status(int stat) {
		}

		@Override
		public String toString() {
			switch (this) {
			case YES:
				return "Yes";
			case NO:
				return "No";
			case MAYBE:
				return "Maybe";
			default:
				break;
			}
			return "No Repsonse";
		}
	}

	public Meeting() {
		// Required empty constructor
		setStartTime(0L);
		setEndTime(1L);
	}

	public Meeting(Parcel in) {
		readFromParcel(in);
	}

	public Meeting(Meeting copyMeeting) {
		setID(copyMeeting.getID());
		setTitle(copyMeeting.getTitle());
		setLocation(copyMeeting.getLocation());
		setStartTime(copyMeeting.getStartTime());
		setEndTime(copyMeeting.getEndTime());
		setDescription(copyMeeting.getDescription());
		setAttendance(copyMeeting.getAttendance());
	}

	public Meeting(Cursor crsr) {
		// get columns
		int idxID = crsr.getColumnIndex(Keys._ID);
		int idxTITLE = crsr.getColumnIndex(Keys.Meeting.TITLE);
		int idxLOCATION = crsr.getColumnIndex(Keys.Meeting.LOCATION);
		int idxSTART_TIME = crsr.getColumnIndex(Keys.Meeting.START);
		int idxEND_TIME = crsr.getColumnIndex(Keys.Meeting.END);
		int idxDESCRIPTION = crsr.getColumnIndex(Keys.Meeting.DESC);
		// set fields
		this.meetingID = "" + crsr.getInt(idxID);
		this.title = crsr.getString(idxTITLE);
		this.location = crsr.getString(idxLOCATION);
		setStartTime(crsr.getLong(idxSTART_TIME));
		setEndTime(crsr.getLong(idxEND_TIME));
		this.description = crsr.getString(idxDESCRIPTION);
	}

	// public Meeting(JsonNode node) {
	// this.meetingID = node.get(Keys.Meeting.ID).asText();
	// this.title = node.get(Keys.Meeting.TITLE).asText();
	// this.location = node.get(Keys.Meeting.LOCATION).asText();
	// this.startTime = node.get(Keys.Meeting.START).asText();
	// this.endTime = node.get(Keys.Meeting.END).asText();
	// this.description = node.get(Keys.Meeting.DESC).asText();
	// JsonNode attendees = node.get(Keys.Meeting.ATTEND);
	// attendance.clear();
	// if (attendees.isArray()) {
	// for (final JsonNode attendee : attendees) {
	// attendance.add(attendee.get(Keys.User.ID).asText());
	// }
	// }
	// }

	@Override
	public String getID() {
		return this.meetingID;
	}

	@Override
	public void setID(int id) {
		this.meetingID = Integer.toString(id);

	}

	public ArrayList<User> getAttendance() {
		return attendance;
	}

	public String getLocation() {
		return (!NinjaTextUtils.isEmpty(location)) ? location : "";
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setAttendance(ArrayList<User> attendance) {
		this.attendance = attendance;
	}

	public void addAttendee(String userID) {
		// this.attendance.add(new AttendeeWrapper(userID,
		// Attendance_Status.YES));
		UserVolleyAdapter.fetchUserInfo(userID, new AsyncResponse<User>() {

			@Override
			public void processFinish(User result) {
				Meeting.this.attendance.add(result);

			}
		});
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(getID());
		dest.writeString(getTitle());
		dest.writeString(getLocation());
		dest.writeString(getStartTime());
		dest.writeString(getEndTime());
		dest.writeString(getDescription());
		dest.writeList(getAttendance());

	}

	@SuppressWarnings("unchecked")
	private void readFromParcel(Parcel in) {
		meetingID = in.readString();
		title = in.readString();
		location = in.readString();
		startTime = in.readString();
		endTime = in.readString();
		description = in.readString();
		attendance = in.readArrayList(User.class.getClassLoader());
	}

	public static final Parcelable.Creator<Meeting> CREATOR = new Parcelable.Creator<Meeting>() {

		@Override
		public Meeting createFromParcel(Parcel in) {
			return new Meeting(in);
		}

		@Override
		public Meeting[] newArray(int size) {
			return new Meeting[size];
		}

	};

	public class AttendeeWrapper {
		private String _id;
		private Meeting.Attendance_Status _attending;

		public AttendeeWrapper() {
			// empty
		}

		public AttendeeWrapper(String userID, Attendance_Status attending) {
			_id = userID;
			_attending = attending;
		}

		public boolean isAttending() {
			switch (_attending) {
			case YES:
			case MAYBE:
			case NO_RESPONSE:
				return true;
			case NO:
				return false;
			}
			return false;
		}

		public String getID() {
			return _id;
		}

	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Meeting [meetingID=");
		builder.append(meetingID);
		builder.append(", title=");
		builder.append(title);
		builder.append(", location=");
		builder.append(location);
		builder.append(", description=");
		builder.append(description);
		builder.append("]");
		return builder.toString();
	}

	@Override
	public JsonNode toJSON() throws JsonGenerationException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

}
