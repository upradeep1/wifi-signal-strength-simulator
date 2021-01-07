import java.util.Scanner;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/** Starter code for Project 4: wireless router signal strength.
 */
public class Proj1 {

   public static void main(String[] args) throws IOException {
   // THIS METHOD IS COMPLETE - DO NOT CHANGE IT

      final double EPS = .0001;
      final String PROMPT1 = "Enter name of grid data file: ";
      final String ERROR = "ERROR: invalid input file";

      Scanner kb = new Scanner(System.in);
      System.out.print(PROMPT1);
      String name = kb.nextLine();

      FileInputStream infile = new FileInputStream(name);
      Scanner scanFile = new Scanner(infile);
      double size = scanFile.nextDouble();
      int rows = scanFile.nextInt();
      int cols = scanFile.nextInt();
      scanFile.nextLine(); // skip past end of line character

      FileOutputStream outstream = new FileOutputStream("signals.txt");
      PrintWriter outfile = new PrintWriter(outstream);

      Cell[][] grid = new Cell[rows][cols];
      Cell[][] old = new Cell[rows][cols];
      initialize(grid);
      initialize(old);

      int routerRow;
      int routerCol;
      final double ROUTER = 23;


      read(grid, scanFile);
      if (! isValid(grid)) {
         System.out.println("ERROR:invalid input file");
      } else {
         // keep processing
         System.out.print("Enter router row and column: ");
         routerRow = kb.nextInt();
         routerCol = kb.nextInt();
         grid[routerRow][routerCol].setSignal(ROUTER);

         setAllDirections(grid, routerRow, routerCol);
         setAllDistances(grid, routerRow, routerCol, size);

         while (! equivalent(grid, old, EPS)) {
            copy(grid, old);
            iterate(grid, old, routerRow, routerCol);
            printAll(grid, outfile);
            outfile.println();  // blank link separator
         }
      }

      double minSignal = findMinSignal(grid);
      System.out.println("minimum signal strength: " + minSignal +
                     " occurs in these cells: ");
      printMinCellCoordinates(grid, minSignal);

      outstream.flush();
      outfile.close();
   }

   /** Set the direction from the router position to every other cell
    *  in the grid. (Do not change the direction of the router cell.)
    *  @param grid the grid of cells to manage
    *  @param routerRow the row position of the router cell in the grid
    *  @param routerCol the column position of the router cell in the grid
    */

   public static void setAllDirections(Cell[][] grid, int routerRow,
                                       int routerCol) {

   for(int i=0; i<grid.length;i+=1){

      for (int j=0;j<grid[0].length;j+=1){
            grid[i][j].setDirection(direction(routerRow,routerCol,i,j));
         }
       }


   }

   /** Set the distance from the router position to every other cell
    *  in the grid. (Do not change the distance of the router cell.)
    *  @param grid the grid of cells to manage
    *  @param routerRow the row position of the router cell in the grid
    *  @param routerCol the column position of the router cell in the grid
    *  @param size the size of each cell
    */
   public static void setAllDistances(Cell[][] grid, int routerRow,
                                       int routerCol, double size) {
   //THIS METHOD IS COMPLETE -DO NOT CHANGE IT
      for (int i = 0; i < grid.length; i++) {
         for (int j = 0; j < grid[0].length; j++) {
            if (! (i == routerRow && j == routerCol)) {
               grid[i][j].setDistance(size * Math.sqrt(Math.pow(routerRow - i,
                        2) + Math.pow(routerCol - j, 2)));
            }
         }
      }
   }


   /** Iterate over the grid, updating the signal strength and
    *  attenuation rate of each cell based on the old values of
    *  the relevant neighbor cells.
    *  @param current the updated values of each cell
    *  @param previous the old values of each cell
    *  @param routerRow the row position of the router's cell
    *  @param routerCol the column position of the router's cell
    */
   public static void iterate(Cell[][] current, Cell[][] previous,
                              int routerRow, int routerCol) {
   // TODO: write this method body

   }

