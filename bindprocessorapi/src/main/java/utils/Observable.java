package utils;

/**
 * @author Marcus Hooper
 */
public interface Observable<T>
{
	void observe(Observer<T> observer);
	void unObserve(Observer<T> observer);
}
