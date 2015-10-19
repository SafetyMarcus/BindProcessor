package au.com.easygoingapps.bindprocessor;

/**
 * @author Marcus Hooper
 */
public class DescriptionManager
{
	private static Descriptor descriptor = new Descriptor();

	public static Descriptor getCurrentDescriptor()
	{
		return descriptor;
	}
}
