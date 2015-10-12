public class SplaySet<E extends Comparable<E>> implements WordSet{
	
	private SplayBST<Word> base;
	
	public SplaySet(){
		base = new SplayBST<Word>();
	}
	
	public Word search(Word word){
		Word w=search(word);
		return w;
	}
	
	public void add(Word wordObject){
		add(wordObject);
	}
	
}	
	