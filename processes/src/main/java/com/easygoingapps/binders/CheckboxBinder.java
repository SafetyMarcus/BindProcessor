package com.easygoingapps.binders;

import android.widget.CheckBox;
import android.widget.CompoundButton;
import com.easygoingapps.utils.State;

/**
 * @author Marcus Hooper
 */
public class CheckboxBinder implements CheckBox.OnCheckedChangeListener
{
	private State<Boolean> value;

	public CheckboxBinder(State<Boolean> value)
	{
		this.value = value;
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
	{
		value.setValue(isChecked);
	}
}