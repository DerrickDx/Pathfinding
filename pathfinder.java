import java.io.*;
import java.util.*;

public class pathfinder {


    public static int sequence = 0;

    public static void main(String[] args) {

        String map_name;

        String algorithm_name;

        BufferedReader br = null;

        String currentLine;

        try {

            map_name = args[0];
            algorithm_name = args[1];

            File file = new File(map_name);

//            System.out.println("map name: "+map_name);
//            System.out.println("algorithm name: "+algorithm_name);

            br = new BufferedReader(new FileReader(file));

            String textLine;

            int size_i =0;
            int size_j =0;
            int start_i =0;
            int start_j =0;
            int end_i =0;
            int end_j = 0;

            int line_number = 1;

            while(line_number <= 3 && (textLine = br.readLine())!=null){
                if (line_number == 1) {
                    size_i = Integer.parseInt(textLine.split(" ")[0]);
                    size_j = Integer.parseInt(textLine.split(" ")[1]);
                }
                else if (line_number == 2) {
                    start_i = Integer.parseInt(textLine.split(" ")[0]);
                    start_j = Integer.parseInt(textLine.split(" ")[1]);

                } else if (line_number == 3) {
                    end_i = Integer.parseInt(textLine.split(" ")[0]);
                    end_j = Integer.parseInt(textLine.split(" ")[1]);
                }
                line_number++;
            }

            String[] row_arr;
            int[][] map_in_array = new int[size_i][size_j];
            boolean[][] map_shown_visited = new boolean[size_i][size_j];
            line_number = 0;
            while ((textLine = br.readLine())!=null) {
                row_arr =  textLine.replaceAll("X","-1").split(" ");
                map_in_array[line_number] = Arrays.asList(row_arr).stream().mapToInt(Integer::parseInt).toArray();
                line_number++;
            }

            for (int i = 0; i < size_i; i++) {
                for (int j = 0; j < size_j; j++) {
//                    System.out.print( map_in_array[i][j]+" ");
                }
//                System.out.println();
            }



            Pos return_pos = null;

            if (algorithm_name.equals("bfs")) {
                LinkedList<Pos> pos_queue = new LinkedList<Pos>();


                map_shown_visited[start_i - 1] [start_j - 1] = true;

                pos_queue.add(new Pos(start_i, start_j, 0, null,  0, 0));

//                System.out.println("pos_queue.size()1:" +pos_queue.size());
                return_pos =BFS(map_in_array, map_shown_visited, start_i, start_j, end_i, end_j, pos_queue);


            }

            if (algorithm_name.equals("ucs")) {

                PriorityQueue<Pos> posPriorityQueue = new PriorityQueue<Pos>(
                        new Comparator<Pos>( ) {

                            //                            @Override
//                            public int compare(Pos pos1, Pos pos2) {
//
//                                if(pos1.distance==pos2.distance) {
//                                    return pos1.sequence-pos2.sequence;
//                                }
//                                else return pos1.distance - pos2.distance;
//                            }
                            @Override
                            public int compare(Pos pos1, Pos pos2) {

                                if (pos1.distance < pos2.distance){
                                    return -1;
                                }
                                if (pos1.distance > pos2.distance){

                                    return 1;
                                }
                                if(pos1.sequence < pos2.sequence){

                                    return -1;
                                }
                                return 1;
                            }
                        }
                );

                map_shown_visited[start_i - 1] [start_j - 1] = false;
                Pos initial_pos = new Pos(start_i, start_j, 0, null,  sequence, 0);
                posPriorityQueue.add(initial_pos);

                return_pos = UCS(map_in_array, map_shown_visited, end_i, end_j, posPriorityQueue);

            }

            if (algorithm_name.equals("astar")) {

                PriorityQueue<Pos> posPriorityQueue = new PriorityQueue<Pos>(
                        new Comparator<Pos>( ) {

                            @Override
                            public int compare(Pos pos1, Pos pos2) {

                                if (pos1.dist_h < pos2.dist_h){
                                    return -1;
                                }
                                if (pos1.dist_h > pos2.dist_h){

                                    return 1;
                                }
                                if(pos1.sequence < pos2.sequence){

                                    return -1;
                                }
                                return 1;
                            }
//                            @Override
//                            public int compare(Pos pos1, Pos pos2) {
//
//                                if(pos1.dist_h == pos2.dist_h) {
//                                    if (pos1.distance < pos2.distance){
//                                        return -1;
//                                    }
//                                    if (pos1.distance > pos2.distance){
//
//                                        return 1;
//                                    }
//                                    if(pos1.sequence < pos2.sequence){
//
//                                        return -1;
//                                    }
//                                    return 1;
//
//                                }
//                                else
//                                    return (int)(pos1.dist_h - pos2.dist_h);
//                            }
                        }
                );
//
                map_shown_visited[start_i - 1] [start_j - 1] = false;
                double dist = 0;
                if (args[2].equals("manhattan")) {
                    dist =  Math.abs(start_i-end_i)+Math.abs(start_j - end_j);
                } else if (args[2].equals("euclidean")){
                    dist = Math.sqrt(Math.pow((start_i-end_i),2) + Math.pow((start_j - end_j),2));
                } else {
                    return;
                }

                Pos initial_pos = new Pos(start_i, start_j, dist, null,  sequence, dist);
                posPriorityQueue.add(initial_pos);

                return_pos = ASTAR(map_in_array, map_shown_visited, end_i, end_j, posPriorityQueue, args[2]);

//                } else {
//
//                }
            }

            if(return_pos == null) {
                System.out.println("null");
            } else {
                LinkedList<Pos> list = new LinkedList<Pos>();

                List<String> temp_list = new ArrayList<String>();
                temp_list.add(return_pos.i + "," + return_pos.j);
                Pos prep = return_pos.pre;

                while (prep != null) {
                    temp_list.add(prep.i + "," + prep.j);
                    //                    System.out.print("x:" + prep.i+" ,y:" + prep.j);
                    prep = prep.pre;
                    //                    System.out.println();
                }

                for (int i = 0; i < temp_list.size(); i++) {

                    String[] sss = temp_list.get(i).split(",");
                    int ier = Integer.parseInt(sss[0]);
                    int jer = Integer.parseInt(sss[1]);
                    map_in_array[ier - 1][jer - 1] = -2;
                }

                for (int i = 0; i < size_i; i++) {
                    for (int j = 0; j < size_j; j++) {
                        if (map_in_array[i][j] == -1) {
                            System.out.print("X");
                        } else if (map_in_array[i][j] == -2) {
                            System.out.print("*");
                        } else {
                            System.out.print(map_in_array[i][j]);
                        }
                        if (j != size_j - 1) {
                            System.out.print(" ");
                        }
                    }
                    System.out.println();
                }
            }
            br.close();

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static Pos ASTAR(int[][] arr, boolean[][] explored_map, int end_i, int end_j, PriorityQueue<Pos> pos_queue, String heuristic) {
        while(!pos_queue.isEmpty()){

            Pos pos = pos_queue.poll();
//            System.out.println("enter!!!!!!!!!  i="+pos.i+",j="+pos.j);

            if (pos.i == end_i && pos.j == end_j) {

                return pos;
            }

            if (!explored_map[pos.i - 1][pos.j - 1]) {

                explored_map[pos.i - 1][pos.j - 1] = true;

                for (int i = 0; i < 4; i++) {

                    int node_num = arr[pos.i - 1][pos.j - 1];
                    Map<Integer,Double> map = new HashMap<>();
                    int ii = pos.i;
                    int jj = pos.j;

                    if (i == 0) {
                        ii = ii - 1;
                    } else if (i == 1) {
                        ii = ii + 1;
                    } else if (i == 2) {
                        jj = jj - 1;
                    } else {
                        jj = jj + 1;
                    }

                    if(jj < 1 || ii < 1 || jj > arr[0].length || ii > arr.length) {
                        continue;
                    }
                    if (arr[ii - 1][jj - 1] == -1) {
                        continue;
                    }


                    if (!explored_map[ii - 1][jj - 1]) {

                        int current_node_num = arr[ii - 1][jj - 1];
//                        System.out.println(" ["+ii+","+jj+"]"+", current_node_num:"+current_node_num+", node_num:"+node_num);

                        double new_path = 0;
                        if (current_node_num > node_num) {
                            new_path = 1 + current_node_num - node_num;
                        }else {
                            new_path = 1;
                        }
                        new_path = new_path + pos.distance;
                        double h = 0;
                        double f = 0;

                        if (heuristic.equals("manhattan")) {
                            h =  Math.abs(ii-end_i)+Math.abs(jj - end_j);
                        } else if (heuristic.equals("euclidean")){

                            h = Math.sqrt(Math.pow((Math.abs(ii-end_i)),2) + Math.pow((Math.abs(jj - end_j)),2));
                        }

                        f = new_path + h;
//                        System.out.println("jj:"+jj+", endx:"+end_i+", h:"+h+", f:"+f);

//                        if ()
                        map.put(i,f);

                        sequence++;
                        Pos nn = new Pos(ii,jj, new_path, pos, sequence, f);

                        pos_queue.add(nn);

//                        System.out.println("pos :["+ii+","+jj+"]"+", current cost:"+new_path+", f:"+f);
                    }
//                    System.out.println("end["+ii+","+jj);
                }
            }
        }

        return null;
    }

    public static Pos UCS(int[][] arr, boolean[][] explored_map, int end_i, int end_j, PriorityQueue<Pos> pos_queue) {

        while(!pos_queue.isEmpty()){
            Pos pos = pos_queue.poll();
//            System.out.println("enter!!!!!!!!!  x="+pos.i+",y="+pos.j+", node_num=");

            if (pos.i == end_i && pos.j == end_j) {
                return pos;
            }

            if (!explored_map[pos.i - 1][pos.j - 1]) {

                explored_map[pos.i - 1][pos.j - 1] = true;

                for (int i = 0; i < 4; i++) {

                    int node_num = arr[pos.i - 1][pos.j - 1];

                    int ii = pos.i;
                    int jj = pos.j;

                    if (i == 0) {
                        ii = ii - 1;
                    } else if (i == 1) {
                        ii = ii + 1;
                    } else if (i == 2) {
                        jj = jj - 1;
                    } else {
                        jj = jj + 1;
                    }

                    if(jj < 1 || ii < 1 || jj > arr[0].length || ii > arr.length) {
                        continue;
                    }
                    if (arr[ii - 1][jj - 1] == -1) {
                        continue;
                    }

                    if (!explored_map[ii - 1][jj - 1]) {

                        int current_node_num = arr[ii - 1][jj - 1];
//                        System.out.println(" ["+jj+","+ii+"]"+", current_node_num:"+current_node_num+", node_num:"+node_num);
                        double new_path = 0;
                        if (current_node_num > node_num) {
                            new_path = 1 + current_node_num - node_num;
                        }else {
                            new_path = 1;
                        }
                        new_path = new_path + pos.distance;

                        sequence++;

                        Pos nn = new Pos(ii,jj, new_path, pos, sequence, 0);

                        pos_queue.add(nn);
//                        System.out.println("pos :["+ii+","+jj+"]"+", current cost:"+new_path+", distance:"+new_path);
                    }
                }
            }
//            Pos a = pos_queue.peek();
//            if (a != null) {
//                System.out.println("-----------peak :[" + a.i + "," + a.j + "]"+", dis:"+a.distance);
//            }else {
//                System.out.println("===============================================================================");
//                pos_queue.add(pos.pre);
//            }
        }

        return null;
    }

    public static Pos BFS(int[][] arr, boolean[][] map_shown_visited,  int current_x, int current_y, int end_i, int end_j, LinkedList<Pos> pos_queue) {


        while(!pos_queue.isEmpty()){
//            int[] current_pos = queue.poll();
            Pos pos = pos_queue.poll();

            for (int i = 0; i < 4; i++) {

                int ii = pos.i;
                int jj = pos.j;

                if (i == 0) {
                    ii = ii - 1;
                } else if (i == 1) {
                    ii = ii + 1;
                } else if (i == 2) {
                    jj = jj - 1;
                } else {
                    jj = jj + 1;
                }

                Pos nn = new Pos(ii,jj,0,pos,  0, 0);

                if (isPosValid(arr, map_shown_visited, jj, ii)) {
                    // queue.add(new int[]{jj, ii});
                    pos_queue.add(nn);
//                    System.out.println("add node: jj:"+jj+"ii:"+ii);
                    map_shown_visited[ii - 1][jj - 1] = true;
                }


                if (jj == end_i && ii == end_j) {
//                    System.out.println("found");
                    return nn;
                }
            }
        }
        return null;
    }

    private static boolean isPosValid(int[][] arr, boolean[][] map_shown_visited, int jj, int ii) {

        if(jj < 1 || ii < 1 || jj > arr[0].length || ii > arr.length) {
            return false;
        }
        if (arr[ii - 1][jj - 1] == -1) {

            return false;
        }
        if (map_shown_visited[ii - 1][jj - 1] == true) {

            return false;
        }
        return true;
    }



}
