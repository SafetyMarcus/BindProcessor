package au.com.easygoingapps.test;

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
