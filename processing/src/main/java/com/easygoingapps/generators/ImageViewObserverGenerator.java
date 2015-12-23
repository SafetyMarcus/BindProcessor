package com.easygoingapps.generators;

/**
 * @author Marcus Hooper
 */
public class ImageViewObserverGenerator extends SourceGenerator
{
	private static final String NAME = "ImageViewObserver";

	public ImageViewObserverGenerator()
	{
		super(NAME);
	}

	@Override
	public String getImports()
	{
		return new ImportBuilder().appendDrawable("ColorDrawable")
				.appendImageView()
				.appendObserver()
				.build();
	}

	@Override
	public String getInterfaces()
	{
		return "Observer<Integer>";
	}

	@Override
	public String getVariables()
	{
		VariablesBuilder builder = new VariablesBuilder();
		SourceVariable imageView = new SourceVariable("private", "ImageView", "imageView");
		variables.add(imageView);
		builder.appendVariable(imageView);

		return builder.build();
	}

	@Override
	public String getBody()
	{
		return new CodeBuilder().indent(1).appendOverride()
				.indent(1).append("public void onChange(Integer value)\n")
				.indent(1).append("{\n")
				.indent(2).append("imageView.setBackground(new ColorDrawable(value));\n")
				.indent(1).append("}").toString();
	}
}
