/* 2020/01/19 | Masaya Kobiki
** 
** 課題A: 順列の問題
** 
** コマンドライン引数としてASCII印字可能文字列を受け取り、受け取った文字列を並べ替えてできる文字列を全て標準出力に出力するプログラム"permutation"を作ってください。
** 
** 文字列は1行に1つずつ、重複する文字列は1つだけ出力してください。
** 
*/ 
import java.util.*;

class Permutation {
	public static void main(String[] args) {
		//エラーチェック
		if(args.length == 0){
			System.out.println("引数に調べたい文字列を指定してください。");
			System.exit(1);
		}
		if(args.length > 1){
			System.out.println("引数は一つだけ指定してください。");
			System.exit(1);
		}
		if(!args[0].matches("\\p{ASCII}*")){
			System.out.println("引数はASCII文字で指定してください。");
			System.exit(1);
		}
		
		//コマンドライン引数を変数に格納
		String str = args[0];
		
		//空のリストを生成し、perm()で順列を取得する
		ArrayList<String> ary = new ArrayList<String>();
		String empt = "";//空文字
		perm(empt, str, empt, ary);
		
		//重複があれば削除する
		Set<String> tmp = new HashSet<>(ary);
		ArrayList<String> ary2 = new ArrayList<>(tmp);
		
		//昇順にして出力
		Collections.sort(ary2);
		for(String a:ary2){
			System.out.println(a);
		}
	}
	
	/*
	**	再帰呼び出しで順列を求めます。
	**	副問合せ先に文字列全体を渡すことで、returnしないようにしています。
	**	
	**	①与えられた文字列s2を頭から1文字削ります。
	**		ex)ABCD → BCD
	**	②削った1文字s1と、残った文字列s2で再帰呼び出しします。
	**		ex)A, BCD
	**	③残った文字列s2が2文字以上あるなら、削った文字をs3にセットしながら、さらに再帰します。
	**		ex)ABCD,-,- → A,BCD,- → B,CD,A → C,D,AB （左記「-」は空文字の意）
	**	④残った文字列s2が1文字になったところで、s3+s1+s2をリストに格納します。
	**		ex)C,D,AB → ABCD
	**	⑤上記①～④を1文字ごとにループさせ、全パターンをリストに格納します。（重複は許可）
	**		ex)ABCD → ABDC → ACBD → ACDB ...
	*/
	public static void perm(String s1, String s2, String s3, ArrayList<String> ary){
		int l1 = s1.length();
		int l2 = s2.length();
		if(l2 == 1){
		//s2が1文字ならパターンは一つしかないのでリストに入れて終わり
			ary.add(s3 + s1 + s2);
			
		}else{
		//s2が2文字以上なら、さらに1文字削って1文字になるまで再帰
			for (int i=0 ; i<l2 ; i++) {
				//第3引数の後ろに、削った1文字を結合 → 第3引数
				s3 = s3 + s1;
				s1 = "";
				
				//s2のi文字目をカーソル文字列curとする → 第1引数
				String cur = s2.substring(i, i+1);
				
				//残りの文字列をzanとする → 第2引数
				String zan = s2.substring(0, i) + s2.substring(i+1, l2);
				
				//第2引数が1文字になるまで再帰呼び出し
				//ex)-,ABCD,- → A,BCD,- → B,CD,A → C,D,AB
				perm(cur, zan, s3, ary);
			}
		}
	}
}