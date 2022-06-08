import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Searching{
                 public List<String> findPaths(GLane [] allLanes,Junction [] allJuncs,String src, String dst){
                     int dem1=0;
                     int dem2=0;
                      List<String> list = new ArrayList<String>();
                      List<String> list2 = new ArrayList<String>();
                     HashMap<String,Integer> hashMap2 =new HashMap<String, Integer>();
                     HashMap<String,Integer> hashMap3 =new HashMap<String, Integer>();
                     HashMap<String,Integer> hashMap = new HashMap<String,Integer>();
                     int check=allLanes.length;
                     int max = check*10 + allJuncs.length*3;
                     for(GLane element: allLanes){
                         if(element !=null ) {
                             if (element.strLd.equals(src)) dem1++;
                             if (element.strLd.equals(dst)) dem2++;
                         }
                     }
                     if(dem1>0&&dem2>0) {
                         for(GLane element: allLanes) {
                             if (element != null){
                                 if (element.strLd.equals(src)) {
                                     for (int i = 0; i < 3; i++) {
                                         if (element.form[i] > check) {
                                             int x = element.form[i] >> 16;
                                             int y = element.form[i] - (x << 16) - check;
                                             if (allJuncs[y].allLanes[x].strLd != null) {
                                                 list.add(allJuncs[y].allLanes[x].strLd);
                                                 hashMap.put(element.strLd+allJuncs[y].allLanes[x].strLd,1);
                                                 hashMap2.put(list.get(list.size() - 1), x);
                                                 hashMap3.put(list.get(list.size() - 1), y);
                                             }
                                         } else if (element.form[i] != -1) {
                                             if (element.form[i] % 2 == 1) {
                                                 list.add("-E" + Integer.toString(element.form[i] / 2 ));
                                                 hashMap.put(element.strLd + "-E" + Integer.toString(element.form[i] / 2 ),1);
                                                 hashMap3.put(list.get(list.size() - 1), element.form[i]);
                                             } else {
                                                 list.add("E" + Integer.toString(element.form[i] / 2));
                                                 hashMap.put(element.strLd + "E" + Integer.toString(element.form[i] / 2),1);
                                                 hashMap3.put(list.get(list.size() - 1), element.form[i]);
                                             }
                                         }
                                     }
                                     while (!list.get(0).equals(dst)) {
                                         list2.add(list.get(0));
                                         list.remove(0);
                                         int index = list2.size() - 1;
                                         if (list2.get(index).charAt(0) == '-' || list2.get(index).charAt(0) == 'E') {
                                             for (int i = 0; i < 3; i++) {
                                                 int index1 = hashMap3.get(list2.get(index));
                                                 if(allLanes[index1] != null ) {
                                                     if (allLanes[index1].form[i] > check ) {
                                                         int x = allLanes[index1].form[i] >> 16;
                                                         int y = allLanes[index1].form[i] - (x << 16) - check;
                                                         if (allJuncs[y].allLanes[x] != null && allJuncs[y].allLanes[x].strLd != null) {
                                                             list.add(allJuncs[y].allLanes[x].strLd);
                                                             hashMap.put(list2.get(index)+ allJuncs[y].allLanes[x].strLd,1);
                                                             hashMap2.put(list.get(list.size() - 1), x);
                                                             hashMap3.put(list.get(list.size() - 1), y);
                                                         }
                                                     } else if (allLanes[index1].form[i] != -1) {
                                                         if (allLanes[index1].form[i] % 2 == 1) {
                                                             list.add("-E" + Integer.toString(allLanes[index1].form[i] / 2));
                                                             hashMap.put(list2.get(index) + "-E" + Integer.toString(allLanes[index1].form[i] / 2),1);
                                                             hashMap3.put(list.get(list.size() - 1), allLanes[index1].form[i]);
                                                             if (list.get(0).equals(dst)) break;
                                                         } else {
                                                             list.add("E" + Integer.toString(allLanes[index1].form[i] / 2));
                                                             hashMap.put(list2.get(index) + "E" + Integer.toString(allLanes[index1].form[i] / 2),1);
                                                             hashMap3.put(list.get(list.size() - 1), allLanes[index1].form[i]);
                                                             if (list.get(0).equals(dst)) break;
                                                         }
                                                     }
                                                 }
                                             }
                                         } else {
                                             for (int i = 0; i < 3; i++) {
                                                 int index1 = hashMap3.get(list2.get(index));
                                                 int index2 = hashMap2.get(list2.get(index));
                                                 if (allJuncs[index1].allLanes[index2].form[i] > check*2) {
                                                     int x = allJuncs[index1].allLanes[index2].form[i] >> 16;
                                                     int y = allJuncs[index1].allLanes[index2].form[i] - (x << 16) - check;
                                                     if (allJuncs[y].allLanes[x] != null && allJuncs[y].allLanes[x].strLd != null) {
                                                         list.add(allJuncs[y].allLanes[x].strLd);
                                                         hashMap.put(list2.get(index) + allJuncs[y].allLanes[x].strLd,1);
                                                         hashMap2.put(list.get(list.size() - 1), x);
                                                         hashMap3.put(list.get(list.size() - 1), y);
                                                     }
                                                 } else if (allJuncs[index1].allLanes[index2].form[i] != -1) {
                                                     if (allJuncs[index1].allLanes[index2].form[i] % 2 == 1) {
                                                         list.add("-E" + Integer.toString(allJuncs[index1].allLanes[index2].form[i] / 2));
                                                         hashMap.put(list2.get(index)+ "-E" + Integer.toString(allJuncs[index1].allLanes[index2].form[i] / 2 ),1);
                                                         hashMap3.put(list.get(list.size() - 1), allJuncs[index1].allLanes[index2].form[i]);
                                                         if (list.get(0).equals(dst)) break;
                                                     } else {
                                                         list.add("E" + Integer.toString(allJuncs[index1].allLanes[index2].form[i] / 2));
                                                         hashMap.put(list2.get(index) + "E" + Integer.toString(allJuncs[index1].allLanes[index2].form[i] / 2 ),1);
                                                         hashMap3.put(list.get(list.size() - 1), allJuncs[index1].allLanes[index2].form[i]);
                                                         if (list.get(0).equals(dst))
                                                             break;
                                                     }
                                                 }
                                             }
                                         }
                                         if(list.isEmpty()) return null;
                                     }
                                     break;
                                 }
                         }
                         }
                     } else return null;
                     list2.add(dst);
                     list2.add(0,src);
                     int i = list2.size()-2;
                  for(;i>0;i--){
                         if(hashMap.get(list2.get(i)+list2.get(i+1)) ==null || hashMap.get(list2.get(i)+list2.get(i+1)) !=1)
                             list2.remove(i);
                     }
                       return list2;
                 }
}
