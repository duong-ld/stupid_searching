import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

public class Read {
    public static void main(String [] args ) {
        int temp;
        int numberline=0;
        int max = 0;
        int max1 =0;
        Vector<String> lane = new Vector<>(200,200);
        String url  ;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Nhap ten file can doc (Enter neu muon dung file input.txt): ");
        url = scanner.nextLine();
        if("".equals(url)) {
        	url = "input.txt";
        }
        String src ;
        String dst ;
        System.out.println("Nhap src (Enter => E0): ");
        src = scanner.nextLine();
        if("".equals(src)) {
        	src = "E0";
        }
        System.out.println("Nhap dst (Enter => E298): ");
        dst = scanner.nextLine();
        if("".equals(dst)) {
        	dst = "E298";
        }
        FileInputStream fileInputStream = null;
        BufferedReader bufferedReader = null;
        try{
            fileInputStream = new FileInputStream(url);
            bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
            String line = bufferedReader.readLine();
            while (line!=null) {
                numberline++;
                temp = line.length();
                for (int j = 1; j < temp; j++) {
                    if (line.charAt(j) == ' ') {
                        lane.add(line.substring(0, j));
                        break;
                    }
                }
                for (int i = 0; i < temp; i++) {
                    if (line.charAt(i) == 'E') {
                        for (int j = i + 1; j < temp; j++)
                            if (line.charAt(j) == '_') {
                                int k = Integer.parseInt(line.substring(i + 1, j));
                                if (max < k) max = k;
                                break;
                            }
                    }
                    else if (line.charAt(i) == 'J') {
                        for (int j = i + 1; j < temp; j++)
                            if (line.charAt(j) == '_') {
                                int k = Integer.parseInt(line.substring(i + 1, j));
                                if (max1 < k) max1 = k;
                                break;
                            }
                    }
                }
                line = bufferedReader.readLine();
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally {
            try {
                bufferedReader.close();
                fileInputStream.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        max++;max1++;
        GLane allLanes[]= new GLane[max*2];
        Junction allJuncs[] = new Junction[max1];
        String check[] = new String[numberline+1];
        int check1[] = new int[numberline+1];
        String chon[] = new String[numberline+1];
        int dem1 = 0;
        try {
            fileInputStream = new FileInputStream(url);
            bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
            String line = bufferedReader.readLine();
            while (line != null) {
                dem1++;
                int add=0;
                int k=0;
                temp = line.length();
                for(int i=0;i<temp;i++) {
                    if (line.charAt(i) == 'E') {
                        for (int j = i + 1; j < temp; j++)
                            if (line.charAt(j) == '_') {
                                k = Integer.parseInt(line.substring(i + 1, j));
                                if (i > 0 && line.charAt(i - 1) == '-') {
                                    k = k * 2 + 1;
                                    for (int u = j; u < temp; u++) {
                                        if (line.charAt(u) == ' ') {
                                            for (int v = u + 1; v < temp; v++) {
                                                if (line.charAt(v) == ' ' || v==temp-1 ) {
                                                    allLanes[k] = new GLane();
                                                 if(v==temp-1)  allLanes[k].length = Double.parseDouble(line.substring(u + 1, v+1));
                                                 else  allLanes[k].length = Double.parseDouble(line.substring(u + 1, v));
                                                    allLanes[k].number = (int) Math.ceil(allLanes[k].length / 1.1);
                                                    allLanes[k].strLd = line.substring(i - 1, j);
                                                    allLanes[k].lastLength = Math.abs(allLanes[k].length - allLanes[k].number * 1.1);
                                                    break;
                                                }
                                            }
                                            break;
                                        }
                                    }
                                } else for (int u = j; u < temp; u++) {
                                    if (line.charAt(u) == ' ') {
                                        for (int v = u + 1; v < temp; v++) {
                                            if (line.charAt(v) == ' ' || v==temp-1) {
                                                k = k * 2;
                                                allLanes[k] = new GLane();
                                                if(v==temp-1)  allLanes[k].length = Double.parseDouble(line.substring(u + 1, v+1));
                                                else  allLanes[k].length = Double.parseDouble(line.substring(u + 1, v));
                                                allLanes[k].number = (int) Math.ceil(allLanes[k].length / 1.1);
                                                allLanes[k].strLd = line.substring(i, j);
                                                allLanes[k].lastLength = Math.abs(allLanes[k].length - allLanes[k].number * 1.1);
                                                break;
                                            }
                                        }
                                        break;
                                    }
                                }

                                for (int a = j + 1; a < temp; a++){
                                    if (line.charAt(a) == 'J') {
                                        for (int b = a + 1; b < temp; b++) {
                                            if (line.charAt(b) == '_') {
                                                int l = Integer.parseInt(line.substring(a + 1, b));
                                                int index ;
                                                if(line.charAt(b+2)=='_')   index = Character.getNumericValue(line.charAt(b + 1));
                                                else index = Integer.parseInt(line.substring(b+1,b+3));
                                                allLanes[k].form[add] = ((max*2 + l) | (index << 16));
                                                add++;
                                                    if (allJuncs[l] == null)  allJuncs[l] = new Junction();
                                                    allJuncs[l].allLanes[index] = new GLane();
                                                    for (int A = b + 1; A < temp; A++) {
                                                        if (line.charAt(A) == ' ' || A == temp - 1) {
                                                            if (A == temp - 1)  check[dem1] = line.substring(a - 1, A + 1);
                                                            else check[dem1] = line.substring(a - 1, A);
                                                            check1[dem1] = l;
                                                            if(line.charAt(b+2)=='_') chon[dem1] = line.substring(a - 1, b + 2);
                                                            else chon[dem1] = line.substring(a - 1, b + 3);
                                                            for (int m = dem1; m < numberline; m++) {
                                                                if (check[dem1].equals(lane.get(m))) {
                                                                    allJuncs[l].allLanes[index].strLd = chon[dem1];
                                                                    break;

                                                                }
                                                            }
                                                            break;
                                                        }
                                                    }
                                                break;
                                            }
                                        }
                                    }else if(line.charAt(a)=='E'){
                                        for(int i1 = a+1;i1<temp;i1++){
                                            if(line.charAt(i1)=='_'){
                                                if(line.charAt(a-1)=='-') allLanes[k].form[add]=Integer.parseInt(line.substring(a+1,i1))*2+1;
                                                else  allLanes[k].form[add] = Integer.parseInt(line.substring(a+1,i1))*2;
                                            }
                                        }
                                    }
                            }
                                break;

                            }
                        break;
                    }else if(line.charAt(i)=='J'){
                        for (int j = i + 1; j < temp; j++){
                            if (line.charAt(j) == '_') {
                                k = Integer.parseInt(line.substring(i + 1, j));
                                if (allJuncs[k] == null) allJuncs[k] = new Junction();
                                for (int u = j+1; u < j+4; u++) {
                                    if (line.charAt(u) == '_') {
                                        for (int v = u + 1; v < temp; v++) {
                                            if (line.charAt(v) == ' '){
                                                for(int n = v+1;n<temp;n++){
                                                    if(line.charAt(n)==' ' || n == temp-1 ){
                                                        int index = Integer.parseInt(line.substring(j+1,u));
                                                        int index1=0;
                                                    if(allJuncs[k].allLanes[index]==null)   allJuncs[k].allLanes[index] = new GLane();
                                                     if(n==temp-1)   allJuncs[k].allLanes[index].length = Double.parseDouble(line.substring(v+1,n+1));
                                                     else allJuncs[k].allLanes[index].length = Double.parseDouble(line.substring(v+1,n));
                                                        for(int i1 = n+1;i1< temp;i1++){
                                                            if(line.charAt(i1)=='E'){
                                                                for(int j1 =i1+1;j1<temp;j1++){
                                                                    if(line.charAt(j1)=='_'){
                                                                        if(line.charAt(i1-1)=='-')  allJuncs[k].allLanes[index].form[index1] = Integer.parseInt(line.substring(i1+1,j1)) *2+1;
                                                                        else allJuncs[k].allLanes[index].form[index1] = Integer.parseInt(line.substring(i1+1,j1)) *2 ;

                                                                        index1++;
                                                                        break;
                                                                    }
                                                                }
                                                            }else if (line.charAt(i1)=='J'){
                                                                for(int j1 =i1+1;j1<temp;j1++){
                                                                    if(line.charAt(j1)=='_'){
                                                                      int l =  Integer.parseInt(line.substring(i1+1,j1));
                                                                      int index2;
                                                                        if(line.charAt(j1+2)=='_')   index2 = Character.getNumericValue(line.charAt(j1 + 1));
                                                                        else index2 = Integer.parseInt(line.substring(j1+1,j1+3));
                                                                        allJuncs[k].allLanes[index].form[index1] =(max*2+ l )|(index2<<16);
                                                                        for (int A = j1 + 1; A < temp; A++) {
                                                                            if (line.charAt(A) == ' ' || A == temp - 1) {
                                                                                if (A == temp - 1)  check[dem1] = line.substring(i1 - 1, A + 1);
                                                                                else check[dem1] = line.substring(i1 - 1, A);
                                                                                check1[dem1] = l;
                                                                                if(line.charAt(j1+2)=='_') chon[dem1] = line.substring(i1 - 1, j1 + 2);
                                                                                else  chon[dem1] = line.substring(i1 - 1, j1 + 3);
                                                                                for (int m = dem1; m < numberline; m++) {
                                                                                    if (check[dem1].equals(lane.get(m))) {
                                                                                        allJuncs[l].allLanes[index2] = new GLane();
                                                                                        allJuncs[l].allLanes[index2].strLd = chon[dem1];
                                                                                        break;

                                                                                    }
                                                                                }
                                                                                break;
                                                                            }
                                                                        }
                                                                        index1++;
                                                                        break;
                                                                    }
                                                                }
                                                            }
                                                        }
                                                        break;
                                                    }
                                                }
                                                break;
                                            }

                                        }
                                    }
                                }
                                break;
                            }
                        }
                        break;
                    }
                }

                line = bufferedReader.readLine();
            }

            }
        catch(Exception e){
            e.printStackTrace();
        }
        finally {
            try {
                bufferedReader.close();
                fileInputStream.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        Searching searching = new Searching();
        List<String> list ;
        
        do {
        	list = searching.findPaths(allLanes,allJuncs,src,dst);
        
        	if(list == null){
        		System.out.println("Khong tim duoc");
        	}
	        else {
	            for (int i = 0; i < list.size(); i++) {
	                  if(list.get(i).charAt(0) != ':')
	                System.out.print(list.get(i) + " ");
	            }
	        }
        	System.out.println("\nNhap src (Enter => E0): ");
            src = scanner.nextLine();
            if("".equals(src)) {
            	src = "E0";
            }
            System.out.println("Nhap dst (Enter => E298): ");
            dst = scanner.nextLine();
            if("".equals(dst)) {
            	dst = "E298";
            }
        }while(!"END".equals(src) && !"END".equals(dst));
    }
}
