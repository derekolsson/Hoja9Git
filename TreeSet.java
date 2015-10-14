import java.util.TreeMap;

public class TreeSet<E extends Comparable<E>> implements WordSet{
	
	private TreeMap<Word,Word> base;
	
	public TreeSet(){
		base = new TreeMap<Word,Word>();
	}
	
	public Word get(Word word){
		Word w=base.get(word);
		return w;
	}
	
	public void add(Word wordObject){
		base.put(wordObject,wordObject);
	}
}
