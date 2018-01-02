# Digital Tree Dictionary

This is a simple implementation of a dictionary using a digital tree (trie) as its data structure for storage.

A custom file format is used to minimize the file size and simulates the structure of the tree by traversing through the branches.

## Methods

### `void putWord(String word) `
#### Description
Puts a word into the dictionary.
#### Parameters
`word` : The word to be inserted
#### Returns
none  

### `boolean removeWord(String word)`
#### Description
Removes a word from the dictionary.
#### Parameters
`word`: The word to be removed
#### Returns
Returns true if the word is removed.

### `int findDifference(char[] w1, char[] w2)`
#### Description
Finds the difference between two words.
#### Parameters
`w1` : Character array of the first word.
`w2` : Character array of the second word.
#### Returns
Returns the position of the first different letter.
Returns length of `w1` if the length of `w1` and `w2` are the same.
Returns the minimum length of both if the length of `w1` and `w2` are different.

### `void addAll(List<String> words)`
#### Description
Adds all the words in the list into the dictionary.
#### Parameters
`words` : List of words
#### Returns
none

### `boolean search(String word)`
#### Description
Search for the word in the dictionary.
#### Parameters
`word` : The word to be searched
#### Returns
Returns true if the word exists, else returns false.

### `void saveToFile(String filename)`
#### Description
Saves the contents in the dictionary into a file.
#### Parameters
`filename` : The name of the file including the path to save to.
#### Returns
none

### `void writeChar(BufferedWriter writer, Letter node)`
#### Description
Writes a character into the file
#### Parameters
`writer` : An opened `BufferedWriter`.   
`node`   : The current visited node.
#### Returns
none
#### Throws
`IOException`

### `void readFromFile(String filename)`
#### Description
Reads contents from formatted file into dictionary.
#### Paramters 
`filename` : The name of the file including the path to read from.
#### Returns
none

### `int size()`
#### Description
Gets the number of words in the dictionary.
#### Returns
Returns the number of words in the dictionary.

