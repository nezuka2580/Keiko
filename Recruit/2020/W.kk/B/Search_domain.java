/* 2020/01/20 | Masaya Kobiki
** 
** 課題B: 検索の問題
** 
** テキストファイル "domains.txt" があるとします。
** 中にはいろいろなドメイン名が1行に1つずつ書いてあります。
** 
** 以下の条件を全て満たすプログラム"search-domain"を作ってください。
** 
** ・"domains.txt"の内容を「標準入力から」受け取る
** ・コマンドライン引数で「任意の数のドメイン名」を受け取る
** ・コマンドラインから指定されたドメインそれぞれについて、標準入力から入力されたドメインのいずれかで「終端」するか否かを判定して、以下の内容を標準出力に1行に1ドメインずつ出力する
**     ・いずれかで終端する場合は "コマンドラインから指定されたドメイン:終端するドメイン" のようにコロンで区切って出力
**     ・いずれでも終端しない場合は "コマンドラインから指定されたドメイン:NONE" を出力
** ・終端するドメインが複数ある場合は、いずれか1つだけ出力すればよい
** ・終端の判定に要する時間は、標準入力から入力されたドメインの数に依存しない  ←★重要★ トライ木で実装。
** 
** （domains.txtの例）
** ----------
** nezuka.jp
** nezuka.co.jp
** nezuka-inc.jp
** example.com
** ----------
** 
*/ 

import java.util.*;
import java.io.*;

public class Search_domain {
	public static void main(String[] args) throws Exception {
		//エラーチェック
		if(args.length < 2){
			System.out.println("1つ目の引数にdomains.txt、2つ目以降に検索したいドメインを指定してください。");
			System.exit(1);
		}
		if(!args[0].equals("domains.txt")){
			System.out.println("1つ目の引数はdomains.txtを指定してください。");
			System.exit(1);
		}
		
		Tree t = new Tree();
		//domains.txtの読み込み
		try{
			Scanner sc = new Scanner(new File(args[0]));
			//domains.txt行数のカウンタ
			int line = 1;
			while(sc.hasNext()){
				//エラーチェック
				String tmp = sc.nextLine();
				if(!tmp.matches("\\p{ASCII}*")){
					System.out.println("domains.txtの"+line+"行目にASCII文字以外が存在します。");
					System.exit(1);
				}
				//Treeに追加
				t.add(tmp);
				line++;
			}
		}catch(FileNotFoundException e){
			System.out.println("ファイルが見つかりません。");
			System.exit(1);
		}
		
		//isDomainがtrueならドメイン名を出力する。falseなら、
		//検索文字列を[.]まで削って再判定を繰り返す。
		//[.]を含まなくなったら再判定せずNONEを出力。
		for(int i=1 ; i<args.length ; i++){
			//エラーチェック
			if(!args[i].matches("\\p{ASCII}*")){
				System.out.println("第" + (i+1) + "引数にASCII文字以外が存在します。");
				System.exit(1);
			}
			if(t.isDomain(args[i])){
				//検索対象文字列：ドメイン名
				System.out.println(args[i] + ":" + args[i]);
			}else{
				String s = args[i];
				Boolean b = false;
				while(!b){
					//検索対象文字列を「.」まで削る
					s = s.substring(s.indexOf(".")+1, s.length());
					//「.」を含まなくなったら再判定繰り返しを終了
					if(s.indexOf(".") == -1) break;
					b = t.isDomain(s);
				}
			   if(b){
					//検索対象文字列：ドメイン名
					System.out.println(args[i] + ":" + s);
				}else{
					//検索対象文字列：NONE
					System.out.println(args[i] + ":" + "NONE");
				}
			}
		}
	}
}
//各ノードのクラス
//ノードごとに、ASCIIコード128種を1文字ごと格納できる配列を持つ。
//ex)rootノードでは、子に対象の1文字(w)が存在すれば、その文字に該当する
//インデックス(119)に孫ノードがセットされる入れ子のような構造。
class Node{
	//ASCIIコード128種
	private Node[] chld = new Node[128];
	//このノードが語句の終わりならフラグON
	private boolean endFlg = false;
	
	//getter,setter
	public Node getChld(int idx) {
		return chld[idx];
	}
	public void setChld(int idx, Node n) {
		chld[idx] = n;
	}
	public boolean getEndFlg() {
		return endFlg;
	}
	public void setEndFlg(boolean b) {
		endFlg = b;
	}
}

//ツリー本体のクラス
class Tree{
	//はじめにrootノードを生成
	private Node root = new Node();
	
	//登録したい語句を引数で受け取り、前から1文字ずつ
	//子ノードがあるか確認し、無い文字は追加する。
	public void add(String s) {
		//rootから探索を開始する
		Node cur = root;
		int len = s.length();
		
		//0文字目から1文字ずつ語句の最後まで処理する
		for(int i=0 ; i<len ; i++){
			//処理中の文字（char）をint型にキャスト
			int x = s.charAt(i);
			//処理中の文字のインデックスに子ノードがセットされてなければセット
			if (cur.getChld(x) == null) {
				cur.setChld(x, new Node());
			}
			//カーソルを次の文字へ進める
			cur = cur.getChld(x);
		}
		//forが回りきったので語句の最後フラグを立てる
		cur.setEndFlg(true);
	}
	
	//検索したいドメイン名候補文字列を引数にTreeから検索する。
	//1文字ずつTreeに一致するか確認していき、検索文字列を読み切って
	//Treeのノードに末尾フラグが立っていれば一致したとしてtrueを返却。
	public boolean isDomain(String s){
		boolean ret = false;
		//rootから探索を開始する
		Node cur = root;
		int len = s.length();
		//0文字目から1文字ずつ語句の最後まで処理する
		for(int i=0 ; i<len ; i++){
			//処理中の文字（char）をint型にキャスト
			int x = s.charAt(i);
			//処理中の文字のインデックスに子ノードがセットされてなければfalse
			if (cur.getChld(x) == null) {
				return false;
			}
			//カーソルを次の文字へ進める
			cur = cur.getChld(x);
		}
		//forが回りきった上で語句の最後ならtrue
		return cur.getEndFlg();
	}
}