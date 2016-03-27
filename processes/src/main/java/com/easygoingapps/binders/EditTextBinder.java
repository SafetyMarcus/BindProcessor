package com.easygoingapps.binders;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;


import com.easygoingapps.utils.Observer;
import com.easygoingapps.utils.State;

/**
 * @author Marcus Hooper
 */
public class EditTextBinder implements TextWatcher
{
	private State<String> value;

	public EditTextBinder(State<String> value)
	{
		this.value = value;
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after)
	{
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count)
	{
	}

	@Override
	public void afterTextChanged(Editable s)
	{
		if(value != null && s != null)
			value.setValue(s.toString());
	}
}