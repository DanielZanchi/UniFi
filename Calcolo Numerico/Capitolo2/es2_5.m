x_0 = 5;
alpha = 5;

Tabella = cell2table(cell(0,3));
Tabella.Properties.VariableNames = {'i' 'sqrt_a' 'err'};

[Tabella, res] = SQRT_secanti(alpha, x_0, 200, 10^(-15), Tabella);

function [T, sqrt_alpha] = SQRT_secanti(alpha, x0, itmax, tolx, T)
x1 = (x0 + alpha/x0)/2;
x = ( (x1^2-alpha) * x0-(x0^2-alpha)*x1 ) / ((x1^2-alpha)-(x0^2 - alpha));
i = 1;
row = {i, x, abs( sqrt(alpha) - x )}; T = [T; row];
while(i < itmax) && (abs(x-x0)>tolx) x0=x1;
    x1=x;
    i = i+1;
    x = ( (x1^2 - alpha) * x0 - (x0^2 - alpha)*x1 ) / ((x1^2 - alpha) - (x0^2 - alpha));
    row = {i, x, abs( sqrt(alpha) - x )}; T = [T; row];
end
sqrt_alpha = x;
end