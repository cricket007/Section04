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
package com.meetingninja.csse.database;

import java.util.List;
import java.util.Map;

import objects.Comment;
import objects.Note;
import android.net.Uri;

import com.android.volley.VolleyError;
import com.fasterxml.jackson.databind.JsonNode;
import com.meetingninja.csse.database.volley.JsonNodeRequest;
import com.meetingninja.csse.database.volley.JsonRequestListener;
import com.meetingninja.csse.extras.JsonUtils;

public class NotesDatabaseAdapter extends BaseDatabaseAdapter {

	public static String getBaseUrl() {
		return BASE_URL + "Note";
	}

	public static Uri.Builder getBaseUri() {
		return Uri.parse(getBaseUrl()).buildUpon();
	}

	public static void getNote(final String noteID,
			final AsyncResponse<Note> delegate) throws Exception {
		String _url = getBaseUri().appendPath(noteID).build().toString();

		JsonNodeRequest req = new JsonNodeRequest(_url, null,
				new JsonRequestListener() {
					@Override
					public void onResponse(JsonNode response, int statusCode,
							VolleyError error) {
						if (response != null) {
							delegate.processFinish(parseNote(response, noteID));
						} else {
							error.printStackTrace();
						}
					}
				});
	}

	public static void createNote(String user, Note n) throws Exception {
		// TODO Implement this method
		throw new Exception("createNote: Unimplemented");
	}

	public static void update(String noteID, Map<String, String> values)
			throws Exception {
		// TODO Implement this method
		throw new Exception("updateNote: Unimplemented");
	}

	public List<Comment> getComments(String noteID) throws Exception {
		// TODO Implement this method
		throw new Exception("getComments: Unimplemented");
	}

	public static Note parseNote(JsonNode node, String noteID) {
		Note n = new Note();
		n.setID(JsonUtils.getJSONValue(node, Keys.Note.ID));
		n.setTitle(JsonUtils.getJSONValue(node, Keys.Note.TITLE));
		n.setContent(JsonUtils.getJSONValue(node, Keys.Note.CONTENT));
		n.setDescription(JsonUtils.getJSONValue(node, Keys.Note.DESC));
		n.setCreatedBy(JsonUtils.getJSONValue(node, Keys.Note.CREATED_BY));
		n.setDateCreated(JsonUtils.getJSONValue(node, Keys.Note.UPDATED));
		return n;
	}
}