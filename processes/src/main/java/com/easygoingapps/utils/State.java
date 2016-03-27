package com.easygoingapps.utils;

import org.apache.commons.lang3.mutable.Mutable;

import java.util.ArrayList;

/**
 * @author Marcus Hooper
 */
public class State<T> implements Mutable<T>
{
	T value;
	ArrayList<Observer<T>> observers;

	public State(T value)
	{
		this.value = value;
		observers = new ArrayList<>();
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
		for(int i = 0, size = observers.size(); i < size; i++)
			observers.get(i).onChange(value);
	}
}

