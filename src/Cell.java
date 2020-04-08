public class Cell {
    int pos, ans, row, col, box;
    boolean[] candidates = new boolean[]{false, false, false, false, false, false, false, false, false};

    Cell(int pos, int ans, int row, int col){
        this.pos = pos;
        this.ans = ans;
        this.row = row;
        this.col = col;
        boxCalc();
        if(ans == 0){
            this.candidates = new boolean[]{true, true, true, true, true, true, true, true, true};
        }
    }

    void boxCalc() {
        /*Calculates which box within the puzzle the cell belongs to in range 0-8.*/
        this.box = ((row / 3) * 3 + (col / 3));
    }

    void findAns(){
        /*If the cell has one candidate set that as the answer.*/
        int count = 0;
        int num = 0;
        for(int i = 0; i < 9; i++) {
            if (candidates[i]) {
                count++;
                num = i;
            }
        }
        if(count == 1){
            num++;
            System.out.println("Answer for cell " + pos + " set to " + num);
            ans = num;
        }
    }

    void clearC(){
        /*Clears all candidates for the cell.*/
        candidates = new boolean[]{false, false, false, false, false, false, false, false, false};
    }

    void printC(){
        /*Prints all the candidates for the cell.*/
        if(ans == 0){
            System.out.println("Cell " + pos + " candidates are: ");
            for(int i = 0; i < 9; i++){
                if(candidates[i]){
                    System.out.print(i + 1);
                }
            }
        }
        else {
            System.out.println("Cell " + pos + " has ans " + ans);
        }
        System.out.println(" ");
    }

    int candCount() {
        /*Counts the number of possible candidates in the cell.*/
        int count = 0;
        for(boolean candidate : candidates) {
            if(candidate) {
                count++;
            }
        }
        return count;
    }
}