   /** Calculate the signal transmission free space path loss (FSPL).
    *  @param distance the distance from the source to the receiver
    *  @param frequency the frequency of the transmission
    *  @return the fspl ratio
    */
   public static double fspl(double distance, double frequency) {
   // TODO: write this method body
      double freespl;
      freespl=20*Math.log10(distance)+20*Math.log10(frequency)+92.45;
      return freespl;double freespl;

   }

   /** Calculate the attenuation rate of a cell based on the
    *  attenuation of its relevant neighbor(s).
    *  @param prev the grid of cells from prior iteration
    *  @param row the row of the current cell
    *  @param col the column of the current cell
    *  @return the new attenuation rate of that cell
    */
   public static int attenRate(Cell[][] prev, int row, int col) {
   // TODO: write this method body
      String Direc=prev[row][col].getDirection;
//      int cord_neigh_row,cord_neigh_col;
//      if( Direc=="N"){
//         coord_neigh_row=row-1;
//         coord_neigh_col=col;
//         atten_rate_neigh= prev[coord_neigh_row][coord_neigh_col].getRate();
//         atten_wall=prev[coord_neigh_row][coord_neigh_col].getSouth();
//         atten_rate=atten_rate_neigh+atten_wall;
//
//         }
//      else if( Direc=="S"){
//         cord_neigh_row=row+1;
//         cord_neigh_col=col;
//         atten_rate_neigh= prev[coord_neigh_row][coord_neigh_col].getRate();
//         atten_wall=prev[coord_neigh_row][coord_neigh_col].getNorth();
//         atten_rate=atten_rate_neigh+atten_wall;
//         }
//      else if( Direc=="E"){
//         cord_neigh_row=row;
//         cord_neigh_col=col+1;
//         atten_rate_neigh= prev[coord_neigh_row][coord_neigh_col].getRate();
//         atten_wall=prev[coord_neigh_row][coord_neigh_col].getWest();
//         atten_rate=atten_rate_neigh+atten_wall;
//         }
//      else if (Direc=="W"){
//         cord_neigh_row=row;
//         cors_neigh_col=col-1;
//         atten_rate_neigh= prev[coord_neigh_row][coord_neigh_col].getRate();
//         atten_wall=prev[coord_neigh_row][coord_neigh_col].getEast();
//         atten_rate=atten_rate_neigh+atten_wall;
//         }
//      else if (Direc=="NE"){
//
//         atten_rate_N= prev[row-1][col].getRate()+prev[coord_neigh_row][coord_neigh_col].getSouth();
//         atten_rate_E= prev[row][col+1].getRate()+prev[coord_neigh_row][coord_neigh_col].getWest();
//
//         if (atten_neigh_N>atten_neigh_E){
//            atten_rate= atten_rate_N;
//            }
//         else if (atten_neigh_N<atten_neigh_E){
//            atten_rate=atten_rate_E;
//          }
//         else {atten_rate=atten_rate_E;}
//       }
//      else if(Direc=="NW"){
//         atten_rate_N= prev[row-1][col].getRate()+prev[coord_neigh_row][coord_neigh_col].getSouth();
//         atten_rate_W= prev[row][col-1].getRate()+prev[coord_neigh_row][coord_neigh_col].getEast();
//         if (atten_neigh_N>atten_neigh_W){
//            atten_rate= atten_rate_N;
//            }
//         else if (atten_neigh_N<atten_neigh_W){
//            atten_rate=atten_rate_W;
//            }
//         else {atten_rate=atten_rate_W;}
//
//       }
//     else if (Direc=="SE"){
//         atten_rate_S= prev[row+1][col].getRate()+prev[coord_neigh_row][coord_neigh_col].getNorth();
//         atten_rate_E= prev[row][col+1].getRate()+prev[coord_neigh_row][coord_neigh_col].getWest();
//
//         if (atten_neigh_S>atten_neigh_E){
//            atten_rate= atten_rate_S;
//            }
//         else if (atten_neigh_S<atten_neigh_E){
//            atten_rate=atten_rate_E;
//          }
//         else {atten_rate=atten_rate_E;}
//       }
//   else if(Direc=="SW"){
//         atten_rate_S= prev[row+1][col].getRate()+prev[coord_neigh_row][coord_neigh_col].getNorth();
//         atten_rate_W= prev[row][col-1].getRate()+prev[coord_neigh_row][coord_neigh_col].getEast();
//         if (atten_neigh_S>atten_neigh_W){
//            atten_rate= atten_rate_S;
//            }
//         else if (atten_neigh_S<atten_neigh_W){
//            atten_rate=atten_rate_W;
//            }
//         else {atten_rate=atten_rate_W;}
//
//
//      return atten_rate;
      return -1;
   }


