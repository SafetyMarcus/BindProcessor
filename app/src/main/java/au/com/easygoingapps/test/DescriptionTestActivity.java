package au.com.easygoingapps.test;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import au.com.easygoingapps.bindprocessor.R;
import com.easygoingapps.annotations.Observe;
import com.easygoingapps.utils.State;

/**
 * @author Marcus Hooper
 */
public class DescriptionTestActivity extends Activity
{
	@Observe(R.id.title)
	public State<String> title;
	@Observe(R.id.description)
	public State<String> description;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.description_test);
		Descriptor descriptor = DescriptionManager.getCurrentDescriptor();
		title = descriptor.title;
		description = descriptor.description;
		au.com.easygoingapps.test.ThePolice.watch(this);

		findViewById(R.id.fab).setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				new DescriptionFragment().show(getFragmentManager(), "");
			}
		});
	}

	@Override
	protected void onResume()
	{
		super.onResume();
	}
}
