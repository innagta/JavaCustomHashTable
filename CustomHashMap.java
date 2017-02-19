/**
 * 
 * This is a custom HashMap data structure to handle list entries more than
 * 1000.
 * 
 * The CustomHashMap has implementations including Insertion, Deletion,
 * Searching, etc. 
 * The methods Insertion, Deletion and Searching take constant time.
 * 
 * @author IGTA
 *
 */
public class CustomHashMap {

	/*
	 * Inner class to define the properties of all nodes held in the LinkedList
	 * 
	 */
	
	private class LinkedListNode {
		private int key;
		private String value;
		private LinkedListNode next;
		
		/*
		 * Constructor
		 */
		public LinkedListNode(int key, String value){
			this.key = key;
			this.value = value;
			this.next = null;
		}
		
		/*
		 * Setters
		 */
		public void setKey (int newKey){
			this.key = newKey;
		}
				
		public void setValue (String newValue){
			this.value = newValue;
		}
				
		public void setNext (LinkedListNode newNext){
			this.next = newNext;
		}
				
				
		/*
		 * Getters
		 */
		public int getKey(){
			return key;
		}
		
		public String getValue(){
			return value;
		}
		
		public LinkedListNode getNext(){
			return next;
		}
		
	} //closes LinkedListNode Inner Class

//_________________________________________________________________________________________________________________________________________________		
	
	/*
	 * Instance variables of CustomHashMap Class
	 */
	private int hashTable;   //the size of the array storing references to the LinkedLists
	private LinkedListNode[] hashArray; 
	private int size;
		
	  /*
	   * Constructor
	   */
    public CustomHashMap(int hashSize){
    	this.hashTable = hashSize; 
    	this.size = 0;
	    this.hashArray = new LinkedListNode[hashTable];
		   for (int index = 0; index < hashTable; index++){
			   hashArray[index] = null;                             //initialize all the buckets to null
		   }
    }
    
    public int getHashTableSize(){
    	return this.hashTable;
    }

//_________________________________________________________________________________________________________________________________________________	   
    /*
     * get the size of the whole HashMap
     * 
     * @return size of the HashMap
     */
    public int getSize(){
    	return this.size;
    }

 //_________________________________________________________________________________________________________________________________________________	
    /*
     * Insert an entry to the LinkedList or replace an existing node's value
     * if its key matches the passed key
     * @param key
     * @param value
     * @return inserted value after adding a new entry or the replaced old value
     */
    public String put(int key, String value){
    	String message = "";
    	int hash = (key % hashTable);                                 //hash function
	
    	/*
    	 * if the bucket contains no entries
    	 */
    	if (hashArray[hash] == null){
    		hashArray[hash] = new LinkedListNode(key, value);
    		this.size++;
    	}
	
    	else {
    		LinkedListNode currentNode = hashArray[hash];                
    		while (currentNode.next != null && currentNode.key != key)
    			currentNode = currentNode.next;                           //traverse the LinkedList
    		if (currentNode.key == key){                                  //if we find a node with the same key, modify the value of the node
    			message = "The old value is " + currentNode.value;
    			currentNode.setValue(value);                              
    		}
    		else{
    			currentNode.setNext(new LinkedListNode(key, value));      //add new entry to the tail of the LinkedList
    			message = "The new value is " + currentNode.value;
    			this.size++;
    	    }
    	}
    	return message;
 	}
    
//_________________________________________________________________________________________________________________________________________________	    
   /*
    * Get the value of an entry based on its key
    * 
    * @param key
    * @return value or null if the key is not found
    */
    public String getValues(int key){
    	String message = "null";
    	int hash = (key % hashTable);                                 //hash function
    	
    	if (hashArray[hash] == null){                                      //empty bucket
    		return message;
    	}
    	else {
    		LinkedListNode currentNode = hashArray[hash];                
    		while (currentNode.next != null && currentNode.key != key)
    			currentNode = currentNode.next;                           //traverse the LinkedList
    		if (currentNode.key != key)                                   //we have reached the end of the list and the key doesn't match
    			return message;                              
    		else                                                          //we have found a node with matching key
    		    return currentNode.getValue();
		}
    }
//_________________________________________________________________________________________________________________________________________________	
   /*
    * Remove an entry if found or return null if doesn't exist
    * 
    * @param key
    * @return removed entry's value or null if not found
    */
    public String remove(int key){
		
		String message = "null";
		int hash = (key % hashTable);                                    //hash function 
		if(hashArray[hash] == null){                                          //bucket is empty
			return message;
		}
		else{
			                                     
				LinkedListNode previousNode = null; 
				LinkedListNode currentNode = hashArray[hash];  
			 				
				while(currentNode.next != null && currentNode.key != key){    //traverse the list keeping track of the previous and the current node
					previousNode = currentNode;
					currentNode = currentNode.next;
				}
				
				if(currentNode.key == key){
					if (previousNode == null){                               //the node with the matching key is the first element of the list
						this.size--;
						message = currentNode.value;
						hashArray[hash] = currentNode.next;
					}
					else {
						this.size--;
						message = currentNode.value;
						previousNode.setNext(currentNode.next);              //remove the current node and link the previous node to the next
					}
				}
			
        }
		return message;
	}
//_________________________________________________________________________________________________________________________________________________	
    /*
     * Merge-sort algorithm to sort the keys of the HashMap - runs in O(nlogn)
     */
    
