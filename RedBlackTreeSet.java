
import java.util.ArrayList;
public class RedBlackTreeSet <E extends Comparable<E>> implements WordSet{


	private RedBlackTree<Word> base;
	
	public RedBlackTreeSet()
	{
		base = new RedBlackTree<Word>();
	}
	
	public Word get(Word word)
	{
		
		return base.find(word);
	}
	
	public void add(Word wordObject)
	{
		base.insert(wordObject);
	}

}
