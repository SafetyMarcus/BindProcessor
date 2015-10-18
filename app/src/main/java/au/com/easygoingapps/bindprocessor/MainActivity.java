package au.com.easygoingapps.bindprocessor;

import android.graphics.Color;
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
	@Bind(R.id.hello_world)
	public State<String> title = new State<>("Hello World");

	@Bind(R.id.hello_checkbox)
	public State<Boolean> checked = new State<>(true);

	@Bind(R.id.hello_image_view)
	public State<Integer> colour = new State<>(Color.RED);

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		MainActivityViewBinding.bind(this);

		((TextView) findViewById(R.id.hello_display)).setText(title.getValue());
		((TextView) findViewById(R.id.checkbox_display)).setText(checked.getValue() ? "checked" : "unchecked");

		title.addObserver(stringObserver);
		checked.addObserver(boolObserver);

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

		FloatingActionButton colFab = (FloatingActionButton) findViewById(R.id.colour_fab);
		colFab.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				int val = new Random().nextInt(3);
				int col = Color.RED;
				if(val == 1)
					col = Color.BLUE;
				else if(val == 2)
					col = Color.GREEN;

				colour.setValue(col);
			}
		});
	}

	public Observer<String> stringObserver = new Observer<String>()
	{
		@Override
		public void onChange(String value)
		{
			((TextView) findViewById(R.id.hello_display)).setText(value);
		}
	};

	public Observer<Boolean> boolObserver = new Observer<Boolean>()
	{
		@Override
		public void onChange(Boolean value)
		{
			((TextView) findViewById(R.id.checkbox_display)).setText(value ? "checked" : "unchecked");
		}
	};
}
