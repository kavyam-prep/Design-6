import java.util.*;

public class AutocompleteSystem{

//without trie and heap - o(Nlogk) -> N is all strings 
    //with trie - o(nlogk) - n -> all prefix matching ones 

    Map<String, Integer> map;
    String search;
    TrieNode root;
    class TrieNode{
        TrieNode[] children; //can use hashmap <char,trienode> children as well 
        // List<String> startsWith;
        List<String> topResults;
        public TrieNode(){
            this.children = new TrieNode[256];
            // this.startsWith = new ArrayList<>();
            this.topResults = new ArrayList<>();
        }
    }

    private void insert(String word){
        TrieNode curr = root;
        for(char c: word.toCharArray()){
            //-' ' cause space is also a valid char so we are not doing -'a' 
            if(curr.children[c - ' '] == null){
                curr.children[c-' '] = new TrieNode();
            }
            curr = curr.children[c-' '];
            //keep a sorted list here itself 
            List<String> lst = curr.topResults;
            if(!lst.contains(word)){
                lst.add(word);
            }
            Collections.sort(lst, (a, b) -> {
                if(map.get(a) == map.get(b)){
                    return a.compareTo(b);
                }
                else return map.get(b) - map.get(a); //descending order so
            });
            if(lst.size() > 3 ){
                lst.remove(lst.size()-1);
            }
            // curr.startsWith.add(word);
        }
    }

    private List<String> searchPrefix(String prefix){
        TrieNode curr = root;
        for(char c: prefix.toCharArray()){
            if(curr.children[c - ' '] == null){
                return new ArrayList<>();
            }
            curr = curr.children[c-' '];
        }
        // return curr.startsWith;
        return curr.topResults;
    }

    public AutocompleteSystem(String[] sentences, int[] times) {
        this.map = new HashMap<>();
        this.search = "";
        this.root = new TrieNode();

        for(int i =0; i < sentences.length; i++){
            // if(!map.containsKey(sentences[i])){
            //     insert(sentences[i]);
            // }
            map.put(sentences[i], map.getOrDefault(sentences[i],0)+times[i]);
            insert(sentences[i]);
        }

    }
    
    //o(Nlogk) 
    public List<String> input(char c) { 
        if(c == '#'){
            // if(!map.containsKey(search)){
            //     insert(search);
            // }
            map.put(search, map.getOrDefault(search,0)+1);
            insert(search);
            search ="";
            return new ArrayList<>();
        }
        search += c;

        // PriorityQueue<String> pq = new PriorityQueue<>((a, b) -> {
        //     if(map.get(a) == map.get(b)){
        //         return b.compareTo(a); //cause we want b on top and a on bottom 
        //     }
        //     return map.get(a) - map.get(b);
        // });
        // for(String sentence: map.keySet()){
        // List<String> list = searchPrefix(search);
        // for(String sentence: list){
        //     // if(sentence.startsWith(search)){
        //         pq.add(sentence);
        //         if(pq.size() > 3){
        //             pq.poll();
        //         }
        //     // }
        // }
        // List<String> result = new ArrayList<>();
        // while(!pq.isEmpty()){
        //     result.add(0,pq.poll()); //ok cause only 3 elements else it would be bad tc 
        // }

        //new way of having sorted list itself and only of size 3 
        // return result;

        return searchPrefix(search);
    }
}