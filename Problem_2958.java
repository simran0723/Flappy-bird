import java.util.HashMap;
import java.util.Map;

public class Problem_2958 {
    public static void main(String[] args) {
        int [] arr = {1,4,4,3};
        int k = 1;
        int max = Integer.MIN_VALUE;
        int i=0,j=0;
        Map<Integer,Integer> map = new HashMap<>();

        while(j<arr.length){
            if(map.containsKey(arr[j])){
                map.put(arr[j],map.get(arr[j])+1);
            }else{
                map.put(arr[j],1);
            }
            System.out.println(map);
            while(map.get(arr[j])>k){
               map.put(arr[i],map.get(arr[i])-1);
               if(map.get(arr[i])==0){
                   map.remove(arr[i]);
               }
               i++;
            }
            max = Math.max(max,j-i+1);
            j++;
        }
        System.out.println(max);
    }
}
