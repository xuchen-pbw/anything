package leetcode.array;

public class Solution_1 {
    private int rowNum = 0;
    private int colNum = 0;
    public void solve(char[][] board) {
        rowNum = board.length;
        colNum = board[0].length;
        if(board==null || rowNum<=0 || colNum<=0)
            return;
        for(int i=0;i<colNum;i++){
            if(board[0][i]=='O')
                board[0][i] = '*';
        }
        for(int i=1;i<rowNum;i++){
            if(board[i][colNum-1]=='O')
                board[i][colNum-1] = '*';
        }
        for(int i=0;i<rowNum-1;i++){
            if(board[rowNum-1][i]=='O')
                board[rowNum-1][i] = '*';
        }
        for(int i=1;i<rowNum-3;i++){
            if(board[i][0]=='O')
                board[i][0] = '*';
        }

        for(int i=0;i<rowNum;i++){
            for (int j=0;j<colNum;j++){
                if(board[i][j]=='O')
                    board[i][j] = 'X';
            }
        }
        for(int i=0;i<rowNum;i++){
            for (int j=0;j<colNum;j++){
                if(board[i][j]=='*')
                    board[i][j] = 'O';
            }
        }
    }
}
