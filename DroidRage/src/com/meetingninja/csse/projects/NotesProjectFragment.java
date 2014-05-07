package com.meetingninja.csse.projects;

import objects.Note;
import android.view.Menu;
import android.view.MenuInflater;

import com.meetingninja.csse.R;
import com.meetingninja.csse.notes.NotesFragment;
import com.meetingninja.csse.notes.tasks.DeleteNoteTask;

public class NotesProjectFragment extends NotesFragment{

	ViewProjectActivity pCont;

	@Override
	protected void deleteNote(Note note){
		pCont.deleteNote(note);
	}
	@Override
	protected void delete(Note note) {
		pCont.deleteNote(note);
	}
	@Override
	public void editNote(){
		pCont.createNote();
	}

	public NotesProjectFragment setProjectController(ViewProjectActivity pCont){
		this.pCont = pCont;
		return this;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		return; // don't want to duplicate super menu
	}

}
