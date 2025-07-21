/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * kTFRUAddon is Open Source and distributed under the
 * AGPLv3 License: https://www.gnu.org/licenses/agpl-3.0.txt
 */

package cn.kuzuanpa.ktfruaddon.tile.multiblock.machine;

public class test {
    /**
     * 转置二维数组
     * @param matrix 原始二维数组
     * @return 转置后的二维数组
     */
    public static int[][] transpose(int[][] matrix) {
        // 检查输入数组是否为空
        if (matrix == null || matrix.length == 0) {
            return new int[0][0];
        }

        int rows = matrix.length;
        int cols = matrix[0].length;

        // 创建新的转置数组
        int[][] transposed = new int[cols][rows];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                transposed[j][i] = matrix[i][j];
            }
        }

        return transposed;
    }

    // 测试方法
    public static void main(String[] args) {
        for (int i = 0; i < blockIDMap.length; i++) {
            int[][] matrix = blockIDMap[i];

            System.out.println(").fixedLayer('"+i+"',");
            int[][] transposed = transpose(matrix);

            printMatrix(transposed);
        }
    }

    // 辅助方法：打印二维数组
    private static void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            System.out.print("\"");
            for (int num : row) {
                System.out.print(num==0?" ":num == 31015?"A":num==18002?"W": num==31017?"B": num==31016?"C": num==31018?"D": num==31019?"F": num==31024?"G": num==32005?"H":num);
            }
            System.out.print("\",");
            System.out.println();
        }
    }
    public final static int[][][] blockIDMap = {{
            {18004, 18004, 18004, 18004, 18004},
            {18004, 18004, 18004, 18004, 18004},
            {18004, 18004, 31039, 18004, 18004},
            {18004, 18004, 18004, 18004, 18004},
            {18004, 18004, 18004, 18004, 18004},
    },{
            {18004, 18004,   0  , 18004, 18004},
            {18004, 31003, 31003, 31003, 18004},
            {18004, 31003, 31003, 31003, 18004},
            {18004, 31003, 31003, 31003, 18004},
            {18004, 18004, 18004, 18004, 18004},
    },{
            {  0  , 18004, 18004, 18004,   0  },
            {18004, 31003, 31003, 31003, 18004},
            {18004, 31003, 31003, 31003, 18004},
            {18004, 31003, 31003, 31003, 18004},
            {  0  , 18004, 18004, 18004,   0  },
    },{
            {  0  ,   0  , 18004,   0  ,   0  },
            {  0  , 18004, 31004, 18004,   0  },
            {18004, 31004, 31004, 31004, 18004},
            {  0  , 18004, 31004, 18004,   0  },
            {  0  ,   0  , 18004,   0  ,   0  },
    }};
}
