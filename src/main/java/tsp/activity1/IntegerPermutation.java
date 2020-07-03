package tsp.activity1;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Set;
import java.util.HashSet;

public class IntegerPermutation{
    
    
    public static List<Integer> nextPermutation(final List<Integer> sequence){
        List<Integer> newSequence = new ArrayList<Integer>();
        for(Integer item:sequence){
            newSequence.add(item);
        }
        int current = Integer.MIN_VALUE;
        int previous;
        int y = 0;
        List<Integer> decreasing = new ArrayList<Integer>();
        for(int i = newSequence.size()-1; i >= 0; i--){
            
            previous = current;
            current = newSequence.get(i);
            if(current >= previous){
                decreasing.add(current);
            }else{
                int swap = newSequence.get(i);
                int min = Integer.MAX_VALUE;
                for(int z = 0; z <= decreasing.size()-1; z++){
                    if(decreasing.get(z) < min && decreasing.get(z) > swap){
                        min = decreasing.get(z);
                    }
                }
                newSequence.set(i, min);
                decreasing.set(decreasing.indexOf(min), swap);
                for(int x = 0; x < decreasing.size(); x++){
                    newSequence.set(i+x+1,decreasing.get(x));
                }
                break;
            }
            y = i;
        }
        if(y == 0){
            return Collections.emptyList();
        }else{
            return newSequence;
        }
    }

    
    public static Set<List<Integer>> allPermutations(int n){
        if(n <= 0){
            throw new IllegalArgumentException();
        }
        List<Integer> curPerm = new ArrayList<>();
        for(int i = 0; i <= n; i++){
            curPerm.add(i);
        }
        Set<List<Integer>> perms = new HashSet<List<Integer>>();
        perms.add(curPerm);
        boolean done = false;
        while(done == false){
            curPerm = nextPermutation(curPerm);
            if(curPerm.size() == 0){
                break;
            }
            perms.add(curPerm);
        }
        return perms;
    }
    
    
    public static Set<List<Integer>> allPermutationsBackTrack(int n){
        if(n <= 0){
            throw new IllegalArgumentException();
        }
        List<Integer> decisions = createDecisions(n);
        Set<List<Integer>> finalPerms = createPermsRecursively(decisions);
        
        return finalPerms;
    }


    private static List<Integer> createDecisions(int n) {
        List<Integer> decisions = new ArrayList<Integer>();
        for(int i = 0; i <= n; i++){
            decisions.add(i);
        }
        return decisions;
    }

    private static void updatePermsRecursively(List<Integer> decisions, Set<List<Integer>> perms, List<Integer> currentPerm){
        if(decisions.size() == 0){
            perms.add(copyList(currentPerm));
        }else{
            for(Integer num:decisions){
                currentPerm.add(num);
                List<Integer> newDecisions = copyList(decisions);
                newDecisions.remove(num);
                updatePermsRecursively(newDecisions, perms, currentPerm);
            }
        }
        if(currentPerm.size() != 0){
            currentPerm.remove(currentPerm.size()-1);
        }
        return;
    }

    private static Set<List<Integer>> createPermsRecursively(List<Integer> decisions){
        Set<List<Integer>> perms = new HashSet<>();
        List<Integer> currentPerm = new ArrayList<>();
        updatePermsRecursively(decisions, perms, currentPerm);
        return perms;
    }

    private static List<Integer> copyList(List<Integer> listMaster){
        List<Integer> copy = new ArrayList<Integer>();
        for(Integer item:listMaster){
            copy.add(item);
        }
        return copy;
    }
}