
clc;
fun = @(x) x^3-4*x^2+5*x-2;
[x, i] = bisez(fun, 0, 3);

function [x, i] = bisez(f, a, b)
%   Metodo iterativo di bisezione per il calcolo della radice di una funzione.
%   Input:
%       f: funzione
%       a: estremo a dell'intervallo di confidenza
%       b: estremo b dell'intervallo di confidenza
format long e
fprintf('\nMEDOTO DI BISEZIONE \n - Intervallo iniziale[%d, %d]\n\n', a, b);
fa = feval(f, a);
fb = feval(f, b);
x = (a+b)/2;
fx = feval(f, x);
i = 0;
tolexp = 1;
zeros = fzero(f, 0);
while fx~=0
   i = i + 1;
   if fa*fx<0
       b = x;
       fb = fx;
   else
       a = x;
       fa = fx;
   end
   x = (a+b)/2;
   fx = feval(f, x);
   diff = abs(zeros - x);
   if diff < 10^(-tolexp)
   string = sprintf('tol^(-%d)\tx = %.15f\tIter = %d', tolexp, x, i);
   disp(string);
   tolexp = tolexp + 1;
   end
end
end