package com.easygoingapps.observers;

import android.widget.EditText;

import com.easygoingapps.utils.Observer;
import com.easygoingapps.utils.State;

/**
 * @author Marcus Hooper
 */
public class EditTextObserver implements Observer<String>
{
	private EditText editText;
	private State<String> state;

	public EditTextObserver(EditText editText, State<String> state)
	{
		this.editText = editText;
		this.state = state;
	}

	@Override
	public void onChange(String value)
	{
		state.removeObserver(this);
		int cursorPosition = editText.getSelectionStart();
		if(cursorPosition == editText.length())
			cursorPosition = -1;

		editText.setText(value);

		if(cursorPosition == -1)
			cursorPosition = editText.length();

		try
		{
			editText.setSelection(cursorPosition);
		}
		catch(IndexOutOfBoundsException e)
		{
			editText.setSelection(editText.length());
		}

		state.addObserver(this);
	}
}