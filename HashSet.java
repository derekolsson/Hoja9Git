import java.util.HashMap;

public class HashSet<E extends Comparable<E>> implements WordSet{
	
	private HashMap<Word,Word> base;
	
	public HashSet(){
		base = new HashMap<Word,Word>();
	}
	
	public Word get(Word word){
		Word w=base.get(word);
		return w;
	}
	
	public void add(Word wordObject){
		base.put(wordObject,wordObject);
	}
}
