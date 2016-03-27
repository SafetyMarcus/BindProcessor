package com.easygoingapps.observers;

import android.widget.TextView;
import com.easygoingapps.utils.Observer;

/**
 * @author Marcus Hooper
 */
public class TextViewObserver implements Observer<Object>
{
	private TextView textView;

	public TextViewObserver(TextView textView)
	{
		this.textView = textView;
	}

	@Override
	public void onChange(Object value)
	{
		textView.setText(value.toString());
	}

}
