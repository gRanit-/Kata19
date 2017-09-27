# Kata19

http://codekata.com/kata/kata19-word-chains/

Manual:

1) Use GraphInitializer implementations to initialize graph
  a) Load dictionary from List with loadDictionary method in BasicGraphInitalizer
  b) Load dictionary from file with loadDictionary method in FileBasedGraphInitializer
  
  All implementations require alphabet to work. You can set up your own with setAlphabet(Set<Character>) 
  or don't and initializer will do this for you!
  If you choose to set it up initializer will omit words which contain characters not found in the alphabet.
  Alphabet can't contain any white character.

2) Use createGraph() method and retrieve Map<String,Node>  (word to Node mapping) which represents graph

3) Use Kata19SolutionImpl findShortestWordChain(Node beginNode, Node endNode) method to find a solution.







