/* 2020/01/20 | Masaya Kobiki
** 
** �ۑ�B: �����̖��
** 
** �e�L�X�g�t�@�C�� "domains.txt" ������Ƃ��܂��B
** ���ɂ͂��낢��ȃh���C������1�s��1�������Ă���܂��B
** 
** �ȉ��̏�����S�Ė������v���O����"search-domain"������Ă��������B
** 
** �E"domains.txt"�̓��e���u�W�����͂���v�󂯎��
** �E�R�}���h���C�������Łu�C�ӂ̐��̃h���C�����v���󂯎��
** �E�R�}���h���C������w�肳�ꂽ�h���C�����ꂼ��ɂ��āA�W�����͂�����͂��ꂽ�h���C���̂����ꂩ�Łu�I�[�v���邩�ۂ��𔻒肵�āA�ȉ��̓��e��W���o�͂�1�s��1�h���C�����o�͂���
**     �E�����ꂩ�ŏI�[����ꍇ�� "�R�}���h���C������w�肳�ꂽ�h���C��:�I�[����h���C��" �̂悤�ɃR�����ŋ�؂��ďo��
**     �E������ł��I�[���Ȃ��ꍇ�� "�R�}���h���C������w�肳�ꂽ�h���C��:NONE" ���o��
** �E�I�[����h���C������������ꍇ�́A�����ꂩ1�����o�͂���΂悢
** �E�I�[�̔���ɗv���鎞�Ԃ́A�W�����͂�����͂��ꂽ�h���C���̐��Ɉˑ����Ȃ�  �����d�v�� �g���C�؂Ŏ����B
** 
** �idomains.txt�̗�j
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
		//�G���[�`�F�b�N
		if(args.length < 2){
			System.out.println("1�ڂ̈�����domains.txt�A2�ڈȍ~�Ɍ����������h���C�����w�肵�Ă��������B");
			System.exit(1);
		}
		if(!args[0].equals("domains.txt")){
			System.out.println("1�ڂ̈�����domains.txt���w�肵�Ă��������B");
			System.exit(1);
		}
		
		Tree t = new Tree();
		//domains.txt�̓ǂݍ���
		try{
			Scanner sc = new Scanner(new File(args[0]));
			//domains.txt�s���̃J�E���^
			int line = 1;
			while(sc.hasNext()){
				//�G���[�`�F�b�N
				String tmp = sc.nextLine();
				if(!tmp.matches("\\p{ASCII}*")){
					System.out.println("domains.txt��"+line+"�s�ڂ�ASCII�����ȊO�����݂��܂��B");
					System.exit(1);
				}
				//Tree�ɒǉ�
				t.add(tmp);
				line++;
			}
		}catch(FileNotFoundException e){
			System.out.println("�t�@�C����������܂���B");
			System.exit(1);
		}
		
		//isDomain��true�Ȃ�h���C�������o�͂���Bfalse�Ȃ�A
		//�����������[.]�܂ō���čĔ�����J��Ԃ��B
		//[.]���܂܂Ȃ��Ȃ�����Ĕ��肹��NONE���o�́B
		for(int i=1 ; i<args.length ; i++){
			//�G���[�`�F�b�N
			if(!args[i].matches("\\p{ASCII}*")){
				System.out.println("��" + (i+1) + "������ASCII�����ȊO�����݂��܂��B");
				System.exit(1);
			}
			if(t.isDomain(args[i])){
				//�����Ώە�����F�h���C����
				System.out.println(args[i] + ":" + args[i]);
			}else{
				String s = args[i];
				Boolean b = false;
				while(!b){
					//�����Ώە�������u.�v�܂ō��
					s = s.substring(s.indexOf(".")+1, s.length());
					//�u.�v���܂܂Ȃ��Ȃ�����Ĕ���J��Ԃ����I��
					if(s.indexOf(".") == -1) break;
					b = t.isDomain(s);
				}
			   if(b){
					//�����Ώە�����F�h���C����
					System.out.println(args[i] + ":" + s);
				}else{
					//�����Ώە�����FNONE
					System.out.println(args[i] + ":" + "NONE");
				}
			}
		}
	}
}
//�e�m�[�h�̃N���X
//�m�[�h���ƂɁAASCII�R�[�h128���1�������Ɗi�[�ł���z������B
//ex)root�m�[�h�ł́A�q�ɑΏۂ�1����(w)�����݂���΁A���̕����ɊY������
//�C���f�b�N�X(119)�ɑ��m�[�h���Z�b�g��������q�̂悤�ȍ\���B
class Node{
	//ASCII�R�[�h128��
	private Node[] chld = new Node[128];
	//���̃m�[�h�����̏I���Ȃ�t���OON
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

//�c���[�{�̂̃N���X
class Tree{
	//�͂��߂�root�m�[�h�𐶐�
	private Node root = new Node();
	
	//�o�^���������������Ŏ󂯎��A�O����1��������
	//�q�m�[�h�����邩�m�F���A���������͒ǉ�����B
	public void add(String s) {
		//root����T�����J�n����
		Node cur = root;
		int len = s.length();
		
		//0�����ڂ���1���������̍Ō�܂ŏ�������
		for(int i=0 ; i<len ; i++){
			//�������̕����ichar�j��int�^�ɃL���X�g
			int x = s.charAt(i);
			//�������̕����̃C���f�b�N�X�Ɏq�m�[�h���Z�b�g����ĂȂ���΃Z�b�g
			if (cur.getChld(x) == null) {
				cur.setChld(x, new Node());
			}
			//�J�[�\�������̕����֐i�߂�
			cur = cur.getChld(x);
		}
		//for����肫�����̂Ō��̍Ō�t���O�𗧂Ă�
		cur.setEndFlg(true);
	}
	
	//�����������h���C������╶�����������Tree���猟������B
	//1��������Tree�Ɉ�v���邩�m�F���Ă����A�����������ǂݐ؂���
	//Tree�̃m�[�h�ɖ����t���O�������Ă���Έ�v�����Ƃ���true��ԋp�B
	public boolean isDomain(String s){
		boolean ret = false;
		//root����T�����J�n����
		Node cur = root;
		int len = s.length();
		//0�����ڂ���1���������̍Ō�܂ŏ�������
		for(int i=0 ; i<len ; i++){
			//�������̕����ichar�j��int�^�ɃL���X�g
			int x = s.charAt(i);
			//�������̕����̃C���f�b�N�X�Ɏq�m�[�h���Z�b�g����ĂȂ����false
			if (cur.getChld(x) == null) {
				return false;
			}
			//�J�[�\�������̕����֐i�߂�
			cur = cur.getChld(x);
		}
		//for����肫������Ō��̍Ō�Ȃ�true
		return cur.getEndFlg();
	}
}