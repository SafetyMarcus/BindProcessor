package utils;

import org.apache.commons.lang3.mutable.Mutable;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Marcus Hooper
 */
public class State<T> implements Mutable<T>
{
	T value;
	Set<Observer<T>> observers;

	public State(T value)
	{
		this.value = value;
		observers = new HashSet<>();
	}

	public void addObserver(Observer<T> observer)
	{
		observers.add(observer);
	}

	public void removeObserver(Observer<T> observer)
	{
		observers.remove(observer);
	}

	public void clearObservers()
	{
		observers.clear();
	}

	@Override
	public T getValue()
	{
		return value;
	}

	@Override
	public void setValue(T value)
	{
		this.value = value;
		for(Observer<T> observer : observers)
			observer.onChange(value);
	}
}

