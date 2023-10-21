cd /d %~dp0

REM ビルドし直す
javac Search_domain.java



REM テスト
java Search_domain domains.txt ejje.nezuka.jp
java Search_domain domains.txt nezuka.jp example.jp
java Search_domain domains.txt ezuka-inc.jp vvv.www.example.com nezuka.co.jp.com

java Search_domain a.txt ezuka-inc.jp
java Search_domain domains.txt
java Search_domain domains.txt あいうezuka-inc.jp

pause