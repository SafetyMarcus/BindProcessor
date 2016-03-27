package com.easygoingapps.observers;

import android.widget.CheckBox;
import android.widget.CompoundButton;
import com.easygoingapps.utils.Observer;
import com.easygoingapps.utils.State;

/**
 * @author Marcus Hooper
 */
public class CheckboxObserver implements Observer<Boolean>
{
	private CheckBox checkbox;
	private State<Boolean> state;

	public CheckboxObserver(CheckBox checkbox, State<Boolean> state)
	{
		this.checkbox = checkbox;
		this.state = state;
	}

	@Override
	public void onChange(Boolean value)
	{
		state.removeObserver(this);
		checkbox.setChecked(value);
		state.addObserver(this);
	}
}