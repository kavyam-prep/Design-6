import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class PhoneSystem {
    //can also change and have a used set instead 
    Set<Integer> unused;
    Queue<Integer> queue;
    public PhoneSystem(int maxNumbers) { //o(n)
        this.unused = new HashSet<>();
        this.queue = new LinkedList<>();

        for(int i = 0; i < maxNumbers; i++){
            unused.add(i);
            queue.offer(i);
        }
    }
    
    public int get() { //o(1)
        if(queue.isEmpty()){
            return -1;
        }    
        int num = queue.remove();
        unused.remove(num);
        return num;
    }
    
    public boolean check(int number) { //o(1)
        if(unused.contains(number)){
            return true;
        }else return false;
    }
    
    public void release(int number) { //o(1) 
        if(unused.contains(number)) return;
        unused.add(number);
        queue.offer(number);
    }
}
