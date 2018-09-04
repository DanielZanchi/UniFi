% input: funzione, derivata prima, punto di innesco,
%        tolleranza, numero massimo iterazioni
% output: radice approssimata, numero di iterazioni eseguite,
%         numero valutazioni di f(x) e di f'(x)

clc;
% f = @(x) x^3-4*x^2+5*x-2;
% f1 = @(x) 3*x^2-8*x+5;
% x0 = 0;

f = @(x) (x-pi)^10;
f1 = @(x) 10*(x-pi)^9;
x0 = 5;

% f = @(x) x - cos(x);
% f1 = @(x) 1 + sin(x);
% x0 = 0;

itmax = 500;
a = newton(f, f1, x0, itmax)

function [radice, it, valf, valf1] = newton(f, f1, x0, itmax)
fx = feval(f, x0);
f1x = feval(f1, x0);
radice = x0 - fx/f1x;
valf = 1;
valf1 = 1;
it = 1;
i = -1;
for i = -1: -1: -15
    tolx = 10^(i);
    while (it < itmax) && (abs(radice-x0) > tolx)
        it = it + 1;
        x0 = radice;
        fx = feval(f, x0);
        f1x = feval(f1, x0);
        valf = valf + 1;
        valf1 = valf1 + 1;
        radice = x0 - fx/f1x;
    end
    string = sprintf('x = %.5f, i = %d', radice, it);
    disp(string);
end

if abs(radice-x0) > tolx
    error('non converge')
end
end