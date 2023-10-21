/* 2020/01/19 | Masaya Kobiki
** 
** �ۑ�A: ����̖��
** 
** �R�}���h���C�������Ƃ���ASCII�󎚉\��������󂯎��A�󂯎�������������בւ��Ăł��镶�����S�ĕW���o�͂ɏo�͂���v���O����"permutation"������Ă��������B
** 
** �������1�s��1���A�d�����镶�����1�����o�͂��Ă��������B
** 
*/ 
import java.util.*;

class Permutation {
	public static void main(String[] args) {
		//�G���[�`�F�b�N
		if(args.length == 0){
			System.out.println("�����ɒ��ׂ�����������w�肵�Ă��������B");
			System.exit(1);
		}
		if(args.length > 1){
			System.out.println("�����͈�����w�肵�Ă��������B");
			System.exit(1);
		}
		if(!args[0].matches("\\p{ASCII}*")){
			System.out.println("������ASCII�����Ŏw�肵�Ă��������B");
			System.exit(1);
		}
		
		//�R�}���h���C��������ϐ��Ɋi�[
		String str = args[0];
		
		//��̃��X�g�𐶐����Aperm()�ŏ�����擾����
		ArrayList<String> ary = new ArrayList<String>();
		String empt = "";//�󕶎�
		perm(empt, str, empt, ary);
		
		//�d��������΍폜����
		Set<String> tmp = new HashSet<>(ary);
		ArrayList<String> ary2 = new ArrayList<>(tmp);
		
		//�����ɂ��ďo��
		Collections.sort(ary2);
		for(String a:ary2){
			System.out.println(a);
		}
	}
	
	/*
	**	�ċA�Ăяo���ŏ�������߂܂��B
	**	���⍇����ɕ�����S�̂�n�����ƂŁAreturn���Ȃ��悤�ɂ��Ă��܂��B
	**	
	**	�@�^����ꂽ������s2�𓪂���1�������܂��B
	**		ex)ABCD �� BCD
	**	�A�����1����s1�ƁA�c����������s2�ōċA�Ăяo�����܂��B
	**		ex)A, BCD
	**	�B�c����������s2��2�����ȏ゠��Ȃ�A�����������s3�ɃZ�b�g���Ȃ���A����ɍċA���܂��B
	**		ex)ABCD,-,- �� A,BCD,- �� B,CD,A �� C,D,AB �i���L�u-�v�͋󕶎��̈Ӂj
	**	�C�c����������s2��1�����ɂȂ����Ƃ���ŁAs3+s1+s2�����X�g�Ɋi�[���܂��B
	**		ex)C,D,AB �� ABCD
	**	�D��L�@�`�C��1�������ƂɃ��[�v�����A�S�p�^�[�������X�g�Ɋi�[���܂��B�i�d���͋��j
	**		ex)ABCD �� ABDC �� ACBD �� ACDB ...
	*/
	public static void perm(String s1, String s2, String s3, ArrayList<String> ary){
		int l1 = s1.length();
		int l2 = s2.length();
		if(l2 == 1){
		//s2��1�����Ȃ�p�^�[���͈�����Ȃ��̂Ń��X�g�ɓ���ďI���
			ary.add(s3 + s1 + s2);
			
		}else{
		//s2��2�����ȏ�Ȃ�A�����1���������1�����ɂȂ�܂ōċA
			for (int i=0 ; i<l2 ; i++) {
				//��3�����̌��ɁA�����1���������� �� ��3����
				s3 = s3 + s1;
				s1 = "";
				
				//s2��i�����ڂ��J�[�\��������cur�Ƃ��� �� ��1����
				String cur = s2.substring(i, i+1);
				
				//�c��̕������zan�Ƃ��� �� ��2����
				String zan = s2.substring(0, i) + s2.substring(i+1, l2);
				
				//��2������1�����ɂȂ�܂ōċA�Ăяo��
				//ex)-,ABCD,- �� A,BCD,- �� B,CD,A �� C,D,AB
				perm(cur, zan, s3, ary);
			}
		}
	}
}