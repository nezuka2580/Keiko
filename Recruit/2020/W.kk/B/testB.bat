cd /d %~dp0

REM �r���h������
javac Search_domain.java



REM �e�X�g
java Search_domain domains.txt ejje.nezuka.jp
java Search_domain domains.txt nezuka.jp example.jp
java Search_domain domains.txt ezuka-inc.jp vvv.www.example.com nezuka.co.jp.com

REM �e�X�g�i�ُ�j
java Search_domain a.txt ezuka-inc.jp
java Search_domain domains.txt
java Search_domain domains.txt ������ezuka-inc.jp

pause