   /** Find the direction between the router cell and the current cell.
    *  @param r0 the router row
    *  @param c0 the router column
    *  @param r1 the current cell row
    *  @param c1 the current cell column
    *  @return a string direction heading (N, E, S, W, NE, SE, SW, NW)
    */
   public static String direction(int r0, int c0, int r1, int c1) {
   //THIS METHOD IS COMPLETE -DO NOT CHANGE IT
      int rDelta = Math.abs(r0 - r1);
      int cDelta = Math.abs(c0 - c1);

      if (rDelta > cDelta) {
         if (r1 < r0) {
            return "N";
         } else if (r1 > r0) {
            return "S";
         }
      }
      if (rDelta < cDelta) {
         if (c1 > c0) {
            return "E";
         } else if (c1 < c0) {
            return "W";
         }
      }

      // rDelta == cDelta -> on a diagonal
      if (r1 < r0 && c1 > c0) {
         return "NE";
      } else if (r1 < r0 && c1 < c0) {
         return "NW";
      } else if (r1 > r0 && c1 < c0) {
         return "SW";
      } else {
         return "SE";
      }
   }


   /** Determine if the corresponding cells in the two grids of the same size
    *  have the same signal value, to a specified precision.
    *  @param grid1 the first grid
    *  @param grid2 the second grid
    *  @param epsilon the difference cutoff that makes two values "equivalent"
    *  @return true if the grids are the same sizes, and the signal values
    *   are all within (<=) epsilon of each other; false otherwise
    */
   public static boolean equivalent(Cell[][] grid1, Cell[][] grid2,
                                    double epsilon) {
      boolean bool=false;
      if(grid1.length==grid2.length && grid1[0].length==grid2[0].length){
         bool=true;
         for (int i = 0; i < grid1.length; i++) {
            for (int j = 0; j < grid1[0].length; j++) {
               if (Math.abs(grid1[i][j].getSignal()-grid2[i][j].getSignal())<=epsilon) {
                  bool=true;
                  }
               else {bool=false;}
              }
           }

         }
      else {bool=false;}


      return bool;
   }


   /**
    * Read a grid from a plain text file using a Scanner that has
    * already advanced past the first line. This method assumes the
    * specified file exists. Each subsequent line provides the wall
    * information for the cells in a single row, using a 4-character
    * string in NESW (north-east-south-west) order for each cell.
    * @param grid is the grid whose Cells must be updated with the input data
    * @param scnr is the Scanner to use to read the rest of the file
    * @throws IOException if file can not be read
    */
   public static void read(Cell[][] grid, Scanner scnr) throws IOException {
      // TODO: write the body of this method
      int rows = grid.length;
      int columns = grid[0].length;
      for (Cell[] cells : grid) {
         for (int col = 0; col < columns; col++) {
            String s = scnr.next();
            cells[col].setWalls(s);
//**change**

         }
      }
   }


