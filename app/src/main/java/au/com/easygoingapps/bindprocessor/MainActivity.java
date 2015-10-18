package au.com.easygoingapps.bindprocessor;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import com.easygoingapps.annotations.Bind;
import utils.Observer;
import utils.State;

import java.util.Random;

public class MainActivity extends AppCompatActivity
{
	private MainActivityViewBinding binding;

	@Bind(R.id.hello_world)
	public State<String> title = new State<>("Hello World");

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		binding = new MainActivityViewBinding(this);

		((TextView) findViewById(R.id.hello_display)).setText(title.getValue());

		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				title.setValue(String.valueOf(new Random().nextInt(10)));
			}
		});
	}

	@Override
	protected void onPause()
	{
		super.onPause();
		binding.unObserve();
		title.unObserve(observer);
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		binding.observe();
		title.observe(observer);
	}

	public Observer<String> observer = new Observer<String>()
	{
		@Override
		public void onChange(String value)
		{
			((TextView) findViewById(R.id.hello_display)).setText(value);
		}
	};
}
