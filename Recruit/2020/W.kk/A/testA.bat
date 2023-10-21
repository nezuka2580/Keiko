cd /d %~dp0


REM ビルドし直す
javac Permutation.java


REM テスト

java Permutation AB
java Permutation ABC
java Permutation ABB
java Permutation ABCC

REM テスト（異常）
java Permutation
java Permutation A B
java Permutation あいう

pause