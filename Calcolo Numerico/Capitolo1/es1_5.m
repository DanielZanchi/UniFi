format long e
a = 0.1
b = dec2bin(0.1)
%delta = 1/20;

%x = 0;
%count = 0;
%while x~=1, x=x+delta, count = count+1, end