package au.com.easygoingapps.bindprocessor;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.easygoingapps.annotations.Observe;
import utils.State;

/**
 * @author Marcus Hooper
 */
public class DescriptionFragment extends DialogFragment
{
	@Observe(R.id.fragment_title)
	public State<String> title;
	@Observe(R.id.fragment_description)
	public State<String> description;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		Descriptor descriptor = DescriptionManager.getCurrentDescriptor();
		this.title = descriptor.title;
		this.description = descriptor.description;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = View.inflate(getActivity(), R.layout.description_fragment, container);
		DescriptionFragmentViewBinding.watch(getActivity());

		return view;
	}
}
