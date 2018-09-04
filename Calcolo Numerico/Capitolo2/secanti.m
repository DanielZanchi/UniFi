% input: funzione, derivata prima, punto di innesco,
%        tolleranza, numero massimo iterazioni
% output: radice approssimata, numero di iterazioni eseguite,
%         numero valutazioni di f(x) e di f'(x)

clc;
f = @(x) x^3-4*x^2+5*x-2;
f1 = @(x) 3*x^2-8*x+5;
x0 = 5/3;

% f = @(x) x - cos(x);
% f1 = @(x) 1 + sin(x);
% x0 = 0;

itmax = 500;
[radice, it, valf, val1] = sec(f, f1, x0, itmax)

function [radice, it, valf, val1] = sec(f, f1, x0, imax)
fx = feval(f, x0);
f1x = feval(f1, x0);
valf = 1;
val1 = 1;
it = 1;
radice = x0 - fx / f1x;
for i = -1: -1: -15
    tolx = 10^(i);
    while (it < imax) && (abs(radice-x0) > tolx)
        fx = feval(f, radice);
        fx0 = feval(f, x0);
        valf = valf + 2;
        it = it + 1;
        x1 = (fx*x0 - fx0*radice) / (fx - fx0);
        x0 = radice;
        radice = x1;
    end
    string = sprintf('x = %.5f, i = %d', radice, it);
    disp(string);
end
if abs(radice-x0) > tolx
    error('non converge')
end
end