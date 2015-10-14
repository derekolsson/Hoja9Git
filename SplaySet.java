public class SplaySet<E extends Comparable<E>> implements WordSet{
	
	private SplayBST<Word> base;
	
	public SplaySet( ){
		base = new SplayBST<Word>( );
	}
	
	public Word get(Word word){
		if(base.contains(word)){
			Word w=base.get(word);
			return w;
		}
		else
			return null;
	}
	
	public void add(Word wordObject){
		base.insert(wordObject);
	}
	
}	
	