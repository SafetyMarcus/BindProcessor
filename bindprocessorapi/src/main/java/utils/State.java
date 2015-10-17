package utils;

import org.apache.commons.lang3.mutable.Mutable;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Junjun Deng 2015
 */
public class State<T> implements Mutable<T>, Observable<T>
{
	T value;

	Set<Observer<T>> observers;

	public State(T value)
	{
		this.value = value;
		observers = new HashSet<>();
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
		{
			observer.onChange(value);
		}
	}

	@Override
	public void observe(Observer<T> observer)
	{
		observers.add(observer);
	}

	@Override
	public void unObserve(Observer<T> observer)
	{
		observers.remove(observer);
	}
}