    public void sortKeys(int[] a, int start, int end) 
    {
     //Merge-sort algorithm
        int n = end - start;         
        if (n <= 1) 
            return; 
        int mid = start + n/2; 
        // recursively sort 
        sortKeys(a, start, mid); 
        sortKeys(a, mid, end); 
        //merge the sorted arrays
        int[] temp = new int[n];
        int i = start, j = mid;
        for (int k = 0; k < n; k++) 
        {
            if (i == mid)  
                temp[k] = a[j++];
            else if (j == end) 
                temp[k] = a[i++];
            else if (a[j] < a[i]) 
                temp[k] = a[j++];
            else 
                temp[k] = a[i++];
        }    
        for (int k = 0; k < n; k++) 
            a[start + k] = temp[k];         
    }

//_________________________________________________________________________________________________________________________________________________	    
    /*
     * Retrieve all the keys in the HashMap
     * Sort the keys using merge-sort method
     * Return all keys as a sorted sequence 
     */
    public int[] allKeys(){
    	int [] all_keys = new int [getSize()];
    	int keysIndex = 0;
		for (int index = 0; index < hashArray.length; index++){
			if (hashArray[index] != null){
				LinkedListNode currentNode = hashArray[index];
				all_keys[keysIndex] = currentNode.getKey();
				keysIndex++;
				while (currentNode.next != null){
					currentNode = currentNode.next;
					all_keys[keysIndex] = currentNode.getKey();
					keysIndex++;
				}
			}
		}
    	sortKeys(all_keys, 0, getSize());
		
		return all_keys;
   }
    
//_________________________________________________________________________________________________________________________________________________	    
    /*
     * Retrieve and print all keys of the HashMap
     */
    public void printAllKeys(){
    	int [] all_keys = new int [getSize()];
    	
    	int keysIndex = 0;
		for (int index = 0; index < hashArray.length; index++){
			if (hashArray[index] != null){
				LinkedListNode currentNode = hashArray[index];
				all_keys[keysIndex] = currentNode.getKey();
				keysIndex++;
				while (currentNode.next != null){
					currentNode = currentNode.next;
					all_keys[keysIndex] = currentNode.getKey();
					keysIndex++;
				}
			}
		}
    			
		sortKeys(all_keys, 0, getSize());
		for (int i = 0; i<getSize();i++){
			System.out.println(all_keys[i]);
		}
		
    }
    
//_________________________________________________________________________________________________________________________________________________	
    
    /*
     * 
     * Get next keys of the passed key
     * @param key
     * @return next key or -1 if the passed key is the last one
     */
    public int nextKey(int key) {
		int[] all_keys = allKeys();
		for (int i = 0; i < all_keys.length; i++) {
			if (key == all_keys[i])
				if ((i + 1) < all_keys.length)
					return all_keys[i + 1];
				else
					return -1;
		}
		return -1;
	}
   
//_________________________________________________________________________________________________________________________________________________	
    /**
	 * Get previous key of the passed key
	 * 
	 * @param key
	 * @return previous key or -1 to indicate that the passed key is the first key
	 */
	public int prevKey(int key) {
		int[] all_keys = allKeys();
		for (int i = 0; i < all_keys.length; i++) {
			if (key == all_keys[i]){
				if ((i - 1) > 0)
					return all_keys[i - 1];
				else
					return -1;
			}
		}
		return -1;
	}

    
//_________________________________________________________________________________________________________________________________________________	
	/**
	 * Check if the HashMap contains the passed key
	 * 
	 * @param key
	 * @return true if yes or false if not found
	 */
	public boolean contains(int key){
		
    	int hash = (key % hashTable);                                 //hash function
    	
    	if (hashArray[hash] == null){                                      //empty bucket
    		return false;
    	}
    	else {
    		LinkedListNode currentNode = hashArray[hash];                
    		while (currentNode.next != null && currentNode.key != key)
    			currentNode = currentNode.next;                           //traverse the LinkedList
    		if (currentNode.key != key)                                   //we have reached the end of the list and the key doesn't match
    			return false;                              
    		else                                                          //we have found a node with matching key
    		    return true;
		}
     }

//_________________________________________________________________________________________________________________________________________________	
	/**
	 * Calculate the number of keys between the given keys
	 * 
	 * @param key1
	 * @param key2
	 * @return the number of keys between the given keys
	 */
	public int rangeKeys(int key1, int key2) {
		int temp = 0;
		if (key1 > key2) {
			temp = key2;
			key2 = key1;
			key1 = temp;
		}
		int start = 0, end = 0;
		if (contains(key1) && contains(key2)) {
			int[] all_keys = allKeys();
			for (int i = 0; i < all_keys.length; i++) {
				if (key1 == all_keys[i])
					start = i;
				if (key2 == all_keys[i])
					end = i;
			}
			return end - start - 1;
		}
		return -1;
	}
}