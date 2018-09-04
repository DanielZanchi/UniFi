%
% Metodo di Newton Modificato
% rispetto al metodo di newton standard, e' stato aggiunto
% il parametro di imput m, che rappresenta il
% coefficiente del termine di correzione
%

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

itmax = 100;


[radice, it, valf, valf1] = n(f, f1, x0, itmax);



function [radice, it, valf, valf1] = newton_m(f, f1, x0, m, itmax)
fx = feval(f, x0);
f1x = feval(f1, x0);
radice = x0 - fx/f1x;

valf = 1;
valf1 = 1;
it = 1;
for i = -1:-1:-15
    tlx = 10^(i);
    while (it < itmax) && (abs(radice-x0)>tlx)
        x0 = radice;
        fx = feval(f, x0);
        f1x = feval(f1, x0);
        radice = x0 - m*(fx/f1x);
        
        valf = valf + 1;
        valf1 = valf1 + 1;
        it = it + 1;
    end
    string = sprintf('x = %.5f, i = %d', radice, it);
    disp(string);
end
if abs(radice-x0) > tlx
    error('non converge')
end
end

%
% Metodo di Accelerazione di Aitken
%
function [radice, it, valf, val1] = aitken(f, f1, x0, imx)
fx = feval(f, x0);
f1x = feval(f1, x0);
radice = x0 - fx/f1x;
disp(radice);

valf = 1;
val1 = 1;
it = 1;
for i=-1:-1:-15
    tolx = 10^(i);
while (abs(radice-x0) > tolx) && (it < imx)
    x0 = radice;
    fx = feval(f, x0);
    f1x = feval(f1, x0);
    x1 = x0 - fx/f1x;
    fx = feval(f, x1);
    f1x = feval(f1, x1);
    radice = x1 - fx/f1x;
    radice = (radice*x0 - x1^2)/(radice-2*x1+x0);
    
    valf = valf + 2;
    val1 = val1 + 2;
    it = it + 1;
end
string = sprintf('x = %.50f, i = %d, tolx = %d', radice, it, i);
    disp(string);
end
end
