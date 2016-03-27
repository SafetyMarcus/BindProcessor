package com.easygoingapps.utils;

/**
 * @author Marcus Hooper
 */
public interface Observer<T>
{
	void onChange(T value);
}
