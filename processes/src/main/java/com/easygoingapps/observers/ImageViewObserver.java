package com.easygoingapps.observers;

import android.graphics.drawable.ColorDrawable;
import android.widget.ImageView;
import com.easygoingapps.utils.Observer;

/**
 * @author Marcus Hooper
 */
public class ImageViewObserver implements Observer<Integer>
{
	private ImageView imageView;

	public ImageViewObserver(ImageView imageView)
	{
		this.imageView = imageView;
	}

	@Override
	public void onChange(Integer value)
	{
		imageView.setBackground(new ColorDrawable(value));
	}
}