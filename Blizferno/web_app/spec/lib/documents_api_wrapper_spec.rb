require 'spec_helper'
require 'documents_api_wrapper'

describe DocumentsApiWrapper do
	let(:wrapper){
		DocumentsApiWrapper.new
	}

	it 'gets notes summaries from the database' do
		userID = 645
		notes_summaries = wrapper.get_user_notes(userID)
		notes_parsed = JSON.parse(notes_summaries)
		notes_parsed['notes'].each do |notes|
			notes.keys.should eq ['noteID', 'noteTitle']
		end
	end

	it 'gets note information' do
		noteID = 697.to_s
		note_string = wrapper.get_note(noteID)
		note_parsed = JSON.parse(note_string)
		note_parsed.keys.should eq ['noteID', 'content', 'title', 'description', 'dateCreated']
	end
end

#web_app/spec/lib/