   /**
    * Validate the cells of a maze as being consistent with respect
    * to neighboring internal walls. For example, suppose some cell
    * C has an east wall with material 'b' for brick. Then for the
    * maze to be valid, the cell to C's east must have a west wall
    * that is also 'b' for brick. (This method does not need to check
    * external walls.)
    * @param grid the grid to check
    * @return true if valid (consistent), false otherwise
    */
   public static boolean isValid(Cell[][] grid) {
   // TODO: write the body of this method
      boolean bool=false;
      for (Cell[] cells : grid) {
         for (int j = 0; j < grid[0].length; j++) {
            if (cells[j].getEast() != cells[j + 1].getWest()) {
               bool = false;
            } else {
               bool = true;
            }
         }
      }

      for (int i=0;i<grid.length;i++) {
         for (int j =0;j<grid[0].length;j++) {
            if(grid[i][j].getSouth()!=grid[i+1][j].getNorth()){
               bool= false;
               }
            else {bool= true;}

          }
        }
      return bool;
   }

   /** Find the minimum cell signal strength.
    *  @param grid the grid of cells to search
    *  @return the minimum signal value
    */
   public static double findMinSignal(Cell[][] grid) {
   // TODO: write the body of this method
   double min=grid[0][0].getSignal();
   for (int i=0;i<grid.length;i++) {
      for (int j =0;j<grid[0].length;j++) {
         if(min>grid[i][j].getSignal()) {
            min=grid[i][j].getSignal();
            }
         }
      }
      return min;
   }

   /** Print the coordinates of cells with <= the minimum signal strength,
    *  one per line in (i, j) format, in row-column order.
    *  @param grid the collection of cells
    *  @param minSignal the minimum signal strength
    */
   public static void printMinCellCoordinates(Cell[][] grid, double minSignal) {
   // TODO: write the body of this method
      for (int i=0;i<grid.length;i++) {
         for (int j =0;j<grid[0].length;j++) {
            if (grid[i][j].getSignal()<= minSignal) {
               System.out.println("("+i+","+j+")");
               }
            }
         }
   }

   /** Get the attenuation rate of a wall material.
    *  @param wall the material type
    *  @return the attenuation rating
    */
   public static int attenuation(char wall) {
   // THIS METHOD IS COMPLETE - DO NOT CHANGE IT
      switch (wall) {
         case 'b':
            return 22;
         case 'c':
            return 6;
         case 'd':
            return 4;
         case 'g':
            return 20;
         case 'w':
            return 6;
         case 'n':
            return 0;
         default:
            System.out.println("ERROR: invalid wall type");
      }
      return -1;
   }


   /** Create a copy of a grid by copying the contents of each
    *  Cell in an original grid to a copy grid. Note that we use the
    *  makeCopy method in the Cell class for this to work correctly.
    *  @param from the original grid
    *  @param to the copy grid
    */
   public static void copy(Cell[][] from, Cell[][] to) {
   // THIS METHOD IS COMPLETE - DO NOT CHANGE IT
      for (int i = 0; i < from.length; i++) {
         for (int j = 0; j < from[0].length; j++) {
            to[i][j] = from[i][j].makeCopy();
         }
      }
   }

   /** Initialize a grid to contain a bunch of new Cell objects.
    *  @param grid the array to initialize
    */
   public static void initialize(Cell[][] grid) {
   // THIS METHOD IS COMPLETE - DO NOT CHANGE IT
      for (int i = 0; i < grid.length; i++) {
         for (int j = 0; j < grid[0].length; j++) {
            grid[i][j] = new Cell();
         }
      }
   }

   /** Display the computed values of a grid (signal strenth, direction,
    *  attenuation rate, and distance to the provided output destination,
    *  using the format provided by the toString method in the Cell class.
    *  @param grid the signal grid to display
    *  @param pout the output location
    */
   public static void printAll(Cell[][] grid, PrintWriter pout) {
   // THIS METHOD IS COMPLETE - DO NOT CHANGE IT
      for (int i = 0; i < grid.length; i++) {
         for (int j = 0; j < grid[0].length; j++) {
            pout.print(grid[i][j].toString() + " ");
         }
         pout.println();
      }
   }
}