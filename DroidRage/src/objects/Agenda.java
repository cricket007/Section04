package objects;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.meetingninja.csse.SessionManager;
import com.meetingninja.csse.database.Keys;
import com.meetingninja.csse.extras.JsonUtils;
import com.meetingninja.csse.extras.NinjaTextUtils;

@JsonInclude(JsonInclude.Include.NON_NULL)
// Generated by : http://www.jsonschema2pojo.org/
@JsonPropertyOrder({ "agendaID", "title", "meetingID","userID", "content" })
public class Agenda implements IJSONObject<Agenda> {
	@JsonProperty("agendaID")
	private String agendaID;
	@JsonProperty("title")
	private String title;
	@JsonProperty("meetingID")
	private String attachedMeetingID;
	@JsonProperty("userID")
	private String userID;
	@JsonProperty("content")
	private ArrayList<Topic> topics = new ArrayList<Topic>();

	public Agenda() {
		// Required empty constructor
	}

	public Agenda(String title) {
		this.title = title;
	}

	public Agenda(Agenda copyAgenda) {
		setID(copyAgenda.getID());
		setTitle(copyAgenda.getTitle());
		setAttachedMeetingID(copyAgenda.getAttachedMeetingID());
		setTopics(copyAgenda.getTopics());
	}

	@JsonProperty("agendaID")
	public void setID(String id) {
		int testInt = Integer.valueOf(id);
		setID(testInt);
	}

	@JsonIgnore
	private void setID(int id) {
		this.agendaID = Integer.toString(id);
	}

	@JsonProperty("agendaID")
	public String getID() {
		return this.agendaID;
	}

	@JsonProperty("title")
	public String getTitle() {
		return (!NinjaTextUtils.isEmpty(title)) ? title : "";
	}

	@JsonProperty("title")
	public void setTitle(String title) {
		this.title = title;
	}

	public void addTopic(Topic topic) {
		topics.add(topic);
	}

	public void addTopic(int index, Topic topic) {
		if (index < topics.size() && index > 0)
			topics.add(index, topic);
	}

	@JsonProperty("content")
	public ArrayList<Topic> getTopics() {
		return topics;
	}

	@JsonProperty("content")
	public void setTopics(ArrayList<Topic> topics) {
		this.topics = topics;
	}

	@JsonProperty("meetingID")
	public String getAttachedMeetingID() {
		return attachedMeetingID;
	}

	@JsonProperty("meetingID")
	public void setAttachedMeetingID(String attachedMeetingID) {
		this.attachedMeetingID = attachedMeetingID;
	}

	public void pprint() {
		System.out.println(String.format("%s (%d)", getTitle(), getTopics()
				.size()));
		for (Iterator<Topic> i = topics.iterator(); i.hasNext();) {
			pprint(i.next(), 0);
		}
	}

	protected void pprint(Topic s, int depth) {
		final Topic root = s;
		final ArrayList<Topic> topicList = root.getTopics();
		for (int i = -1; i < depth; i++) {
			System.out.print("--");
		}
		System.out.println(String.format("%s (%d)", root.getTitle(),
				topicList.size()));
		for (Iterator<Topic> i = topicList.iterator(); i.hasNext();) {
			pprint(i.next(), depth + 1);
		}
	}

	@JsonIgnore
	public int getDepth() {
		int depth = 0;
		for (Iterator<Topic> i = topics.iterator(); i.hasNext();) {
			depth += depthHelper(i.next(), 0);
		}
		return depth;
	}

	@JsonIgnore
	private int depthHelper(Topic t, int depth) {
		int subDepth = 0;
		final Topic root = t;
		final ArrayList<Topic> topicList = root.getTopics();
		for (Iterator<Topic> i = topicList.iterator(); i.hasNext();) {
			subDepth = depthHelper(i.next(), depth + 1);
		}
		return subDepth;
	}

	public ArrayList<Topic> flatten() {
		ArrayList<Topic> flat_topics = new ArrayList<Topic>();
		if (topics.isEmpty())
			return flat_topics;
		flatten_helper(topics, flat_topics);
		return flat_topics;
	}

	public void flatten_helper(List<Topic> topics, ArrayList<Topic> flatList) {
		for (Topic item : topics) {
			if (item.getTopics().size() > 0) {
				Topic flat = new Topic(item.getTitle());
				flat.setTime(item.getTime());
				flatList.add(flat);
				flatten_helper(item.getTopics(), flatList);
			} else
				flatList.add(item);
		}
	}

	@JsonIgnore
	public String getUserID() {
		return this.userID;
	}

	@Override
	public JsonNode toJSON() throws JsonGenerationException, IOException {
		ByteArrayOutputStream _json = new ByteArrayOutputStream();
		// this type of print stream allows us to get a string easily
		PrintStream ps = new PrintStream(_json);
		// Create a generator to build the JSON string
		JsonGenerator jgen = JsonUtils.getJsonFactory().createGenerator(ps,
				JsonEncoding.UTF8);

		// Build JSON Object
		jgen.writeStartObject(); // start agenda
		jgen.writeStringField(Keys.Agenda.ID, getID());
		jgen.writeStringField(Keys.Agenda.TITLE, getTitle());
		jgen.writeStringField(Keys.Meeting.ID, getAttachedMeetingID());
		jgen.writeStringField(Keys.User.ID, getUserID());

		contentBuilder(jgen, getTopics());

		jgen.writeEndObject(); // end agenda
		jgen.close();

		// Get JSON Object payload from print stream
		String json = _json.toString("UTF8");
		ps.close();

		return JsonUtils.getObjectMapper().readTree(json);

	}

	private void contentBuilder(JsonGenerator jgen, ArrayList<Topic> topics)
			throws IOException {
		if (topics.isEmpty()) {
			jgen.writeArrayFieldStart("content");
			jgen.writeEndArray();
		} else {
			jgen.writeObjectFieldStart("content");
			for (int i = 0; i < topics.size(); i++) {
				jgen.writeObjectFieldStart("" + (i + 1));

				jgen.writeStringField(Keys.Agenda.TITLE, topics.get(i)
						.getTitle());
				jgen.writeStringField(Keys.Agenda.TIME, topics.get(i).getTime());
				jgen.writeStringField(Keys.Agenda.DESC, "");

				contentBuilder(jgen, topics.get(i).getTopics());

				jgen.writeEndObject();

			}
			jgen.writeEndObject();
		}
	}

}
